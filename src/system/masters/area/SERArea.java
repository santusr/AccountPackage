/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.area;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERArea {
    
    public static void save(OBJArea area, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOArea.save(area, conn, Act);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Area Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJArea getArea(int Index) {
        OBJArea area = null;
        try {
            Connection conn = DBConnection.getConnection();
            area = DAOArea.getArea(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return area;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOArea.getArea(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOArea.deleteArea(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJArea serchArea(String code) {
        OBJArea area = null;
        try {
            Connection conn = DBConnection.getConnection();
            area = DAOArea.serchArea(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return area;
    }

    public static String getID() {
        String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOArea.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }  
}
