/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.customer.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import system.masters.customer.CustomerStatus;
import system.masters.customer.addnew.OBJCustomer;

/**
 *
 * @author Rabid
 */
class DAOSearchCustomer {

    final static String TABALHISTORY = "customer";

    static ArrayList<OBJCustomer> getContent(Connection conn, String r) throws SQLException {
        ArrayList<OBJCustomer> obja = new ArrayList<>();
        OBJCustomer obj;
        String sql = "SELECT customer.*, salesrep.RepName, area.Description FROM customer \n"
                + "	LEFT JOIN salesrep ON salesrep.RepCode = customer.repcode\n"
                + "	LEFT JOIN area ON area.AreaCode = customer.AreaCode\n"
                + "WHERE customer.Type = '" + r + "' AND customer.Status IN ('0', '2', '4')";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJCustomer(
                    result.getString("AccCode"),
                    result.getString("CustCode"),
                    result.getString("Title"),
                    result.getString("CustName"),
                    result.getString("Contact"),
                    result.getString("Contact1"),
                    result.getString("Contact2"),
                    result.getString("MobileNo"),
                    result.getString("MobileNo1"),
                    result.getString("MobileNo2"),
                    result.getString("POBox"),
                    result.getString("Address1"),
                    result.getString("Address2"),
                    result.getString("Address3"),
                    result.getString("TelOff"),
                    result.getString("TelRes"),
                    result.getString("FaxNo"),
                    result.getString("Email"),
                    result.getString("WebSite"),
                    result.getString("repName"),
                    result.getString("Description"),
                    result.getString("ID"),
                    result.getString("FCCode"),
                    result.getString("FCOPBalance"),
                    result.getString("CreditLimit"),
                    result.getString("CreditDays"),
                    result.getString("custgrpcode"),
                    result.getString("Remarks"),
                    result.getString("Type"),
                    result.getString("PrintName"));

            String status = CustomerStatus.customerStatus(result.getString("Status"));

            obj.setStatus(status);
            obja.add(obj);
        }
        st.close();
        return obja;
    }

    static ArrayList<OBJCustomer> getContent(Connection conn, String r, String s) throws SQLException {
        ArrayList<OBJCustomer> obja = new ArrayList<>();
        OBJCustomer obj;

        String sql = "SELECT customer.*, salesrep.RepName, area.Description FROM customer \n"
                + "LEFT JOIN salesrep ON salesrep.RepCode = customer.repcode\n"
                + "	LEFT JOIN area ON area.AreaCode = customer.AreaCode\n"
                + "WHERE customer.Type = '" + r + "' AND customer.Status IN ('0', '2', '4') AND CustCode LIKE '" + s + "%' OR AccCode LIKE '" + s + "%' OR CustName LIKE '%" + s + "%' OR customer.ID LIKE '" + s + "%'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJCustomer(
                    result.getString("AccCode"),
                    result.getString("CustCode"),
                    result.getString("Title"),
                    result.getString("CustName"),
                    result.getString("Contact"),
                    result.getString("Contact1"),
                    result.getString("Contact2"),
                    result.getString("MobileNo"),
                    result.getString("MobileNo1"),
                    result.getString("MobileNo2"),
                    result.getString("POBox"),
                    result.getString("Address1"),
                    result.getString("Address2"),
                    result.getString("Address3"),
                    result.getString("TelOff"),
                    result.getString("TelRes"),
                    result.getString("FaxNo"),
                    result.getString("Email"),
                    result.getString("WebSite"),
                    result.getString("repName"),
                    result.getString("Description"),
                    result.getString("ID"),
                    result.getString("FCCode"),
                    result.getString("FCOPBalance"),
                    result.getString("CreditLimit"),
                    result.getString("CreditDays"),
                    result.getString("custgrpcode"),
                    result.getString("Remarks"),
                    result.getString("Type"),
                    result.getString("PrintName"));

            String status = CustomerStatus.customerStatus(result.getString("Status"));

            obj.setStatus(status);

            obja.add(obj);
        }
        st.close();
        return obja;
    }

    static void doBlackList(Connection conn, String customer, String status) throws SQLException {
        String sql = "UPDATE customer SET status = '" + status + "' WHERE AccCode = '" + customer + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.executeUpdate();
    }

}
