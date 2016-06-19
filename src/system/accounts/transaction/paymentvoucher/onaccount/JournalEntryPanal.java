/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.paymentvoucher.onaccount;

import javax.swing.JOptionPane;
import mainApp.findwindow.GUIFindWindow;

/**
 *
 * @author RoWi
 */
public class JournalEntryPanal extends javax.swing.JDialog {

    /**
     * Creates new form JournalEntryPanal
     */
    public JournalEntryPanal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void getAccount() {
        new GUIFindWindow(null, true, "Account", "AccCode", "AccName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtAccountCode.setText(code);
        txtAccountName.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void doAdd() {
        if (checkStatus()) {
            entry = new OBJEntry();
            entry.setAccount(txtAccountCode.getText());
            entry.setAccountName(txtAccountName.getText());
            entry.setNaration(txtNarration.getText());
            entry.setAmount(txtAmount.getText());
            System.out.println("Entr panal = " + entry.getAmount());
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "One or more required fields are empty...");
        }
    }

    private boolean checkStatus() {
        boolean b = false;
        double debitAmount = Double.parseDouble(txtAmount.getText());
        if (!txtAccountCode.getText().isEmpty() && debitAmount > 0.00) {
            b = true;
        }

        return b;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        txtAccountCode = new javax.swing.JTextField();
        cmdAccoumt = new javax.swing.JButton();
        txtAccountName = new javax.swing.JTextField();
        txtNarration = new javax.swing.JTextField();
        txtAmount = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Account");

        jLabel2.setText("Narration");

        jLabel3.setText("Amount");

        txtAccountCode.setEditable(false);
        txtAccountCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtAccountCode);
        txtAccountCode.setBounds(0, 0, 50, 18);

        cmdAccoumt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdAccoumt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdAccoumt.setContentAreaFilled(false);
        cmdAccoumt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAccoumtActionPerformed(evt);
            }
        });
        jLayeredPane3.add(cmdAccoumt);
        cmdAccoumt.setBounds(53, 0, 20, 20);

        txtAccountName.setEditable(false);
        txtAccountName.setBackground(new java.awt.Color(255, 255, 241));
        txtAccountName.setForeground(new java.awt.Color(153, 0, 0));
        txtAccountName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtAccountName);
        txtAccountName.setBounds(75, 0, 150, 18);

        txtNarration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNarrationActionPerformed(evt);
            }
        });

        txtAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtAmount.setText("0.00");
        txtAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountActionPerformed(evt);
            }
        });
        txtAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAmountFocusLost(evt);
            }
        });

        jButton1.setText("Cancel");

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNarration)
                            .addComponent(txtAmount))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNarration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
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
            java.util.logging.Logger.getLogger(JournalEntryPanal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JournalEntryPanal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JournalEntryPanal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JournalEntryPanal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JournalEntryPanal dialog = new JournalEntryPanal(new javax.swing.JFrame(), true);
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
    private void txtAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmountFocusLost
    }//GEN-LAST:event_txtAmountFocusLost

    private void txtNarrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNarrationActionPerformed
        txtAmount.grabFocus();
    }//GEN-LAST:event_txtNarrationActionPerformed

    private void txtAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountActionPerformed
    }//GEN-LAST:event_txtAmountActionPerformed

    private void cmdAccoumtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAccoumtActionPerformed
        getAccount();
    }//GEN-LAST:event_cmdAccoumtActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        doAdd();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAccoumt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JTextField txtAccountCode;
    private javax.swing.JTextField txtAccountName;
    private javax.swing.JFormattedTextField txtAmount;
    private javax.swing.JTextField txtNarration;
    // End of variables declaration//GEN-END:variables
    public static OBJEntry entry;
}