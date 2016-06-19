/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.reports;

import core.Locals;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mainApp.DAOCommen;

/**
 *
 * @author Administrator
 */
class DAOArrears {

    static ArrayList<OBJArrears> loadList(Connection conn, String date) throws SQLException {
        
        ArrayList<OBJArrears> obja = new ArrayList<OBJArrears>();
        OBJArrears obj;
        String sql = "SELECT * FROM payschedule, Credit WHERE payschedule.Status = '0' AND payschedule.DueDate = '"+date+"' AND payschedule.CreditId = Credit.CreditId  ORDER BY CustCode";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String invoNo = result.getString("invoNo");
            
            double d = result.getDouble("totInstallval");
            int ins = result.getInt("ID");
            double d1 = 0.00;
            ins = ins - 1;
            String s = ins+"";
            
            String in = DAOCommen.getDescription("payschedule", s, "ID", "InvoNo", conn);
            
            if (ins > 0 && in.equals(invoNo)) {
                d1 = Double.parseDouble(DAOCommen.getDescription("payschedule", s, "ID", "Balance", conn));
            }
           
            
            String CID = DAOCommen.getDescription("payschedule", invoNo, "InvoNo", "CreditID", conn);
            String AccCode = DAOCommen.getDescription("Credit", CID, "CreditId", "CustCode", conn);
            String name = DAOCommen.getDescription("customer", AccCode, "AccCode", "CustName", conn);
            String Code = DAOCommen.getDescription("customer", AccCode, "AccCode", "CustCode", conn);
            
            obj = new OBJArrears(
                    AccCode,
                    name + " ("+Code+")",
                    result.getString("invoNo"),
                    result.getString("installNo"),
                    Locals.currencyFormat(d),
                    result.getString("LatePay"),
                    Locals.currencyFormat(d+d1),
                    result.getString("DueDate"));
            obja.add(obj);
        }

        return obja;
    }
    
}
