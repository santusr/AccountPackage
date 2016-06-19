/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.customer.search;

import accountpackage.AccountPackage;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import system.masters.customer.addnew.OBJCustomer;

/**
 *
 * @author Rabid
 */
public class SERSearchCustomer {

    public static ArrayList<OBJCustomer> getContent(String r) {
         ArrayList <OBJCustomer> obj = null;
        try {
            Connection conn = AccountPackage.connect();
            conn.setAutoCommit(false);
            obj = DAOSearchCustomer.getContent(conn, r);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
    

    public static ArrayList<OBJCustomer> getContent(String r, String s) {
         ArrayList <OBJCustomer> obj = null;
        try {
            Connection conn = AccountPackage.connect();
            conn.setAutoCommit(false);
            obj = DAOSearchCustomer.getContent(conn, r, s);
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
            DAOSearchCustomer.doBlackList(conn, customer, status);
            conn.commit();
            return true;
        } catch (SQLException ex) {
            Exp.Handle(ex);
            return false;
        }
    }
    
}
