/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.voucher_cancel;

import core.Locals;
import core.system_transaction.TransactionType;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RoWi
 */
public class VoucherCancel extends javax.swing.JInternalFrame {

    /**
     * Creates new form VoucherCancel
     */
    public VoucherCancel() {
        initComponents();
        initOthers();
    }

    public VoucherCancel(String transType) {
        initComponents();
        this.transType = transType;
        initOthers();
        if (transType.equals(TransactionType.VOUCHER)) {
            jLabel14.setText("Voucher Cancel");
        } else {
            jLabel14.setText("Receipt Cancel");
        }
    }

    private void getVouchers() {
        vouchers = SERVoucherCancel.getVoucherHeaders(Locals.setDateFormat(txtTransDate.getDate()), transType);
        if (vouchers != null) {
            loadTable();
        }
    }

    private void loadTable() {
        clearTable(jTable1);
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        int i = 0;
        for (OBJVoucher oBJVoucher : vouchers) {
            dt.addRow(new Object[]{oBJVoucher, oBJVoucher.getVoucherDate(), oBJVoucher.getNarration(), oBJVoucher.getAmount()});
            i++;
        }
        for (int j = i; j < 22; j++) {
            dt.addRow(new Object[]{"", "", "", ""});
        }

    }

    private void clearTable(JTable table) {
        DefaultTableModel dt = (DefaultTableModel) table.getModel();
        int row = table.getRowCount();
        for (int i = 0; i < row; i++) {
            dt.removeRow(0);
        }
    }

    private void loadVoucher() {
        clearTable(tblCredit);
        resetAll();
        int i = jTable1.getSelectedRow();
        int row = 0;
        DefaultTableModel dt = (DefaultTableModel) tblCredit.getModel();
        if (i >= 0) {
            if (jTable1.getValueAt(i, 0) != null && !jTable1.getValueAt(i, 0).equals("")) {
                OBJVoucher voucher = (OBJVoucher) jTable1.getValueAt(i, 0);
                vouchers = SERVoucherCancel.getVouchers(Locals.setDateFormat(txtTransDate.getDate()), transType, voucher.getVoucherNo());
                txtjvNo.setText(voucher.getVoucherNo());
                txtJVDate.setDate(Locals.toDate(voucher.getVoucherDate()));
                txtAmount.setText(voucher.getAmount());
                txtInvoNo.setText(voucher.getInvoiceNo());
                txtCustCode.setText(voucher.getCustCode());
                txtLoan.setText(voucher.getLoan());
                txtDocumentNo.setText(voucher.getDocumentNo());
                txtCostCenter.setText(voucher.getCostCenter());
                txtNarration.setText(voucher.getNarration());

                for (OBJVoucher oBJVoucher : vouchers) {
                    dt.addRow(new Object[]{
                        oBJVoucher.getAccount(),
                        "",
                        oBJVoucher.getDescription(),
                        Double.parseDouble(oBJVoucher.getDebitAmount()) > 0 ? Locals.sCurrencyFormat(oBJVoucher.getDebitAmount()) : "",
                        Double.parseDouble(oBJVoucher.getCreditAmount()) > 0 ? Locals.sCurrencyFormat(oBJVoucher.getCreditAmount()) : ""});
                    row++;
                }
                cmdTransactionCancel.setEnabled(true);
            } else {
                cmdTransactionCancel.setEnabled(!true);
            }
        }
        for (int j = row; j < 9; j++) {
            dt.addRow(new Object[]{"", "", "", ""});
        }
    }

    private void resetAll() {
        txtjvNo.setText("");
        txtJVDate.setDate(null);
        txtAmount.setText("");
        txtInvoNo.setText("");
        txtCustCode.setText("");
        txtLoan.setText("");
        txtDocumentNo.setText("");
        txtCostCenter.setText("");
        txtNarration.setText("");
    }

    private void doCancel() {
        if (SERVoucherCancel.doCancel(txtjvNo.getText(), txtInvoNo.getText(), transType, txtAmount.getText())) {
            JOptionPane.showMessageDialog(null, "Cancel sucsesful..");
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
        txtTransDate.setDate(Locals.toDate(Locals.currentDate_F2()));
        getVouchers();
        clearTable(tblCredit);
        loadVoucher();
        jTable1.setDefaultRenderer(Object.class, new DefaultCellRenderClass());
        tblCredit.setDefaultRenderer(Object.class, new DefaultCellRenderClass());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        txtTransDate = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
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
        tblCredit = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JFormattedTextField();
        txtNarration = new javax.swing.JTextField();
        txtDocumentNo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtLoan = new javax.swing.JTextField();
        cmdTransactionCancel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtCostName = new javax.swing.JTextField();
        txtCostCenter = new javax.swing.JTextField();
        txtCustName = new javax.swing.JTextField();
        txtCustCode = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtInvoNo = new javax.swing.JTextField();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Receipt/Voucher Cancel");
        setToolTipText("");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTable1.setFont(new java.awt.Font("Calibri", 1, 13)); // NOI18N
        jTable1.setForeground(new java.awt.Color(102, 102, 102));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Rec/Vou. No", "Date", "Narration", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(153, 255, 255));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jLabel2.setText("Search");

        jButton2.setText("Search");

        jLabel9.setText("Transaction Date");

        jButton3.setText("Load");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTransDate, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(txtTransDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("Customer");

        jLabel5.setText("Narration");

        txtjvNo.setEditable(false);
        txtjvNo.setBackground(new java.awt.Color(255, 255, 233));
        txtjvNo.setForeground(new java.awt.Color(153, 0, 0));
        txtjvNo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        txtjvNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjvNoActionPerformed(evt);
            }
        });

        jLabel3.setText("J.V. Date");

        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setText("Rec/Vou. No.");

