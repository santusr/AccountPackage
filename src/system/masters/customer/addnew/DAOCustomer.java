/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.customer.addnew;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author dell
 */
public class DAOCustomer {
    //set Table
    private static final String TABAL = "Customer";
    
    static void save(OBJCustomer obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" (AccCode, CustName, Contact,Contact1, Contact2, MobileNo, Mobileno1, Mobileno2, POBox,  Address1, Address2, Address3, TelOff, TelRes, FaxNo, Email, WebSite, repcode, AreaCode, ID, FCCode, FCOPBalance, CreditLimit, CreditDays, custgrpcode, Remarks, Type, PrintName, CustCode, title) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                 // Remarks, FCDebit, FCCredit, Type, ArabicName, , , ,  , , , 
            System.out.println(obj.getPayterm());
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getName());
            st.setString(3, obj.getContact1());
            st.setString(4, obj.getContact2());
            st.setString(5, obj.getContact3());
            st.setString(6, obj.getMobi1());
            st.setString(7, obj.getMobi2());
            st.setString(8, obj.getMobi3());
            st.setString(9, obj.getPbox());
            st.setString(10, obj.getAdd1());
            st.setString(11, obj.getAdd2());
            st.setString(12, obj.getAdd3());
            st.setString(13, obj.getTeloff());
            st.setString(14, obj.getTelres());
            st.setString(15, obj.getFax());
            st.setString(16, obj.getEmail());
            st.setString(17, obj.getWeb());
            st.setString(18, obj.getSrep());
            st.setString(19, obj.getArea());
            st.setString(20, obj.getPayterm());
            st.setString(21, obj.getCurrency());
            st.setString(22, "0.00");
            st.setString(23, obj.getCreditlimit());
            st.setString(24, obj.getCreditdays());
            st.setString(25, obj.getCustomergroup());
            st.setString(26, obj.getRemark());
            st.setString(27, "C");
            st.setString(28, obj.getPrintName());
            st.setString(29, obj.getCusCode());
            st.setString(30, obj.getTitle());
           
            st.execute();
            
