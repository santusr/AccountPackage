/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIPayRealization.java
 *
 * Created on Jul 20, 2013, 10:18:31 PM
 */
package system.accounts.transaction.realization.Receipts;

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
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.transaction.OBJTransactionHistory;
import core.system_transaction.transaction.TransactionStatus;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.SERCommen;
import system.accounts.transaction.cheque.ChequeStatus;
import system.accounts.transaction.cheque.GUICheque;
import system.accounts.transaction.cheque.OBJCheque;
import system.accounts.transaction.cheque.SERCheque;
import system.accounts.transaction.cheque.cheque_deposit.GUIDeposit;
import system.accounts.transaction.realization.ChequeCellRender;

/**
 *
 * @author dell
 */
public class GUIRecRealizationReceipt extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUIPayRealization
     */
    public GUIRecRealizationReceipt() {
        initComponents();
        initOthers();
    }

    private void doDelete() {
        int i = jTable1.getSelectedRow();
        String code = jTable1.getValueAt(i, 1).toString();
        SERCheque.delete(code);
    }

    private void setMode(int state) {
        switch (state) {
            case DEFAULT_STATUS:
                setEnableAll(true);

                cmdNew.setEnabled(!true);
                cmdDelete.setEnabled(!true);
                cmdReport.setEnabled(!true);

                break;
            case NEW_STATUS:
                setEnableAll(!true);

                cmdNew.setEnabled(true);
                cmdDelete.setEnabled(true);
                cmdReport.setEnabled(true);

                break;
        }
    }

    private void setEnableAll(boolean state) {

        chkBounced.setEnabled(state);
        jTable1.setEnabled(state);

        if (state) {
            jTable1.setBackground(new java.awt.Color(255, 255, 255));
        } else {
            jTable1.setBackground(new java.awt.Color(249, 249, 229));
        }
        /*
         * -----******( )*****-----
         */

    }

    private void setContentHistory() {
        String qry = "";
        if (txtFromDate.getDate() != null && txtToDate.getDate() != null) {
            qry = "RDate BETWEEN '" + Locals.setDateFormat(txtFromDate.getDate()) + "' AND '" + Locals.setDateFormat(txtToDate.getDate()) + "' AND ";
        }
        obja = SERRealizationReceipt.getContent(qry + "CheqType IN ('" + TransactionType.RECEIPT + "', '" + TransactionType.INVOICE + "')");
        loadCheques();
        doCal();
    }

    private void loadCheques() {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        clrTable();
        if (!obja.isEmpty()) {
            boolean cond = false;
            for (OBJCheque objq : obja) {
                if (objq.getStatus().equals(ChequeStatus.PENDING) && chkToBeRealised.isSelected()) {
                    cond = true;
                } else if (objq.getStatus().equals(ChequeStatus.REALIZE) && chkRealised.isSelected()) {
                    cond = true;
                } else if (objq.getStatus().equals(ChequeStatus.BOUNCED) && chkBounced.isSelected()) {
                    cond = true;
                } else if (objq.getStatus().equals(ChequeStatus.DEPOSIT) && chkDeposit.isSelected()) {
                    cond = true;
                }

                if (cond) {
                    String name = SERCommen.getDescription("customer", objq.getCustCode(), "AccCode", "PrintName");
                    dt.addRow(new Object[]{objq, objq.getRDate(), objq.getCustCode() + " : " + name, objq.getInvoNo(), objq.getAmount(), objq.getBankCharge(), objq.getInterestCharge(), objq.getNet(), objq.getStatus()});
                }
                cond = false;
            }
            jTable1.setDefaultRenderer(Object.class, new ChequeCellRender());
            jTable1.setRowSelectionAllowed(true);
        }
    }

    private void doCal() {
        int row = jTable1.getRowCount();
        if (row > 0) {
            double total = 0.00;
            double totalBank = 0.00;
            double totalInterest = 0.00;
            double totalNet = 0.00;
            for (int i = 0; i < row; i++) {
                total = total + Double.parseDouble(((OBJCheque) jTable1.getValueAt(i, 0)).getAmount());
                totalBank = totalBank + Double.parseDouble(((OBJCheque) jTable1.getValueAt(i, 0)).getBankCharge());
                totalInterest = totalInterest + Double.parseDouble(((OBJCheque) jTable1.getValueAt(i, 0)).getInterestCharge());
                totalNet = totalNet + Double.parseDouble(((OBJCheque) jTable1.getValueAt(i, 0)).getNet());
            }
            txtTotal.setText(Locals.currencyFormat(total));
            txtTotalBank.setText(Locals.currencyFormat(totalBank));
            txtTotalInterest.setText(Locals.currencyFormat(totalInterest));
            txtTotalNet.setText(Locals.currencyFormat(totalNet));
        }
    }

    private void clrTable() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int rc = jTable1.getRowCount();
        for (int i = 0; i < rc; i++) {
            df.removeRow(0);            
            jTable1.getDefaultRenderer(Object.class);
        }
    }

    private void doNew() {
        new GUICheque(null, true).setVisible(true);
    }

    private void setButtonMode() {
        int i = jTable1.getSelectedRow();
        if (i >= 0) {
            cmdChequeView.setEnabled(true);
            switch (((OBJCheque) jTable1.getValueAt(i, 0)).getStatus()) {
                case ChequeStatus.PENDING:
                    cmdDeposit.setEnabled(true);
                    cmdRealize.setEnabled(!true);
                    cmdBounsed.setEnabled(!true);
                    break;
                case ChequeStatus.DEPOSIT:
                    cmdDeposit.setEnabled(!true);
                    cmdRealize.setEnabled(true);
                    cmdBounsed.setEnabled(true);
                    break;
                default:
                    cmdDeposit.setEnabled(!true);
                    cmdRealize.setEnabled(!true);
                    cmdBounsed.setEnabled(!true);
                    break;
            }
        } else {
            cmdChequeView.setEnabled(!true);
            cmdDeposit.setEnabled(!true);
            cmdRealize.setEnabled(!true);
            cmdBounsed.setEnabled(!true);
        }

    }

    private void doRealize() {
        cheque = new OBJCheque();
        int i = jTable1.getSelectedRow();
        cheque = (OBJCheque) jTable1.getValueAt(i, 0);
        this.cheque.setStatus(ChequeStatus.REALIZE);
        transaction();
        accountTransaction();
        payment = null;
        paymentsInfo = null;
        itemTransactions = null;

        if (SERCheque.deposit(cheque, transaction, payment, paymentsInfo, accountTranses, itemTransactions)) {
            JOptionPane.showMessageDialog(null, "Realize successful...");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Realize fail...");
        }
    }

    private void transaction() {
        // Transaction Object
        int i = jTable1.getSelectedRow();
        OBJCheque cheque = (OBJCheque) jTable1.getValueAt(i, 0);
        String note = JOptionPane.showInputDialog(null, "Note");
        transaction = new OBJTransaction();
        transaction.setTransactionDate(accountpackage.AccountPackage.company.getWorkingDate());
        transaction.setTransactionType(TransactionType.CHEQUE_REALIZE);
        transaction.setDocumentNo(null);
        transaction.setLoan(null);
        transaction.setReferanceNo("CHR" + Locals.getRefarance());
        transaction.setCostCode(accountpackage.AccountPackage.costCode);
        transaction.setCustomerCode(cheque.getCustCode());
        transaction.setNote(note);
        transaction.setStatus(TransactionStatus.ACTIVE);

    }

    private boolean accountTransaction() {
        accountTranses = new ArrayList<>();

        // Account Transaction
        // Debit Account Trans
        int i = jTable1.getSelectedRow();
        OBJCheque cheque = (OBJCheque) jTable1.getValueAt(i, 0);
        OBJAccountTrans accountTrans = new OBJAccountTrans();
        accountTrans.setTransactionDate(accountpackage.AccountPackage.company.getWorkingDate());
        accountTrans.setCostCode(accountpackage.AccountPackage.costCode);
        accountTrans.setAccountSetting(null);
        accountTrans.setDescription(AccountTransactionDescription.RECEIPT_CHEQUE_REALIZE_DEBIT);
        accountTrans.setAccount(cheque.getDepositAccount());
        accountTrans.setCreditAmount("0.00");
        accountTrans.setDebitAmount(cheque.getAmount());
        accountTrans.setTransactionType(TransactionType.CHEQUE_REALIZE);
        accountTrans.setType("AUTO");
        accountTrans.setStatus(AccountTransStatus.ACTIVE);
        accountTranses.add(accountTrans);

        // Credit Account trans
        accountTrans = new OBJAccountTrans();
        accountTrans.setTransactionDate(accountpackage.AccountPackage.company.getWorkingDate());
        accountTrans.setCostCode(accountpackage.AccountPackage.costCode);
        accountTrans.setAccountSetting(null);
        accountTrans.setDescription(AccountTransactionDescription.RECEIPT_CHEQUE_REALIZE_CREDIT + " : " + cheque.getChqeNo());
        accountTrans.setAccount(((PaymentSettingObject) SystemSetting.getPaymentSeting(PaymentSetting.RECEIPT_CHEQUE)).getAccount());
        accountTrans.setCreditAmount(cheque.getAmount());
        accountTrans.setDebitAmount("0.00");
        accountTrans.setTransactionType(TransactionType.CHEQUE_REALIZE + " : " + cheque.getChqeNo());
        accountTrans.setType("AUTO");
        accountTrans.setStatus(AccountTransStatus.ACTIVE);
        accountTranses.add(accountTrans);
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initOthers() {
        setMode(DEFAULT_STATUS);
        setContentHistory();
        setButtonMode();
        cmdNew.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });

        cmdReport.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // cmdReportActionPerformed(evt);
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
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
        cmdExit1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        chkRealised = new javax.swing.JCheckBox();
        chkBounced = new javax.swing.JCheckBox();
        chkToBeRealised = new javax.swing.JCheckBox();
        chkDeposit = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtFromDate = new com.toedter.calendar.JDateChooser();
        txtToDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        txtAreaCode3 = new javax.swing.JTextField();
        cmdArea3 = new javax.swing.JButton();
        txtArea3 = new javax.swing.JTextField();
        cmdChequeView = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel14 = new javax.swing.JLabel();
        txtTotalNet = new javax.swing.JFormattedTextField();
        txtTotal = new javax.swing.JFormattedTextField();
        txtTotalBank = new javax.swing.JFormattedTextField();
        txtTotalInterest = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        cmdDeposit = new javax.swing.JButton();
        cmdRealize = new javax.swing.JButton();
        cmdBounsed = new javax.swing.JButton();

        setClosable(true);
        setTitle("Realization (Receipt)");

        jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        cmdFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point_090.png"))); // NOI18N
        cmdFirst.setToolTipText("First (Up Key)");
        cmdFirst.setAlignmentX(0.5F);
        cmdFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdFirst.setEnabled(false);
        cmdFirst.setFocusable(false);
        cmdFirst.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdFirst.setIconTextGap(5);
        cmdFirst.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdFirst);

        cmdPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point_180.png"))); // NOI18N
        cmdPrev.setToolTipText("Previous (Left Key)");
        cmdPrev.setAlignmentX(0.5F);
        cmdPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdPrev.setEnabled(false);
        cmdPrev.setFocusable(false);
        cmdPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdPrev.setIconTextGap(5);
        cmdPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdPrev);

        cmdNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point.png"))); // NOI18N
        cmdNext.setToolTipText("Next (Right Key)");
        cmdNext.setAlignmentX(0.5F);
        cmdNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdNext.setEnabled(false);
        cmdNext.setFocusable(false);
        cmdNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdNext.setIconTextGap(5);
        cmdNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdNext);

        cmdLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point_270.png"))); // NOI18N
        cmdLast.setToolTipText("Last (Down Key)");
        cmdLast.setAlignmentX(0.5F);
        cmdLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdLast.setEnabled(false);
        cmdLast.setFocusable(false);
        cmdLast.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdLast.setIconTextGap(5);
        cmdLast.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdLast);

        cmdFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/find.png"))); // NOI18N
        cmdFind.setToolTipText("Find (F4)");
        cmdFind.setAlignmentX(0.5F);
        cmdFind.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdFind.setEnabled(false);
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
        cmdNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdNew);

        cmdEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/document_prepare.png"))); // NOI18N
        cmdEdit.setToolTipText("Modify (F6)");
        cmdEdit.setAlignmentX(0.5F);
        cmdEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdEdit.setEnabled(false);
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
        cmdView.setEnabled(false);
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

        cmdExit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/arrow_rotate_clockwise.png"))); // NOI18N
        cmdExit1.setToolTipText("Refresh (F9)");
        cmdExit1.setAlignmentX(0.5F);
        cmdExit1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdExit1.setFocusable(false);
        cmdExit1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdExit1.setIconTextGap(5);
        cmdExit1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdExit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExit1ActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdExit1);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        chkRealised.setSelected(true);
        chkRealised.setText("Realised Chq.");
        chkRealised.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRealisedActionPerformed(evt);
            }
        });

        chkBounced.setSelected(true);
        chkBounced.setText("Bounced Chq.");
        chkBounced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBouncedActionPerformed(evt);
            }
        });

        chkToBeRealised.setSelected(true);
        chkToBeRealised.setText("To Be Realised Chq.");
        chkToBeRealised.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkToBeRealisedActionPerformed(evt);
            }
        });

        chkDeposit.setSelected(true);
        chkDeposit.setText("Deposit Chq.");
        chkDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDepositActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkBounced)
                    .addComponent(chkRealised)
                    .addComponent(chkToBeRealised)
                    .addComponent(chkDeposit))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkRealised)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBounced)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkDeposit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkToBeRealised)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Enter Cheque Due Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 102)));

        jLabel1.setText("From");

        jLabel2.setText("To");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFromDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtToDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Party");

        txtAreaCode3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtAreaCode3);
        txtAreaCode3.setBounds(0, 0, 70, 18);

        cmdArea3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdArea3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdArea3.setContentAreaFilled(false);
        jLayeredPane4.add(cmdArea3);
        cmdArea3.setBounds(72, 0, 21, 20);

        txtArea3.setBackground(new java.awt.Color(255, 255, 241));
        txtArea3.setEditable(false);
        txtArea3.setForeground(new java.awt.Color(153, 0, 0));
        txtArea3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtArea3);
        txtArea3.setBounds(95, 0, 230, 18);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLayeredPane4)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        cmdChequeView.setText("View Cheque");
        cmdChequeView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdChequeViewActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(102, 102, 102));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Total");
        jLabel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel14.setOpaque(true);
        jLayeredPane1.add(jLabel14);
        jLabel14.setBounds(150, 0, 80, 20);

        txtTotalNet.setEditable(false);
        txtTotalNet.setBackground(new java.awt.Color(204, 204, 255));
        txtTotalNet.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotalNet.setForeground(new java.awt.Color(153, 0, 0));
        txtTotalNet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalNet.setText("0.00");
        jLayeredPane1.add(txtTotalNet);
        txtTotalNet.setBounds(480, 0, 80, 20);

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(204, 204, 255));
        txtTotal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotal.setForeground(new java.awt.Color(153, 0, 0));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0.00");
        jLayeredPane1.add(txtTotal);
        txtTotal.setBounds(230, 0, 110, 20);

        txtTotalBank.setEditable(false);
        txtTotalBank.setBackground(new java.awt.Color(204, 204, 255));
        txtTotalBank.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotalBank.setForeground(new java.awt.Color(153, 0, 0));
        txtTotalBank.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalBank.setText("0.00");
        jLayeredPane1.add(txtTotalBank);
        txtTotalBank.setBounds(340, 0, 70, 20);

        txtTotalInterest.setEditable(false);
        txtTotalInterest.setBackground(new java.awt.Color(204, 204, 255));
        txtTotalInterest.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotalInterest.setForeground(new java.awt.Color(153, 0, 0));
        txtTotalInterest.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalInterest.setText("0.00");
        jLayeredPane1.add(txtTotalInterest);
        txtTotalInterest.setBounds(410, 0, 70, 20);

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseReleased(evt);
            }
        });
        jScrollPane2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jScrollPane2PropertyChange(evt);
            }
        });

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBackground(new java.awt.Color(255, 255, 241));
        jTable1.setForeground(new java.awt.Color(153, 0, 0));
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
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Chq. No", "Chq. Date", "Name", "Voucher No.", "Amount", "Bank Charges", "Intrest Charges", "Net", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setMaximumSize(new java.awt.Dimension(850, 320));
        jTable1.setMinimumSize(new java.awt.Dimension(850, 320));
        jTable1.setPreferredSize(new java.awt.Dimension(850, 320));
        jTable1.setRowHeight(20);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(220);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(8).setResizable(false);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(30);
        }

        cmdDeposit.setText("Deposit");
        cmdDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDepositActionPerformed(evt);
            }
        });

        cmdRealize.setText("Realize");
        cmdRealize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRealizeActionPerformed(evt);
            }
        });

        cmdBounsed.setText("Bounsed");
        cmdBounsed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBounsedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmdBounsed, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdDeposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdRealize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdChequeView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmdChequeView)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdBounsed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdRealize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDeposit))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed
    Act = 1;
    doNew();
}//GEN-LAST:event_cmdNewActionPerformed

    private void cmdChequeViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdChequeViewActionPerformed
        int i = jTable1.getSelectedRow();
        if (i >= 0) {
            new GUICheque(null, true, 1, ((OBJCheque) jTable1.getValueAt(i, 0)).getChqeNo()).setVisible(true);
        }
    }//GEN-LAST:event_cmdChequeViewActionPerformed

    private void cmdExit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExit1ActionPerformed
        setContentHistory();
    }//GEN-LAST:event_cmdExit1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        setButtonMode();
    }//GEN-LAST:event_jTable1MouseClicked

    private void chkRealisedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRealisedActionPerformed
        loadCheques();
        doCal();
    }//GEN-LAST:event_chkRealisedActionPerformed

    private void chkBouncedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBouncedActionPerformed
        loadCheques();
        doCal();
    }//GEN-LAST:event_chkBouncedActionPerformed

    private void chkToBeRealisedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkToBeRealisedActionPerformed
        loadCheques();
        doCal();
    }//GEN-LAST:event_chkToBeRealisedActionPerformed

    private void cmdDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDepositActionPerformed
        System.out.println("Che" + ((OBJCheque) jTable1.getValueAt(jTable1.getSelectedRow(), 0)).getChqeNo());
        if (jTable1.getSelectedRow() >= 0) {
            new GUIDeposit(null, true, ((OBJCheque) jTable1.getValueAt(jTable1.getSelectedRow(), 0))).setVisible(true);
            setButtonMode();
            setContentHistory();
        }
    }//GEN-LAST:event_cmdDepositActionPerformed

    private void chkDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDepositActionPerformed
        loadCheques();
        doCal();
    }//GEN-LAST:event_chkDepositActionPerformed

    private void cmdRealizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRealizeActionPerformed
        if (jTable1.getSelectedRow() >= 0) {
            int i = JOptionPane.showConfirmDialog(this, "Are you sure \nDo you want to continue this transaction?");
            if (i == JOptionPane.YES_OPTION) {
                doRealize();
                setButtonMode();
                setContentHistory();
            }
        }
    }//GEN-LAST:event_cmdRealizeActionPerformed

    private void cmdBounsedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBounsedActionPerformed
        if (jTable1.getSelectedRow() >= 0) {
            setButtonMode();
            setContentHistory();
        }
    }//GEN-LAST:event_cmdBounsedActionPerformed

    private void jScrollPane2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MousePressed
    }//GEN-LAST:event_jScrollPane2MousePressed

    private void jScrollPane2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseReleased

    }//GEN-LAST:event_jScrollPane2MouseReleased

    private void jScrollPane2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jScrollPane2PropertyChange

    }//GEN-LAST:event_jScrollPane2PropertyChange

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int q = JOptionPane.showConfirmDialog(null, "Are you sure do you want to delete ?", "delete Area", JOptionPane.YES_NO_OPTION);

        if (q == JOptionPane.YES_OPTION) {
            doDelete();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkBounced;
    private javax.swing.JCheckBox chkDeposit;
    private javax.swing.JCheckBox chkRealised;
    private javax.swing.JCheckBox chkToBeRealised;
    private javax.swing.JButton cmdArea3;
    private javax.swing.JButton cmdBounsed;
    private javax.swing.JButton cmdChequeView;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdDeposit;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdExit;
    private javax.swing.JButton cmdExit1;
    private javax.swing.JButton cmdFind;
    private javax.swing.JButton cmdFirst;
    private javax.swing.JButton cmdLast;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdNext;
    private javax.swing.JButton cmdPrev;
    private javax.swing.JButton cmdRealize;
    private javax.swing.JButton cmdReport;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtArea3;
    private javax.swing.JTextField txtAreaCode3;
    private com.toedter.calendar.JDateChooser txtFromDate;
    private com.toedter.calendar.JDateChooser txtToDate;
    private javax.swing.JFormattedTextField txtTotal;
    private javax.swing.JFormattedTextField txtTotalBank;
    private javax.swing.JFormattedTextField txtTotalInterest;
    private javax.swing.JFormattedTextField txtTotalNet;
    // End of variables declaration//GEN-END:variables
    private OBJCheque obj;
    private ArrayList<OBJCheque> obja;

    //Cheque
    private OBJCheque cheque;

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
}
