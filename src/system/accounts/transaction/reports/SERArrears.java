/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.reports;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
class SERArrears {

    static ArrayList<OBJArrears> loadList(String date) {
        ArrayList <OBJArrears> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOArrears.loadList(conn, date);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
    
}
