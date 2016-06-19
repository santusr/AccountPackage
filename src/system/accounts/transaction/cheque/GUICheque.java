/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.cheque;

import core.Locals;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import mainApp.SERCommen;
import mainApp.findwindow.GUIFindWindow;
import core.system_transaction.payment.multy_payment.MultiPayOption;

/**
 *
 * @author Rabid
 */
public class GUICheque extends javax.swing.JDialog {

    /**
     * Creates new form GUICheque
     * @param parent
     */
    public GUICheque(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initOthers();
    }

    public GUICheque(Object object, boolean b, int i, String RefNo, String Acc) {
        this(null, b);
        cont = i;
        if (i == 1) {
            jComboBox1.setSelectedIndex(0);
        } else if (i == 2) {
            jComboBox1.setSelectedIndex(1);
        }
        txtInvoNo.setText(RefNo);

        txtAccCode1.setText(Acc);
        txtAccName1.setText(SERCommen.getDescription("Account", txtAccCode1.getText(), "AccCode", "AccName"));

        txtInvoNo.setEnabled(false);
        jComboBox1.setEnabled(false);
        cmdPrev.setEnabled(false);
    }

    public GUICheque(Object object, boolean b, int i, String cno) {
        this(null, b);
        cont = i;

        if (i == 1) {
            jComboBox1.setSelectedIndex(0);
        } else if (i == 2) {
            jComboBox1.setSelectedIndex(1);
            txtBankCharge.setEnabled(false);
            txtIntrestCharge.setEnabled(false);
            txtNet.setEnabled(false);
        }

        obj = SERCheque.serchCNo(cno);
        setContent(obj);
        setMode(NEW_STATUS);

    }

