/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.Quotion;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author sandaruwan
 */
class SERQuotation {

    public static void save(OBJQuotation obj, ArrayList<OBJQuotation> obja, int Act) {
        Connection conn = null;
        try {
            conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            DAOQuotation.save(obj, conn, Act);
            DAOQuotation.saveHistory(obja, conn, Act);
            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SERQuotation.class.getName()).log(Level.SEVERE, null, ex1);
            }
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
             Logger.getLogger(SERQuotation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static OBJQuotation getitemCat(int Index) {
        OBJQuotation obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOQuotation.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJQuotation getNavi(int Index) {
        OBJQuotation obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOQuotation.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOQuotation.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOQuotation.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJQuotation serch(String code) {
        OBJQuotation obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOQuotation.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static ArrayList<OBJQuotation> QuotHistory(String code) {
        ArrayList<OBJQuotation> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOQuotation.QuotHistory(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
        String ID = "SON000001";
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOQuotation.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

    public static String setName(String code, String tble, String col) {
        String Name = null;
        try {
            Connection conn = DBConnection.getConnection();
            Name = DAOQuotation.genName(conn, tble, code, col);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Name;
    }

    public static Vector getUnit() {
        Vector Unit = null;
        try {
            Connection conn = DBConnection.getConnection();
            Unit = DAOQuotation.getUnit(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Unit;
    }

    public static String getUName(String UOMCode) {
        String UName = null;
        try {
            Connection conn = DBConnection.getConnection();
            UName = DAOQuotation.getUnitName(conn, UOMCode);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return UName;
    }

    static Vector loadCurrency() {
        Vector v = new Vector();
        try {
            Connection con = DBConnection.getConnection();
            v = DAOQuotation.loadCurrency(con);
        } catch (Exception e) {
            Exp.Handle(e);
        }
        return v;
    }

    static double getFCRate(String cc) {
        double d = 0.00;
        try {
            Connection con = DBConnection.getConnection();
            d = DAOQuotation.gatFCRate(con, cc);
        } catch (Exception e) {
        }
        return d;
    }

    static OBJQuotation getTablInfo(String itc, String text) {
        OBJQuotation obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOQuotation.getTablInfo(conn, itc, text);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
}
