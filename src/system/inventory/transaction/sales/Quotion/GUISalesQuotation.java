/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIDelivaryON.java
 *
 * Created on Jul 22, 2013, 6:55:31 PM
 */
package system.inventory.transaction.sales.Quotion;

import core.Locals;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mainApp.SERCommen;
import mainApp.findwindow.GUIFindWindow;
import system.inventory.master.salesrep.SERSalesRep;

/**
 *
 * @author dell
 */
public class GUISalesQuotation extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUIDelivaryON
     */
    public GUISalesQuotation() {
        initComponents();
        initOthers();
    }

    private void doSave() {
        String code = txtQuotNo.getText();
        String name = txtCusCode.getText();

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
                String[] fccode = txtCurrencyCode.getSelectedItem().toString().split(":");
                obj = new OBJQuotation(
                        txtQuotNo.getText(),
                        core.Locals.setDateFormat(txtQoutDate.getDate()),
                        txtCusCode.getText(),
                        txtRepCode.getText(),
                        txtCostCode.getText(),
                        txtStoreCode.getText(),
                        txtArea.getText(),
                        fccode[0],
                        txtFCRate.getText(),
                        txtPayTermCode.getText(),
                        core.Locals.setDateFormat(txtDeliDate.getDate()),
                        txtPrepBy.getText(),
                        txtApproBy.getText(),
                        txtRemark.getText(),
                        txtGrossAmount.getText(),
                        txtDiscount.getText(),
                        txtDiscountRate.getText(),
                        txtNetAmount.getText(),
                        txtPayment.getText(),
                        txtBalance.getText(),
                        accountpackage.AccountPackage.user);

                try {
                    obja = new ArrayList<OBJQuotation>();
                    int row = jTable1.getRowCount();
                    for (int i = 0; i < row; i++) {
                        obj = new OBJQuotation(
                                txtQuotNo.getText(),
                                jTable1.getValueAt(i, 1).toString(),
                                jTable1.getValueAt(i, 2).toString(),
                                jTable1.getValueAt(i, 4).toString(),
                                jTable1.getValueAt(i, 6).toString(),
                                jTable1.getValueAt(i, 7).toString(),
                                jTable1.getValueAt(i, 3).toString(),
                                jTable1.getValueAt(i, 8).toString(),
                                jTable1.getValueAt(i, 5).toString(),
                                jTable1.getValueAt(i, 9).toString());

                        obja.add(obj);
                    }
                    SERQuotation.save(obj, obja, Act);
                } catch (Exception e) {
                    core.Exp.Handle(e);
                }

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
        txtQoutDate.setDate(Calendar.getInstance().getTime());
        txtCusCode.setText("");
        txtCusName.setText("");
        txtCurrencyCode.setSelectedIndex(0);
        txtFCRate.setText("");
        txtCostCode.setText("");
        txtCostCenter.setText("");
        txtRepCode.setText("");
        txtRep.setText("");
        txtStoreCode.setText("");
        txtStore.setText("");
        txtArea.setText("");
        txtDeliDate.setDate(Calendar.getInstance().getTime());
        txtPayTermCode.setText("");
        txtGrossAmount.setText("0.00");
        txtDiscount.setText("0.00");
        txtDiscountRate.setText("");
        txtNetAmount2.setText("0.00");
        txtApproBy.setText("");
        txtPrepBy.setText("");
        txtRemark.setText("");
        jCheckBox1.setSelected(false);
        chBoxAct();

        clrTable();
        addNewRow();
        setEnableAll(true);
        setMode(DEFAULT_STATUS);
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
            }
        } else {
            txtDiscount.setBackground(new java.awt.Color(249, 249, 229));
            txtDiscountRate.setBackground(new java.awt.Color(249, 249, 229));
            txtDiscountRate.setText("0.00");
            txtDiscountRate.setEditable(!true);
            txtDiscount.setEditable(!true);
            txtDiscount.setText("0.00");
            setDisc(false);
        }
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
        txtQuotNo.setEnabled(false);
    }

    private void doDelete() {
        String code = txtQuotNo.getText();
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
        txtQoutDate.setEnabled(state);
        txtCusCode.setEnabled(state);
        txtRepCode.setEnabled(state);
        txtCostCode.setEnabled(state);
        txtStoreCode.setEnabled(state);
        txtArea.setEnabled(state);
        txtCurrencyCode.setEnabled(state);
        txtFCRate.setEnabled(state);
        txtPayTermCode.setEnabled(state);
        txtDeliDate.setEnabled(state);
        txtPrepBy.setEnabled(state);
        txtApproBy.setEnabled(state);
        txtRemark.setEnabled(state);
        txtGrossAmount.setEnabled(state);
        txtDiscount.setEnabled(state);
        txtDiscountRate.setEnabled(state);
        txtNetAmount2.setEnabled(state);
        jCheckBox1.setEnabled(state);
        jTable1.setEnabled(state);

        if (state) {
            jTable1.setBackground(new java.awt.Color(255, 255, 255));
        } else {
            jTable1.setBackground(new java.awt.Color(249, 249, 229));
        }
        /*
         * -----******( )*****-----
         */

        cmdCustomer.setEnabled(state);
        cmdCusAddress.setEnabled(state);
        cmdCostCenter.setEnabled(state);
        cmdPayTerm.setEnabled(state);
        cmdRep.setEnabled(state);
        cmdStore.setEnabled(state);
    }

    private void getNavi() {
        obj = SERQuotation.getNavi(Index);
        obja = SERQuotation.QuotHistory(obj.getQuotNo());
        setContent(obj);
        setContentHistory(obja);
    }

    private void setContent(OBJQuotation obj) {
        txtQuotNo.setText(obj.getQuotNo());
        txtQoutDate.setDate(Locals.toDate(obj.getQuotDate()));
        txtCusCode.setText(obj.getCussCode());
        txtCusName.setText(SERCommen.getDescription("customer", txtCusCode.getText(), "CustCode", "CustName"));
        txtRepCode.setText(obj.getRepCode());
        txtRep.setText(SERCommen.getDescription("salesrep", txtRepCode.getText(), "RepCode", "RepName"));
        txtCostCode.setText(obj.getCostCode());
        txtCostCenter.setText(SERCommen.getDescription("costcenter", txtCostCode.getText(), "CostCode", "Description"));
        txtStoreCode.setText(obj.getStoreCode());
        txtStore.setText(SERCommen.getDescription("store", txtStoreCode.getText(), "StoreCode", "Description"));
        txtArea.setText(obj.getAreaCode());
        txtCurrencyCode.setSelectedItem(obj.getFCCode());
        txtFCRate.setText(obj.getFCRate());
        txtPayTermCode.setText(obj.getPaymentTerms());
        txtDeliDate.setDate(Locals.toDate(obj.getDeliDate()));
        txtPrepBy.setText(obj.getPrepBy());
        txtApproBy.setText(obj.getApproBy());
        txtRemark.setText(obj.getRemarks());
        txtGrossAmount.setText(obj.getGrossAmount());
        txtDiscount.setText(obj.getTotalDiscount());
        txtDiscountRate.setText(obj.getDiscountRate());
        if (obj.getDiscountRate().equals("0.00")) {
            jCheckBox1.setSelected(false);
        } else {
            jCheckBox1.setSelected(!false);

        }
        txtNetAmount2.setText(obj.getNetAmount());
    }

    private void setContentHistory(ArrayList<OBJQuotation> obja) {
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        clrTable();
        int i = 1;
        if (!obja.isEmpty()) {
            for (OBJQuotation objq : obja) {
                dt.addRow(new Object[]{i, objq.getItemCode(), objq.getItemDescription(), objq.getUnitCode(), objq.getQuantity(), objq.getWarranty(), objq.getRate(), objq.getDiscount(), objq.getNet(), objq.getSn()});
                i++;
            }
        }
        jTable1.setEnabled(!true);
    }

    private void doFind() {
        new GUIFindWindow(null, true, "QuotationHeader", "QuotationNo", "CustCode").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        serch(code);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void serch(String code) {
        obj = SERQuotation.serch(code);
        obja = SERQuotation.QuotHistory(obj.getQuotNo());
        setContent(obj);
        setContentHistory(obja);
    }

    /**
    * sssss aaaaa
    **/
    private void setID() {
        txtQuotNo.setText(SERQuotation.getID());
    }

    private void getArea() {
        new GUIFindWindow(null, true, "Area", "AreaCode", "Description").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        //  txtAreaCode.setText(code);
        //   txtArea.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void doDeleteRow() {
        int selRow = jTable1.getSelectedRow();
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        dt.removeRow(selRow);
        int i = jTable1.getRowCount();
        if (i == 0) {
            addNewRow();
        }
    }

    private void getCustomer() {
        new GUIFindWindow(null, true, "Customer", "CustCode", "CustName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtCusCode.setText(code);
        txtCusName.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";
    }

    private void getSalesRep() {
        new GUIFindWindow(null, true, "SalesRep", "RepCode", "RepName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtCostCode.setText(code);
        txtCostCenter.setText(name);
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

    private void loadCurrency() {
        Vector v = new Vector();
        v = SERQuotation.loadCurrency();
        txtCurrencyCode.setModel(new DefaultComboBoxModel(v));
        loadFCRate();
    }

    private void loadFCRate() {
        double d = 0.00;
        d = SERQuotation.getFCRate(txtCurrencyCode.getSelectedItem().toString());
        txtFCRate.setText(core.Locals.currencyFormat(d));
    }

    private void loadTable() {
        int i = jTable1.getSelectedRow();
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        String itc = df.getValueAt(i, 1).toString();
        boolean b = itc != null && jTable1.getSelectedColumn() == 1;
        if (b) {

            obj = SERQuotation.getTablInfo(itc, txtStoreCode.getText());
            df.setValueAt(obj.getName(), i, 2);
            df.setValueAt(obj.getUnit(), i, 3);
            df.setValueAt("0", i, 4);
            df.setValueAt(obj.getWar(), i, 5);
            df.setValueAt(Locals.currencyFormat(obj.getSellingRate()), i, 6);
            df.setValueAt("0.00", i, 7);
            df.setValueAt("0.00", i, 8);

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
        double d = 0.00;
        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            d += Double.parseDouble(df.getValueAt(i, 7).toString());
            d1 += Double.parseDouble(df.getValueAt(i, 8).toString());
        }
        txtGrossAmount.setText(Locals.currencyFormat(d1));
        txtDiscount.setText(Locals.currencyFormat(d));
        txtNetAmount.setText(Locals.currencyFormat(d1 - d));
        double pay = Double.parseDouble(txtPayment.getText());
        txtBalance.setText(Locals.currencyFormat(d1 - d - pay));
    }

    private void setDisc(boolean b) {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            df.setValueAt("0.00", i, 7);
        }
        calcTotDisc();
    }

    private void calcTotDisc() {
        double d = Double.parseDouble(txtGrossAmount.getText());
        double d1 = Double.parseDouble(txtDiscountRate.getText());
        d1 = (d * d1) / 100;
        txtDiscount.setText(Locals.currencyFormat(d1));
        txtNetAmount.setText(Locals.currencyFormat(d - d1));

    }

    private void setItem() {
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        int i = jTable1.getSelectedRow();
        String itc = df.getValueAt(i, 1).toString();
        String itd = df.getValueAt(i, 2).toString();
        txtSelectedItem.setText(itc);
        txtSelectedItemName.setText(itd);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initOthers() {
        setMode(DEFAULT_STATUS);
        setID();
        loadCurrency();

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
        cmdCustomer.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCustomerActionPerformed(evt);
            }
        });
        cmdRep.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRepActionPerformed(evt);
            }
        });
        cmdCostCenter.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCostCenterActionPerformed(evt);
            }
        });
        cmdStore.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStoreActionPerformed(evt);
            }
        });

        cmdReport.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCustomerActionPerformed(evt);
            }
        });

        cmdPayTerm.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPayTermActionPerformed(evt);
            }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbUnit = new javax.swing.JComboBox();
        jComboBox1 = new javax.swing.JComboBox();
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
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtQuotNo = new javax.swing.JTextField();
        txtArea = new javax.swing.JTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel10 = new javax.swing.JLabel();
        jLayeredPane7 = new javax.swing.JLayeredPane();
        cmdPayTerm = new javax.swing.JButton();
        txtPayTermCode = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPrepBy = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtApproBy = new javax.swing.JTextField();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        cmdCusAddress = new javax.swing.JButton();
        txtCusName = new javax.swing.JTextField();
        txtCusCode = new javax.swing.JTextField();
        cmdCustomer = new javax.swing.JButton();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        cmdRep = new javax.swing.JButton();
        txtRep = new javax.swing.JTextField();
        txtRepCode = new javax.swing.JTextField();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        cmdCostCenter = new javax.swing.JButton();
        txtCostCenter = new javax.swing.JTextField();
        txtCostCode = new javax.swing.JTextField();
        txtQoutDate = new com.toedter.calendar.JDateChooser();
        txtFCRate = new javax.swing.JFormattedTextField();
        txtCurrencyCode = new javax.swing.JComboBox();
        jLayeredPane8 = new javax.swing.JLayeredPane();
        jCheckBox1 = new javax.swing.JCheckBox();
        txtDiscountRate = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        txtGrossAmount = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        txtNetAmount2 = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        txtNetAmount = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        txtPayment = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JFormattedTextField();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        cmdStore = new javax.swing.JButton();
        txtStore = new javax.swing.JTextField();
        txtStoreCode = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtRemark = new javax.swing.JTextField();
        txtDeliDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLayeredPane9 = new javax.swing.JLayeredPane();
        jLabel13 = new javax.swing.JLabel();
        txtSelectedItem = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSelectedItemName = new javax.swing.JLabel();

        cbUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setClosable(true);
        setTitle("Sales Order");

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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("FC.Rate");
        jLabel9.setEnabled(false);

        jLabel1.setText("Quot. No.");

        jLabel2.setText("Quot. Date");

        jLabel4.setText("Customer");

        jLabel5.setText("Rep. Code");

        jLabel6.setText("Area");

        jLabel7.setText("Cost Center");

        jLabel8.setText("Currency");
        jLabel8.setEnabled(false);

        txtQuotNo.setEditable(false);
        txtQuotNo.setBackground(new java.awt.Color(255, 255, 222));
        txtQuotNo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtQuotNo.setForeground(new java.awt.Color(198, 0, 0));
        txtQuotNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtArea.setEditable(false);
        txtArea.setBackground(new java.awt.Color(255, 255, 222));
        txtArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel10.setText("Pay Term");
        jLayeredPane1.add(jLabel10);
        jLabel10.setBounds(10, 0, 45, 19);

        cmdPayTerm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdPayTerm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdPayTerm.setContentAreaFilled(false);
        jLayeredPane7.add(cmdPayTerm);
        cmdPayTerm.setBounds(120, 0, 20, 20);

        txtPayTermCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPayTermCode.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane7.add(txtPayTermCode);
        txtPayTermCode.setBounds(0, 0, 120, 18);

        jLayeredPane1.add(jLayeredPane7);
        jLayeredPane7.setBounds(90, 0, 140, 22);

        jLabel11.setText("Prepared By");
        jLayeredPane1.add(jLabel11);
        jLabel11.setBounds(10, 20, 59, 14);

        txtPrepBy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPrepBy.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane1.add(txtPrepBy);
        txtPrepBy.setBounds(90, 20, 199, 18);

        jLabel12.setText("Approved By");
        jLayeredPane1.add(jLabel12);
        jLabel12.setBounds(10, 40, 62, 14);

        txtApproBy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtApproBy.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane1.add(txtApproBy);
        txtApproBy.setBounds(90, 40, 199, 18);

        cmdCusAddress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/font.png"))); // NOI18N
        cmdCusAddress.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdCusAddress.setContentAreaFilled(false);
        jLayeredPane3.add(cmdCusAddress);
        cmdCusAddress.setBounds(370, 0, 20, 20);

        txtCusName.setEditable(false);
        txtCusName.setBackground(new java.awt.Color(255, 255, 241));
        txtCusName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane3.add(txtCusName);
        txtCusName.setBounds(115, 0, 250, 18);

        txtCusCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCusCode.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtCusCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusCodeActionPerformed(evt);
            }
        });
        txtCusCode.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtCusCodeInputMethodTextChanged(evt);
            }
        });
        txtCusCode.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCusCodePropertyChange(evt);
            }
        });
        jLayeredPane3.add(txtCusCode);
        txtCusCode.setBounds(0, 0, 90, 18);

        cmdCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdCustomer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdCustomer.setContentAreaFilled(false);
        jLayeredPane3.add(cmdCustomer);
        cmdCustomer.setBounds(93, 0, 20, 20);

        cmdRep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdRep.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdRep.setContentAreaFilled(false);
        jLayeredPane4.add(cmdRep);
        cmdRep.setBounds(73, 0, 20, 20);

        txtRep.setEditable(false);
        txtRep.setBackground(new java.awt.Color(255, 255, 241));
        txtRep.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane4.add(txtRep);
        txtRep.setBounds(95, 0, 230, 18);

        txtRepCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtRepCode.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane4.add(txtRepCode);
        txtRepCode.setBounds(0, 0, 70, 18);

        cmdCostCenter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdCostCenter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdCostCenter.setContentAreaFilled(false);
        jLayeredPane5.add(cmdCostCenter);
        cmdCostCenter.setBounds(73, 0, 20, 20);

        txtCostCenter.setBackground(new java.awt.Color(255, 255, 241));
        txtCostCenter.setEditable(false);
        txtCostCenter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane5.add(txtCostCenter);
        txtCostCenter.setBounds(95, 0, 230, 18);

        txtCostCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCostCode.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane5.add(txtCostCode);
        txtCostCode.setBounds(0, 0, 70, 18);

        txtQoutDate.setDateFormatString("yyyy-MM-dd");

        txtFCRate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtFCRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtFCRate.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtFCRate.setEnabled(false);

        txtCurrencyCode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtCurrencyCode.setEnabled(false);
        txtCurrencyCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrencyCodeActionPerformed(evt);
            }
        });

        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jLayeredPane8.add(jCheckBox1);
        jCheckBox1.setBounds(50, 20, 20, 21);

        txtDiscountRate.setEditable(false);
        txtDiscountRate.setBackground(new java.awt.Color(249, 249, 229));
        txtDiscountRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtDiscountRate.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtDiscountRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountRateActionPerformed(evt);
            }
        });
        jLayeredPane8.add(txtDiscountRate);
        txtDiscountRate.setBounds(80, 20, 50, 20);

        jLabel17.setBackground(new java.awt.Color(102, 102, 102));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Gross Amount");
        jLabel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel17.setOpaque(true);
        jLayeredPane8.add(jLabel17);
        jLabel17.setBounds(0, 0, 150, 20);

        txtGrossAmount.setEditable(false);
        txtGrossAmount.setBackground(new java.awt.Color(249, 249, 229));
        txtGrossAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtGrossAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtGrossAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtGrossAmount.setText("500.00");
        txtGrossAmount.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane8.add(txtGrossAmount);
        txtGrossAmount.setBounds(150, 0, 90, 20);

        jLabel18.setBackground(new java.awt.Color(102, 102, 102));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Discont                     %");
        jLabel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel18.setOpaque(true);
        jLayeredPane8.add(jLabel18);
        jLabel18.setBounds(0, 20, 150, 20);

        txtDiscount.setEditable(false);
        txtDiscount.setBackground(new java.awt.Color(249, 249, 229));
        txtDiscount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDiscount.setForeground(new java.awt.Color(153, 0, 0));
        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscount.setText("0.00");
        txtDiscount.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane8.add(txtDiscount);
        txtDiscount.setBounds(150, 20, 90, 20);

        jLabel19.setBackground(new java.awt.Color(102, 102, 102));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel19.setOpaque(true);
        jLayeredPane8.add(jLabel19);
        jLabel19.setBounds(0, 40, 150, 20);

        txtNetAmount2.setEditable(false);
        txtNetAmount2.setBackground(new java.awt.Color(249, 249, 229));
        txtNetAmount2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNetAmount2.setForeground(new java.awt.Color(153, 0, 0));
        txtNetAmount2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNetAmount2.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane8.add(txtNetAmount2);
        txtNetAmount2.setBounds(150, 40, 90, 20);

        jLabel22.setBackground(new java.awt.Color(102, 102, 102));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Net Amount");
        jLabel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel22.setOpaque(true);
        jLayeredPane8.add(jLabel22);
        jLabel22.setBounds(260, 0, 130, 20);

        txtNetAmount.setEditable(false);
        txtNetAmount.setBackground(new java.awt.Color(249, 249, 229));
        txtNetAmount.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNetAmount.setForeground(new java.awt.Color(153, 0, 0));
        txtNetAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNetAmount.setText("500.00");
        txtNetAmount.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane8.add(txtNetAmount);
        txtNetAmount.setBounds(390, 0, 90, 20);

        jLabel23.setBackground(new java.awt.Color(102, 102, 102));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Payment");
        jLabel23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel23.setOpaque(true);
        jLayeredPane8.add(jLabel23);
        jLabel23.setBounds(260, 20, 130, 20);

        txtPayment.setBackground(new java.awt.Color(249, 249, 229));
        txtPayment.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPayment.setForeground(new java.awt.Color(153, 0, 0));
        txtPayment.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPayment.setText("0.00");
        txtPayment.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtPayment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaymentKeyReleased(evt);
            }
        });
        jLayeredPane8.add(txtPayment);
        txtPayment.setBounds(390, 20, 90, 20);

        jLabel24.setBackground(new java.awt.Color(102, 102, 102));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Balance");
        jLabel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel24.setOpaque(true);
        jLayeredPane8.add(jLabel24);
        jLabel24.setBounds(260, 40, 130, 20);

        txtBalance.setEditable(false);
        txtBalance.setBackground(new java.awt.Color(249, 249, 229));
        txtBalance.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtBalance.setForeground(new java.awt.Color(153, 0, 0));
        txtBalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtBalance.setText("0.00");
        txtBalance.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane8.add(txtBalance);
        txtBalance.setBounds(390, 40, 90, 20);

        cmdStore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdStore.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdStore.setContentAreaFilled(false);
        jLayeredPane6.add(cmdStore);
        cmdStore.setBounds(73, 0, 20, 20);

        txtStore.setEditable(false);
        txtStore.setBackground(new java.awt.Color(255, 255, 241));
        txtStore.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane6.add(txtStore);
        txtStore.setBounds(95, 0, 210, 18);

        txtStoreCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtStoreCode.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane6.add(txtStoreCode);
        txtStoreCode.setBounds(0, 0, 70, 18);

        jLabel20.setText("Store");

        jLabel21.setText("Remarks");

        txtRemark.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtRemark.setDisabledTextColor(new java.awt.Color(153, 0, 0));

        txtDeliDate.setDateFormatString("yyyy-MM-dd");

        jLabel3.setText("Delivery Date");

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(153, 51, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "h", "e", "h", null, "12", null, null, null, "6h"}
            },
            new String [] {
                "", "Item Code", "Descrip", "Unit", "Qty.", "Warranty", "Rate", "Discount", "Net", "S/N"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setRowHeight(25);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
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
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(cbUnit));
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(45);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(8).setResizable(false);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(65);
            jTable1.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtQuotNo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDeliDate, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtQoutDate, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtFCRate, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLayeredPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                                    .addComponent(jLayeredPane5, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtCurrencyCode, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jLayeredPane6)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtRemark))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLayeredPane8)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtQuotNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQoutDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDeliDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtCurrencyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtFCRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(jLayeredPane5)
                    .addComponent(jLayeredPane6)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane8)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRemark, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ItemCode");
        jLabel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(jLabel13);
        jLabel13.setBounds(0, 0, 130, 20);

        txtSelectedItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItem.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItem.setText("jLabel14");
        txtSelectedItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(txtSelectedItem);
        txtSelectedItem.setBounds(130, 0, 170, 20);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Description");
        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(jLabel15);
        jLabel15.setBounds(310, 0, 140, 20);

        txtSelectedItemName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSelectedItemName.setForeground(new java.awt.Color(204, 0, 0));
        txtSelectedItemName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedItemName.setText("jLabel16");
        txtSelectedItemName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLayeredPane9.add(txtSelectedItemName);
        txtSelectedItemName.setBounds(450, 0, 350, 20);

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
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if (evt.getKeyCode() == 127 && jTable1.getSelectedRow() >= 0) {
            doDeleteRow();
        } else if (evt.getKeyCode() == evt.VK_F3) {
            addNewRow();
        } else if (evt.getKeyCode() == evt.VK_ENTER) {
            loadTable();
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void txtCusCodePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCusCodePropertyChange
    }//GEN-LAST:event_txtCusCodePropertyChange

    private void txtCurrencyCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrencyCodeActionPerformed
        loadFCRate();
    }//GEN-LAST:event_txtCurrencyCodeActionPerformed

    private void txtCusCodeInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCusCodeInputMethodTextChanged
    }//GEN-LAST:event_txtCusCodeInputMethodTextChanged

    private void txtCusCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusCodeActionPerformed
    }//GEN-LAST:event_txtCusCodeActionPerformed

    private void txtDiscountRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountRateActionPerformed
        calcTotDisc();
    }//GEN-LAST:event_txtDiscountRateActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        chBoxAct();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        setItem();
    }//GEN-LAST:event_jTable1MouseClicked

    private void txtPaymentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaymentKeyReleased
        if (txtPayment.getText() == null) {
            txtPayment.setText("0.00");
        }
        doCalAll();
    }//GEN-LAST:event_txtPaymentKeyReleased

    private void cmdCustomerActionPerformed(java.awt.event.ActionEvent evt) {
        getCustomer();
    }

    private void cmdRepActionPerformed(java.awt.event.ActionEvent evt) {
        getSalesRep();
    }

    private void cmdCostCenterActionPerformed(java.awt.event.ActionEvent evt) {
        getCost();
    }

    private void cmdStoreActionPerformed(java.awt.event.ActionEvent evt) {
        getStore();
    }

    private void cmdPayTermActionPerformed(java.awt.event.ActionEvent evt) {
        getPayTerm();
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
        indexCount = SERQuotation.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index = indexCount - 1;
        }
    }

    private void cmdLastActionPerformed(java.awt.event.ActionEvent evt) {
        Index = SERQuotation.getIndex() - 1;
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
    private javax.swing.JButton cmdCostCenter;
    private javax.swing.JButton cmdCusAddress;
    private javax.swing.JButton cmdCustomer;
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
    private javax.swing.JButton cmdRep;
    private javax.swing.JButton cmdReport;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdStore;
    private javax.swing.JButton cmdView;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JLayeredPane jLayeredPane7;
    private javax.swing.JLayeredPane jLayeredPane8;
    private javax.swing.JLayeredPane jLayeredPane9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtApproBy;
    private javax.swing.JTextField txtArea;
    private javax.swing.JFormattedTextField txtBalance;
    private javax.swing.JTextField txtCostCenter;
    private javax.swing.JTextField txtCostCode;
    private javax.swing.JComboBox txtCurrencyCode;
    private javax.swing.JTextField txtCusCode;
    private javax.swing.JTextField txtCusName;
    private com.toedter.calendar.JDateChooser txtDeliDate;
    private javax.swing.JFormattedTextField txtDiscount;
    private javax.swing.JFormattedTextField txtDiscountRate;
    private javax.swing.JFormattedTextField txtFCRate;
    private javax.swing.JFormattedTextField txtGrossAmount;
    private javax.swing.JFormattedTextField txtNetAmount;
    private javax.swing.JFormattedTextField txtNetAmount2;
    private javax.swing.JTextField txtPayTermCode;
    private javax.swing.JFormattedTextField txtPayment;
    private javax.swing.JTextField txtPrepBy;
    private com.toedter.calendar.JDateChooser txtQoutDate;
    private javax.swing.JTextField txtQuotNo;
    private javax.swing.JTextField txtRemark;
    private javax.swing.JTextField txtRep;
    private javax.swing.JTextField txtRepCode;
    private javax.swing.JLabel txtSelectedItem;
    private javax.swing.JLabel txtSelectedItemName;
    private javax.swing.JTextField txtStore;
    private javax.swing.JTextField txtStoreCode;
    // End of variables declaration//GEN-END:variables
    private OBJQuotation obj;
    private ArrayList<OBJQuotation> obja;
    //status
    private static final int DEFAULT_STATUS = 0;
    private static final int NEW_STATUS = 1;
    private int Act = 1;
    private int Index = 0;
    private int indexCount = 0;
}
