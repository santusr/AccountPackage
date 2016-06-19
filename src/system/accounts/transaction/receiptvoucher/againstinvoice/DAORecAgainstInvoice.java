/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.receiptvoucher.againstinvoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAORecAgainstInvoice {
     //set Table
    private static final String TABAL = "customerGroup";
   
    static OBJRecAgainstInvoice getNavi(Connection conn, int Ix) throws SQLException {

        OBJRecAgainstInvoice obj = null;
        //SQL Server
        //String sql = "SELECT TOP(" + Ix + ") * FROM "+TABAL+" ORDER BY CustGrpCode ASC";
        
        //MySql
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" ORDER BY CustGrpCode ASC LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

//            obj = new OBJRecAgainstInvoice(
//                    result.getString(1),
//                    result.getString(2),
//                    result.getString(3),
//                    result.getString(4),
//                    result.getString(5),
//                    result.getString(6),
//                    result.getString(7),
//                    result.getString(8),
//                    result.getString(9),
//                    result.getString(10),
//                    result.getString(11));
        }

        return obj;
    }

    static OBJRecAgainstInvoice serch(Connection conn, String code) throws SQLException {

        OBJRecAgainstInvoice obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE CustGrpCode = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

//            obj = new OBJRecAgainstInvoice(
//                    result.getString(1),
//                    result.getString(2),
//                    result.getString(3),
//                    result.getString(4),
//                    result.getString(5),
//                    result.getString(6),
//                    result.getString(7),
//                    result.getString(8),
//                    result.getString(9),
//                    result.getString(10),
//                    result.getString(11));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM "+TABAL+"";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            //index++;
            index = result.getInt("val");
        }
        return index;
    }

    
    static double getBalance(String CreditId, int installNo, Connection conn) throws SQLException {
        double balance = 0.00;
        String sql = "SELECT installNo, CreditId FROM payschedule WHERE CreditId = '"+CreditId+"' AND Status = '1' ORDER BY creditId, installNo DESC LIMIT 1";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        if (result.next()) {
            int lastPayinstallNo = result.getInt("installNo"); 
           balance = calcBalance(lastPayinstallNo, installNo, CreditId, conn);
        } else {
             balance = calcBalance(1, installNo, CreditId, conn);
        }
        return balance;
    }

    private static double calcBalance(int lastPayInstall, int payInstall, String CreditId, Connection conn) throws SQLException {
        payInstall = payInstall - 1;
        double balance = 0.00;
        String sql = "SELECT SUM(balance) as totBalance FROM payschedule WHERE creditId = '"+CreditId+"' AND installNo BETWEEN '"+lastPayInstall+"' AND '"+payInstall+"'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
           balance = result.getDouble("totBalance");
        }
        return balance;
    }

}
