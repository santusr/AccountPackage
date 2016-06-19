/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.receiptvoucher.onaccount;

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
import javax.swing.JOptionPane;
import system.accounts.transaction.cheque.DAOCheque;
import system.accounts.transaction.cheque.OBJCheque;
import system.accounts.transaction.receiptvoucher.againstinvoice.DAOVouch;

/**
 *
 * @author RoWi
 */
class SERRecOnAccount {
    
    static boolean save(
            OBJVoucherOnAccount voucherOnAccount,
            ArrayList<OBJCheque> cheques, 
            OBJTransaction transaction, 
            OBJPayment payment, 
            ArrayList<OBJPaymentInfo> paymentsInfo, 
            ArrayList<OBJAccountTrans> accountTranses,
            ArrayList<OBJItemTransaction> itemTransactions) {
 
        try {
            Connection conn = accountpackage.AccountPackage.connect();
            conn.setAutoCommit(false);
            DAOVouch.doSave(voucherOnAccount, conn, 1);
            int b = TRSAccount.executeTrans(transaction, payment, paymentsInfo, accountTranses, itemTransactions, conn);
            if (cheques != null) {
                for (OBJCheque oBJCheque : cheques) {
                    oBJCheque.setTransaction(b+"");
                    DAOCheque.save(oBJCheque, conn, 1);
                }
            }
            conn.commit();
            return true;
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Duplicate Code is not allowed", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Exp.Handle(ex);
            }
            return false;
        }
    }
}