         /*   
            sql = "INSERT INTO AccMst ( LevelNo, ParentCode, ACKey, ACCode, Description, OpBalance, Debit, Credit, ParentList, StartDebit, StartCredit, EndDebit, EndCredit, SrNo, Type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                            
            st = conn.prepareStatement(sql);
            
            st.setString(1, "3");
            st.setString(2, "123000");
            st.setString(3, obj.getCode());
            st.setString(4, obj.getCode());
            st.setString(5, obj.getName());
            st.setString(6, "0.00");
            st.setString(7, "0.00");
            st.setString(8, "0.00");
            st.setString(9,  "123000,100000,000000");
            st.setString(10, "0.00");
            st.setString(11, "0.00");
            st.setString(12, "0.00");
            st.setString(13, "0.00");
            st.setString(14, "1");//getSerial());
            st.setString(15, "C");
            
            st.execute();
            * 
            */
        } else {
            String sql = "UPDATE "+TABAL+" SET CustName = ?, Contact = ?,Contact1 = ?, Contact2 = ?, MobileNo = ?, Mobileno1 = ?, Mobileno2 = ?, POBox = ?,  Address1 = ?, Address2 = ?, Address3 = ?, TelOff = ?, TelRes = ?, FaxNo = ?, Email = ?, WebSite = ?, repcode = ?, AreaCode = ?, ID = ?, FCCode = ?, FCOPBalance = ?, CreditLimit = ?, CreditDays = ?, custgrpcode = ?, Remarks = ?, PrintName = ?, CustCode = ? WHERE AccCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getContact1());
            st.setString(3, obj.getContact2());
            st.setString(4, obj.getContact3());
            st.setString(5, obj.getMobi1());
            st.setString(6, obj.getMobi2());
            st.setString(7, obj.getMobi3());
            st.setString(8, obj.getPbox());
            st.setString(9, obj.getAdd1());
            st.setString(10, obj.getAdd2());
            st.setString(11, obj.getAdd3());
            st.setString(12, obj.getTeloff());
            st.setString(13, obj.getTelres());
            st.setString(14, obj.getFax());
            st.setString(15, obj.getEmail());
            st.setString(16, obj.getWeb());
            st.setString(17, obj.getSrep());
            st.setString(18, obj.getArea());
            st.setString(19, obj.getPayterm());
            st.setString(20, obj.getCurrency());
            st.setString(21, obj.getBalance());
            st.setString(22, obj.getCreditlimit());
            st.setString(23, obj.getCreditdays());
            st.setString(24, obj.getCustomergroup());
            st.setString(25, obj.getRemark());
            st.setString(26, obj.getPrintName());
            st.setString(26, obj.getCusCode());
            st.setString(27, obj.getTitle());
            
            
            st.executeUpdate();
        }
    }

    static OBJCustomer getNavi(Connection conn, int Ix) throws SQLException {

        OBJCustomer obj = null;
        //SQL Server
        //String sql = "SELECT TOP ("+Ix+") * FROM "+TABAL+" WHERE Type = 'U' ORDER BY CustCode ASC";
        
        //My Sql   
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" WHERE Type = 'C' AND Status = '0' ORDER BY AccCode ASC LIMIT "+Ix+",1";
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
                    result.getString("repcode"),
                    result.getString("AreaCode"),
                    result.getString("ID"),
                    result.getString("FCCode"),
                    result.getString("FCOPBalance"),
                    result.getString("CreditLimit"),
                    result.getString("CreditDays"),
                    result.getString("custgrpcode"),
                    result.getString("Remarks"),
                    result.getString("Type"),
                    result.getString("PrintName"));
        }

        return obj;
    }

    static OBJCustomer serch(Connection conn, String code) throws SQLException {

        OBJCustomer obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE AccCode = '" + code + "' AND Status = '0' LIMIT 1";
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
                    result.getString("repcode"),
                    result.getString("AreaCode"),
                    result.getString("ID"),
                    result.getString("FCCode"),
                    result.getString("FCOPBalance"),
                    result.getString("CreditLimit"),
                    result.getString("CreditDays"),
                    result.getString("custgrpcode"),
                    result.getString("Remarks"),
                    result.getString("Type"),
                    result.getString("PrintName"));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM "+TABAL+" WHERE Type = 'C' AND Status = '0'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            index = result.getInt("val");
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE CustCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE AccCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");        
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "123001";
        String sql = "SELECT AccCode FROM "+TABAL+" WHERE Type = 'C' ORDER BY AccCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            ID = result.getString("AccCode");
        }
            int i = Integer.parseInt(ID);
            i++;
            ID = i+"";
            
        return ID;
    }

    static Vector getCusgroup(Connection conn) throws SQLException {
        Vector cusGroup = new Vector();
        String sql = "SELECT CustGrpCode,CustGrpname FROM customerGroup";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("CustGrpCode");
            String name = result.getString("CustGrpname");
            cd = "("+cd+")" + name;
            cusGroup.add(cd);
        }
        return cusGroup;
    }

    static Vector getCurrency(Connection conn) throws SQLException {
        Vector currency = new Vector();
        String sql = "SELECT FCCode,Description FROM FCurrency";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("FCCode");
            String name = result.getString("Description");
            cd = "("+cd+")" + name;
            currency.add(cd);
        }
        return currency;
    }

    static String genName(Connection conn, String tble, String code, String col) throws SQLException{
        String name = "";
        
         String sql = "SELECT Description FROM "+tble+" WHERE "+col+" = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        System.out.println("ssas");
        while (result.next()) {
            name = result.getString("Description");
        }
        
        return name;
    }

    static Vector getSrep(Connection conn) throws SQLException{
        Vector sRep = new Vector();
        String sql = "SELECT RepCode,RepName FROM SalesRep";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("RepCode");
            String name = result.getString("RepName");
            cd = "("+cd+")" + name;
            sRep.add(cd);
        }
        return sRep;
    }
    
    static String getSrep(Connection conn, String code) throws SQLException{
        String sRep = null;
        String sql = "SELECT RepCode,RepName FROM SalesRep WHERE RepCode = '"+code+"'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("RepCode");
            String name = result.getString("RepName");
            sRep = "("+cd+")" + name;
        }
        return sRep;
    }

    static String getcusGroup(Connection conn, String code) throws SQLException{
        String cusGroup = null;
        String sql = "SELECT CustGrpCode,CustGrpName FROM customerGroup WHERE CustGrpCode = '"+code+"'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("CustGrpCode");
            String name = result.getString("CustGrpName");
            cusGroup = "("+cd+")" + name;
        }
        return cusGroup;
    }

    static String getCurrency(Connection conn, String code) throws SQLException{
        String cusGroup = null;
        String sql = "SELECT FCCode,Description FROM FCurrency WHERE FCCode = '"+code+"'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("FCCode");
            String name = result.getString("Description");
            cusGroup = "("+cd+")" + name;
        }
        return cusGroup;
    }

}
