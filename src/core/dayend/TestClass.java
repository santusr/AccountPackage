/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.dayend;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RoWi
 */
public class TestClass {

    public static void main(String[] args) {
        try {
            Connection conn = accountpackage.AccountPackage.connect;
//            Connection conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            DayEnd.paneltyCalculate(conn);
            DayEnd.calculateDate(conn);
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DayEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
