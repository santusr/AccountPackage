/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.narration;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERNarration {
    public static void save(OBJNarration obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAONarration.save(obj, conn, Act);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Cost Center Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJNarration getNavi(int Index) {
        OBJNarration obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAONarration.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAONarration.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAONarration.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJNarration serch(String code) {
        OBJNarration obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAONarration.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
        String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAONarration.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }
}
