/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.bankmaster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAOBankMaster {
   //set Table
    private static final String TABAL = "Bank";
    
    static void save(OBJBankMaster obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" ( BankCode, BankName, Branch, ContactPerson, PoBox, Address, TelNo, FaxNo, Email) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getName());
            st.setString(3, obj.getBranch());
            st.setString(4, obj.getContperson());
            st.setString(5, obj.getPbox());
            st.setString(6, obj.getAddress());
            st.setString(7, obj.getTel());
            st.setString(8, obj.getFax());
            st.setString(9, obj.getEmail());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET BankName = ?, Branch = ?, ContactPerson = ?, PoBox = ?, Address = ?, TelNo = ?, FaxNo = ?, Email = ? WHERE BankCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getBranch());
            st.setString(3, obj.getContperson());
            st.setString(4, obj.getPbox());
            st.setString(5, obj.getAddress());
            st.setString(6, obj.getTel());
            st.setString(7, obj.getFax());
            st.setString(8, obj.getEmail());
            st.executeUpdate();
        }
    }

    static OBJBankMaster getNavi(Connection conn, int Ix) throws SQLException {

        OBJBankMaster obj = null;
        //SQL Server
        //String sql = "SELECT TOP (" + Ix + ") * FROM "+TABAL+" ORDER BY BankCode ASC";
        
        //MySql
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" ORDER BY BankCode ASC LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJBankMaster(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9));
        }

        return obj;
    }

    static OBJBankMaster serch(Connection conn, String code) throws SQLException {

        OBJBankMaster obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE BankCode = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJBankMaster(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9));
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
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE BankCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE BankCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "01";
        String sql = "SELECT BankCode FROM "+TABAL+" ORDER BY BankCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("BankCode");
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
