/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.system_transaction.payment.multy_payment;

import core.Locals;
import core.system_transaction.TransactionType;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.payment.PaymentSetting;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import system.accounts.transaction.cheque.OBJCheque;

/**
 *
 * @author RoWi
 */
public class MultiPayOption extends javax.swing.JDialog {

    /**
     * Creates new form MultiPayOption
     */
    public MultiPayOption(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initOthers();
    }

    public MultiPayOption(Object object, boolean b, String amount, ArrayList<OBJPaymentInfo> PaymentInfo, String voucherType, ArrayList<OBJCheque> chequeList) {
        this(null, b);
        initOthers();
        this.amount = Double.parseDouble(amount);
        lblAmount.setText("Payment : " + amount);
        this.voucherType = voucherType;
        cheques = chequeList;
        if (PaymentInfo != null) {
            infos = new ArrayList<OBJPaymentInfo>();
            infos = PaymentInfo;
            loadPayment();
        }
    }

    private void setButtonMode() {
        if (jTable1.getSelectedRow() >= 0) {
            cmdRemove.setEnabled(true);
        } else {
            cmdRemove.setEnabled(!true);
        }
    }

    private void removeRow() {
        int row = jTable1.getSelectedRow();
        ((DefaultTableModel) jTable1.getModel()).removeRow(row);
        doCall();
    }

    private void addCheque() {
        MultiPayOption.cheque = null;
        new ChequePanle(null, true, voucherType).setVisible(true);

        if (MultiPayOption.cheque != null) {
            loadTable();
        }
    }

    private void loadTable() {
        OBJCheque cheque = MultiPayOption.cheque;
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        dt.addRow(new Object[]{cheque.getChqeNo(), cheque.getRDate(), cheque.getBank(), cheque.getAmount(), cheque.getRemarks(), cheque.getBankAccount()});
        doCall();
    }

