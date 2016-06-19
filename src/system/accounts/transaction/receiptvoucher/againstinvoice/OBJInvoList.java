/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.receiptvoucher.againstinvoice;

/**
 *
 * @author Rabid
 */
public class OBJInvoList {
    private String InvoNo;
    private String InvoDate;
    private String OriAmount;
    private String OwingAmount;
    private String PaiedAmount;
    private String InvoBalance;
    private String InstallAmount;
    private String PayDate;
    private String loanNo;
    private String status;

    public OBJInvoList(String InvoNo, String InvoDate, String OriAmount, String OwingAmount, String PaiedAmount, String InvoBalance, String InstallAmount, String PayDate, String loanNo) {
        this.InvoNo = InvoNo;
        this.InvoDate = InvoDate;
        this.OriAmount = OriAmount;
        this.OwingAmount = OwingAmount;
        this.PaiedAmount = PaiedAmount;
        this.InvoBalance = InvoBalance;
        this.InstallAmount = InstallAmount;
        this.PayDate = PayDate;
        this.loanNo = loanNo;
    }

    public String getInvoNo() {
        return InvoNo;
    }

    public void setInvoNo(String InvoNo) {
        this.InvoNo = InvoNo;
    }

    public String getInvoDate() {
        return InvoDate;
    }

    public void setInvoDate(String InvoDate) {
        this.InvoDate = InvoDate;
    }

    public String getOriAmount() {
        return OriAmount;
    }

    public void setOriAmount(String OriAmount) {
        this.OriAmount = OriAmount;
    }

    public String getOwingAmount() {
        return OwingAmount;
    }

    public void setOwingAmount(String OwingAmount) {
        this.OwingAmount = OwingAmount;
    }

    public String getPaiedAmount() {
        return PaiedAmount;
    }

    public void setPaiedAmount(String PaiedAmount) {
        this.PaiedAmount = PaiedAmount;
    }

    public String getInvoBalance() {
        return InvoBalance;
    }

    public void setInvoBalance(String InvoBalance) {
        this.InvoBalance = InvoBalance;
    }

    public String getInstallAmount() {
        return InstallAmount;
    }

    public void setInstallAmount(String InstallAmount) {
        this.InstallAmount = InstallAmount;
    }

    public String getPayDate() {
        return PayDate;
    }

    public void setPayDate(String PayDate) {
        this.PayDate = PayDate;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
