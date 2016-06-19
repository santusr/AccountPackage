/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.salesrep;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERSalesRep {
   
     public static void save(OBJSalesRep obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOSalesRep.save(obj, conn, Act);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Pay Term Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJSalesRep getNavi(int Index) {
        OBJSalesRep obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesRep.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOSalesRep.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOSalesRep.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJSalesRep serch(String code) {
        OBJSalesRep obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesRep.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
        String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOSalesRep.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }
    public static String setName(String code, String tble, String col) {
         String Name = null;
        try {
            Connection conn = DBConnection.getConnection();
            Name = DAOSalesRep.genName(conn, tble, code, col);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Name;
    }
}
