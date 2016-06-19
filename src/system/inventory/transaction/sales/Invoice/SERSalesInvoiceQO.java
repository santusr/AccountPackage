/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.Invoice;

import accountpackage.AccountPackage;
import core.DBConnection;
import core.Exp;
import core.system_transaction.TRSAccount;
import core.system_transaction.TransactionType;
import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.payment.OBJPayment;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import system.accounts.transaction.cheque.DAOCheque;
import system.accounts.transaction.cheque.OBJCheque;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import core.system_transaction.item_trans.OBJItemTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.inventory.transaction.sales.paymentplan.DAOInvPayPlan;
import system.inventory.transaction.sales.paymentplan.OBJInvPayPlan;
import system.inventory.transaction.sales.paymentplan.OBJPaySchedule;

/**
 *
 * @author sandaruwan
 */
class SERSalesInvoiceQO {

//    public static void savee(OBJSalesInvoiceQO obji, ArrayList<OBJSalesInvoiceQO> obja, Object o, String invoNo,int Act) throws SQLException {
//        OBJSalesInvoiceQO obj = obji;
//        Connection conn = DBConnection.getConnection();
//        try {
//            conn.setAutoCommit(false);
//            DAOSalesInvoiceQO.save(obj, conn, Act);
//            DAOSalesInvoiceQO.saveHistory(obja, conn, Act);
//
//            if ((obj.getPaymentTerms()).equals("CHEQUE")) {
//                DAOCheque.save((OBJCheque) o, conn, Act);
//            }
//            conn.commit();
//            conn.close();
//        } catch (SQLException ex) {
//            conn.rollback();
//            if (ex.getErrorCode() == 1062) {
//                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
//            } else {
//                Exp.Handle(ex);
//            }
//        }
//    }
    public static boolean saveAll(
            OBJSalesInvoiceQO obj,
            ArrayList<OBJSalesInvoiceQO> obja,
            OBJInvPayPlan objpp,
            ArrayList<OBJPaySchedule> objs,
            ArrayList<OBJCheque> cheques,
            OBJTransaction transaction,
            OBJPayment payment,
            ArrayList<OBJPaymentInfo> paymentsInfo,
            ArrayList<OBJAccountTrans> accountTranses,
            ArrayList<OBJItemTransaction> itemTransactions,
            String invoNo,
            String privInvoNo,
            int Act) throws SQLException {
        boolean b = false;
        Connection conn = accountpackage.AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOSalesInvoiceQO.save(obj, conn, Act);
            DAOSalesInvoiceQO.saveHistory(obja, invoNo, conn, Act);
            if (obj.getPaymentTerms().equals("INVO")) {
                DAOSalesInvoiceQO.updateRecall(privInvoNo, conn);
            }
            if (!obj.getInvoType().equals("0")) {
                int i = DAOInvPayPlan.save(objpp, conn, Act);
                ArrayList<OBJPaySchedule> schedules = new ArrayList<>();
                for (OBJPaySchedule oBJPaySchedule : objs) {
                    oBJPaySchedule.setCreditId(i + "");
                    schedules.add(oBJPaySchedule);
                }
                DAOInvPayPlan.saveSched(schedules, conn, Act);
            }

            int bb = TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, conn);
            if (cheques != null) {
                for (OBJCheque oBJCheque : cheques) {
                    oBJCheque.setTransaction(bb + "");
                    DAOCheque.save(oBJCheque, conn, Act);
                }
            }

            conn.commit();
            b = true;
        } catch (SQLException ex) {
            conn.rollback();
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
        }
        return b;
    }
// 
//    public static void saveHistory(ArrayList <OBJSalesInvoiceQO> obj, int Act) {
//        try {
//            Connection conn = DBConnection.getConnection();
//            DAOSalesInvoiceQO.saveHistory(obj, conn, Act);
//            conn.close();
//        } catch (SQLException ex) {
//            if (ex.getErrorCode() == 1062) {
//                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
//            } else {
//                Exp.Handle(ex);
//            }
//        }
//    }

    public static OBJSalesInvoiceQO getitemCat(int Index) {
        OBJSalesInvoiceQO obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesInvoiceQO.getNavi(conn, Index);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJSalesInvoiceQO getNavi(int Index) {
        OBJSalesInvoiceQO obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesInvoiceQO.getNavi(conn, Index);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
        return obj;
    }

    public static int getIndex() {
        int indexCount = 0;
        Connection conn = AccountPackage.connect();
        try {
            indexCount = DAOSalesInvoiceQO.getValue(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }
        return indexCount;
    }

    public static boolean canselInvoice(String code, ArrayList<OBJSalesInvoiceQO> invoiceQOs, String store) {
        Connection conn = AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOSalesInvoiceQO.doCancelTrans(code, TransactionType.INVOICE, conn);
            DAOSalesInvoiceQO.doCancel(conn, code);
            DAOSalesInvoiceQO.updateStock(invoiceQOs, store, conn);
            conn.commit();
            return true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SERSalesInvoiceQO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Exp.Handle(ex);
            return false;
        }
    }

    public static OBJSalesInvoiceQO serch(String code) {
        OBJSalesInvoiceQO obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesInvoiceQO.serch(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static ArrayList<OBJSalesInvoiceQO> InvoHistory(String code) {
        ArrayList<OBJSalesInvoiceQO> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesInvoiceQO.InvoHistory(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static OBJSalesInvoiceQO searchSuspend(String code) {
        OBJSalesInvoiceQO obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesInvoiceQO.serchSuspend(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static ArrayList<OBJSalesInvoiceQO> InvoHistorySuspend(String code) {
        ArrayList<OBJSalesInvoiceQO> obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesInvoiceQO.InvoHistorySuspend(conn, code);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    public static String getID() {
        String ID = "INV000001";
        try {
            Connection conn = DBConnection.getConnection();
            ID = DAOSalesInvoiceQO.genID(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return ID;
    }

    public static String setName(String code, String tble, String col) {
        String Name = null;
        try {
            Connection conn = DBConnection.getConnection();
            Name = DAOSalesInvoiceQO.genName(conn, tble, code, col);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Name;
    }

    public static Vector getUnit() {
        Vector Unit = null;
        try {
            Connection conn = DBConnection.getConnection();
            Unit = DAOSalesInvoiceQO.getUnit(conn);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return Unit;
    }

    public static String getUName(String UOMCode) {
        String UName = null;
        try {
            Connection conn = DBConnection.getConnection();
            UName = DAOSalesInvoiceQO.getUnitName(conn, UOMCode);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return UName;
    }

    static Vector loadCurrency() {
        Vector v = new Vector();

        try (Connection conn = DBConnection.getConnection()) {
            v = DAOSalesInvoiceQO.loadCurrency(conn);

        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return v;
    }

    static double getFCRate(String cc) {
        double d = 0.00;
        try {
            Connection conn = DBConnection.getConnection();
            d = DAOSalesInvoiceQO.gatFCRate(conn, cc);
            conn.close();
        } catch (Exception e) {
        }
        return d;
    }

    static OBJSalesInvoiceQO getTablInfo(String itc, String text) {
        OBJSalesInvoiceQO obj = null;
        try {
            Connection conn = DBConnection.getConnection();
            obj = DAOSalesInvoiceQO.getTablInfo(conn, itc, text);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return obj;
    }

    static Vector itemList(String toString) {
        Vector v;
        try {
            Connection conn = DBConnection.getConnection();
            v = DAOSalesInvoiceQO.getItem(conn, toString);
            conn.close();
            return v;
        } catch (SQLException ex) {
            Exp.Handle(ex);
            return null;
        }

    }

}
