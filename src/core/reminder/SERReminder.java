/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.reminder;

import accountpackage.AccountPackage;
import core.Exp;
import core.reminder.object.LowStockItem;
import core.reminder.object.UpcomingCheque;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RoWi
 */
public class SERReminder {

    static ArrayList<UpcomingCheque> voucherRealization() {
        try {
            ArrayList<UpcomingCheque> upcomingCheques = new ArrayList<>();
            Connection connection = AccountPackage.connect();
            upcomingCheques = DAOReminder.getVoucherRealize(connection);
            return upcomingCheques;
        } catch (SQLException e) {
            Exp.Handle(e);
            return null;
        }
    }

    static ArrayList<UpcomingCheque> receiptRealization() {
        try {
            ArrayList<UpcomingCheque> upcomingCheques = new ArrayList<>();
            Connection connection = AccountPackage.connect();
            upcomingCheques = DAOReminder.getReceiptRealize(connection);
            return upcomingCheques;
        } catch (SQLException e) {
            Exp.Handle(e);
            return null;
        }
    }

    static ArrayList<LowStockItem> lowStock() {
        try {
            ArrayList<LowStockItem> lowStockItems = new ArrayList<>();
            Connection connection = AccountPackage.connect();
            lowStockItems = DAOReminder.getLowStock(connection);
            return lowStockItems;
        } catch (SQLException e) {
            Exp.Handle(e);
            return null;
        }
    }
    
}
