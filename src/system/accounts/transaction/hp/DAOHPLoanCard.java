/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.hp;

import core.system_transaction.payment.multy_payment.VoucherType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RoWi
 */
class DAOHPLoanCard {

    static ArrayList<OBJHPLoanCard> getCredit(Connection con) throws SQLException {
        ArrayList<OBJHPLoanCard> il = new ArrayList<OBJHPLoanCard>();
        OBJHPLoanCard obj = null;
        String sql = "SELECT \n"
                + "				credit.CreditId,\n"
                + "				credit.InvoNo,\n"
                + "				invoiceheader.loan_no,\n"
                + "				invoiceheader.CustCode,\n"
                + "				customer.PrintName,\n"
                + "				credit.InvoType,\n"
                + "				credit.NoInstal,\n"
                + "				credit.AGDate,\n"
                + "				credit.isPanaltyCal,\n"
                + "				invoiceheader.NetAmount,\n"
                + "				invoiceheader.PayAmount,\n"
                + "				credit.InterestRate,\n"
                + "				credit.TotInterest,\n"
                + "				credit.LateCharge,\n"
                + "				credit.SPDisc,\n"
                + "				(credit.InvoCredit + credit.TotInterest + credit.LateCharge - credit.SPDisc) AS totalPayble,\n"
                + "				credit.TotPay,\n"
                + "				credit.TotPayble,\n"
                + "				credit.InstalValue,\n"
                + "				vou.VouDate\n"
                + "FROM \n"
                + "				credit\n"
                + "				LEFT JOIN invoiceheader ON invoiceheader.InvoNo = credit.InvoNo\n"
                + "				LEFT JOIN customer ON customer.AccCode = credit.custCode\n"
                + "				LEFT JOIN (SELECT \n"
                + "										voucher.VouDate,\n"
                + "										voucher.InvoNo\n"
                + "								FROM \n"
                + "										voucher \n"
                + "								WHERE \n"
                + "										voucher.status = 'ACTIVE' AND \n"
                + "										voucher.type = '" + VoucherType.RECEIPT + "' \n"
                + "								ORDER BY \n"
                + "										voucher.VouDate\n"
                + "										DESC\n"
                + "								LIMIT 1								 \n"
                + "								) vou\n"
                + "				ON vou.InvoNo = credit.InvoNo\n"
                + "WHERE \n"
                + "				credit.`status` = '0'	";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJHPLoanCard();
            obj.setCreditId(result.getString("CreditId"));
            obj.setInvoiceNo(result.getString("InvoNo"));
            obj.setLoanNo(result.getString("Loan_no"));
            obj.setCustomerCode(result.getString("CustCode"));
            obj.setCustomerName(result.getString("printName"));
            obj.setInvoiceType(result.getString("invoType"));
            obj.setNoOfInstallment(result.getString("NoInstal"));
            obj.setAgreementDate(result.getString("AGDate"));
            obj.setIsPanaltyCal(result.getString("isPanaltyCal"));
            obj.setInvoiceAmount(result.getString("netAmount"));
            obj.setDownPayment(result.getString("payAmount"));
            obj.setInterestRate(result.getString("InterestRate"));
            obj.setTiotalInterest(result.getString("TotInterest"));
            obj.setPanalty(result.getString("LateCharge"));
            obj.setRebit(result.getString("SPDisc"));
            obj.setTotalPayble(result.getString("totalPayble"));
            obj.setPayment(result.getString("totPay"));
            obj.setDueAmount(result.getString("TotPayble"));
            obj.setInstallAmount(result.getString("InstalValue"));
            obj.setLED(result.getString("VouDate"));
            il.add(obj);
        }
        return il;

    }
    
    static ArrayList<OBJHPLoanCard> getCreditLike(String key, Connection con) throws SQLException {
        ArrayList<OBJHPLoanCard> il = new ArrayList<OBJHPLoanCard>();
        OBJHPLoanCard obj = null;
        String sql = "SELECT \n"
                + "				credit.CreditId,\n"
                + "				credit.InvoNo,\n"
                + "				invoiceheader.loan_no,\n"
                + "				invoiceheader.CustCode,\n"
                + "				customer.PrintName,\n"
                + "				credit.InvoType,\n"
                + "				credit.NoInstal,\n"
                + "				credit.AGDate,\n"
                + "				credit.isPanaltyCal,\n"
                + "				invoiceheader.NetAmount,\n"
                + "				invoiceheader.PayAmount,\n"
                + "				credit.InterestRate,\n"
                + "				credit.TotInterest,\n"
                + "				credit.LateCharge,\n"
                + "				credit.SPDisc,\n"
                + "				(credit.InvoCredit + credit.TotInterest + credit.LateCharge - credit.SPDisc) AS totalPayble,\n"
                + "				credit.TotPay,\n"
                + "				credit.TotPayble,\n"
                + "				credit.InstalValue,\n"
                + "				vou.VouDate\n"
                + "FROM \n"
                + "				credit\n"
                + "				LEFT JOIN invoiceheader ON invoiceheader.InvoNo = credit.InvoNo\n"
                + "				LEFT JOIN customer ON customer.AccCode = credit.custCode\n"
                + "				LEFT JOIN (SELECT \n"
                + "										voucher.VouDate,\n"
                + "										voucher.InvoNo\n"
                + "								FROM \n"
                + "										voucher \n"
                + "								WHERE \n"
                + "										voucher.status = 'ACTIVE' AND \n"
                + "										voucher.type = '" + VoucherType.RECEIPT + "' \n"
                + "								ORDER BY \n"
                + "										voucher.VouDate\n"
                + "										DESC\n"
                + "								LIMIT 1								 \n"
                + "								) vou\n"
                + "				ON vou.InvoNo = credit.InvoNo\n"
                + "WHERE \n"
                + "				invoiceheader.loan_no LIKE '" + key + "%' OR \n"
                + "				customer.PrintName LIKE '" + key + "%' OR \n"
                + "				credit.InvoNo LIKE '" + key + "%' OR \n"
                + "				invoiceheader.CustCode LIKE '" + key + "%' AND \n"
                + "				credit.`status` = '0'	";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJHPLoanCard();
            obj.setCreditId(result.getString("CreditId"));
            obj.setInvoiceNo(result.getString("InvoNo"));
            obj.setLoanNo(result.getString("Loan_no"));
            obj.setCustomerCode(result.getString("CustCode"));
            obj.setCustomerName(result.getString("printName"));
            obj.setInvoiceType(result.getString("invoType"));
            obj.setNoOfInstallment(result.getString("NoInstal"));
            obj.setAgreementDate(result.getString("AGDate"));
            obj.setIsPanaltyCal(result.getString("isPanaltyCal"));
            obj.setInvoiceAmount(result.getString("netAmount"));
            obj.setDownPayment(result.getString("payAmount"));
            obj.setInterestRate(result.getString("InterestRate"));
            obj.setTiotalInterest(result.getString("TotInterest"));
            obj.setPanalty(result.getString("LateCharge"));
            obj.setRebit(result.getString("SPDisc"));
            obj.setTotalPayble(result.getString("totalPayble"));
            obj.setPayment(result.getString("totPay"));
            obj.setDueAmount(result.getString("TotPayble"));
            obj.setInstallAmount(result.getString("InstalValue"));
            obj.setLED(result.getString("VouDate"));
            il.add(obj);
        }
        return il;

    }

    static ArrayList<OBJSchedule> getSchedule(String invoiceNo, Connection con) throws SQLException {
        ArrayList<OBJSchedule> il = new ArrayList<OBJSchedule>();
        OBJSchedule obj = null;
        String sql = "SELECT \n"
                + "				payschedule.DueDate, \n"
                + "				payschedule.installNo,\n"
                + "				SUM(payschedule.PayAmount) as PayAmount,\n"
                + "				SUM(payschedule.Balance) as Balance,\n"
                + "				payschedule.`type`\n"
                + "FROM \n"
                + "				payschedule \n"
                + "					LEFT JOIN credit ON credit.InvoNo = payschedule.invoNo\n"
                + "WHERE \n"
                + "				payschedule.invoNo = '"+invoiceNo+"' AND \n"
                + "				credit.`status` = '0' AND \n" +
"				payschedule.`Status` <> '3' \n"
                + "GROUP BY payschedule.`type`, payschedule.DueDate";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJSchedule();
            obj.setDueDate(result.getString("DueDate"));
            obj.setInstallNo(result.getString("installNo"));
            obj.setPaid(result.getString("PayAmount"));
            obj.setBalance(result.getString("Balance"));
            obj.setType(result.getString("type"));
            il.add(obj);
        }
        return il;
    }

}
