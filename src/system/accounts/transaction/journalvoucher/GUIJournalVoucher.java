/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIJournalVoucher.java
 *
 * Created on Jul 19, 2013, 11:53:24 AM
 */
package system.accounts.transaction.journalvoucher;

import accountpackage.AccountPackage;
import core.Locals;
import core.system_transaction.TransactionType;
import core.system_transaction.account_trans.AccountTransStatus;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.transaction.OBJTransactionHistory;
import core.system_transaction.transaction.TransactionStatus;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.findwindow.GUIFindWindow;

/**
 *
 * @author dell
 */
public class GUIJournalVoucher extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUIJournalVoucher
     */
    public GUIJournalVoucher() {
        initComponents();
        initOthers();
    }

    private void doNew() {
        txtJVDate.setDate(Locals.toDate(Locals.currentDate_F2()));
        txtRemarks.setText("");
        txtTotalDebit.setText("0.00");
        txtTotalCredit.setText("0.00");
        txtDebitDefference.setText("0.00");
        txtCreditDefference.setText("0.00");
        clearTable();
        setID();
        buttonMode();
    }

    private void setID() {
        txtJVNo.setText(SERJournalVoucher.getID());
    }

    private void doSave() {
        if (checkAll()) {
            journalEntry.setJournalEntryNo(txtJVNo.getText());
            journalEntry.setJournalEntryDate(Locals.setDateFormat(txtJVDate.getDate()));
            journalEntry.setRemark(txtRemarks.getText());
            journalEntry.setAmount(txtTotalDebit.getText());

            transaction();
            accountTransaction();
            paymentsInfo = null;
            payment = null;
            itemTransactions = null;

            if (SERJournalVoucher.save(journalEntry, transaction, payment, paymentsInfo, accountTranses, itemTransactions)) {
                JOptionPane.showMessageDialog(null, "Saved successful...");
                doNew();
            } else {
                JOptionPane.showMessageDialog(null, "Saving fail...");
            }
        }
    }

    private void transaction() {
        // Transaction Object
        transaction = new OBJTransaction();
        transaction.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
        transaction.setTransactionType(TransactionType.JOURNAL_ENTRY);
        transaction.setReferanceNo(txtJVNo.getText());
        transaction.setDocumentNo(null);
        transaction.setLoan(null);
        transaction.setCostCode(txtCostCode.getText());
        transaction.setCustomerCode(null);
        transaction.setNote(txtRemarks.getText());
        transaction.setStatus(TransactionStatus.ACTIVE);

    }

    private boolean accountTransaction() {
        accountTranses = new ArrayList<>();

        // Account Transaction
        // Debit Account Trans
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            OBJAccountTrans accountTrans = new OBJAccountTrans();
            accountTrans.setTransactionDate(Locals.setDateFormat(txtJVDate.getDate()));
            accountTrans.setCostCode(txtCostCode.getText());
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(jTable1.getValueAt(i, 3).toString());
            accountTrans.setAccount(jTable1.getValueAt(i, 1).toString());
            accountTrans.setCreditAmount(jTable1.getValueAt(i, 5).toString());
            accountTrans.setDebitAmount(jTable1.getValueAt(i, 4).toString());
            accountTrans.setTransactionType(TransactionType.JOURNAL_ENTRY);
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransStatus.ACTIVE);
            accountTranses.add(accountTrans);
        }
        return true;
    }

    private boolean checkAll() {
        doCall();

        double debitAmount = Double.parseDouble(txtTotalDebit.getText());
        double creditAmount = Double.parseDouble(txtTotalCredit.getText());

        boolean defarance = (debitAmount == creditAmount);
        boolean jvNo = !txtJVNo.getText().isEmpty();
        boolean jvDate = txtJVDate.getDate() != null;
        boolean table = jTable1.getRowCount() > 0;

        if (!defarance) {
            JOptionPane.showMessageDialog(null, "Debit amount and credit amount dose not match...");
        } else if (!jvNo || !jvDate) {
            JOptionPane.showMessageDialog(null, "One or more required fields are empty...");
        } else if (!table) {
            JOptionPane.showMessageDialog(null, "There is no entrys...");
        }

        return defarance && jvNo && jvDate && table;
    }

    private void addEntry() {
        entryPanal = new JournalEntryPanal(null, true);
        entryPanal.setVisible(true);
        if (JournalEntryPanal.entry != null && !JournalEntryPanal.entry.getAccount().equals("") && JournalEntryPanal.entry.getAccount() != null) {
            loadTable();
        }
    }

    private void loadTable() {
        this.entry = JournalEntryPanal.entry;
        journalEntry.getEntrys().add(entry);
        JournalEntryPanal.entry = null;
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        dt.addRow(new Object[]{"", entry.getAccount(), entry.getAccountName(), entry.getNaration(), entry.getDebitAmount(), entry.getCreditAmount()});
        doCall();
    }

    private void doCall() {
        double debitAmount = 0.00;
        double creditAmount = 0.00;

        if (jTable1.getRowCount() > 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                debitAmount = debitAmount + Double.parseDouble(jTable1.getValueAt(i, 4).toString());
                creditAmount = creditAmount + Double.parseDouble(jTable1.getValueAt(i, 5).toString());
            }
        }

        txtTotalDebit.setText(Locals.currencyFormat(debitAmount));
        txtTotalCredit.setText(Locals.currencyFormat(creditAmount));
        if (debitAmount > creditAmount) {
            txtDebitDefference.setText(Locals.currencyFormat(debitAmount - creditAmount));
            txtCreditDefference.setText("0.00");
        } else {
            txtCreditDefference.setText(Locals.currencyFormat(creditAmount - debitAmount));
            txtDebitDefference.setText("0.00");
        }
    }

    private void clearTable() {
        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            ((DefaultTableModel) jTable1.getModel()).removeRow(0);
        }
    }

    private void buttonMode() {
        int i = jTable1.getSelectedRowCount();
        if (i > 0) {
            cmdRemove.setEnabled(true);
        } else {
            cmdRemove.setEnabled(!true);
        }
    }

    private void removeRow() {
        int i = jTable1.getSelectedRowCount();
        if (i > 0) {
            int row = jTable1.getSelectedRow();
            journalEntry.getEntrys().remove(row);
            ((DefaultTableModel) jTable1.getModel()).removeRow(row);
        }
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

    private void doView() {
        setStatus(false);
        resetAll();
    }

    private void setStatus(boolean b) {
        txtJVNo.setEnabled(b);
        txtJVDate.setEnabled(b);
        txtCostCode.setEnabled(b);
        txtRemarks.setEnabled(b);

        jTable1.setEnabled(b);

        cmdArea2.setEnabled(b);
        cmdAdd.setEnabled(b);
        cmdRemove.setEnabled(b);
        cmdFind.setEnabled(!b);
        cmdNew.setEnabled(!b);
        cmdDelete.setEnabled(!b);
    }

    private void resetAll() {
        clearTable();
        txtJVNo.setText(null);
        txtJVDate.setDate(Locals.toDate(Locals.currentDate_F2()));
        txtCostCode.setText(null);
        txtRemarks.setText(null);
        txtTotalCredit.setText("0.00");
        txtTotalDebit.setText("0.00");
        txtCreditDefference.setText("0.00");
        txtDebitDefference.setText("0.00");
        doNew();
    }

    private void doFind() {
        new GUIFindWindow(null, true, "journal", "journal_no", "Remark", "journal_date = '" + AccountPackage.company.getWorkingDate() + "' AND status", "ACTIVE").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        journalEntry = SERJournalVoucher.search(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
        if (journalEntry.getJournalEntryNo() != null && !journalEntry.getJournalEntryNo().equals("")) {
            setContent();
        }
    }

    private void setContent() {
        txtJVNo.setText(journalEntry.getJournalEntryNo());
        txtJVDate.setDate(Locals.toDate(journalEntry.getJournalEntryDate()));
        txtRemarks.setText(journalEntry.getRemark());

        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        for (OBJEntry entry : journalEntry.getEntrys()) {
            dt.addRow(new Object[]{"", entry.getAccount(), entry.getAccountName(), entry.getNaration(), entry.getDebitAmount(), entry.getCreditAmount()});
            doCall();
        }
    }

    private void doCancel() {
        if (SERJournalVoucher.doCancel(txtJVNo.getText())) {
            JOptionPane.showMessageDialog(null, "Cancel sucsesful..");
            doNew();
        } else {
            JOptionPane.showMessageDialog(null, "Cancel fail..");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initOthers() {
        journalEntry = new OBJJournalEntry();
        clearTable();
        buttonMode();
        doNew();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accode = new javax.swing.JComboBox();
        costcenter = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtJVNo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtRemarks = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        txtCreditDefference = new javax.swing.JFormattedTextField();
        txtTotalCredit = new javax.swing.JFormattedTextField();
        txtTotalDebit = new javax.swing.JFormattedTextField();
        txtDebitDefference = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmdRemove = new javax.swing.JButton();
        cmdAdd = new javax.swing.JButton();
        txtJVDate = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        txtCostCode = new javax.swing.JTextField();
        cmdArea2 = new javax.swing.JButton();
        txtCostCenter = new javax.swing.JTextField();

        accode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        costcenter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("jLabel8");

        setClosable(true);
        setTitle("Journal Voucher");

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
        cmdFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFindActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdFind);
        jToolBar1.add(jSeparator1);

        cmdNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/new.png"))); // NOI18N
        cmdNew.setToolTipText("Add New (F5)");
        cmdNew.setAlignmentX(0.5F);
        cmdNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdNew.setEnabled(false);
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
        cmdDelete.setToolTipText("Cancel (F7)");
        cmdDelete.setAlignmentX(0.5F);
        cmdDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdDelete.setEnabled(false);
        cmdDelete.setFocusable(false);
        cmdDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdDelete.setIconTextGap(5);
        cmdDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdDelete);

        cmdView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/magnifier.png"))); // NOI18N
        cmdView.setToolTipText("View (F8)");
        cmdView.setAlignmentX(0.5F);
        cmdView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdView.setFocusable(false);
        cmdView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdView.setIconTextGap(5);
        cmdView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdViewActionPerformed(evt);
            }
        });
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
        cmdReport.setEnabled(false);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setText("J.V. Number");

        txtJVNo.setEditable(false);
        txtJVNo.setBackground(new java.awt.Color(255, 255, 233));
        txtJVNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setText("J.V. Date");

        txtRemarks.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel5.setText("Remarks");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "A/C Code", "A/C Name", "Narration", "Debit", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(55);
            jTable1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(accode));
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(125);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(135);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(70);
        }

        txtCreditDefference.setEditable(false);
        txtCreditDefference.setBackground(new java.awt.Color(204, 204, 255));
        txtCreditDefference.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCreditDefference.setForeground(new java.awt.Color(153, 0, 0));
        txtCreditDefference.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCreditDefference.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLayeredPane1.add(txtCreditDefference);
        txtCreditDefference.setBounds(330, 20, 100, 20);

        txtTotalCredit.setEditable(false);
        txtTotalCredit.setBackground(new java.awt.Color(204, 204, 255));
        txtTotalCredit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotalCredit.setForeground(new java.awt.Color(153, 0, 0));
        txtTotalCredit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalCredit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLayeredPane1.add(txtTotalCredit);
        txtTotalCredit.setBounds(330, 0, 100, 20);

        txtTotalDebit.setEditable(false);
        txtTotalDebit.setBackground(new java.awt.Color(204, 204, 255));
        txtTotalDebit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotalDebit.setForeground(new java.awt.Color(153, 0, 0));
        txtTotalDebit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalDebit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLayeredPane1.add(txtTotalDebit);
        txtTotalDebit.setBounds(240, 0, 90, 20);

        txtDebitDefference.setEditable(false);
        txtDebitDefference.setBackground(new java.awt.Color(204, 204, 255));
        txtDebitDefference.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDebitDefference.setForeground(new java.awt.Color(153, 0, 0));
        txtDebitDefference.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtDebitDefference.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLayeredPane1.add(txtDebitDefference);
        txtDebitDefference.setBounds(240, 20, 90, 20);

        jLabel6.setBackground(new java.awt.Color(102, 102, 102));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Difference");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel6.setOpaque(true);
        jLayeredPane1.add(jLabel6);
        jLabel6.setBounds(120, 20, 120, 20);

        jLabel7.setBackground(new java.awt.Color(102, 102, 102));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Total");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel7.setOpaque(true);
        jLayeredPane1.add(jLabel7);
        jLabel7.setBounds(120, 0, 120, 20);

        cmdRemove.setText("REMOVE");
        cmdRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveActionPerformed(evt);
            }
        });

        cmdAdd.setText("ADD");
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddActionPerformed(evt);
            }
        });

        txtJVDate.setDateFormatString("yyyy-MM-dd");

        jLabel10.setText("Cost Center");

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
        txtCostCenter.setBounds(75, 0, 170, 18);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(cmdAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdRemove))
                            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJVNo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJVDate, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLayeredPane3)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtJVNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(txtJVDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(cmdAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(249, 249, 249))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
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

    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
        addEntry();
    }//GEN-LAST:event_cmdAddActionPerformed

    private void cmdRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveActionPerformed
        removeRow();
    }//GEN-LAST:event_cmdRemoveActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        buttonMode();
    }//GEN-LAST:event_jTable1MouseClicked

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        doSave();
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void cmdArea2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdArea2ActionPerformed
        getCost();
    }//GEN-LAST:event_cmdArea2ActionPerformed

    private void cmdViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdViewActionPerformed
        doView();
    }//GEN-LAST:event_cmdViewActionPerformed

    private void cmdFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdFindActionPerformed
        doFind();
    }//GEN-LAST:event_cmdFindActionPerformed

    private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed
        setStatus(true);
    }//GEN-LAST:event_cmdNewActionPerformed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        int i = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this entry");
        if (i == JOptionPane.YES_OPTION) {
            doCancel();
        }
    }//GEN-LAST:event_cmdDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox accode;
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdArea2;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdExit;
    private javax.swing.JButton cmdFind;
    private javax.swing.JButton cmdFirst;
    private javax.swing.JButton cmdLast;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdNext;
    private javax.swing.JButton cmdPrev;
    private javax.swing.JButton cmdRemove;
    private javax.swing.JButton cmdReport;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdView;
    private javax.swing.JComboBox costcenter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtCostCenter;
    private javax.swing.JTextField txtCostCode;
    private javax.swing.JFormattedTextField txtCreditDefference;
    private javax.swing.JFormattedTextField txtDebitDefference;
    private com.toedter.calendar.JDateChooser txtJVDate;
    private javax.swing.JTextField txtJVNo;
    private javax.swing.JTextField txtRemarks;
    private javax.swing.JFormattedTextField txtTotalCredit;
    private javax.swing.JFormattedTextField txtTotalDebit;
    // End of variables declaration//GEN-END:variables
    private JournalEntryPanal entryPanal;
    private OBJJournalEntry journalEntry;
    private OBJEntry entry;

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
