/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIMultipliDebiting.java
 *
 * Created on Jul 19, 2013, 7:56:32 PM
 */
package system.accounts.transaction.paymentvoucher.onaccount;

import accountpackage.AccountPackage;
import core.Locals;
import core.system_transaction.SystemSetting;
import core.system_transaction.TransactionType;
import core.system_transaction.account_trans.AccountTransStatus;
import core.system_transaction.account_trans.AccountTransactionDescription;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.payment.PaymentSetting;
import core.system_transaction.payment.PaymentSettingObject;
import core.system_transaction.payment.multy_payment.MultiPayOption;
import core.system_transaction.payment.multy_payment.VoucherType;
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.transaction.OBJTransactionHistory;
import core.system_transaction.transaction.TransactionStatus;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.findwindow.GUIFindWindow;
import system.accounts.transaction.cheque.OBJCheque;
import system.accounts.transaction.paymentvoucher.againstinvoice.SERVouch;

/**
 *
 * @author dell
 */
public class GUIMultipleDebiting extends javax.swing.JPanel {

    /**
     * Creates new form GUIMultipliDebiting
     */
    public GUIMultipleDebiting() {
        initComponents();
        initOthers();
    }

    public void save() {
        if (checks()) {
            OBJVoucherOnAccount voucherOnAccount = new OBJVoucherOnAccount();

            voucherOnAccount.setVoucherNo(txtjvNo.getText());
            voucherOnAccount.setVouDate(Locals.setDateFormat(txtJVDate.getDate()));
            voucherOnAccount.setCostCenter(txtCostCenter.getText());
            voucherOnAccount.setNarration(txtNarration.getText());
            voucherOnAccount.setPayAmount(txtAmount.getText());
            voucherOnAccount.setPayTerm("MULTY");
            voucherOnAccount.setRef(txtDocumentNo.getText());
            voucherOnAccount.setType(TransactionType.VOUCHER);
            voucherOnAccount.setUser(AccountPackage.user);
            voucherOnAccount.setPrpBy(txtAccountant.getText());
            voucherOnAccount.setAprBy(txtApproved.getText());

            // Payment Info
            if (getPayment()) {

                // Payment
                payment = new OBJPayment();
                payment.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
                payment.setCostCode(txtCostCenter.getText());
                payment.setCustomerCode(null);
                payment.setCashireSession(null);
                payment.setAmount(txtAmount.getText());
                payment.setTransaction("");
                payment.setTransactionType(TransactionType.VOUCHER);
                payment.setStatus(TransactionStatus.ACTIVE);

                // Transaction Object
                transaction = new OBJTransaction();
                transaction.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
                transaction.setTransactionType(TransactionType.VOUCHER);
                transaction.setReferanceNo(txtjvNo.getText());
                transaction.setDocumentNo(txtDocumentNo.getText());
                transaction.setLoan("");
                transaction.setCostCode(txtCostCenter.getText());
                transaction.setCustomerCode("");
                transaction.setNote(txtNarration.getText());
                transaction.setStatus(TransactionStatus.ACTIVE);

//        Transaction History
//        historys = new ArrayList<>();
//        transHistory();
//        Account Transaction
                accountTransaction();
                itemTransactions = null;
                boolean b = SERPayOnAccount.save(voucherOnAccount, cheques, transaction, payment, paymentsInfo, accountTranses, itemTransactions);
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

                    doNew();
                    JOptionPane.showMessageDialog(null, "Save Successfull...");
                } else {
                    JOptionPane.showMessageDialog(null, "Saving fail...");
                }
            }
        }
    }

    private boolean checks() {
        return !txtAmount.getText().isEmpty() && !txtCostCenter.getText().isEmpty() && tblDebit.getRowCount() > 0;
    }

    private boolean accountTransaction() {
        accountTranses = new ArrayList<>();
        // Debit Account Trans
        for (OBJPaymentInfo info : paymentsInfo) {
            OBJAccountTrans accountTrans = new OBJAccountTrans();
            accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
            accountTrans.setCostCode(txtCost.getText());
            accountTrans.setAccountSetting(null);

            switch (info.getPaymentSetting()) {
                case PaymentSetting.VOUCHER_CASH:
                    accountTrans.setDescription(AccountTransactionDescription.VOUCHER_CASH_PAYMENT);
                    accountTrans.setAccount(((PaymentSettingObject) SystemSetting.getPaymentSeting(PaymentSetting.VOUCHER_CASH)).getAccount());
                    accountTrans.setDebitAmount("0.00");
                    accountTrans.setCreditAmount(info.getAmount());
                    break;
                case PaymentSetting.VOUCHER_CHEQUE:
                    accountTrans.setDescription(AccountTransactionDescription.VOUCHER_CHEQUE_PAYMENT);
                    accountTrans.setAccount(((PaymentSettingObject) SystemSetting.getPaymentSeting(PaymentSetting.VOUCHER_CHEQUE)).getAccount());
                    accountTrans.setDebitAmount("0.00");
                    accountTrans.setCreditAmount(info.getAmount());
                    break;
            }

            accountTrans.setTransactionType(TransactionType.VOUCHER);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }

        // Credit Account Trans
        for (int i = 0; i < tblDebit.getRowCount(); i++) {
            OBJAccountTrans accountTrans = new OBJAccountTrans();
            accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
            accountTrans.setCostCode(txtCost.getText());
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(AccountTransactionDescription.VOUCHER_AMOUNT + "Debit Account : " + tblDebit.getValueAt(i, 1).toString());
            accountTrans.setAccount(tblDebit.getValueAt(i, 1).toString());
            accountTrans.setDebitAmount(tblDebit.getValueAt(i, 3).toString());
            accountTrans.setCreditAmount("0.00");
            accountTrans.setTransactionType(TransactionType.VOUCHER);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }

        return true;
    }

    private boolean getPayment() {

        new MultiPayOption(null, true, txtAmount.getText(), paymentsInfo, VoucherType.VOUCHER, cheques).setVisible(true);
        if (MultiPayOption.infos != null) {
            paymentsInfo = MultiPayOption.infos;
            MultiPayOption.infos = null;
            ArrayList<OBJCheque> cheques = MultiPayOption.cheques;
            MultiPayOption.cheques = null;
            this.cheques = null;
            this.cheques = new ArrayList<>();
            if (cheques != null) {
                for (OBJCheque oBJCheque : cheques) {
                    cheque = new OBJCheque();
                    cheque.setCustCode("");
                    cheque.setInvoNo(txtjvNo.getText());
                    cheque.setChqeNo(oBJCheque.getChqeNo());
                    cheque.setGetDate(txtAmount.getText());
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
                    cheque.setStatus("0");
                    this.cheques.add(cheque);
                }
            }
            return true;
        }
        return false;
    }

    private void addEntry() {
        entryPanal = new JournalEntryPanal(null, true);
        entryPanal.setVisible(true);
        if (JournalEntryPanal.entry != null && !JournalEntryPanal.entry.getAccount().equals("") && JournalEntryPanal.entry.getAccount() != null) {
            loadCreditTable();
        }
    }

    private void loadCreditTable() {
        this.entry = JournalEntryPanal.entry;
        jVEntry.getEntrys().add(entry);
        JournalEntryPanal.entry = null;
        DefaultTableModel dt = (DefaultTableModel) tblDebit.getModel();
        dt.addRow(new Object[]{"", entry.getAccount(), entry.getAccountName(), entry.getAmount(), entry.getNaration()});
        doCall();
    }

    private void doCall() {
        double creditAmount = 0.00;

        if (tblDebit.getRowCount() > 0) {
            for (int i = 0; i < tblDebit.getRowCount(); i++) {
                creditAmount = creditAmount + Double.parseDouble(tblDebit.getValueAt(i, 3).toString());
            }
        }

        txtTotalDebit.setText(Locals.currencyFormat(creditAmount));
    }

    private void clearTable() {
        int row = tblDebit.getRowCount();
        for (int i = 0; i < row; i++) {
            ((DefaultTableModel) tblDebit.getModel()).removeRow(0);
        }
    }

    private void doNew() {
        txtJVDate.setDate(Locals.toDate(Locals.currentDate_F2()));
        txtAmount.setText("0.00");
        txtNarration.setText("");
        txtTotalDebit.setText("0.00");
        txtCostCenter.setText("");
        txtCost.setText("");
        txtAccountant.setText("");
        txtApproved.setText("");
        txtDocumentNo.setText("");
        clearTable();
        doCall();
        setID();
        setButtonMode();
    }

    private void setID() {
        txtjvNo.setText(SERVouch.getID());
    }

    private void setButtonMode() {
        if (tblDebit.getSelectedRow() >= 0) {
            cmdRemove.setEnabled(true);
        } else {
            cmdRemove.setEnabled(!true);
        }
    }

    private void getCost() {
        new GUIFindWindow(null, true, "CostCenter", "CostCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtCostCenter.setText(code);
        txtCost.setText(name);
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
        jVEntry = new OBJJVEntry();
        clearTable();
//        buttonMode();
        doNew();
        cheque = new OBJCheque();
        cheques = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane3 = new javax.swing.JLayeredPane();
        txtCostCenter = new javax.swing.JTextField();
        cmdArea2 = new javax.swing.JButton();
        txtCost = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtjvNo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAccountant = new javax.swing.JTextField();
        txtApproved = new javax.swing.JTextField();
        txtJVDate = new com.toedter.calendar.JDateChooser();
        tblCreditPnl = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDebit = new javax.swing.JTable();
        cmdAddCredit = new javax.swing.JButton();
        cmdRemove = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        txtTotalDebit = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JFormattedTextField();
        txtNarration = new javax.swing.JTextField();
        txtDocumentNo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCostCenter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtCostCenter);
        txtCostCenter.setBounds(0, 0, 80, 18);

        cmdArea2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdArea2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdArea2.setContentAreaFilled(false);
        cmdArea2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdArea2ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(cmdArea2);
        cmdArea2.setBounds(80, 0, 20, 20);

        txtCost.setEditable(false);
        txtCost.setBackground(new java.awt.Color(255, 255, 241));
        txtCost.setForeground(new java.awt.Color(153, 0, 0));
        txtCost.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtCost);
        txtCost.setBounds(105, 0, 230, 18);

        jLabel10.setText("Cost Center");

        jLabel5.setText("Narration");

        txtjvNo.setEditable(false);
        txtjvNo.setBackground(new java.awt.Color(255, 255, 233));
        txtjvNo.setForeground(new java.awt.Color(153, 0, 0));
        txtjvNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setText("J.V. Date");

        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setText("Vou. Number");

        jLabel6.setText("Accountant");

        jLabel7.setText("Approved By");

        txtAccountant.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtApproved.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtJVDate.setDateFormatString("yyyy-MM-dd");

        tblCreditPnl.setBorder(javax.swing.BorderFactory.createTitledBorder("Dedit Account"));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblDebit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "", "Debit A/C", "Account Name", "Amount", "Narration"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDebit.setGridColor(new java.awt.Color(204, 204, 204));
        tblDebit.setOpaque(false);
        jScrollPane2.setViewportView(tblDebit);
        if (tblDebit.getColumnModel().getColumnCount() > 0) {
            tblDebit.getColumnModel().getColumn(0).setResizable(false);
            tblDebit.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblDebit.getColumnModel().getColumn(1).setResizable(false);
            tblDebit.getColumnModel().getColumn(1).setPreferredWidth(65);
            tblDebit.getColumnModel().getColumn(2).setResizable(false);
            tblDebit.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblDebit.getColumnModel().getColumn(3).setResizable(false);
            tblDebit.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblDebit.getColumnModel().getColumn(4).setResizable(false);
            tblDebit.getColumnModel().getColumn(4).setPreferredWidth(110);
        }

        cmdAddCredit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmdAddCredit.setForeground(new java.awt.Color(102, 102, 102));
        cmdAddCredit.setText("ADD DEBIT");
        cmdAddCredit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddCreditActionPerformed(evt);
            }
        });

        cmdRemove.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmdRemove.setForeground(new java.awt.Color(102, 102, 102));
        cmdRemove.setText("REMOVE");
        cmdRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveActionPerformed(evt);
            }
        });

        txtTotalDebit.setBackground(new java.awt.Color(204, 204, 255));
        txtTotalDebit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotalDebit.setForeground(new java.awt.Color(153, 0, 0));
        txtTotalDebit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalDebit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalDebit.setText("0.00");
        jLayeredPane1.add(txtTotalDebit);
        txtTotalDebit.setBounds(110, 0, 110, 20);

        jLabel15.setBackground(new java.awt.Color(102, 102, 102));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Total");
        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel15.setOpaque(true);
        jLayeredPane1.add(jLabel15);
        jLabel15.setBounds(0, 0, 110, 20);

        javax.swing.GroupLayout tblCreditPnlLayout = new javax.swing.GroupLayout(tblCreditPnl);
        tblCreditPnl.setLayout(tblCreditPnlLayout);
        tblCreditPnlLayout.setHorizontalGroup(
            tblCreditPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
            .addGroup(tblCreditPnlLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cmdAddCredit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdRemove))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tblCreditPnlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
        );
        tblCreditPnlLayout.setVerticalGroup(
            tblCreditPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tblCreditPnlLayout.createSequentialGroup()
                .addGroup(tblCreditPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmdAddCredit, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel11.setText("Amount");

        txtAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtAmount.setText("0.00");

        txtDocumentNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentNoActionPerformed(evt);
            }
        });

        jLabel4.setText("Document No");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAccountant, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtApproved)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(tblCreditPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNarration)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtjvNo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtJVDate, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDocumentNo)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLayeredPane3))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(txtDocumentNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNarration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tblCreditPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApproved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtAccountant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(txtJVDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtjvNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRemoveActionPerformed

    private void cmdAddCreditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddCreditActionPerformed
        addEntry();
    }//GEN-LAST:event_cmdAddCreditActionPerformed

    private void txtDocumentNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentNoActionPerformed

    private void cmdArea2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdArea2ActionPerformed
        getCost();
    }//GEN-LAST:event_cmdArea2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAddCredit;
    private javax.swing.JButton cmdArea2;
    private javax.swing.JButton cmdRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel tblCreditPnl;
    private javax.swing.JTable tblDebit;
    private javax.swing.JTextField txtAccountant;
    private javax.swing.JFormattedTextField txtAmount;
    private javax.swing.JTextField txtApproved;
    private javax.swing.JTextField txtCost;
    private javax.swing.JTextField txtCostCenter;
    private javax.swing.JTextField txtDocumentNo;
    private com.toedter.calendar.JDateChooser txtJVDate;
    private javax.swing.JTextField txtNarration;
    private javax.swing.JFormattedTextField txtTotalDebit;
    private javax.swing.JTextField txtjvNo;
    // End of variables declaration//GEN-END:variables
    private ArrayList<OBJCheque> cheques;
    private JournalEntryPanal entryPanal;
    private OBJJVEntry jVEntry;
    private OBJEntry entry;
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

}
