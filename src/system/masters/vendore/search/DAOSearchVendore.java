/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.vendore.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import system.masters.customer.CustomerStatus;
import system.masters.customer.addnew.OBJCustomer;
import system.masters.vendore.OBJVendore;

/**
 *
 * @author Rabid
 */
class DAOSearchVendore {

    final static String TABALHISTORY = "customer";

    static ArrayList<OBJVendore> getContent(Connection conn, String r) throws SQLException {
        ArrayList<OBJVendore> obja = new ArrayList<>();
        OBJVendore obj;
        String sql = "SELECT customer.*, salesrep.RepName, area.Description FROM customer \n"
                + "	LEFT JOIN salesrep ON salesrep.RepCode = customer.repcode\n"
                + "	LEFT JOIN area ON area.AreaCode = customer.AreaCode\n"
                + "WHERE customer.Type = '" + r + "' AND customer.Status IN ('0', '2', '4')";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJVendore(
                    result.getString("AccCode"),
                    result.getString("CustCode"),
                    result.getString("CustName"),
                    result.getString("ID"),
                    result.getString("Contact"),
                    result.getString("Contact"),
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
                    "",//payterm
                    result.getString("FCCode"),
                    result.getString("FCOPBalance"),
                    result.getString("CreditLimit"),
                    result.getString("CreditDays"),
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

    static ArrayList<OBJVendore> getContent(Connection conn, String r, String s) throws SQLException {
        ArrayList<OBJVendore> obja = new ArrayList<>();
        OBJVendore obj;

        String sql = "SELECT customer.*, salesrep.RepName, area.Description FROM customer \n"
                + "LEFT JOIN salesrep ON salesrep.RepCode = customer.repcode\n"
                + "	LEFT JOIN area ON area.AreaCode = customer.AreaCode\n"
                + "WHERE customer.Type = '" + r + "' AND customer.Status IN ('0', '2', '4') AND CustCode LIKE '" + s + "%' OR AccCode LIKE '" + s + "%' OR CustName LIKE '%" + s + "%' OR customer.ID LIKE '" + s + "%'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJVendore(
                    result.getString("AccCode"),
                    result.getString("CustCode"),
                    result.getString("CustName"),
                    result.getString("ID"),
                    result.getString("Contact"),
                    result.getString("Contact"),
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
                    "",//payterm
                    result.getString("FCCode"),
                    result.getString("FCOPBalance"),
                    result.getString("CreditLimit"),
                    result.getString("CreditDays"),
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
