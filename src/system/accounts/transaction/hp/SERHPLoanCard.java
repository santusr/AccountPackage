/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.hp;

import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RoWi
 */
public class SERHPLoanCard {

    static ArrayList<OBJHPLoanCard> getCredit() {
        ArrayList<OBJHPLoanCard> il = null;
        try {
            Connection con = accountpackage.AccountPackage.connect();
            con.setAutoCommit(false);
            il = DAOHPLoanCard.getCredit(con);
            con.commit();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return il;
    }
    
    static ArrayList<OBJHPLoanCard> getCreditLike(String key) {
        ArrayList<OBJHPLoanCard> il = null;
        try {
            Connection con = accountpackage.AccountPackage.connect();
            con.setAutoCommit(false);
            il = DAOHPLoanCard.getCreditLike(key, con);
            con.commit();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return il;
    }

    static ArrayList<OBJSchedule> getSchedule(String invoiceNo) {
        ArrayList<OBJSchedule> schedules = null;
        try {
            Connection con = accountpackage.AccountPackage.connect();
            con.setAutoCommit(false);
            schedules = DAOHPLoanCard.getSchedule(invoiceNo, con);
            con.commit();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return schedules;
    }

}
