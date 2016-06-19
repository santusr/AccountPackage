/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.stock.stocktransfer;

import core.Exp;
import core.Locals;
import core.system_transaction.TransactionType;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.ItemTransactionStatus;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.transaction.OBJTransactionHistory;
import core.system_transaction.transaction.TransactionStatus;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.SERCommen;
import mainApp.findwindow.GUIFindWindow;
import system.inventory.master.salesrep.SERSalesRep;
import system.inventory.transaction.item_list.ItemList;

/**
 *
 * @author Rabid
 */
public class GUIStockTransfer extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUIStockTransfer
     */
    public GUIStockTransfer() {
        initComponents();
        initOthers();
    }

    private void doSave() {
        String code = txtTransNo.getText();
        String StF = txtStoreCodeF.getText();
        String StT = txtStoreCodeT.getText();

        if (code.isEmpty() || StF.isEmpty() || StT.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Blank entry not allowed", "Warning !", JOptionPane.OK_OPTION);
        } else {
            String s = "";
            if (Act == 1) {
                s = "Save";
            } else {
                s = "Update";
            }
            int q = JOptionPane.showConfirmDialog(null, "Are you want to " + s + " ?", s, JOptionPane.YES_NO_OPTION);
            if (q == JOptionPane.YES_OPTION) {
                obj = new OBJStockTransfer(
                        txtTransNo.getText(),
                        core.Locals.setDateFormat(txtTransDate.getDate()),
                        txtStoreCodeF.getText(),
                        txtStoreCodeT.getText(),
                        txtPrepBy.getText(),
                        txtApproBy.getText(),
                        txtRemark.getText(),
                        txtNetAmount.getText(),
                        accountpackage.AccountPackage.user);

                try {
                    obja = new ArrayList<>();
                    int row = jTable1.getRowCount();
                    for (int i = 0; i < row; i++) {

                        obj_1 = new OBJStockTransfer(
                                txtTransNo.getText(),
                                jTable1.getValueAt(0, 1).toString(),
                                jTable1.getValueAt(0, 2).toString(),
                                jTable1.getValueAt(0, 4).toString(),
                                jTable1.getValueAt(0, 6).toString(),
                                jTable1.getValueAt(0, 3).toString(),
                                jTable1.getValueAt(0, 8).toString(),
                                jTable1.getValueAt(0, 5).toString(),
                                jTable1.getValueAt(0, 9).toString(),
                                jTable1.getValueAt(0, 6).toString(),
                                txtStoreCodeF.getText(),
                                txtStoreCodeT.getText(),
                                jTable1.getValueAt(0, 7).toString());

                        obja.add(obj_1);
                    }
                } catch (Exception e) {
                    core.Exp.Handle(e);
                }
                boolean b;
                itemTransaction();
                transaction();
                payment = null;
                paymentsInfo = null;
                accountTranses = null;
                try {
                    b = SERStockTransfer.save(obj, obja, transaction, payment, paymentsInfo, accountTranses, itemTransactions, Act);
                if (jCheckBox2.isSelected() && b) {
                    printTrans();
                }

                doNew();
                if (Act == 1) {
                    setMode(DEFAULT_STATUS);
                } else {
                    setMode(NEW_STATUS);
                    Act = 1;
                }
                } catch (SQLException ex) {
                    Logger.getLogger(GUIStockTransfer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void transaction() {
        // Transaction Object
        transaction = new OBJTransaction();
        transaction.setTransactionDate(Locals.setDateFormat(txtTransDate.getDate()));
        transaction.setTransactionType(TransactionType.ITEM_TRANSFER);
        transaction.setReferanceNo(txtTransNo.getText());
        transaction.setDocumentNo(null);
        transaction.setLoan(null);
        transaction.setCostCode(accountpackage.AccountPackage.costCode);
        transaction.setCustomerCode(null);
        transaction.setNote(txtRemark.getText());
        transaction.setStatus(TransactionStatus.ACTIVE);

    }

    private void itemTransaction() {
        itemTransactions = new ArrayList<>();

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            double cost = Double.parseDouble(costs.get(i).toString()) * Double.parseDouble(jTable1.getValueAt(i, 8).toString());
            // Debit Transfer
            itemTransaction = new OBJItemTransaction();
            itemTransaction.setTransactionType(TransactionType.ITEM_TRANSFER);
            itemTransaction.setTransactionDate(Locals.setDateFormat(txtTransDate.getDate()));
            itemTransaction.setItem(jTable1.getValueAt(i, 1).toString());
            itemTransaction.setStore(txtStoreCodeT.getText());
            itemTransaction.setDebitQty(jTable1.getValueAt(i, 4).toString());
            itemTransaction.setCreditQty("0.00");
            itemTransaction.setDebitPrice(cost + "");
            itemTransaction.setCreditPrice("0.00");
            itemTransaction.setStatus(ItemTransactionStatus.ACTIVE);

            itemTransactions.add(itemTransaction);

            // Credit Transfer
            itemTransaction = new OBJItemTransaction();
            itemTransaction.setTransactionType(TransactionType.ITEM_TRANSFER);
            itemTransaction.setTransactionDate(Locals.setDateFormat(txtTransDate.getDate()));
            itemTransaction.setItem(jTable1.getValueAt(i, 1).toString());
            itemTransaction.setStore(txtStoreCodeF.getText());
            itemTransaction.setDebitQty("0.00");
            itemTransaction.setCreditQty(jTable1.getValueAt(i, 4).toString());
            itemTransaction.setDebitPrice("0.00");
            itemTransaction.setCreditPrice(cost + "");
            itemTransaction.setStatus(ItemTransactionStatus.ACTIVE);

            itemTransactions.add(itemTransaction);
        }
    }

    private void printTrans() {
        if (!txtTransNo.getText().isEmpty()) {
            try {
                new PrintTransaction(txtTransNo.getText()).setVisible(true);
            } catch (Exception ex) {
                Exp.Handle(ex);
            }
        }
    }

    private void doNew() {
        setID();
        //txtCode.setText("");
        txtTransDate.setDate(Calendar.getInstance().getTime());

        //  txtFCRate.setText("");
        txtStoreCodeF.setText("");
        txtStoreF.setText("");
        txtStoreCodeT.setText("");
        txtStoreT.setText("");

        txtNetAmount.setText("0.00");

        txtApproBy.setText("");
        txtPrepBy.setText("");
        txtRemark.setText("");

        clrTable();
        addNewRow();
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
    }

    private void addNewRow() {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getRowCount();
        dt.addRow(new Object[]{row + 1, "", "", "", "", "", "", "", "", ""});
    }

    private void clrTable() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int rc = jTable1.getRowCount();
        for (int i = 0; i < rc; i++) {
            df.removeRow(0);
        }
    }

    private void doEdit() {
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
        txtTransNo.setEnabled(false);
    }

    private void doDelete() {
        String code = txtTransNo.getText();
        SERSalesRep.delete(code);
        indexCount = SERSalesRep.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index--;
            getNavi();
        }
    }

    private void doView() {
        doNew();
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
        //txtCode.setEnabled(state);
        txtTransDate.setEnabled(state);
        txtStoreCodeF.setEnabled(state);
        txtStoreCodeT.setEnabled(state);

        txtTransDate.setEnabled(state);
        txtPrepBy.setEnabled(state);
        txtApproBy.setEnabled(state);
        txtRemark.setEnabled(state);

        txtNetAmount.setEnabled(state);

        jTable1.setEnabled(state);

        if (state) {
            jTable1.setBackground(new java.awt.Color(255, 255, 255));
        } else {
            jTable1.setBackground(new java.awt.Color(249, 249, 229));
        }
        /*
         * -----******( )*****-----
         */

        cmdStoreT.setEnabled(state);
        cmdStoreF.setEnabled(state);
    }

    private void getNavi() {
        obj = SERStockTransfer.getNavi(Index);
        obja = SERStockTransfer.InvoHistory(obj.getTransNo());
        setContent(obj);
        setContentHistory(obja);
    }

    private void setContent(OBJStockTransfer obj) {
        txtTransNo.setText(obj.getTransNo());
        txtTransDate.setDate(Locals.toDate(obj.getTransDate()));

        txtStoreCodeF.setText(obj.getStoreCodeF());
        txtStoreCodeT.setText(obj.getStoreCodeT());
        txtStoreF.setText(SERCommen.getDescription("store", txtStoreCodeF.getText(), "StoreCode", "Description"));
        txtStoreT.setText(SERCommen.getDescription("store", txtStoreCodeT.getText(), "StoreCode", "Description"));

        txtPrepBy.setText(obj.getPrepBy());
        txtApproBy.setText(obj.getApproBy());
        txtRemark.setText(obj.getRemarks());

        txtNetAmount.setText(obj.getNetAmount());
    }

    private void setContentHistory(ArrayList<OBJStockTransfer> obja) {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        clrTable();
        int i = 1;
        if (!obja.isEmpty()) {
            for (OBJStockTransfer objq : obja) {
                dt.addRow(new Object[]{i, objq.getItemCode(), objq.getItemDescription(), objq.getUnitCode(), objq.getQuantity(), objq.getWarranty(), objq.getRate(), objq.getDiscount(), objq.getNet(), objq.getSn()});
                i++;
            }
        }
        jTable1.setEnabled(!true);
    }

    private void doFind() {
        new GUIFindWindow(null, true, "TransferHeader", "TransNo", "StoreCodeF").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void serch(String code) {
        obj = SERStockTransfer.serch(code);
        obja = SERStockTransfer.InvoHistory(obj.getTransNo());
        setContent(obj);
        setContentHistory(obja);
    }

    private void setID() {
        txtTransNo.setText(SERStockTransfer.getID());
    }

    private void doDeleteRow() {
        int selRow = jTable1.getSelectedRow();
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        dt.removeRow(selRow);
        costs.remove(selRow);
        int i = jTable1.getRowCount();
        if (i == 0) {
            addNewRow();
        }
    }

    private void getStoreF() {
        new GUIFindWindow(null, true, "Store", "StoreCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtStoreCodeF.setText(code);
        txtStoreF.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void getStoreT() {
        new GUIFindWindow(null, true, "Store", "StoreCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtStoreCodeT.setText(code);
        txtStoreT.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void loadTable() {
        int i = jTable1.getSelectedRow();
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        String itc = df.getValueAt(i, 1).toString();
        boolean b = itc != null && jTable1.getSelectedColumn() == 1;
        if (b) {

            obj = SERStockTransfer.getTablInfo(itc, txtStoreCodeF.getText());
            df.setValueAt(obj.getName(), i, 2);
            df.setValueAt(obj.getUnit(), i, 3);
            df.setValueAt("0", i, 4);
            df.setValueAt(obj.getWar(), i, 5);
            df.setValueAt(Locals.sCurrencyFormat(obj.getSellingRate()), i, 6);
            df.setValueAt("0.00", i, 7);
            df.setValueAt("0.00", i, 8);
            costs.add(obj.getCostRate());
            //String name = SERCommen.getDescription( "ItemMaster", itc, "ItemCode", "ShortName");
            // String unit = SERCommen.getDescription( "ItemMaster", itc, "ItemCode", "UnitCode");
            // Double minlev = Double.parseDouble(SERCommen.getDescription( "ItemMaster", itc, "ItemCode", "MinLevel"));
            // String Warr = SERCommen.getDescription( "Stock", itc + "AND Store = '"+txtStoreCode.getText()+"'", "ItemCode", "Warranty");
            // Double Rate = Double.parseDouble(SERCommen.getDescription( "Stock", itc + "AND Store = '"+txtStoreCode.getText()+"'", "ItemCode", "SellingRate"));
        }
        boolean b1 = (df.getValueAt(i, 4).toString() != null && df.getValueAt(i, 6).toString() != null) && (jTable1.getSelectedColumn() == 4 || jTable1.getSelectedColumn() == 6 || jTable1.getSelectedColumn() == 7);
        if (b1) {
            doCalc(i, df);
        }
        doCalAll();
    }

    private void doCalc(int i, DefaultTableModel df) {
        double d = Double.parseDouble(df.getValueAt(i, 4).toString());
        double d1 = Double.parseDouble(df.getValueAt(i, 6).toString());
        df.setValueAt(Locals.currencyFormat(d * d1), i, 8);
    }

    private void doCalAll() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        double d1 = 0.00;

        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            d1 += Double.parseDouble(df.getValueAt(i, 8).toString());
        }
        txtNetAmount.setText(Locals.currencyFormat(d1));

    }

    private void setItem() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int i = jTable1.getSelectedRow();
        String itc = df.getValueAt(i, 1).toString();
        String itd = df.getValueAt(i, 2).toString();
        txtSelectedItem.setText(itc);
        txtSelectedItemName.setText(itd);
    }

    private void loadItem() {
        if (!txtStoreCodeF.getText().isEmpty()) {
            ItemList i = new ItemList(null, true, txtStoreCodeF.getText());
            i.setVisible(true);
            if (ItemList.itemSearch.getCode() != null) {
                jTextField1.setText(ItemList.itemSearch.getCode());
            }
            ItemList.itemSearch = null;
            jTextField1.setFocusable(false);
            cellEdit();
            loadTable();
        } else {
            JOptionPane.showMessageDialog(null, "Pleas select the store first...");
            txtStoreCodeF.grabFocus();
        }
    }

    private void cellEdit() {
        int i = jTable1.getSelectedRow();
        if (i >= 0) {
            jTable1.grabFocus();
            jTable1.setCellSelectionEnabled(true);
            jTable1.editCellAt(i, 4);
            jFormattedTextField1.grabFocus();
            jFormattedTextField1.selectAll();
        }
    }

    /*
     * private void setDate() { Calendar c = Calendar.getInstance();
     * txtCrPayDate.setDate(Locals.toDate(Locals.setDateFormat(c.getTime())));
     * txtInvDate.setDate(Locals.toDate(Locals.setDateFormat(c.getTime())));
     }
     */
    @SuppressWarnings("unchecked")
    private void initOthers() {
        setMode(DEFAULT_STATUS);
        setID();
        doNew();
        //setDate();
        cmdFirst.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFirstActionPerformed(evt);
            }
        });
        cmdLast.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLastActionPerformed(evt);
            }
        });
        cmdPrev.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPrevActionPerformed(evt);
            }
        });
        cmdNext.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNextActionPerformed(evt);
            }
        });
        cmdFind.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFindActionPerformed(evt);
            }
        });
        cmdNew.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        cmdView.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdViewActionPerformed(evt);
            }
        });
        cmdSave.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });

        cmdStoreF.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStoreActionPerformed(evt);
            }
        });

        cmdReport.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReportActionPerformed(evt);
            }
        });

        cmdStoreT.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAreaActionPerformed(evt);
            }
        });

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane11 = new javax.swing.JLayeredPane();
        jLabel26 = new javax.swing.JLabel();
        txtSelectedItem = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtSelectedItemName = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtjvnletter = new javax.swing.JTextField();
        txtTransNo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTransDate = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        jLayeredPane9 = new javax.swing.JLayeredPane();
        txtStoreCodeF = new javax.swing.JTextField();
        cmdStoreF = new javax.swing.JButton();
        txtStoreF = new javax.swing.JTextField();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        txtStoreCodeT = new javax.swing.JTextField();
        cmdStoreT = new javax.swing.JButton();
        txtStoreT = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtPrepBy = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtApproBy = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtRemark = new javax.swing.JTextField();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        txtNetAmount = new javax.swing.JFormattedTextField();
        jLayeredPane12 = new javax.swing.JLayeredPane();
        jLabel28 = new javax.swing.JLabel();
        txtSelectedItem1 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtSelectedItemName1 = new javax.swing.JLabel();

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("ItemCode");
        jLabel26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane11.add(jLabel26);
        jLabel26.setBounds(0, 0, 130, 20);

        txtSelectedItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItem.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItem.setText("jLabel14");
        txtSelectedItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane11.add(txtSelectedItem);
        txtSelectedItem.setBounds(130, 0, 170, 20);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Description");
        jLabel27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane11.add(jLabel27);
        jLabel27.setBounds(310, 0, 140, 20);

        txtSelectedItemName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItemName.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItemName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItemName.setText("jLabel16");
        txtSelectedItemName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane11.add(txtSelectedItemName);
        txtSelectedItemName.setBounds(450, 0, 330, 20);

        jTextField1.setText("jTextField1");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jFormattedTextField1.setText("jFormattedTextField1");

        setClosable(true);
        setTitle("Stock Transfer");

        jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jToolBar1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

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

        jTable1.setForeground(new java.awt.Color(102, 51, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "", "", "", "", "", "", "", "", ""}
            },
            new String [] {
                "", "Item Code", "Descrip", "Unit", "Qty.", "Warranty", "Rate", "Discount", "Net", "S/N"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setRowHeight(25);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(1).setCellEditor(null);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(280);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(135);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(135);
            jTable1.getColumnModel().getColumn(4).setCellEditor(null);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(160);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(145);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(165);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(150);
        }

        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setText("Trans. Number");

        txtjvnletter.setEditable(false);
        txtjvnletter.setBackground(new java.awt.Color(255, 255, 233));
        txtjvnletter.setForeground(new java.awt.Color(153, 0, 0));
        txtjvnletter.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtjvnletter.setText("S");
        txtjvnletter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtTransNo.setEditable(false);
        txtTransNo.setBackground(new java.awt.Color(255, 255, 233));
        txtTransNo.setForeground(new java.awt.Color(153, 0, 0));
        txtTransNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setText("Trans. Date");

        txtTransDate.setDateFormatString("yyyy-MM-dd");

        jLabel21.setText("From");

        txtStoreCodeF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(txtStoreCodeF);
        txtStoreCodeF.setBounds(0, 0, 70, 20);

        cmdStoreF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdStoreF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdStoreF.setContentAreaFilled(false);
        jLayeredPane9.add(cmdStoreF);
        cmdStoreF.setBounds(71, 0, 20, 20);

        txtStoreF.setBackground(new java.awt.Color(255, 255, 241));
        txtStoreF.setEditable(false);
        txtStoreF.setForeground(new java.awt.Color(153, 0, 0));
        txtStoreF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(txtStoreF);
        txtStoreF.setBounds(95, 0, 190, 18);

        txtStoreCodeT.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane6.add(txtStoreCodeT);
        txtStoreCodeT.setBounds(0, 0, 70, 20);

        cmdStoreT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdStoreT.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdStoreT.setContentAreaFilled(false);
        jLayeredPane6.add(cmdStoreT);
        cmdStoreT.setBounds(71, 0, 20, 20);

        txtStoreT.setBackground(new java.awt.Color(255, 255, 241));
        txtStoreT.setEditable(false);
        txtStoreT.setForeground(new java.awt.Color(153, 0, 0));
        txtStoreT.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane6.add(txtStoreT);
        txtStoreT.setBounds(95, 0, 220, 18);

        jLabel25.setText("To");

        txtPrepBy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel14.setText("Prepared By");

        jLabel12.setText("Approved By");

        txtApproBy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel13.setText("Remarks");

        txtRemark.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("Print Transfer Report");

        jLabel20.setBackground(new java.awt.Color(102, 102, 102));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Net Amount");
        jLabel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel20.setOpaque(true);

        txtNetAmount.setEditable(false);
        txtNetAmount.setBackground(new java.awt.Color(249, 249, 229));
        txtNetAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNetAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtNetAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNetAmount.setText("0.00");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("ItemCode");
        jLabel28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane12.add(jLabel28);
        jLabel28.setBounds(0, 0, 130, 20);

        txtSelectedItem1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItem1.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItem1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItem1.setText("jLabel14");
        txtSelectedItem1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane12.add(txtSelectedItem1);
        txtSelectedItem1.setBounds(130, 0, 170, 20);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Description");
        jLabel29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane12.add(jLabel29);
        jLabel29.setBounds(310, 0, 140, 20);

        txtSelectedItemName1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItemName1.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItemName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItemName1.setText("jLabel16");
        txtSelectedItemName1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane12.add(txtSelectedItemName1);
        txtSelectedItemName1.setBounds(450, 0, 330, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtApproBy, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPrepBy, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(txtNetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtRemark)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtjvnletter, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTransNo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTransDate, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCheckBox2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLayeredPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addComponent(jLayeredPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTransNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtjvnletter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTransDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLayeredPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLayeredPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrepBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApproBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRemark, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToolBar1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jToolBar1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jToolBar1AncestorAdded

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if (evt.getKeyCode() == 127 && jTable1.getSelectedRow() >= 0) {
            doDeleteRow();
        } else if (evt.getKeyCode() == evt.VK_F3) {
            addNewRow();
        } else if (evt.getKeyCode() == evt.VK_ENTER) {
            loadTable();
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        loadItem();
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        loadItem();
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        setItem();
    }

    private void cmdStoreActionPerformed(java.awt.event.ActionEvent evt) {
        getStoreF();
    }

    private void cmdAreaActionPerformed(java.awt.event.ActionEvent evt) {
        getStoreT();
    }

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {

        doSave();

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
        indexCount = SERStockTransfer.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index = indexCount - 1;
        }
    }

    private void cmdLastActionPerformed(java.awt.event.ActionEvent evt) {
        Index = SERStockTransfer.getIndex() - 1;
        getNavi();
    }

    private void cmdFindActionPerformed(java.awt.event.ActionEvent evt) {
        doFind();
    }

    private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {
        Act = 1;
        doNew();
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

    private void cmdViewActionPerformed(java.awt.event.ActionEvent evt) {
        Index = 0;
        doView();
    }

    private void cmdReportActionPerformed(java.awt.event.ActionEvent evt) {
        printTrans();
    }
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
    private javax.swing.JButton cmdStoreF;
    private javax.swing.JButton cmdStoreT;
    private javax.swing.JButton cmdView;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane11;
    private javax.swing.JLayeredPane jLayeredPane12;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JLayeredPane jLayeredPane9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtApproBy;
    private javax.swing.JFormattedTextField txtNetAmount;
    private javax.swing.JTextField txtPrepBy;
    private javax.swing.JTextField txtRemark;
    private javax.swing.JLabel txtSelectedItem;
    private javax.swing.JLabel txtSelectedItem1;
    private javax.swing.JLabel txtSelectedItemName;
    private javax.swing.JLabel txtSelectedItemName1;
    private javax.swing.JTextField txtStoreCodeF;
    private javax.swing.JTextField txtStoreCodeT;
    private javax.swing.JTextField txtStoreF;
    private javax.swing.JTextField txtStoreT;
    private com.toedter.calendar.JDateChooser txtTransDate;
    private javax.swing.JTextField txtTransNo;
    private javax.swing.JTextField txtjvnletter;
    // End of variables declaration//GEN-END:variables
    private OBJStockTransfer obj;
    private OBJStockTransfer obj_1;
    //  private OBJInvPayPlan objpp;
    private ArrayList<OBJStockTransfer> obja;
    private ArrayList costs;
    private String cost;

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
    private int Index = 0;
    private int indexCount = 0;
}
