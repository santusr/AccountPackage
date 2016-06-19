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
public class OBJInstallPay {
  private String CreditId;
  private String install;
  private String DueDate;
  private String Amount;
  private String ClearDate;
  private String Status;
  private String invoNo;
  private String installNo;
  private String LatePay;
  private String TotInstallVal;
  private String PayAmount;
  private String Balance;
  private String SPDisc;
  private String ID;

    public OBJInstallPay(){
    
    }
    
    public OBJInstallPay(String CreditId, String install, String DueDate, String Amount, String ClearDate, String Status, String invoNo, String installNo, String LatePay, String TotInstallVal, String PayAmount, String Balance, String SPDisc, String ID) {
        this.CreditId = CreditId;
        this.install = install;
        this.DueDate = DueDate;
        this.Amount = Amount;
        this.ClearDate = ClearDate;
        this.Status = Status;
        this.invoNo = invoNo;
        this.installNo = installNo;
        this.LatePay = LatePay;
        this.TotInstallVal = TotInstallVal;
        this.PayAmount = PayAmount;
        this.Balance = Balance;
        this.SPDisc = SPDisc;
        this.ID = ID;
    }

    public String getCreditId() {
        return CreditId;
    }

    public void setCreditId(String CreditId) {
        this.CreditId = CreditId;
    }

    public String getInstall() {
        return install;
    }

    public void setInstall(String install) {
        this.install = install;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getClearDate() {
        return ClearDate;
    }

    public void setClearDate(String ClearDate) {
        this.ClearDate = ClearDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getInvoNo() {
        return invoNo;
    }

    public void setInvoNo(String invoNo) {
        this.invoNo = invoNo;
    }

    public String getInstallNo() {
        return installNo;
    }

    public void setInstallNo(String installNo) {
        this.installNo = installNo;
    }

    public String getLatePay() {
        return LatePay;
    }

    public void setLatePay(String LatePay) {
        this.LatePay = LatePay;
    }

    public String getTotInstallVal() {
        return TotInstallVal;
    }

    public void setTotInstallVal(String TotInstallVal) {
        this.TotInstallVal = TotInstallVal;
    }

    public String getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(String PayAmount) {
        this.PayAmount = PayAmount;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }

    public String getSPDisc() {
        return SPDisc;
    }

    public void setSPDisc(String SPDisc) {
        this.SPDisc = SPDisc;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
  
}
