/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.receiptvoucher.againstinvoice;

import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class SERRecAgainstInvoice {

    
    public static OBJRecAgainstInvoice getNavi(int Index) {
        OBJRecAgainstInvoice obj = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            obj = DAORecAgainstInvoice.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            indexCount = DAORecAgainstInvoice.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static OBJRecAgainstInvoice serch(String code) {
        OBJRecAgainstInvoice obj = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            obj = DAORecAgainstInvoice.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    
    static double getBalance(String CreditId, int installNo) {
        double balance = 0.00;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            balance = DAORecAgainstInvoice.getBalance(CreditId, installNo, conn);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
        return balance;
    }

}
