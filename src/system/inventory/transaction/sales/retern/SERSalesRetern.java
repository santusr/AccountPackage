/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.retern;

import accountpackage.AccountPackage;
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
import system.accounts.transaction.cheque.OBJCheque;
import system.inventory.transaction.sales.Invoice.OBJSalesInvoiceQO;

/**
 *
 * @author sandaruwan
 */
class SERSalesRetern {

    public static boolean doReturn(
            String s,
            ArrayList<OBJSalesReturnItem> obj,
            String date,
            ArrayList<OBJCheque> cheques,
            OBJTransaction transaction,
            OBJPayment payment,
            ArrayList<OBJPaymentInfo> paymentsInfo,
            ArrayList<OBJAccountTrans> accountTranses,
            ArrayList<OBJItemTransaction> itemTransactions) throws SQLException {
        Connection conn = AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOSalesRetern.save(s, obj, date, conn);
            int bb = TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, conn);
            conn.commit();
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

//    public static void save(OBJSalesRetern obji, int Act) {
//        OBJSalesRetern obj = obji;
//        try {
//            try (Connection conn = DBConnection.getConnection()) {
//                DAOSalesRetern.save(obj, conn, Act);
//            }
//        } catch (SQLException ex) {
//            if (ex.getErrorCode() == 1062) {
//                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
//            } else {
//                Exp.Handle(ex);
//            }
//        }
//    }
//    public static void saveHistory(ArrayList<OBJSalesRetern> obj, int Act) {
//        try {
//            Connection conn = DBConnection.getConnection();
//            DAOSalesRetern.saveHistory(obj, conn, Act);
//            conn.close();
//        } catch (SQLException ex) {
//            if (ex.getErrorCode() == 1062) {
//                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
//            } else {
//                Exp.Handle(ex);
//            }
//        }
//    }
    public static OBJSalesRetern getitemCat(int Index) {
        OBJSalesRetern obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesRetern.getNavi(conn, Index);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJSalesRetern getNavi(int Index) {
        OBJSalesRetern obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesRetern.getNavi(conn, Index);
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
            indexCount = DAOSalesRetern.getValue(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOSalesRetern.delete(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJSalesRetern serch(String code) {
        OBJSalesRetern obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesRetern.serch(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static ArrayList<OBJSalesInvoiceQO> InvoHistory(String code) {
        ArrayList<OBJSalesInvoiceQO> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesRetern.InvoHistory(conn, code);
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
            ID = DAOSalesRetern.genID(conn);
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
            Name = DAOSalesRetern.genName(conn, tble, code, col);
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
            Unit = DAOSalesRetern.getUnit(conn);
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
            UName = DAOSalesRetern.getUnitName(conn, UOMCode);
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
            v = DAOSalesRetern.loadCurrency(conn);
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
            d = DAOSalesRetern.gatFCRate(conn, cc);
            conn.close();
        } catch (Exception e) {
        }
        return d;
    }

    static OBJSalesInvoiceQO getTablInfo(String itc, String text) {
        OBJSalesInvoiceQO obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesRetern.getTablInfo(conn, itc, text);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    static Vector itemList(String ic) {
        Vector v = new Vector();
        v.add("asa");
        v.add("ss");
        v.add("sddsd");
        v.add("dffff");
        return v;
    }
}
