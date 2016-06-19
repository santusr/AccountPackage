/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.system_transaction.item_trans;

/**
 *
 * @author RoWi
 */

public class OBJItemTransaction {
    private String transaction;
    private String transactionType;
    private String transactionDate;
    private String item;
    private String store;
    private String creditQty;
    private String debitQty;
    private String creditPrice;
    private String debitPrice;
    private String status;

    public OBJItemTransaction() {
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

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCreditQty() {
        return creditQty;
    }

    public void setCreditQty(String creditQty) {
        this.creditQty = creditQty;
    }

    public String getDebitQty() {
        return debitQty;
    }

    public void setDebitQty(String debitQty) {
        this.debitQty = debitQty;
    }

    public String getCreditPrice() {
        return creditPrice;
    }

    public void setCreditPrice(String creditPrice) {
        this.creditPrice = creditPrice;
    }

    public String getDebitPrice() {
        return debitPrice;
    }

    public void setDebitPrice(String debitPrice) {
        this.debitPrice = debitPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
