/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.reports;

/**
 *
 * @author Administrator
 */
class OBJArrears {
    private String AccCode;
    private String Name;
    private String InvoNo;
    private String Install;
    private String Amount;
    private String LatePay;
    private String totArrears;
    private String dueDate;

    public OBJArrears(String AccCode, String Name, String InvoNo, String Install, String Amount, String LatePay, String totArrears, String dueDate) {
        this.AccCode = AccCode;
        this.Name = Name;
        this.InvoNo = InvoNo;
        this.Install = Install;
        this.Amount = Amount;
        this.LatePay = LatePay;
        this.totArrears = totArrears;
        this.dueDate = dueDate;
    }

    public String getAccCode() {
        return AccCode;
    }

    public void setAccCode(String AccCode) {
        this.AccCode = AccCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getInvoNo() {
        return InvoNo;
    }

    public void setInvoNo(String InvoNo) {
        this.InvoNo = InvoNo;
    }

    public String getInstall() {
        return Install;
    }

    public void setInstall(String Install) {
        this.Install = Install;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getLatePay() {
        return LatePay;
    }

    public void setLatePay(String LatePay) {
        this.LatePay = LatePay;
    }

    public String getTotArrears() {
        return totArrears;
    }

    public void setTotArrears(String totArrears) {
        this.totArrears = totArrears;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
}
