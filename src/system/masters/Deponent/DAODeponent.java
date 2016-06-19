/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.Deponent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAODeponent {
  //set Table
    private static final String TABAL = "Deponent";
    
    static void save(OBJDeponent obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" ( DepCode, DepName, AreaCode, Address1, Address2, Address3, TelNo, FaxNo, TargetAmt, POBox, Email, id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getName());
            st.setString(3, obj.getArea());
            st.setString(4, obj.getAdd1());
            st.setString(5, obj.getAdd2());
            st.setString(6, obj.getAdd3());
            st.setString(7, obj.getTellOff());
            st.setString(8, obj.getFax());
            st.setString(9, obj.getTarget());
            st.setString(10, obj.getMobi());
            st.setString(11, obj.getEmail());
            st.setString(12, obj.getId());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET  Email = ?, DepName = ?, AreaCode = ?, Address1 = ?, Address2 = ?, Address3 = ?, TelNo = ?, FaxNo = ?, TargetAmt = ?, POBox = ?, id = ? WHERE DepCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);            
            st.setString(1, obj.getEmail());
            st.setString(2, obj.getName());
            st.setString(3, obj.getArea());
            st.setString(4, obj.getAdd1());
            st.setString(4, obj.getAdd2());
            st.setString(4, obj.getAdd3());
            st.setString(5, obj.getPbox());
            st.setString(6, obj.getTellOff());
            st.setString(7, obj.getFax());
            st.setString(8, obj.getTarget());
            st.setString(9, obj.getMobi());
            st.setString(10, obj.getId());

            st.executeUpdate();
        }
    }

    static OBJDeponent getNavi(Connection conn, int Ix) throws SQLException {

        OBJDeponent obj = null;
        // SQL Server
        // String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY ItemCode ASC";

        //MySql  
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" WHERE Status = '0' ORDER BY DepCode ASC LIMIT "+Ix+",1";
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJDeponent(
                    result.getString("DepCode"),
                    result.getString("id"),
                    result.getString("DepName"),
                    result.getString("AreaCode"),
                    result.getString("Address1"),
                    result.getString("Address2"),
                    result.getString("Address3"),
                    result.getString("POBox"),
                    result.getString("TelNo"),
                    result.getString("FaxNo"),
                    "",
                    result.getString("POBox"),
                    result.getString("TargetAmt"));
        }
        return obj;
    }

    static OBJDeponent serch(Connection conn, String code) throws SQLException {

        OBJDeponent obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE DepCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJDeponent(
                    result.getString("DepCode"),
                    result.getString("id"),
                    result.getString("DepName"),
                    result.getString("AreaCode"),
                    result.getString("Address1"),
                    result.getString("Address2"),
                    result.getString("Address3"),
                    result.getString("POBox"),
                    result.getString("TelNo"),
                    result.getString("FaxNo"),
                    "",
                    result.getString("POBox"),
                    result.getString("TargetAmt"));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT * FROM "+TABAL+"";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            index++;
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE DepCode = '" + code + "'");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "01";
        String sql = "SELECT DepCode FROM "+TABAL+" ORDER BY DepCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("DepCode");
            int i = Integer.parseInt(cd);
            i++;
            cd = i+"";
            i = cd.length();
            for (int j = i; j < 2; j++) {
                cd = "0"+cd;
            }
            ID = cd;
        }
        return ID;
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
}
