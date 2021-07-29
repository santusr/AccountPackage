/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.stock.stock_adgesment;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author sandaruwan
 */
class SERStockAdjestment {

    public static Boolean save(OBJAdjestment obji, int Act) throws SQLException {
        OBJAdjestment obj = obji;

        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            DAOStockAdjestment.save(obj, conn, Act);
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

    public static OBJAdjestment getitemCat(int Index) {
        OBJAdjestment obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOStockAdjestment.getNavi(conn, Index);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJAdjestment getNavi(int Index) {
        OBJAdjestment obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOStockAdjestment.getNavi(conn, Index);
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
            indexCount = DAOStockAdjestment.getValue(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try (Connection conn = DBConnection.getConnection()) {
            DAOStockAdjestment.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJAdjestment serch(String code) {
        OBJAdjestment obj = new OBJAdjestment();

        try (Connection conn = DBConnection.getConnection()) {
            obj = DAOStockAdjestment.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static ArrayList<OBJAdjestmentHistory> adjHistory(String code) {
        ArrayList<OBJAdjestmentHistory> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            obj = DAOStockAdjestment.adjHistory(conn, code);
            conn.commit();
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
            ID = DAOStockAdjestment.genID(conn);
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
            Name = DAOStockAdjestment.genName(conn, tble, code, col);
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
            Unit = DAOStockAdjestment.getUnit(conn);
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
            UName = DAOStockAdjestment.getUnitName(conn, UOMCode);
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
            v = DAOStockAdjestment.loadCurrency(conn);
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
            d = DAOStockAdjestment.gatFCRate(conn, cc);
            conn.close();
        } catch (Exception e) {
        }
        return d;
    }

//    static OBJStockTransfer getTablInfo(String itc, String text) {
//        OBJStockTransfer obj = null;
//        try {
//            Connection conn = DBConnection.getConnection();
//            obj = DAOStockAdjestment.getTablInfo(conn, itc, text);
//            conn.close();
//        } catch (SQLException ex) {
//            Exp.Handle(ex);
//        }
//
//        return obj;
//    }
    static ArrayList<OBJAdjestmentHistory> loadTable(String code) {
        ArrayList<OBJAdjestmentHistory> obj = null;

        try (Connection conn = DBConnection.getConnection()) {
            obj = DAOStockAdjestment.loadTable(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
}
