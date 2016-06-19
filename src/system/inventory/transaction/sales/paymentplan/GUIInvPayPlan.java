/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.paymentplan;

import core.Exp;
import core.Locals;
import core.system_transaction.SystemSetting;
import core.system_transaction.TransactionType;
import core.system_transaction.account_trans.AccountSetting;
import core.system_transaction.account_trans.AccountSettingObject;
import core.system_transaction.account_trans.AccountTransStatus;
import core.system_transaction.account_trans.AccountTransactionDescription;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.transaction.OBJTransactionHistory;
import core.system_transaction.transaction.TransactionStatus;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.SERCommen;
import mainApp.findwindow.GUIFindWindow;
import system.inventory.master.salesrep.SERSalesRep;

/**
 *
 * @author Rabid
 */
public class GUIInvPayPlan extends javax.swing.JDialog {

    /**
     * Creates new form GUIInvPayPlan
     *
     * @param frame
     * @param str
     */
    public GUIInvPayPlan(JDialog frame, String str) {
        super(frame, str);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
    }

    public GUIInvPayPlan(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initOthers();
    }

    public GUIInvPayPlan(Object object, boolean b, String InvNo) {
        this(null, b);
        serchPP(InvNo);
        Act = 0;

    }

    public GUIInvPayPlan(Object object, boolean b, OBJInvPayPlan objpp, ArrayList<OBJPaySchedule> objs) {
        this(null, b);
        setContentPP(objpp);
        this.objs = objs;
        loadTable();
        Act = 1;

    }