    private void doSave() {

        String code = txtCode.getText();
        String Cust = txtAccCode1.getText();
        String invo = txtInvoNo.getText();
        String Cno = txtChequeNo.getText();
        String GDate = Locals.setDateFormat(txtGetDate.getDate());
        String RDate = Locals.setDateFormat(txtRealizedate.getDate());
        String Amo = txtAmount.getText();
        String Bank = txtBankCode.getText();
        String FCurrency;

        FCurrency = txtCurrency.getSelectedItem().toString();
        FCurrency = FCurrency.substring(1, 4);

        String ChqeType = "R";
        if (jComboBox1.getSelectedIndex() == 1) {
            ChqeType = "V";
        }
        if (code.isEmpty() || Cust.isEmpty() || invo.isEmpty() || Cno.isEmpty() || GDate.isEmpty() || RDate.isEmpty() || Amo.isEmpty() || Bank.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Blank entry not allowed", "Warning !", JOptionPane.OK_OPTION);
        } else {
            String s = "";
            if (Act == 1) {
                s = "Save";
            } else {
                s = "Update";
            }
            int q = JOptionPane.showConfirmDialog(null, "Are you sure want to " + s + " ?", s + " Cheque", JOptionPane.YES_NO_OPTION);
            if (q == JOptionPane.YES_OPTION) {
                String Status = ChequeStatus.PENDING;
                if (chkRealized.isSelected()) {
                    Status = ChequeStatus.REALIZE;
                } else if (chkBounced.isSelected()) {
                    Status = ChequeStatus.BOUNCED;
                }
                obj.setBankAccount(txtAccCode2.getText());
                obj.setBankCharge(txtBankCharge.getText());
                obj.setInterestCharge(txtIntrestCharge.getText());
                obj.setNet(txtNet.getText());
                obj.setBank(txtBankCode.getText());
                obj.setStatus(Status);

                accountpackage.AccountPackage.CODE = txtChequeNo.getText();
                //save part is include in parant save position
                if(Act == 1){
                    MultiPayOption.cheque = obj;
                } else {
                SERCheque.save(obj, Act);
                }
                if (cont != 0) {
                    this.dispose();
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
        txtAccCode1.setText("");
        txtAccName1.setText("");
        txtAccCode2.setText("");
        txtAccName2.setText("");
        txtInvoNo.setText("");
        txtChequeNo.setText("");
        txtAmount.setText("0.00");
        txtBankCharge.setText("0.00");
        txtIntrestCharge.setText("0.00");
        txtNet.setText("0.00");
        txtBankCode.setText("");
        txtBank.setText("");
        if (txtCurrency.getSelectedIndex() != -1) {
            txtCurrency.setSelectedIndex(0);
        }
        txtRemarks.setText("");
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
        SERCheque.delete(code);
        indexCount = SERCheque.getIndex();
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
       // txtAccCode1.setEnabled(state);
        txtAccCode2.setEnabled(state);
        //txtAccName1.setEnabled(state);
       // txtInvoNo.setEnabled(state);
       // txtChequeNo.setEnabled(state);
       // txtAmount.setEnabled(state);
        txtBankCharge.setEnabled(state);
        txtIntrestCharge.setEnabled(state);
       // txtNet.setEnabled(state);
        txtBankCode.setEnabled(state);
       // txtBank.setEnabled(state);
        txtRemarks.setEnabled(state);
       // txtRealizedate.setEnabled(state);
       // txtGetDate.setEnabled(state);

        //txtCurrency.setEnabled(state);
        //txtCurrencyRate.setEnabled(state);

        jComboBox1.setEnabled(state);

        cmdBank.setEnabled(state);
        //cmdAcc1.setEnabled(state);
        cmdAcc2.setEnabled(state);
    }

    private void getNavi() {
        obj = SERCheque.getNavi(Index);
        setContent(obj);
        /*
         * txtCode.setText(obj.getCode()); txtPrintName.setText(obj.getName());
         * txtName.setText(obj.getPrintName()); txtId.setText(obj.getId());
         * txtCont1.setText(obj.getContact1());
         * txtCont2.setText(obj.getContact2());
         * txtmobi1.setText(obj.getMobi1()); txtmobi2.setText(obj.getMobi2());
         * txtmobi3.setText(obj.getMobi3()); txtPobox.setText(obj.getPbox());
         * txtAddress1.setText(obj.getAdd1());
         * txtAddress2.setText(obj.getAdd2());
         * txtAddress3.setText(obj.getAdd3());
         * txtTelOff.setText(obj.getTeloff());
         * txtTelRes.setText(obj.getTelres()); txtFax.setText(obj.getFax());
         * txtEmail.setText(obj.getEmail()); txtWeb.setText(obj.getWeb());
         * txtSrep.setSelectedItem(loadSrep(obj.getSrep()));
         * txtAreaCode.setText(obj.getArea());
         * txtArea.setText(SERVendore.setName(obj.getArea(), "Area",
         * "AreaCode")); txtPayCode.setText(obj.getPayterm());
         * txtPayTerm.setText(SERVendore.setName(obj.getPayterm(), "PayTerms",
         * "PTCode"));
         * txtCurrency.setSelectedItem(loadCurrency(obj.getCurrency()));
         * txtBalance.setText(obj.getBalance());
         * txtCreditLimit.setText(obj.getCreditlimit());
         txtCreditdays.setText(obj.getCreditdays());
         */
    }

    private void doFind() {
        new GUIFindWindow(null, true, "Cheque", "CheqCode", "CustNo").setVisible(true);
        if (!accountpackage.AccountPackage.CODE.equals("")) {
            String code = accountpackage.AccountPackage.CODE;
            serch(code);
            accountpackage.AccountPackage.CODE = "";
            accountpackage.AccountPackage.NAME = "";
        }
    }

    private void doCalNet() {
        double d, d1, d2, d3;
        d = Double.parseDouble(txtAmount.getText());
        d1 = Double.parseDouble(txtBankCharge.getText());
        d2 = Double.parseDouble(txtIntrestCharge.getText());

        d3 = d + d1 + d2;
        txtNet.setText(Locals.currencyFormat(d3));
    }

    private void serch(String code) {
        obj = SERCheque.serch(code);
        setContent(obj);
    }

    private void setContent(OBJCheque obj) {
        txtCode.setText(obj.getCode());
        txtAccCode1.setText(obj.getCustCode());
        txtAccName1.setText(SERCommen.getDescription("account", txtAccCode1.getText(), "AccCode", "AccName"));
        txtInvoNo.setText(obj.getInvoNo());
        txtChequeNo.setText(obj.getChqeNo());
        txtAmount.setText(obj.getAmount());
        txtBankCharge.setText(obj.getBankCharge());
        txtIntrestCharge.setText(obj.getInterestCharge());
        txtNet.setText(obj.getNet());
        txtBankCode.setText(obj.getBank());
        txtBank.setText(SERCommen.getDescription("Bank", txtBankCode.getText(), "BankCode", "BankName"));
        txtCurrency.setSelectedItem(obj.getFCCode());
        txtRemarks.setText(obj.getRemarks());
        txtGetDate.setDate(Locals.toDate(obj.getGetDate()));
        txtRealizedate.setDate(Locals.toDate(obj.getRDate()));
        txtAccCode2.setText(obj.getBankAccount());
        txtAccName2.setText(SERCommen.getDescription("account", txtAccCode2.getText(), "AccCode", "AccName"));
        switch (obj.getStatus()) {
            case ChequeStatus.REALIZE:
                chkRealized.setSelected(true);
                break;
            case ChequeStatus.BOUNCED:
                chkBounced.setSelected(true);
                break;
        }
    }

    private void loadCurrency() {
        Vector currency = new Vector();
        currency = SERCheque.getCurrency();
        txtCurrency.removeAllItems();
        txtCurrency.setModel(new DefaultComboBoxModel(currency));
        loadFCRate();
    }

    private String loadCurrency(String code) {
        String currency = SERCheque.getCurrency(code);
        return currency;
    }

    private void setID() {
        txtCode.setText(SERCheque.getID());
    }

    private void getAcc(int i) {

        new GUIFindWindow(null, true, "Account", "AccCode", "AccName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        if (i == 1) {
            txtAccCode1.setText(code);
            txtAccName1.setText(name);
        } else if (i == 2) {
            txtAccCode2.setText(code);
            txtAccName2.setText(name);
        }
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";

    }

    private void getInvo() {

        new GUIFindWindow(null, true, "invoiceheader", "InvoNo", "CustCode").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtInvoNo.setText(code);
        //txtCusName.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";

    }

    private void getBank() {

        new GUIFindWindow(null, true, "Bank", "BankCode", "BankName").setVisible(true);
        String code = accountpackage.AccountPackage.CODE;
        String name = accountpackage.AccountPackage.NAME;
        txtBankCode.setText(code);
        txtBank.setText(name);
        accountpackage.AccountPackage.CODE = "";
        accountpackage.AccountPackage.NAME = "";

    }

    private void loadFCRate() {
        double d = 0.00;
        d = SERCheque.getFCRate(txtCurrency.getSelectedItem().toString());
        txtCurrencyRate.setText(core.Locals.currencyFormat(d));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initOthers() {
        setMode(DEFAULT_STATUS);
        setID();
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
        acc1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        cmdBank = new javax.swing.JButton();
        txtBank = new javax.swing.JTextField();
        txtBankCode = new javax.swing.JTextField();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        cmdAcc1 = new javax.swing.JButton();
        txtAccName1 = new javax.swing.JTextField();
        txtAccCode1 = new javax.swing.JTextField();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        txtInvoNo = new javax.swing.JTextField();
        txtChequeNo = new javax.swing.JTextField();
        txtGetDate = new com.toedter.calendar.JDateChooser();
        txtRealizedate = new com.toedter.calendar.JDateChooser();
        txtAmount = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        txtCurrency = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCurrencyRate = new javax.swing.JFormattedTextField();
        txtRemarks = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        txtBankCharge = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtIntrestCharge = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        txtNet = new javax.swing.JFormattedTextField();
        acc2 = new javax.swing.JLabel();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        cmdAcc2 = new javax.swing.JButton();
        txtAccName2 = new javax.swing.JTextField();
        txtAccCode2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        chkRealized = new javax.swing.JCheckBox();
        chkBounced = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cheque Registry");

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

        acc1.setText("Customer");

        jLabel2.setText("Ref. No");

        jLabel3.setText("Cheque No");

        jLabel4.setText("Get Date");

        jLabel5.setText("Realize Date");

        jLabel6.setText("Amount");

        jLabel7.setText("Bank");

        cmdBank.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdBank.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdBank.setContentAreaFilled(false);
        cmdBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBankActionPerformed(evt);
            }
        });
        jLayeredPane3.add(cmdBank);
        cmdBank.setBounds(70, 0, 21, 20);

        txtBank.setBackground(new java.awt.Color(255, 255, 241));
        txtBank.setEditable(false);
        jLayeredPane3.add(txtBank);
        txtBank.setBounds(95, 0, 140, 20);

        txtBankCode.setBackground(new java.awt.Color(255, 255, 241));
        txtBankCode.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane3.add(txtBankCode);
        txtBankCode.setBounds(0, 0, 70, 20);

        cmdAcc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdAcc1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdAcc1.setContentAreaFilled(false);
        cmdAcc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAcc1ActionPerformed(evt);
            }
        });
        jLayeredPane4.add(cmdAcc1);
        cmdAcc1.setBounds(80, 0, 21, 20);

        txtAccName1.setBackground(new java.awt.Color(255, 255, 241));
        txtAccName1.setEditable(false);
        jLayeredPane4.add(txtAccName1);
        txtAccName1.setBounds(105, 0, 270, 20);

        txtAccCode1.setBackground(new java.awt.Color(255, 255, 241));
        txtAccCode1.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane4.add(txtAccCode1);
        txtAccCode1.setBounds(0, 0, 80, 20);

        txtInvoNo.setBackground(new java.awt.Color(255, 255, 241));
        txtInvoNo.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane5.add(txtInvoNo);
        txtInvoNo.setBounds(0, 0, 110, 20);

        txtChequeNo.setBackground(new java.awt.Color(255, 255, 241));
        txtChequeNo.setDisabledTextColor(new java.awt.Color(153, 0, 0));

        txtGetDate.setBackground(new java.awt.Color(255, 255, 241));
        txtGetDate.setDateFormatString("yyyy-MM-dd");

        txtRealizedate.setBackground(new java.awt.Color(255, 255, 241));
        txtRealizedate.setDateFormatString("yyyy-MM-dd");

        txtAmount.setBackground(new java.awt.Color(255, 255, 241));
        txtAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAmount.setText("0.00");
        txtAmount.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAmountKeyReleased(evt);
            }
        });

        jLabel8.setText("Code");

        txtCode.setEditable(false);
        txtCode.setBackground(new java.awt.Color(253, 246, 246));
        txtCode.setDisabledTextColor(new java.awt.Color(102, 0, 0));

        txtCurrency.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtCurrency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrencyActionPerformed(evt);
            }
        });

        jLabel9.setText("FC Code");

        jLabel10.setText("FC Rate");

        txtCurrencyRate.setBackground(new java.awt.Color(255, 255, 241));
        txtCurrencyRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCurrencyRate.setDisabledTextColor(new java.awt.Color(153, 0, 0));

        txtRemarks.setBackground(new java.awt.Color(255, 255, 241));
        txtRemarks.setDisabledTextColor(new java.awt.Color(153, 0, 0));

        jLabel11.setText("Remarks");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Recipt Cheque", "Voucher Cheque" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel12.setText("Bank Charge");

        txtBankCharge.setBackground(new java.awt.Color(255, 255, 241));
        txtBankCharge.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtBankCharge.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtBankCharge.setText("0.00");
        txtBankCharge.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtBankCharge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBankChargeKeyReleased(evt);
            }
        });

        jLabel13.setText("Intrest Charge");

        txtIntrestCharge.setBackground(new java.awt.Color(255, 255, 241));
        txtIntrestCharge.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIntrestCharge.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIntrestCharge.setText("0.00");
        txtIntrestCharge.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtIntrestCharge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIntrestChargeKeyReleased(evt);
            }
        });

        jLabel14.setText("Net Amount");

        txtNet.setEditable(false);
        txtNet.setBackground(new java.awt.Color(255, 255, 241));
        txtNet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtNet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNet.setText("0.00");
        txtNet.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        txtNet.setEnabled(false);
        txtNet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNetKeyReleased(evt);
            }
        });

        acc2.setText("Bank Account");

        cmdAcc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool bar/help.png"))); // NOI18N
        cmdAcc2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdAcc2.setContentAreaFilled(false);
        cmdAcc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAcc2ActionPerformed(evt);
            }
        });
        jLayeredPane6.add(cmdAcc2);
        cmdAcc2.setBounds(80, 0, 21, 20);

        txtAccName2.setBackground(new java.awt.Color(255, 255, 241));
        txtAccName2.setEditable(false);
        jLayeredPane6.add(txtAccName2);
        txtAccName2.setBounds(105, 0, 270, 20);

        txtAccCode2.setBackground(new java.awt.Color(255, 255, 241));
        txtAccCode2.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jLayeredPane6.add(txtAccCode2);
        txtAccCode2.setBounds(0, 0, 80, 20);

        buttonGroup1.add(chkRealized);
        chkRealized.setText("Realised");

        buttonGroup1.add(chkBounced);
        chkBounced.setText("Bounced");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkRealized)
                    .addComponent(chkBounced))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkRealized)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkBounced)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(acc1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(acc2)
                        .addGap(13, 13, 13)
                        .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBankCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtChequeNo, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtGetDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
                                        .addGap(25, 25, 25)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(53, 53, 53)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNet, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(txtIntrestCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRealizedate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCurrency, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCurrencyRate, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(acc1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(acc2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtChequeNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCurrencyRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGetDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtRealizedate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtBankCharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtIntrestCharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtNet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLayeredPane3)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtRemarks)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(493, 422));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdAcc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAcc1ActionPerformed
        getAcc(1);
    }//GEN-LAST:event_cmdAcc1ActionPerformed

    private void txtCurrencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrencyActionPerformed
        if (txtCurrency.getItemCount() > 0) {
            loadFCRate();
        }
    }//GEN-LAST:event_txtCurrencyActionPerformed

    private void cmdAcc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAcc2ActionPerformed
        getAcc(2);
    }//GEN-LAST:event_cmdAcc2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getSelectedIndex() == 0) {
            acc2.setText("Credit A/C");
            acc1.setText("Debit A/C");
        } else if (jComboBox1.getSelectedIndex() == 1) {
            acc2.setText("Debit A/C");
            acc1.setText("Credit A/C");
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void cmdBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBankActionPerformed
        getBank();
    }//GEN-LAST:event_cmdBankActionPerformed

    private void txtAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountKeyReleased
        doCalNet();
    }//GEN-LAST:event_txtAmountKeyReleased

    private void txtBankChargeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBankChargeKeyReleased
        doCalNet();
    }//GEN-LAST:event_txtBankChargeKeyReleased

    private void txtIntrestChargeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIntrestChargeKeyReleased
        doCalNet();
    }//GEN-LAST:event_txtIntrestChargeKeyReleased

    private void txtNetKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNetKeyReleased
        doCalNet();
    }//GEN-LAST:event_txtNetKeyReleased

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
        indexCount = SERCheque.getIndex();
        if (Index < indexCount) {
            getNavi();
        } else {
            Index = indexCount;
        }
    }

    private void cmdLastActionPerformed(java.awt.event.ActionEvent evt) {
        Index = SERCheque.getIndex() - 1;
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
    private javax.swing.JLabel acc1;
    private javax.swing.JLabel acc2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkBounced;
    private javax.swing.JCheckBox chkRealized;
    private javax.swing.JButton cmdAcc1;
    private javax.swing.JButton cmdAcc2;
    private javax.swing.JButton cmdBank;
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
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtAccCode1;
    private javax.swing.JTextField txtAccCode2;
    private javax.swing.JTextField txtAccName1;
    private javax.swing.JTextField txtAccName2;
    private javax.swing.JFormattedTextField txtAmount;
    private javax.swing.JTextField txtBank;
    private javax.swing.JFormattedTextField txtBankCharge;
    private javax.swing.JTextField txtBankCode;
    private javax.swing.JTextField txtChequeNo;
    private javax.swing.JTextField txtCode;
    private javax.swing.JComboBox txtCurrency;
    private javax.swing.JFormattedTextField txtCurrencyRate;
    private com.toedter.calendar.JDateChooser txtGetDate;
    private javax.swing.JFormattedTextField txtIntrestCharge;
    private javax.swing.JTextField txtInvoNo;
    private javax.swing.JFormattedTextField txtNet;
    private com.toedter.calendar.JDateChooser txtRealizedate;
    private javax.swing.JTextField txtRemarks;
    // End of variables declaration//GEN-END:variables
    private OBJCheque obj;
    //status
    private static final int DEFAULT_STATUS = 0;
    private static final int NEW_STATUS = 1;
    private int Act = 1;
    private int Index = 0;
    private int indexCount = 0;
    private int cont = 0;
}
