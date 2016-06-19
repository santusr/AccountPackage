/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.voucher_cancel;

import accountpackage.AccountPackage;
import core.Exp;
import core.system_transaction.TransactionType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RoWi
 */
class SERVoucherCancel {

    static ArrayList<OBJVoucher> getVoucherHeaders(String transDate, String transactionType) {
        ArrayList<OBJVoucher> vouchers = new ArrayList<>();
        try {
            Connection con = AccountPackage.connect();
            vouchers = DAOVoucherCancel.getVoucherHeaders(transDate, transactionType, con);
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return vouchers;
    }

    static ArrayList<OBJVoucher> getVouchers(String transDate, String transactionType, String voucherNo) {
        ArrayList<OBJVoucher> vouchers = new ArrayList<>();
        try {
            Connection con = AccountPackage.connect();
            vouchers = DAOVoucherCancel.getVouchers(transDate, transactionType, voucherNo, con);
        } catch (SQLException e) {
            Exp.Handle(e);
        }
        return vouchers;
    }

    static boolean doCancel(String code, String invoNo, String transType, String amount) {
        Connection conn = accountpackage.AccountPackage.connect();
        try {
            conn.setAutoCommit(false);
            DAOVoucherCancel.doCancelTrans(code, transType, conn);
//            if ((invoNo != null && !invoNo.equals("")) && transType.equals(TransactionType.VOUCHER)) {
//                DAOVoucherCancel.doRallBackCreditGRN(invoNo, transType, amount, conn);
//            } else if ((invoNo != null && !invoNo.equals("")) && transType.equals(TransactionType.RECEIPT)) {
                DAOVoucherCancel.doRallBackCredit(invoNo, transType, amount, conn);
//            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(SERVoucherCancel.class.getName()).log(Level.SEVERE, null, ex);
            }
            Exp.Handle(e);
            return false;
        }
    }

}
