/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.master.accountcreation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author dell
 */
public class DAOAccountcreation {
    //set Table
    private static final String TABAL = "Account";
    
    public static void save(OBJAccountCreation obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" (AccCode, AccName, L1Type, L2Type, L3Type, OPBalance, Debit, Credit, AccType, PCode) VALUES (?,?,?,?,?,?,?,?,?,?)";
                                 // Remarks, FCDebit, FCCredit, Type, ArabicName, , , ,  , , , 
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getAccCode());
            st.setString(2, obj.getAccName());
            st.setString(3, obj.getL1Type());
            st.setString(4, obj.getL2Type());
            st.setString(5, obj.getL3Type());
            st.setString(6, obj.getOPBalance());
            st.setString(7, obj.getDebit());
            st.setString(8, obj.getCredit());
            st.setString(9, obj.getAccType());
            st.setString(10, obj.getPCode());
            
           
            st.execute();
           
        } else {
            
            String sql = "UPDATE "+TABAL+" SET AccName = ?, L1Type = ?, L2Type = ?, L3Type = ?, AccType = ?, PCode = ? WHERE AccCode = '" + obj.getAccCode() + "'";
            //OPBalance = ?, Debit = ?, Credit = ?, 
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getAccName());
            st.setString(2, obj.getL1Type());
            st.setString(3, obj.getL2Type());
            st.setString(4, obj.getL3Type());
//            st.setString(5, obj.getOPBalance());
//            st.setString(6, obj.getDebit());
//            st.setString(7, obj.getCredit());
            st.setString(5, obj.getAccType());
            st.setString(6, obj.getPCode());
            
            st.executeUpdate();
        }
    }

    static OBJAccountCreation getNavi(Connection conn, int Ix) throws SQLException {

        OBJAccountCreation obj = null;
        //SQL Server
        //String sql = "SELECT TOP ("+Ix+") * FROM "+TABAL+" WHERE Type = 'U' ORDER BY CustCode ASC";
        
        //My Sql   
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" WHERE Type = 'C' AND Status = '0' ORDER BY CustCode ASC LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJAccountCreation(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10));
        }

        return obj;
    }

    static OBJAccountCreation serch(Connection conn, String code) throws SQLException {

        OBJAccountCreation obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE AccCode = '" + code + "' AND Status = '0' LIMIT 1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJAccountCreation(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM "+TABAL+" WHERE Status = '0'";

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

    static String genID(Connection conn, String l1, String l2, String l3) throws SQLException {
        String ID = "100000";
        String sql = "SELECT AccCode FROM "+TABAL+" WHERE L1Type = '"+l1+"' AND L2Type = '"+l2+"' AND L3Type = '"+l3+"' ORDER BY AccCode";

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

    static ArrayList<OBJAccountCreation> loadTbl(Connection conn) throws SQLException {
         ArrayList <OBJAccountCreation> obja = new ArrayList<OBJAccountCreation>();
         OBJAccountCreation obj = null;
         
         String qry = "SELECT * FROM "+TABAL+" WHERE status = '0'"; 
         PreparedStatement p = conn.prepareStatement(qry);
         p.execute();
         ResultSet r = p.getResultSet();
         while (r.next()) {            
            obj = new OBJAccountCreation(
                    r.getString(1),
                    r.getString(2),
                    r.getString(3),
                    r.getString(4),
                    r.getString(5),
                    r.getString(6),
                    r.getString(7),
                    r.getString(8),
                    r.getString(9),
                    r.getString(10));
            obja.add(obj);
        }
         return obja;
    }

}
