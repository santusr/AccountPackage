/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIPayRealization.java
 *
 * Created on Jul 20, 2013, 10:18:31 PM
 */
package system.masters.vendore.search;

import core.Exp;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.MainFrame;
import system.accounts.transaction.cheque.GUICheque;
import system.accounts.transaction.cheque.SERCheque;
import system.masters.customer.CustomerStatus;
import system.masters.vendore.OBJVendore;
import system.masters.vendore.VendoreCellRender;

/**
 *
 * @author dell
 */
public class GUISearchVendor extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUIPayRealization
     */
    public GUISearchVendor() {
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
                /*cmdFirst.setEnabled(!true);
                 cmdLast.setEnabled(!true);
                 cmdNext.setEnabled(!true);
                 cmdPrev.setEnabled(!true);
                 cmdFind.setEnabled(!true);
                 cmdEdit.setEnabled(!true);
                 cmdView.setEnabled(true);
                 cmdSave.setEnabled(true);*/
                cmdNew.setEnabled(!true);
                cmdDelete.setEnabled(!true);
                cmdReport.setEnabled(!true);

                break;
            case NEW_STATUS:
                setEnableAll(!true);
                /* cmdFirst.setEnabled(true);
                 cmdLast.setEnabled(true);
                 cmdNext.setEnabled(true);
                 cmdPrev.setEnabled(true);
                 cmdFind.setEnabled(true);
                 cmdEdit.setEnabled(true);
                 cmdView.setEnabled(!true);
                 cmdSave.setEnabled(!true);*/
                cmdNew.setEnabled(true);
                cmdDelete.setEnabled(true);
                cmdReport.setEnabled(true);

                break;
        }
    }

    private void setEnableAll(boolean state) {

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

        obja = SERSearchVendore.getContent("V");
        setContent();
        //  jTable1.setEnabled(!true);
    }

    private void doLSearch(String s) {
        obja = SERSearchVendore.getContent("V", s);
        setContent();
        jTable1.setDefaultRenderer(Object.class, new VendoreCellRender());
        //  jTable1.setEnabled(!true);
    }

    private void setContent() {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        clrTable();
        int i = 1;
        boolean b = rbtBlack.isSelected();
        boolean w = rbtWC.isSelected();
        boolean av = rbtAvarage.isSelected();
        boolean a = rbtAll.isSelected();
        if (!obja.isEmpty()) {
            for (OBJVendore objq : obja) {
                if (a || (b && objq.getStatus().equals(CustomerStatus.BlACKLIST)) || (w && objq.getStatus().equals(CustomerStatus.ACTIVE)) || (av && objq.getStatus().equals(CustomerStatus.AVARAGE))) {
                    dt.addRow(new Object[]{i, objq.getCode(), objq.getName(), objq.getPayterm(), objq.getMobi1(), objq.getTelres(), objq.getArea(), objq.getStatus()});
                    
                    i++;
                }
            }
        }
    }

    private void doBlackList(String action) {
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            String customer = jTable1.getValueAt(row, 1).toString();
            String status = jTable1.getValueAt(row, 7).toString();
            String statust = status;
            switch (action) {
                case CustomerStatus.BlACKLIST:
                    status = "4";
                    statust = CustomerStatus.BlACKLIST;
                    break;
                case CustomerStatus.ACTIVE:
                    status = "0";
                    statust = CustomerStatus.ACTIVE;
                    break;
                case CustomerStatus.AVARAGE:
                    status = "2";
                    statust = CustomerStatus.AVARAGE;
                    break;
            }
            boolean b = SERSearchVendore.setBlackList(customer, status);
            if (b) {
                JOptionPane.showMessageDialog(null, "Vendore " + statust + " Succssful...");
                setContentHistory();
            }
        }

    }

    private void clrTable() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int rc = jTable1.getRowCount();
        for (int i = 0; i < rc; i++) {
            df.removeRow(0);
        }
    }

    private void doNew() {
        new GUICheque(null, true).setVisible(true);
        setButtonMode();
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
        jTable1.setDefaultRenderer(Object.class, new VendoreCellRender());
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
        rbtAll = new javax.swing.JRadioButton();
        rbtBlack = new javax.swing.JRadioButton();
        rbtWC = new javax.swing.JRadioButton();
        rbtAvarage = new javax.swing.JRadioButton();
        cmdCustomerDetails = new javax.swing.JButton();
        cmdCustomerHistory = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        cmdCreatInvoice = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        cmdCreatePayment = new javax.swing.JButton();
        cmdBlackList = new javax.swing.JButton();
        cmdCustomerInvoice = new javax.swing.JButton();
        cmdAvarageList = new javax.swing.JButton();
        cmdWhiteList = new javax.swing.JButton();

        setClosable(true);
        setTitle("Vendore Search");

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        buttonGroup1.add(rbtAll);
        rbtAll.setSelected(true);
        rbtAll.setText("All Vendores");
        rbtAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtAllActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtBlack);
        rbtBlack.setText("Black List");
        rbtBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtBlackActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtWC);
        rbtWC.setText("White Vendores");
        rbtWC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtWCActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtAvarage);
        rbtAvarage.setText("Avarage");
        rbtAvarage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtAvarageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtAll)
                    .addComponent(rbtBlack)
                    .addComponent(rbtWC)
                    .addComponent(rbtAvarage))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtBlack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtAvarage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtWC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmdCustomerDetails.setText("Show Customer Details");
        cmdCustomerDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCustomerDetailsActionPerformed(evt);
            }
        });

        cmdCustomerHistory.setText("Show Customer History");
        cmdCustomerHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCustomerHistoryActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Enter Serch Keyword", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 102)));

        jLabel1.setText("Serch Key");

        jTextField1.setBackground(new java.awt.Color(233, 253, 253));
        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        cmdCreatInvoice.setText("Creat Invoice");
        cmdCreatInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCreatInvoiceActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Cust Code", "Name", "ID", "mobi", "Tel Res", "Area", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(3);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(300);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(45);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(45);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(35);
        }

        cmdCreatePayment.setText("Creat Payment");
        cmdCreatePayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCreatePaymentActionPerformed(evt);
            }
        });

        cmdBlackList.setText("Black List");
        cmdBlackList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBlackListActionPerformed(evt);
            }
        });

        cmdCustomerInvoice.setText("Show Customer Invoices");
        cmdCustomerInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCustomerInvoiceActionPerformed(evt);
            }
        });

        cmdAvarageList.setText("Avarage List");
        cmdAvarageList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAvarageListActionPerformed(evt);
            }
        });

        cmdWhiteList.setText("White List");
        cmdWhiteList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdWhiteListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdCustomerInvoice, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmdCustomerDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmdCustomerHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmdCreatInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(cmdWhiteList, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdAvarageList, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdBlackList, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdCreatePayment, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmdCustomerHistory)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdCustomerDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmdCreatInvoice)
                                    .addComponent(cmdCustomerInvoice)))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cmdBlackList)
                            .addComponent(cmdCreatePayment)
                            .addComponent(cmdAvarageList)
                            .addComponent(cmdWhiteList))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed
    Act = 1;
    doNew();
}//GEN-LAST:event_cmdNewActionPerformed

    private void cmdCustomerDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCustomerDetailsActionPerformed
        int i = jTable1.getSelectedRow();
        String cc = jTable1.getValueAt(i, 1).toString();
        if (i >= 0) {
            MainFrame.val = cc;
            MainFrame.f = 1;
        }
    }//GEN-LAST:event_cmdCustomerDetailsActionPerformed

    private void cmdCustomerHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCustomerHistoryActionPerformed
        int i = jTable1.getSelectedRow();
        String cc = jTable1.getValueAt(i, 1).toString();
        if (i >= 0) {
            try {
                new PrintVendorHistory(cc).setVisible(true);
            } catch (Exception ex) {
                Exp.Handle(ex);
            }
        }
    }//GEN-LAST:event_cmdCustomerHistoryActionPerformed

    private void rbtWCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtWCActionPerformed
        setContent();
    }//GEN-LAST:event_rbtWCActionPerformed

    private void cmdExit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExit1ActionPerformed
        setContentHistory();
    }//GEN-LAST:event_cmdExit1ActionPerformed

    private void cmdCreatInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCreatInvoiceActionPerformed
        int i = jTable1.getSelectedRow();
        String cc = jTable1.getValueAt(i, 1).toString();
        if (i >= 0) {
            MainFrame.val = cc;
            MainFrame.f = 2;
        }
    }//GEN-LAST:event_cmdCreatInvoiceActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        doLSearch(jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyReleased

    private void cmdCreatePaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCreatePaymentActionPerformed
        int i = jTable1.getSelectedRow();
        String cc = jTable1.getValueAt(i, 1).toString();
        if (i >= 0) {
            MainFrame.val = cc;
            MainFrame.f = 5;
        }
    }//GEN-LAST:event_cmdCreatePaymentActionPerformed

    private void cmdBlackListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBlackListActionPerformed
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            doBlackList(CustomerStatus.BlACKLIST);
        }
        setButtonMode();
    }//GEN-LAST:event_cmdBlackListActionPerformed

    private void cmdCustomerInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCustomerInvoiceActionPerformed
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
//            new GUIShowInvoices(null, true, jTable1.getValueAt(row, 1)).setVisible(true);
        }
    }//GEN-LAST:event_cmdCustomerInvoiceActionPerformed

    private void rbtAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtAllActionPerformed
        setContent();
        setButtonMode();
    }//GEN-LAST:event_rbtAllActionPerformed

    private void rbtBlackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtBlackActionPerformed
        setContent();
        setButtonMode();
    }//GEN-LAST:event_rbtBlackActionPerformed

    private void rbtAvarageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtAvarageActionPerformed
        setContent();
        setButtonMode();
    }//GEN-LAST:event_rbtAvarageActionPerformed

    private void cmdAvarageListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAvarageListActionPerformed
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            doBlackList(CustomerStatus.AVARAGE);
        }
        setButtonMode();
    }//GEN-LAST:event_cmdAvarageListActionPerformed

    private void cmdWhiteListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdWhiteListActionPerformed
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            doBlackList(CustomerStatus.ACTIVE);
        }
        setButtonMode();
    }//GEN-LAST:event_cmdWhiteListActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        setButtonMode();
    }//GEN-LAST:event_jTable1MouseClicked

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int q = JOptionPane.showConfirmDialog(null, "Are you sure want to delete ?", "delete Area", JOptionPane.YES_NO_OPTION);

        if (q == JOptionPane.YES_OPTION) {
            doDelete();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cmdAvarageList;
    private javax.swing.JButton cmdBlackList;
    private javax.swing.JButton cmdCreatInvoice;
    private javax.swing.JButton cmdCreatePayment;
    private javax.swing.JButton cmdCustomerDetails;
    private javax.swing.JButton cmdCustomerHistory;
    private javax.swing.JButton cmdCustomerInvoice;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdExit;
    private javax.swing.JButton cmdExit1;
    private javax.swing.JButton cmdFind;
    private javax.swing.JButton cmdFirst;
    private javax.swing.JButton cmdLast;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdNext;
    private javax.swing.JButton cmdPrev;
    private javax.swing.JButton cmdReport;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdView;
    private javax.swing.JButton cmdWhiteList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JRadioButton rbtAll;
    private javax.swing.JRadioButton rbtAvarage;
    private javax.swing.JRadioButton rbtBlack;
    private javax.swing.JRadioButton rbtWC;
    // End of variables declaration//GEN-END:variables
    private OBJVendore obj;
    private ArrayList<OBJVendore> obja;
    //status
    private static final int DEFAULT_STATUS = 0;
    private static final int NEW_STATUS = 1;
    private int Act = 1;

    private void setButtonMode() {
        int row = jTable1.getSelectedRow();

        String status = "";
        if (row >= 0) {
            status = jTable1.getValueAt(row, 7).toString();
        }
        switch (status) {
            case CustomerStatus.ACTIVE:
                cmdCustomerHistory.setEnabled(true);
                cmdCustomerDetails.setEnabled(true);
                cmdCreatInvoice.setEnabled(true);
                cmdCreatePayment.setEnabled(true);
                cmdCustomerInvoice.setEnabled(true);
                cmdBlackList.setEnabled(true);
                cmdAvarageList.setEnabled(true);
                cmdWhiteList.setEnabled(!true);
                break;
            case CustomerStatus.AVARAGE:
                cmdCustomerHistory.setEnabled(true);
                cmdCustomerDetails.setEnabled(true);
                cmdCreatInvoice.setEnabled(true);
                cmdCreatePayment.setEnabled(true);
                cmdCustomerInvoice.setEnabled(true);
                cmdBlackList.setEnabled(true);
                cmdAvarageList.setEnabled(!true);
                cmdWhiteList.setEnabled(true);
                break;
            case CustomerStatus.BlACKLIST:
                cmdCustomerHistory.setEnabled(true);
                cmdCustomerDetails.setEnabled(true);
                cmdCreatInvoice.setEnabled(!true);
                cmdCreatePayment.setEnabled(true);
                cmdCustomerInvoice.setEnabled(true);
                cmdBlackList.setEnabled(!true);
                cmdAvarageList.setEnabled(true);
                cmdWhiteList.setEnabled(true);
                break;
            default:
                cmdCustomerHistory.setEnabled(!true);
                cmdCustomerDetails.setEnabled(!true);
                cmdCreatInvoice.setEnabled(!true);
                cmdCreatePayment.setEnabled(!true);
                cmdCustomerInvoice.setEnabled(!true);
                cmdBlackList.setEnabled(!true);
                cmdAvarageList.setEnabled(!true);
                cmdWhiteList.setEnabled(!true);
                break;
        }
    }
}
