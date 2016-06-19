/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.vendore;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import system.accounts.master.accountcreation.DAOAccountcreation;
import system.accounts.master.accountcreation.OBJAccountCreation;

/**
 *
 * @author dell
 */
public class SERVendore {

     public static void save(OBJVendore obj, OBJAccountCreation objacc, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            DAOVendore.save(obj, conn, Act);
            DAOAccountcreation.save(objacc, conn, Act);
            conn.commit();
            conn.close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Cost Center Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJVendore getNavi(int Index) {
        OBJVendore obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOVendore.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOVendore.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOVendore.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJVendore serch(String code) {
        OBJVendore obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOVendore.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
        String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOVendore.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

    public static Vector getCurrency() {
       Vector currency = null;
        try {
            Connection conn = DBConnection.getConnection();
            currency = DAOVendore.getCurrency(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return currency; 
    }
    public static String setName(String code, String tble, String col) {
         String Name = null;
        try {
            Connection conn = DBConnection.getConnection();
            Name = DAOVendore.genName(conn, tble, code, col);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Name;
    }
      public static Vector getSRep() {
        Vector Srep = null;
        try {
            Connection conn = DBConnection.getConnection();
            Srep = DAOVendore.getSrep(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Srep; 
    }
    
    public static String getSRep(String code) {
        String Srep = null;
        try {
            Connection conn = DBConnection.getConnection();
            Srep = DAOVendore.getSrep(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Srep; 
    }

    public static String getCurrency(String code) {
        String Currency = null;
        try {
            Connection conn = DBConnection.getConnection();
            Currency = DAOVendore.getCurrency(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Currency; 
    }
}
