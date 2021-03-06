/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.cheque.cheque_deposit;

import core.system_transaction.TransactionType;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.transaction.OBJTransactionHistory;
import core.system_transaction.transaction.TransactionStatus;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import mainApp.SERCommen;
import mainApp.findwindow.GUIFindWindow;
import system.accounts.AccountType;
import system.accounts.transaction.cheque.ChequeStatus;
import system.accounts.transaction.cheque.OBJCheque;
import system.accounts.transaction.cheque.SERCheque;

/**
 *
 * @author RoWi
 */
public class GUIDeposit extends javax.swing.JDialog {

    /**
     * Creates new form GUIDeposit
     */
    public GUIDeposit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initOthers();
    }

    public GUIDeposit(Object object, boolean b, Object cheque) {
        this(null, b);
        initOthers();
        this.cheque = (OBJCheque) cheque;
        loadCheque();
    }

    private void loadCheque() {
        txtCustomer.setText(cheque.getCustCode());
        txtCustomerName.setText(SERCommen.getDescription("customer", cheque.getCustCode(), "AccCode", "PrintName"));
        txtChequeNo.setText(cheque.getChqeNo());
        txtDate.setText(cheque.getRDate());
        txtDepositAccount.setText(cheque.getDepositAccount());
        txtDepositAccountName.setText(SERCommen.getDescription("account", cheque.getDepositAccount(), "AccCode", "AccName"));
    }

    private void doSave() {
        if (checkField()) {
            cheque.setDepositAccount(txtDepositAccount.getText());
            cheque.setStatus(ChequeStatus.DEPOSIT);
            transaction();
            payment = null;
            paymentsInfo = null;
            accountTranses = null;
            itemTransactions = null;

            if (SERCheque.deposit(cheque, transaction, payment, paymentsInfo, accountTranses, itemTransactions)) {
                JOptionPane.showMessageDialog(null, "Deposit successful...");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Deposit fail...");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Blank entry not allowed.");
        }
    }

    private void transaction() {
        // Transaction Object
        transaction = new OBJTransaction();
        transaction.setTransactionDate(accountpackage.AccountPackage.company.getWorkingDate());
        transaction.setTransactionType(TransactionType.CHEQUE_DEPOSIT);
        transaction.setReferanceNo(txtSlipNo.getText());
        transaction.setDocumentNo(null);
        transaction.setLoan(null);
        transaction.setCostCode(accountpackage.AccountPackage.costCode);
        transaction.setCustomerCode(null);
        transaction.setNote(txtNote.getText());
        transaction.setStatus(TransactionStatus.ACTIVE);

    }

    private boolean checkField() {
        return !txtDepositAccount.getText().equals("");
    }

    private void getBankAccount() {
        new GUIFindWindow(null, true, "account", "AccCode", "AccName", "type", AccountType.BANK_ACCOUNT).setVisible(true);

        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtDepositAccount.setText(code);
        txtDepositAccountName.setText(name);
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
        cheque = new OBJCheque();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        txtChequeNo = new javax.swing.JTextField();
        txtCustomer = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        txtDepositAccount = new javax.swing.JTextField();
        cmdArea3 = new javax.swing.JButton();
        txtDepositAccountName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSlipNo = new javax.swing.JTextField();
        txtNote = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Cheque No");

        jLabel2.setText("Deposit Account");

        jLabel3.setText("Date");

        jLabel4.setText("Customer");

        txtDate.setEditable(false);

        txtChequeNo.setEditable(false);
        txtChequeNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChequeNoActionPerformed(evt);
            }
        });

        txtCustomer.setEditable(false);

        jButton1.setText("Deposit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtDepositAccount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtDepositAccount);
        txtDepositAccount.setBounds(0, 0, 70, 18);

        cmdArea3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdArea3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdArea3.setContentAreaFilled(false);
        cmdArea3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdArea3ActionPerformed(evt);
            }
        });
        jLayeredPane4.add(cmdArea3);
        cmdArea3.setBounds(72, 0, 21, 20);

        txtDepositAccountName.setEditable(false);
        txtDepositAccountName.setBackground(new java.awt.Color(255, 255, 241));
        txtDepositAccountName.setForeground(new java.awt.Color(153, 0, 0));
        txtDepositAccountName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtDepositAccountName);
        txtDepositAccountName.setBounds(95, 0, 190, 18);

        jLabel5.setText("Bank Slip No");

        jLabel6.setText("Note");

        txtCustomerName.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 234, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNote, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCustomerName))
                            .addComponent(txtChequeNo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDate)
                            .addComponent(txtSlipNo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane4))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtChequeNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSlipNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(3, 3, 3))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtChequeNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChequeNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChequeNoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        doSave();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmdArea3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdArea3ActionPerformed
        getBankAccount();
    }//GEN-LAST:event_cmdArea3ActionPerformed

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIDeposit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIDeposit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIDeposit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIDeposit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUIDeposit dialog = new GUIDeposit(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton cmdArea3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JTextField txtChequeNo;
    private javax.swing.JTextField txtCustomer;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDepositAccount;
    private javax.swing.JTextField txtDepositAccountName;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtSlipNo;
    // End of variables declaration//GEN-END:variables
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

}
