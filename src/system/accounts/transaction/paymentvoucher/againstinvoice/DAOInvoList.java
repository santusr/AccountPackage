/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.paymentvoucher.againstinvoice;

import core.Locals;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Rabid
 */
class DAOInvoList {

    static ArrayList<OBJInvoList> getList(String Cust, Connection con) throws SQLException {
        ArrayList<OBJInvoList> il = new ArrayList<>();
        OBJInvoList obj;
        String sql = "SELECT creditgrn.InvoNo,creditgrn.PDate,purchaseheader.NetAmount,purchaseheader.PurDate,creditgrn.Totpayble,creditgrn.TotPay,creditgrn.InstalValue,creditgrn.InvoCredit,creditgrn.TotInterest,creditgrn.LateCharge,creditgrn.SPDisc FROM creditgrn,purchaseheader WHERE creditgrn.CustCode = '" + Cust + "' AND purchaseheader.PurNo = creditgrn.InvoNo AND creditgrn.Status = '0'";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        String tot;
        double d, d1, d2, d3;
        while (result.next()) {
            d = Double.parseDouble(result.getString("InvoCredit"));
            d1 = Double.parseDouble(result.getString("TotInterest"));
            d2 = Double.parseDouble(result.getString("LateCharge"));
            d3 = Double.parseDouble(result.getString("SPDisc"));

            d = d + d1 + d2 - d3;
            tot = Locals.currencyFormat(d);
            obj = new OBJInvoList(
                    result.getString("InvoNo"),
                    result.getString("PurDate"),
                    result.getString("NetAmount"),
                    tot,
                    result.getString("TotPay"),
                    result.getString("Totpayble"),
                    result.getString("InstalValue"),
                    result.getString("PDate"));
            il.add(obj);
        }
        return il;
    }

    static OBJInstallPay getInstall(String invoNo, Connection con) throws SQLException {
        OBJInstallPay obj = null;

//        String sql = "SELECT * FROM payschedule WHERE invoNo = '"+invoNo+"' AND status = '0' AND DueDate <= CURRENT_TIMESTAMP ORDER BY InstallNo ASC LIMIT 1";
        String sql = "SELECT * FROM creditgrn WHERE invoNo = '" + invoNo + "' AND status = '0' LIMIT 1";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        String tot = "0.00";
        if (result.next()) {

            obj = new OBJInstallPay(
                    result.getString("CreditID"),
                    result.getString("AgNo"),
                    result.getString("PDate"),
                    result.getString("TotPayble"),
                    "",
                    "",
                    result.getString("InvoNo"),
                    "1",
                    result.getString("LateCharge"),
                    result.getString("InstalValue"),
                    result.getString("TotPay"),
                    result.getString("TotPayble"),
                    result.getString("SPDisc"));

        }
        return obj;
    }

    static void UpdateCr(OBJInstallPay objIP, Connection conn) throws SQLException {
        String sql = "UPDATE creditgrn SET TotPayble = (TotPayble - ?) - ? + ?, TotPay = TotPay + ?, PDate = ?, SPDisc = SPDisc + ?, LateCharge = LateCharge + ? WHERE CreditId = '" + objIP.getCreditId() + "'";
        PreparedStatement st = conn.prepareStatement(sql);

        String SPdate = objIP.getClearDate();

        st.setDouble(1, Double.parseDouble(objIP.getPayAmount()));
        st.setDouble(2, Double.parseDouble(objIP.getSPDisc()));
        st.setDouble(3, Double.parseDouble(objIP.getLatePay()));
        st.setDouble(4, Double.parseDouble(objIP.getPayAmount()));
        st.setString(5, SPdate);
        st.setDouble(6, Double.parseDouble(objIP.getSPDisc()));
        st.setDouble(7, Double.parseDouble(objIP.getLatePay()));

        st.executeUpdate();

    }
}
