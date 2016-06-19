/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.receiptvoucher.againstinvoice;

import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Rabid
 */
public class SERInvoList {

    public static ArrayList<OBJInvoList> getList(String Cust) {
        ArrayList<OBJInvoList> il = null;
        try {
            Connection con = accountpackage.AccountPackage.connect();
            con.setAutoCommit(false);
            il = DAOInvoList.getList(Cust, con);
            con.commit();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return il;
    }

    public static OBJInstallPay getInstall(String invoNo, String jvDate) {
        OBJInstallPay obj = null;
        try {
            Connection con = accountpackage.AccountPackage.connect();
            con.setAutoCommit(false);
            obj = DAOInvoList.getInstall(invoNo, jvDate, con);
            con.commit();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return obj;
    }

    public static void UpdateIP(OBJInstallPay objIP, String nextPay) {
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(!true);
            DAOInvoList.UpdateCr(objIP, nextPay, conn);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

    }

    public static ArrayList<OBJPaymentSchedule> getSchedule(String invoiceNo, String VOUDate) {
        ArrayList<OBJPaymentSchedule> il = null;
        try {
            Connection con = accountpackage.AccountPackage.connect();
            con.setAutoCommit(false);
            il = DAOInvoList.getSchedule(invoiceNo, VOUDate, con);
            con.commit();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return il;
    }

}
