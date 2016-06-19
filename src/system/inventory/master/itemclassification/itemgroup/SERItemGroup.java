/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.itemclassification.itemgroup;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERItemGroup {

    public static void save(OBJItemGroup obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOItemGroup.save(obj, conn, Act);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJItemGroup getitemCat(int Index) {
        OBJItemGroup obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOItemGroup.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJItemGroup getNavi(int Index) {
        OBJItemGroup obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOItemGroup.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
    
    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOItemGroup.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOItemGroup.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJItemGroup serch(String code) {
        OBJItemGroup obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOItemGroup.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
         String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOItemGroup.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }
}
