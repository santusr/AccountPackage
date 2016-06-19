/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.journalvoucher;

import core.Exp;
import core.system_transaction.TRSAccount;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import system.accounts.transaction.journalvoucher.cancel.JVHeader;

/**
 *
 * @author RoWi
 */
public class SERJournalVoucher {

    static boolean save(
            OBJJournalEntry journalEntry,
            OBJTransaction transaction,
            OBJPayment payment,
            ArrayList<OBJPaymentInfo> paymentsInfo,
            ArrayList<OBJAccountTrans> accountTranses,
            ArrayList<OBJItemTransaction> itemTransactions) {
        try {
            Connection con = accountpackage.AccountPackage.connect();
            con.setAutoCommit(false);
            DAOJournalVoucher.save(journalEntry, con);
            TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, con);
            con.commit();
            return true;
        } catch (SQLException e) {
            core.Exp.Handle(e);
            return false;
        }
    }

    static String getID() {
        String ID = "JOV000001";
        try {
            try (Connection conn = accountpackage.AccountPackage.connect()) {
                ID = DAOJournalVoucher.genID(conn);
            }
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
        return ID;
    }

    public static ArrayList<JVHeader> getJournals() {
        ArrayList<JVHeader> headers = new ArrayList<>();
        try {
            try (Connection conn = accountpackage.AccountPackage.connect()) {
                headers = DAOJournalVoucher.getJournals(conn);
            }
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
        return headers;
    }

    public static boolean doCancel(String code) {
        Connection conn = accountpackage.AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOJournalVoucher.doCancel(code, conn);
            conn.commit();
            return true;
        } catch (SQLException e) {
            Exp.Handle(e);
            return false;
        }
    }

    static OBJJournalEntry search(String code) {
        Connection conn = accountpackage.AccountPackage.connect();
        OBJJournalEntry journalEntry = new OBJJournalEntry();
        try {
            conn.setAutoCommit(false);
            journalEntry = DAOJournalVoucher.search(code, conn);
            conn.commit();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return journalEntry;
    }

}
