/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.reminder;

import core.reminder.object.LowStockItem;
import core.reminder.object.UpcomingCheque;
import core.system_transaction.TransactionType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import system.accounts.transaction.cheque.ChequeStatus;

/**
 *
 * @author RoWi
 */
public class DAOReminder {

    public static ArrayList<LowStockItem> getLowStock(Connection con) throws SQLException {
        ArrayList<LowStockItem> lowStockItems = new ArrayList<>();
        String query = "SELECt DISTINCT itemmaster.*, item_transaction.index_no FROM itemmaster\n"
                + "		LEFT JOIN item_transaction ON itemmaster.ItemCode = item_transaction.item\n"
                + " 	WHERE "
                + "             item_transaction.transaction_type = '" + TransactionType.INVOICE + "' AND \n"
                + "             item_transaction.`status` = 'ACTIVE' AND \n"
                + "             itemmaster .StockInHand <= itemmaster.ReorderLevel\n"
                + " 	ORDER BY "
                + "             item_transaction.index_no\n"
                + " 	LIMIT 10";

        PreparedStatement p = con.prepareStatement(query);
        ResultSet r = p.executeQuery();

        while (r.next()) {
            LowStockItem lowStockItem = new LowStockItem();
            lowStockItem.setItem(r.getString("itemCode"));
            lowStockItem.setItemName(r.getString("shortName"));
            lowStockItem.setDescription(r.getString("description"));
            lowStockItem.setAvailable(r.getString("stockInHand"));

            lowStockItems.add(lowStockItem);
        }

        return lowStockItems;
    }

    public static ArrayList<UpcomingCheque> getReceiptRealize(Connection con) throws SQLException {

        ArrayList<UpcomingCheque> upcomingCheques = new ArrayList<>();
        String query = "SELECT \n"
                + "				cheque.ChqeNo AS cheque, \n"
                + "				customer.PrintName AS name, \n"
                + "				bank.BankName AS bank, \n"
                + "				cheque.RDate AS r_date, \n"
                + "				cheque.Amount AS amount\n"
                + "FROM \n"
                + "				cheque\n"
                + "LEFT JOIN \n"
                + "				customer ON customer.AccCode = cheque.CustCode\n"
                + "LEFT JOIN \n"
                + "				bank ON Bank.Bankcode = cheque.banck\n"
                + "WHERE \n"
                + "				cheque.CheqType IN ('"+TransactionType.GRN+"', '"+TransactionType.VOUCHER+"') AND \n"
                + "                             cheque.status = '"+ChequeStatus.PENDING+"'\n"
                + "ORDER BY \n"
                + "				cheque.RDate";

        PreparedStatement p = con.prepareStatement(query);
        ResultSet r = p.executeQuery();

        while (r.next()) {
            UpcomingCheque upcomingCheque = new UpcomingCheque();
            upcomingCheque.setCustomer(r.getString("name"));
            upcomingCheque.setChequeNo(r.getString("cheque"));
            upcomingCheque.setDate(r.getString("r_date"));
            upcomingCheque.setBank(r.getString("bank"));
            upcomingCheque.setAmount(r.getString("amount"));

            upcomingCheques.add(upcomingCheque);
        }

        return upcomingCheques;
    }

    public static ArrayList<UpcomingCheque> getVoucherRealize(Connection con) throws SQLException {

        ArrayList<UpcomingCheque> upcomingCheques = new ArrayList<>();
        String query = "SELECT \n"
                + "				cheque.ChqeNo AS cheque, \n"
                + "				customer.PrintName AS name, \n"
                + "				account.AccName AS bank, \n"
                + "				cheque.RDate AS r_date, \n"
                + "				cheque.Amount AS amount\n"
                + "FROM \n"
                + "				cheque\n"
                + "LEFT JOIN \n"
                + "				customer ON customer.AccCode = cheque.CustCode\n"
                + "LEFT JOIN \n"
                + "				account ON account.AccCode = cheque.bankAccount\n"
                + "WHERE \n"
                + "				cheque.CheqType IN ('"+TransactionType.INVOICE+"', '"+TransactionType.RECEIPT+"') AND "
                + "                             cheque.status = '"+ChequeStatus.PENDING+"'\n"
                + "ORDER BY \n"
                + "				cheque.RDate";

        PreparedStatement p = con.prepareStatement(query);
        ResultSet r = p.executeQuery();

        while (r.next()) {
            UpcomingCheque upcomingCheque = new UpcomingCheque();
            upcomingCheque.setCustomer(r.getString("name"));
            upcomingCheque.setChequeNo(r.getString("cheque"));
            upcomingCheque.setDate(r.getString("r_date"));
            upcomingCheque.setBank(r.getString("bank"));
            upcomingCheque.setAmount(r.getString("amount"));

            upcomingCheques.add(upcomingCheque);
        }

        return upcomingCheques;
    }
}
