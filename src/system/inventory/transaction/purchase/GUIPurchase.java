/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUISalesInvoice.java
 *
 * Created on Jul 22, 2013, 9:30:08 PM
 */
package system.inventory.transaction.purchase;

import core.Exp;
import core.Locals;
import core.system_transaction.SystemSetting;
import core.system_transaction.TransactionType;
import core.system_transaction.account_trans.AccountSetting;
import core.system_transaction.account_trans.AccountSettingObject;
import core.system_transaction.account_trans.AccountTransStatus;
import core.system_transaction.account_trans.AccountTransactionDescription;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.ItemTransactionStatus;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.SERCommen;
import system.accounts.transaction.cheque.OBJCheque;
import core.system_transaction.payment.multy_payment.MultiPayOption;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.payment.PaymentSetting;
import core.system_transaction.payment.PaymentSettingObject;
import core.system_transaction.payment.multy_payment.VoucherType;
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.transaction.OBJTransactionHistory;
import core.system_transaction.transaction.TransactionStatus;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import mainApp.findwindow.GUIFindWindow;
import system.inventory.transaction.item_list.ItemList;
import system.inventory.transaction.sales.paymentplan.OBJInvPayPlan;
import system.inventory.transaction.sales.paymentplan.SERInvPayPlan;

/**
 *
 * @author dell
 */
public class GUIPurchase extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUISalesInvoice
     */
    public GUIPurchase() {
        initComponents();
        initOthers();
    }

    public GUIPurchase(String val) {
        initComponents();
        initOthers();
        txtCusCode.setText(val);
        txtCusName.setText(SERCommen.getDescription("customer", val, "AccCode", "PrintName"));
    }

    private void doSave() throws SQLException {
        String code = txtInvon.getText();
        String name = txtCusCode.getText();

        if (code.isEmpty() || name.isEmpty() || txtCrPayDate.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Blank entry not allowed", "Warning !", JOptionPane.OK_OPTION);
        } else {
            String s = "";
            if (Act == 1) {
                s = "Save";
            } else {
                s = "Update";
            }
            int q = JOptionPane.showConfirmDialog(null, "Are you sure you want to " + s + " ?", s, JOptionPane.YES_NO_OPTION);
            if (q == JOptionPane.YES_OPTION) {
                String[] fccode = txtCurrencyCode.getSelectedItem().toString().split(":");
                obj = new OBJPurchase(
                        txtInvon.getText(),
                        core.Locals.setDateFormat(txtInvDate.getDate()),
                        txtCusCode.getText(),
                        txtCostCode.getText(),
                        "",
                        fccode[0],
                        txtFCRate.getText(),
                        txtrepCode.getText(),
                        txtStoreCode.getText(),
                        txtAreaCode.getText(),
                        txtPayTermCode.getText(),
                        txtPrepBy.getText(),
                        txtApproBy.getText(),
                        txtRemark.getText(),
                        txtGrossAmount.getText(),
                        txtDiscount.getText(),
                        txtDiscountRate.getText(),
                        txtOther.getText(),
                        txtNetAmount.getText(),
                        txtPay.getText(),
                        txtDue.getText(),
                        accountpackage.AccountPackage.user,
                        txtInvoType.getSelectedIndex() + "",
                        txtRef.getText(),
                        txtpinvNo.getText());

                try {
                    obja = new ArrayList<OBJPurchase>();
                    int row = jTable1.getRowCount();
                    for (int i = 0; i < row; i++) {
                        obj1 = new OBJPurchase(
                                txtInvon.getText(),
                                jTable1.getValueAt(i, 1).toString(),
                                jTable1.getValueAt(i, 2).toString(),
                                jTable1.getValueAt(i, 4).toString(),
                                jTable1.getValueAt(i, 6).toString(),
                                jTable1.getValueAt(i, 7).toString(),
                                jTable1.getValueAt(i, 3).toString(),
                                jTable1.getValueAt(i, 8).toString(),
                                jTable1.getValueAt(i, 5).toString(),
                                jTable1.getValueAt(i, 9).toString(),
                                txtStoreCode.getText(),
                                jTable1.getValueAt(i, 10).toString(),
                                jTable1.getValueAt(i, 11).toString());

                        obja.add(obj1);
                    }
                } catch (Exception e) {
                    core.Exp.Handle(e);
                }
                /**
                 * ***********************************************
                 */
                if (!txtPayTermCode.getText().equals("INVO") && Double.parseDouble(txtPay.getText()) > 0.00) {
                    paymentsInfo = new ArrayList<>();
                    getPayment();
                } else {
                    paymentsInfo = null;
                }
                accountTransaction();
                transaction();
                itemTransaction();
                /**
                 * ***********************************************
                 */
                if (saveCredit() && (payment != null || !txtPayTermCode.getText().equals("INVO"))) {
                    if (SERPurchase.saveAll(obj, obja, objpp, paymentsInfo, cheques, transaction, payment, paymentsInfo, accountTranses, itemTransactions, Act)) {

                        //saveCredit();
                        // new GUIInvPayPlan(null, true, txtInvon.getText()).setVisible(true);
                        // }
                        if (jCheckBox2.isSelected()) {
                            try {
                                new PrintGRN(txtInvon.getText()).setVisible(true);
                            } catch (Exception ex) {
                                Exp.Handle(ex);
                            }
                        }
                        cheques = null;
                        transaction = null;
                        payment = null;
                        paymentsInfo = null;
                        accountTranses = null;
                        itemTransactions = null;

                        MultiPayOption.infos = null;
                        MultiPayOption.cheques = null;
                        MultiPayOption.cheque = null;
                        doNew();
                        if (Act == 1) {
                            setMode(DEFAULT_STATUS);
                        } else {
                            setMode(NEW_STATUS);
                            Act = 1;
                        }
                        JOptionPane.showMessageDialog(null, "Save Successfull...");
                    } else {
                        JOptionPane.showMessageDialog(null, "Saving fail...");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Payment Type");
                }
            }
        }
    }

    private void itemTransaction() {
        itemTransaction = new OBJItemTransaction();

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            itemTransactions = new ArrayList<>();
            itemTransaction.setTransactionType(TransactionType.GRN);
            itemTransaction.setTransactionDate(Locals.setDateFormat(txtInvDate.getDate()));
            itemTransaction.setItem(jTable1.getValueAt(i, 1).toString());
            itemTransaction.setStore(txtStoreCode.getText());
            itemTransaction.setDebitQty(jTable1.getValueAt(i, 4).toString());
            itemTransaction.setCreditQty("0.00");
            itemTransaction.setDebitPrice(jTable1.getValueAt(i, 8).toString());
            itemTransaction.setCreditPrice("0.00");
            itemTransaction.setStatus(ItemTransactionStatus.ACTIVE);

            itemTransactions.add(itemTransaction);
        }
    }

    private void transaction() {
        // Transaction Object
        transaction = new OBJTransaction();
        transaction.setTransactionDate(Locals.setDateFormat(txtInvDate.getDate()));
        transaction.setTransactionType(TransactionType.GRN);
        transaction.setReferanceNo(txtInvon.getText());
        transaction.setDocumentNo(txtpinvNo.getText());
        transaction.setLoan(null);
        transaction.setCostCode(txtCostCode.getText());
        transaction.setCustomerCode(txtCusCode.getText());
        transaction.setNote(txtRemark.getText());
        transaction.setStatus(TransactionStatus.ACTIVE);

    }

    private boolean accountTransaction() {
        accountTranses = new ArrayList<>();

        // Account Transaction
        /**
         * ********** Account Transaction For Stock ********** *
         */
        // Debit Account Trans
// Stock Accounts
        OBJAccountTrans accountTrans = new OBJAccountTrans();
        accountTrans.setTransactionDate(Locals.setDateFormat(txtInvDate.getDate()));
        accountTrans.setCostCode(txtCostCode.getText());
        accountTrans.setAccountSetting(null);
        accountTrans.setDescription(AccountTransactionDescription.STOCK_IN_GRN + " For - GRN No : " + txtInvon.getText());
        accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.STOCK_DEBIT_GRN)).getAccount());
        accountTrans.setCreditAmount("0.00");
        accountTrans.setDebitAmount(txtNetAmount.getText());
        accountTrans.setTransactionType(TransactionType.GRN);
        accountTrans.setType("AUTO");
        accountTrans.setStatus(AccountTransStatus.ACTIVE);
        accountTranses.add(accountTrans);
