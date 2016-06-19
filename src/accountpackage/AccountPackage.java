/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accountpackage;

import core.Exp;
import core.company.Company;
import core.Locals;
import core.system_transaction.account_trans.AccountSettingObject;
import core.system_transaction.payment.PaymentSettingObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import system.login.GUILogin;
import system.login.OBJCostCenter;

/**
 *
 * @author dell
 */
public class AccountPackage {

    public static String user;
    public static String userlevel;
    public static String CODE = "";
    public static String NAME = "";
    public static Connection connect;
    public static Company company = null;
   // public static String UPDATE = "2014/15/17";
    public static OBJCostCenter costCenter;
    public static String costCode;
    public static ArrayList<AccountSettingObject> accountSettingObjects;
    public static ArrayList<PaymentSettingObject> paymentSettingObjects;
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
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//             UIManager.setLookAndFeel(new SubstanceOfficeBlack2007LookAndFeel());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUILogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccountPackage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AccountPackage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AccountPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        //UPDATE = lastUpdate();
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    connect = core.DBConnection.getConnection();
                    company = Locals.getCompany();
                    accountSettingObjects = Locals.getAccountSettingObject();
                    paymentSettingObjects = Locals.getPaymentSettingObject();
                    getCostCenter();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(AccountPackage.class.getName()).log(Level.SEVERE, null, ex);
                }
                //connect = connect();
//                MainFrame dialog = new MainFrame();
                GUILogin dialog = new GUILogin(null, true);
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

    public static Connection connect() {
        try {
            if (connect == null || connect.isClosed()) {
                connect = core.DBConnection.getConnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
    }
    
    public static String lastUpdate() {
        Properties prop = new Properties();
        String update = "2014-11-07";
        try {
            //load a properties file
            prop.load(new FileInputStream("infor.rap"));

            //get the property value and print it out
            update = prop.getProperty("update");
            
        } catch (IOException ex) {
            Exp.Handle(ex);
        }
        return update;
    }
    
    public static void getCostCenter() {
        Properties prop = new Properties();
        try {
            //load a properties file
            prop.load(new FileInputStream("infor.rap"));

            //get the property value and print it out
            costCode = prop.getProperty("costCenter");
            
        } catch (IOException ex) {
            Exp.Handle(ex);
        }
    }
    
}
