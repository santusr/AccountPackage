/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.customer.addnew;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERCustomer {
    
     public static void save(OBJCustomer obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            DAOCustomer.save(obj, conn, Act);
            //DAOAccountcreation.save(objacc, conn, Act);
            conn.commit();
            //conn.close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Cost Center Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJCustomer getNavi(int Index) {
        OBJCustomer obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOCustomer.getNavi(conn, Index);
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
            indexCount = DAOCustomer.getValue(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOCustomer.delete(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJCustomer serch(String code) {
        OBJCustomer obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOCustomer.serch(conn, code);
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
            ID = DAOCustomer.genID(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

    public static Vector getCusgroup() {
       Vector cusGroup = null;
        try {
            Connection conn = DBConnection.getConnection();
            cusGroup = DAOCustomer.getCusgroup(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return cusGroup; 
    }
    public static Vector getCurrency() {
       Vector currency = null;
        try {
            Connection conn = DBConnection.getConnection();
            currency = DAOCustomer.getCurrency(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return currency; 
    }

    public static String setName(String code, String tble, String col) {
         String Name = null;
        try {
            Connection conn = DBConnection.getConnection();
            Name = DAOCustomer.genName(conn, tble, code, col);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Name;
    }

    public static Vector getSRep() {
        Vector Srep = null;
        try {
            Connection conn = DBConnection.getConnection();
            Srep = DAOCustomer.getSrep(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Srep; 
    }
    
    public static String getSRep(String code) {
        String Srep = null;
        try {
            Connection conn = DBConnection.getConnection();
            Srep = DAOCustomer.getSrep(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Srep; 
    }

    public static String getcusGroup(String code) {
        String cusGroup = null;
        try {
            Connection conn = DBConnection.getConnection();
            cusGroup = DAOCustomer.getcusGroup(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return cusGroup; 
    }

    public static String getCurrency(String code) {
        String Currency = null;
        try {
            Connection conn = DBConnection.getConnection();
            Currency = DAOCustomer.getCurrency(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
        return Currency; 
    }
}