// End of Stock Accounts

        // Credit Account Trans
// Loan Payble Liability
        accountTrans = new OBJAccountTrans();
        accountTrans.setTransactionDate(Locals.setDateFormat(txtInvDate.getDate()));
        accountTrans.setCostCode(txtCostCode.getText());
        accountTrans.setAccountSetting(null);
        accountTrans.setDescription(AccountTransactionDescription.GRN_PAYBLE + " For - GRN No : " + txtInvon.getText());
        accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.SUPLIER_PAYBLE_CREDIT)).getAccount());
        accountTrans.setCreditAmount(txtNetAmount.getText());
        accountTrans.setDebitAmount("0.00");
        accountTrans.setTransactionType(TransactionType.GRN);
        accountTrans.setType("AUTO");
        accountTrans.setStatus(AccountTransStatus.ACTIVE);
        accountTranses.add(accountTrans);

        if (Double.parseDouble(txtDiscount.getText()) > 0.00) {
            accountTrans = new OBJAccountTrans();
            accountTrans.setTransactionDate(Locals.setDateFormat(txtInvDate.getDate()));
            accountTrans.setCostCode(txtCostCode.getText());
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(AccountTransactionDescription.PURCHESING_DISCOUNT + " For - GRN No : " + txtInvon.getText());
            accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.PURCHESING_DISCOUNT_INCOME)).getAccount());
            accountTrans.setCreditAmount(txtNetAmount.getText());
            accountTrans.setDebitAmount("0.00");
            accountTrans.setTransactionType(TransactionType.GRN);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }

// End of Loan Payble Liability
        /**
         * ************* End Of Account Transaction For Stock * *************
         */
        /**
         * ************* Account Transaction For Payment * *************
         */
        if (paymentsInfo != null) {
            // Debit Account Trans
// Accounts Payble
            accountTrans = new OBJAccountTrans();
            accountTrans.setTransactionDate(Locals.setDateFormat(txtInvDate.getDate()));
            accountTrans.setCostCode(txtCostCode.getText());
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(AccountTransactionDescription.GRN_AMOUNT + " For - GRN No : " + txtInvon.getText());
            accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.SUPLIER_PAYBLE_DEBIT)).getAccount());
            accountTrans.setCreditAmount("0.00");
            accountTrans.setDebitAmount(txtPay.getText());
            accountTrans.setTransactionType(TransactionType.GRN);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
// End of Accounts Payble

            // Credit Account Trans
// Payment
            for (OBJPaymentInfo info : paymentsInfo) {
                accountTrans = new OBJAccountTrans();
                accountTrans.setTransactionDate(Locals.setDateFormat(txtInvDate.getDate()));
                accountTrans.setCostCode(txtCostCode.getText());
                accountTrans.setAccountSetting(null);

                switch (info.getPaymentSetting()) {
                    case PaymentSetting.GRN_CASH:
                        accountTrans.setDescription(AccountTransactionDescription.GRN_CASH_PAYMENT + "For - GRN No : " + txtInvon.getText());
                        accountTrans.setAccount(((PaymentSettingObject) SystemSetting.getPaymentSeting(PaymentSetting.GRN_CASH)).getAccount());
                        accountTrans.setCreditAmount(info.getAmount());
                        accountTrans.setDebitAmount("0.00");
                        break;
                    case PaymentSetting.GRN_CHEQUE:
                        accountTrans.setDescription(AccountTransactionDescription.GRN_CHEQUE_PAYMENT + "For - GRN No : " + txtInvon.getText());
                        accountTrans.setAccount(((PaymentSettingObject) SystemSetting.getPaymentSeting(PaymentSetting.GRN_CHEQUE)).getAccount());
                        accountTrans.setCreditAmount(info.getAmount());
                        accountTrans.setDebitAmount("0.00");
                        break;
                }

                accountTrans.setTransactionType(TransactionType.GRN);
                accountTrans.setType("AUTO");
                accountTrans.setStatus(AccountTransStatus.ACTIVE);
                accountTranses.add(accountTrans);
            }
        }
