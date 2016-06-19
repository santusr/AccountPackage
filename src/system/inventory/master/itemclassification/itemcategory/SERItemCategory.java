/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.itemclassification.itemcategory;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERItemCategory {

    public static void save(OBJItemCategory obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOItemCategory.save(obj, conn, Act);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJItemCategory getitemCat(int Index) {
        OBJItemCategory obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOItemCategory.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJItemCategory getNavi(int Index) {
        OBJItemCategory obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOItemCategory.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
    
    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOItemCategory.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOItemCategory.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJItemCategory serch(String code) {
        OBJItemCategory obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOItemCategory.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
         String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOItemCategory.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }
}
