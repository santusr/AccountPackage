/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.dayend;

import core.Locals;
import core.SystemSettings;
import core.system_transaction.SystemSetting;
import core.system_transaction.TRSAccount;
import core.system_transaction.TransactionType;
import core.system_transaction.account_trans.AccountSetting;
import core.system_transaction.account_trans.AccountSettingObject;
import core.system_transaction.account_trans.AccountTransStatus;
import core.system_transaction.account_trans.AccountTransactionDescription;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.transaction.OBJTransactionHistory;
import core.system_transaction.transaction.TransactionStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import system.inventory.transaction.sales.paymentplan.PayPlanStatus;

/**
 *
 * @author Rabid
 */
public class DayEnd {

    //Transaction Object
    private static OBJTransaction transaction;
    private static ArrayList<OBJTransactionHistory> historys;
    //Account Transaction object
    private static ArrayList<OBJAccountTrans> accountTranses;
    //Payment Object
    private static OBJPayment payment;
    private static ArrayList<OBJPaymentInfo> paymentsInfo;

    // Item Trans
    private static OBJItemTransaction itemTransaction;
    private static ArrayList<OBJItemTransaction> itemTransactions;


    /*
     +---Clear Customer Payments---+
     |                             |
     | (Calculate Late Pay Charge) |
     |                             |
     +-----------------------------+
     */
    public static void paneltyCalculate(Connection con) throws SQLException {
        OBJInvPayPlan plan = null;
        ArrayList<OBJInvPayPlan> payPlans = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Locals.toDate(accountpackage.AccountPackage.company.getWorkingDate()));
//        calendar.setTime(Locals.toDate("2015-03-08"));
        calendar.add(Calendar.DATE, (-1 * (SystemSettings.GRACE_DAYS)));
        String DueDate = Locals.setDateFormat(calendar.getTime());

        String qry = "SELECT\n"
                + "			payschedule.CreditId,\n"
                + "			payschedule.installNo,\n"
                + "			payschedule.install,\n"
                + "			payschedule.invoNo,\n"
                + "			invoiceheader.CostCode,\n"
                + "			invoiceheader.loan_no,\n"
                + "			credit.custCode,\n"
                + "			payschedule.Balance,\n"
                + "			payschedule.DueDate, \n"
                + "			payschedule.LatePay \n"
                + "FROM \n"
                + "			payschedule \n"
                + "LEFT JOIN  \n"
                + "			credit ON payschedule.CreditId = credit.CreditId\n"
                + "LEFT JOIN  \n"
                + "			invoiceheader ON invoiceheader.invoNo = credit.invoNo\n"
                + "WHERE \n"
                + "			credit.`status` = '0' AND\n"
                + "			payschedule.`Status`= '0' AND\n"
                + "			payschedule.DueDate <= '" + DueDate + "' AND\n"
                + "			credit.isPanaltyCal = 'Y' AND\n"
                + "			payschedule.type = '" + PayPlanStatus.INSTALMENT + "'";

