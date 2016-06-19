/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.stock.stocktransfer;

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
import javax.swing.JOptionPane;

/**
 *
 * @author sandaruwan
 */
class SERStockTransfer {

    public static Boolean save(
            OBJStockTransfer obji,
            ArrayList<OBJStockTransfer> obja,
            OBJTransaction transaction,
            OBJPayment payment,
            ArrayList<OBJPaymentInfo> paymentsInfo,
            ArrayList<OBJAccountTrans> accountTranses,
            ArrayList<OBJItemTransaction> itemTransactions,
            int Act) throws SQLException {
        
        OBJStockTransfer obj = obji;
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            DAOStockTransfer.save(obj, conn, Act);
            DAOStockTransfer.saveHistory(obja, conn, Act);
            int b = TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, conn);
            conn.commit();
            conn.close();
            return true;
        } catch (SQLException ex) {
            conn.rollback();
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
            return false;
        }
    }
    /* DISABLE BECUSE ATOMIC TRANSACTION (COMMITING)
     public static void saveHistory(ArrayList <OBJStockTransfer> obj, int Act) {
     try {
     Connection conn = DBConnection.getConnection();
     DAOStockTransfer.saveHistory(obj, conn, Act);
     conn.close();
     } catch (SQLException ex) {
     if (ex.getErrorCode() == 1062) {
     JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
     } else {
     Exp.Handle(ex);
     }
     }
     }*/

    public static OBJStockTransfer getitemCat(int Index) {
        OBJStockTransfer obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOStockTransfer.getNavi(conn, Index);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJStockTransfer getNavi(int Index) {
        OBJStockTransfer obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOStockTransfer.getNavi(conn, Index);
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
            indexCount = DAOStockTransfer.getValue(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOStockTransfer.delete(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJStockTransfer serch(String code) {
        OBJStockTransfer obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOStockTransfer.serch(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static ArrayList<OBJStockTransfer> InvoHistory(String code) {
        ArrayList<OBJStockTransfer> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOStockTransfer.InvoHistory(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
        String ID = "000001";
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOStockTransfer.genID(conn);
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
            Name = DAOStockTransfer.genName(conn, tble, code, col);
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
            Unit = DAOStockTransfer.getUnit(conn);
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
            UName = DAOStockTransfer.getUnitName(conn, UOMCode);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return UName;
    }

    static Vector loadCurrency() {
        Vector v = new Vector();
        try {
            Connection conn = DBConnection.getConnection();
            v = DAOStockTransfer.loadCurrency(conn);
            conn.close();
        } catch (Exception e) {
            Exp.Handle(e);
        }
        return v;
    }

    static double getFCRate(String cc) {
        double d = 0.00;
        try {
            Connection conn = DBConnection.getConnection();
            d = DAOStockTransfer.gatFCRate(conn, cc);
            conn.close();
        } catch (Exception e) {
        }
        return d;
    }

    static OBJStockTransfer getTablInfo(String itc, String text) {
        OBJStockTransfer obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOStockTransfer.getTablInfo(conn, itc, text);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
}
