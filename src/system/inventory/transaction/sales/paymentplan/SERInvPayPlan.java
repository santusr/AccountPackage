/*
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.paymentplan;

import core.Exp;
import core.Locals;
import core.system_transaction.TRSAccount;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERInvPayPlan {

    public static int save(
            OBJInvPayPlan obj,
            ArrayList<OBJPaySchedule> objs,
            OBJTransaction transaction,
            OBJPayment payment,
            ArrayList<OBJPaymentInfo> paymentsInfo,
            ArrayList<OBJAccountTrans> accountTranses, 
            ArrayList<OBJItemTransaction> itemTransactions, 
            int Act) throws SQLException {

        Connection conn = accountpackage.AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            if (Act == 1) {
                DAOInvPayPlan.deleteAllCredit(conn, obj.getInvoNo());
                DAOInvPayPlan.CancelPayPlan(conn, obj.getInvoNo());
            } else {
                DAOInvPayPlan.deletePayPlan(conn, obj.getCreditId());
            }
            DAOInvPayPlan.save(obj, conn, Act);
            DAOInvPayPlan.saveSched(objs, conn, Act);
            int bb = TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, conn);
            
            conn.commit();
            return 1;
        } catch (SQLException ex) {
            conn.rollback();
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
                return 2;
            } else {
                Exp.Handle(ex);
                return 3;
            }
        }
    }

    public static void saveGRN(OBJInvPayPlan obj, int Act) throws SQLException {

        Connection conn = accountpackage.AccountPackage.connect();
        try {
            DAOInvPayPlan.saveGRN(obj, conn, Act);

        } catch (SQLException ex) {
            conn.rollback();
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJInvPayPlan getNavi(int Index) {
        OBJInvPayPlan currency = null;
        try (Connection conn = accountpackage.AccountPackage.connect()) {
            currency = DAOInvPayPlan.getPayPlan(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return currency;
    }

    public static int getIndex() {
        int indexCount = 0;
        try (Connection conn = accountpackage.AccountPackage.connect()) {
            indexCount = DAOInvPayPlan.getIndex(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try (Connection conn = accountpackage.AccountPackage.connect()) {
            conn.setAutoCommit(false);
            DAOInvPayPlan.deleteAllCredit(conn, code);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }
//
//    public static void deleteAll(String InvoNo) {
//        try {
//            Connection conn = DBConnection.getConnection();
//            DAOInvPayPlan.deleteAllPayPlan(conn, InvoNo);
//        } catch (SQLException ex) {
//            Exp.Handle(ex);
//        }
//    }

    public static OBJInvPayPlan serch(String code) {
        OBJInvPayPlan currency = null;
        try (Connection conn = accountpackage.AccountPackage.connect()) {
            conn.setAutoCommit(false);
            currency = DAOInvPayPlan.serch(conn, code);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return currency;
    }

    public static OBJInvPayPlan serchPP(String code) {
        OBJInvPayPlan PP = null;
        try (Connection conn = accountpackage.AccountPackage.connect()) {
            conn.setAutoCommit(false);
            PP = DAOInvPayPlan.serchPP(conn, code);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return PP;
    }

    public static String getID() {
        String ID = null;
        try (Connection conn = accountpackage.AccountPackage.connect()) {
            conn.setAutoCommit(false);
            ID = DAOInvPayPlan.genID(conn);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

    public static String getGRNID() {
        String ID = null;
        try (Connection conn = accountpackage.AccountPackage.connect()) {
            conn.setAutoCommit(false);
            ID = DAOInvPayPlan.genGRNID(conn);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

    static OBJInvPayPlan getPPDetails(String code) {
        OBJInvPayPlan obj = null;
        try (Connection conn = accountpackage.AccountPackage.connect()) {
            conn.setAutoCommit(false);
            obj = DAOInvPayPlan.getPPDetails(conn, code);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    static ArrayList<OBJPaySchedule> getSchedule(Calendar paydate, String noins, String amo) {
        ArrayList<OBJPaySchedule> obja = new ArrayList<>();
        OBJPaySchedule obj;
        int i = Integer.parseInt(noins);
        double d = 0.00;
        Calendar c = paydate;
        c.add(Calendar.MONTH, -1);
        for (int j = 0; j < i; j++) {

            c.add(Calendar.MONTH, 1);
            d = d + Double.parseDouble(amo);
            obj = new OBJPaySchedule(Locals.setDateFormat(c.getTime()), Locals.sCurrencyFormat(amo), Locals.currencyFormat(d));
            obja.add(obj);
        }
        return obja;
    }

//    public static void saveSched(ArrayList<OBJPaySchedule> objs, int Act) {
//        try {
//            Connection conn = DBConnection.getConnection();
//            DAOInvPayPlan.saveSched(objs, conn, Act);
//        } catch (SQLException ex) {
//            if (ex.getErrorCode() == 1062) {
//                JOptionPane.showMessageDialog(null, "Duplicate Agriment Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
//            } else {
//                Exp.Handle(ex);
//            }
//        }
//    }
//    public static void updateScheduleeee(OBJAgInvo objin) {
//        try (Connection conn = accountpackage.AccountPackage.connect()) {
//            conn.setAutoCommit(false);
//            DAOInvPayPlan.updateSchedule(objin, conn);
//            conn.commit();
//            conn.close();
//        } catch (SQLException ex) {
//            if (ex.getErrorCode() == 1062) {
//                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
//            } else {
//                Exp.Handle(ex);
//            }
//        }
//    }
    static ArrayList<OBJPaySchedule> loadSchedule(String cid) {
        ArrayList<OBJPaySchedule> obja = new ArrayList<OBJPaySchedule>();

        try (Connection conn = accountpackage.AccountPackage.connect()) {
            conn.setAutoCommit(false);
            obja = DAOInvPayPlan.loadSchedule(cid, conn);
            conn.commit();
        } catch (SQLException ex) {

            Exp.Handle(ex);

        }

        return obja;
    }

}