    private void doSave() {
        String code = txtInvoNo.getText();
        String name = txtCusCode.getText();
        if (code.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Blank entry not allowed", "Warning !", JOptionPane.OK_OPTION);
        } else {
            String s;
            if (Act == 1) {
                s = "Save";
            } else {
                s = "Update";
            }
            int q = JOptionPane.showConfirmDialog(this, "Are you sure want to " + s + " ?", s + " Pay Term", JOptionPane.YES_NO_OPTION);
            if (q == JOptionPane.YES_OPTION) {
                Calendar c = txtNextPay.getCalendar();
                //c.add(Calendar.MONTH, (Integer.parseInt(txtNoInstal.getText())-1)); Because get next pay date
                obj = new OBJInvPayPlan(
                        txtAgriNo.getText(),
                        txtInvoNo.getText(),
                        txtPayPlanCode.getText(),
                        txtNoInstal.getText(),
                        txtIntRate.getText(),
                        txtDue.getText(),
                        txtTotInt.getText(),
                        "0.00",
                        txtTotPayble.getText(),
                        txtTotPaied.getText(),
                        Locals.setDateFormat(txtAgDate.getDate()),
                        Locals.setDateFormat(c.getTime()),
                        "2",
                        txtInstal.getText(),
                        txtDepCode1.getText(),
                        txtDepCode2.getText(),
                        txtCusCode.getText(),
                        "0.00",
                        "0.00");
                int i;
                try {
                    doSavePaySched();
                    transaction();
                    accountTransaction();
                    paymentsInfo = null;
                    payment = null;
                    itemTransactions = null;

                    i = SERInvPayPlan.save(obj, objs, transaction, payment, paymentsInfo, accountTranses, itemTransactions, Act);
                    if (i == 1) {
                        JOptionPane.showMessageDialog(this, "Saved successful..");
                        try {
                            new PrintPaySched(txtAgriNo.getText()).setVisible(true);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Print error...!");
                            Exp.Handle(ex);
                        }
                        doNew();
                        if (Act == 1) {
                            setMode(DEFAULT_STATUS);
                        } else {
                            setMode(NEW_STATUS);
                            Act = 1;
                        }
                        this.dispose();
                    } else if (i == 3) {
                        JOptionPane.showMessageDialog(this, "Saved error..");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GUIInvPayPlan.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private void transaction() {

        costCode = SERCommen.getDescription(
                "SELECT \n"
                + "			invoiceheader.CostCode \n"
                + "FROM \n"
                + "			invoiceheader \n"
                + "WHERE \n"
                + "			invoiceheader.InvoNo =  '" + txtInvoNo.getText() + "'", "invoiceheader.CostCode");

        loan = SERCommen.getDescription(
                "SELECT \n"
                + "			invoiceheader.loan_no \n"
                + "FROM \n"
                + "			invoiceheader \n"
                + "WHERE \n"
                + "			invoiceheader.InvoNo =  '" + txtInvoNo.getText() + "'", "invoiceheader.loan_no");

        // Transaction Object
        transaction = new OBJTransaction();
        transaction.setTransactionDate(Locals.setDateFormat(txtAgDate.getDate()));
        transaction.setTransactionType(TransactionType.PAYMENT_PLAN);
        transaction.setReferanceNo(txtInvoNo.getText());
        transaction.setDocumentNo(null);
        transaction.setLoan(loan);
        transaction.setCostCode(costCode);
        transaction.setCustomerCode(null);
        transaction.setNote("Payment Plan for Invoice No : " + txtInvoNo.getText());
        transaction.setStatus(TransactionStatus.ACTIVE);

    }

    private boolean accountTransaction() {
        accountTranses = null;
        if (Double.parseDouble(txtTotInt.getText()) > 0) {
            accountTranses = new ArrayList<>();

        // Account Transaction
            // Debit Account Trans
            OBJAccountTrans accountTrans = new OBJAccountTrans();
            accountTrans.setTransactionDate(Locals.setDateFormat(txtAgDate.getDate()));
            accountTrans.setCostCode(costCode);
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(AccountTransactionDescription.DEFAULT_INTEREST_DEBIT + " Invoice No : " + txtInvoNo.getText());
            accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.DEFAULT_INTEREST_DEBIT)).getAccount());
            accountTrans.setCreditAmount("0.00");
            accountTrans.setDebitAmount(txtTotInt.getText());
            accountTrans.setTransactionType(TransactionType.PAYMENT_PLAN);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);

            // Credit Account Trans
            accountTrans = new OBJAccountTrans();
            accountTrans.setTransactionDate(Locals.setDateFormat(txtAgDate.getDate()));
            accountTrans.setCostCode(costCode);
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(AccountTransactionDescription.DEFAULT_INTEREST_INCOME);
            accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.DEFAULT_INTEREST_INCOME)).getAccount());
            accountTrans.setCreditAmount(txtTotInt.getText());
            accountTrans.setDebitAmount("0.00");
            accountTrans.setTransactionType(TransactionType.PAYMENT_PLAN);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }
        return true;
    }

    private ArrayList doSavePaySched() {
        try {
            objs = new ArrayList<>();
            int row = jTable1.getRowCount();
            for (int i = 0; i < row; i++) {

                objse = new OBJPaySchedule(
                        txtAgriNo.getText(),
                        txtNoInstal.getText(),
                        jTable1.getValueAt(i, 1).toString(),
                        jTable1.getValueAt(i, 2).toString(),
                        txtInvoNo.getText());

                objs.add(objse);
            }
        } catch (Exception e) {
            core.Exp.Handle(e);
        }
        return objs;
    }

    private void doNew() {
        preCC = txtAgriNo.getText();
        setID();
        //txtCode.setText("");
        txtInvoNo.setText("");
        txtAgDate.setDate(Calendar.getInstance().getTime());
        txtCusCode.setText("");
        txtCusName.setText("");
        txtDepCode1.setText("");
        txtDepCode2.setText("");
        txtDepName1.setText("");
        txtDepName2.setText("");
        txtDue.setText("0.00");
        txtTotPaied.setText("0.00");
        txtInvoAmount.setText("0.00");
        txtTotPayble.setText("0.00");

        txtPayPlanName.setText("");
        txtPayPlanCode.setText("");
        txtInstal.setText("0");
        txtIntRate.setText("0");
        txtNextPay.setDate((Calendar.getInstance().getTime()));
        txtNoInstal.setText("0");
        txtTotInt.setText("0.00");
        removeTbl();
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
    }

    private void setEditMode() {
        txtPayPlanName.setText("");
        txtPayPlanCode.setText("");
        txtInstal.setText("0");
        txtIntRate.setText("0");
        txtNextPay.setDate((Calendar.getInstance().getTime()));
        txtNoInstal.setText("0");
        txtTotInt.setText("0.00");
        txtTotPayble.setText("");

        txtInvoNo.setEnabled(false);
        txtAgDate.setEnabled(false);
        txtCusCode.setEnabled(false);
        txtDepCode1.setEnabled(false);
        txtDepCode2.setEnabled(false);
        txtDue.setEnabled(false);
        cmddepo1.setEnabled(false);
        cmddepo2.setEnabled(false);
        cmddepo3.setEnabled(false);
        cmddepo4.setEnabled(false);

        removeTbl();
    }

    private void doEdit() {
        int q = JOptionPane.showConfirmDialog(this, "<html>Are you want to create new payment plan ?<br />This will remove excisting payment plan</html>", "Create Pay Term", JOptionPane.YES_NO_OPTION);
        if (q == 0) {
            Act = 1;
            setID();
            setEnableAll(true);
            setMode(DEFAULT_STATUS);
            setEditMode();
            txtInvoNo.setEnabled(false);
            txtNextPay.setDate(Calendar.getInstance().getTime());
        }
    }

    private void doDelete() {
        String code = txtInvoNo.getText();
        SERInvPayPlan.delete(code);
        indexCount = SERSalesRep.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index--;
            getNavi();
        }
    }

    private void doView() {
        setEnableAll(false);
        setMode(NEW_STATUS);
        getNavi();
    }

    private void setMode(int state) {
        switch (state) {
            case DEFAULT_STATUS:
                setEnableAll(true);
                cmdFirst.setEnabled(!true);
                cmdLast.setEnabled(!true);
                cmdNext.setEnabled(!true);
                cmdPrev.setEnabled(!true);
                cmdFind.setEnabled(!true);
                cmdNew.setEnabled(!true);
                cmdEdit.setEnabled(!true);
                cmdDelete.setEnabled(!true);
                cmdView.setEnabled(true);
                cmdSave.setEnabled(true);
                cmdReport.setEnabled(!true);

                break;
            case NEW_STATUS:
                setEnableAll(!true);
                cmdFirst.setEnabled(true);
                cmdLast.setEnabled(true);
                cmdNext.setEnabled(true);
                cmdPrev.setEnabled(true);
                cmdFind.setEnabled(true);
                cmdNew.setEnabled(true);
                cmdEdit.setEnabled(true);
                cmdDelete.setEnabled(true);
                cmdView.setEnabled(!true);
                cmdSave.setEnabled(!true);
                cmdReport.setEnabled(true);
                break;
        }
    }

    private void setEnableAll(boolean state) {
        //txtCode.setEnabled(state);

        txtInvoNo.setEnabled(state);
        txtAgDate.setEnabled(state);
        txtCusCode.setEnabled(state);
        txtDepCode1.setEnabled(state);
        txtDepCode2.setEnabled(state);
        txtDue.setEnabled(state);

        txtInstal.setEnabled(state);
        txtIntRate.setEnabled(state);
        txtNextPay.setEnabled(state);
        txtNoInstal.setEnabled(state);
        txtPayPlanCode.setEnabled(state);
        txtTotInt.setEnabled(state);

        cmdPayplan.setEnabled(state);
        cmddepo1.setEnabled(state);
        cmddepo2.setEnabled(state);
        cmddepo3.setEnabled(state);
        cmddepo4.setEnabled(state);
//        cmdCustomer.setEnabled(state);
//        cmdInvoice.setEnabled(state);
    }

    private void getNavi() {
        obj = SERInvPayPlan.getNavi(Index);
        setContent(obj);
    }

    private void doFind() {
        new GUIFindWindow(null, true, "Credit", "Creditid", "InvoNo").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void serchPP(String code) {
        obj = SERInvPayPlan.serchPP(code);
        setContentPP(obj);
        loadSchedule(obj.getCreditId());
    }

    private void serch(String code) {
        obj = SERInvPayPlan.serch(code);
        setContent(obj);
//        txtCode.setText(obj.getCode());
//        txtName.setText(obj.getName());
//        txtAreaCode.setText(obj.getArea());
//        txtArea.setText(SERSalesRep.setName(obj.getArea(), "Area", "AreaCode"));
//        txtAddress1.setText(obj.getAdd1());
//        txtAddress2.setText(obj.getAdd2());
//        txtAddress3.setText(obj.getAdd3());
//        txtPobox.setText(obj.getPbox());
//        txtTel.setText(obj.getTellOff());
//        txtFax.setText(obj.getFax());
//        txtmobi.setText(obj.getMobi());
//        txtEmail.setText(obj.getEmail());
//        txtTargt.setText(obj.getTarget());
    }

    private String getAmount() {
        double d = Double.parseDouble(txtDue.getText());
        double d1 = Double.parseDouble(txtTotPaied.getText());
        d = d + d1;
        return Locals.currencyFormat(d);
    }

    ;
    private void setContent(OBJInvPayPlan obj) {
        if (obj != null) {
            txtInvoNo.setText(obj.getInvoNo());
            txtAgriNo.setText(obj.getCreditId());
            txtAgDate.setDate(Locals.toDate(obj.getSDate()));
            txtCusCode.setText(SERCommen.getDescription("invoiceheader", txtInvoNo.getText(), "InvoNo", "CustCode"));
            txtCusName.setText(SERCommen.getDescription("customer", txtCusCode.getText(), "CustCode", "CustName"));
            txtTotPaied.setText(Locals.sCurrencyFormat(obj.getTotPay()));

            txtDue.setText(Locals.sCurrencyFormat(obj.getTotPayble()));
            txtInvoAmount.setText(getAmount());

            // getDue();
            txtPayPlanCode.setText(obj.getPayPlan());
            txtAPP.setText(obj.getPayPlan());
            txtPayPlanName.setText(SERCommen.getDescription("payplan", txtPayPlanCode.getText(), "PayPlanNo", "Description"));
            txtNoInstal.setText(obj.getNoInstal());
            txtIntRate.setText(obj.getInterestRate());
            txtTotInt.setText(Locals.sCurrencyFormat(obj.getTotInterest()));
            /*txtTotPayble.setText*/ getTotPayble();
            /**
             * txtInstal.setText*
             */
            getInstal();
            txtNextPay.setDate(Locals.toDate(obj.getEDate()));
            txtDepCode1.setText(obj.getDep1());
            txtDepName1.setText(SERCommen.getDescription("customer", txtDepCode1.getText(), "CustCode", "CustName"));
            txtDepCode2.setText(obj.getDep2());
            txtDepName2.setText(SERCommen.getDescription("customer", txtDepCode2.getText(), "CustCode", "CustName"));
            loadSchedule(obj.getCreditId());
        }
    }

    private void setContentPP(OBJInvPayPlan obj) {
        if (obj != null) {
            txtInvoNo.setText(obj.getInvoNo());
            txtAgriNo.setText(obj.getCreditId());
            txtAgDate.setDate(Locals.toDate(obj.getSDate()));
            txtCusCode.setText(SERCommen.getDescription("invoiceheader", txtInvoNo.getText(), "InvoNo", "CustCode"));
            txtCusName.setText(SERCommen.getDescription("customer", txtCusCode.getText(), "CustCode", "CustName"));
            txtTotPaied.setText(Locals.sCurrencyFormat(obj.getTotPay()));

            txtDue.setText(Locals.sCurrencyFormat(obj.getTotPayble()));
           // txtInvoAmount.setText(getAmount());

            // getDue();
            txtPayPlanCode.setText(obj.getPayPlan());
            txtAPP.setText(obj.getPayPlan());
            txtPayPlanName.setText(SERCommen.getDescription("payplan", txtPayPlanCode.getText(), "PayPlanNo", "Description"));
            txtNoInstal.setText(obj.getNoInstal());
            txtIntRate.setText(obj.getInterestRate());
            txtTotInt.setText(Locals.sCurrencyFormat(obj.getTotInterest()));
            double interestAmount = Double.parseDouble(obj.getTotInterest());
            double lateChargeAmount = Double.parseDouble(obj.getLateCharge());
            double loanAmount = Double.parseDouble(obj.getInvoCredit());
            double spDescountAmount = Double.parseDouble(obj.getSPDisc());
            double paidAmount = Double.parseDouble(obj.getTotPay());

//            double initPayAmount = Double.parseDouble(SERCommen.getDescription("invoiceHeader", txtInvoNo.getText(), "invoNo", "payAmount"));
            double totalPayble = loanAmount + interestAmount + lateChargeAmount - (spDescountAmount);
            double invoAmount = loanAmount + interestAmount + lateChargeAmount + paidAmount - spDescountAmount;
            txtInvoAmount.setText(invoAmount + "");
            txtDue.setText(totalPayble + "");
            /*txtTotPayble.setText*/ //getTotPayble();
            /**
             * txtInstal.setText*
             */
            //getInstal();
            txtNextPay.setDate(Locals.toDate(obj.getEDate()));
            txtDepCode1.setText(obj.getDep1());
            txtDepName1.setText(SERCommen.getDescription("customer", txtDepCode1.getText(), "CustCode", "CustName"));
            txtDepCode2.setText(obj.getDep2());
            txtDepName2.setText(SERCommen.getDescription("customer", txtDepCode2.getText(), "CustCode", "CustName"));
        }
    }

    private void loadSchedule(String cid) {

        objs = SERInvPayPlan.loadSchedule(cid);
        loadTable();
    }

    private void loadTable() {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        int i = 1;
        removeTbl();
        for (OBJPaySchedule obja : objs) {
            dt.addRow(new Object[]{i, obja.getDate(), obja.getAmount(), obja.getTotAmount()});
            i++;
        }

    }

    private void getTotPayble() {
        String TotPayble;
        double due = Double.parseDouble(txtDue.getText());
        double toyInt = Double.parseDouble(txtTotInt.getText());
        TotPayble = (due + toyInt) + "";
        txtTotPayble.setText(Locals.sCurrencyFormat(TotPayble));
    }

    /*  private void getDue() {
     String due = "0.00";
     double invoAm = Double.parseDouble(txtInvoAmount.getText());
     double totPaid = Double.parseDouble(txtTotPaied.getText());
     due = (invoAm - totPaid) + "";
     txtDue.setText(Locals.sCurrencyFormat(due));
     }*/
    private void getInstal() {
        String due;
        double TotPayble = Double.parseDouble(txtTotPayble.getText());
        double instal = Double.parseDouble(txtNoInstal.getText());
        due = (TotPayble / instal) + "";
        txtInstal.setText(Locals.sCurrencyFormat(due));
    }

    private void setID() {
        txtAgriNo.setText(SERInvPayPlan.getID());
    }

    private void getPayPlan() {
        new GUIFindWindow(null, true, "PayPlan", "PayPlanNo", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtPayPlanCode.setText(code);
        txtPayPlanName.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void loadDepo1() {
        new GUIFindWindow(null, true, "Deponent", "DepCode", "DepName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtDepCode1.setText(code);
        txtDepName1.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void loadDepo2() {
        new GUIFindWindow(null, true, "Deponent", "DepCode", "DepName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtDepCode2.setText(code);
        txtDepName2.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void getInvoNo() {
        new GUIFindWindow(null, true, "invoiceheader", "InvoNo", "CustCode").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtInvoNo.setText(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void calOthers() {
        if (!txtPayPlanCode.getText().equals("0")) {
            obj = SERInvPayPlan.getPPDetails(txtPayPlanCode.getText());
            txtNoInstal.setText(obj.getNoInstal());
            txtIntRate.setText(obj.getInterestRate());

        }
        double totDue = Double.parseDouble(txtDue.getText());
        double ir = Double.parseDouble(txtIntRate.getText());
        double totint = (totDue * ir) / (100);

        txtTotInt.setText(Locals.currencyFormat(totint));

        getTotPayble();
        getInstal();
        steSchedule();
    }

    private void setDate() {
        Calendar c = Calendar.getInstance();
        txtAgDate.setDate(c.getTime());
        txtNextPay.setDate(c.getTime());
    }

    private void steSchedule() {
        if (txtNextPay.getCalendar() != null) {
            objs = SERInvPayPlan.getSchedule(txtNextPay.getCalendar(), txtNoInstal.getText(), txtInstal.getText());
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            int i = 1;
            removeTbl();
            for (OBJPaySchedule obja : objs) {
                dt.addRow(new Object[]{i, obja.getDate(), obja.getAmount(), obja.getTotAmount()});
                i++;
            }
        }
    }

    private void removeTbl() {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        int rc = jTable1.getRowCount();
        for (int j = 0; j < rc; j++) {
            dt.removeRow(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initOthers() {
        setMode(DEFAULT_STATUS);
        setID();
        setDate();
        cmdFirst.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFirstActionPerformed(evt);
            }
        });
        cmdLast.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLastActionPerformed(evt);
            }
        });
        cmdPrev.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPrevActionPerformed(evt);
            }
        });
        cmdNext.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNextActionPerformed(evt);
            }
        });
        cmdFind.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFindActionPerformed(evt);
            }
        });
        cmdNew.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        cmdView.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdViewActionPerformed(evt);
            }
        });

        cmdReport.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReportActionPerformed(evt);
            }
        });
        cmdExit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExitActionPerformed(evt);
            }
        });

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane6 = new javax.swing.JLayeredPane();
        txtDepName2 = new javax.swing.JTextField();
        txtDepCode2 = new javax.swing.JTextField();
        cmddepo2 = new javax.swing.JButton();
        cmddepo3 = new javax.swing.JButton();
        txtInvoNo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtInvoAmount = new javax.swing.JFormattedTextField();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        txtDepName1 = new javax.swing.JTextField();
        txtDepCode1 = new javax.swing.JTextField();
        cmddepo1 = new javax.swing.JButton();
        cmddepo4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTotPaied = new javax.swing.JFormattedTextField();
        txtDue = new javax.swing.JFormattedTextField();
        txtTotInt = new javax.swing.JFormattedTextField();
        txtTotPayble = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        txtInstal = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        txtIntRate = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        txtPayPlanName = new javax.swing.JTextField();
        txtPayPlanCode = new javax.swing.JTextField();
        cmdPayplan = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtNextPay = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        cmdInvoice = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        txtCusName = new javax.swing.JTextField();
        txtCusCode = new javax.swing.JTextField();
        cmdCustomer = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNoInstal = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        cmdFirst = new javax.swing.JButton();
        cmdPrev = new javax.swing.JButton();
        cmdNext = new javax.swing.JButton();
        cmdLast = new javax.swing.JButton();
        cmdFind = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cmdNew = new javax.swing.JButton();
        cmdEdit = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();
        cmdView = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        cmdSave = new javax.swing.JButton();
        cmdReport = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        cmdExit = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtAgriNo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtAPP = new javax.swing.JLabel();
        txtAgDate = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Payment Plane");
        setAlwaysOnTop(true);
        setResizable(false);

        txtDepName2.setEditable(false);
        txtDepName2.setBackground(new java.awt.Color(255, 255, 241));
        txtDepName2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane6.add(txtDepName2);
        txtDepName2.setBounds(80, 0, 240, 18);

        txtDepCode2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDepCode2.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtDepCode2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepCode2ActionPerformed(evt);
            }
        });
        txtDepCode2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtDepCode2InputMethodTextChanged(evt);
            }
        });
        txtDepCode2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDepCode2PropertyChange(evt);
            }
        });
        jLayeredPane6.add(txtDepCode2);
        txtDepCode2.setBounds(0, 0, 60, 18);

        cmddepo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/new1.png"))); // NOI18N
        cmddepo2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmddepo2.setContentAreaFilled(false);
        jLayeredPane6.add(cmddepo2);
        cmddepo2.setBounds(320, 0, 40, 20);

        cmddepo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmddepo3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmddepo3.setContentAreaFilled(false);
        cmddepo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmddepo3ActionPerformed(evt);
            }
        });
        jLayeredPane6.add(cmddepo3);
        cmddepo3.setBounds(60, 0, 20, 20);

        txtInvoNo.setEditable(false);
        txtInvoNo.setBackground(new java.awt.Color(255, 255, 222));
        txtInvoNo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtInvoNo.setForeground(new java.awt.Color(198, 0, 0));
        txtInvoNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel19.setText("Deponent 2");

        jLabel1.setText("Invo. No.");

        jLabel18.setText("Deponent 1");

        txtInvoAmount.setBackground(new java.awt.Color(249, 249, 229));
        txtInvoAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtInvoAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtInvoAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        txtDepName1.setEditable(false);
        txtDepName1.setBackground(new java.awt.Color(255, 255, 241));
        txtDepName1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDepName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepName1ActionPerformed(evt);
            }
        });
        jLayeredPane5.add(txtDepName1);
        txtDepName1.setBounds(80, 0, 240, 18);

        txtDepCode1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDepCode1.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtDepCode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepCode1ActionPerformed(evt);
            }
        });
        txtDepCode1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtDepCode1InputMethodTextChanged(evt);
            }
        });
        txtDepCode1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDepCode1PropertyChange(evt);
            }
        });
        jLayeredPane5.add(txtDepCode1);
        txtDepCode1.setBounds(0, 0, 60, 18);

        cmddepo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/new1.png"))); // NOI18N
        cmddepo1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmddepo1.setContentAreaFilled(false);
        cmddepo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmddepo1ActionPerformed(evt);
            }
        });
        jLayeredPane5.add(cmddepo1);
        cmddepo1.setBounds(320, 0, 40, 20);

        cmddepo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmddepo4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmddepo4.setContentAreaFilled(false);
        cmddepo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmddepo4ActionPerformed(evt);
            }
        });
        jLayeredPane5.add(cmddepo4);
        cmddepo4.setBounds(60, 0, 20, 20);

        jLabel5.setText("Due Amount");

        jLabel3.setText("Tot. Paied ");

        jLabel2.setText("Loan + Interest");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("-");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("=");

        txtTotPaied.setBackground(new java.awt.Color(249, 249, 229));
        txtTotPaied.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotPaied.setForeground(new java.awt.Color(153, 0, 0));
        txtTotPaied.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        txtDue.setBackground(new java.awt.Color(249, 249, 229));
        txtDue.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDue.setForeground(new java.awt.Color(153, 0, 0));
        txtDue.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        txtTotInt.setBackground(new java.awt.Color(249, 249, 229));
        txtTotInt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotInt.setForeground(new java.awt.Color(153, 0, 0));
        txtTotInt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        txtTotPayble.setBackground(new java.awt.Color(249, 249, 229));
        txtTotPayble.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotPayble.setForeground(new java.awt.Color(153, 0, 0));
        txtTotPayble.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jLabel14.setText("Tot. Payble (plan)");

        txtInstal.setBackground(new java.awt.Color(249, 249, 229));
        txtInstal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtInstal.setForeground(new java.awt.Color(153, 0, 0));
        txtInstal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jLabel15.setText("Inst. Value");

        txtIntRate.setBackground(new java.awt.Color(249, 249, 229));
        txtIntRate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIntRate.setForeground(new java.awt.Color(153, 0, 0));
        txtIntRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIntRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIntRateActionPerformed(evt);
            }
        });

        jLabel16.setText("No instalment");

        txtPayPlanName.setBackground(new java.awt.Color(255, 255, 241));
        txtPayPlanName.setEditable(false);
        txtPayPlanName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtPayPlanName);
        txtPayPlanName.setBounds(85, 0, 130, 18);

        txtPayPlanCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPayPlanCode.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtPayPlanCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPayPlanCodeActionPerformed(evt);
            }
        });
        txtPayPlanCode.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtPayPlanCodeInputMethodTextChanged(evt);
            }
        });
        txtPayPlanCode.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtPayPlanCodePropertyChange(evt);
            }
        });
        jLayeredPane4.add(txtPayPlanCode);
        txtPayPlanCode.setBounds(0, 0, 60, 18);

        cmdPayplan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdPayplan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdPayplan.setContentAreaFilled(false);
        cmdPayplan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPayplanActionPerformed(evt);
            }
        });
        jLayeredPane4.add(cmdPayplan);
        cmdPayplan.setBounds(60, 0, 20, 20);

        jLabel17.setText("Next Pay");

        txtNextPay.setDateFormatString("yyyy-MM-dd");

        jTable1.setBackground(new java.awt.Color(249, 249, 229));
        jTable1.setForeground(new java.awt.Color(153, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "Date", "Amount", "Tot. Pay"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(20);
        jTable1.setSelectionBackground(new java.awt.Color(255, 204, 0));
        jTable1.setSelectionForeground(new java.awt.Color(51, 0, 0));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        cmdInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdInvoice.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdInvoice.setContentAreaFilled(false);
        cmdInvoice.setEnabled(false);
        cmdInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdInvoiceActionPerformed(evt);
            }
        });

        jLabel6.setText("Payment Plan");

        txtCusName.setBackground(new java.awt.Color(255, 255, 241));
        txtCusName.setEditable(false);
        txtCusName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtCusName);
        txtCusName.setBounds(115, 0, 250, 18);

        txtCusCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCusCode.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtCusCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusCodeActionPerformed(evt);
            }
        });
        txtCusCode.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtCusCodeInputMethodTextChanged(evt);
            }
        });
        txtCusCode.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCusCodePropertyChange(evt);
            }
        });
        jLayeredPane3.add(txtCusCode);
        txtCusCode.setBounds(0, 0, 90, 18);

        cmdCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdCustomer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdCustomer.setContentAreaFilled(false);
        cmdCustomer.setEnabled(false);
        jLayeredPane3.add(cmdCustomer);
        cmdCustomer.setBounds(93, 0, 20, 20);

        jLabel9.setText("Int. Rate (%)");

        jLabel4.setText("Customer");

        txtNoInstal.setBackground(new java.awt.Color(249, 249, 229));
        txtNoInstal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNoInstal.setForeground(new java.awt.Color(153, 0, 0));
        txtNoInstal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtNoInstal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoInstalActionPerformed(evt);
            }
        });

        jLabel10.setText("Tot. Interest");

        jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        cmdFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point_090.png"))); // NOI18N
        cmdFirst.setToolTipText("First (Up Key)");
        cmdFirst.setAlignmentX(0.5F);
        cmdFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdFirst.setFocusable(false);
        cmdFirst.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdFirst.setIconTextGap(5);
        cmdFirst.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdFirst);

        cmdPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point_180.png"))); // NOI18N
        cmdPrev.setToolTipText("Previous (Left Key)");
        cmdPrev.setAlignmentX(0.5F);
        cmdPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdPrev.setFocusable(false);
        cmdPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdPrev.setIconTextGap(5);
        cmdPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdPrev);

        cmdNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point.png"))); // NOI18N
        cmdNext.setToolTipText("Next (Right Key)");
        cmdNext.setAlignmentX(0.5F);
        cmdNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdNext.setFocusable(false);
        cmdNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdNext.setIconTextGap(5);
        cmdNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdNext);

        cmdLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point_270.png"))); // NOI18N
        cmdLast.setToolTipText("Last (Down Key)");
        cmdLast.setAlignmentX(0.5F);
        cmdLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdLast.setFocusable(false);
        cmdLast.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdLast.setIconTextGap(5);
        cmdLast.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdLast);

        cmdFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/find.png"))); // NOI18N
        cmdFind.setToolTipText("Find (F4)");
        cmdFind.setAlignmentX(0.5F);
        cmdFind.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdFind.setFocusable(false);
        cmdFind.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdFind.setIconTextGap(5);
        cmdFind.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdFind);
        jToolBar1.add(jSeparator1);

        cmdNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/new.png"))); // NOI18N
        cmdNew.setToolTipText("Add New (F5)");
        cmdNew.setAlignmentX(0.5F);
        cmdNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdNew.setFocusable(false);
        cmdNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdNew.setIconTextGap(5);
        cmdNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdNew);

        cmdEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/document_prepare.png"))); // NOI18N
        cmdEdit.setToolTipText("Modify (F6)");
        cmdEdit.setAlignmentX(0.5F);
        cmdEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdEdit.setFocusable(false);
        cmdEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdEdit.setIconTextGap(5);
        cmdEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdEdit);

        cmdDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/delete.png"))); // NOI18N
        cmdDelete.setToolTipText("Delete (F7)");
        cmdDelete.setAlignmentX(0.5F);
        cmdDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdDelete.setFocusable(false);
        cmdDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdDelete.setIconTextGap(5);
        cmdDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdDelete);

        cmdView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/magnifier.png"))); // NOI18N
        cmdView.setToolTipText("View (F8)");
        cmdView.setAlignmentX(0.5F);
        cmdView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdView.setFocusable(false);
        cmdView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdView.setIconTextGap(5);
        cmdView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdView);
        jToolBar1.add(jSeparator5);

        cmdSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/save_as.png"))); // NOI18N
        cmdSave.setToolTipText("Save (F10)");
        cmdSave.setAlignmentX(0.5F);
        cmdSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdSave.setFocusable(false);
        cmdSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdSave.setIconTextGap(5);
        cmdSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdSave);

        cmdReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/script.png"))); // NOI18N
        cmdReport.setToolTipText("Report (F11)");
        cmdReport.setAlignmentX(0.5F);
        cmdReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdReport.setFocusable(false);
        cmdReport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdReport.setIconTextGap(5);
        cmdReport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdReport);
        jToolBar1.add(jSeparator3);

        cmdExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/door_in.png"))); // NOI18N
        cmdExit.setToolTipText("Exit (F12)");
        cmdExit.setAlignmentX(0.5F);
        cmdExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdExit.setFocusable(false);
        cmdExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdExit.setIconTextGap(5);
        cmdExit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdExit);

        jButton1.setText("jButton1");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        txtAgriNo.setBackground(new java.awt.Color(255, 255, 222));
        txtAgriNo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtAgriNo.setForeground(new java.awt.Color(198, 0, 0));
        txtAgriNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel11.setText("Agri. No.");

        jLabel12.setText("Available");

        txtAgDate.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInvoNo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAgriNo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAgDate, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtInvoAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTotPaied, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtDue, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLayeredPane6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNextPay, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLayeredPane5)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTotInt, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTotPayble, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtInstal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLayeredPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(txtNoInstal, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtIntRate, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAPP, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtInvoNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtAgriNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))
                            .addComponent(cmdInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAgDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtInvoAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotPaied, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel16))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtNoInstal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9)
                                            .addComponent(txtIntRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLabel12)
                            .addComponent(txtAPP, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txtTotPayble, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtTotInt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(txtInstal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17))
                            .addComponent(txtNextPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLayeredPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(751, 368));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDepCode2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepCode2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepCode2ActionPerformed

    private void txtDepCode2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtDepCode2InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepCode2InputMethodTextChanged

    private void txtDepCode2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDepCode2PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepCode2PropertyChange

    private void txtDepCode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepCode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepCode1ActionPerformed

    private void txtDepCode1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtDepCode1InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepCode1InputMethodTextChanged

    private void txtDepCode1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDepCode1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepCode1PropertyChange

    private void txtPayPlanCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPayPlanCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPayPlanCodeActionPerformed

    private void txtPayPlanCodeInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtPayPlanCodeInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPayPlanCodeInputMethodTextChanged

    private void txtPayPlanCodePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtPayPlanCodePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPayPlanCodePropertyChange

    private void txtCusCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusCodeActionPerformed

   }//GEN-LAST:event_txtCusCodeActionPerformed

    private void txtCusCodeInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCusCodeInputMethodTextChanged

   }//GEN-LAST:event_txtCusCodeInputMethodTextChanged

    private void txtCusCodePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCusCodePropertyChange

   }//GEN-LAST:event_txtCusCodePropertyChange

    private void cmdPayplanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPayplanActionPerformed
        getPayPlan();
        if (!txtPayPlanCode.getText().equals("") && txtPayPlanCode.getText() != null) {
            calOthers();
        }
    }//GEN-LAST:event_cmdPayplanActionPerformed

    private void txtNoInstalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoInstalActionPerformed
        txtPayPlanCode.setText("0");
        txtPayPlanName.setText("Variable");
        calOthers();
    }//GEN-LAST:event_txtNoInstalActionPerformed

    private void txtIntRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIntRateActionPerformed
        txtPayPlanCode.setText("0");
        txtPayPlanName.setText("Variable");
        calOthers();
    }//GEN-LAST:event_txtIntRateActionPerformed

    private void cmdInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdInvoiceActionPerformed
        getInvoNo();
        serchPP(txtInvoNo.getText());
        setEnableAll(false);
        setMode(NEW_STATUS);
    }//GEN-LAST:event_cmdInvoiceActionPerformed

    private void txtDepName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepName1ActionPerformed

    private void cmddepo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmddepo4ActionPerformed
        loadDepo1();
    }//GEN-LAST:event_cmddepo4ActionPerformed

    private void cmddepo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmddepo3ActionPerformed
        loadDepo2();
    }//GEN-LAST:event_cmddepo3ActionPerformed

    private void cmddepo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmddepo1ActionPerformed
        mainApp.MainFrame.f = 3;
    }//GEN-LAST:event_cmddepo1ActionPerformed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        doSave();
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed
    private void cmdExitActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void cmdAreaActionPerformed(java.awt.event.ActionEvent evt) {
        // getArea();
    }

    private void cmdFirstActionPerformed(java.awt.event.ActionEvent evt) {
        Index = 0;
        getNavi();
    }

    private void cmdPrevActionPerformed(java.awt.event.ActionEvent evt) {
        Index--;
        if (Index >= 0) {
            getNavi();
        } else {
            Index = 0;
        }
    }

    private void cmdNextActionPerformed(java.awt.event.ActionEvent evt) {
        Index++;
        indexCount = SERInvPayPlan.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index = indexCount - 1;
        }
    }

    private void cmdLastActionPerformed(java.awt.event.ActionEvent evt) {
        Index = SERInvPayPlan.getIndex();
        getNavi();
    }

    private void cmdFindActionPerformed(java.awt.event.ActionEvent evt) {
        doFind();
    }

    private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {
        Act = 1;
        doNew();
    }

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {
        Act = 1;
        doEdit();
    }

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int q = JOptionPane.showConfirmDialog(null, "Are you sure want to delete ?", "delete Area", JOptionPane.YES_NO_OPTION);

        if (q == JOptionPane.YES_OPTION) {
            doDelete();
        }
    }

    private void cmdViewActionPerformed(java.awt.event.ActionEvent evt) {

        Index = 1;
        doView();
    }

    private void cmdReportActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            new PrintPaySched(txtAgriNo.getText()).setVisible(true);
        } catch (Exception ex) {
            Exp.Handle(ex);
        }
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCustomer;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdExit;
    private javax.swing.JButton cmdFind;
    private javax.swing.JButton cmdFirst;
    private javax.swing.JButton cmdInvoice;
    private javax.swing.JButton cmdLast;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdNext;
    private javax.swing.JButton cmdPayplan;
    private javax.swing.JButton cmdPrev;
    private javax.swing.JButton cmdReport;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdView;
    private javax.swing.JButton cmddepo1;
    private javax.swing.JButton cmddepo2;
    private javax.swing.JButton cmddepo3;
    private javax.swing.JButton cmddepo4;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel txtAPP;
    private com.toedter.calendar.JDateChooser txtAgDate;
    private javax.swing.JTextField txtAgriNo;
    private javax.swing.JTextField txtCusCode;
    private javax.swing.JTextField txtCusName;
    private javax.swing.JTextField txtDepCode1;
    private javax.swing.JTextField txtDepCode2;
    private javax.swing.JTextField txtDepName1;
    private javax.swing.JTextField txtDepName2;
    private javax.swing.JFormattedTextField txtDue;
    private javax.swing.JFormattedTextField txtInstal;
    private javax.swing.JFormattedTextField txtIntRate;
    private javax.swing.JFormattedTextField txtInvoAmount;
    private javax.swing.JTextField txtInvoNo;
    private com.toedter.calendar.JDateChooser txtNextPay;
    private javax.swing.JFormattedTextField txtNoInstal;
    private javax.swing.JTextField txtPayPlanCode;
    private javax.swing.JTextField txtPayPlanName;
    private javax.swing.JFormattedTextField txtTotInt;
    private javax.swing.JFormattedTextField txtTotPaied;
    private javax.swing.JFormattedTextField txtTotPayble;
    // End of variables declaration//GEN-END:variables
    private OBJInvPayPlan obj;
    private ArrayList<OBJPaySchedule> objs;
    private OBJPaySchedule objse;
    private String costCode;
    private String loan;

    //Transaction Object
    private OBJTransaction transaction;
    private ArrayList<OBJTransactionHistory> historys;
    //Account Transaction object
    private ArrayList<OBJAccountTrans> accountTranses;
    //Payment Object
    private OBJPayment payment;
    private ArrayList<OBJPaymentInfo> paymentsInfo;

    // Item Trans
    private OBJItemTransaction itemTransaction;
    private ArrayList<OBJItemTransaction> itemTransactions;
    
    //status
    private static final int DEFAULT_STATUS = 0;
    private static final int NEW_STATUS = 1;
    private int Act = 1;
//    private int pp = 0;
    private int Index = 0;
    private int indexCount = 0;
    private String preCC = "";
}
