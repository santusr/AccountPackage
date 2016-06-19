/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.paymentplan;

import core.Locals;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import system.accounts.transaction.receiptvoucher.againstinvoice.OBJInstallPay;

/**
 *
 * @author dell
 */
public class DAOInvPayPlan {
//set Table

    private static final String TABAL = "Credit";

    public static int save(OBJInvPayPlan obj, Connection conn, int Act) throws SQLException {
        int transIndexNo;
            if (Act == 1) {
            String sql = "INSERT INTO " + TABAL + " (CreditId, InvoNo, PayPlan, NoInstal, InterestRate, InvoCredit, TotInterest, LateCharge, TotPayble, TotPay, AGDate, PDate, InvoType, InstalValue, dep1, dep2, AgNo, CustCode, SPDisc, SPDiscRate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql, 1);
            st.setString(1, obj.getCreditId());
            st.setString(2, obj.getInvoNo());
            st.setString(3, obj.getPayPlan());
            st.setString(4, obj.getNoInstal());
            st.setString(5, obj.getInterestRate());
            st.setString(6, obj.getInvoCredit());
            st.setString(7, obj.getTotInterest());
            st.setString(8, obj.getLateCharge());
            st.setString(9, obj.getTotPayble());
            st.setString(10, obj.getTotPay());
            st.setString(11, obj.getSDate());
            st.setString(12, obj.getEDate());
            st.setString(13, obj.getInvoType());
            st.setString(14, obj.getInstalValue());
            st.setString(15, obj.getDep1());
            st.setString(16, obj.getDep2());
            st.setString(17, getAgNo(conn, obj.getInvoNo()));
            st.setString(18, obj.getCustCode());
            st.setString(19, obj.getSPDisc());
            st.setString(20, obj.getSPDiscRate());
            st.execute();
            ResultSet resTrans = st.getGeneratedKeys();
        if (resTrans.next()) {
            transIndexNo = resTrans.getInt(1);
            return transIndexNo;
        }
        } else {
            String sql = "UPDATE " + TABAL + " SET PayPlan = ?, NoInstal = ?, InterestRate = ?, InvoCredit = ?, TotInterest = ?, LateCharge = ?, TotPayble = ?, TotPay = ?, AGDate = ?, PDate = ?, InvoType = ?, InstalValue = ?, dep1 = ?, dep2 = ?, CustCode = ? WHERE CreditId = '" + obj.getCreditId() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getPayPlan());
            st.setString(2, obj.getNoInstal());
            st.setString(3, obj.getInterestRate());
            st.setString(4, obj.getInvoCredit());
            st.setString(5, obj.getTotInterest());
            st.setString(6, obj.getLateCharge());
            st.setString(7, obj.getTotPayble());
            st.setString(8, obj.getTotPay());
            st.setString(9, obj.getSDate());
            st.setString(10, obj.getEDate());
            st.setString(11, obj.getInvoType());
            st.setString(12, obj.getInstalValue());
            st.setString(13, obj.getDep1());
            st.setString(14, obj.getDep2());
            st.setString(15, obj.getCustCode());
            st.executeUpdate();
        }
            return -1;
    }

    public static void saveGRN(OBJInvPayPlan obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO CreditGRN (CreditId, InvoNo, PayPlan, NoInstal, InterestRate, InvoCredit, TotInterest, LateCharge, TotPayble, TotPay, AGDate, PDate, InvoType, InstalValue, dep1, dep2, AgNo, CustCode, SPDisc, SPDiscRate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, genGRNID(conn));
            st.setString(2, obj.getInvoNo());
            st.setString(3, obj.getPayPlan());
            st.setString(4, obj.getNoInstal());
            st.setString(5, obj.getInterestRate());
            st.setString(6, obj.getInvoCredit());
            st.setString(7, obj.getTotInterest());
            st.setString(8, obj.getLateCharge());
            st.setString(9, obj.getTotPayble());
            st.setString(10, obj.getTotPay());
            st.setString(11, obj.getSDate());
            st.setString(12, obj.getEDate());
            st.setString(13, obj.getInvoType());
            st.setString(14, obj.getInstalValue());
            st.setString(15, obj.getDep1());
            st.setString(16, obj.getDep2());
            st.setString(17, getAgNo(conn, obj.getInvoNo()));
            st.setString(18, obj.getCustCode());
            st.setString(19, obj.getSPDisc());
            st.setString(20, obj.getSPDiscRate());
            st.execute();

        } else {
            String sql = "UPDATE CreditGRN SET PayPlan = ?, NoInstal = ?, InterestRate = ?, InvoCredit = ?, TotInterest = ?, LateCharge = ?, TotPayble = ?, TotPay = ?, AGDate = ?, PDate = ?, InvoType = ?, InstalValue = ?, dep1 = ?, dep2 = ?, CustCode = ? WHERE CreditId = '" + obj.getCreditId() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getPayPlan());
            st.setString(2, obj.getNoInstal());
            st.setString(3, obj.getInterestRate());
            st.setString(4, obj.getInvoCredit());
            st.setString(5, obj.getTotInterest());
            st.setString(6, obj.getLateCharge());
            st.setString(7, obj.getTotPayble());
            st.setString(8, obj.getTotPay());
            st.setString(9, obj.getSDate());
            st.setString(10, obj.getEDate());
            st.setString(11, obj.getInvoType());
            st.setString(12, obj.getInstalValue());
            st.setString(13, obj.getDep1());
            st.setString(14, obj.getDep2());
            st.setString(15, obj.getCustCode());
            st.executeUpdate();
        }
    }

    public static void saveSched(ArrayList<OBJPaySchedule> objs, Connection conn, int Act) throws SQLException {
        int i = 1;
        for (OBJPaySchedule obj : objs) {
            String sql = "INSERT INTO payschedule ( CreditId, Install, DueDate, Amount, InvoNo, installNo,totInstallval,Balance) VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setString(1, obj.getCreditId());
                st.setString(2, obj.getInstall());
                st.setString(3, obj.getDate());
                st.setString(4, obj.getAmount());
                st.setString(5, obj.getInvoNo());
                st.setString(6, i + "");
                st.setString(7, obj.getAmount());
                st.setString(8, obj.getAmount());
                i++;
                st.execute();
            }
        }
    }

    public static void updateSchedul(ArrayList<OBJInstallPay> objList, Connection conn) throws SQLException {
        //update pay schedule
        PreparedStatement st;
        String sql;
        for (OBJInstallPay obj : objList) {
            if (obj.getInstall().equals("1")) {
                sql = "UPDATE payschedule SET ClearDate = ?, PayAmount = ?, Balance = ?, SPDisc = ?, Status = ? WHERE CreditId = '" + obj.getCreditId() + "' AND ID = '" + obj.getID() + "'";
                st = conn.prepareStatement(sql);
                st.setString(1, obj.getClearDate());
                st.setString(2, obj.getPayAmount());
                st.setString(3, obj.getBalance());
                st.setString(4, obj.getSPDisc());
                st.setString(5, obj.getStatus());
                st.execute();
            }
        }
    }
