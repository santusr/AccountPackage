/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.voucher_cancel;

import core.system_transaction.transaction.TransactionStatus;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import system.accounts.transaction.receiptvoucher.againstinvoice.DAOInvoList;
import system.accounts.transaction.receiptvoucher.againstinvoice.OBJPaymentSchedule;

/**
 *
 * @author RoWi
 */
class DAOVoucherCancel {

    static ArrayList<OBJVoucher> getVouchers(String transDate, String transactionType, String voucherNo, Connection con) throws SQLException {
        ArrayList<OBJVoucher> vouchers = new ArrayList<>();
        String sql = "SELECT \n"
                + "				voucher.VoucherNo, \n"
                + "				voucher.InvoNo, \n"
                + "				voucher.Cust, \n"
                + "				voucher.VouDate, \n"
                + "				voucher.CostCenter, \n"
                + "				voucher.Narration, \n"
                + "				voucher.PayAmount,\n"
                + "				transaction.document_no,\n"
                + "				transaction.loan,\n"
                + "				transaction.index_no,\n"
                + "				account_trans.account,\n"
                + "				account_trans.description,\n"
                + "				account_trans.credit_amount,\n"
                + "				account_trans.debit_amount\n"
                + "FROM \n"
                + "				voucher\n"
                + "				LEFT JOIN transaction ON `transaction`.referance_no = voucher.VoucherNo\n"
                + "				LEFT JOIN account_trans ON account_trans.`transaction` = transaction.index_no\n"
                + "WHERE \n"
                + "				VouDate = '" + transDate + "' AND \n"
                + "				voucher.type = '" + transactionType + "' AND\n"
                + "				transaction.status = '" + TransactionStatus.ACTIVE + "' AND \n"
                + "                             voucher.VoucherNo = '" + voucherNo + "'";

        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            OBJVoucher voucher = new OBJVoucher();
            voucher.setVoucherNo(result.getString("VoucherNo"));
            voucher.setInvoiceNo(result.getString("InvoNo"));
            voucher.setCustCode(result.getString("Cust"));
            voucher.setVoucherDate(result.getString("VouDate"));
            voucher.setCostCenter(result.getString("CostCenter"));
            voucher.setNarration(result.getString("Narration"));
            voucher.setAmount(result.getString("PayAmount"));
            voucher.setDocumentNo(result.getString("document_no"));
            voucher.setLoan(result.getString("loan"));
            voucher.setTransaction(result.getString("index_no"));
            voucher.setAccount(result.getString("account"));
            voucher.setDescription(result.getString("description"));
            voucher.setCreditAmount(result.getString("credit_amount"));
            voucher.setDebitAmount(result.getString("debit_amount"));
            vouchers.add(voucher);
        }
        return vouchers;
    }

    static ArrayList<OBJVoucher> getVoucherHeaders(String transDate, String transactionType, Connection con) throws SQLException {
        ArrayList<OBJVoucher> vouchers = new ArrayList<>();
        String sql = "SELECT \n"
                + "				voucher.VoucherNo, \n"
                + "				voucher.InvoNo, \n"
                + "				voucher.Cust, \n"
                + "				voucher.VouDate, \n"
                + "				voucher.CostCenter, \n"
                + "				voucher.Narration, \n"
                + "				voucher.PayAmount,\n"
                + "				transaction.document_no,\n"
                + "				transaction.loan,\n"
                + "				transaction.index_no\n"
                + "FROM \n"
                + "				voucher \n"
                + "				LEFT JOIN transaction ON `transaction`.referance_no = voucher.VoucherNo \n"
                + "WHERE \n"
                + "				VouDate = '" + transDate + "' AND \n"
                + "				voucher.type = '" + transactionType + "' AND\n"
                + "				transaction.status = '" + TransactionStatus.ACTIVE + "'";

        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            OBJVoucher voucher = new OBJVoucher();
            voucher.setVoucherNo(result.getString("VoucherNo"));
            voucher.setInvoiceNo(result.getString("InvoNo"));
            voucher.setCustCode(result.getString("Cust"));
            voucher.setVoucherDate(result.getString("VouDate"));
            voucher.setCostCenter(result.getString("CostCenter"));
            voucher.setNarration(result.getString("Narration"));
            voucher.setAmount(result.getString("PayAmount"));
            voucher.setDocumentNo(result.getString("document_no"));
            voucher.setLoan(result.getString("loan"));
            voucher.setTransaction(result.getString("index_no"));
            vouchers.add(voucher);
        }
        return vouchers;
    }

    static void doCancelTrans(String code, String transType, Connection conn) throws SQLException {
        CallableStatement cstmt = null;
        // Journal Cancel Structprocedure
        String SQL = "{call z_voucher_cancel ('" + code + "', '" + transType + "')}";
        cstmt = conn.prepareCall(SQL);
        cstmt.executeQuery();
    }

    static void doRallBackCreditGRN(String invoNo, String transType, String amount, Connection con) throws SQLException {
        String sql = "UPDATE creditgrn SET TotPayble = TotPayble + ?, TotPay = TotPay - ?, status = ? WHERE invoNo = '" + invoNo + "' AND Status <> '2'";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, amount);
            st.setString(2, amount);
            st.setString(3, "0");
            st.execute();
        }
    }

    static void doRallBackCredit(String invoNo, String transType, String amount, Connection con) throws SQLException {
        String sql = "UPDATE credit SET TotPayble = TotPayble + '" + amount + "', TotPay = TotPay - '" + amount + "', status = '0' WHERE invoNo = '" + invoNo + "' AND Status <> '2'";

        PreparedStatement st = con.prepareStatement(sql);
        st.executeUpdate();
        //TODO : PaymentSchedule Update;
        ArrayList<OBJPaymentSchedule> il = DAOInvoList.getScheduleRev(invoNo, "", con);
        il = paymentQue(il, amount);

        for (OBJPaymentSchedule schedule : il) {
            sql = "UPDATE payschedule SET PayAmount = '" + schedule.getPaid() + "', Balance = '" + schedule.getBalance() + "', status = '0' WHERE invoNo = '" + invoNo + "' AND CreditId = '" + schedule.getCreditId() + "'";
            st = con.prepareStatement(sql);
            st.executeUpdate();
        }
    }

    private static ArrayList<OBJPaymentSchedule> paymentQue(ArrayList<OBJPaymentSchedule> il, String samount) {
        double amount = Double.parseDouble(samount);
        for (OBJPaymentSchedule schedule : il) {
            double pay = Double.parseDouble(schedule.getPaid());
            if (amount > 0) {
                if (pay > 0) {
                    if (amount > pay) {
                        schedule.setBalance((Double.parseDouble(schedule.getBalance()) + pay) + "");
                        schedule.setPaid("0.00");
                        amount = amount - pay;
                    } else {
                        schedule.setBalance((Double.parseDouble(schedule.getBalance()) + amount) + "");
                        schedule.setPaid((pay - amount) + "");
                        amount = 0.00;
                    }
                }
            }
        }
        return il;
    }

}
