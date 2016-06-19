/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.cheque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author dell
 */
public class DAOCheque {
    //set Table

    private static final String TABAL = "cheque";

   public static void save(OBJCheque obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO " + TABAL + " (Code, CustCode, InvoNo, ChqeNo, getDate, RDate, Amount, Banck, FCCode, FCRate, Remarks, CheqType, BankCharge, IntrestCharge, Net, bankAccount, Status, depositAccount, transaction) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            // Remarks, FCDebit, FCCredit, Type, ArabicName, , , ,  , , , 

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getCustCode());
            st.setString(3, obj.getInvoNo());
            st.setString(4, obj.getChqeNo());
            st.setString(5, obj.getGetDate());
            st.setString(6, obj.getRDate());
            st.setString(7, obj.getAmount());
            st.setString(8, obj.getBank());
            st.setString(9, obj.getFCCode());
            st.setString(10, obj.getFCRate());
            st.setString(11, obj.getRemarks());
            st.setString(12, obj.getType());
            st.setString(13, obj.getBankCharge());
            st.setString(14, obj.getInterestCharge());
            st.setString(15, obj.getNet());
            st.setString(16, obj.getBankAccount());
            st.setString(17, obj.getStatus());
            st.setString(18, obj.getDepositAccount());
            st.setString(19, obj.getTransaction());

            st.execute();

        } else {
            String sql = "UPDATE " + TABAL + " SET CustCode = ?, InvoNo = ?, ChqeNo = ?, getDate = ?, RDate = ?, Amount = ?, Banck = ?, FCCode = ?, FCRate = ?, Remarks = ?, CheqType = ?, BankCharge = ?, IntrestCharge = ?, Net = ?, bankAccount = ?, Status = ?, depositAccount=?, transaction=? WHERE Code = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCustCode());
            st.setString(2, obj.getInvoNo());
            st.setString(3, obj.getChqeNo());
            st.setString(4, obj.getGetDate());
            st.setString(5, obj.getRDate());
            st.setString(6, obj.getAmount());
            st.setString(7, obj.getBank());
            st.setString(8, obj.getFCCode());
            st.setString(9, obj.getFCRate());
            st.setString(10, obj.getRemarks());
            st.setString(11, obj.getType());
            st.setString(12, obj.getBankCharge());
            st.setString(13, obj.getInterestCharge());
            st.setString(14, obj.getNet());
            st.setString(15, obj.getBankAccount());
            st.setString(16, obj.getStatus());
            st.setString(17, obj.getDepositAccount());
            st.setString(18, obj.getTransaction());

            st.executeUpdate();
        }
    }

    static OBJCheque getNavi(Connection conn, int Ix) throws SQLException {

        OBJCheque obj = null;
        // SQL Server
        // String sql = "SELECT TOP ("+Ix+") * FROM "+TABAL+" WHERE Type = 'V' AND Status = '0' ORDER BY Code ASC";

        //MySql  
        //Ix =-1;
        String sql = "SELECT * FROM " + TABAL + " WHERE Status != '"+ChequeStatus.CANCEL+"' ORDER BY Code ASC LIMIT " + Ix + ",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJCheque(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(11),
                    result.getString(12),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(13),
                    result.getString(18),
                    result.getString(20),
                    result.getString(21),
                    result.getString(22));
        }

        return obj;
    }

    static OBJCheque serch(Connection conn, String code) throws SQLException {

        OBJCheque obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE Code = '" + code + "' AND Status != '"+ChequeStatus.CANCEL+"'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJCheque(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(11),
                    result.getString(12),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(13),
                    result.getString(18),
                    result.getString(20),
                    result.getString(21),
                    result.getString(22));
        }

        return obj;
    }

    static OBJCheque serchCNo(Connection conn, String code) throws SQLException {

        OBJCheque obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE ChqeNo = '" + code + "' AND Status != '"+ChequeStatus.CANCEL+"'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJCheque(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(11),
                    result.getString(12),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(13),
                    result.getString(18),
                    result.getString(20),
                    result.getString(21),
                    result.getString(22));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM " + TABAL;

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            index = result.getInt("val");
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE Code = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE Code = '" + code + "'";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, ChequeStatus.CANCEL);
            st.execute();
        }
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "1";
        String sql = "SELECT Code FROM " + TABAL + " ORDER BY Code";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("Code");
            int i = Integer.parseInt(cd);
            i++;
            cd = i + "";
            ID = cd;
        }
        return ID;
    }

    static Vector getCurrency(Connection conn) throws SQLException {
        Vector currency = new Vector();
        String sql = "SELECT FCCode,Description FROM FCurrency";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("FCCode");
            String name = result.getString("Description");
            cd = "(" + cd + ")" + name;
            currency.add(cd);
        }
        return currency;
    }

    static String genName(Connection conn, String tble, String code, String col) throws SQLException {
        String name = "";

        String sql = "SELECT Description FROM " + tble + " WHERE " + col + " = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            name = result.getString("Description");
        }

        return name;
    }

    static Vector getSrep(Connection conn) throws SQLException {
        Vector sRep = new Vector();
        String sql = "SELECT RepCode,RepName FROM SalesRep";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("RepCode");
            String name = result.getString("RepName");
            cd = "(" + cd + ")" + name;
            sRep.add(cd);
        }
        return sRep;
    }

    static String getSrep(Connection conn, String code) throws SQLException {
        String sRep = null;
        String sql = "SELECT RepCode,RepName FROM SalesRep WHERE RepCode = '" + code + "'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("RepCode");
            String name = result.getString("RepName");
            sRep = "(" + cd + ")" + name;
        }
        return sRep;
    }

    static String getcusGroup(Connection conn, String code) throws SQLException {
        String cusGroup = null;
        String sql = "SELECT CustGrpCode,CustGrpName FROM customerGroup WHERE CustGrpCode = '" + code + "'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("CustGrpCode");
            String name = result.getString("CustGrpName");
            cusGroup = "(" + cd + ")" + name;
        }
        return cusGroup;
    }

    static String getCurrency(Connection conn, String code) throws SQLException {
        String Currency = null;
        String sql = "SELECT FCCode,Description FROM FCurrency WHERE FCCode = '" + code + "'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("FCCode");
            String name = result.getString("Description");
            Currency = "(" + cd + ")" + name;
        }
        return Currency;
    }
    
    static double gatFCRate(Connection con, String cc) throws SQLException {
        double d = 0.00;
        String sql = "SELECT Factor FROM fcurrency WHERE FCCode = '" + cc + "' LIMIT 1";

        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            d = result.getDouble("Factor");
        }
        return d;
    }

    static void deposit(OBJCheque cheque, Connection con) throws SQLException {
        String sql = "UPDATE " + TABAL + " SET bankAccount = ?, depositAccount = ?, deposit_transaction = ?, realize_transaction = ?, Status = ? WHERE ChqeNo = '" + cheque.getChqeNo() + "'";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            System.out.println("Do Realize");
            st.setString(1, cheque.getBankAccount());
            st.setString(2, cheque.getDepositAccount());
            st.setString(3, cheque.getDepositTransaction());
            st.setString(4, cheque.getRealizeTransaction());
            st.setString(5, cheque.getStatus());
            st.execute();
        }
    }

}
