/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIAgainstInvoice.java
 *
 * Created on Jul 19, 2013, 4:59:24 PM
 */
package system.accounts.transaction.paymentvoucher.againstinvoice;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.SERCommen;
import mainApp.findwindow.GUIFindWindow;
import system.accounts.transaction.cheque.GUICheque;
import system.accounts.transaction.cheque.OBJCheque;
import core.system_transaction.payment.multy_payment.MultiPayOption;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.payment.PaymentSetting;
import core.system_transaction.payment.PaymentSettingObject;
import core.system_transaction.payment.multy_payment.VoucherType;
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.transaction.OBJTransactionHistory;
import core.system_transaction.transaction.TransactionStatus;
import system.masters.customergroup.SERCustomerGroup;

/**
 *
 * @author dell
 */
public class GUIPayAgainstInvoice extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUIAgainstInvoice
     */
    public GUIPayAgainstInvoice() {
        initComponents();
        initOthers();
    }

    private void doSave() {
        String code = txtVouCode.getText();
        String name = txtName.getText();

        if (code.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Blank entry not allowed", "Warning !", JOptionPane.OK_OPTION);
        } else {
            String s;
            if (Act == 1) {
                s = "Save";
            } else {
                s = "Update";
            }

            int q = JOptionPane.showConfirmDialog(null, "Are you sure want to " + s + " ?", s + " Customer Group", JOptionPane.YES_NO_OPTION);

            if (q == JOptionPane.YES_OPTION) {

                Date nextPayment = txtJVDate.getDate();
                if (!jCheckBox2.isSelected()) {
                    if (txtNextPay.getDate() == null) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(nextPayment);
                        c.add(Calendar.MONTH, 1);
                    } else {
                        nextPayment = txtNextPay.getDate();
                    }
                }

                objin = new OBJAgInvo(
                        txtCid.getText(),
                        txtinvo.getText(),
                        txtinsNo.getText(),
                        Locals.setDateFormat(nextPayment),
                        txtpay.getText(),
                        txtdisc.getText(),
                        txtbal.getText(),
                        txtVouCode.getText(),
                        Locals.setDateFormat(txtJVDate.getDate()),
                        TransactionType.VOUCHER,
                        txtCustAccCode.getText(),
                        txtNarration.getText(),
                        txtCostCode.getText(),
                        txtPayTermCode.getText(),
                        txtAccountant.getText(),
                        txtApproved.getText(),
                        TransactionType.VOUCHER);

                // SERInvPayPlan.updateSchedule(objin);
                objIP = new OBJInstallPay(
                        txtCid.getText(),
                        "",
                        "",
                        "",
                        core.Locals.setDateFormat(txtJVDate.getDate()),
                        "2",
                        txtinvo.getText(),
                        txtinsNo.getText(),
                        txtLatepay.getText(),
                        txtTotInvoBalance.getText(),
                        txtpay.getText(),
                        txtbal.getText(),
                        txtdisc.getText());

                /**
                 * ***********************************************
                 */
                if (!txtPayTermCode.getText().equals("INVO")) {
                    paymentsInfo = new ArrayList<>();
                    getPayment();
                }
                accountTransaction();
                transaction();
                itemTransactions = null;
                /**
                 * ***********************************************
                 */
                if (payment != null || !txtPayTermCode.getText().equals("INVO")) {
                    boolean b = SERVouch.doSave(objin, objIP, paymentsInfo, cheques, transaction, payment, paymentsInfo, accountTranses, itemTransactions, Act);
//                SERInvoList.UpdateIP(objIP);  
                    if (b) {
                        cheques = null;
                        transaction = null;
                        payment = null;
                        paymentsInfo = null;
                        accountTranses = null;
                        itemTransactions = null;

                        MultiPayOption.infos = null;
                        MultiPayOption.cheques = null;
                        MultiPayOption.cheque = null;

                        if (jCheckBox1.isSelected()) {
                            try {
                                new system.accounts.transaction.paymentvoucher.againstinvoice.PrintVou(txtCid.getText(), txtinsNo.getText(), txtVouCode.getText(), "P").setVisible(true);
                            } catch (Exception ex) {
                                Exp.Handle(ex);
                            }
                        }
                        doNew();
                        if (Act == 1) {
                            setMode(DEFAULT_STATUS);
                        } else {
                            setMode(NEW_STATUS);
                            Act = 1;
                        }
                    }
                }
            }
        }
    }

    private void transaction() {
        // Transaction Object
        transaction = new OBJTransaction();
        transaction.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
        transaction.setTransactionType(TransactionType.VOUCHER);
        transaction.setReferanceNo(txtVouCode.getText());
        transaction.setDocumentNo(txtDocumentNo.getText());
        transaction.setLoan("");
        transaction.setCostCode(txtCostCode.getText());
        transaction.setCustomerCode(txtCustAccCode.getText());
        transaction.setNote(txtNarration.getText());
        transaction.setStatus(TransactionStatus.ACTIVE);

    }

    private boolean accountTransaction() {
        accountTranses = new ArrayList<>();

        // Account Transaction
        // Debit Account Trans
        OBJAccountTrans accountTrans = new OBJAccountTrans();
        accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
        accountTrans.setCostCode(txtCostCode.getText());
        accountTrans.setAccountSetting(null);
        accountTrans.setDescription(AccountTransactionDescription.VOUCHER_AMOUNT + " Invoice No : " + jTable1.getValueAt(jTable1.getSelectedRow(), 1));
        accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.VOUCHER_AMOUNT_DEBIT)).getAccount());
        accountTrans.setCreditAmount("0.00");
        accountTrans.setDebitAmount(txtpay.getText());
        accountTrans.setTransactionType(TransactionType.VOUCHER);
        accountTrans.setType("AUTO");
        accountTrans.setStatus(AccountTransStatus.ACTIVE);
        accountTranses.add(accountTrans);

        // Credit Account Trans
        for (OBJPaymentInfo info : paymentsInfo) {
            accountTrans = new OBJAccountTrans();
            accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
            accountTrans.setCostCode(txtCostCode.getText());
            accountTrans.setAccountSetting(null);

            switch (info.getPaymentSetting()) {
                case PaymentSetting.VOUCHER_CASH:
                    accountTrans.setDescription(AccountTransactionDescription.VOUCHER_CASH_PAYMENT);
                    accountTrans.setAccount(((PaymentSettingObject) SystemSetting.getPaymentSeting(PaymentSetting.VOUCHER_CASH)).getAccount());
                    accountTrans.setCreditAmount(info.getAmount());
                    accountTrans.setDebitAmount("0.00");
                    break;
                case PaymentSetting.VOUCHER_CHEQUE:
                    accountTrans.setDescription(AccountTransactionDescription.VOUCHER_CHEQUE_PAYMENT);
                    accountTrans.setAccount(((PaymentSettingObject) SystemSetting.getPaymentSeting(PaymentSetting.VOUCHER_CHEQUE)).getAccount());
                    accountTrans.setCreditAmount(info.getAmount());
                    accountTrans.setDebitAmount("0.00");
                    break;
            }

            accountTrans.setTransactionType(TransactionType.VOUCHER);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }

        return true;
    }

    private void getPayment() {

        new MultiPayOption(null, true, txtpay.getText(), paymentsInfo, VoucherType.VOUCHER, cheques).setVisible(true);
// Payment Information
        paymentsInfo = MultiPayOption.infos;
        MultiPayOption.infos = null;
        ArrayList<OBJCheque> cheques = MultiPayOption.cheques;
        MultiPayOption.cheques = null;
// End Of Payment Infor 

// Payment 
        payment = new OBJPayment();
        payment.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
        payment.setCostCode(txtCostCode.getText());
        payment.setCustomerCode(txtCustAccCode.getText());
        payment.setCashireSession(null);
        payment.setAmount(txtpay.getText());
        payment.setTransaction("");
        payment.setTransactionType(TransactionType.VOUCHER);
        payment.setStatus(TransactionStatus.ACTIVE);

// End Of Payment    
// Cheque Registry Entry
        if (cheques != null) {
            this.cheques = null;
            this.cheques = new ArrayList<>();
            for (OBJCheque oBJCheque : cheques) {
                cheque = new OBJCheque();
                cheque.setCustCode(txtCustAccCode.getText());
                cheque.setBankAccount(oBJCheque.getBankAccount());
                cheque.setInvoNo(txtVouCode.getText());
                cheque.setChqeNo(oBJCheque.getChqeNo());
                cheque.setGetDate(Locals.setDateFormat(txtJVDate.getDate()));
                cheque.setRDate(oBJCheque.getRDate());
                cheque.setAmount(oBJCheque.getAmount());
                cheque.setBank(oBJCheque.getBank());
                cheque.setBankAccount(oBJCheque.getBankAccount());
                cheque.setFCCode("001");
                cheque.setFCRate("1.00");
                cheque.setRemarks(oBJCheque.getRemarks());
                cheque.setType(TransactionType.VOUCHER);
                cheque.setBankCharge("0.00");
                cheque.setInterestCharge("0.00");
                cheque.setNet(oBJCheque.getAmount());
                this.cheques.add(cheque);
            }
        }
//End Of Cheque Registry
    }

    private void doNew() {
        setID();
        // txtCode.setText("");
        txtJVDate.setDate(Locals.toDate(Locals.currentDate_F2()));
        txtCustAccCode.setText("");
        txtName.setText("");
        txtNarration.setText("");
        txtCostCode.setText("");
        txtCostCenter.setText("");

        txtTotOwing.setText("0.00");
        txtTotInvoBalance.setText("0.00");
        txtTotPaied.setText("0.00");
        txtInstalAmount.setText("0.00");

        txtInstalAmount.setText("0.00");
        txtLatepay.setText("0.00");
        txtdisc.setText("0.00");
        txtdiscrate.setText("0.00");
        txtpayble.setText("0.00");
        txtpay.setText("0.00");
        txtbal.setText("0.00");

        txtCid.setText("");
        txtinvo.setText("");
        txtinsNo.setText("");

        txtPayTermCode.setText("");
        txtRef.setText("");

        txtNextPay.setDate(Locals.toDate(Locals.currentDate_F2()));
        txtDue.setText("");
        txtsp.setText("");
        txtApproved.setText("");
        txtAccountant.setText("");

        int i = jTable1.getRowCount();
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        for (int j = 0; j < i; j++) {
            dt.removeRow(0);
        }

        setEnableAll(true);
        setMode(DEFAULT_STATUS);
    }

    private void doEdit() {
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
        txtVouCode.setEnabled(false);
    }

    private void doDelete() {
        String code = txtVouCode.getText();
        SERCustomerGroup.delete(code);
        indexCount = SERCustomerGroup.getIndex();
        if (Index <= indexCount) {
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
        txtName.setEnabled(state);
//         txtAddress1.setEnabled(state);
//        txtAddress2.setEnabled(state);
//        txtAddress3.setEnabled(state);
//        txtPobox.setEnabled(state);
//        txtTel.setEnabled(state);
//        txtFax.setEnabled(state);
//        txtmobi.setEnabled(state);
//        txtEmail.setEnabled(state);
//        txtId.setEnabled(state);
    }

    private void getNavi() {
        obj = SERPayAgainstInvoice.getNavi(Index);
        setContent(obj);
    }

    private void doFind() {
        new GUIFindWindow(null, true, "customer", "CustCode", "Custname").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;

        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void serch(String code) {
        obj = SERPayAgainstInvoice.serch(code);
        setContent(obj);
    }

    private void setContent(OBJPayAgainstInvoice obj) {

//        txtVouCode.setText(obj.getId());
//        txtCustAccCode.setText(obj.getCustAccCode());
//        txtName.setText(obj.getName());
//        txtAddress1.setText(obj.getAdd1());
//        txtAddress2.setText(obj.getAdd2());
//        txtAddress3.setText(obj.getAdd3());
//        txtPobox.setText(obj.getPbox());
//        txtTel.setText(obj.getTel());
//        txtFax.setText(obj.getFax());
//        txtmobi.setText(obj.getMobi());
//        txtEmail.setText(obj.getEmail());
//        obja = SERRecAgainstInvoice.serchH(obj.getCustAccCode());
        setContentH(obja);
    }

    private void setContentH(ArrayList<OBJPayAgainstInvoice> obja) {

    }

    private void setContent() {
//    txtCustAccCode.setText(obj.getCustAccCode());
//        txtName.setText(obj.getName());
//        txtAddress1.setText(obj.getAdd1());
//        txtAddress2.setText(obj.getAdd2());
//        txtAddress3.setText(obj.getAdd3());
//        txtPobox.setText(obj.getPbox());
//        txtTel.setText(obj.getTel());
//        txtFax.setText(obj.getFax());
//        txtmobi.setText(obj.getMobi());
//        txtEmail.setText(obj.getEmail());
//        txtId.setText(obj.getId());

//        obja = SERRecAgainstInvoice.serchH(obj.getCustAccCode());
        setContentH(obja);
    }

    private void getPayTerm() {
        new GUIFindWindow(null, true, "PayTerms", "PTCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtPayTermCode.setText(code);
        //txtCostCenter.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";

        switch (txtPayTermCode.getText()) {
            case "CHEQ":
                new GUICheque(null, true, 1, txtVouCode.getText(), txtCustAccCode.getText()).setVisible(true);
                code = accountpackage.AccountPackage.CODE;
                txtRef.setText(code);
                accountpackage.AccountPackage.CODE = "";
                break;
            case "CARD":
                break;
        }
    }

    private void loadCustomer() {
        new GUIFindWindow(null, true, "customer", "AccCode", "CustName", "V").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        txtCustAccCode.setText(code);
        txtName.setText(accountpackage.AccountPackage.NAME);
        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void loadCust(String cd, String fld) {
        txtName.setText(SERCommen.getDescription("customer", cd, fld, "PrintName"));
        txtCustAccCode.setText(SERCommen.getDescription("customer", cd, fld, "AccCode"));
    }

    private void loadInvoice() {
        String Cust = txtCustAccCode.getText();
        obji = SERInvoList.getList(Cust);
        clrTable();
        int i = 1;
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        for (OBJInvoList objq : obji) {
            dt.addRow(new Object[]{i, objq.getInvoNo(), objq.getInvoDate(), objq.getOriAmount(), objq.getOwingAmount(), objq.getPaiedAmount(), objq.getInvoBalance(), objq.getInstallAmount(), objq.getPayDate()});
            i++;
        }
        calcAll();
    }

    private void clrTable() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int rc = jTable1.getRowCount();
        for (int i = 0; i < rc; i++) {
            df.removeRow(0);
        }
    }

    private void calcAll() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int rc = jTable1.getRowCount();

        double owi = 0.00, paid = 0.00, Bal = 0.00, adj = 0.00;

        for (int i = 0; i < rc; i++) {
            owi = owi + Double.parseDouble(df.getValueAt(i, 4).toString());
            paid = paid + Double.parseDouble(df.getValueAt(i, 5).toString());
            Bal = Bal + Double.parseDouble(df.getValueAt(i, 6).toString());
            adj = adj + Double.parseDouble(df.getValueAt(i, 7).toString());
        }
        txtTotOwing.setText(core.Locals.currencyFormat(owi));
        txtTotPaied.setText(core.Locals.currencyFormat(paid));
        txtTotInvoBalance.setText(core.Locals.currencyFormat(Bal));
        txtInstalAmount.setText(core.Locals.currencyFormat(adj));
    }

    private void calcPayment() {

        double d;
        double d1;
        double d2;
        double d3;
        double d4;
        double d5;

        d = Double.parseDouble(txtdiscrate.getText());
        d1 = Double.parseDouble(txtInstal.getText());
        if (!txtdiscrate.getText().equals("0.00")) {

            txtdisc.setText(Locals.currencyFormat(d1 * d * 0.01));

        }
        d2 = Double.parseDouble(txtdisc.getText());
        d4 = Double.parseDouble(txtpay.getText());
        d5 = Double.parseDouble(txtLatepay.getText());

        txtpayble.setText(Locals.currencyFormat(d1 + d5 - d2));
        d3 = Double.parseDouble(txtpayble.getText());
        txtbal.setText(Locals.currencyFormat(d3 - d4));

    }

    private void loadPay() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int i = jTable1.getSelectedRow();

        objIP = SERInvoList.getInstall(df.getValueAt(i, 1).toString(), Locals.setDateFormat(txtJVDate.getDate()));
        txtInstal.setText(objIP.getAmount());
        txtDue.setText(objIP.getDueDate());

        txtLatepay.setText(Locals.currencyFormat(Double.parseDouble(objIP.getLatePay())));
        txtInstal.setText(Locals.currencyFormat(Double.parseDouble(objIP.getBalance())));
        txtpayble.setText(Locals.currencyFormat(Double.parseDouble(objIP.getBalance())));
        txtbal.setText(Locals.currencyFormat(0.00));

        txtCid.setText(objIP.getCreditId());
        txtinvo.setText(objIP.getInvoNo());
        txtinsNo.setText(objIP.getInstallNo());
    }

    private void setID() {
        txtVouCode.setText(SERVouch.getID());
    }

    private void getCost() {
        new GUIFindWindow(null, true, "CostCenter", "CostCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtCostCode.setText(code);
        txtCostCenter.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
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
        txtJVDate.setDate(Locals.toDate(Locals.currentDate_F2()));
        cheque = new OBJCheque();
        cheques = new ArrayList<>();

        cmdFirst.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFirstActionPerformed(evt);
            }
        });
        cmdLast.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLastActionPerformed(evt);
            }
        });
        cmdPrev.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPrevActionPerformed(evt);
            }
        });
        cmdNext.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNextActionPerformed(evt);
            }
        });
        cmdFind.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFindActionPerformed(evt);
            }
        });
        cmdNew.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        cmdView.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdViewActionPerformed(evt);
            }
        });
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });
        cmdReport.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReportActionPerformed(evt);
            }
        });
        cmdExit.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExitActionPerformed(evt);
            }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cmdPrintChq = new javax.swing.JButton();
        cmdGetInvoice = new javax.swing.JButton();
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
        jPanel2 = new javax.swing.JPanel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        txtCostCode = new javax.swing.JTextField();
        cmdCostCenter = new javax.swing.JButton();
        txtCostCenter = new javax.swing.JTextField();
        txtVouCode = new javax.swing.JTextField();
        txtAccountant = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtJVDate = new com.toedter.calendar.JDateChooser();
        txtApproved = new javax.swing.JTextField();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        cmdCust = new javax.swing.JButton();
        txtName = new javax.swing.JTextField();
        txtCustAccCode = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNarration = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel14 = new javax.swing.JLabel();
        txtTotOwing = new javax.swing.JFormattedTextField();
        txtTotPaied = new javax.swing.JFormattedTextField();
        txtTotInvoBalance = new javax.swing.JFormattedTextField();
        txtInstalAmount = new javax.swing.JFormattedTextField();
        txtDue = new javax.swing.JLabel();
        txtInstal = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        txtpayble = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtLatepay = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        txtpay = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        txtbal = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        txtPayTermCode = new javax.swing.JTextField();
        cmdArea3 = new javax.swing.JButton();
        txtRef = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtdisc = new javax.swing.JFormattedTextField();
        txtdiscrate = new javax.swing.JFormattedTextField();
        txtCid = new javax.swing.JLabel();
        txtinvo = new javax.swing.JLabel();
        txtinsNo = new javax.swing.JLabel();
        txtNextPay = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtsp = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        txtDocumentNo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();

        setClosable(true);
        setTitle("Payment Voucher (Against Invoice)");

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cmdPrintChq.setText("Print Cheque");
        cmdPrintChq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPrintChqActionPerformed(evt);
            }
        });

        cmdGetInvoice.setText("Get Invoice");
        cmdGetInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGetInvoiceActionPerformed(evt);
            }
        });

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

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCostCode.setText("001");
        txtCostCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtCostCode);
        txtCostCode.setBounds(0, 0, 50, 18);

        cmdCostCenter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdCostCenter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdCostCenter.setContentAreaFilled(false);
        cmdCostCenter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCostCenterActionPerformed(evt);
            }
        });
        jLayeredPane3.add(cmdCostCenter);
        cmdCostCenter.setBounds(53, 0, 20, 20);

        txtCostCenter.setEditable(false);
        txtCostCenter.setBackground(new java.awt.Color(255, 255, 241));
        txtCostCenter.setForeground(new java.awt.Color(153, 0, 0));
        txtCostCenter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtCostCenter);
        txtCostCenter.setBounds(75, 0, 160, 18);

        txtVouCode.setEditable(false);
        txtVouCode.setBackground(new java.awt.Color(255, 255, 233));
        txtVouCode.setForeground(new java.awt.Color(153, 0, 0));
        txtVouCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtAccountant.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel7.setText("Approved By");

        txtJVDate.setDateFormatString("yyyy-MM-dd");

        txtApproved.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        cmdCust.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdCust.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdCust.setContentAreaFilled(false);
        cmdCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCustActionPerformed(evt);
            }
        });
        jLayeredPane2.add(cmdCust);
        cmdCust.setBounds(70, 0, 20, 20);

        txtName.setEditable(false);
        txtName.setBackground(new java.awt.Color(255, 255, 241));
        txtName.setForeground(new java.awt.Color(153, 0, 0));
        txtName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane2.add(txtName);
        txtName.setBounds(90, 0, 230, 18);

        txtCustAccCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCustAccCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustAccCodeActionPerformed(evt);
            }
        });
        jLayeredPane2.add(txtCustAccCode);
        txtCustAccCode.setBounds(0, 0, 70, 20);

        jLabel5.setText("Narration");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Invoice No", "Inv. Date", "Original Amt.", "Amt. Owing", "Amt. Paid", "Invoice Bal.", "Instal Amount", "PayDate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(2);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(95);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(95);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(95);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(95);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(8).setResizable(false);
        }

        jLabel3.setText("J.V. Date");

        jLabel8.setText("Vend.  A/C");

        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setText("Vou. Number");

        txtNarration.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel6.setText("Prep. By");

        jLabel10.setText("Cost Center");

        jLabel14.setBackground(new java.awt.Color(238, 238, 228));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Total");
        jLabel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel14.setOpaque(true);
        jLayeredPane1.add(jLabel14);
        jLabel14.setBounds(120, 0, 110, 20);

        txtTotOwing.setBackground(new java.awt.Color(204, 204, 255));
        txtTotOwing.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotOwing.setForeground(new java.awt.Color(153, 0, 0));
        txtTotOwing.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotOwing.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotOwing.setText("0.00");
        jLayeredPane1.add(txtTotOwing);
        txtTotOwing.setBounds(230, 0, 90, 20);

        txtTotPaied.setBackground(new java.awt.Color(204, 204, 255));
        txtTotPaied.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotPaied.setForeground(new java.awt.Color(153, 0, 0));
        txtTotPaied.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotPaied.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotPaied.setText("0.00");
        jLayeredPane1.add(txtTotPaied);
        txtTotPaied.setBounds(320, 0, 90, 20);

        txtTotInvoBalance.setBackground(new java.awt.Color(204, 204, 255));
        txtTotInvoBalance.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotInvoBalance.setForeground(new java.awt.Color(153, 0, 0));
        txtTotInvoBalance.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotInvoBalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotInvoBalance.setText("0.00");
        jLayeredPane1.add(txtTotInvoBalance);
        txtTotInvoBalance.setBounds(410, 0, 90, 20);

        txtInstalAmount.setBackground(new java.awt.Color(204, 204, 255));
        txtInstalAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtInstalAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtInstalAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtInstalAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtInstalAmount.setText("0.00");
        jLayeredPane1.add(txtInstalAmount);
        txtInstalAmount.setBounds(500, 0, 80, 20);
        jLayeredPane1.add(txtDue);
        txtDue.setBounds(0, -6, 120, 30);

        txtInstal.setEditable(false);
        txtInstal.setBackground(new java.awt.Color(204, 204, 255));
        txtInstal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtInstal.setForeground(new java.awt.Color(153, 0, 0));
        txtInstal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtInstal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtInstal.setText("0.00");

        jLabel15.setBackground(new java.awt.Color(238, 238, 228));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Installment");
        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel15.setOpaque(true);

        txtpayble.setEditable(false);
        txtpayble.setBackground(new java.awt.Color(204, 204, 255));
        txtpayble.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtpayble.setForeground(new java.awt.Color(153, 0, 0));
        txtpayble.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtpayble.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtpayble.setText("0.00");

        jLabel18.setBackground(new java.awt.Color(238, 238, 228));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Total Payble");
        jLabel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel18.setOpaque(true);

        txtLatepay.setBackground(new java.awt.Color(204, 204, 255));
        txtLatepay.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtLatepay.setForeground(new java.awt.Color(153, 0, 0));
        txtLatepay.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtLatepay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtLatepay.setText("0.00");
        txtLatepay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLatepayKeyReleased(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(238, 238, 228));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Late pay");
        jLabel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel19.setOpaque(true);

        txtpay.setBackground(new java.awt.Color(204, 204, 255));
        txtpay.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtpay.setForeground(new java.awt.Color(153, 0, 0));
        txtpay.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtpay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtpay.setText("0.00");
        txtpay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpayKeyReleased(evt);
            }
        });

        jLabel20.setBackground(new java.awt.Color(238, 238, 228));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Pay");
        jLabel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel20.setOpaque(true);

        txtbal.setEditable(false);
        txtbal.setBackground(new java.awt.Color(204, 204, 255));
        txtbal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtbal.setForeground(new java.awt.Color(153, 0, 0));
        txtbal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtbal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtbal.setText("0.00");

        jLabel21.setBackground(new java.awt.Color(238, 238, 228));
        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Balance");
        jLabel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel21.setOpaque(true);

        txtPayTermCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtPayTermCode);
        txtPayTermCode.setBounds(0, 0, 90, 18);

        cmdArea3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdArea3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdArea3.setContentAreaFilled(false);
        cmdArea3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdArea3ActionPerformed(evt);
            }
        });
        jLayeredPane4.add(cmdArea3);
        cmdArea3.setBounds(90, 0, 20, 20);

        txtRef.setEditable(false);
        txtRef.setBackground(new java.awt.Color(255, 255, 241));
        txtRef.setForeground(new java.awt.Color(153, 0, 0));
        txtRef.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtRef);
        txtRef.setBounds(110, 0, 150, 18);

        jLabel11.setText("Pay Mod");

        jLabel22.setBackground(new java.awt.Color(238, 238, 228));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Special Disc.");
        jLabel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel22.setOpaque(true);

        txtdisc.setBackground(new java.awt.Color(204, 204, 255));
        txtdisc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtdisc.setForeground(new java.awt.Color(153, 0, 0));
        txtdisc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtdisc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtdisc.setText("0.00");
        txtdisc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdiscActionPerformed(evt);
            }
        });
        txtdisc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdiscKeyReleased(evt);
            }
        });

        txtdiscrate.setBackground(new java.awt.Color(204, 204, 255));
        txtdiscrate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtdiscrate.setForeground(new java.awt.Color(153, 0, 0));
        txtdiscrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtdiscrate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtdiscrate.setText("0.00");
        txtdiscrate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdiscrateKeyReleased(evt);
            }
        });

        txtNextPay.setDateFormatString("yyyy-MM-dd");

        jLabel2.setText("Next Pay");

        txtsp.setForeground(new java.awt.Color(204, 0, 0));

        jLabel4.setText("Credit Id");

        jLabel9.setText("Invo. No");

        jLabel12.setText("Install No.");

        jCheckBox2.setText("Paid");

        jLabel13.setText("Document No");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtNarration, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLayeredPane3))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(88, 88, 88)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDocumentNo))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtVouCode, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtJVDate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(259, 259, 259))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(8, 8, 8)
                        .addComponent(txtAccountant, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtApproved, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)))
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtNextPay, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtsp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCid, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtinvo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtinsNo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtdiscrate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtdisc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtInstal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtLatepay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtbal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtpayble, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtpay, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtVouCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(txtJVDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtDocumentNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNarration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                        .addComponent(jLayeredPane3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtpayble, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpay, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtbal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCid, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addComponent(txtInstal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtinvo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtLatepay, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtdisc, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtdiscrate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtinsNo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12)))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox2)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addComponent(txtNextPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtsp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtAccountant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtApproved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel7)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane2)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                .addGap(322, 322, 322))
        );

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Print Receipt");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdPrintChq, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdGetInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdPrintChq)
                    .addComponent(cmdGetInvoice)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cmdPrintChqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPrintChqActionPerformed
//    try {
//        new system.accounts.transaction.receiptvoucher.againstinvoice.PrintVou("29", "1", "000001").setVisible(true);
//    } catch (Exception ex) {
//        Exp.Handle(ex);
//    }
}//GEN-LAST:event_cmdPrintChqActionPerformed

    private void cmdArea3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdArea3ActionPerformed
        getPayTerm();
    }//GEN-LAST:event_cmdArea3ActionPerformed

    private void txtCustAccCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustAccCodeActionPerformed
        loadCust(txtCustAccCode.getText(), "AccCode");

        loadInvoice();
    }//GEN-LAST:event_txtCustAccCodeActionPerformed

    private void cmdCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCustActionPerformed
        loadCustomer();
    }//GEN-LAST:event_cmdCustActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (jTable1.getSelectedRow() >= 0) {
            loadPay();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void cmdGetInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGetInvoiceActionPerformed
        int i = jTable1.getSelectedRow();
        if (i >= 0) {
            mainApp.MainFrame.f = 4;
            mainApp.MainFrame.val = jTable1.getValueAt(i, 1).toString();
        }
    }//GEN-LAST:event_cmdGetInvoiceActionPerformed

    private void txtdiscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdiscActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdiscActionPerformed

    private void txtdiscKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdiscKeyReleased
        calcPayment();
    }//GEN-LAST:event_txtdiscKeyReleased

    private void txtdiscrateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdiscrateKeyReleased
        calcPayment();
    }//GEN-LAST:event_txtdiscrateKeyReleased

    private void txtpayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpayKeyReleased
        calcPayment();
    }//GEN-LAST:event_txtpayKeyReleased

    private void txtLatepayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLatepayKeyReleased
        calcPayment();
    }//GEN-LAST:event_txtLatepayKeyReleased

    private void cmdCostCenterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCostCenterActionPerformed
        getCost();
    }//GEN-LAST:event_cmdCostCenterActionPerformed

    private void cmdViewActionPerformed(java.awt.event.ActionEvent evt) {
        Index = 0;
        doView();
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
        indexCount = SERCustomerGroup.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index--;
        }
    }

    private void cmdLastActionPerformed(java.awt.event.ActionEvent evt) {
        Index = SERCustomerGroup.getIndex();
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
        Act = 0;
        doEdit();
    }

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int q = JOptionPane.showConfirmDialog(null, "Are you sure want to delete ?", "delete Area", JOptionPane.YES_NO_OPTION);

        if (q == JOptionPane.YES_OPTION) {
            doDelete();
        }
    }

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {
        doSave();
    }

    private void cmdReportActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void cmdExitActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdArea3;
    private javax.swing.JButton cmdCostCenter;
    private javax.swing.JButton cmdCust;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdExit;
    private javax.swing.JButton cmdFind;
    private javax.swing.JButton cmdFirst;
    private javax.swing.JButton cmdGetInvoice;
    private javax.swing.JButton cmdLast;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdNext;
    private javax.swing.JButton cmdPrev;
    private javax.swing.JButton cmdPrintChq;
    private javax.swing.JButton cmdReport;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdView;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtAccountant;
    private javax.swing.JTextField txtApproved;
    private javax.swing.JLabel txtCid;
    private javax.swing.JTextField txtCostCenter;
    private javax.swing.JTextField txtCostCode;
    private javax.swing.JTextField txtCustAccCode;
    private javax.swing.JTextField txtDocumentNo;
    private javax.swing.JLabel txtDue;
    private javax.swing.JFormattedTextField txtInstal;
    private javax.swing.JFormattedTextField txtInstalAmount;
    private com.toedter.calendar.JDateChooser txtJVDate;
    private javax.swing.JFormattedTextField txtLatepay;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNarration;
    private com.toedter.calendar.JDateChooser txtNextPay;
    private javax.swing.JTextField txtPayTermCode;
    private javax.swing.JTextField txtRef;
    private javax.swing.JFormattedTextField txtTotInvoBalance;
    private javax.swing.JFormattedTextField txtTotOwing;
    private javax.swing.JFormattedTextField txtTotPaied;
    private javax.swing.JTextField txtVouCode;
    private javax.swing.JFormattedTextField txtbal;
    private javax.swing.JFormattedTextField txtdisc;
    private javax.swing.JFormattedTextField txtdiscrate;
    private javax.swing.JLabel txtinsNo;
    private javax.swing.JLabel txtinvo;
    private javax.swing.JFormattedTextField txtpay;
    private javax.swing.JFormattedTextField txtpayble;
    private javax.swing.JLabel txtsp;
    // End of variables declaration//GEN-END:variables
    private OBJPayAgainstInvoice obj;
    private OBJAgInvo objin;

    private OBJInstallPay objIP;
    private ArrayList<OBJPayAgainstInvoice> obja;
    private ArrayList<OBJInvoList> obji;

    private ArrayList<OBJCheque> cheques;
    public static OBJCheque cheque;

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
    private int Index = 0;
    private int indexCount = 0;
}
