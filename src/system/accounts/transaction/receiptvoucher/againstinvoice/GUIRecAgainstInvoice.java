/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIAgainstInvoice.java
 *
 * Created on Jul 19, 2013, 4:59:24 PM
 */
package system.accounts.transaction.receiptvoucher.againstinvoice;

import accountpackage.AccountPackage;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.SERCommen;
import mainApp.findwindow.GUIFindWindow;
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
public class GUIRecAgainstInvoice extends javax.swing.JInternalFrame {

    private static String cid;
    private static String install;
    private static String invoNo;
    private static String duedate;

    /**
     * Creates new form GUIAgainstInvoice
     */
    public GUIRecAgainstInvoice() {
        initComponents();
        initOthers();
    }

    public GUIRecAgainstInvoice(String a) {
        this();
        // initComponents();
        //initOthers();
        loadCust(a, "AccCode");
        loadInvoice();
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
                String nextPaymentDate;
                try {
                    nextPaymentDate = Locals.setDateFormat(datNextPayDate.getDate());
                } catch (Exception e) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(txtJVDate.getDate());
                    cal.add(Calendar.MONTH, 1);
                    nextPaymentDate = Locals.setDateFormat(cal.getTime());
                }
                DefaultTableModel df = (DefaultTableModel) tblInvoice.getModel();
                invoNo = df.getValueAt(tblInvoice.getSelectedRow(), 1).toString();
                objin = new OBJAgInvo(
                        cid,
                        invoNo,
                        install,
                        nextPaymentDate,
                        txtpay.getText(),
                        txtdisc.getText(),
                        txtbal.getText(),
                        txtVouCode.getText(),
                        Locals.setDateFormat(txtJVDate.getDate()),
                        txtTranstype.getSelectedItem().toString(),
                        txtCustAccCode.getText(),
                        txtNarration.getText(),
                        txtCostCode.getText(),
                        txtPayTermCode.getText(),
                        txtAccountant.getText(),
                        txtApproved.getText(),
                        TransactionType.RECEIPT,
                        chkStatus.isSelected(),
                        txtRef.getText());

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
                if (createSchedule() && createOBJIP() && (payment != null || !txtPayTermCode.getText().equals("INVO"))) {
                    boolean bul = SERVouch.save(objin, objIP, objIPList, nextPaymentDate, paymentsInfo, cheques, transaction, payment, paymentsInfo, accountTranses, itemTransactions, Act);
                    if (bul) {
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
                                new PrintVou(cid, install, txtVouCode.getText(), TransactionType.RECEIPT).setVisible(true);
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

    private boolean createSchedule() {
        objIPList = new ArrayList<>();
        int row = tblPaySchedule.getRowCount();
        try {
            for (int i = 0; i < row; i++) {
                OBJInstallPay objIP = new OBJInstallPay();
                objIP.setCreditId(cid);
                objIP.setClearDate(core.Locals.setDateFormat(txtJVDate.getDate()));
                
                if (Double.parseDouble(tblPaySchedule.getValueAt(i, 6).toString()) == 0) {
                    objIP.setStatus("1");
                } else if (chkStatus.isSelected()) {
                    objIP.setStatus("2");
                } else {
                    objIP.setStatus("0");
                } 
                
                objIP.setInvoNo(invoNo);
                objIP.setInstallNo(tblPaySchedule.getValueAt(i, 0).toString());
                Double totPaid = Double.parseDouble(tblPaySchedule.getValueAt(i, 7).toString()) + Double.parseDouble(tblPaySchedule.getValueAt(i, 5).toString());
                objIP.setPayAmount(totPaid + "");
                objIP.setBalance(tblPaySchedule.getValueAt(i, 6).toString());
                objIP.setSPDisc(tblPaySchedule.getValueAt(i, 3).toString());
                objIP.setID(((OBJPaymentSchedule)tblPaySchedule.getValueAt(i, 0)).getID());
                
                if (!tblPaySchedule.getValueAt(i, 7).toString().equals("0.00")) {
                    objIP.setInstall("1");
                } else {
                    objIP.setInstall("0");
                }
                
                objIPList.add(objIP);
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean createOBJIP() {
        objIP = new OBJInstallPay();
        try {
            objIP.setCreditId(cid);
            objIP.setClearDate(core.Locals.setDateFormat(txtJVDate.getDate()));
            if (chkStatus.isSelected()) {
                objIP.setStatus("1");
            } else {
                objIP.setStatus("0");
            }
            objIP.setInvoNo(invoNo);
            objIP.setInstall(install);
            objIP.setPayAmount(txtpay.getText());
            objIP.setSPDisc(txtdisc.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void transaction() {
        // Transaction Object
        transaction = new OBJTransaction();
        transaction.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
        transaction.setTransactionType(TransactionType.RECEIPT);
        transaction.setReferanceNo(txtVouCode.getText());
        transaction.setDocumentNo(txtDocumentNo.getText());
        transaction.setLoan(txtLoanNo.getText());
        transaction.setCostCode(txtCostCode.getText());
        transaction.setCustomerCode(txtCustAccCode.getText());
        transaction.setNote(txtNarration.getText());
        transaction.setStatus(TransactionStatus.ACTIVE);

    }

    private boolean accountTransaction() {
        accountTranses = new ArrayList<>();

        // Account Transaction
        // Debit Account Trans
        for (OBJPaymentInfo info : paymentsInfo) {
            OBJAccountTrans accountTrans = new OBJAccountTrans();
            accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
            accountTrans.setCostCode(txtCostCode.getText());
            accountTrans.setAccountSetting(null);

            switch (info.getPaymentSetting()) {
                case PaymentSetting.RECEIPT_CASH:
                    accountTrans.setDescription(AccountTransactionDescription.RECEIPT_CASH_PAYMENT);
                    accountTrans.setAccount(((PaymentSettingObject) SystemSetting.getPaymentSeting(PaymentSetting.RECEIPT_CASH)).getAccount());
                    accountTrans.setCreditAmount("0.00");
                    accountTrans.setDebitAmount(info.getAmount());
                    break;
                case PaymentSetting.RECEIPT_CHEQUE:
                    accountTrans.setDescription(AccountTransactionDescription.RECEIPT_CHEQUE_PAYMENT);
                    accountTrans.setAccount(((PaymentSettingObject) SystemSetting.getPaymentSeting(PaymentSetting.RECEIPT_CHEQUE)).getAccount());
                    accountTrans.setCreditAmount("0.00");
                    accountTrans.setDebitAmount(info.getAmount());
                    break;
            }

            accountTrans.setTransactionType(core.system_transaction.TransactionType.RECEIPT);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }

// Rebit Expenses
        OBJAccountTrans accountTrans = new OBJAccountTrans();
        if (Double.parseDouble(txtdisc.getText()) > 0.00) {
            accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
            accountTrans.setCostCode(txtCostCode.getText());
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(AccountTransactionDescription.LOAN_REBIT_EXPENSES + " For Invoice No. : " + objIP.getInvoNo());
            accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.LOAN_REBIT_EXPENSES)).getAccount());
            accountTrans.setCreditAmount("0.00");
            accountTrans.setDebitAmount(txtdisc.getText());
            accountTrans.setTransactionType(core.system_transaction.TransactionType.RECEIPT);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }

        if (chkStatus.isSelected() && (Double.parseDouble(txtTotPayble.getText()) - Double.parseDouble(txtpay.getText())) > 0.00) {
            accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
            accountTrans.setCostCode(txtCostCode.getText());
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(AccountTransactionDescription.UNPAID_EXPENCES + " For Invoice No. : " + objIP.getInvoNo());
            accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.UNPAID_EXPENSES)).getAccount());
            accountTrans.setCreditAmount("0.00");
            accountTrans.setDebitAmount((Double.parseDouble(txtTotPayble.getText()) - Double.parseDouble(txtpay.getText())) + "");
            accountTrans.setTransactionType(core.system_transaction.TransactionType.RECEIPT);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }

        if ((Double.parseDouble(txtTotPayble.getText()) - Double.parseDouble(txtpay.getText())) < 0.00) {
            accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
            accountTrans.setCostCode(txtCostCode.getText());
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(AccountTransactionDescription.OVER_PAY_INCOME + " For Invoice No. : " + objIP.getInvoNo());
            accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.OVER_PAY_INCOME)).getAccount());
            accountTrans.setCreditAmount("0.00");
            accountTrans.setDebitAmount((Double.parseDouble(txtpay.getText()) - Double.parseDouble(txtTotPayble.getText())) + "");
            accountTrans.setTransactionType(core.system_transaction.TransactionType.RECEIPT);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }

