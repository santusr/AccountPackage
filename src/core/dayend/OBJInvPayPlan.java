/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.dayend;

/**
 *
 * @author Rabid
 */
public class OBJInvPayPlan {

    private String CreditId;
    private String InstallNo;
    private String Install;
    private String InvoNo;
    private String CustCode;
    private String balance;
    private String DueDate;
    private String latePay;
    private String costCode;
    private String loan;

    public OBJInvPayPlan() {
    }

    public String getCreditId() {
        return CreditId;
    }

    public void setCreditId(String CreditId) {
        this.CreditId = CreditId;
    }

    public String getInstallNo() {
        return InstallNo;
    }

    public void setInstallNo(String InstallNo) {
        this.InstallNo = InstallNo;
    }

    public String getInstall() {
        return Install;
    }

    public void setInstall(String Install) {
        this.Install = Install;
    }

    public String getInvoNo() {
        return InvoNo;
    }

    public void setInvoNo(String InvoNo) {
        this.InvoNo = InvoNo;
    }

    public String getCustCode() {
        return CustCode;
    }

    public void setCustCode(String CustCode) {
        this.CustCode = CustCode;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    public String getLatePay() {
        return latePay;
    }

    public void setLatePay(String latePay) {
        this.latePay = latePay;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }
    
}