// End of Payment
        /**
         * ************* End Of Account Transaction For Payment *************
         */
        return true;
    }

    private void getPayment() {

        new MultiPayOption(null, true, txtPay.getText(), paymentsInfo, VoucherType.GRN, cheques).setVisible(true);
// Payment Information
        if (MultiPayOption.infos != null) {
            paymentsInfo = MultiPayOption.infos;
            MultiPayOption.infos = null;
            ArrayList<OBJCheque> cheques = MultiPayOption.cheques;
            this.cheques = null;
            this.cheques = new ArrayList<>();
            MultiPayOption.cheques = null;

// End Of Payment Infor 
// Payment 
            payment = new OBJPayment();
            payment.setTransactionDate(Locals.setDateFormat(txtInvDate.getDate()));
            payment.setCostCode(txtCostCode.getText());
            payment.setCustomerCode(txtCusCode.getText());
            payment.setCashireSession(null);
            payment.setAmount(txtPay.getText());
            payment.setTransaction("");
            payment.setTransactionType(TransactionType.GRN);
            payment.setStatus(TransactionStatus.ACTIVE);

// End Of Payment    
// Cheque Registry Entry
            if (cheques != null) {
                this.cheques = null;
                this.cheques = new ArrayList<>();
                for (OBJCheque oBJCheque : cheques) {
                    cheque = new OBJCheque();
                    cheque.setCustCode(txtCusCode.getText());
                    cheque.setBankAccount(oBJCheque.getBankAccount());
                    cheque.setInvoNo(txtInvon.getText());
                    cheque.setChqeNo(oBJCheque.getChqeNo());
                    cheque.setGetDate(Locals.setDateFormat(txtInvDate.getDate()));
                    cheque.setRDate(oBJCheque.getRDate());
                    cheque.setAmount(oBJCheque.getAmount());
                    cheque.setBank(oBJCheque.getBank());
                    cheque.setBankAccount(oBJCheque.getBankAccount());
                    cheque.setFCCode("001");
                    cheque.setFCRate("1.00");
                    cheque.setRemarks(oBJCheque.getRemarks());
                    cheque.setType(TransactionType.GRN);
                    cheque.setBankCharge("0.00");
                    cheque.setInterestCharge("0.00");
                    cheque.setNet(oBJCheque.getAmount());
                    cheque.setStatus("0");
                    this.cheques.add(cheque);
                }
            }
//End Of Cheque Registry
        }
    }

    private boolean saveCredit() {

        try {
            objpp = new OBJInvPayPlan(
                    SERInvPayPlan.getGRNID(),
                    txtInvon.getText(),
                    "0",
                    "1",
                    "0.00",
                    txtDue.getText(),
                    "0.00",
                    "0.00",
                    txtNetAmount.getText(),
                    txtPay.getText(),
                    Locals.setDateFormat(txtInvDate.getDate()),
                    Locals.setDateFormat(txtCrPayDate.getDate()),
                    txtInvoType.getSelectedIndex() + "",
                    txtDue.getText(),
                    "",
                    "",
                    txtCusCode.getText(),
                    "0.00",
                    "0.00");

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void doNew() {
        setID();
        //txtCode.setText("");
        clearAll();
        addNewRow();
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
    }

    private void clearAll() {
        txtInvDate.setDate(Calendar.getInstance().getTime());
        txtCusCode.setText("");
        txtCusName.setText("");
        txtCurrencyCode.setSelectedIndex(0);
        //  txtFCRate.setText("");
        txtCostCode.setText("");
        txtCostCenter.setText("");
        txtrepCode.setText("");
        txtrep.setText("");
        txtStoreCode.setText("");
        txtStore.setText("");
        txtAreaCode.setText("");
        txtArea.setText("");
        txtCrPayDate.setDate(Calendar.getInstance().getTime());
        txtPayTermCode.setText("");
        txtGrossAmount.setText("0.00");
        txtDiscount.setText("0.00");
        txtDiscountRate.setText("0.00");
        txtOther.setText("0.00");
        txtNetAmount.setText("0.00");
        txtPay.setText("0.00");
        txtDue.setText("0.00");
        txtApproBy.setText("");
        txtPrepBy.setText("");
        txtRemark.setText("");
        txtRef.setText("");
        txtpinvNo.setText("");
        txtInvoType.setSelectedIndex(0);
        jCheckBox1.setSelected(false);
        chBoxAct();

        clrTable();
    }

    private void chBoxAct() {
        if (jCheckBox1.isSelected()) {
            int q = JOptionPane.showConfirmDialog(this, "Are you sure you want to perform this?\n Discount colenm will set 0.00.", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (q == 0) {
                txtDiscountRate.setEditable(true);
                txtDiscount.setEditable(true);
                txtDiscountRate.setText("0.00");
                txtDiscount.setText("0.00");
                txtDiscount.setBackground(new java.awt.Color(255, 255, 255));
                txtDiscountRate.setBackground(new java.awt.Color(255, 255, 255));
                setDisc(true);
                calcDue();
            }
        } else {
            txtDiscount.setBackground(new java.awt.Color(249, 249, 229));
            txtDiscountRate.setBackground(new java.awt.Color(249, 249, 229));
            txtDiscountRate.setText("0.00");
            txtDiscountRate.setEditable(!true);
            txtDiscount.setEditable(!true);
            txtDiscount.setText("0.00");
            setDisc(false);
            calcDue();
        }
    }

    private void addNewRow() {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getRowCount();
        boolean isEmptyRowAvailable = false;
        if (row > 0) {
            isEmptyRowAvailable = checkEmptyRow(row - 1);
        }
        if (isEmptyRowAvailable || row == 0) {
            dt.addRow(new Object[]{row + 1, "", "", "", "", "", "", "", "", "", "", ""});
        } else {
            row--;
        }
        if (jTable1.getRowCount() > 1) {
            jTable1.editCellAt(row, 1);
        }
//        jTable1.setCellSelectionEnabled(true);
        jTable1.setRowSelectionInterval(row, row);
        jTextField1.grabFocus();

        if (jTable1.getRowCount() > 1) {
            loadItem();
        }

    }

    private boolean checkEmptyRow(int i) {
        int row = jTable1.getRowCount();
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        boolean b;
//        for (int i = 0; i < row; i++) {
        if (df.getValueAt(i, 1).toString() == null || df.getValueAt(i, 1).toString().equals("") || df.getValueAt(i, 1).toString().isEmpty()) {
            b = false;
        } else if (df.getValueAt(i, 2).toString() == null || df.getValueAt(i, 2).toString().equals("") || df.getValueAt(i, 2).toString().isEmpty()) {
            b = false;
        } else if (df.getValueAt(i, 3).toString() == null || df.getValueAt(i, 3).toString().equals("") || df.getValueAt(i, 3).toString().isEmpty()) {
            b = false;
        } else if (df.getValueAt(i, 4).toString() == null || df.getValueAt(i, 4).toString().equals("") || df.getValueAt(i, 4).toString().isEmpty()) {
            b = false;
        } else if (df.getValueAt(i, 5).toString() == null || df.getValueAt(i, 5).toString().equals("") || df.getValueAt(i, 5).toString().isEmpty()) {
            b = false;
        } else if (df.getValueAt(i, 6).toString() == null || df.getValueAt(i, 6).toString().equals("") || df.getValueAt(i, 6).toString().isEmpty()) {
            b = false;
        } else if (df.getValueAt(i, 7).toString() == null || df.getValueAt(i, 7).toString().equals("") || df.getValueAt(i, 7).toString().isEmpty()) {
            b = false;
        } else {
            b = df.getValueAt(i, 8).toString() != null && !df.getValueAt(i, 8).toString().equals("") && !df.getValueAt(i, 8).toString().isEmpty();
        }
//                }
        return b;
    }

    private void clrTable() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int rc = jTable1.getRowCount();
        for (int i = 0; i < rc; i++) {
            df.removeRow(0);
        }
    }

    private void doEdit() {
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
        txtInvon.setEnabled(false);
    }

    private void doCancel() {
        String code = txtInvon.getText();
        obja = SERPurchase.InvoHistory(code);
        boolean b = SERPurchase.canselGRN(code, obja, txtStoreCode.getText());
        if (b) {
            indexCount = SERPurchase.getIndex();
            if (Index < indexCount) {
                getNavi();
            } else {
                Index--;
                getNavi();
            }
            JOptionPane.showMessageDialog(null, "Cancel sucsesful..");
        } else {
            JOptionPane.showMessageDialog(null, "Cancel fail..");
        }

    }

    private void doView() {
        clearAll();
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
        txtInvDate.setEnabled(state);
        txtCusCode.setEnabled(state);
        txtrepCode.setEnabled(state);
        txtCostCode.setEnabled(state);
        txtStoreCode.setEnabled(state);
        txtAreaCode.setEnabled(state);
        txtCurrencyCode.setEnabled(state);
        txtFCRate.setEnabled(state);
        txtPayTermCode.setEnabled(state);
        txtInvDate.setEnabled(state);
        txtPrepBy.setEnabled(state);
        txtApproBy.setEnabled(state);
        txtRemark.setEnabled(state);
        txtGrossAmount.setEnabled(state);
        txtDiscount.setEnabled(state);
        txtDiscountRate.setEnabled(state);
        txtOther.setEnabled(state);
        txtNetAmount.setEnabled(state);
        txtPay.setEnabled(state);
        txtDue.setEnabled(state);
        txtInvoType.setEnabled(state);
        txtpinvNo.setEnabled(state);
        jCheckBox1.setEnabled(state);
        jTable1.setEnabled(state);
        jTable1.setFocusable(state);
        if (state) {
            jTable1.setBackground(new java.awt.Color(255, 255, 255));
        } else {
            jTable1.setBackground(new java.awt.Color(249, 249, 229));
        }
        /*
         * -----******( )*****-----
         */

        cmdCustomer.setEnabled(state);
        //cmdCusAddress.setEnabled(state);
        cmdCostCenter.setEnabled(state);
        cmdPayTerm.setEnabled(state);
        cmdRep.setEnabled(state);
        cmdStore.setEnabled(state);
        cmdArea.setEnabled(state);
    }

    private void getNavi() {
        obj = SERPurchase.getNavi(Index);
        obja = SERPurchase.InvoHistory(obj.getInvoNo());
        setContent(obj);
        setContentHistory(obja);
    }

    private void setContent(OBJPurchase obj) {
        txtInvon.setText(obj.getInvoNo());
        txtInvDate.setDate(Locals.toDate(obj.getInvoDate()));
        txtCusCode.setText(obj.getCussCode());
        txtCusName.setText(SERCommen.getDescription("customer", txtCusCode.getText(), "AccCode", "CustName"));
        txtrepCode.setText(obj.getRepCode());
        txtrep.setText(SERCommen.getDescription("salesrep", txtrepCode.getText(), "RepCode", "RepName"));
        txtCostCode.setText(obj.getCostCode());
        txtCostCenter.setText(SERCommen.getDescription("costcenter", txtCostCode.getText(), "CostCode", "Description"));
        txtStoreCode.setText(obj.getStoreCode());
        txtStore.setText(SERCommen.getDescription("store", txtStoreCode.getText(), "StoreCode", "Description"));
        txtAreaCode.setText(obj.getAreaCode());
        txtArea.setText(SERCommen.getDescription("area", txtAreaCode.getText(), "AreaCode", "Description"));

        txtCurrencyCode.setSelectedItem(obj.getFCCode());
        txtFCRate.setText(obj.getFCRate());
        txtPayTermCode.setText(obj.getPaymentTerms());
        txtPrepBy.setText(obj.getPrepBy());
        txtApproBy.setText(obj.getApproBy());
        txtRemark.setText(obj.getRemarks());
        txtGrossAmount.setText(obj.getGrossAmount());
        txtDiscount.setText(obj.getTotalDiscount());
        txtDiscountRate.setText(obj.getDiscountRate());
        txtPay.setText(obj.getPayAmount());
        txtpinvNo.setText(obj.getPInvoNo());
        if (obj.getDiscountRate().equals("0.00")) {
            jCheckBox1.setSelected(false);
        } else {
            jCheckBox1.setSelected(!false);

        }
        txtNetAmount.setText(obj.getNetAmount());
        txtRef.setText(obj.getRef());
    }

    private void setContentHistory(ArrayList<OBJPurchase> obja) {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        clrTable();
        int i = 1;
        if (!obja.isEmpty()) {
            for (OBJPurchase objq : obja) {
                dt.addRow(new Object[]{i, objq.getItemCode(), objq.getItemDescription(), objq.getUnitCode(), objq.getQuantity(), "0", objq.getRate(), "0.00", objq.getNet(), objq.getSn(), objq.getSellingRate(), objq.getMinSellingRate()});
                i++;
            }
        }
        jTable1.setEnabled(!true);
    }

    private void doFind() {
        new GUIFindWindow(null, true, "InvoiceHeader", "InvoNo", "CustCode").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void serch(String code) {
        obj = SERPurchase.serch(code);
        obja = SERPurchase.InvoHistory(obj.getInvoNo());
        setContent(obj);
        setContentHistory(obja);
    }

    private void setID() {
        txtInvon.setText(SERPurchase.getID());
    }

    private void getArea() {
        new GUIFindWindow(null, true, "Area", "AreaCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtAreaCode.setText(code);
        txtArea.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void doDeleteRow() {
        int selRow = jTable1.getSelectedRow();
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        dt.removeRow(selRow);
        jTable1.setFocusable(true);
        int i = jTable1.getRowCount();
        if (i == 0) {
            addNewRow();
        }
    }

    private void getCustomer() {
        new GUIFindWindow(null, true, "Customer", "AccCode", "CustName", "V").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtCusCode.setText(code);
        txtCusName.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void getCrAcc() {
        new GUIFindWindow(null, true, "account", "AccCode", "AccName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void getSalesRep() {
        new GUIFindWindow(null, true, "SalesRep", "RepCode", "RepName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtrepCode.setText(code);
        txtrep.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
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

    private void getStore() {
        new GUIFindWindow(null, true, "Store", "StoreCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtStoreCode.setText(code);
        txtStore.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
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

    private void loadCurrency() {
        Vector v = new Vector();
        v = SERPurchase.loadCurrency();
        txtCurrencyCode.setModel(new DefaultComboBoxModel(v));
        loadFCRate();
    }

    private void loadFCRate() {
        double d = 0.00;
        d = SERPurchase.getFCRate(txtCurrencyCode.getSelectedItem().toString());
        txtFCRate.setText(core.Locals.currencyFormat(d));
    }

    private void cellEdit() {
        int i = jTable1.getSelectedRow();
        jTable1.setCellSelectionEnabled(true);

        if (i >= 0) {
            jTable1.grabFocus();
            jTable1.editCellAt(i, 4);
            jFormattedTextField1.grabFocus();
        }
    }

    private void loadItem() {
        if (!txtStoreCode.getText().isEmpty()) {
            ItemList i = new ItemList(null, true, txtStoreCode.getText());
            i.setVisible(true);
            if (null != ItemList.itemSearch && ItemList.itemSearch.getCode() != null) {
                jTextField1.setText(ItemList.itemSearch.getCode());
                ItemList.itemSearch = null;
                jTextField1.setFocusable(false);
                cellEdit();
                loadTable();
            }
        } else {
            txtStoreCode.grabFocus();
            JOptionPane.showMessageDialog(null, "Pleas select the store first...");
        }
    }

    private void loadTable() {
        int i = jTable1.getSelectedRow();
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        String itc = df.getValueAt(i, 1).toString();
//        boolean b = itc != null && jTable1.getSelectedColumn() == 1;
        boolean b = itc != null && df.isCellEditable(i, 1) && df.getValueAt(i, 4) == "";
        if (i >= 0) {
            if (b) {

                obj = SERPurchase.getTablInfo(itc, txtStoreCode.getText());
                df.setValueAt(obj.getName(), i, 2);
                df.setValueAt(obj.getUnit(), i, 3);
                df.setValueAt("0", i, 4);
                df.setValueAt(obj.getWar(), i, 5);
                df.setValueAt(obj.getCost(), i, 6);
                //df.setValueAt(obj.getDiscount(), i, 7);
                df.setValueAt("0.00", i, 7);
                df.setValueAt("0.00", i, 8);
                df.setValueAt(Locals.sCurrencyFormat(obj.getSellingRate()), i, 10);
                df.setValueAt(Locals.sCurrencyFormat(obj.getMinSellingRate()), i, 11);

                //String name = SERCommen.getDescription( "ItemMaster", itc, "ItemCode", "ShortName");
                // String unit = SERCommen.getDescription( "ItemMaster", itc, "ItemCode", "UnitCode");
                // Double minlev = Double.parseDouble(SERCommen.getDescription( "ItemMaster", itc, "ItemCode", "MinLevel"));
                // String Warr = SERCommen.getDescription( "Stock", itc + "AND Store = '"+txtStoreCode.getText()+"'", "ItemCode", "Warranty");
                // Double Rate = Double.parseDouble(SERCommen.getDescription( "Stock", itc + "AND Store = '"+txtStoreCode.getText()+"'", "ItemCode", "SellingRate"));
            }
            boolean b1 = (df.getValueAt(i, 4).toString() != null && df.getValueAt(i, 6).toString() != null) && (jTable1.getSelectedColumn() == 4 || jTable1.getSelectedColumn() == 6 || jTable1.getSelectedColumn() == 7);
            if (b1) {
                doCalc(i, df);
            }

            doCalAll();
            jTable1.setCellSelectionEnabled(!true);
            jTable1.setRowSelectionAllowed(true);
        }
    }

    private void doCalc(int i, DefaultTableModel df) {
        double d = Double.parseDouble(df.getValueAt(i, 4).toString());
        double d1 = Double.parseDouble(df.getValueAt(i, 6).toString());
        df.setValueAt(Locals.currencyFormat(d * d1), i, 8);
    }

    private void calcTotDiscVal() {
        double d = Double.parseDouble(txtGrossAmount.getText());
        double d1 = Double.parseDouble(txtDiscountRate.getText());
        d1 = (d * d1) / 100;
        txtDiscount.setText(Locals.currencyFormat(d1));
        txtNetAmount.setText(Locals.currencyFormat(d - d1));
        calcDue();
    }

    private void calcTotDiscRate() {
        double d = Double.parseDouble(txtGrossAmount.getText());
        double d1 = Double.parseDouble(txtDiscount.getText());
        txtNetAmount.setText(Locals.currencyFormat(d - d1));
        d1 = (100 * d1) / d;
        txtDiscountRate.setText(Locals.currencyFormat(d1));
        calcDue();
    }

    private void doCalAll() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        double d1 = 0.00;
        double d = 0.00;
        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            if (null != df.getValueAt(i, 7) && null != df.getValueAt(i, 8)) {
                d += Double.parseDouble(df.getValueAt(i, 7).toString());
                d1 += Double.parseDouble(df.getValueAt(i, 8).toString());
            }
        }
        txtGrossAmount.setText(Locals.currencyFormat(d1));
        txtDiscount.setText(Locals.currencyFormat(d));
        txtNetAmount.setText(Locals.currencyFormat(d1));
        calcDue();
    }

    private void setDisc(boolean b) {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            df.setValueAt("0.00", i, 7);
        }
        calcTotDisc();
    }

    private void calcTotDisc() {
        double d = Double.parseDouble(txtGrossAmount.getText());
        double d1 = 0.00;//Double.parseDouble(txtDiscountRate.getText());
        //d1 = (d * d1) / 100;
        txtDiscount.setText(Locals.currencyFormat(d1));
        //txtNetAmount.setText(Locals.currencyFormat(d - d1));
        //calcDue();
    }

    private void calcDue() {
        double d = Double.parseDouble(txtNetAmount.getText());
        double d1 = Double.parseDouble(txtPay.getText());
        d1 = (d - d1);
        txtDue.setText(Locals.currencyFormat(d1));

    }

    private void setItem() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int i = jTable1.getSelectedRow();
        String itc = df.getValueAt(i, 1).toString();
        String itd = df.getValueAt(i, 2).toString();
        txtSelectedItem.setText(itc);
        txtSelectedItemName.setText(itd);
    }

    /*
     * private void setDate() { Calendar c = Calendar.getInstance();
     * txtCrPayDate.setDate(Locals.toDate(Locals.setDateFormat(c.getTime())));
     * txtInvDate.setDate(Locals.toDate(Locals.setDateFormat(c.getTime()))); }
     */
    private boolean invType() {
        boolean b = false;
        String due = txtDue.getText();
        double d = Double.parseDouble(due);
        if (txtInvoType.getSelectedIndex() == 0 && d != 0.0) {
            JOptionPane.showMessageDialog(this, "Pleas select the correct invoice type..");
        } else if (txtInvoType.getSelectedIndex() > 0 && d == 0.0) {
            int i = JOptionPane.showConfirmDialog(this, "<html>This is not a Credit bill,</br>Do you want to continue as cash bill..?</html>", "Warning..!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (i == 0) {
                txtInvoType.setSelectedIndex(0);
                b = true;
            }
        } else {
            b = true;
        }
        return b;
    }

    private boolean checkFields() {
        boolean b = true;
        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            if (jTable1.getValueAt(i, 4).equals("") || jTable1.getValueAt(i, 4) == null) {
                b = false;
            }

            if (jTable1.getValueAt(i, 6).equals("") || jTable1.getValueAt(i, 6) == null) {
                b = false;
            }

            if (jTable1.getValueAt(i, 10).equals("") || jTable1.getValueAt(i, 10) == null) {
                b = false;
            }

            if (jTable1.getValueAt(i, 11).equals("") || jTable1.getValueAt(i, 11) == null) {
                b = false;
            }

        }
        return b;
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
        loadCurrency();
        doNew();

//        setDate();
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
                try {
                    cmdSaveActionPerformed(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(GUIPurchase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        cmdCustomer.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCustomerActionPerformed(evt);
            }
        });
        cmdRep.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRepActionPerformed(evt);
            }
        });
        cmdCostCenter.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCostCenterActionPerformed(evt);
            }
        });
        cmdStore.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStoreActionPerformed(evt);
            }
        });

        cmdReport.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReportActionPerformed(evt);
            }
        });

        cmdArea.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAreaActionPerformed(evt);
            }
        });

        cmdPayTerm.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPayTermActionPerformed(evt);
            }
        });

        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jTable1.setBackground(new java.awt.Color(204, 255, 204));

        txtDiscountRate.setBackground(new java.awt.Color(249, 249, 229));
        txtDiscountRate.setEditable(false);
        txtDiscountRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtDiscountRate.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtDiscountRate.setText("0.00");
        txtDiscountRate.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountRateActionPerformed(evt);
            }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtInvon = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCurrencyCode = new javax.swing.JComboBox();
        lblCust = new javax.swing.JLabel();
        txtFCRate = new javax.swing.JFormattedTextField();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        txtCusCode = new javax.swing.JTextField();
        cmdCustomer = new javax.swing.JButton();
        txtCusName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtInvDate = new com.toedter.calendar.JDateChooser();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        txtCostCode = new javax.swing.JTextField();
        cmdCostCenter = new javax.swing.JButton();
        txtCostCenter = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLayeredPane8 = new javax.swing.JLayeredPane();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        txtDiscountRate = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        txtGrossAmount = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        txtOther = new javax.swing.JFormattedTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel10 = new javax.swing.JLabel();
        txtApproBy = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPrepBy = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtPayTermCode = new javax.swing.JTextField();
        cmdPayTerm = new javax.swing.JButton();
        txtPayTermCode1 = new javax.swing.JTextField();
        txtRef = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLayeredPane7 = new javax.swing.JLayeredPane();
        txtrepCode = new javax.swing.JTextField();
        cmdRep = new javax.swing.JButton();
        txtrep = new javax.swing.JTextField();
        jLayeredPane9 = new javax.swing.JLayeredPane();
        txtStoreCode = new javax.swing.JTextField();
        cmdStore = new javax.swing.JButton();
        txtStore = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLayeredPane10 = new javax.swing.JLayeredPane();
        jLabel23 = new javax.swing.JLabel();
        txtDue = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        txtPay = new javax.swing.JFormattedTextField();
        txtNetAmount = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        txtAreaCode = new javax.swing.JTextField();
        cmdArea = new javax.swing.JButton();
        txtArea = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtRemark = new javax.swing.JTextField();
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
        jButton3 = new javax.swing.JButton();
        jLayeredPane11 = new javax.swing.JLayeredPane();
        jLabel26 = new javax.swing.JLabel();
        txtSelectedItem = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtSelectedItemName = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtInvoType = new javax.swing.JComboBox();
        txtCrPayDate = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        txtpinvNo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jFormattedTextField1.setText("jFormattedTextField1");
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(220, 239, 220));
        setClosable(true);
        setTitle("Purchase(GRN)");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setMaximumSize(new java.awt.Dimension(788, 437));
        jPanel1.setPreferredSize(new java.awt.Dimension(788, 437));

        jLabel2.setText("FC Rate");

        jLabel3.setText("Inv. Date");

        txtInvon.setEditable(false);
        txtInvon.setBackground(new java.awt.Color(255, 255, 233));
        txtInvon.setForeground(new java.awt.Color(153, 0, 0));
        txtInvon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setText("GRN. Number");

        txtCurrencyCode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtCurrencyCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrencyCodeActionPerformed(evt);
            }
        });

        lblCust.setText("Credit A/C");

        txtFCRate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtCusCode.setText("223001");
        txtCusCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane2.add(txtCusCode);
        txtCusCode.setBounds(0, 0, 70, 20);

        cmdCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdCustomer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdCustomer.setContentAreaFilled(false);
        jLayeredPane2.add(cmdCustomer);
        cmdCustomer.setBounds(71, 0, 20, 20);

        txtCusName.setEditable(false);
        txtCusName.setBackground(new java.awt.Color(255, 255, 241));
        txtCusName.setForeground(new java.awt.Color(153, 0, 0));
        txtCusName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane2.add(txtCusName);
        txtCusName.setBounds(95, 0, 230, 18);

        jLabel4.setText("FC Code");

        txtInvDate.setDateFormatString("yyyy-MM-dd");

        txtCostCode.setText("001");
        txtCostCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtCostCode);
        txtCostCode.setBounds(0, 0, 70, 20);

        cmdCostCenter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdCostCenter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdCostCenter.setContentAreaFilled(false);
        jLayeredPane4.add(cmdCostCenter);
        cmdCostCenter.setBounds(71, 0, 20, 20);

        txtCostCenter.setBackground(new java.awt.Color(255, 255, 241));
        txtCostCenter.setEditable(false);
        txtCostCenter.setForeground(new java.awt.Color(153, 0, 0));
        txtCostCenter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtCostCenter);
        txtCostCenter.setBounds(95, 0, 230, 18);

        jLabel9.setText("Cost Center");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Add Charges");
        jButton1.setEnabled(false);
        jLayeredPane8.add(jButton1);
        jButton1.setBounds(0, 40, 140, 23);

        jCheckBox1.setOpaque(false);
        jLayeredPane8.add(jCheckBox1);
        jCheckBox1.setBounds(30, 20, 20, 21);

        txtDiscountRate.setEditable(false);
        txtDiscountRate.setText("0.00");
        txtDiscountRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountRateKeyReleased(evt);
            }
        });
        jLayeredPane8.add(txtDiscountRate);
        txtDiscountRate.setBounds(60, 20, 60, 20);

        jLabel17.setBackground(new java.awt.Color(102, 102, 102));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Gross Amount");
        jLabel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel17.setOpaque(true);
        jLayeredPane8.add(jLabel17);
        jLabel17.setBounds(0, 0, 140, 20);

        txtGrossAmount.setBackground(new java.awt.Color(249, 249, 229));
        txtGrossAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtGrossAmount.setEditable(false);
        txtGrossAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtGrossAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtGrossAmount.setText("0.00");
        jLayeredPane8.add(txtGrossAmount);
        txtGrossAmount.setBounds(140, 0, 120, 20);

        jLabel18.setBackground(new java.awt.Color(102, 102, 102));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Disc                        %");
        jLabel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel18.setOpaque(true);
        jLayeredPane8.add(jLabel18);
        jLabel18.setBounds(0, 20, 140, 20);

        txtDiscount.setEditable(false);
        txtDiscount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDiscount.setForeground(new java.awt.Color(153, 0, 0));
        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscount.setText("0.00");
        txtDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountKeyReleased(evt);
            }
        });
        jLayeredPane8.add(txtDiscount);
        txtDiscount.setBounds(140, 20, 120, 20);

        jLabel19.setBackground(new java.awt.Color(102, 102, 102));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel19.setOpaque(true);
        jLayeredPane8.add(jLabel19);
        jLabel19.setBounds(0, 40, 140, 20);

        txtOther.setBackground(new java.awt.Color(249, 249, 229));
        txtOther.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtOther.setEditable(false);
        txtOther.setForeground(new java.awt.Color(153, 0, 0));
        txtOther.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtOther.setText("0.00");
        jLayeredPane8.add(txtOther);
        txtOther.setBounds(140, 40, 120, 20);

        jLabel10.setText("Pay Term");
        jLayeredPane1.add(jLabel10);
        jLabel10.setBounds(0, 0, 45, 19);

        txtApproBy.setText("sdawd");
        txtApproBy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane1.add(txtApproBy);
        txtApproBy.setBounds(70, 40, 300, 18);

        jLabel12.setText("Approved By");
        jLayeredPane1.add(jLabel12);
        jLabel12.setBounds(0, 40, 62, 20);

        txtPrepBy.setText("sdsd");
        txtPrepBy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane1.add(txtPrepBy);
        txtPrepBy.setBounds(70, 20, 300, 18);

        jLabel14.setText("Prepared By");
        jLayeredPane1.add(jLabel14);
        jLabel14.setBounds(0, 20, 59, 20);

        txtPayTermCode.setText("CASH");
        txtPayTermCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane1.add(txtPayTermCode);
        txtPayTermCode.setBounds(70, 0, 80, 18);

        cmdPayTerm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdPayTerm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdPayTerm.setContentAreaFilled(false);
        jLayeredPane1.add(cmdPayTerm);
        cmdPayTerm.setBounds(150, 0, 20, 20);

        txtPayTermCode1.setText("CASH");
        txtPayTermCode1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane1.add(txtPayTermCode1);
        txtPayTermCode1.setBounds(70, 0, 70, 18);

        txtRef.setEditable(false);
        txtRef.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane1.add(txtRef);
        txtRef.setBounds(170, 0, 200, 18);

        jTable1.setBackground(new java.awt.Color(255, 252, 239));
        jTable1.setForeground(new java.awt.Color(102, 51, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "", "", "", "", "", "", "", "", "", null, null}
            },
            new String [] {
                "", "Item Code", "Descrip", "Unit", "Qty.", "Warranty", "Rate", "Discount", "Net", "S/N", "Sale Price", "Min Sale Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, false, true, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setRowHeight(25);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(155);
            jTable1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(jTextField1));
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(280);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(140);
            jTable1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(jFormattedTextField1));
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(140);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(125);
            jTable1.getColumnModel().getColumn(8).setResizable(false);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(9).setResizable(false);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(10).setResizable(false);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(11).setResizable(false);
            jTable1.getColumnModel().getColumn(11).setPreferredWidth(150);
        }

        txtrepCode.setText("001");
        txtrepCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane7.add(txtrepCode);
        txtrepCode.setBounds(0, 0, 70, 20);

        cmdRep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdRep.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdRep.setContentAreaFilled(false);
        jLayeredPane7.add(cmdRep);
        cmdRep.setBounds(71, 0, 20, 20);

        txtrep.setBackground(new java.awt.Color(255, 255, 241));
        txtrep.setEditable(false);
        txtrep.setForeground(new java.awt.Color(153, 0, 0));
        txtrep.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane7.add(txtrep);
        txtrep.setBounds(95, 0, 190, 18);

        txtStoreCode.setText("001");
        txtStoreCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(txtStoreCode);
        txtStoreCode.setBounds(0, 0, 70, 20);

        cmdStore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdStore.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdStore.setContentAreaFilled(false);
        jLayeredPane9.add(cmdStore);
        cmdStore.setBounds(71, 0, 20, 20);

        txtStore.setBackground(new java.awt.Color(255, 255, 241));
        txtStore.setEditable(false);
        txtStore.setForeground(new java.awt.Color(153, 0, 0));
        txtStore.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(txtStore);
        txtStore.setBounds(95, 0, 190, 18);

        jLabel21.setText("Store Code");

        jLabel22.setText("Rep. Code");

        jLabel23.setBackground(new java.awt.Color(102, 102, 102));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Pay Amount");
        jLabel23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel23.setOpaque(true);
        jLayeredPane10.add(jLabel23);
        jLabel23.setBounds(0, 20, 110, 20);

        txtDue.setEditable(false);
        txtDue.setBackground(new java.awt.Color(249, 249, 229));
        txtDue.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDue.setForeground(new java.awt.Color(153, 0, 0));
        txtDue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDue.setText("0.00");
        jLayeredPane10.add(txtDue);
        txtDue.setBounds(110, 40, 120, 20);

        jLabel24.setBackground(new java.awt.Color(102, 102, 102));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Due Amount");
        jLabel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel24.setOpaque(true);
        jLayeredPane10.add(jLabel24);
        jLabel24.setBounds(0, 40, 110, 20);

        txtPay.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPay.setForeground(new java.awt.Color(153, 0, 0));
        txtPay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPay.setText("0.00");
        txtPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPayActionPerformed(evt);
            }
        });
        txtPay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPayKeyReleased(evt);
            }
        });
        jLayeredPane10.add(txtPay);
        txtPay.setBounds(110, 20, 120, 20);

        txtNetAmount.setEditable(false);
        txtNetAmount.setBackground(new java.awt.Color(249, 249, 229));
        txtNetAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNetAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtNetAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNetAmount.setText("0.00");
        jLayeredPane10.add(txtNetAmount);
        txtNetAmount.setBounds(110, 0, 120, 20);

        jLabel20.setBackground(new java.awt.Color(102, 102, 102));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Net Amount");
        jLabel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel20.setOpaque(true);
        jLayeredPane10.add(jLabel20);
        jLabel20.setBounds(0, 0, 110, 20);

        jLabel25.setText("Area");

        txtAreaCode.setText("001");
        txtAreaCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane6.add(txtAreaCode);
        txtAreaCode.setBounds(0, 0, 70, 20);

        cmdArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdArea.setContentAreaFilled(false);
        jLayeredPane6.add(cmdArea);
        cmdArea.setBounds(71, 0, 20, 20);

        txtArea.setBackground(new java.awt.Color(255, 255, 241));
        txtArea.setEditable(false);
        txtArea.setForeground(new java.awt.Color(153, 0, 0));
        txtArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane6.add(txtArea);
        txtArea.setBounds(95, 0, 190, 18);

        jLabel13.setText("Remarks");

        txtRemark.setText("ererwer");
        txtRemark.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLayeredPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLayeredPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblCust, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtInvon)
                                                .addGap(37, 37, 37)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtInvDate, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(123, 123, 123)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCurrencyCode, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFCRate, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLayeredPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLayeredPane6)
                                            .addComponent(jLayeredPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRemark)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtInvon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(10, 10, 10)
                                .addComponent(lblCust, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtInvDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLayeredPane2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCurrencyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(txtFCRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLayeredPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLayeredPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                        .addComponent(jLayeredPane1))
                    .addComponent(jLayeredPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRemark, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

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

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setText("Populate PO");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("ItemCode");
        jLabel26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel26.setOpaque(true);
        jLayeredPane11.add(jLabel26);
        jLabel26.setBounds(0, 0, 130, 20);

        txtSelectedItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItem.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItem.setText("jLabel14");
        txtSelectedItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSelectedItem.setOpaque(true);
        jLayeredPane11.add(txtSelectedItem);
        txtSelectedItem.setBounds(130, 0, 170, 20);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Description");
        jLabel27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel27.setOpaque(true);
        jLayeredPane11.add(jLabel27);
        jLabel27.setBounds(310, 0, 180, 20);

        txtSelectedItemName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItemName.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItemName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItemName.setText("jLabel16");
        txtSelectedItemName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSelectedItemName.setOpaque(true);
        jLayeredPane11.add(txtSelectedItemName);
        txtSelectedItemName.setBounds(490, 0, 400, 20);

        jLabel5.setText("Invo. Type");

        txtInvoType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cash", "Credit" }));
        txtInvoType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInvoTypeActionPerformed(evt);
            }
        });

        txtCrPayDate.setDateFormatString("yyyy-MM-dd");
        txtCrPayDate.setEnabled(false);

        jLabel6.setText("Inv. Date");

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("Print GRN");
        jCheckBox2.setOpaque(false);

        txtpinvNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel7.setText("Invo No.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane11)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtInvoType, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCrPayDate, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtpinvNo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(txtInvoType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtpinvNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCrPayDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if (evt.getKeyCode() == 127 && jTable1.getSelectedRow() >= 0) {
            doDeleteRow();
        } else if (evt.getKeyCode() == KeyEvent.VK_F3) {
            addNewRow();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loadTable();
        } else {
            if (jTable1.getSelectedColumn() == 1) {
                loadItem();
            }
        }
        doCalAll();
    }//GEN-LAST:event_jTable1KeyReleased

    private void txtPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPayActionPerformed
        if (txtPay.getText() == null) {
            txtPay.setText("0.00");
        }
        calcDue();
    }//GEN-LAST:event_txtPayActionPerformed

    private void txtCurrencyCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrencyCodeActionPerformed
        loadFCRate();
    }//GEN-LAST:event_txtCurrencyCodeActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            new PrintGRN("000011").setVisible(true);
        } catch (Exception e) {
            Exp.Handle(e);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtInvoTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInvoTypeActionPerformed
        Calendar c = Calendar.getInstance();
        txtCrPayDate.setDate(Locals.toDate(Locals.setDateFormat(c.getTime())));
        if (txtInvoType.getSelectedIndex() == 0) {
            txtCrPayDate.setEnabled(false);
        } else if (txtInvoType.getSelectedIndex() == 1) {
            txtCrPayDate.setEnabled(true);
        } else if (txtInvoType.getSelectedIndex() == 2) {
            txtCrPayDate.setEnabled(!true);
        }
    }//GEN-LAST:event_txtInvoTypeActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        loadItem();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadItem();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        loadItem();
    }//GEN-LAST:event_jTextField1FocusGained

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        int i = jTable1.getSelectedRow();
        if (i >= 0) {
            DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
            boolean b1 = (df.getValueAt(i, 4).toString() != null && df.getValueAt(i, 6).toString() != null && Double.parseDouble(df.getValueAt(i, 4).toString()) != 0);
            if (b1) {
                doCalc(i, df);
                doCalAll();
            } else {
                JOptionPane.showMessageDialog(null, "Item Qty is Zero", "Qty erorr !", JOptionPane.ERROR_MESSAGE);
                jFormattedTextField1.grabFocus();
            }
        }
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void txtDiscountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyReleased
        calcTotDiscRate();
    }//GEN-LAST:event_txtDiscountKeyReleased

    private void txtDiscountRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountRateKeyReleased
        calcTotDiscVal();
    }//GEN-LAST:event_txtDiscountRateKeyReleased

    private void txtPayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPayKeyReleased
        if (txtPay.getText() == null) {
            txtPay.setText("0.00");
        }
        calcDue();
    }//GEN-LAST:event_txtPayKeyReleased

    private void txtDiscountRateActionPerformed(java.awt.event.ActionEvent evt) {
        calcTotDisc();
    }

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        chBoxAct();
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        setItem();
    }

    private void cmdCustomerActionPerformed(java.awt.event.ActionEvent evt) {
        getCustomer();
    }

    private void cmdCrAccActionPerformed(java.awt.event.ActionEvent evt) {
        getCrAcc();
    }

    private void cmdRepActionPerformed(java.awt.event.ActionEvent evt) {
        getSalesRep();
    }

    private void cmdCostCenterActionPerformed(java.awt.event.ActionEvent evt) {
        getCost();
    }

    private void cmdStoreActionPerformed(java.awt.event.ActionEvent evt) {
        getStore();
    }

    private void cmdAreaActionPerformed(java.awt.event.ActionEvent evt) {
        getArea();
    }

    private void cmdPayTermActionPerformed(java.awt.event.ActionEvent evt) {
        getPayTerm();
    }

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        if (invType() && checkFields()) {
            doCalAll();
            doSave();
        }
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
        indexCount = SERPurchase.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index = indexCount - 1;
        }
    }

    private void cmdLastActionPerformed(java.awt.event.ActionEvent evt) {
        Index = SERPurchase.getIndex() - 1;
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
        int q = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this?", "GRN Cancel", JOptionPane.YES_NO_OPTION);

        if (q == JOptionPane.YES_OPTION) {
            doCancel();
        }
    }

    private void cmdViewActionPerformed(java.awt.event.ActionEvent evt) {
        Index = 0;
        doView();
    }

    private void cmdReportActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            new PrintGRN(txtInvon.getText()).setVisible(true);
        } catch (Exception ex) {
            Exp.Handle(ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdArea;
    private javax.swing.JButton cmdCostCenter;
    private javax.swing.JButton cmdCustomer;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdExit;
    private javax.swing.JButton cmdFind;
    private javax.swing.JButton cmdFirst;
    private javax.swing.JButton cmdLast;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdNext;
    private javax.swing.JButton cmdPayTerm;
    private javax.swing.JButton cmdPrev;
    private javax.swing.JButton cmdRep;
    private javax.swing.JButton cmdReport;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdStore;
    private javax.swing.JButton cmdView;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane10;
    private javax.swing.JLayeredPane jLayeredPane11;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JLayeredPane jLayeredPane7;
    private javax.swing.JLayeredPane jLayeredPane8;
    private javax.swing.JLayeredPane jLayeredPane9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblCust;
    private javax.swing.JTextField txtApproBy;
    private javax.swing.JTextField txtArea;
    private javax.swing.JTextField txtAreaCode;
    private javax.swing.JTextField txtCostCenter;
    private javax.swing.JTextField txtCostCode;
    private com.toedter.calendar.JDateChooser txtCrPayDate;
    private javax.swing.JComboBox txtCurrencyCode;
    private javax.swing.JTextField txtCusCode;
    private javax.swing.JTextField txtCusName;
    private javax.swing.JFormattedTextField txtDiscount;
    private javax.swing.JFormattedTextField txtDiscountRate;
    private javax.swing.JFormattedTextField txtDue;
    private javax.swing.JFormattedTextField txtFCRate;
    private javax.swing.JFormattedTextField txtGrossAmount;
    private com.toedter.calendar.JDateChooser txtInvDate;
    private javax.swing.JComboBox txtInvoType;
    private javax.swing.JTextField txtInvon;
    private javax.swing.JFormattedTextField txtNetAmount;
    private javax.swing.JFormattedTextField txtOther;
    private javax.swing.JFormattedTextField txtPay;
    private javax.swing.JTextField txtPayTermCode;
    private javax.swing.JTextField txtPayTermCode1;
    private javax.swing.JTextField txtPrepBy;
    private javax.swing.JTextField txtRef;
    private javax.swing.JTextField txtRemark;
    private javax.swing.JLabel txtSelectedItem;
    private javax.swing.JLabel txtSelectedItemName;
    private javax.swing.JTextField txtStore;
    private javax.swing.JTextField txtStoreCode;
    private javax.swing.JTextField txtpinvNo;
    private javax.swing.JTextField txtrep;
    private javax.swing.JTextField txtrepCode;
    // End of variables declaration//GEN-END:variables
    private OBJPurchase obj;
    private OBJPurchase obj1;
    private OBJInvPayPlan objpp;
    private ArrayList<OBJPurchase> obja;

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
