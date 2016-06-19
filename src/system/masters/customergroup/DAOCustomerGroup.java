/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.customergroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAOCustomerGroup {
     //set Table
    private static final String TABAL = "customerGroup";
    
    static void save(OBJCustomerGroup obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" (CustGrpCode, CustGrpname, address1, address2, address3, pobox, teloff, faxno, MobileNo, Email, Id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getName());
            st.setString(3, obj.getAdd1());
            st.setString(4, obj.getAdd2());
            st.setString(5, obj.getAdd3());
            st.setString(6, obj.getPbox());
            st.setString(7, obj.getTel());
            st.setString(8, obj.getFax());
            st.setString(9, obj.getMobi());
            st.setString(10, obj.getEmail());
            st.setString(11, obj.getId());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET CustGrpname = ?, address1 = ?, address2 = ?, address3 = ?, pobox = ?, teloff = ?, faxno = ?, MobileNo = ?, Email = ?, Id = ? WHERE CustGrpCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getAdd1());
            st.setString(3, obj.getAdd2());
            st.setString(4, obj.getAdd3());
            st.setString(5, obj.getPbox());
            st.setString(6, obj.getTel());
            st.setString(7, obj.getFax());
            st.setString(8, obj.getMobi());
            st.setString(9, obj.getEmail());
            st.setString(10, obj.getId());
            st.executeUpdate();
        }
    }

    static OBJCustomerGroup getNavi(Connection conn, int Ix) throws SQLException {

        OBJCustomerGroup obj = null;
        //SQL Server
        //String sql = "SELECT TOP(" + Ix + ") * FROM "+TABAL+" ORDER BY CustGrpCode ASC";
        
        //MySql
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" ORDER BY CustGrpCode ASC LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJCustomerGroup(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(11));
        }

        return obj;
    }

    static OBJCustomerGroup serch(Connection conn, String code) throws SQLException {

        OBJCustomerGroup obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE CustGrpCode = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJCustomerGroup(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(11));
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

    static void delete(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE CustGrpCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE CustGrpCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "0001";
        String sql = "SELECT CustGrpCode FROM "+TABAL+" ORDER BY CustGrpCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("CustGrpCode");
            int i = Integer.parseInt(cd);
            i++;
            cd = i+"";
            i = cd.length();
            for (int j = i; j < 4; j++) {
                cd = "0"+cd;
            }
            ID = cd;
        }
        return ID;
    }
}
