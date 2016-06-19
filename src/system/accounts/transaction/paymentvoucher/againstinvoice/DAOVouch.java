/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.paymentvoucher.againstinvoice;

import core.system_transaction.TransactionType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import system.accounts.transaction.paymentvoucher.onaccount.OBJVoucherOnAccount;

/**
 *
 * @author Rabid
 */
public class DAOVouch {

   private static final String TABAL = "voucher";

    static void doSave(OBJAgInvo obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" (VoucherNo, InvoNo, Cust, VouDate, CostCenter, Narration, PayAmount, PayTerm, RefNo, PrepBy, ApproBy, Ref, type, user) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getVouNo());
            st.setString(2, obj.getInvoNo());
            st.setString(3, obj.getCrAcc());
            st.setString(4, obj.getVouDate());
            st.setString(5, obj.getCostCenter());
            st.setString(6, obj.getNaretion());
            st.setString(7, obj.getPayAmount());
            st.setString(8, obj.getPayMod());
            st.setString(9, obj.getInvoNo());
            st.setString(10, obj.getPrepBy());
            st.setString(11, obj.getAppBy());
            st.setString(12, accountpackage.AccountPackage.user);
            st.setString(13, TransactionType.VOUCHER);
            st.setString(14, accountpackage.AccountPackage.user);
            
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET InvoNo = ?, Cust = ?, VouDate = ?, CostCenter = ?, Narration = ?, PayAmount = ?, PayTerm = ?, RefNo = ?, PrepBy = ?, ApproBy = ?, Ref = ? WHERE VoucherNo = '" + obj.getVouNo() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            
            st.setString(1, obj.getInvoNo());
            st.setString(2, obj.getCrAcc());
            st.setString(3,obj.getVouDate());
            st.setString(4, obj.getCostCenter());
            st.setString(5, obj.getNaretion());
            st.setString(6, obj.getPayAmount());
            st.setString(7, obj.getPayMod());
            st.setString(8, obj.getInvoNo());
            st.setString(9, obj.getPrepBy());
            st.setString(10, obj.getAppBy());
            st.setString(11, accountpackage.AccountPackage.user);
            
            st.executeUpdate();
        }
    }
    
    public static void doSave(OBJVoucherOnAccount obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO " + TABAL + " (VoucherNo, InvoNo, VouDate, CostCenter, Narration, PayAmount, PayTerm, RefNo, PrepBy, ApproBy, user, type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, genID(conn));
            st.setString(2, obj.getInvoNo());
            st.setString(3, obj.getVouDate());
            st.setString(4, obj.getCostCenter());
            st.setString(5, obj.getNarration());
            st.setString(6, obj.getPayAmount());
            st.setString(7, obj.getPayTerm());
            st.setString(8, obj.getRef());
            st.setString(9, obj.getPrpBy());
            st.setString(10, obj.getAprBy());
            st.setString(11, accountpackage.AccountPackage.user);
            st.setString(12, obj.getType());

            st.execute();

        } else {
            String sql = "UPDATE " + TABAL + " SET InvoNo = ?, Cust = ?, VouDate = ?, CostCenter = ?, Narration = ?, PayAmount = ?, PayTerm = ?, RefNo = ?, PrepBy = ?, ApproBy = ?, user = ? WHERE VoucherNo = '" + obj.getVoucherNo() + "'";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, obj.getInvoNo());
            st.setString(2, obj.getVouDate());
            st.setString(3, obj.getCostCenter());
            st.setString(4, obj.getNarration());
            st.setString(5, obj.getPayAmount());
            st.setString(6, obj.getPayTerm());
            st.setString(7, obj.getRef());
            st.setString(8, obj.getPrpBy());
            st.setString(9, obj.getAprBy());
            st.setString(10, accountpackage.AccountPackage.user);

            st.executeUpdate();
        }
    }