        // Credit Account Trans
        accountTrans = new OBJAccountTrans();
        accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
        accountTrans.setCostCode(txtCostCode.getText());
        accountTrans.setAccountSetting(null);
        accountTrans.setDescription(AccountTransactionDescription.RECEIPT_AMOUNT + " - Invoice No : " + objIP.getInvoNo());
        accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.RECEIPT_AMOUNT_CREDIT)).getAccount());
        accountTrans.setCreditAmount(txtpay.getText());
        accountTrans.setDebitAmount("0.00");
        accountTrans.setTransactionType(core.system_transaction.TransactionType.RECEIPT);
        accountTrans.setType("AUTO");
        accountTrans.setStatus(AccountTransStatus.ACTIVE);
        accountTranses.add(accountTrans);
        return true;
    }

    private void getPayment() {

        new MultiPayOption(null, true, txtpay.getText(), paymentsInfo, VoucherType.RECEIPT, cheques).setVisible(true);
// Payment Information
        if (MultiPayOption.infos != null) {
            paymentsInfo = MultiPayOption.infos;
            MultiPayOption.infos = null;
            ArrayList<OBJCheque> cheques = MultiPayOption.cheques;
            MultiPayOption.cheques = null;
            this.cheques = null;
            this.cheques = new ArrayList<>();
// End Of Payment Infor 

// Payment 
            payment = new OBJPayment();
            payment.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
            payment.setCostCode(txtCostCode.getText());
            payment.setCustomerCode(txtCustAccCode.getText());
            payment.setCashireSession(null);
            payment.setAmount(txtpay.getText());
            payment.setTransaction("");
            payment.setTransactionType(core.system_transaction.TransactionType.RECEIPT);
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
                    cheque.setFCCode("001");
                    cheque.setFCRate("1.00");
                    cheque.setRemarks(oBJCheque.getRemarks());
                    cheque.setType(core.system_transaction.TransactionType.RECEIPT);
                    cheque.setBankCharge("0.00");
                    cheque.setInterestCharge("0.00");
                    cheque.setNet(oBJCheque.getAmount());
                    cheque.setStatus("0");
                    this.cheques.add(cheque);
                }
            }
        }
