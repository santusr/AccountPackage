/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAOCurrency {

    static void save(OBJCurrency currency, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO FCurrency (FCCode, Description, Factor) VALUES (?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, currency.getCode());
            st.setString(2, currency.getName());
            st.setString(3, currency.getFactore());
            st.execute();
            
        } else {
            String sql = "UPDATE FCurrency SET Description = ?, Factor = ? WHERE FCCode = '" + currency.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, currency.getName());
            st.setString(2, currency.getFactore());

            st.executeUpdate();
        }
    }

    static OBJCurrency getCurrency(Connection conn, int Ix) throws SQLException {

        OBJCurrency currency = null;
        //SQL Server
        //String sql = "SELECT TOP("+Ix+") * FROM FCurrency ORDER BY FCCode";
        
        //MySql
        //Ix =-1;
        String sql = "SELECT * FROM FCurrency ORDER BY FCCode LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            currency = new OBJCurrency(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3));
        }

        return currency;
    }

    static OBJCurrency serchCurrency(Connection conn, String code) throws SQLException {

        OBJCurrency currency = null;
        String sql = "SELECT * FROM FCurrency WHERE FCCode = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            currency = new OBJCurrency(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3));
        }

        return currency;
    }

    static int getCurrency(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM FCurrency";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            //index++;
            index = result.getInt("val");
        }
        return index;
    }

    static void deleteCurrency(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM FCurrency WHERE FCCode = '" + code + "'");
        String sql = "UPDATE FCurrency SET Status = ? WHERE FCCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException{
        String ID = "001";
        String sql = "SELECT FCCode FROM FCurrency ORDER BY FCCode";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("FCCode");
            int i = Integer.parseInt(cd);
            i++;
            cd = i+"";
            i = cd.length();
            for (int j = i; j < 3; j++) {
                cd = "0"+cd;
            }
            ID = cd;
        }
        return ID;
    }
    }

