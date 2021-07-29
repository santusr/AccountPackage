/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.itemclassification.itemmaster;

import core.DBConnection;
import core.Exp;
import core.system_transaction.TRSAccount;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERItemMaster {

    public static boolean save(
            OBJItemMaster obj,
            OBJItemMaster objstk,
            OBJTransaction transaction,
            OBJPayment payment,
            ArrayList<OBJPaymentInfo> paymentsInfo,
            ArrayList<OBJAccountTrans> accountTranses,
            ArrayList<OBJItemTransaction> itemTransactions,
            int Act) {
            Connection conn = accountpackage.AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOItemMaster.save(obj, conn, Act);
            if(Act == 1){
            DAOItemMaster.saveStock(conn,objstk);
            int b = TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, conn);
            }
            conn.commit();
            return true;
        } catch (SQLException ex) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(SERItemMaster.class.getName()).log(Level.SEVERE, null, ex1);
                }
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
            return false;
        }
    }

    public static OBJItemMaster getitemCat(int Index) {
        OBJItemMaster obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOItemMaster.getNavi(conn, Index);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJItemMaster getNavi(int Index) {
        OBJItemMaster obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOItemMaster.getNavi(conn, Index);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
    
    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOItemMaster.getValue(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOItemMaster.delete(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJItemMaster serch(String code) {
        OBJItemMaster obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOItemMaster.serch(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
         String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOItemMaster.genID(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

    public static String setName(String code, String tble, String col) {
        String Name = null;
        try {
            Connection conn = DBConnection.getConnection();
            Name = DAOItemMaster.genName(conn, tble, code, col);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Name;
    }

    public static Vector getUnit() {
         Vector Unit = null;
        try {
            Connection conn = DBConnection.getConnection();
            Unit = DAOItemMaster.getUnit(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Unit;
    }

    public static String getUName(String UOMCode) {
        String UName = null;
        try {
            Connection conn = DBConnection.getConnection();
            UName = DAOItemMaster.getUnitName(conn,UOMCode);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return UName;
    }

    static void saveStock(OBJItemMaster obj) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOItemMaster.saveStock(conn,obj);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

    }
}