        jLabel6.setText("Accountant");

        jLabel7.setText("Approved By");

        txtAccountant.setEditable(false);
        txtAccountant.setBackground(new java.awt.Color(237, 253, 253));
        txtAccountant.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtApproved.setEditable(false);
        txtApproved.setBackground(new java.awt.Color(237, 253, 253));
        txtApproved.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtJVDate.setBackground(new java.awt.Color(230, 253, 253));
        txtJVDate.setDateFormatString("yyyy-MM-dd");
        txtJVDate.setEnabled(false);

        tblCreditPnl.setBorder(javax.swing.BorderFactory.createTitledBorder("Credit Account"));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblCredit.setModel(new javax.swing.table.DefaultTableModel(
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
                "Account", "Account Name", "Description", "Debit", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCredit.setGridColor(new java.awt.Color(153, 255, 255));
        tblCredit.setOpaque(false);
        jScrollPane2.setViewportView(tblCredit);
        if (tblCredit.getColumnModel().getColumnCount() > 0) {
            tblCredit.getColumnModel().getColumn(0).setResizable(false);
            tblCredit.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblCredit.getColumnModel().getColumn(1).setResizable(false);
            tblCredit.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblCredit.getColumnModel().getColumn(2).setResizable(false);
            tblCredit.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblCredit.getColumnModel().getColumn(3).setResizable(false);
            tblCredit.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblCredit.getColumnModel().getColumn(4).setResizable(false);
            tblCredit.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        javax.swing.GroupLayout tblCreditPnlLayout = new javax.swing.GroupLayout(tblCreditPnl);
        tblCreditPnl.setLayout(tblCreditPnlLayout);
        tblCreditPnlLayout.setHorizontalGroup(
            tblCreditPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
        );
        tblCreditPnlLayout.setVerticalGroup(
            tblCreditPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tblCreditPnlLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        jLabel11.setText("Amount");

        txtAmount.setEditable(false);
        txtAmount.setBackground(new java.awt.Color(237, 253, 253));
        txtAmount.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        txtAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        txtNarration.setEditable(false);
        txtNarration.setBackground(new java.awt.Color(237, 253, 253));
        txtNarration.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtDocumentNo.setEditable(false);
        txtDocumentNo.setBackground(new java.awt.Color(237, 253, 253));
        txtDocumentNo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        txtDocumentNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentNoActionPerformed(evt);
            }
        });

        jLabel4.setText("Document No");

        jLabel12.setText("Cost Center");

        jLabel8.setText("Loan No.");

        txtLoan.setEditable(false);
        txtLoan.setBackground(new java.awt.Color(237, 253, 253));
        txtLoan.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        cmdTransactionCancel.setText("Cancel Transaction");
        cmdTransactionCancel.setEnabled(false);
        cmdTransactionCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTransactionCancelActionPerformed(evt);
            }
        });

        txtCostName.setEditable(false);
        txtCostName.setBackground(new java.awt.Color(255, 255, 241));
        txtCostName.setForeground(new java.awt.Color(153, 0, 0));
        txtCostName.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtCostCenter.setEditable(false);
        txtCostCenter.setBackground(new java.awt.Color(237, 253, 253));
        txtCostCenter.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtCustName.setEditable(false);
        txtCustName.setBackground(new java.awt.Color(255, 255, 241));
        txtCustName.setForeground(new java.awt.Color(153, 0, 0));
        txtCustName.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtCustCode.setEditable(false);
        txtCustCode.setBackground(new java.awt.Color(237, 253, 253));
        txtCustCode.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel15.setText("Invoice No.");

        txtInvoNo.setEditable(false);
        txtInvoNo.setBackground(new java.awt.Color(237, 253, 253));
        txtInvoNo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(tblCreditPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAccountant, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtApproved, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(6, 6, 6)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtjvNo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(23, 23, 23)
                                .addComponent(txtJVDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtNarration)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDocumentNo))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCostCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCostName))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCustCode, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCustName))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addGap(13, 13, 13)
                                .addComponent(txtInvoNo))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmdTransactionCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtJVDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtjvNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel11)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(txtInvoNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel10)
                            .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel8)
                            .addComponent(txtLoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDocumentNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel12)
                            .addComponent(txtCostName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCostCenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNarration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblCreditPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApproved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtAccountant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdTransactionCancel)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jLayeredPane2.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLayeredPane2.setOpaque(true);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/RAP-Logo xsmall copy.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Receipt/Voucher Cancel");

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );
        jLayeredPane2.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)
            .addComponent(jLayeredPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDocumentNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentNoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        getVouchers();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtjvNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjvNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjvNoActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        loadVoucher();
    }//GEN-LAST:event_jTable1MouseClicked

    private void cmdTransactionCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTransactionCancelActionPerformed
        doCancel();
    }//GEN-LAST:event_cmdTransactionCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdTransactionCancel;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblCredit;
    private javax.swing.JPanel tblCreditPnl;
    private javax.swing.JTextField txtAccountant;
    private javax.swing.JFormattedTextField txtAmount;
    private javax.swing.JTextField txtApproved;
    private javax.swing.JTextField txtCostCenter;
    private javax.swing.JTextField txtCostName;
    private javax.swing.JTextField txtCustCode;
    private javax.swing.JTextField txtCustName;
    private javax.swing.JTextField txtDocumentNo;
    private javax.swing.JTextField txtInvoNo;
    private com.toedter.calendar.JDateChooser txtJVDate;
    private javax.swing.JTextField txtLoan;
    private javax.swing.JTextField txtNarration;
    private com.toedter.calendar.JDateChooser txtTransDate;
    private javax.swing.JTextField txtjvNo;
    // End of variables declaration//GEN-END:variables
    private ArrayList<OBJVoucher> vouchers;
    private String transType;
}
