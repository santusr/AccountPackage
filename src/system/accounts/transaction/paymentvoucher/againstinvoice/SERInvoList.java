/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.paymentvoucher.againstinvoice;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Rabid
 */
public class SERInvoList {
    
    public static ArrayList<OBJInvoList> getList(String Cust){
        ArrayList<OBJInvoList> il = null;
        try {
             Connection con = core.DBConnection.getConnection();
             il = DAOInvoList.getList(Cust, con);
             con.close();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return il;
    }

    public static OBJInstallPay getInstall(String invoNo, String jvDate) {
        OBJInstallPay obj = null;
        try {
             Connection con = core.DBConnection.getConnection();
             obj = DAOInvoList.getInstall(invoNo, con);
             con.close();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return obj;
    }
    
    public static void UpdateIP(OBJInstallPay objIP) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOInvoList.UpdateCr(objIP, conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

    }

    
}
