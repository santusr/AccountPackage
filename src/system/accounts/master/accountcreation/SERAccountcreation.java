/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.master.accountcreation;

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
public class SERAccountcreation {
    
     public static void save(OBJAccountCreation obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOAccountcreation.save(obj, conn, Act);
            conn.close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Cost Center Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJAccountCreation getNavi(int Index) {
        OBJAccountCreation obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOAccountcreation.getNavi(conn, Index);
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
            indexCount = DAOAccountcreation.getValue(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOAccountcreation.delete(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJAccountCreation serch(String code) {
        OBJAccountCreation obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOAccountcreation.serch(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID(String l1, String l2,  String l3) {
        String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOAccountcreation.genID(conn, l1, l2, l3);
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
            cusGroup = DAOAccountcreation.getCusgroup(conn);
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
            currency = DAOAccountcreation.getCurrency(conn);
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
            Name = DAOAccountcreation.genName(conn, tble, code, col);
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
            Srep = DAOAccountcreation.getSrep(conn);
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
            Srep = DAOAccountcreation.getSrep(conn, code);
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
            cusGroup = DAOAccountcreation.getcusGroup(conn, code);
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
            Currency = DAOAccountcreation.getCurrency(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
        return Currency; 
    }

    static ArrayList<OBJAccountCreation> loadTbl() {
        ArrayList <OBJAccountCreation> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOAccountcreation.loadTbl(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
}
