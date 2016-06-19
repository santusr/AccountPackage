/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.Deponent;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERDeponent {
   
     public static void save(OBJDeponent obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAODeponent.save(obj, conn, Act);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Pay Term Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJDeponent getNavi(int Index) {
        OBJDeponent obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAODeponent.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAODeponent.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAODeponent.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJDeponent serch(String code) {
        OBJDeponent obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAODeponent.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
        String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAODeponent.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }
    public static String setName(String code, String tble, String col) {
         String Name = null;
        try {
            Connection conn = DBConnection.getConnection();
            Name = DAODeponent.genName(conn, tble, code, col);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Name;
    }
}
