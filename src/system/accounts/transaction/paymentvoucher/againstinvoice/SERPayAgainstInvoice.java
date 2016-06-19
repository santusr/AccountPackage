/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.paymentvoucher.againstinvoice;

import system.accounts.transaction.receiptvoucher.againstinvoice.*;
import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERPayAgainstInvoice {
    
     public static void save(OBJPayAgainstInvoice obj, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOPayAgainstInvoice.save(obj, conn, Act);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Cost Center Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJPayAgainstInvoice getNavi(int Index) {
        OBJPayAgainstInvoice obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOPayAgainstInvoice.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOPayAgainstInvoice.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOPayAgainstInvoice.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJPayAgainstInvoice serch(String code) {
        OBJPayAgainstInvoice obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOPayAgainstInvoice.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
    public static String getID() {
        String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOPayAgainstInvoice.genID(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

}
