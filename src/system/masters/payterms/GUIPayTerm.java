/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIPayTerms.java
 *
 * Created on Jul 18, 2013, 2:51:01 PM
 */
package system.masters.payterms;

import javax.swing.JOptionPane;
import mainApp.findwindow.GUIFindWindow;

/**
 *
 * @author dell
 */
public class GUIPayTerm extends javax.swing.JInternalFrame {

    /** Creates new form GUIPayTerms */
    public GUIPayTerm() {
        initComponents();
        initOthers();
    }

    private void doSave() {
        String code = txtCode.getText();
        String name = txtName.getText();

        if (code.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Blank entry not allowed", "Warning !", JOptionPane.OK_OPTION);
        } else {
            String s = "";
            if (Act == 1) {
                s = "Save";
            } else {
                s = "Update";
            }
            int q = JOptionPane.showConfirmDialog(null, "Are you sure want to " + s + " ?", s + " Pay Term", JOptionPane.YES_NO_OPTION);
            if (q == JOptionPane.YES_OPTION) {
                obj = new OBJPayTerm(
                        txtCode.getText(),
                        txtName.getText());

                SERPayTerm.save(obj, Act);
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

    private void doNew() {
        //setID();
        txtCode.setText("");
        txtName.setText("");
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
    }

    private void doEdit() {
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
        txtCode.setEnabled(false);
    }

    private void doDelete() {
        String code = txtCode.getText();
        SERPayTerm.delete(code);
        indexCount = SERPayTerm.getIndex();
        if (Index < indexCount) {
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
        txtCode.setEnabled(state);
        txtName.setEnabled(state);
    }

    private void getNavi() {
        obj = SERPayTerm.getNavi(Index);
        txtCode.setText(obj.getCode());
        txtName.setText(obj.getName());
    }

    private void doFind() {
        new GUIFindWindow(null, true, "PayTerms", "PTCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void serch(String code) {
        obj = SERPayTerm.serch(code);
        txtCode.setText(obj.getCode());
        txtName.setText(obj.getName());
    }

//    private void setID() {
//        txtCode.setText(SERPayTerm.getID());
//    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initOthers() {
        setMode(DEFAULT_STATUS);
        //setID();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Payment Term");

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
        cmdFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFirstActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdFirst);

        cmdPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point_180.png"))); // NOI18N
        cmdPrev.setToolTipText("Previous (Left Key)");
        cmdPrev.setAlignmentX(0.5F);
        cmdPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdPrev.setFocusable(false);
        cmdPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdPrev.setIconTextGap(5);
        cmdPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPrevActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdPrev);

        cmdNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point.png"))); // NOI18N
        cmdNext.setToolTipText("Next (Right Key)");
        cmdNext.setAlignmentX(0.5F);
        cmdNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdNext.setFocusable(false);
        cmdNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdNext.setIconTextGap(5);
        cmdNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNextActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdNext);

        cmdLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/hand_point_270.png"))); // NOI18N
        cmdLast.setToolTipText("Last (Down Key)");
        cmdLast.setAlignmentX(0.5F);
        cmdLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdLast.setFocusable(false);
        cmdLast.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdLast.setIconTextGap(5);
        cmdLast.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLastActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdLast);

        cmdFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/find.png"))); // NOI18N
        cmdFind.setToolTipText("Find (F4)");
        cmdFind.setAlignmentX(0.5F);
        cmdFind.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        cmdEdit.setFocusable(false);
        cmdEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdEdit.setIconTextGap(5);
        cmdEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdEdit);

        cmdDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/delete.png"))); // NOI18N
        cmdDelete.setToolTipText("Delete (F7)");
        cmdDelete.setAlignmentX(0.5F);
        cmdDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        cmdReport.setFocusable(false);
        cmdReport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdReport.setIconTextGap(5);
        cmdReport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReportActionPerformed(evt);
            }
        });
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
        cmdExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExitActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdExit);

        jLabel1.setText("Code");

        jLabel2.setText("Payment Term");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(137, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cmdFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdFirstActionPerformed
    Index = 0;
    getNavi();
}//GEN-LAST:event_cmdFirstActionPerformed

private void cmdPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPrevActionPerformed
    Index--;
    if (Index >= 0) {
        getNavi();
    } else {
        Index = 0;
    }
}//GEN-LAST:event_cmdPrevActionPerformed

private void cmdNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNextActionPerformed
    Index++;
    indexCount = SERPayTerm.getIndex();
    if (Index < indexCount) {
        getNavi();
    } else {
        Index = indexCount;
    }
}//GEN-LAST:event_cmdNextActionPerformed

private void cmdLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLastActionPerformed
    Index = SERPayTerm.getIndex();
    getNavi();
}//GEN-LAST:event_cmdLastActionPerformed

private void cmdFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdFindActionPerformed
    doFind();
}//GEN-LAST:event_cmdFindActionPerformed

private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed
    Act = 1;
    doNew();
}//GEN-LAST:event_cmdNewActionPerformed

private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
    Act = 0;
    doEdit();
}//GEN-LAST:event_cmdEditActionPerformed

private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
    int q = JOptionPane.showConfirmDialog(null, "Are you sure want to delete ?", "delete Area", JOptionPane.YES_NO_OPTION);

    if (q == JOptionPane.YES_OPTION) {
        doDelete();
    }
}//GEN-LAST:event_cmdDeleteActionPerformed

private void cmdViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdViewActionPerformed
    Index = 0;
    doView();
}//GEN-LAST:event_cmdViewActionPerformed

private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
    doSave();
}//GEN-LAST:event_cmdSaveActionPerformed

private void cmdReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdReportActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_cmdReportActionPerformed

private void cmdExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExitActionPerformed
    this.dispose();
}//GEN-LAST:event_cmdExitActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdExit;
    private javax.swing.JButton cmdFind;
    private javax.swing.JButton cmdFirst;
    private javax.swing.JButton cmdLast;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdNext;
    private javax.swing.JButton cmdPrev;
    private javax.swing.JButton cmdReport;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
    private OBJPayTerm obj;
    //status
    private static final int DEFAULT_STATUS = 0;
    private static final int NEW_STATUS = 1;
    private int Act = 1;
    private int Index = 0;
    private int indexCount = 0;
}
