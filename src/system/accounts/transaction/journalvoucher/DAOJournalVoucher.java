/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.journalvoucher;

import accountpackage.AccountPackage;
import core.system_transaction.transaction.TransactionStatus;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import system.accounts.transaction.journalvoucher.cancel.JVHeader;

/**
 *
 * @author RoWi
 */
class DAOJournalVoucher {

    static void save(OBJJournalEntry journalEntry, Connection con) throws SQLException {
        String sql = "INSERT INTO journal (journal_no, journal_date, remark, amount, user, status) VALUES (?,?,?,?,?,?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, journalEntry.getJournalEntryNo());
        st.setString(2, journalEntry.getJournalEntryDate());
        st.setString(3, journalEntry.getRemark());
        st.setString(4, journalEntry.getAmount());
        st.setString(5, accountpackage.AccountPackage.user);
        st.setString(6, JournalStatus.ACTIVE);
        st.execute();

        for (OBJEntry obj : journalEntry.getEntrys()) {
            sql = "INSERT INTO journal_history ( journal, account, narration, debit_amount, credit_amount, status) VALUES (?,?,?,?,?,?)";
            st = con.prepareStatement(sql);
            st.setString(1, journalEntry.getJournalEntryNo());
            st.setString(2, obj.getAccount());
            st.setString(3, obj.getNaration());
            st.setString(4, obj.getDebitAmount());
            st.setString(5, obj.getCreditAmount());
            st.setString(6, JournalStatus.ACTIVE);
            st.execute();
        }
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "JOV000001";
        String sql = "SELECT journal_no FROM journal ORDER BY journal_no DESC LIMIT 1";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        int i = 0;
        if (result.next()) {
            ID = result.getString("journal_no");
            ID = ID.substring(4, 9);
            i = Integer.parseInt(ID);
        }
        i++;
        ID = i + "";
        i = ID.length();
        for (int j = i; j < 6; j++) {
            ID = "0" + ID;
        }

        return "JOV" + ID;
    }

    static ArrayList<JVHeader> getJournals(Connection conn) throws SQLException {
        ArrayList<JVHeader> headers = new ArrayList<>();
        String sql = "SELECT journal_no, journal_date, remark, amount FROM journal WHERE journal_date = '" + AccountPackage.company.getWorkingDate() + "' AND status = '" + TransactionStatus.ACTIVE + "' ORDER BY journal_no DESC";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        if (result.next()) {
            JVHeader header = new JVHeader();
            header.setJVNo(result.getString("journal_no"));
            header.setJVDate(result.getString("journal_date"));
            header.setRemark(result.getString("remark"));
            header.setAmount(result.getString("amount"));
            headers.add(header);
        }
        return headers;
    }

    static void doCancel(String code, Connection conn) throws SQLException {
        CallableStatement cstmt = null;
        // Journal Cancel Structprocedure
        String SQL = "{call z_journal_cancel ('" + code + "')}";
        cstmt = conn.prepareCall(SQL);
        cstmt.executeQuery();
    }

    static OBJJournalEntry search(String code, Connection conn) throws SQLException {
        OBJJournalEntry journalEntry = new OBJJournalEntry();
        String sql = "SELECT * FROM journal LEFT JOIN journal_history ON journal_history.journal = journal.journal_no WHERE journal.journal_date = '" + AccountPackage.company.getWorkingDate() + "' AND journal.status = '" + TransactionStatus.ACTIVE + "' ORDER BY journal_no DESC";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            
            OBJEntry entry = new OBJEntry();
            journalEntry.setJournalEntryNo(result.getString("journal_no"));
            journalEntry.setJournalEntryDate(result.getString("journal_date"));
            journalEntry.setRemark(result.getString("remark"));
            journalEntry.setAmount(result.getString("amount"));
            
            entry.setAccount(result.getString("account"));
            entry.setNaration(result.getString("narration"));
            entry.setDebitAmount(result.getString("debit_amount"));
            entry.setCreditAmount(result.getString("credit_amount"));
            
            journalEntry.getEntrys().add(entry);
        }
        return journalEntry;
    }
}
