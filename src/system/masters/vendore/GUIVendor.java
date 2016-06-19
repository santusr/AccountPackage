/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUICustomer.java
 *
 * Created on Jul 18, 2013, 7:23:06 PM
 */
package system.masters.vendore;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import mainApp.findwindow.GUIFindWindow;
import system.accounts.master.accountcreation.OBJAccountCreation;

/**
 *
 * @author dell
 */
public class GUIVendor extends javax.swing.JInternalFrame {

    /** Creates new form GUICustomer */
    public GUIVendor() {
        initComponents();
     initOthers();
    }

    private void doSave() {
        String code = txtCode.getText();
        String name = txtPrintName.getText();
        String Srep = "01";
        String FCurrency;
        
        if(txtSrep.getItemCount() > 0){
        Srep = txtSrep.getSelectedItem().toString();
        Srep = Srep.substring(1, 3);
        } 
        
        FCurrency = txtCurrency.getSelectedItem().toString();
        FCurrency = FCurrency.substring(1, 4);
        
        if (code.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Blank entry not allowed", "Warning !", JOptionPane.OK_OPTION);
        } else {
            String s = "";
            if (Act == 1) {
                s = "Save";
            } else {
                s = "Update";
            }
            int q = JOptionPane.showConfirmDialog(null, "Are you sure want to " + s + " ?", s + " Vendore", JOptionPane.YES_NO_OPTION);
            if (q == JOptionPane.YES_OPTION) {
                obj = new OBJVendore(
                        txtCode.getText(),
                        txtCode.getText(),
                        txtPrintName.getText(),
                        txtId.getText(),
                        txtCont1.getText(),
                        txtCont2.getText(),
                        txtmobi1.getText(),
                        txtmobi2.getText(),
                        txtmobi3.getText(),
                        txtPobox.getText(),
                        txtAddress1.getText(),
                        txtAddress2.getText(),
                        txtAddress3.getText(),
                        txtTelOff.getText(),
                        txtTelRes.getText(),
                        txtFax.getText(),
                        txtEmail.getText(),
                        txtWeb.getText(),
                        Srep,
                        txtAreaCode.getText(),
                        txtPayCode.getText(),
                        FCurrency,
                        txtBalance.getText(),
                        txtCreditLimit.getText(),
                        txtCreditdays.getText(),
                        txtRemark.getText(),
                        "D",
                        txtPrintName.getText());

                
                obja = new OBJAccountCreation(
                        txtCode.getText(), 
                        txtName.getText(),
                        "0", 
                        "0",
                        "1", 
                        "0.00",
                        "0.00",
                        "0.00", 
                        "D",
                        "22300");
                SERVendore.save(obj,obja, Act);
                //SERAccountcreation.save(obja, Act);
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
        setID();
        //txtCode.setText("");
        txtPrintName.setText("");
        txtName.setText("");
        txtId.setText("");
        txtCont1.setText("");
        txtCont2.setText("");
        txtmobi1.setText("");
        txtmobi2.setText("");
        txtmobi3.setText("");
        txtPobox.setText("");
        txtAddress1.setText("");
        txtAddress2.setText("");
        txtAddress3.setText("");
        txtTelOff.setText("");
        txtTelRes.setText("");
        txtFax.setText("");
        txtEmail.setText("");
        txtWeb.setText("");
        if (txtSrep.getSelectedIndex() != -1) {
        txtSrep.setSelectedIndex(0);
        }
        txtAreaCode.setText("");
        txtPayCode.setText("");
        if (txtCurrency.getSelectedIndex() != -1) {
        txtCurrency.setSelectedIndex(0);
        }
        txtBalance.setText("");
        txtCreditLimit.setText("");
        txtCreditdays.setText("");
        txtRemark.setText("");
        setEnableAll(true);
        setID();
        setMode(DEFAULT_STATUS);
    }

    private void doEdit() {
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
        txtCode.setEnabled(false);
    }

    private void doDelete() {
        String code = txtCode.getText();
        SERVendore.delete(code);
        indexCount = SERVendore.getIndex();
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
       // txtCode.setEnabled(state);
        txtPrintName.setEnabled(state);
        txtName.setEnabled(state);
        txtId.setEnabled(state);
        txtCont1.setEnabled(state);
        txtCont2.setEnabled(state);
        txtmobi1.setEnabled(state);
        txtmobi2.setEnabled(state);
        txtmobi3.setEnabled(state);
        txtPobox.setEnabled(state);
        txtAddress1.setEnabled(state);
        txtAddress2.setEnabled(state);
        txtAddress3.setEnabled(state);
        txtTelOff.setEnabled(state);
        txtTelRes.setEnabled(state);
        txtFax.setEnabled(state);
        txtEmail.setEnabled(state);
        txtWeb.setEnabled(state);
        txtSrep.setEnabled(state);
        txtAreaCode.setEnabled(state);
        txtPayCode.setEnabled(state);
        txtCurrency.setEnabled(state);
        txtBalance.setEnabled(state);
        txtCreditLimit.setEnabled(state);
        txtCreditdays.setEnabled(state);
        txtRemark.setEnabled(state);
        cmdArea.setEnabled(state);
        cmdPayTerm.setEnabled(state);
    }

    private void getNavi() {
        obj = SERVendore.getNavi(Index);
        setContent(obj);
        /*txtCode.setText(obj.getCode());
        txtPrintName.setText(obj.getName());
        txtName.setText(obj.getPrintName());
        txtId.setText(obj.getId());
        txtCont1.setText(obj.getContact1());
        txtCont2.setText(obj.getContact2());
        txtmobi1.setText(obj.getMobi1());
        txtmobi2.setText(obj.getMobi2());
        txtmobi3.setText(obj.getMobi3());
        txtPobox.setText(obj.getPbox());
        txtAddress1.setText(obj.getAdd1());
        txtAddress2.setText(obj.getAdd2());
        txtAddress3.setText(obj.getAdd3());
        txtTelOff.setText(obj.getTeloff());
        txtTelRes.setText(obj.getTelres());
        txtFax.setText(obj.getFax());
        txtEmail.setText(obj.getEmail());
        txtWeb.setText(obj.getWeb());
        txtSrep.setSelectedItem(loadSrep(obj.getSrep()));
        txtAreaCode.setText(obj.getArea());
        txtArea.setText(SERVendore.setName(obj.getArea(), "Area", "AreaCode"));
        txtPayCode.setText(obj.getPayterm());
        txtPayTerm.setText(SERVendore.setName(obj.getPayterm(), "PayTerms", "PTCode"));
        txtCurrency.setSelectedItem(loadCurrency(obj.getCurrency()));
        txtBalance.setText(obj.getBalance());
        txtCreditLimit.setText(obj.getCreditlimit());
        txtCreditdays.setText(obj.getCreditdays());*/
    }

    private void doFind() {
        new GUIFindWindow(null, true, "Customer", "AccCode", "CustName", "V").setVisible(true);
        if (!accountpackage.AccountPackage.CODE.equals("")) {
        String code = accountpackage.AccountPackage.CODE;        
        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
        }
    }

    private void serch(String code) {
        obj = SERVendore.serch(code);
        setContent(obj);
    }
    
    private void setContent(OBJVendore obj){
        txtCode.setText(obj.getCode());
        txtName.setText(obj.getName());
        txtPrintName.setText(obj.getPrintName());
        txtId.setText(obj.getId());
        txtCont1.setText(obj.getContact1());
        txtCont2.setText(obj.getContact2());
        txtmobi1.setText(obj.getMobi1());
        txtmobi2.setText(obj.getMobi2());
        txtmobi3.setText(obj.getMobi3());
        txtPobox.setText(obj.getPbox());
        txtAddress1.setText(obj.getAdd1());
        txtAddress2.setText(obj.getAdd2());
        txtAddress3.setText(obj.getAdd3());
        txtTelOff.setText(obj.getTeloff());
        txtTelRes.setText(obj.getTelres());
        txtFax.setText(obj.getFax());
        txtEmail.setText(obj.getEmail());
        txtWeb.setText(obj.getWeb());
        txtSrep.setSelectedItem(loadSrep(obj.getSrep()));
        txtAreaCode.setText(obj.getArea());
        txtArea.setText(SERVendore.setName(obj.getArea(), "Area", "AreaCode"));
        txtPayCode.setText(obj.getPayterm());
        txtPayTerm.setText(SERVendore.setName(obj.getPayterm(), "PayTerms", "PTCode"));
        txtCurrency.setSelectedItem(loadCurrency(obj.getCurrency()));
        txtBalance.setText(obj.getBalance());
        txtCreditLimit.setText(obj.getCreditlimit());
        txtCreditdays.setText(obj.getCreditdays());
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
    
    private void getPayTerm() {
        new GUIFindWindow(null, true, "PayTerms", "PTCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtPayCode.setText(code);
        txtPayTerm.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }     
    
    private void loadSrep() {
        Vector srep = SERVendore.getSRep();        
        txtSrep.removeAllItems();
        txtSrep.setModel(new DefaultComboBoxModel(srep));
    }
    
    private String loadSrep(String code) {
        String srep = SERVendore.getSRep(code); 
        return srep;
    }
    
    private void loadCurrency() {
        Vector currency = SERVendore.getCurrency();        
        txtCurrency.removeAllItems();
        txtCurrency.setModel(new DefaultComboBoxModel(currency));
    }
    
    private String loadCurrency(String code) {
        String currency = SERVendore.getCurrency(code); 
        return currency;
    }
    
    
    private void setID() {
        txtCode.setText(SERVendore.getID());
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initOthers() {
        setMode(DEFAULT_STATUS);
        setID();
        loadSrep();
        loadCurrency();
        cmdFirst.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFirstActionPerformed(evt);
            }
        });
        cmdLast.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLastActionPerformed(evt);
            }
        });
        cmdPrev.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPrevActionPerformed(evt);
            }
        });
        cmdNext.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNextActionPerformed(evt);
            }
        });
        cmdFind.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFindActionPerformed(evt);
            }
        });
        cmdNew.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        cmdView.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdViewActionPerformed(evt);
            }
        });
        cmdSave.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });
        cmdReport.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReportActionPerformed(evt);
            }
        });
        cmdExit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExitActionPerformed(evt);
            }
        });
        cmdArea.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAreaActionPerformed(evt);
            }
        });
        cmdPayTerm.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPayTermActionPerformed(evt);
            }
        });
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTelRes = new javax.swing.JTextField();
        txtmobi1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
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
        jLabel2 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTelOff = new javax.swing.JTextField();
        txtPrintName = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtAddress1 = new javax.swing.JTextField();
        txtAddress2 = new javax.swing.JTextField();
        txtAddress3 = new javax.swing.JTextField();
        txtPobox = new javax.swing.JTextField();
        txtSrep = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        txtPayCode = new javax.swing.JTextField();
        cmdPayTerm = new javax.swing.JButton();
        txtPayTerm = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCreditdays = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCreditLimit = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        txtRemark = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtmobi2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtmobi3 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtCont1 = new javax.swing.JTextField();
        txtCont2 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        cmdArea = new javax.swing.JButton();
        txtArea = new javax.swing.JTextField();
        txtAreaCode = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtLSDate = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtLRDate = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtLRamount = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtLSAmount = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtWeb = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtCurrency = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        txtFax = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setClosable(true);
        setTitle("Vendors");
        setMaximumSize(new java.awt.Dimension(694, 574));
        setMinimumSize(new java.awt.Dimension(694, 574));
        setPreferredSize(new java.awt.Dimension(694, 574));

        jLabel7.setText("Tel Off.");

        txtTelRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelResActionPerformed(evt);
            }
        });

        txtmobi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmobi1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Vendors Code");

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

        jLabel2.setText("Print Name");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel3.setText("Mobile");

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        jLabel4.setText("ID No.");

        txtCode.setBackground(new java.awt.Color(255, 255, 204));
        txtCode.setEditable(false);
        txtCode.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCode.setForeground(new java.awt.Color(195, 0, 0));

        jLabel8.setText("Tel Res.");

        jLabel9.setText("E-mail");

        txtTelOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelOffActionPerformed(evt);
            }
        });

        txtPrintName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrintNameActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Present Address (Mailing address)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel6.setText("PoBox");

        txtAddress1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddress1ActionPerformed(evt);
            }
        });

        txtAddress2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddress2ActionPerformed(evt);
            }
        });

        txtAddress3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddress3ActionPerformed(evt);
            }
        });

        txtPobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPoboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtAddress3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAddress2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAddress1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtPobox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAddress3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtSrep.setEditable(true);
        txtSrep.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("S. Rep");

        jLabel10.setText("Area");

        txtPayCode.setBounds(0, 0, 34, 20);
        jLayeredPane2.add(txtPayCode, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cmdPayTerm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdPayTerm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdPayTerm.setContentAreaFilled(false);
        cmdPayTerm.setBounds(33, 0, 21, 20);
        jLayeredPane2.add(cmdPayTerm, javax.swing.JLayeredPane.DEFAULT_LAYER);

        txtPayTerm.setBackground(new java.awt.Color(255, 255, 241));
        txtPayTerm.setEditable(false);
        txtPayTerm.setBounds(55, 0, 170, 20);
        jLayeredPane2.add(txtPayTerm, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel11.setText("Pay. Terms");

        jLabel12.setText("Creadit Days");

        txtCreditdays.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtCreditdays.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCreditdays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreditdaysActionPerformed(evt);
            }
        });

        jLabel13.setText("Creadit Limit");

        txtCreditLimit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtCreditLimit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCreditLimit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreditLimitActionPerformed(evt);
            }
        });

        jLabel15.setText("Remarks");

        jLabel16.setText("Mobile");

        txtmobi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmobi2ActionPerformed(evt);
            }
        });

        jLabel17.setText("Mobile");

        txtmobi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmobi3ActionPerformed(evt);
            }
        });

        jLabel18.setText("Contact 1");

        txtCont1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCont1ActionPerformed(evt);
            }
        });

        txtCont2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCont2ActionPerformed(evt);
            }
        });

        jLabel19.setText("Contact 2");

        cmdArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdArea.setContentAreaFilled(false);
        cmdArea.setBounds(33, 0, 21, 20);
        jLayeredPane3.add(cmdArea, javax.swing.JLayeredPane.DEFAULT_LAYER);

        txtArea.setBackground(new java.awt.Color(255, 255, 241));
        txtArea.setEditable(false);
        txtArea.setBounds(55, 0, 170, 20);
        jLayeredPane3.add(txtArea, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtAreaCode.setBounds(0, 0, 34, 20);
        jLayeredPane3.add(txtAreaCode, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("Last Purchase Date");

        txtLSDate.setBackground(new java.awt.Color(255, 255, 241));
        txtLSDate.setEditable(false);

        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("Last Payment Date");

        txtLRDate.setBackground(new java.awt.Color(255, 255, 241));
        txtLRDate.setEditable(false);

        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("Last Purchase Amount");

        txtLRamount.setBackground(new java.awt.Color(255, 255, 241));
        txtLRamount.setEditable(false);

        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("Last Payment Amount");

        txtLSAmount.setBackground(new java.awt.Color(255, 255, 241));
        txtLSAmount.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtLRamount, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(txtLSDate)
                    .addComponent(txtLRDate)
                    .addComponent(txtLSAmount))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLSDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLRDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLSAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLRamount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel25.setText("Web Site");

        txtWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWebActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCurrency.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel24.setText("Currency Code");

        jLabel26.setForeground(new java.awt.Color(0, 0, 102));
        jLabel26.setText("Balance");

        txtBalance.setBackground(new java.awt.Color(255, 255, 241));
        txtBalance.setEditable(false);
        txtBalance.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCurrency, 0, 147, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBalance, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel27.setText("Fax No");

        txtFax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFaxActionPerformed(evt);
            }
        });

        jLabel14.setText("Name");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRemark)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelRes, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelOff, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFax, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtWeb)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrintName, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtCont2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtId, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCont1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                                    .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtmobi2)
                                            .addComponent(txtmobi3)
                                            .addComponent(txtmobi1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtSrep, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtCreditdays, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtCreditLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 23, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrintName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSrep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCreditdays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCreditLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtId)
                        .addComponent(txtmobi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtmobi2)
                        .addComponent(txtCont1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtmobi3)
                        .addComponent(txtCont2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtTelOff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtTelRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE))
                            .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, 0, 73, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEmail)
                        .addComponent(txtWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRemark, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        txtPrintName.grabFocus();
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtPrintNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrintNameActionPerformed
        txtCreditdays.grabFocus();
    }//GEN-LAST:event_txtPrintNameActionPerformed

    private void txtCreditdaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreditdaysActionPerformed
       txtCreditLimit.grabFocus();
    }//GEN-LAST:event_txtCreditdaysActionPerformed

    private void txtCreditLimitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreditLimitActionPerformed
        txtId.grabFocus();
    }//GEN-LAST:event_txtCreditLimitActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        txtCont1.grabFocus();
    }//GEN-LAST:event_txtIdActionPerformed

    private void txtCont1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCont1ActionPerformed
        txtCont2.grabFocus();
    }//GEN-LAST:event_txtCont1ActionPerformed

    private void txtCont2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCont2ActionPerformed
        txtmobi1.grabFocus();
    }//GEN-LAST:event_txtCont2ActionPerformed

    private void txtmobi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmobi2ActionPerformed
        txtmobi3.grabFocus();
    }//GEN-LAST:event_txtmobi2ActionPerformed

    private void txtmobi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmobi3ActionPerformed
        txtPobox.grabFocus();
    }//GEN-LAST:event_txtmobi3ActionPerformed

    private void txtPoboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPoboxActionPerformed
        txtAddress1.grabFocus();
    }//GEN-LAST:event_txtPoboxActionPerformed

    private void txtmobi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmobi1ActionPerformed
       txtmobi2.grabFocus();
    }//GEN-LAST:event_txtmobi1ActionPerformed

    private void txtAddress1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddress1ActionPerformed
        txtAddress2.grabFocus();
    }//GEN-LAST:event_txtAddress1ActionPerformed

    private void txtAddress2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddress2ActionPerformed
        txtAddress3.grabFocus();
    }//GEN-LAST:event_txtAddress2ActionPerformed

    private void txtAddress3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddress3ActionPerformed
        txtTelOff.grabFocus();
    }//GEN-LAST:event_txtAddress3ActionPerformed

    private void txtTelOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelOffActionPerformed
       txtTelRes.grabFocus();
    }//GEN-LAST:event_txtTelOffActionPerformed

    private void txtTelResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelResActionPerformed
        txtFax.grabFocus();
    }//GEN-LAST:event_txtTelResActionPerformed

    private void txtFaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFaxActionPerformed
        txtEmail.grabFocus();
    }//GEN-LAST:event_txtFaxActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        txtWeb.grabFocus();
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWebActionPerformed
        txtRemark.grabFocus();
    }//GEN-LAST:event_txtWebActionPerformed
