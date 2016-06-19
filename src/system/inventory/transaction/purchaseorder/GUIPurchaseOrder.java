/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIDelivaryON.java
 *
 * Created on Jul 22, 2013, 6:55:31 PM
 */
package system.inventory.transaction.purchaseorder;

import core.Locals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.SERCommen;
import mainApp.findwindow.GUIFindWindow;

/**
 *
 * @author dell
 */
public class GUIPurchaseOrder extends javax.swing.JInternalFrame {

    /** Creates new form GUIDelivaryON */
    public GUIPurchaseOrder() {
        initComponents();
        initOthers();
    }

    private void doSave() {
        String code = txtPOCode.getText();
        String name = txtVendorCode.getText();
        if (code.isEmpty() || name.isEmpty()) {
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
                obj = new OBJPurchaseOrder(
                        txtPOCode.getText(),
                        core.Locals.setDateFormat(txtDate.getDate()),
                        txtVendorCode.getText(),
                        txtSRep.getText(),
                        txtCostCode.getText(),
                        txtStoreCode.getText(),
                        txtCurrencyCode.getSelectedItem().toString(),
                        txtFCRate.getText(),
                        txtPayTermCode.getText(),
                        core.Locals.setDateFormat(txtEDDate.getDate()),
                        txtComment.getText(),
                        txtGrossAmount.getText(),
                        txtDiscount.getText(),
                        txtDiscountRate.getText(),
                        txtExpenses.getText(),
                        txtNetAmount.getText(),
                        accountpackage.AccountPackage.user,
                        txtReqNo.getText());

                SERPurchaseOrder.save(obj, Act);
                obja = new ArrayList<OBJPurchaseOrder>();
                int row = jTable1.getRowCount();
                for (int i = 0; i < row; i++) {
                    obj = new OBJPurchaseOrder(
                            txtPOCode.getText(),
                            jTable1.getValueAt(i, 1).toString(),
                            jTable1.getValueAt(i, 2).toString(),
                            jTable1.getValueAt(i, 3).toString(),
                            jTable1.getValueAt(i, 4).toString(),
                            jTable1.getValueAt(i, 5).toString(),
                            jTable1.getValueAt(i, 6).toString(),
                            jTable1.getValueAt(i, 7).toString()
                        );
                    obja.add(obj);
                }
                SERPurchaseOrder.saveHistory(obja, Act);
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
        txtDate.setDate(Calendar.getInstance().getTime());
        txtVendorCode.setText("");
        txtVendorName.setText("");
        txtReqNo.setText("");
        txtCostCode.setText("");
        txtCostCenter.setText("");
        txtStoreCode.setText("");
        txtStore.setText("");
        txtCurrencyCode.setSelectedIndex(0);
        txtFCRate.setText("");
        txtEDDate.setDate(Calendar.getInstance().getTime());
        txtPayTermCode.setText("");
        txtComment.setText("");
        txtGrossAmount.setText("0.00");
        txtDiscount.setText("0.00");
        txtDiscountRate.setText("0.00");
        txtExpenses.setText("0.00");
        txtNetAmount.setText("0.00");
        txtSRep.setText("");
        txtSRepName.setText("");
        clerTbl();
        addNewRow();
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
    }

    private void clerTbl(){
        int rw = jTable1.getRowCount();
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < rw; i++) {
            df.removeRow(0);
        }               
    }
    
    private void addNewRow() {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getRowCount();
        dt.addRow(new Object[]{row + 1, "", "", "", "", "", "", "", "", ""});
    }

    private void doEdit() {
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
        txtPOCode.setEnabled(false);
    }

