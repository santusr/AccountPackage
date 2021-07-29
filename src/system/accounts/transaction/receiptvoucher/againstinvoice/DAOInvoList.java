/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.receiptvoucher.againstinvoice;

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
public class DAOInvoList {

    static ArrayList<OBJInvoList> getList(String Cust, Connection con) throws SQLException {
        ArrayList<OBJInvoList> il = new ArrayList<OBJInvoList>();
        OBJInvoList obj = null;
        String sql = ""
                + "SELECT "
                + "             credit.InvoNo,"
                + "             credit.PDate,"
                + "             invoiceheader.NetAmount,"
                + "             invoiceheader.InvoDate,"
                + "             credit.Totpayble,"
                + "             credit.TotPay,"
                + "             credit.InstalValue,"
                + "             credit.InvoCredit,"
                + "             Credit.TotInterest,"
                + "             Credit.LateCharge,"
                + "             credit.SPDisc,"
                + "             invoiceheader.loan_no, "
                + "             Credit.Status "
                + "FROM "
                + "             credit "
                + "              left join invoiceheader on invoiceheader.InvoNo = credit.InvoNo "
                + "WHERE "
                + "             credit.CustCode = '" + Cust + "' AND "
                + "             invoiceheader.InvoNo = credit.InvoNo AND "
                + "             Credit.Status in ('0')";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        String tot = "0.00";
        double d0, d1, d2, d3;
        while (result.next()) {
            d0 = Double.parseDouble(result.getString("InvoCredit"));
            d1 = Double.parseDouble(result.getString("TotInterest"));
            d2 = Double.parseDouble(result.getString("LateCharge"));
            d3 = Double.parseDouble(result.getString("SPDisc"));

            d0 = d0 + d1 + d2 - d3;
            tot = Locals.currencyFormat(d0);
            obj = new OBJInvoList(
                    result.getString("InvoNo"),
                    result.getString("InvoDate"),
                    result.getString("NetAmount"),
                    tot,
                    result.getString("TotPay"),
                    result.getString("Totpayble"),
                    result.getString("InstalValue"),
                    result.getString("PDate"),
                    result.getString("loan_no"));
            obj.setStatus(result.getString("status"));
            il.add(obj);
        }
        return il;
    }

    static OBJInstallPay getInstall(String invoNo, String jvDate, Connection con) throws SQLException {
        OBJInstallPay obj = null;
//        String sql = "SELECT * FROM payschedule WHERE invoNo = '"+invoNo+"' AND status = '0' AND DueDate <= jvDate ORDER BY InstallNo ASC LIMIT 1";
        String sql = "SELECT * FROM payschedule WHERE invoNo = '" + invoNo + "' AND status = '0' AND DueDate <= '" + jvDate + "' ORDER BY InstallNo DESC LIMIT 1";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        String tot = "0.00";
        if (result.next()) {
            obj = new OBJInstallPay(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    "",
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(11),
                    result.getString(12),
                    result.getString(14),
                    result.getString(13));

        } else {
            sql = "SELECT * FROM payschedule WHERE invoNo = '" + invoNo + "' AND DueDate > CURRENT_TIMESTAMP AND status = '0' ORDER BY InstallNo ASC LIMIT 1";
            st = con.prepareStatement(sql);
            st.execute();
            result = st.getResultSet();
            tot = "0.00";
            if (result.next()) {
                System.out.println("Credit_ID = " + result.getString(1));
                obj = new OBJInstallPay(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        "",
                        "10",
                        result.getString(7),
                        result.getString(8),
                        result.getString(9),
                        result.getString(10),
                        result.getString(11),
                        result.getString(12),
                        result.getString(14),
                        result.getString(13));

            }
        }
        return obj;
    }

