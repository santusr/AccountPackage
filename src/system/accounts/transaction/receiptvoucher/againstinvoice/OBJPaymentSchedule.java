/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.receiptvoucher.againstinvoice;

/**
 *
 * @author RoWi
 */
public class OBJPaymentSchedule {
    private String indexNo;
    private String dueDate;
    private String latePay;
    private String spDiscount;
    private String installValue;
    private String paid;
    private String balance;
    private String status;
    private String printOrder;
    private String type;
    private String CreditId;
    private String ID;

    public OBJPaymentSchedule() {
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getLatePay() {
        return latePay;
    }

    public void setLatePay(String latePay) {
        this.latePay = latePay;
    }

    public String getSpDiscount() {
        return spDiscount;
    }

    public void setSpDiscount(String spDiscount) {
        this.spDiscount = spDiscount;
    }

    public String getInstallValue() {
        return installValue;
    }

    public void setInstallValue(String installValue) {
        this.installValue = installValue;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrintOrder() {
        return printOrder;
    }

    public void setPrintOrder(String printOrder) {
        this.printOrder = printOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreditId() {
        return CreditId;
    }

    public void setCreditId(String CreditId) {
        this.CreditId = CreditId;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return indexNo;
    }
    
}
