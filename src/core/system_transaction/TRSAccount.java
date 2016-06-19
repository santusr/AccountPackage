/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.system_transaction;

import core.system_transaction.account_trans.OBJAccountTrans;
import core.system_transaction.payment.OBJPayment;
import core.system_transaction.payment.OBJPaymentInfo;
import core.system_transaction.transaction.OBJTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import core.system_transaction.item_trans.OBJItemTransaction;

/**
 *
 * @author RABID
 */
public class TRSAccount {
    
    public static int executeTrans(
            OBJTransaction transaction,
            OBJPayment payment,
            ArrayList<OBJPaymentInfo> paymentsInfo,
            ArrayList<OBJAccountTrans> accountTranses,
            ArrayList<OBJItemTransaction> itemTransactions,
            Connection con) throws SQLException {
        int transIndexNo = 0;
        String sql = "INSERT INTO transaction (transaction_date, transaction_type, referance_no, document_no, loan, cost_code, cust_code, note, status, user) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement st = con.prepareStatement(sql, 1);
        st.setString(1, transaction.getTransactionDate());
        st.setString(2, transaction.getTransactionType());
        st.setString(3, transaction.getReferanceNo());
        st.setString(4, transaction.getDocumentNo());
        st.setString(5, transaction.getLoan());
        st.setString(6, transaction.getCostCode());
        st.setString(7, transaction.getCustomerCode());
        st.setString(8, transaction.getNote());
        st.setString(9, transaction.getStatus());
        st.setString(10, accountpackage.AccountPackage.user);
        st.executeUpdate();
        ResultSet resTrans;
        resTrans = st.getGeneratedKeys();
        if (resTrans.next()) {
            transIndexNo = resTrans.getInt(1);

            //save Payment
            if (payment != null) {
                
        System.out.println("2");
                sql = "INSERT INTO payment (transaction_date, cost_code, cust_code, cashire_session, amount, transaction, transaction_type, status, user) VALUES (?,?,?,?,?,?,?,?,?)";
                PreparedStatement st1 = con.prepareStatement(sql, 1);
                st1.setString(1, payment.getTransactionDate());
                st1.setString(2, payment.getCostCode());
                st1.setString(3, payment.getCustomerCode());
                st1.setString(4, payment.getCashireSession());
                st1.setString(5, payment.getAmount());
                st1.setString(6, transIndexNo + "");
                st1.setString(7, payment.getTransactionType());
                st1.setString(8, payment.getStatus());
                st1.setString(9, accountpackage.AccountPackage.user);
                st1.executeUpdate();
        System.out.println("3");
                
                ResultSet resPayment;
                resPayment = st1.getGeneratedKeys();
                if (resPayment.next()) {
                    
                    int paymentIndexNo = resPayment.getInt(1);

                    //save Payment Information
                    for (OBJPaymentInfo paymentInformation : paymentsInfo) {
        System.out.println("4");
                        sql = "INSERT INTO payment_information (payment, payment_setting, amount, transaction, transaction_type, refarance_no) VALUES (?,?,?,?,?,?)";
                        PreparedStatement st2 = con.prepareStatement(sql);
                        st2.setString(1, paymentIndexNo + "");
                        st2.setString(2, paymentInformation.getPaymentSetting());
                        st2.setString(3, paymentInformation.getAmount());
                        st2.setString(4, transIndexNo + "");
                        st2.setString(5, paymentInformation.getTransactionType());
                        st2.setString(6, paymentInformation.getReferance_no());
                        st2.executeUpdate();
                    }
                    
                }
                
            }
            if (accountTranses != null) {
        System.out.println("5");
                for (OBJAccountTrans accountTrans : accountTranses) {
                    sql = "INSERT INTO account_trans (transaction_date, cost_code, account_setting, description, account, credit_amount, debit_amount, transaction, transaction_type, type, status, user) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement st3 = con.prepareStatement(sql);
                    st3.setString(1, accountTrans.getTransactionDate());
                    st3.setString(2, accountTrans.getCostCode());
                    st3.setString(3, accountTrans.getAccountSetting());
                    st3.setString(4, accountTrans.getDescription());
                    st3.setString(5, accountTrans.getAccount());
                    st3.setString(6, accountTrans.getCreditAmount());
                    st3.setString(7, accountTrans.getDebitAmount());
                    st3.setString(8, transIndexNo + "");
                    st3.setString(9, accountTrans.getTransactionType());
                    st3.setString(10, accountTrans.getType());
                    st3.setString(11, accountTrans.getStatus());
                    st3.setString(12, accountpackage.AccountPackage.user);
                    st3.executeUpdate();
        System.out.println("6");
                }
            }
            
            if (itemTransactions != null) {
                for (OBJItemTransaction itemTransaction : itemTransactions) {
                    sql = "INSERT INTO item_transaction (transaction, transaction_type, transaction_date, item, store, credit_quantity, debit_quantity, credit_price, debit_price, status, user) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement st3 = con.prepareStatement(sql);
                    st3.setString(1, transIndexNo + "");
                    st3.setString(2, itemTransaction.getTransactionType());
                    st3.setString(3, itemTransaction.getTransactionDate());
                    st3.setString(4, itemTransaction.getItem());
                    st3.setString(5, itemTransaction.getStore());
                    st3.setString(6, itemTransaction.getCreditQty());
                    st3.setString(7, itemTransaction.getDebitQty());
                    st3.setString(8, itemTransaction.getCreditPrice());
                    st3.setString(9, itemTransaction.getDebitPrice());
                    st3.setString(10, itemTransaction.getStatus());
                    st3.setString(11, accountpackage.AccountPackage.user);
                    st3.executeUpdate();
                }
            }
        }
        return transIndexNo;
    }
}