        PreparedStatement st = con.prepareStatement(qry);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            plan = new OBJInvPayPlan();
            plan.setCreditId(result.getString("CreditId"));
            plan.setInstallNo(result.getString("installNo"));
            plan.setInstall(result.getString("install"));
            plan.setInvoNo(result.getString("invoNo"));
            plan.setCustCode(result.getString("custCode"));
            plan.setBalance(result.getString("Balance"));
            plan.setDueDate(result.getString("DueDate"));
            plan.setLatePay(result.getString("LatePay"));
            plan.setCostCode(result.getString("CostCode"));
            plan.setLoan(result.getString("loan_no"));
            payPlans.add(plan);
        }
        double balance = 0;
        double panalty = 0;

        // Transaction 
        paymentsInfo = null;
        payment = null;
        accountTranses = null;
        accountTranses = new ArrayList<>();

        // Item Teansactions
        itemTransactions = null;

        Calendar cal = Calendar.getInstance();

        cal.setTime(Locals.toDate(accountpackage.AccountPackage.company.getWorkingDate()));
        // Transaction Object
        transaction = new OBJTransaction();
        transaction.setTransactionDate(Locals.setDateFormat(cal.getTime()));
        transaction.setTransactionType(TransactionType.DAY_END);
        transaction.setReferanceNo("DAY" + Locals.getRefarance());
        transaction.setDocumentNo(null);
        transaction.setLoan(null);
        transaction.setCostCode(accountpackage.AccountPackage.costCode);
        transaction.setCustomerCode(null);
        transaction.setNote("Day End for Date : " + Locals.setDateFormat(cal.getTime()));
        transaction.setStatus(TransactionStatus.ACTIVE);

        for (OBJInvPayPlan PayPlan : payPlans) {
            accountTranses = new ArrayList<>();
            balance = Double.parseDouble(PayPlan.getBalance()) - Double.parseDouble(PayPlan.getLatePay());
            panalty = (balance * SystemSettings.PANALTY_RATE) / 30;
            String sql = "INSERT INTO payschedule (CreditId, install, DueDate, Amount, invoNo, installNo, TotInstallVal, balance, Type, print_order) VALUES (?,?,?,?,?,?,?,?,?,?)";
            st = con.prepareStatement(sql);
            st.setString(1, PayPlan.getCreditId());
            st.setString(2, PayPlan.getInstall());
            st.setString(3, Locals.setDateFormat(cal.getTime()));
            st.setString(4, panalty + "");
            st.setString(5, PayPlan.getInvoNo());
            st.setString(6, PayPlan.getInstallNo());
            st.setString(7, panalty + "");
            st.setString(8, panalty + "");
            st.setString(9, PayPlanStatus.PANALTY);
            st.setString(10, "1");
            st.execute();
            sql = "UPDATE credit SET LateCharge = LateCharge+" + panalty + ", totPayble = totPayble+" + panalty + " WHERE CreditID = '" + PayPlan.getCreditId() + "'";
            st = con.prepareStatement(sql);
            st.execute();
            if (panalty > 0) {

                // Account Transaction
                // Debit Account Trans
                OBJAccountTrans accountTrans = new OBJAccountTrans();
                accountTrans.setTransactionDate(Locals.setDateFormat(cal.getTime()));
                accountTrans.setCostCode("001");
                accountTrans.setAccountSetting(null);
                accountTrans.setDescription(AccountTransactionDescription.LOAN_PANALTY_DEBIT + " - Loan No : " + PayPlan.getLoan());
                accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.PANALTY_AMOUNT_DEBIT)).getAccount());
                accountTrans.setCreditAmount("0.00");
                accountTrans.setDebitAmount(panalty + "");
                accountTrans.setTransactionType(TransactionType.DAY_END);
                accountTrans.setType("AUTO");
                accountTrans.setStatus(AccountTransStatus.ACTIVE);
                accountTranses.add(accountTrans);

                // Credit Account Trans
                accountTrans = new OBJAccountTrans();
                accountTrans.setTransactionDate(Locals.setDateFormat(cal.getTime()));
                accountTrans.setCostCode("001");
                accountTrans.setAccountSetting(null);
                accountTrans.setDescription(AccountTransactionDescription.LOAN_PANALTY_CREDIT + " - Loan No : " + PayPlan.getLoan());
                accountTrans.setAccount(((AccountSettingObject) SystemSetting.getAccountSeting(AccountSetting.PANALTY_AMOUNT_CREDIT)).getAccount());
                accountTrans.setCreditAmount(panalty + "");
                accountTrans.setDebitAmount("0.00");
                accountTrans.setTransactionType(TransactionType.DAY_END);
                accountTrans.setType("AUTO");
                accountTrans.setStatus(AccountTransStatus.ACTIVE);
                accountTranses.add(accountTrans);