// OLD SCHEDULE UPDATE
//    public static boolean updateSchedul(OBJAgInvo obj, Connection conn) throws SQLException {
//        boolean b = false;
//        //update pay schedule
//        String sql = "UPDATE payschedule SET ClearDate = ?, PayAmount = ?, Balance = ?, SPDisc = ?, Status = ? WHERE CreditId = '" + obj.getCreditId() + "' AND InstallNo = '" + obj.getInstallNo() + "'";
//        PreparedStatement st = conn.prepareStatement(sql);
//        st.setString(1, obj.getVouDate());
//        st.setString(2, obj.getPayAmount());
//        st.setString(3, obj.getBalance());
//        st.setString(4, obj.getSPDisc());
//        st.setString(5, "1");
//        st.execute();
//        int noInstalment = Integer.parseInt(mainApp.DAOCommen.getDescription("PaySchedule", obj.getInstallNo(), "CreditId = '" + obj.getCreditId() + "' AND InstallNo", "Install", conn));
//        //int nextInstalment = Integer.parseInt(obj.getInstallNo()) + 1;
//        int Instalment = Integer.parseInt(obj.getInstallNo());
//
//        //update missing instalments (set Status as 2)
//        if (Instalment > 1) {
//            int Inst = Instalment - 1;
//            for (int i = Inst; i > 0; i--) {
//                sql = "UPDATE payschedule SET Status = '2' WHERE CreditId = '" + obj.getCreditId() + "' AND InstallNo = '" + i + "' AND Status = '0' ";
//                st = conn.prepareStatement(sql);
//                st.execute();
//            }
//        }
//
//        String creditBalance = mainApp.DAOCommen.getDescription("credit", obj.getCreditId(), "CreditId", "totPayble", conn);
//        if (Instalment < noInstalment) {
//
//            //update paySchedule next payment date
//            sql = "UPDATE payschedule SET DueDate = ? WHERE CreditId = '" + obj.getCreditId() + "' AND InstallNo = '" + (Instalment + 1) + "'";
//            st = conn.prepareStatement(sql);
//            st.setString(1, obj.getNxtPDate());
//            st.execute();
//
////            int ins = Integer.parseInt(obj.getInstallNo()) - 1;
////            String stat = mainApp.DAOCommen.getDescription("PaySchedule", ins + "", "CreditId = '" + obj.getCreditId() + "' AND InstallNo", "Status", conn);
////            boolean b = true;
////            while (b) {
////
////                if (ins > 0 && stat.equals("0")) {
////
////                    sql = "UPDATE payschedule SET Status = '2' WHERE CreditId = '" + obj.getCreditId() + "' AND InstallNo = '" + ins + "' ";
////                    st = conn.prepareStatement(sql);
////
////                    st.execute();
////                } else {
////                    b = false;
////                }
////
////                ins--;
////            }
////
//        } else {
//            String status = "2";
//            if (Double.parseDouble(creditBalance) == 0.00 || obj.getEnd()) {
//                status = "1";
//            }
//            //Update credit entry as Status = 2(aoutoCancel)
//            sql = "UPDATE credit SET Status = ? WHERE creditId = '" + obj.getCreditId() + "'";
//            st = conn.prepareStatement(sql);
//            st.setString(1, status);
//            st.execute();
//            st.close();
//
//            //Insert new credit entry
//            // Inset new pay schedule
//            if (!obj.getEnd()) {
//                b=true;
//                addNewCredit(obj, conn);
//            }
//        }
//        return b;
//    }

    static OBJInvPayPlan getPayPlan(Connection conn, int Ix) throws SQLException {

        OBJInvPayPlan obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+Ix+") * FROM FCurrency ORDER BY FCCode";

        //MySql
        //Ix =-1;
        String sql = "SELECT * FROM " + TABAL + " WHERE Status = '0' ORDER BY CreditId LIMIT " + Ix + ",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJInvPayPlan(
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
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(18),
                    result.getString(19));
        }

        return obj;
    }

    static OBJInvPayPlan serch(Connection conn, String code) throws SQLException {

        OBJInvPayPlan obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE CreditId = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJInvPayPlan(
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
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(19),
                    result.getString(20),
                    result.getString(21));
        }

        return obj;
    }

    static OBJInvPayPlan serchAllCredit(Connection conn, String code) throws SQLException {

        OBJInvPayPlan obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE CreditId = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJInvPayPlan(
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
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(19),
                    result.getString(20),
                    result.getString(21));
        }

        return obj;
    }

    static OBJInvPayPlan serchPP(Connection conn, String code) throws SQLException {

        OBJInvPayPlan currency = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE InvoNo = '" + code + "' AND Status = '0' ORDER BY CreditId DESC LIMIT 1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            currency = new OBJInvPayPlan(
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
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(19),
                    result.getString(20),
                    result.getString(21));
        }
        return currency;
    }

    static int getIndex(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM " + TABAL + " WHERE Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            //index++;
            index = result.getInt("val");
        }
        return index;
    }

    static void CancelPayPlan(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM FCurrency WHERE FCCode = '" + code + "'");
        String sql = "UPDATE payschedule SET Status = ? WHERE invoNo = '" + code + "' AND Status = '0'";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "3");
            st.execute();
        }
    }

    static void deletePayPlan(Connection conn, String code) throws SQLException {
//        String sql = "UPDATE payschedule SET Status = ? WHERE invoNo = '" + code + "' AND Status = '0'";

        PreparedStatement st = conn.prepareStatement("DELETE FROM payschedule WHERE CreditID = '" + code + "'");
        st.execute();

    }

    static void deleteAllCredit(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM FCurrency WHERE FCCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE InvoNo = '" + code + "'";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "2");
            st.execute();
        }
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "1";
        String sql = "SELECT CreditId FROM " + TABAL + " ORDER BY CreditId";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("CreditId");
            int i = Integer.parseInt(cd);
            i++;
            cd = i + "";
