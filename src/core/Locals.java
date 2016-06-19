/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import accountpackage.AccountPackage;
import core.company.Company;
import core.system_transaction.account_trans.AccountSettingObject;
import core.system_transaction.payment.PaymentSettingObject;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class Locals {

    public static String passwordGen(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String currentDate_F1() {
        String date = "17/07/2013";

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date = df.format(cal.getTime());
        return date;
    }
    
    public static String getRefarance() {
        String date = "17/07/2013";

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyMMddhhmmss");
        date = df.format(cal.getTime());
        return date;
    }

    public static String toDate_F1(Date d) {
        String date = "17/07/2013";

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date = df.format(d);
        return date;
    }

    public static String currentDate_F2() {
        String date = "2013-07-17";

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(cal.getTime());
        return date;
    }

    public static String setDateFormat(Date date) {
        String F_DATE = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        F_DATE = df.format(date);
        return F_DATE;
    }

    public static String currencyFormat(double d) {
        String frm = "0.00";
        DecimalFormat df = new DecimalFormat("#0.00");
        frm = df.format(d);
        return frm;
    }

    public static String sCurrencyFormat(String d) {
        String frm = "0.00";
        DecimalFormat df = new DecimalFormat("#0.00");
        frm = df.format(Double.parseDouble(d));
        return frm;
    }

    public static Date toDate(String IDate) {
        java.util.Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(IDate);
        } catch (Exception e) {
            Exp.Handle(e);
        }
        return date;
    }

    public static Double ignoCase(double d) {
        if (d < 0) {
            d = d * -1;
        }
        return d;
    }

    public static Connection connection() {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Locals.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }

    public static Company getCompany() {
        Company company = null;
        String que = "SELECT * FROM company WHERE Status = '0'";
        try {
            PreparedStatement preparedStmt = AccountPackage.connect().prepareStatement(que);
            preparedStmt.execute();
            ResultSet rs = preparedStmt.getResultSet();
            while (rs.next()) {
                company = new Company(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10)
                );

            }
        } catch (SQLException e) {
            Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, e);
        }

        return company;
    }

    public static ArrayList<AccountSettingObject> getAccountSettingObject() {
        AccountSettingObject accountSettingObject = null;
        ArrayList<AccountSettingObject> accountSettingObjects = new ArrayList<>();
        String que = "SELECT * FROM account_setting";
        try {
            PreparedStatement preparedStmt = AccountPackage.connect().prepareStatement(que);
            preparedStmt.execute();
            ResultSet rs = preparedStmt.getResultSet();
            while (rs.next()) {
                accountSettingObject = new AccountSettingObject();

                accountSettingObject.setCode(rs.getString(1));
                accountSettingObject.setName(rs.getString(2));
                accountSettingObject.setTransactionType(rs.getString(3));
                accountSettingObject.setCreditOrDebit(rs.getString(4));
                accountSettingObject.setAccount(rs.getString(5));
                accountSettingObjects.add(accountSettingObject);
            }
        } catch (SQLException e) {
            Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, e);
        }

        return accountSettingObjects;
    }

    public static ArrayList<PaymentSettingObject> getPaymentSettingObject() {
        PaymentSettingObject paymentSettingObject = null;
        ArrayList<PaymentSettingObject> paymentSettingObjects = new ArrayList<>();
        String que = "SELECT * FROM payment_setting";
        try {
            PreparedStatement preparedStmt = AccountPackage.connect().prepareStatement(que);
            preparedStmt.execute();
            ResultSet rs = preparedStmt.getResultSet();
            while (rs.next()) {
                paymentSettingObject = new PaymentSettingObject();

                paymentSettingObject.setCode(rs.getString(1));
                paymentSettingObject.setName(rs.getString(2));
                paymentSettingObject.setTransactionType(rs.getString(3));
                paymentSettingObject.setAccount(rs.getString(4));
                paymentSettingObject.setCreditOrDebit(rs.getString(5));
                paymentSettingObjects.add(paymentSettingObject);
            }
        } catch (SQLException e) {
            Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, e);
        }

        return paymentSettingObjects;
    }

}