    private void doDelete() {
        String code = txtPOCode.getText();
        SERPurchaseOrder.delete(code);
        indexCount = SERPurchaseOrder.getIndex();
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
        //txtCode.setEnabled(state);
        txtDate.setEnabled(state);
        txtVendorCode.setEnabled(state);
        //txtVendorName.setEnabled(state);
        txtReqNo.setEnabled(state);
        txtCostCode.setEnabled(state);
        //txtCostCenter.setEnabled(state);
        txtStoreCode.setEnabled(state);
        //txtStore.setEnabled(state);
        txtCurrencyCode.setSelectedIndex(0);
        txtFCRate.setEnabled(state);
        txtEDDate.setEnabled(state);
        txtPayTermCode.setEnabled(state);
        txtComment.setEnabled(state);
        txtGrossAmount.setEnabled(state);
        txtDiscount.setEnabled(state);
        txtDiscountRate.setEnabled(state);
        txtExpenses.setEnabled(state);
        txtNetAmount.setEnabled(state);
        txtSRep.setEnabled(state);
        jTable1.setEnabled(state);
        
        
        if (state) {
            jTable1.setBackground(new java.awt.Color(255, 255, 255));
        } else {
            jTable1.setBackground(new java.awt.Color(249, 249, 229));
        }
        /*
         * -----******( )*****-----
         */

        cmdVendor.setEnabled(state);
        //cmdCusAddress.setEnabled(state);
        cmdCost.setEnabled(state);
        cmdPayTerm.setEnabled(state);
        cmdSRep.setEnabled(state);
        cmdStore.setEnabled(state);
    }

    private void doFind() {
        new GUIFindWindow(null, true, "POHeader", "OrderNo", "VendCode").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void getNavi() {
        obj = SERPurchaseOrder.getNavi(Index);
        obja = SERPurchaseOrder.getHistory(obj.getOrderNo());
        setContent(obj);
        setContentHistory(obja);
    }

    private void serch(String code) {
        obj = SERPurchaseOrder.serch(code);
        obja = SERPurchaseOrder.getHistory(obj.getOrderNo());
        setContent(obj);
        setContentHistory(obja);
//        txtPOCode.setText(obj.getCode());
//        txtName.setText(obj.getName());
//        txtAreaCode.setText(obj.getArea());
//        txtArea.setText(SERSalesRep.setName(obj.getArea(), "Area", "AreaCode"));
//        txtAddress1.setText(obj.getAdd1());
//        txtAddress2.setText(obj.getAdd2());
//        txtAddress3.setText(obj.getAdd3());
//        txtPobox.setText(obj.getPbox());
//        txtTel.setText(obj.getTellOff());
//        txtFax.setText(obj.getFax());
//        txtmobi.setText(obj.getMobi());
//        txtEmail.setText(obj.getEmail());
//        txtTargt.setText(obj.getTarget());
    }
    
    private void setContent(OBJPurchaseOrder obj) {
        txtPOCode.setText(obj.getOrderNo());
        Date da = null;
        try {
            da = new SimpleDateFormat("yyyy-MM-dd").parse(obj.getOrderDate());
        } catch (ParseException ex) {
           core.Exp.Handle(ex);
        }
        txtDate.setDate(da);
        txtVendorCode.setText( obj.getVendCode());
        txtVendorName.setText(SERCommen.getDescription("customer", txtVendorCode.getText(), "CustCode", "CustName"));
        txtReqNo.setText(obj.getRequestNo());
        txtCostCode.setText(obj.getCostCode());
        txtCostCenter.setText(SERCommen.getDescription("costcenter", txtCostCode.getText(), "CostCode", "Description"));
        txtStoreCode.setText(obj.getStoreCode());
        txtStore.setText(SERCommen.getDescription("store", txtStoreCode.getText(), "StoreCode", "Description"));
        txtCurrencyCode.setSelectedItem(obj.getFCCode());
        txtFCRate.setText(obj.getFCRate());
        Date da1 = null;
        try {
            da1 = new SimpleDateFormat("yyyy-MM-dd").parse(obj.getDueDate());
        } catch (ParseException ex) {
           core.Exp.Handle(ex);
        }
        txtEDDate.setDate(da1);
        txtPayTermCode.setText(obj.getPaymentTerms());
        txtComment.setText(obj.getComment());
        txtGrossAmount.setText(obj.getGrossAmount());
        txtDiscount.setText(obj.getTotalDiscount());
        txtDiscountRate.setText(obj.getDiscountRate());
        txtExpenses.setText(obj.getExpence());
        txtNetAmount.setText(obj.getNetAmount());
        txtSRep.setText(obj.getRepCode());
        txtSRepName.setText(SERCommen.getDescription("salesrep", txtSRep.getText(), "RepCode", "RepName"));
    }

    private void setContentHistory(ArrayList <OBJPurchaseOrder> lobja) {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        clrTable();
        int i = 1;
        if (!obja.isEmpty()) {
        for (OBJPurchaseOrder objq : lobja) {
            dt.addRow(new Object[]{i, objq.getItemCode(), objq.getItemDescription(), objq.getUnitCode(), objq.getQuantity(),objq.getRate(),objq.getDiscount(),objq.getAmount()});
            i++;
        }
        }
        jTable1.setEnabled(false);
    }
    private void setID() {
        txtPOCode.setText(SERPurchaseOrder.getID());
    }

    private void doDeleteRow() {
        int selRow = jTable1.getSelectedRow();
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        dt.removeRow(selRow);
    }
    
    private void clrTable() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int rc = jTable1.getRowCount();
        for (int i = 0; i < rc; i++) {
            df.removeRow(0);
        }
    }
    