    static void UpdateCr(OBJInstallPay objIP, String nextPay, Connection conn) throws SQLException {
//        String sql = "UPDATE credit SET TotPayble = ?, TotPay = ?, PDate = ?, status = ?, SPDisc = ? WHERE CreditId = '" + objIP.getCreditId()+ "'";
        PreparedStatement st;
        String sql;

        sql = "UPDATE credit SET TotPayble = ((TotPayble - ?) - ?), TotPay = TotPay + ?, PDate = ?, SPDisc = SPDisc + ?, status = ? WHERE CreditId = '" + objIP.getCreditId() + "'";
        //clarDate = ?, status = ?, LatePay = ?, TotInstallVal = ?, PayAmount = ?, Balance = ?, SPDate = ? WHERE CreditId = '" + objIP.getCreditId()+ " AND '";
        st = conn.prepareStatement(sql);

        //int ii = Integer.parseInt(objIP.getInstallNo()) + 1;
        String SPdate = nextPay;

        st.setDouble(1, Double.parseDouble(objIP.getPayAmount()));
        st.setDouble(2, Double.parseDouble(objIP.getSPDisc()));
        st.setDouble(3, Double.parseDouble(objIP.getPayAmount()));
        st.setString(4, SPdate);
        st.setDouble(5, Double.parseDouble(objIP.getSPDisc()));
        st.setString(6, objIP.getStatus());

        st.executeUpdate();

    }

    static ArrayList<OBJPaymentSchedule> getSchedule(String invoiceNo, String VOUDate, Connection con) throws SQLException {
        ArrayList<OBJPaymentSchedule> il = new ArrayList<OBJPaymentSchedule>();
        OBJPaymentSchedule obj = null;
        String sql = "SELECT\n"
                + "	payschedule.* \n"
                + "FROM \n"
                + "	payschedule \n"
                + "LEFT JOIN \n"
                + "	credit ON credit.CreditId = payschedule.CreditId\n"
                + "WHERE \n"
                + "	credit.InvoNo = '" + invoiceNo + "' AND \n"
                + "	credit.`status`='0' "
                + "ORDER BY payschedule.print_order, payschedule.ID";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJPaymentSchedule();
            obj.setIndexNo(result.getString("installNo"));
            obj.setDueDate(result.getString("DueDate"));
            obj.setLatePay(result.getString("LatePay"));
            obj.setSpDiscount(result.getString("SPDisc"));
            obj.setInstallValue(result.getString("totInstallVal"));
            obj.setPaid(result.getString("payAmount"));
            obj.setBalance(result.getString("balance"));
            obj.setStatus(result.getString("status"));
            obj.setType(result.getString("type"));
            obj.setPrintOrder(result.getString("print_order"));
            obj.setID(result.getString("ID"));
            il.add(obj);
        }
        return il;

    }

    public static ArrayList<OBJPaymentSchedule> getScheduleRev(String invoiceNo, String VOUDate, Connection con) throws SQLException {
        ArrayList<OBJPaymentSchedule> il = new ArrayList<OBJPaymentSchedule>();
        OBJPaymentSchedule obj = null;
        String sql = "SELECT\n"
                + "	payschedule.* \n"
                + "FROM \n"
                + "	payschedule \n"
                + "LEFT JOIN \n"
                + "	credit ON credit.CreditId = payschedule.CreditId\n"
                + "WHERE \n"
                + "	credit.InvoNo = '" + invoiceNo + "' AND \n"
                + "	credit.`status`='0' "
                + "ORDER BY payschedule.ID, payschedule.print_order DESC";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJPaymentSchedule();
            obj.setIndexNo(result.getString("installNo"));
            obj.setDueDate(result.getString("DueDate"));
            obj.setLatePay(result.getString("LatePay"));
            obj.setSpDiscount(result.getString("SPDisc"));
            obj.setInstallValue(result.getString("totInstallVal"));
            obj.setPaid(result.getString("payAmount"));
            obj.setBalance(result.getString("balance"));
            obj.setStatus(result.getString("status"));
            obj.setType(result.getString("type"));
            obj.setCreditId(result.getString("CreditId"));
            obj.setPrintOrder(result.getString("print_order"));
            obj.setID(result.getString("ID"));
            il.add(obj);
        }
        return il;

    }
}
