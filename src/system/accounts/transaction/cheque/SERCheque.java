/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.cheque;

import core.DBConnection;
import core.Exp;
import core.system_transaction.TRSAccount;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class SERCheque {

    public static void save(OBJCheque obj, int Act) {
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            DAOCheque.save(obj, conn, Act);
            conn.commit();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Cost Center Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static OBJCheque getNavi(int Index) {
        OBJCheque obj = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            obj = DAOCheque.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            indexCount = DAOCheque.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }

    public static void delete(String code) {
        try {
            Connection conn = DBConnection.getConnection();
            DAOCheque.delete(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
    }

    public static OBJCheque serch(String code) {
        OBJCheque obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOCheque.serch(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    static OBJCheque serchCNo(String cno) {
        OBJCheque obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOCheque.serchCNo(conn, cno);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
        String ID = null;
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOCheque.genID(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

    public static Vector getCurrency() {
        Vector currency = null;
        try {
            Connection conn = DBConnection.getConnection();
            currency = DAOCheque.getCurrency(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return currency;
    }

    public static String setName(String code, String tble, String col) {
        String Name = null;
        try {
            Connection conn = DBConnection.getConnection();
            Name = DAOCheque.genName(conn, tble, code, col);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Name;
    }

    public static Vector getSRep() {
        Vector Srep = null;
        try {
            Connection conn = DBConnection.getConnection();
            Srep = DAOCheque.getSrep(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Srep;
    }

    public static String getSRep(String code) {
        String Srep = null;
        try {
            Connection conn = DBConnection.getConnection();
            Srep = DAOCheque.getSrep(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Srep;
    }

    public static String getCurrency(String code) {
        String Currency = null;
        try {
            Connection conn = DBConnection.getConnection();
            Currency = DAOCheque.getCurrency(conn, code);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Currency;
    }

    static double getFCRate(String cc) {
        double d = 0.00;
        try (Connection conn = accountpackage.AccountPackage.connect()) {
            d = DAOCheque.gatFCRate(conn, cc);
        } catch (Exception e) {
            Exp.Handle(e);
        }
        return d;
    }

    public static boolean deposit(
            OBJCheque cheque,
            OBJTransaction transaction,
            OBJPayment payment,
            ArrayList<OBJPaymentInfo> paymentsInfo,
            ArrayList<OBJAccountTrans> accountTranses,
            ArrayList<OBJItemTransaction> itemTransactions) {
        try {
            Connection con = accountpackage.AccountPackage.connect();
            con.setAutoCommit(false);
            int t = TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, con);
            switch (cheque.getStatus()) {
                case ChequeStatus.DEPOSIT:
                    cheque.setDepositTransaction(t + "");
                    break;
                case ChequeStatus.REALIZE:
                case ChequeStatus.BOUNCED:
                    cheque.setRealizeTransaction(t + "");
                    break;
            }
            DAOCheque.deposit(cheque, con);
            con.commit();
            return true;
        } catch (SQLException e) {
            core.Exp.Handle(e);
            return false;
        }
    }

}