////
////    static void saveSched(ArrayList<OBJPaySchedule> objs, Connection conn, int Act) throws SQLException {
////        int i = 1;
////      for (OBJPaySchedule obj : objs) {
////                String sql = "INSERT INTO payschedule ( CreditId, Install, DueDate, Amount, InvoNo, installNo,totInstallval,Balance) VALUES (?,?,?,?,?,?,?,?)";
////                PreparedStatement st = conn.prepareStatement(sql);
////                st.setString(1, obj.getCreditId());
////                st.setString(2, obj.getInstall());
////                st.setString(3, obj.getDate());
////                st.setString(4, obj.getAmount());
////                st.setString(5, obj.getInvoNo());
////                st.setString(6, i+"");
////                st.setString(7, obj.getAmount());
////                st.setString(8, obj.getAmount());
////                i++;
////                st.execute();
////                
////                st.close();
////            }
////    }
////    
//    public static void updateSchedule(OBJAgInvo obj, Connection conn) throws SQLException {
//        
//                String sql = "UPDATE payschedule SET ClearDate = ?, PayAmount = ?, Balance = ?, SPDisc = ?, Status = 2 WHERE CreditId = '"+obj.getCreditId()+"' AND InstallNo = '"+obj.getInstallNo()+"'";
//                PreparedStatement st = conn.prepareStatement(sql);
//                st.setString(1, obj.getVouDate());
//                st.setString(2, obj.getPayAmount());
//                st.setString(3, obj.getBalance());
//                st.setString(4, obj.getSPDisc());
//                st.execute();  
//                int a = Integer.parseInt(mainApp.DAOCommen.getDescription("PaySchedule", obj.getInstallNo(), "CreditId = '"+obj.getCreditId()+"' AND InstallNo","Install", conn));
//                int aa = Integer.parseInt(obj.getInstallNo()) + 1;
//                if (aa <= a) {
//            
//                sql = "UPDATE payschedule SET DueDate = ?, TotInstallVal = TotInstallVal + ? WHERE CreditId = '"+obj.getCreditId()+"' AND InstallNo = '"+Integer.parseInt(obj.getInstallNo()+1)+"' AND Install <= "+aa+"";
//                st = conn.prepareStatement(sql);
//                st.setString(1, obj.getNxtPDate());
//                st.setString(2, obj.getBalance());
//                st.execute();       
//                
//        } else {
//                    
//                    String ID = SERInvPayPlan.getID();
//               OBJInvPayPlan objpp = new OBJInvPayPlan(
//                ID,
//                obj.getInvoNo(),
//                "0",
//                "1",
//                "0.00",
//                mainApp.DAOCommen.getDescription("Credit", obj.getCreditId(), "CreditId", "InvoCredit", conn),
//                "0.00",
//                "0.00",
//                mainApp.DAOCommen.getDescription("Credit", obj.getCreditId(), "CreditId", "TotPayble", conn),
//                mainApp.DAOCommen.getDescription("Credit", obj.getCreditId(), "CreditId", "TotPay", conn),
//                Locals.setDateFormat(Locals.toDate(obj.getVouDate())),
//                Locals.setDateFormat(Locals.toDate(obj.getNxtPDate())),
//                "0",
//                mainApp.DAOCommen.getDescription("Credit", obj.getCreditId(), "CreditId", "TotPayble", conn),
//                "",
//                "",
//                mainApp.DAOCommen.getDescription("Credit", obj.getCreditId(), "CreditId", "CustCode", conn),
//                "0.00",
//                "0.00");
//
//        SERInvPayPlan.save(objpp, 1);
//                
//                try {
//                   ArrayList<OBJPaySchedule> objs = new ArrayList<OBJPaySchedule>();
//                    
//                    
//
//                       OBJPaySchedule objse = new OBJPaySchedule(
//                                ID,
//                                "1",
//                                Locals.setDateFormat(Locals.toDate(obj.getNxtPDate())),
//                                mainApp.DAOCommen.getDescription("Credit", obj.getCreditId(), "CreditId", "TotPayble", conn),
//                                obj.getInvoNo());
//
//                        objs.add(objse);
//                    SERInvPayPlan.saveSched(objs, 1);
//                } catch (SQLException e) {
//                    core.Exp.Handle(e);
//                }
//        new GUIInvPayPlan(null, true, obj.getInvoNo()).setVisible(true);
//        }
//                
//                st.close();
//          
//    }
//    
    static OBJAgInvo getVouch(Connection conn, int Ix) throws SQLException {

        OBJAgInvo obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+Ix+") * FROM FCurrency ORDER BY FCCode";
        
        //MySql
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" ORDER BY VoucherNo LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJAgInvo(
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
                    result.getString(17));
        }

        return obj;
    }

    static OBJAgInvo serch(Connection conn, String code) throws SQLException {

        OBJAgInvo obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE CreditId = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJAgInvo(
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
                    result.getString(17));
        }

        return obj;
    }
