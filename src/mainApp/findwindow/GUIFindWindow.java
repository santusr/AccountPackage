/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIFindWindow.java
 *
 * Created on Jul 23, 2013, 6:31:07 PM
 */
package mainApp.findwindow;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dell
 */
public class GUIFindWindow extends javax.swing.JDialog {

    /**
     * Creates new form GUIFindWindow
     */
    public GUIFindWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initOthers();
    }

    private void createKeybindings(javax.swing.JTable table) {
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        table.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doAct();
            }
        });
    }
    
    public GUIFindWindow(Object object, boolean b, String table, String code, String name) {
        this(null, b);
        TABLE = table;
        this.code = code;
        this.name = name;
        publishTable(table, code, name);
        initOthers();
    }

    public GUIFindWindow(Object object, boolean b, String table1, String table2, String code, String name, String key, String status) {
        this(null, b);
        TABLE = table1;
        TABLEEX = table2;
        this.code = code;
        this.name = name;
        this.key = key;
        this.status = status;
        publishTable(table1, table2, code, name, key, status);
        initOthers();
    }

    public GUIFindWindow(Object object, boolean b, String table, String code, String name, String s) {
        this(null, b);
        TABLE = table;
        this.code = code;
        this.name = name;
        this.s = s;
        publishTable(table, code, name, s);
        initOthers();
    }

    public GUIFindWindow(Object object, boolean b, String table, String code, String name, String s, String ss) {
        this(null, b);
        TABLE = table;
        this.code = code;
        this.name = name;
        publishTable(table, code, name, s, ss);
        initOthers();
    }

    private void doAct() {
        int cou = jTable1.getSelectedRowCount();

        if (cou == 1) {
            int row = jTable1.getSelectedRow();
            accountpackage.AccountPackage.CODE = jTable1.getValueAt(row, 0).toString();
            accountpackage.AccountPackage.NAME = jTable1.getValueAt(row, 1).toString();
            TABLEEX = null;
            this.dispose();
        }
    }

    private void publishTable(String table, String code, String name) {
        ArrayList<OBJFindWindow> Adata = SERFindWindow.getData(table, code, name);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
        // Vector v = new Vector();
        for (OBJFindWindow data : Adata) {
            model.addRow(new Object[]{data.getCode().toString(), data.getName().toString()});
        }
    }

    private void publishTable(String table1, String table2, String code, String name, String key, String status) {
        ArrayList<OBJFindWindow> Adata = SERFindWindow.getData(table1, table2, code, name, key, status);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
        // Vector v = new Vector();
        for (OBJFindWindow data : Adata) {
            model.addRow(new Object[]{data.getCode().toString(), data.getName().toString()});
        }
    }

    private void publishTable(String table, String code, String name, String s) {
        ArrayList<OBJFindWindow> Adata = SERFindWindow.getData(table, code, name, s);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
        // Vector v = new Vector();
        for (OBJFindWindow data : Adata) {
            model.addRow(new Object[]{data.getCode().toString(), data.getName().toString()});
        }
    }

    private void publishTable(String table, String code, String name, String s, String ss) {
        ArrayList<OBJFindWindow> Adata = SERFindWindow.getData(table, code, name, s, ss);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
        // Vector v = new Vector();
        for (OBJFindWindow data : Adata) {
            model.addRow(new Object[]{data.getCode().toString(), data.getName().toString()});
        }
    }

    private void searchLike(String rbt, String txt) {
        ArrayList<OBJFindWindow> Adata = SERFindWindow.getLikeData(TABLE, code, name, rbt, txt);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
        // Vector v = new Vector();
        for (OBJFindWindow data : Adata) {
            model.addRow(new Object[]{data.getCode().toString(), data.getName().toString()});
        }
    }
    
    private void searchLike(String s, String rbt, String txt) {
        ArrayList<OBJFindWindow> Adata = SERFindWindow.getLikeData(TABLE, code, name, s, rbt, txt);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
        // Vector v = new Vector();
        for (OBJFindWindow data : Adata) {
            model.addRow(new Object[]{data.getCode().toString(), data.getName().toString()});
        }
    }

    private void searchLike(String key, String status, String rbt, String txt) {
        ArrayList<OBJFindWindow> Adata = SERFindWindow.getLikeData(TABLE, TABLEEX, code, name, key, status, rbt, txt);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
        // Vector v = new Vector();
        for (OBJFindWindow data : Adata) {
            model.addRow(new Object[]{data.getCode(), data.getName()});
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initOthers() {
        this.setIconImage(new ImageIcon("find.png").getImage());
        createKeybindings(jTable1);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        rbtCode = new javax.swing.JRadioButton();
        rbtName = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        txtParam = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Find Window..");
        setAlwaysOnTop(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 225, 196));
        jTable1.setOpaque(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(25);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Search For What ?", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 102)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup1.add(rbtCode);
        rbtCode.setText("Code");
        jPanel3.add(rbtCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 78, -1));

        buttonGroup1.add(rbtName);
        rbtName.setText("Name (Description)");
        jPanel3.add(rbtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/find.png"))); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, 40));

        txtParam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtParamKeyReleased(evt);
            }
        });
        jPanel3.add(txtParam, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 270, 20));

        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 90, -1));

        jButton2.setText("Accept");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 90, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(new java.awt.Dimension(415, 522));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    doAct();
}//GEN-LAST:event_jButton2ActionPerformed

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    int count = evt.getClickCount();
    if (count == 2) {
        doAct();
    }
}//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtParamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtParamKeyReleased
        String key = "code";
        if (rbtName.isSelected()) {
            key = name;
        } else {
            key = code;
        }
        if (TABLEEX == null && s == null && ss == null) {
            searchLike(key, txtParam.getText());
        } else if(s == null && ss == null){
            searchLike(this.key, this.status, key, txtParam.getText());
        } else if(TABLEEX == null && ss == null){
            searchLike(s, key, txtParam.getText());        
        } else if (TABLEEX == null && ss == null){
            String y = s+"='"+ss+"' AND ";
            searchLike((y+key), txtParam.getText());        
        }
    }//GEN-LAST:event_txtParamKeyReleased
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton rbtCode;
    private javax.swing.JRadioButton rbtName;
    private javax.swing.JTextField txtParam;
    // End of variables declaration//GEN-END:variables
    private static String TABLE;
    private static String TABLEEX = null;
    private static String code;
    private static String name;
    private static String key = null;
    private static String status = null;
    private static String s = null;
    private static String ss = null;

}
