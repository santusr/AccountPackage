/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.realization.paymennts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import system.accounts.transaction.cheque.OBJCheque;

/**
 *
 * @author Rabid
 */
class DAORealizationPayment {
final static String TABALHISTORY = "cheque";
    static ArrayList<OBJCheque> getContent(Connection conn, String r) throws SQLException {
         ArrayList<OBJCheque> obja = new ArrayList<OBJCheque>();
        OBJCheque obj = null;
        String sql = "SELECT * FROM " + TABALHISTORY + " WHERE Status != '1' AND "+r+"";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJCheque(
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
                    result.getString(11),
                    result.getString(12),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(13),
                    result.getString(18),
                    result.getString(20),
                    result.getString(21),
                    result.getString(22));
            obja.add(obj);
        }

        return obja;
    }
    
}
