/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.system_transaction.transaction;

import java.util.ArrayList;

/**
 *
 * @author RoWi
 */
public class OBJTransaction {
    private String transactionDate;
    private String transactionType;
    private String referanceNo;
    private String documentNo;
    private String loan;
    private String costCode;
    private String customerCode;
    private String note;
    private String status;
    private ArrayList<OBJTransactionHistory> transactionHistorys = new ArrayList<OBJTransactionHistory>(); 
    
    public OBJTransaction() {
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferanceNo() {
        return referanceNo;
    }

    public void setReferanceNo(String referanceNo) {
        this.referanceNo = referanceNo;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<OBJTransactionHistory> getTransactionHistorys() {
        return transactionHistorys;
    }

    public void setTransactionHistorys(ArrayList<OBJTransactionHistory> transactionHistorys) {
        this.transactionHistorys = transactionHistorys;
    }
    
}
