/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.realization.paymennts;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import system.accounts.transaction.cheque.OBJCheque;

/**
 *
 * @author Rabid
 */
public class SERRealizationPayment {

    public static ArrayList<OBJCheque> getContent(String r) {
         ArrayList <OBJCheque> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAORealizationPayment.getContent(conn, r);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
    
}
