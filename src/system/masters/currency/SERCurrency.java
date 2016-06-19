/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.currency;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERCurrency {

    public static void save(OBJCurrency currency, int Act) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOCurrency.save(currency, conn, Act);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate FC Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJCurrency getCurrency(int Index) {
        OBJCurrency currency = null;
        try {
            Connection conn = DBConnection.getConnection();
            currency = DAOCurrency.getCurrency(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return currency;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOCurrency.getCurrency(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOCurrency.deleteCurrency(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJCurrency serchCurrency(String code) {
        OBJCurrency currency = null;
        try {
            Connection conn = DBConnection.getConnection();
            currency = DAOCurrency.serchCurrency(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return currency;
    }

    public static String getID() {
         String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOCurrency.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }
}