//        
            }
            int bb = TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, con);
        }
    }

    static void calculateDate(Connection con) throws SQLException {
        Date today = Locals.toDate(accountpackage.AccountPackage.company.getWorkingDate());
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        Date nextDay = c.getTime();

        String sql = "UPDATE company SET working_date = '" + Locals.setDateFormat(nextDay) + "' WHERE code = '" + accountpackage.AccountPackage.company.getCode() + "'";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
    }

//    public static void paySchedule(Connection con) throws SQLException {
//        panaltyCalculate(con);
//        balanceCalculate(con);
//    }
//
//    private static void panaltyCalculate(Connection conn) throws SQLException {
//        //status = 5 (due to pay)
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(Locals.toDate(accountpackage.AccountPackage.company.getWorkingDate()));
//        calendar.add(Calendar.DATE, (-1 * (SystemSettings.GRACE_DAYS)));
//        String sql = "UPDATE payschedule SET latepay = LatePay+((Balance-LatePay)*" + SystemSettings.PANALTY_RATE + "), Balance = (Balance+latepay) WHERE DueDate < '" + calendar.getTime() + "' AND status = '0'";
//        PreparedStatement st = conn.prepareStatement(sql);
//        st.execute();
//
//    }
//
//    private static void balanceCalculate(Connection conn) throws SQLException {
//        //status = 5 (due to pay)
//        ArrayList<OBJInvPayPlan> payPlans = new ArrayList<>();
//        String sql = "SELECT * FROM payschedule WHERE DueDate = '" + accountpackage.AccountPackage.company.getWorkingDate() + "'";
//        Statement st = conn.createStatement();
//        ResultSet resultSet = st.executeQuery(sql);
//        while (resultSet.next()) {
//            OBJInvPayPlan payPlan = new OBJInvPayPlan();
//            payPlan.setNoInstal(resultSet.getString("installNo"));
//            payPlan.setCreditId(resultSet.getString("creditId"));
//            payPlans.add(payPlan);
//        }
//
//        for (OBJInvPayPlan oBJInvPayPlan : payPlans) {
//            Double PrivBalance = 0.00;
//            System.out.println("ins " + oBJInvPayPlan.getNoInstal());
//            int installNo = Integer.parseInt(oBJInvPayPlan.getNoInstal());
//            if (installNo > 1) {
//                PrivBalance = getPrevBalance(conn, installNo, oBJInvPayPlan.getCreditId());
//            }
//            String sqli = "UPDATE payschedule SET  balance = balance+" + PrivBalance + ", status = '5' WHERE creditId = '" + oBJInvPayPlan.getCreditId() + "' AND installNo = '" + oBJInvPayPlan.getNoInstal() + "'";
//            PreparedStatement sti = conn.prepareStatement(sqli);
//            sti.execute();
//        }
//
//    }
//
//    private static Double getPrevBalance(Connection conn, int count, String creditId) throws SQLException {
//        String sql = "SELECT * FROM payschedule WHERE creditId = '" + creditId + "' AND installNo = '" + (count - 1) + "'";
//        Statement st = conn.createStatement();
//        ResultSet resultSet = st.executeQuery(sql);
//        int Status = 0;
//        Double bal = 0.00;
//        while (resultSet.next()) {
//            Status = resultSet.getInt("status");
//            bal = resultSet.getDouble("Balance");
//        }
//        if (Status == 5) {
//            updateStatus(conn, count, creditId);
//        }
//        return bal;
//    }
//
//    private static void updateStatus(Connection conn, int count, String creditId) throws SQLException {
//        String sql = "UPDATE payschedule SET  status = '2' WHERE creditId = '" + creditId + "' AND installNo = '" + (count - 1) + "'";
//        PreparedStatement st = conn.prepareStatement(sql);
//        st.execute();
//    }

    /*
     +---Ene Customer Payments---+
     */
}
