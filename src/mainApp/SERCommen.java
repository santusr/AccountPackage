/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainApp;

import accountpackage.AccountPackage;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Rabid
 */
public class SERCommen {

    public static String getDescription(String tbl, String whr, String Code, String Descrip) {
        String name = "";
        try {
            Connection con = AccountPackage.connect();
            con.setAutoCommit(false);
            name = DAOCommen.getDescription(tbl, whr, Code, Descrip, con);
            con.commit();
        } catch (SQLException e) {
            core.Exp.Handle(e);
        }
        return name;
    }

    public static String getDescription(String qry, String key) {
        String name = "";
        try {
            Connection con = AccountPackage.connect();
            con.setAutoCommit(false);
            name = DAOCommen.getDescription(qry, key, con);
            con.commit();
        } catch (SQLException e) {
            core.Exp.Handle(e);
        }
        return name;
    }
    
}