private void cmdAreaActionPerformed(java.awt.event.ActionEvent evt) {                                        
    getArea();    
}                                       

private void cmdPayTermActionPerformed(java.awt.event.ActionEvent evt) {                                           
    getPayTerm();
}                                          

private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {                                       
     Act = 1;
        doNew();
}                                      

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
        indexCount = SERVendore.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index = indexCount;
        }
    }

    private void cmdLastActionPerformed(java.awt.event.ActionEvent evt) {
        Index = SERVendore.getIndex() - 1;
        getNavi();
    }

    private void cmdFindActionPerformed(java.awt.event.ActionEvent evt) {
        doFind();
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
    private javax.swing.JButton cmdArea;
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
    private javax.swing.JButton cmdReport;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdView;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtAddress1;
    private javax.swing.JTextField txtAddress2;
    private javax.swing.JTextField txtAddress3;
    private javax.swing.JTextField txtArea;
    private javax.swing.JTextField txtAreaCode;
    private javax.swing.JFormattedTextField txtBalance;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtCont1;
    private javax.swing.JTextField txtCont2;
    private javax.swing.JFormattedTextField txtCreditLimit;
    private javax.swing.JFormattedTextField txtCreditdays;
    private javax.swing.JComboBox txtCurrency;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLRDate;
    private javax.swing.JTextField txtLRamount;
    private javax.swing.JTextField txtLSAmount;
    private javax.swing.JTextField txtLSDate;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPayCode;
    private javax.swing.JTextField txtPayTerm;
    private javax.swing.JTextField txtPobox;
    private javax.swing.JTextField txtPrintName;
    private javax.swing.JTextField txtRemark;
    private javax.swing.JComboBox txtSrep;
    private javax.swing.JTextField txtTelOff;
    private javax.swing.JTextField txtTelRes;
    private javax.swing.JTextField txtWeb;
    private javax.swing.JTextField txtmobi1;
    private javax.swing.JTextField txtmobi2;
    private javax.swing.JTextField txtmobi3;
    // End of variables declaration//GEN-END:variables
    private OBJVendore obj;
    private OBJAccountCreation obja;
    //status
    private static final int DEFAULT_STATUS = 0;
    private static final int NEW_STATUS = 1;
    private int Act = 1;
    private int Index = 0;
    private int indexCount = 0;
}