    private void getVendor() {
        new GUIFindWindow(null, true, "Customer", "CustCode", "CustName", "V").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtVendorCode.setText(code);
        txtVendorName.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
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
    
    private void getStore() {
        new GUIFindWindow(null, true, "Store", "StoreCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtStoreCode.setText(code);
        txtStore.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }
    
    private void getPayTerm() {
        new GUIFindWindow(null, true, "PayTerms", "PTCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtPayTermCode.setText(code);
        //txtCostCenter.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }
    
        private void chBoxAct() {
        if (jCheckBox1.isSelected()) {
            int q = JOptionPane.showConfirmDialog(this, "Are you want to perform this..?\n Discount colenm will set 0.00.", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (q == 0) {
                txtDiscountRate.setEditable(true);
                txtDiscount.setEditable(true);
                txtDiscountRate.setText("0.00");
                txtDiscount.setText("0.00");
                txtDiscount.setBackground(new java.awt.Color(255, 255, 255));
                txtDiscountRate.setBackground(new java.awt.Color(255, 255, 255));
                setDisc(true);
     //           calcDue();
            }
        } else {
            txtDiscount.setBackground(new java.awt.Color(249, 249, 229));
            txtDiscountRate.setBackground(new java.awt.Color(249, 249, 229));
            txtDiscountRate.setText("0.00");
            txtDiscountRate.setEditable(!true);
            txtDiscount.setEditable(!true);
            txtDiscount.setText("0.00");
            setDisc(false);
//            calcDue();
        }
    }
        
    private void doCalc(int i, DefaultTableModel df) {
        double d = Double.parseDouble(df.getValueAt(i, 4).toString());
        double d1 = Double.parseDouble(df.getValueAt(i, 5).toString());
        df.setValueAt(Locals.currencyFormat(d * d1), i, 7);
    }

    private void doCalAll() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        double d1 = 0.00;
        double d = 0.00;
        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            d += Double.parseDouble(df.getValueAt(i, 6).toString());
            d1 += Double.parseDouble(df.getValueAt(i, 7).toString());
        }
        txtGrossAmount.setText(Locals.currencyFormat(d1));
        txtDiscount.setText(Locals.currencyFormat(d));
        txtNetAmount.setText(Locals.currencyFormat(d1 - d));
//        calcDue();
    }

    private void setDisc(boolean b) {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            df.setValueAt("0.00", i, 6);
        }
        calcTotDisc();
    }

    private void calcTotDisc() {
        double d = Double.parseDouble(txtGrossAmount.getText());
        double d1 = Double.parseDouble(txtDiscountRate.getText());
        d1 = (d * d1) / 100;
        txtDiscount.setText(Locals.currencyFormat(d1));
        txtNetAmount.setText(Locals.currencyFormat(d - d1));
       // calcDue();
    }

    //private void calcDue() {
      //  double d = Double.parseDouble(txtNetAmount.getText());
      //  double d1 = Double.parseDouble(txtPay.getText());
       // d1 = (d - d1);
       // txtDue.setText(Locals.currencyFormat(d1));

  //  }

    private void setItem() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int i = jTable1.getSelectedRow();
        String itc = df.getValueAt(i, 1).toString();
        String itd = df.getValueAt(i, 2).toString();
        txtSelectedItem.setText(itc);
        txtSelectedItemName.setText(itd);
    }

    private void loadCurrency() {
        Vector v = new Vector();
        v = SERPurchaseOrder.loadCurrency();
        txtCurrencyCode.setModel(new DefaultComboBoxModel(v));
        loadFCRate();
    }

    private void loadFCRate() {
        double d = 0.00;
        d = SERPurchaseOrder.getFCRate(txtCurrencyCode.getSelectedItem().toString());
        txtFCRate.setText(core.Locals.currencyFormat(d));
    }

    private void loadTable() {
        int i = jTable1.getSelectedRow();
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        String itc = df.getValueAt(i, 1).toString();
        boolean b = itc != null && jTable1.getSelectedColumn() == 1;
        if (b) {

            obj = SERPurchaseOrder.getTablInfo(itc, txtStoreCode.getText());
            df.setValueAt(obj.getName(), i, 2);
            df.setValueAt(obj.getUnit(), i, 3);
            df.setValueAt("0", i, 4);
            df.setValueAt(Locals.currencyFormat(obj.getSellingRate()), i, 5);
            df.setValueAt("0.00", i, 6);
            df.setValueAt("0.00", i, 7);

            //String name = SERCommen.getDescription( "ItemMaster", itc, "ItemCode", "ShortName");
            // String unit = SERCommen.getDescription( "ItemMaster", itc, "ItemCode", "UnitCode");
            // Double minlev = Double.parseDouble(SERCommen.getDescription( "ItemMaster", itc, "ItemCode", "MinLevel"));
            // String Warr = SERCommen.getDescription( "Stock", itc + "AND Store = '"+txtStoreCode.getText()+"'", "ItemCode", "Warranty");
            // Double Rate = Double.parseDouble(SERCommen.getDescription( "Stock", itc + "AND Store = '"+txtStoreCode.getText()+"'", "ItemCode", "SellingRate"));

        }
        boolean b1 = (df.getValueAt(i, 4).toString() != null && df.getValueAt(i, 5).toString() != null) && (jTable1.getSelectedColumn() == 4 || jTable1.getSelectedColumn() == 5 || jTable1.getSelectedColumn() == 6);
        if (b1) {
            doCalc(i, df);
        }
        doCalAll();
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
        loadCurrency();
        doNew();
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
        
        cmdCost.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                  cmdCostActionPerformed(evt);
            }
        });
        
        cmdStore.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                  cmdStoreActionPerformed(evt);
            }
        });
        
        cmdPayTerm.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                  cmdPayTermActionPerformed(evt);
            }
        });
        
        txtDiscountRate.setBackground(new java.awt.Color(249, 249, 229));
        txtDiscountRate.setEditable(false);
        txtDiscountRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtDiscountRate.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtDiscountRate.setText("0.00");
        txtDiscountRate.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountRateActionPerformed(evt);
            }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbUnit = new javax.swing.JComboBox();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPOCode = new javax.swing.JTextField();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        cmdAdd = new javax.swing.JButton();
        txtVendorName = new javax.swing.JTextField();
        txtVendorCode = new javax.swing.JTextField();
        cmdVendor = new javax.swing.JButton();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        cmdCost = new javax.swing.JButton();
        txtCostCenter = new javax.swing.JTextField();
        txtCostCode = new javax.swing.JTextField();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        cmdStore = new javax.swing.JButton();
        txtStore = new javax.swing.JTextField();
        txtStoreCode = new javax.swing.JTextField();
        txtDate = new com.toedter.calendar.JDateChooser();
        txtFCRate = new javax.swing.JFormattedTextField();
        txtCurrencyCode = new javax.swing.JComboBox();
        jLayeredPane7 = new javax.swing.JLayeredPane();
        cmdPayTerm = new javax.swing.JButton();
        txtPayTermCode = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLayeredPane8 = new javax.swing.JLayeredPane();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        txtDiscountRate = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        txtGrossAmount = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        txtExpenses = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        txtNetAmount = new javax.swing.JFormattedTextField();
        txtReqNo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComment = new javax.swing.JTextArea();
        txtEDDate = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        cmdSRep = new javax.swing.JButton();
        txtSRepName = new javax.swing.JTextField();
        txtSRep = new javax.swing.JTextField();
        jLayeredPane9 = new javax.swing.JLayeredPane();
        jLabel13 = new javax.swing.JLabel();
        txtSelectedItem = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSelectedItemName = new javax.swing.JLabel();

        cbUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setClosable(true);
        setTitle("Purchase Order");

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

        jLabel1.setText("PO No.");

        jLabel2.setText("PO.Date");

        jLabel3.setText("Req. No.");

        jLabel4.setText("Vendor");

        jLabel5.setText("Cost Center");

        jLabel6.setText("Estimated Delivery Date");

        jLabel7.setText("Store");

        jLabel8.setText("Currency");
        jLabel8.setEnabled(false);

        jLabel9.setText("FC.Rate");
        jLabel9.setEnabled(false);

        jLabel10.setText("Pay Term");

        jLabel11.setText("Comment/Details");

        txtPOCode.setEditable(false);
        txtPOCode.setBackground(new java.awt.Color(255, 255, 204));
        txtPOCode.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtPOCode.setForeground(new java.awt.Color(204, 0, 0));
        txtPOCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        cmdAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/font.png"))); // NOI18N
        cmdAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdAdd.setContentAreaFilled(false);
        cmdAdd.setSelected(true);
        jLayeredPane3.add(cmdAdd);
        cmdAdd.setBounds(370, 0, 20, 20);

        txtVendorName.setBackground(new java.awt.Color(255, 255, 241));
        txtVendorName.setEditable(false);
        txtVendorName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtVendorName);
        txtVendorName.setBounds(115, 0, 250, 18);

        txtVendorCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtVendorCode);
        txtVendorCode.setBounds(0, 0, 90, 18);

        cmdVendor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdVendor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdVendor.setContentAreaFilled(false);
        cmdVendor.setSelected(true);
        cmdVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdVendorActionPerformed(evt);
            }
        });
        jLayeredPane3.add(cmdVendor);
        cmdVendor.setBounds(93, 0, 20, 20);

        cmdCost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdCost.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdCost.setContentAreaFilled(false);
        cmdCost.setSelected(true);
        jLayeredPane4.add(cmdCost);
        cmdCost.setBounds(53, 0, 21, 20);

        txtCostCenter.setBackground(new java.awt.Color(255, 255, 241));
        txtCostCenter.setEditable(false);
        txtCostCenter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtCostCenter);
        txtCostCenter.setBounds(75, 0, 170, 18);

        txtCostCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtCostCode);
        txtCostCode.setBounds(0, 0, 50, 18);

        cmdStore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdStore.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdStore.setContentAreaFilled(false);
        cmdStore.setSelected(true);
        jLayeredPane5.add(cmdStore);
        cmdStore.setBounds(53, 0, 20, 20);

        txtStore.setEditable(false);
        txtStore.setBackground(new java.awt.Color(255, 255, 241));
        txtStore.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane5.add(txtStore);
        txtStore.setBounds(75, 0, 250, 18);

        txtStoreCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane5.add(txtStoreCode);
        txtStoreCode.setBounds(0, 0, 50, 18);

        txtDate.setDateFormatString("yyyy-MM-dd");

        txtFCRate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtFCRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtFCRate.setEnabled(false);

        txtCurrencyCode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtCurrencyCode.setEnabled(false);

        cmdPayTerm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdPayTerm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdPayTerm.setContentAreaFilled(false);
        cmdPayTerm.setSelected(true);
        jLayeredPane7.add(cmdPayTerm);
        cmdPayTerm.setBounds(150, 0, 20, 20);

        txtPayTermCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane7.add(txtPayTermCode);
        txtPayTermCode.setBounds(0, 0, 150, 18);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Item Code", "Descrip", "Unit", "Qty.", "Rate", "Discount", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, true, true, true, false
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
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(15);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(45);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(75);
        }

        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jLayeredPane8.add(jCheckBox1);
        jCheckBox1.setBounds(50, 20, 20, 21);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Expenses");
        jLayeredPane8.add(jButton1);
        jButton1.setBounds(0, 40, 140, 20);

        txtDiscountRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jLayeredPane8.add(txtDiscountRate);
        txtDiscountRate.setBounds(70, 20, 50, 20);

        jLabel17.setBackground(new java.awt.Color(102, 102, 102));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Gross Amount");
        jLabel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel17.setOpaque(true);
        jLayeredPane8.add(jLabel17);
        jLabel17.setBounds(0, 0, 140, 20);

        txtGrossAmount.setBackground(new java.awt.Color(249, 249, 229));
        txtGrossAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtGrossAmount.setEditable(false);
        txtGrossAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtGrossAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtGrossAmount.setText("0.00");
        jLayeredPane8.add(txtGrossAmount);
        txtGrossAmount.setBounds(140, 0, 100, 20);

        jLabel18.setBackground(new java.awt.Color(102, 102, 102));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Discont                   %");
        jLabel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel18.setOpaque(true);
        jLayeredPane8.add(jLabel18);
        jLabel18.setBounds(0, 20, 140, 20);

        txtDiscount.setBackground(new java.awt.Color(249, 249, 229));
        txtDiscount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDiscount.setForeground(new java.awt.Color(153, 0, 0));
        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscount.setText("110.00");
        txtDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountActionPerformed(evt);
            }
        });
        jLayeredPane8.add(txtDiscount);
        txtDiscount.setBounds(140, 20, 100, 20);

        jLabel19.setBackground(new java.awt.Color(102, 102, 102));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Net Amount");
        jLabel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel19.setOpaque(true);
        jLayeredPane8.add(jLabel19);
        jLabel19.setBounds(0, 40, 140, 20);

        txtExpenses.setBackground(new java.awt.Color(249, 249, 229));
        txtExpenses.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtExpenses.setEditable(false);
        txtExpenses.setForeground(new java.awt.Color(153, 0, 0));
        txtExpenses.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtExpenses.setText("0.00");
        jLayeredPane8.add(txtExpenses);
        txtExpenses.setBounds(140, 40, 100, 20);

        jLabel20.setBackground(new java.awt.Color(102, 102, 102));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Net Amount");
        jLabel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel20.setOpaque(true);
        jLayeredPane8.add(jLabel20);
        jLabel20.setBounds(0, 60, 140, 20);

        txtNetAmount.setBackground(new java.awt.Color(249, 249, 229));
        txtNetAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNetAmount.setEditable(false);
        txtNetAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtNetAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNetAmount.setText("0.00");
        jLayeredPane8.add(txtNetAmount);
        txtNetAmount.setBounds(140, 60, 100, 20);

        txtReqNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtComment.setColumns(20);
        txtComment.setRows(2);
        jScrollPane2.setViewportView(txtComment);

        txtEDDate.setDateFormatString("yyyy-MM-dd");

        jLabel12.setText("S. Rep");

        cmdSRep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdSRep.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdSRep.setContentAreaFilled(false);
        cmdSRep.setSelected(true);
        jLayeredPane6.add(cmdSRep);
        cmdSRep.setBounds(53, 0, 20, 20);

        txtSRepName.setEditable(false);
        txtSRepName.setBackground(new java.awt.Color(255, 255, 241));
        txtSRepName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane6.add(txtSRepName);
        txtSRepName.setBounds(75, 0, 200, 18);

        txtSRep.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane6.add(txtSRep);
        txtSRep.setBounds(0, 0, 50, 18);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtReqNo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtCurrencyCode, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtFCRate, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEDDate, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLayeredPane5)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPOCode, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLayeredPane6))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLayeredPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLayeredPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPOCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtReqNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCurrencyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtEDDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtFCRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(jLayeredPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ItemCode");
        jLabel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(jLabel13);
        jLabel13.setBounds(0, 0, 88, 20);

        txtSelectedItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItem.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItem.setText("ITM001");
        txtSelectedItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(txtSelectedItem);
        txtSelectedItem.setBounds(90, 0, 170, 20);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Description");
        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(jLabel15);
        jLabel15.setBounds(260, 0, 130, 20);

        txtSelectedItemName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItemName.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItemName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItemName.setText("TEST ITEM");
        txtSelectedItemName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(txtSelectedItemName);
        txtSelectedItemName.setBounds(390, 0, 360, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane9)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdVendorActionPerformed
        getVendor();
    }//GEN-LAST:event_cmdVendorActionPerformed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
       doSave();
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if (evt.getKeyCode() == 127 && jTable1.getSelectedRow() >= 0) {
            doDeleteRow();
        } else if (evt.getKeyCode() == evt.VK_F3) {
            addNewRow();
        } else if (evt.getKeyCode() == evt.VK_ENTER) {
            loadTable();
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void txtDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscountActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
       chBoxAct();
    }//GEN-LAST:event_jCheckBox1ActionPerformed
    
    private void txtDiscountRateActionPerformed(java.awt.event.ActionEvent evt) {
        calcTotDisc();
    }

    private void cmdCostActionPerformed(java.awt.event.ActionEvent evt) {                                          
        getCost();
    } 
        
    private void cmdStoreActionPerformed(java.awt.event.ActionEvent evt) {                                          
        getStore();
    } 
        
    private void cmdPayTermActionPerformed(java.awt.event.ActionEvent evt) {                                          
        getPayTerm();
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
        indexCount = SERPurchaseOrder.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index = indexCount - 1;
        }
    }

    private void cmdLastActionPerformed(java.awt.event.ActionEvent evt) {
        Index = SERPurchaseOrder.getIndex() - 1;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbUnit;
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdCost;
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
    private javax.swing.JButton cmdSRep;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdStore;
    private javax.swing.JButton cmdVendor;
    private javax.swing.JButton cmdView;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JLayeredPane jLayeredPane7;
    private javax.swing.JLayeredPane jLayeredPane8;
    private javax.swing.JLayeredPane jLayeredPane9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea txtComment;
    private javax.swing.JTextField txtCostCenter;
    private javax.swing.JTextField txtCostCode;
    private javax.swing.JComboBox txtCurrencyCode;
    private com.toedter.calendar.JDateChooser txtDate;
    private javax.swing.JFormattedTextField txtDiscount;
    private javax.swing.JFormattedTextField txtDiscountRate;
    private com.toedter.calendar.JDateChooser txtEDDate;
    private javax.swing.JFormattedTextField txtExpenses;
    private javax.swing.JFormattedTextField txtFCRate;
    private javax.swing.JFormattedTextField txtGrossAmount;
    private javax.swing.JFormattedTextField txtNetAmount;
    private javax.swing.JTextField txtPOCode;
    private javax.swing.JTextField txtPayTermCode;
    private javax.swing.JTextField txtReqNo;
    private javax.swing.JTextField txtSRep;
    private javax.swing.JTextField txtSRepName;
    private javax.swing.JLabel txtSelectedItem;
    private javax.swing.JLabel txtSelectedItemName;
    private javax.swing.JTextField txtStore;
    private javax.swing.JTextField txtStoreCode;
    private javax.swing.JTextField txtVendorCode;
    private javax.swing.JTextField txtVendorName;
    // End of variables declaration//GEN-END:variables
    private OBJPurchaseOrder obj;
    private ArrayList <OBJPurchaseOrder> obja;
    //status
    private static final int DEFAULT_STATUS = 0;
    private static final int NEW_STATUS = 1;
    private int Act = 1;
    private int Index = 0;
    private int indexCount = 0;

}