//            i = cd.length();
//            for (int j = i; j < 3; j++) {
//                cd = "0"+cd;
//            }
            ID = cd;
        }
        return ID;
    }

    static String genGRNID(Connection conn) throws SQLException {
        String ID = "1";
        String sql = "SELECT CreditId FROM creditgrn ORDER BY CreditId";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("CreditId");
            int i = Integer.parseInt(cd);
            i++;
            cd = i + "";
//            i = cd.length();
//            for (int j = i; j < 3; j++) {
//                cd = "0"+cd;
//            }
            ID = cd;
        }
        return ID;
    }

    static OBJInvPayPlan getPPDetails(Connection conn, String code) throws SQLException {

        OBJInvPayPlan obj = null;
        String sql = "SELECT * FROM PayPlan WHERE PayPlanNo = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJInvPayPlan(
                    result.getString(1),
                    result.getString(3),
                    result.getString(4));
        }

        return obj;
    }

    private static String getAgNo(Connection conn, String creditId) throws SQLException {
        String ID = "1";
        String sql = "SELECT COUNT(CreditId) AS CO FROM " + TABAL + " WHERE invoNo = '" + creditId + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            int cd = result.getInt("CO");

            cd++;
//            i = cd.length();
//            for (int j = i; j < 3; j++) {
//                cd = "0"+cd;
//            }
            ID = cd + "";
        }
        return ID;
    }

    static ArrayList<OBJPaySchedule> loadSchedule(String cid, Connection conn) throws SQLException {
        OBJPaySchedule obj;
        ArrayList<OBJPaySchedule> obja = new ArrayList<>();
        String sql = "SELECT * FROM payschedule, credit WHERE credit.CreditId = '" + cid + "' AND credit.status = '0' AND payschedule.CreditId = credit.CreditId";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        String insVal;
        Double d = 0.00;
        while (result.next()) {
            insVal = result.getString("payschedule.totInstallVal");
            d = d + Double.parseDouble(insVal);
            obj = new OBJPaySchedule(
                    result.getString("payschedule.DueDate"),
                    result.getString("payschedule.totInstallVal"),
                    Locals.currencyFormat(d));
            obja.add(obj);
        }

        return obja;
    }

}
