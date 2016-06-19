/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.purchase;

import accountpackage.AccountPackage;
import core.Exp;
import core.system_transaction.TRSAccount;
import core.system_transaction.TransactionType;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.item_trans.OBJItemTransaction;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import system.accounts.transaction.cheque.DAOCheque;
import system.accounts.transaction.cheque.OBJCheque;
import system.inventory.transaction.sales.paymentplan.DAOInvPayPlan;
import system.inventory.transaction.sales.paymentplan.OBJInvPayPlan;

/**
 *
 * @author sandaruwan
 */
class SERPurchase {

    public static void save(OBJPurchase obji, ArrayList<OBJPurchase> obja, int Act) throws SQLException {
        OBJPurchase obj = obji;
        Connection conn = accountpackage.AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOPurchase.save(obj, conn, Act);
            DAOPurchase.saveHistory(obja, conn, Act);
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static boolean saveAll(
            OBJPurchase obji,
            ArrayList<OBJPurchase> obja,
            OBJInvPayPlan objpp,
            ArrayList<OBJPaymentInfo> infos,
            ArrayList<OBJCheque> cheques,
            OBJTransaction transaction,
            OBJPayment payment,
            ArrayList<OBJPaymentInfo> paymentsInfo,
            ArrayList<OBJAccountTrans> accountTranses,
            ArrayList<OBJItemTransaction> itemTransactions,
            int Act) throws SQLException {

        OBJPurchase obj = obji;
        Connection conn = accountpackage.AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOPurchase.save(obj, conn, Act);
            DAOPurchase.saveHistory(obja, conn, Act);
            if (!obj.getInvoType().equals("0")) {
                DAOInvPayPlan.saveGRN(objpp, conn, Act);
            }

            int b = TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, conn);
            if (cheques != null) {
                for (OBJCheque oBJCheque : cheques) {
                    oBJCheque.setTransaction(b + "");
                    DAOCheque.save(oBJCheque, conn, Act);
                }
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            conn.rollback();
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
            return false;
        }
    }

    public static void saveHistory(ArrayList<OBJPurchase> obj, int Act) throws SQLException {
        Connection conn = accountpackage.AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOPurchase.saveHistory(obj, conn, Act);
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed11", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
    }

    public static boolean canselGRN(String code, ArrayList<OBJPurchase> purchases, String store) {
        Connection conn = AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOPurchase.doCancelTrans(code, TransactionType.GRN, conn);
            DAOPurchase.doCancel(conn, code);
            DAOPurchase.updateStock(purchases, store, conn);
            conn.commit();
            return true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SERPurchase.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Exp.Handle(ex);
            return false;
        }
    }

    public static OBJPurchase getitemCat(int Index) {
        OBJPurchase obj = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            obj = DAOPurchase.getNavi(conn, Index);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJPurchase getNavi(int Index) {
        OBJPurchase obj = null;
        try {
            try (Connection conn = accountpackage.AccountPackage.connect()) {
                obj = DAOPurchase.getNavi(conn, Index);
            }
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            indexCount = DAOPurchase.getValue(conn);
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return indexCount;
    }
//
//    public static void delete(String code) {
//        try {
//            Connection conn = accountpackage.AccountPackage.connect();
//            conn.setAutoCommit(false);
//            DAOPurchase.delete(conn, code);
//            conn.commit();
//        } catch (SQLException ex) {
//            Exp.Handle(ex);
//        }
//    }

    public static OBJPurchase serch(String code) {
        OBJPurchase obj = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            obj = DAOPurchase.serch(conn, code);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static ArrayList<OBJPurchase> InvoHistory(String code) {
        ArrayList<OBJPurchase> obj = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            obj = DAOPurchase.InvoHistory(conn, code);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
        String ID = "PUR000001";
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            ID = DAOPurchase.genID(conn);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

    public static String setName(String code, String tble, String col) {
        String Name = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            Name = DAOPurchase.genName(conn, tble, code, col);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Name;
    }

    public static Vector getUnit() {
        Vector Unit = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            Unit = DAOPurchase.getUnit(conn);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Unit;
    }

    public static String getUName(String UOMCode) {
        String UName = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            UName = DAOPurchase.getUnitName(conn, UOMCode);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return UName;
    }

    static Vector loadCurrency() {
        Vector v = new Vector();
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            v = DAOPurchase.loadCurrency(conn);
            conn.commit();
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return v;
    }

    static double getFCRate(String cc) {
        double d = 0.00;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            d = DAOPurchase.gatFCRate(conn, cc);
            conn.commit();
        } catch (SQLException e) {
        }
        return d;
    }

    static OBJPurchase getTablInfo(String itc, String text) {
        OBJPurchase obj = null;
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            obj = DAOPurchase.getTablInfo(conn, itc, text);
            conn.commit();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }
}