    private void doCall() {
        double cash = 0.00;
        double cheque = 0.00;

        if (!txtCash.getText().isEmpty()) {
            cash = Double.parseDouble(txtCash.getText());
        }
        if (jTable1.getRowCount() > 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                cheque = cheque + Double.parseDouble(jTable1.getValueAt(i, 3).toString());
            }
        }
        txtCheque.setText(Locals.currencyFormat(cheque));
        txtValue.setText(Locals.currencyFormat(cash + cheque));
    }

    private void clearTable() {
        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            ((DefaultTableModel) jTable1.getModel()).removeRow(0);
        }
    }

    private void doContinue() {
        if (Double.parseDouble(txtValue.getText()) == amount) {
            OBJPaymentInfo info;
            if (infos == null) {
                infos = new ArrayList<OBJPaymentInfo>();
            }
// CASH PAYMENT INFO
            info = new OBJPaymentInfo();
            info.setAmount(txtCash.getText());
            switch (voucherType) {
                case VoucherType.RECEIPT:
                    info.setPaymentSetting(PaymentSetting.RECEIPT_CASH);
                    info.setTransactionType(TransactionType.RECEIPT);
                    break;
                case VoucherType.VOUCHER:
                    info.setPaymentSetting(PaymentSetting.VOUCHER_CASH);
                    info.setTransactionType(TransactionType.VOUCHER);
                    break;
                case VoucherType.INVOICE:
                    info.setPaymentSetting(PaymentSetting.INVOICE_CASH);
                    info.setTransactionType(TransactionType.INVOICE);
                    break;
                case VoucherType.GRN:
                    info.setPaymentSetting(PaymentSetting.GRN_CASH);
                    info.setTransactionType(TransactionType.GRN);
                    break;
            }
            infos.add(info);
// END OF CASH PAYMENT
// CHEQUE PAYMENT INFO
            int row = jTable1.getRowCount();
            if (row > 0) {
                OBJCheque cheque;
                cheques = new ArrayList<>();
                for (int i = 0; i < row; i++) {
                    cheque = new OBJCheque();

                    cheque.setChqeNo(jTable1.getValueAt(i, 0).toString());
                    cheque.setRDate(jTable1.getValueAt(i, 1).toString());
                    cheque.setBank(jTable1.getValueAt(i, 2).toString());
                    cheque.setAmount(jTable1.getValueAt(i, 3).toString());
                    cheque.setRemarks(jTable1.getValueAt(i, 4).toString());
                    switch (voucherType) {
                        case VoucherType.VOUCHER:
                            cheque.setBankAccount(jTable1.getValueAt(i, 5).toString());
                            break;
                        case VoucherType.RECEIPT:
                            cheque.setBankAccount("");
                            break;
                        case VoucherType.INVOICE:
                            cheque.setBankAccount("");
                            break;
                        case VoucherType.GRN:
                            cheque.setBankAccount(jTable1.getValueAt(i, 5).toString());
                            break;
                    }
                    cheques.add(cheque);

                    info = new OBJPaymentInfo();
                    info.setAmount(jTable1.getValueAt(i, 3).toString());
                    info.setReferance_no(jTable1.getValueAt(i, 0).toString());
                    switch (voucherType) {
                        case VoucherType.RECEIPT:
                            info.setPaymentSetting(PaymentSetting.RECEIPT_CHEQUE);
                            info.setTransactionType(TransactionType.RECEIPT);
                            break;
                        case VoucherType.VOUCHER:
                            info.setPaymentSetting(PaymentSetting.VOUCHER_CHEQUE);
                            info.setTransactionType(TransactionType.VOUCHER);
                            break;
                        case VoucherType.INVOICE:
                            info.setPaymentSetting(PaymentSetting.INVOICE_CHEQUE);
                            info.setTransactionType(TransactionType.INVOICE);
                            break;
                        case VoucherType.GRN:
                            info.setPaymentSetting(PaymentSetting.GRN_CHEQUE);
                            info.setTransactionType(TransactionType.GRN);
                            break;
                    }

                    infos.add(info);
                }
            }

//END OF CHEQUE PAYMENT INFO
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect payment value...");
        }
    }

    private void loadPayment() {
        for (OBJPaymentInfo oBJPaymentInfo : infos) {
            if (oBJPaymentInfo.getPaymentSetting().equals(PaymentSetting.RECEIPT_CASH) || oBJPaymentInfo.getPaymentSetting().equals(PaymentSetting.VOUCHER_CASH)) {
                txtCash.setText(oBJPaymentInfo.getAmount());
            }
        }
        if (cheques != null) {
            for (OBJCheque cheque : cheques) {
                DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
                dt.addRow(new Object[]{cheque.getChqeNo(), cheque.getRDate(), cheque.getBank(), cheque.getAmount(), cheque.getRemarks()});
            }
        }
        doCall();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initOthers() {
        setButtonMode();
        clearTable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtAmount = new javax.swing.JFormattedTextField();
        txtDate = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCash = new javax.swing.JFormattedTextField();
        txtValue = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        cmdAdd = new javax.swing.JButton();
        cmdRemove = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCheque = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblAmount = new javax.swing.JLabel();

        txtAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        txtDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Multi Pay Option");
        setMaximumSize(new java.awt.Dimension(521, 281));
        setMinimumSize(new java.awt.Dimension(521, 281));
        setUndecorated(true);

        jLabel1.setText("Value");

        jLabel2.setText("Cash");

        txtCash.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCash.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCash.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCash.setText("0.00");
        txtCash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCashKeyReleased(evt);
            }
        });

        txtValue.setEditable(false);
        txtValue.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtValue.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValue.setText("0.00");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cheque Payments"));

        jTable1.setBackground(new java.awt.Color(244, 244, 232));
        jTable1.setForeground(new java.awt.Color(102, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cheque No", "Date", "Bank", "Amount", "Remark", "Account"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(txtDate));
            jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(txtAmount));
        }

        cmdAdd.setText("ADD CHEQUE");
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddActionPerformed(evt);
            }
        });

        cmdRemove.setText("REMOVE");
        cmdRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cmdAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdRemove))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmdRemove)
                    .addComponent(cmdAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
        );

        jLabel4.setText("Cheque");

        txtCheque.setEditable(false);
        txtCheque.setBackground(new java.awt.Color(204, 204, 255));
        txtCheque.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCheque.setForeground(new java.awt.Color(102, 0, 0));
        txtCheque.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCheque.setText("0.00");
        txtCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChequeActionPerformed(evt);
            }
        });

        jButton1.setText("Continue >>");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("<< Back");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        lblAmount.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(txtCash, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCheque))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAmount)))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChequeActionPerformed

    private void cmdRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveActionPerformed
        removeRow();
    }//GEN-LAST:event_cmdRemoveActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        setButtonMode();
    }//GEN-LAST:event_jTable1MouseClicked

    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
        addCheque();
    }//GEN-LAST:event_cmdAddActionPerformed

    private void txtCashKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCashKeyReleased
        doCall();
    }//GEN-LAST:event_txtCashKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        doContinue();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        MultiPayOption.cheque = null;
        PaymentTerms.payment = null;
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {

            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MultiPayOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MultiPayOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MultiPayOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MultiPayOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MultiPayOption dialog = new MultiPayOption(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdRemove;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JFormattedTextField txtAmount;
    private javax.swing.JFormattedTextField txtCash;
    private javax.swing.JFormattedTextField txtCheque;
    private javax.swing.JFormattedTextField txtDate;
    private javax.swing.JFormattedTextField txtValue;
    // End of variables declaration//GEN-END:variables
    public static OBJCheque cheque = null;
    public static ArrayList<OBJCheque> cheques = null;
    public static ArrayList<OBJPaymentInfo> infos = null;
    private double amount;
    private String voucherType;
}
