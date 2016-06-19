/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.costcenter;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERCostCenter {
     public static void save(OBJCostCenter obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOCostCenter.save(obj, conn, Act);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Cost Center Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJCostCenter getNavi(int Index) {
        OBJCostCenter obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOCostCenter.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOCostCenter.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOCostCenter.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJCostCenter serch(String code) {
        OBJCostCenter obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOCostCenter.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
    public static String getID() {
        String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOCostCenter.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }
}
