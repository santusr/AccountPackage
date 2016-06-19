/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.vendore.search;

import accountpackage.AccountPackage;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import system.masters.vendore.OBJVendore;

/**
 *
 * @author Rabid
 */
public class SERSearchVendore {

    public static ArrayList<OBJVendore> getContent(String r) {
         ArrayList <OBJVendore> obj = null;
        try {
            Connection conn = AccountPackage.connect();
            conn.setAutoCommit(false);
            obj = DAOSearchVendore.getContent(conn, r);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
    

    public static ArrayList<OBJVendore> getContent(String r, String s) {
         ArrayList <OBJVendore> obj = null;
        try {
            Connection conn = AccountPackage.connect();
            conn.setAutoCommit(false);
            obj = DAOSearchVendore.getContent(conn, r, s);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    static boolean setBlackList(String customer, String status) {
        try {
            Connection conn = AccountPackage.connect();
            conn.setAutoCommit(false);
            DAOSearchVendore.doBlackList(conn, customer, status);
            conn.commit();
            return true;
        } catch (SQLException ex) {
            Exp.Handle(ex);
            return false;
        }
    }
    
}
