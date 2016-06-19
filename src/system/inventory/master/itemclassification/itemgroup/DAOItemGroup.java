/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.itemclassification.itemgroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAOItemGroup {
//set Table
    private static final String TABAL = "ItemGroup";
    
    static void save(OBJItemGroup obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" ( GroupCode, ShortName, Description) VALUES (?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getName());
            st.setString(3, obj.getCatname());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET ShortName = ?, Description = ? WHERE GroupCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getCatname());

            st.executeUpdate();
        }
    }

    static OBJItemGroup getNavi(Connection conn, int Ix) throws SQLException {

        OBJItemGroup obj = null;
        
        // SQL Server
        // String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY GroupCode ASC";

        //MySql  
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" WHERE Status = '0' ORDER BY GroupCode ASC LIMIT "+Ix+",1";
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJItemGroup(
                    result.getString(1),
                    result.getString(3),
                    result.getString(2));
        }

        return obj;
    }

    static OBJItemGroup serch(Connection conn, String code) throws SQLException {

        OBJItemGroup obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE GroupCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJItemGroup(
                    result.getString(1),
                    result.getString(3),
                    result.getString(2));
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
            index = result.getInt("val");
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE GroupCode = '" + code + "'");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "01";
        String sql = "SELECT GroupCode FROM "+TABAL+" ORDER BY GroupCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("GroupCode");
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
    }