//    
//    static OBJInvPayPlan serchPP(Connection conn, String code) throws SQLException {
//
//        OBJInvPayPlan currency = null;
//        String sql = "SELECT * FROM "+TABAL+" WHERE InvoNo = '" + code + "' AND Status = '0'";
//        PreparedStatement st = conn.prepareStatement(sql);
//        st.execute();
//        ResultSet result = st.getResultSet();
//        while (result.next()) {
//            currency = new OBJInvPayPlan(
//                    result.getString(1),
//                    result.getString(2),
//                    result.getString(3),
//                    result.getString(4),
//                    result.getString(5),
//                    result.getString(6),
//                    result.getString(7),
//                    result.getString(8),
//                    result.getString(9),
//                    result.getString(10),
//                    result.getString(11),
//                    result.getString(12),
//                    result.getString(13),
//                    result.getString(14),
//                    result.getString(15),
//                    result.getString(16),
//                    result.getString(17),
//                    result.getString(18),
//                    result.getString(19));
//        }
//        return currency;
//    }

    static int getIndex(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM "+TABAL+"";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            //index++;
            index = result.getInt("val");
        }
        return index;
    }

    static void deleteVouch(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM FCurrency WHERE FCCode = '" + code + "'");
        String sql = "UPDATE "+TABAL+" SET Status = ? WHERE VoucherNo = '" + code + "' AND Type = 'P'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException{
        String ID = "VOU000001";
        String sql = "SELECT VoucherNo FROM "+TABAL+" WHERE Type = 'VOUCHER' ORDER BY VoucherNo DESC LIMIT 1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        if (result.next()) {
            String cd = result.getString("VoucherNo");
            cd = cd.substring(4, 9);
            int i = Integer.parseInt(cd);
            i++;
            cd = i+"";
            i = cd.length();
            for (int j = i; j < 6; j++) {
                cd = "0"+cd;
            }
            ID = "VOU"+cd;
        }
        return ID;
    }

//    static OBJInvPayPlan getPPDetails(Connection conn, String code) throws SQLException {
//        
//        OBJInvPayPlan obj = null;
//        String sql = "SELECT * FROM PayPlan WHERE PayPlanNo = '" + code + "' AND Status = '0'";
//        PreparedStatement st = conn.prepareStatement(sql);
//        st.execute();
//        ResultSet result = st.getResultSet();
//        while (result.next()) {
//            obj = new OBJInvPayPlan(
//                    result.getString(1),
//                    result.getString(3),
//                    result.getString(4));
//        }
//
//        return obj;
//    }
//
//    private static String getAgNo(Connection conn, String creditId) throws SQLException {
//        String ID = "1";
//        String sql = "SELECT COUNT(CreditId) AS CO FROM "+TABAL+" WHERE CreditId = '"+creditId+"'";
//        PreparedStatement st = conn.prepareStatement(sql);
//        st.execute();
//        ResultSet result = st.getResultSet();
//        while (result.next()) {
//            int cd = result.getInt("CO");
//            
//            cd++;
////            i = cd.length();
////            for (int j = i; j < 3; j++) {
////                cd = "0"+cd;
////            }
//            ID = cd+"";
//        }
//        return ID;
//    }

     
}