//End Of Cheque Registry
    }

    private void doNew() {
        setID();
        // txtCode.setText("");
        txtJVDate.setDate(Locals.toDate(Locals.currentDate_F2()));
        txtTranstype.setSelectedIndex(1);
        txtCustAccCode.setText("");
        txtName.setText("");
        txtLoanNo.setText("");
        txtDocumentNo.setText("");
        txtNarration.setText("");
        txtCostCode.setText("");
        txtCostCenter.setText("");

        txtTotOwing.setText("0.00");
        txtTotInvoBalance.setText("0.00");
        txtTotPaied.setText("0.00");
        txtInstalAmount.setText("0.00");

        txtInstalAmount.setText("0.00");
        txtdisc.setText("0.00");
        txtpayble.setText("0.00");
        txtpay.setText("0.00");
        txtbal.setText("0.00");

        cid = "";
        invoNo = "";
        install = "";

        txtPayTermCode.setText("");
        txtRef.setText("");

        invoTableClear();
        scheduleTableClear();

        datNextPayDate.setDate(Locals.toDate(Locals.currentDate_F2()));
        duedate = "";
        txtsp.setText("");
        txtApproved.setText("");
        txtAccountant.setText("");

        setEnableAll(true);
        setMode(DEFAULT_STATUS);
    }

    private void invoTableClear() {
        int i = tblInvoice.getRowCount();
        DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
        for (int j = 0; j < i; j++) {
            dt.removeRow(0);
        }

    }

    private void scheduleTableClear() {
        int i = tblPaySchedule.getRowCount();
        DefaultTableModel dt = (DefaultTableModel) tblPaySchedule.getModel();
        for (int j = 0; j < i; j++) {
            dt.removeRow(0);
        }

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
                // cmdFirst.setEnabled(true);
                // cmdLast.setEnabled(true);
                //cmdNext.setEnabled(true);
                //cmdPrev.setEnabled(true);
                //cmdFind.setEnabled(true);
                //cmdNew.setEnabled(true);
                //cmdEdit.setEnabled(true);
                //cmdDelete.setEnabled(true);
                //cmdView.setEnabled(!true);
                cmdSave.setEnabled(true);
//                cmdReport.setEnabled(true);
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
        obj = SERRecAgainstInvoice.getNavi(Index);
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
        obj = SERRecAgainstInvoice.serch(code);
        setContent(obj);
    }

    private void setContent(OBJRecAgainstInvoice obj) {

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

    private void setContentH(ArrayList<OBJRecAgainstInvoice> obja) {

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

    }

    private void loadCustomer() {
        new GUIFindWindow(null, true, "customer", "AccCode", "CustName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        txtCustAccCode.setText(accountpackage.AccountPackage.CODE);

        txtName.setText(accountpackage.AccountPackage.NAME);
        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void loadCust(String cd, String fld) {
        txtName.setText(SERCommen.getDescription("customer", cd, fld, "PrintName"));
        txtCustAccCode.setText(SERCommen.getDescription("customer", cd, fld, "AccCode"));
    }

    private void loadLoan() {
        txtCustAccCode.setText(SERCommen.getDescription("invoiceHeader", txtLoanNo.getText(), "loan_no", "CustCode"));
        loadCust(txtCustAccCode.getText(), "AccCode");
        loadInvoice();
    }

    private void loadInvoice() {
        clrTable();
        String Cust = txtCustAccCode.getText();
        obji = SERInvoList.getList(Cust);
        int i = 1;
        DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
        for (OBJInvoList objq : obji) {
            dt.addRow(new Object[]{i, objq.getInvoNo(), objq.getInvoDate(), objq.getOriAmount(), objq.getPaiedAmount(), objq.getOwingAmount(), objq.getInvoBalance(), objq.getInstallAmount(), objq.getPayDate(), objq.getLoanNo()});
            i++;
        }
        calcAll();
    }

    private void clrTable() {
        DefaultTableModel df = (DefaultTableModel) tblInvoice.getModel();
        int rc = tblInvoice.getRowCount();
        for (int i = 0; i < rc; i++) {
            df.removeRow(0);
        }
    }

    private void calcAll() {
        DefaultTableModel df = (DefaultTableModel) tblInvoice.getModel();
        int rc = tblInvoice.getRowCount();

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
        double d4 = 0.00;

        d1 = Double.parseDouble(txtInstal.getText());

        d2 = Double.parseDouble(txtdisc.getText());
        if (!txtpay.getText().equals("")) {
            d4 = Double.parseDouble(txtpay.getText());
        }

        txtpayble.setText(Locals.currencyFormat(d1 - d2));
        d3 = Double.parseDouble(txtpayble.getText());
        txtbal.setText(Locals.currencyFormat(d3 - d4));

        double totPayble = Double.parseDouble(txtTotPayble.getText()) - d2;
        if (totPayble <= d4) {
            chkStatus.setSelected(true);
        } else { 
            chkStatus.setSelected(false);
        }

    }

    private void setQue() {
        DefaultTableModel df = (DefaultTableModel) tblPaySchedule.getModel();
        loadScheduleContent();
        int row = tblPaySchedule.getRowCount();
        double rebit = Double.parseDouble(txtdisc.getText());
        double pay = Double.parseDouble(txtpay.getText());
        double balance = 0.00;
        double rebitav = 0.00;
        for (int i = 0; i < row; i++) {
            balance = Double.parseDouble(df.getValueAt(i, 6).toString());
            rebitav = Double.parseDouble(df.getValueAt(i, 3).toString());
            if (pay > 0 || rebit > 0) {
                if (balance > 0) {
                    if (rebit > balance) {
                        df.setValueAt(Locals.currencyFormat(balance + rebitav), i, 3);
                        df.setValueAt("0.00", i, 6);
                        rebit = rebit - balance;
                        balance = 0.00;
                    } else {
                        df.setValueAt(Locals.currencyFormat(rebit + rebitav), i, 3);
                        df.setValueAt(Locals.currencyFormat(balance - rebit), i, 6);
                        rebit = 0.00;
                        balance = balance - rebit;
                    }
                }

            }
        }
        for (int j = 0; j < row; j++) {
            balance = Double.parseDouble(df.getValueAt(j, 6).toString());
            rebitav = Double.parseDouble(df.getValueAt(j, 3).toString());
            if (pay > 0 || rebit > 0) {
                if (balance > 0) {
                    if (pay > balance) {
                        df.setValueAt("0.00", j, 6);
                        pay = pay - balance;
                        df.setValueAt(Locals.currencyFormat(balance), j, 7);
                        balance = 0.00;
                    } else {
                        df.setValueAt(Locals.currencyFormat(balance - pay), j, 6);
                        df.setValueAt(Locals.currencyFormat(pay), j, 7);
                        pay = 0.00;
                        balance = balance - pay;
                    }
                } else {
                    df.setValueAt(Locals.currencyFormat(0.00), j, 7);
                }
            } else {
                df.setValueAt(Locals.currencyFormat(0.00), j, 7);
            }
        }

        if (rebit > 0) {
            JOptionPane.showMessageDialog(null, "Rebit amount exceads the total balance amount...");
            txtdisc.grabFocus();
        }
        if (pay > 0) {
            JOptionPane.showMessageDialog(null, "Pay amount exceads the total balance amount...");
            txtdisc.grabFocus();
        }
    }

    private void clearScheduleTable() {
        DefaultTableModel df = (DefaultTableModel) tblPaySchedule.getModel();
        int row = tblPaySchedule.getRowCount();
        for (int i = 0; i < row; i++) {
            df.removeRow(0);
        }
    }

    private void loadPaymentQue() {
        int i = tblInvoice.getSelectedRow();
        String invoiceNo = tblInvoice.getValueAt(i, 1).toString();
        objps = SERInvoList.getSchedule(invoiceNo, Locals.setDateFormat(txtJVDate.getDate()));
        loadScheduleContent();
        loadPay(invoiceNo);
    }

    private void loadScheduleContent() {
        clearScheduleTable();
        DefaultTableModel df = (DefaultTableModel) tblPaySchedule.getModel();
        boolean b;
        double totale = 0.00;
        double panalty = 0.00;
        double installment = 0.00;
        for (OBJPaymentSchedule objsch : objps) {
            b = false;
            if (jCheckBox2.isSelected() || objsch.getStatus().equals("0")) {
                b = true;
            }
            if (b) {
                df.addRow(new Object[]{objsch,
                    objsch.getDueDate(),
                    objsch.getLatePay(),
                    objsch.getSpDiscount(),
                    objsch.getInstallValue(),
                    objsch.getPaid(),
                    objsch.getBalance(),
                    Locals.currencyFormat(0.00),
                    objsch.getType()});
                
                if (objsch.getType().equals(SettlementType.INSTALMENT)){
                    installment += Double.parseDouble(objsch.getBalance());
                } else if (objsch.getType().equals(SettlementType.PANALTY)) {
                    panalty += Double.parseDouble(objsch.getBalance());
                }
                 totale += Double.parseDouble(objsch.getBalance());
            }

        }
        txtInstallment.setText(Locals.currencyFormat(installment));
        txtPanalty.setText(Locals.currencyFormat(panalty));
        txtTotale.setText(Locals.currencyFormat(totale));
    }

    private void loadPay(String invoNo) {
        DefaultTableModel df = (DefaultTableModel) tblInvoice.getModel();
        int i = tblInvoice.getSelectedRow();
        String invoiceNo = df.getValueAt(i, 1).toString();
        objIP = SERInvoList.getInstall(invoiceNo, Locals.setDateFormat(txtJVDate.getDate()));

        txtInstal.setText(objIP.getTotInstallVal());
        duedate = objIP.getDueDate();
        cid = objIP.getCreditId();
        invoNo = objIP.getInvoNo();
        install = objIP.getInstallNo();

// OLD PANALTY CALCUlATER
//        try {
//        int f = 0;
//        Date dueDate = Locals.toDate(objIP.getDueDate());
//        Date JVDate = txtJVDate.getDate();
//
//            Calendar cal1 = Calendar.getInstance();
//            Calendar cal2 = Calendar.getInstance();
//
//            cal1.setTime(JVDate);
//            cal2.setTime(dueDate);
//
//            boolean b = true;
//
//            if (cal1.after(cal2)) {
//
//                while (b) {
//                    cal2.add(cal2.MONTH, 1);
//                    f++;
//                    if (cal1.before(cal2)) {
//                        b = false;
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            Exp.Handle(ex);
//        }
        int installNo = Integer.parseInt(objIP.getInstallNo());
        double Rebit = Double.parseDouble(txtdisc.getText());
        double pay = Double.parseDouble(txtpay.getText());
        double installVal = 0.00;
        double totInstallVal = 0.00;
        for (OBJPaymentSchedule obj : objps) {
            if (Locals.toDate(obj.getDueDate()).before(Locals.toDate(AccountPackage.company.getWorkingDate()))) {
                installVal = installVal + Double.parseDouble(obj.getBalance());
            }
            totInstallVal = totInstallVal + Double.parseDouble(obj.getBalance());
        }

        double installAmount = Double.parseDouble(objIP.getTotInstallVal());
        double balance = 0.00;
        if (installNo > 1) {
            int in = installNo;
            balance = SERRecAgainstInvoice.getBalance(cid, installNo);
            //String balan = SERCommen.getDescription("payschedule", in + "", "invoNo = '" + objIP.getInvoNo() + "' AND CreditId = '" + objIP.getCreditId() + "' AND InstallNo", "Balance");
            //dbal = Double.parseDouble(balan);
        }

        txtInstal.setText(Locals.currencyFormat(installVal));
        txtpayble.setText(Locals.currencyFormat(installVal - Rebit));
        txtbal.setText(Locals.currencyFormat((installVal - Rebit) - pay));
        txtTotPayble.setText(Locals.currencyFormat((totInstallVal - Rebit) - pay));
//        txtTotPayble.setText(df.getValueAt(i, 6).toString());
        if (!objIP.getStatus().equals("10")) {

            if (installNo <= Integer.parseInt(objIP.getInstall())) {
                datNextPayDate.setDate(Locals.toDate(SERCommen.getDescription("payschedule", installNo + "", "invoNo = '" + objIP.getInvoNo() + "' AND CreditId = '" + objIP.getCreditId() + "' AND InstallNo", "DueDate")));
                txtsp.setText("");
            }
        } else {
            txtsp.setText("Special Discount is Available");
        }
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

    private boolean payment() {
        double due = Double.parseDouble(txtTotPayble.getText()) - Double.parseDouble(txtpay.getText());
        return due >= 0;
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
        cmdArea2 = new javax.swing.JButton();
        txtCostCenter = new javax.swing.JTextField();
        txtTranstype = new javax.swing.JComboBox();
        txtVouCode = new javax.swing.JTextField();
        txtAccountant = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtJVDate = new com.toedter.calendar.JDateChooser();
        txtApproved = new javax.swing.JTextField();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        cmdCust = new javax.swing.JButton();
        txtName = new javax.swing.JTextField();
        txtCustAccCode = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();
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
        txtInstal = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        txtpayble = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
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
        datNextPayDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtsp = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPaySchedule = new javax.swing.JTable();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        jLabel17 = new javax.swing.JLabel();
        txtInstallment = new javax.swing.JFormattedTextField();
        txtPanalty = new javax.swing.JFormattedTextField();
        txtTotale = new javax.swing.JFormattedTextField();
        chkStatus = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtTotPayble = new javax.swing.JFormattedTextField();
        txtLoanNo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        txtDocumentNo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();

        setClosable(true);
        setTitle("Recipt Voucher (Against Invoice)");

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

        cmdArea2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdArea2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdArea2.setContentAreaFilled(false);
        cmdArea2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdArea2ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(cmdArea2);
        cmdArea2.setBounds(53, 0, 20, 20);

        txtCostCenter.setEditable(false);
        txtCostCenter.setBackground(new java.awt.Color(255, 255, 241));
        txtCostCenter.setForeground(new java.awt.Color(153, 0, 0));
        txtCostCenter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtCostCenter);
        txtCostCenter.setBounds(75, 0, 150, 18);

        txtTranstype.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        jLabel16.setText("Transaction Type");

        jLabel5.setText("Narration");

        tblInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Invoice No", "Inv. Date", "Original Amt.", "Paied Amt.", "Amt. Owings", "Invoice Bal.", "Instal Amount", "PayDate", "Loan No"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInvoice.setGridColor(new java.awt.Color(204, 204, 204));
        tblInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInvoiceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblInvoice);
        if (tblInvoice.getColumnModel().getColumnCount() > 0) {
            tblInvoice.getColumnModel().getColumn(0).setPreferredWidth(2);
            tblInvoice.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblInvoice.getColumnModel().getColumn(2).setPreferredWidth(75);
            tblInvoice.getColumnModel().getColumn(3).setPreferredWidth(95);
            tblInvoice.getColumnModel().getColumn(6).setPreferredWidth(95);
            tblInvoice.getColumnModel().getColumn(7).setPreferredWidth(75);
        }

        jLabel3.setText("J.V. Date");

        jLabel8.setText("Custo.  A/C");

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
        txtTotOwing.setBounds(230, 0, 80, 20);

        txtTotPaied.setBackground(new java.awt.Color(204, 204, 255));
        txtTotPaied.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotPaied.setForeground(new java.awt.Color(153, 0, 0));
        txtTotPaied.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotPaied.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotPaied.setText("0.00");
        jLayeredPane1.add(txtTotPaied);
        txtTotPaied.setBounds(310, 0, 80, 20);

        txtTotInvoBalance.setBackground(new java.awt.Color(204, 204, 255));
        txtTotInvoBalance.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotInvoBalance.setForeground(new java.awt.Color(153, 0, 0));
        txtTotInvoBalance.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotInvoBalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotInvoBalance.setText("0.00");
        jLayeredPane1.add(txtTotInvoBalance);
        txtTotInvoBalance.setBounds(390, 0, 100, 20);

        txtInstalAmount.setBackground(new java.awt.Color(204, 204, 255));
        txtInstalAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtInstalAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtInstalAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtInstalAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtInstalAmount.setText("0.00");
        jLayeredPane1.add(txtInstalAmount);
        txtInstalAmount.setBounds(490, 0, 80, 20);

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

        txtpay.setBackground(java.awt.SystemColor.info);
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
        txtPayTermCode.setBounds(0, 0, 60, 18);

        cmdArea3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdArea3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdArea3.setContentAreaFilled(false);
        cmdArea3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdArea3ActionPerformed(evt);
            }
        });
        jLayeredPane4.add(cmdArea3);
        cmdArea3.setBounds(60, 0, 20, 20);

        txtRef.setEditable(false);
        txtRef.setBackground(new java.awt.Color(255, 255, 241));
        txtRef.setForeground(new java.awt.Color(153, 0, 0));
        txtRef.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtRef);
        txtRef.setBounds(80, 0, 110, 18);

        jLabel11.setText("Pay Mod");

        jLabel22.setBackground(new java.awt.Color(238, 238, 228));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Rebit");
        jLabel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel22.setOpaque(true);

        txtdisc.setBackground(java.awt.SystemColor.info);
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

        datNextPayDate.setDateFormatString("yyyy-MM-dd");

        jLabel2.setText("Next Pay");

        txtsp.setForeground(new java.awt.Color(204, 0, 0));

        tblPaySchedule.setModel(new javax.swing.table.DefaultTableModel(
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
                "Index", "Due Date", "Late Pay", "Rebit", "Tot. Payble", "Paid", "Balance", "Settlment", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPaySchedule.setGridColor(new java.awt.Color(204, 204, 204));
        tblPaySchedule.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPayScheduleMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPaySchedule);
        if (tblPaySchedule.getColumnModel().getColumnCount() > 0) {
            tblPaySchedule.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblPaySchedule.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblPaySchedule.getColumnModel().getColumn(2).setPreferredWidth(95);
            tblPaySchedule.getColumnModel().getColumn(3).setPreferredWidth(95);
            tblPaySchedule.getColumnModel().getColumn(4).setPreferredWidth(95);
            tblPaySchedule.getColumnModel().getColumn(5).setPreferredWidth(95);
        }

        jLabel17.setBackground(new java.awt.Color(238, 238, 228));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Totale");
        jLabel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel17.setOpaque(true);
        jLayeredPane5.add(jLabel17);
        jLabel17.setBounds(520, 0, 110, 20);

        txtInstallment.setBackground(new java.awt.Color(204, 204, 255));
        txtInstallment.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtInstallment.setForeground(new java.awt.Color(153, 0, 0));
        txtInstallment.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtInstallment.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtInstallment.setText("0.00");
        jLayeredPane5.add(txtInstallment);
        txtInstallment.setBounds(230, 0, 90, 20);

        txtPanalty.setBackground(new java.awt.Color(204, 204, 255));
        txtPanalty.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPanalty.setForeground(new java.awt.Color(153, 0, 0));
        txtPanalty.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtPanalty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPanalty.setText("0.00");
        jLayeredPane5.add(txtPanalty);
        txtPanalty.setBounds(430, 0, 90, 20);

        txtTotale.setBackground(new java.awt.Color(204, 204, 255));
        txtTotale.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotale.setForeground(new java.awt.Color(153, 0, 0));
        txtTotale.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotale.setText("0.00");
        jLayeredPane5.add(txtTotale);
        txtTotale.setBounds(630, 0, 90, 20);

        chkStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chkStatus.setForeground(new java.awt.Color(204, 0, 0));
        chkStatus.setText("Paid");
        jLayeredPane5.add(chkStatus);
        chkStatus.setBounds(720, 0, 55, 25);

        jLabel19.setBackground(new java.awt.Color(238, 238, 228));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Installment");
        jLabel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel19.setOpaque(true);
        jLayeredPane5.add(jLabel19);
        jLabel19.setBounds(120, 0, 110, 20);

        jLabel24.setBackground(new java.awt.Color(238, 238, 228));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Panalty");
        jLabel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel24.setOpaque(true);
        jLayeredPane5.add(jLabel24);
        jLabel24.setBounds(320, 0, 110, 20);

        jLabel23.setBackground(new java.awt.Color(238, 238, 228));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Invo. Credit");
        jLabel23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel23.setOpaque(true);

        txtTotPayble.setEditable(false);
        txtTotPayble.setBackground(new java.awt.Color(204, 204, 255));
        txtTotPayble.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotPayble.setForeground(new java.awt.Color(153, 0, 0));
        txtTotPayble.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotPayble.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotPayble.setText("0.00");

        txtLoanNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtLoanNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoanNoActionPerformed(evt);
            }
        });

        jLabel12.setText("Loan No");

        jCheckBox2.setText("View History");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jLabel13.setText("Document No");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDocumentNo)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtLoanNo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtVouCode, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtJVDate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTranstype, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addComponent(jScrollPane2)
            .addComponent(jScrollPane1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotPayble))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtsp, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtdisc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtInstal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(datNextPayDate, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLayeredPane4)))
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
                                .addComponent(txtpay, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(8, 8, 8)
                        .addComponent(txtAccountant, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtApproved, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLayeredPane5, javax.swing.GroupLayout.Alignment.TRAILING))
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
                        .addComponent(jLabel3)
                        .addComponent(jLabel16)
                        .addComponent(txtTranstype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtJVDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLayeredPane2)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtLoanNo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                        .addComponent(txtDocumentNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNarration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                        .addComponent(jLayeredPane3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInstal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTotPayble, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtdisc, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtsp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLayeredPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                            .addComponent(datNextPayDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtAccountant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtApproved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel7)))
                .addGap(2, 2, 2))
        );

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Print Receipt");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdPrintChq, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdGetInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
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
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cmdPrintChqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPrintChqActionPerformed
//    try {
//        new PrintVou("29", "1", "000001").setVisible(true);
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

    private void txtLoanNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoanNoActionPerformed
        loadLoan();
        loadInvoice();
    }//GEN-LAST:event_txtLoanNoActionPerformed

    private void cmdCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCustActionPerformed
        loadCustomer();
    }//GEN-LAST:event_cmdCustActionPerformed

    private void tblInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInvoiceMouseClicked
        if (tblInvoice.getSelectedRow() >= 0) {
            loadPaymentQue();
        }
    }//GEN-LAST:event_tblInvoiceMouseClicked

    private void cmdGetInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGetInvoiceActionPerformed
        int i = tblInvoice.getSelectedRow();
        if (i >= 0) {
            mainApp.MainFrame.f = 4;
            mainApp.MainFrame.val = tblInvoice.getValueAt(i, 1).toString();
        }
    }//GEN-LAST:event_cmdGetInvoiceActionPerformed

    private void txtdiscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdiscActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdiscActionPerformed

    private void txtdiscKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdiscKeyReleased
        calcPayment();
        setQue();
    }//GEN-LAST:event_txtdiscKeyReleased

    private void txtpayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpayKeyReleased
        if (Double.parseDouble(txtpay.getText()) > 0) {
            calcPayment();
            setQue();
        } else {
            JOptionPane.showMessageDialog(null, "Please check the payment value.");
        }
    }//GEN-LAST:event_txtpayKeyReleased

    private void tblPayScheduleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPayScheduleMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPayScheduleMouseClicked

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        loadScheduleContent();
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void cmdArea2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdArea2ActionPerformed
        getCost();
    }//GEN-LAST:event_cmdArea2ActionPerformed

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
    private javax.swing.JCheckBox chkStatus;
    private javax.swing.JButton cmdArea2;
    private javax.swing.JButton cmdArea3;
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
    private com.toedter.calendar.JDateChooser datNextPayDate;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblInvoice;
    private javax.swing.JTable tblPaySchedule;
    private javax.swing.JTextField txtAccountant;
    private javax.swing.JTextField txtApproved;
    private javax.swing.JTextField txtCostCenter;
    private javax.swing.JTextField txtCostCode;
    private javax.swing.JTextField txtCustAccCode;
    private javax.swing.JTextField txtDocumentNo;
    private javax.swing.JFormattedTextField txtInstal;
    private javax.swing.JFormattedTextField txtInstalAmount;
    private javax.swing.JFormattedTextField txtInstallment;
    private com.toedter.calendar.JDateChooser txtJVDate;
    private javax.swing.JTextField txtLoanNo;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNarration;
    private javax.swing.JFormattedTextField txtPanalty;
    private javax.swing.JTextField txtPayTermCode;
    private javax.swing.JTextField txtRef;
    private javax.swing.JFormattedTextField txtTotInvoBalance;
    private javax.swing.JFormattedTextField txtTotOwing;
    private javax.swing.JFormattedTextField txtTotPaied;
    private javax.swing.JFormattedTextField txtTotPayble;
    private javax.swing.JFormattedTextField txtTotale;
    private javax.swing.JComboBox txtTranstype;
    private javax.swing.JTextField txtVouCode;
    private javax.swing.JFormattedTextField txtbal;
    private javax.swing.JFormattedTextField txtdisc;
    private javax.swing.JFormattedTextField txtpay;
    private javax.swing.JFormattedTextField txtpayble;
    private javax.swing.JLabel txtsp;
    // End of variables declaration//GEN-END:variables
    private OBJRecAgainstInvoice obj;
    private OBJAgInvo objin;

    private OBJInstallPay objIP;
    private ArrayList<OBJInstallPay> objIPList;
    private ArrayList<OBJRecAgainstInvoice> obja;
    private ArrayList<OBJInvoList> obji;
    private ArrayList<OBJPaymentSchedule> objps;

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
