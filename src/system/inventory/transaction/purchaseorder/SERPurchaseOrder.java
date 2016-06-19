/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.purchaseorder;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERPurchaseOrder {

    public static void save(OBJPurchaseOrder obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOPurchaseOrder.save(obj, conn, Act);
            conn.close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }
    
    public static void saveHistory(ArrayList <OBJPurchaseOrder> obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOPurchaseOrder.saveHistory(obj, conn, Act);
            conn.close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJPurchaseOrder getitemCat(int Index) {
        OBJPurchaseOrder obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOPurchaseOrder.getNavi(conn, Index);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJPurchaseOrder getNavi(int Index) {
        OBJPurchaseOrder obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOPurchaseOrder.getNavi(conn, Index);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
      
    public static ArrayList <OBJPurchaseOrder> getHistory(String code) {
        ArrayList <OBJPurchaseOrder> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOPurchaseOrder.POHistory(conn, code);
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
            indexCount = DAOPurchaseOrder.getValue(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOPurchaseOrder.delete(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJPurchaseOrder serch(String code) {
        OBJPurchaseOrder obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOPurchaseOrder.serch(conn, code);
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
            ID = DAOPurchaseOrder.genID(conn);
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
            Name = DAOPurchaseOrder.genName(conn, tble, code, col);
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
            Unit = DAOPurchaseOrder.getUnit(conn);
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
            UName = DAOPurchaseOrder.getUnitName(conn,UOMCode);
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
            v = DAOPurchaseOrder.loadCurrency(conn);
            conn.close();
        } catch (Exception e) {
            Exp.Handle(e);
        }
        return v;
    }

    static OBJPurchaseOrder getTablInfo(String itc, String text) {
        OBJPurchaseOrder obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOPurchaseOrder.getTablInfo(conn, itc, text);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
        
    static double getFCRate(String cc) {
        double d = 0.00;
        try {
            Connection conn = DBConnection.getConnection();
            d = DAOPurchaseOrder.gatFCRate(conn, cc);
            conn.close();
        } catch (Exception e) {
        }
        return d;
    }

}
