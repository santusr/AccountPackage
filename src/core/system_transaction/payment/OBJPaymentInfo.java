/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.system_transaction.payment;

/**
 *
 * @author RoWi
 */
public class OBJPaymentInfo {
    private String payment;
    private String paymentSetting;
    private String amount;
    private String transaction;
    private String transactionType;
    private String referance_no;

    public OBJPaymentInfo() {
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPaymentSetting() {
        return paymentSetting;
    }

    public void setPaymentSetting(String paymentSetting) {
        this.paymentSetting = paymentSetting;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferance_no() {
        return referance_no;
    }

    public void setReferance_no(String referance_no) {
        this.referance_no = referance_no;
    }

}
