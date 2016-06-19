/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Rabid
 */
public class DAOCommen {

   public static String getDescription(String tbl, String whr, String Code, String Descrip, Connection con) throws SQLException {
        String name = "";
        String qry = "SELECT "+Descrip+" FROM "+tbl+" WHERE "+Code+" = '"+whr+"' LIMIT 1";
        PreparedStatement prep = con.prepareStatement(qry);
        ResultSet r = prep.executeQuery();
        while(r.next()){
            name = r.getString(Descrip);
        }
        prep.close();
        return name;
    }
   
   public static String getDescription(String qry, String key, Connection con) throws SQLException {
        String name = "";
        PreparedStatement prep = con.prepareStatement(qry);
        ResultSet r = prep.executeQuery();
        while(r.next()){
            name = r.getString(key);
        }
        prep.close();
        return name;
    }
    
}
