/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.paymentvoucher.againstinvoice;

import system.accounts.transaction.receiptvoucher.againstinvoice.*;

/**
 *
 * @author Rabid
 */
public class OBJAgInvo extends OBJVouch{

    private String CreditId;
    private String InvoNo;
    private String InstallNo;
    private String nxtPDate;
    private String PayAmount;
    private String SPDisc;
    private String Balance;

    public OBJAgInvo(String CreditId, String InvoNo, String InstallNo, String nxtPDate, String PayAmount, String SPDisc, String Balance, String VouNo, String VouDate, String TransType, String CrAcc, String Naretion, String CostCenter, String PayMod, String PrepBy, String AppBy, String Type) {
        super(VouNo, VouDate, TransType, CrAcc, Naretion, CostCenter, PayMod, PrepBy, AppBy, Type);
        this.CreditId = CreditId;
        this.InvoNo = InvoNo;
        this.InstallNo = InstallNo;
        this.nxtPDate = nxtPDate;
        this.PayAmount = PayAmount;
        this.SPDisc = SPDisc;
        this.Balance = Balance;
    }

    public String getCreditId() {
        return CreditId;
    }

    public void setCreditId(String CreditId) {
        this.CreditId = CreditId;
    }

    public String getInvoNo() {
        return InvoNo;
    }

    public void setInvoNo(String InvoNo) {
        this.InvoNo = InvoNo;
    }

    public String getInstallNo() {
        return InstallNo;
    }

    public void setInstallNo(String InstallNo) {
        this.InstallNo = InstallNo;
    }

    public String getNxtPDate() {
        return nxtPDate;
    }

    public void setNxtPDate(String nxtPDate) {
        this.nxtPDate = nxtPDate;
    }

    public String getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(String PayAmount) {
        this.PayAmount = PayAmount;
    }

    public String getSPDisc() {
        return SPDisc;
    }

    public void setSPDisc(String SPDisc) {
        this.SPDisc = SPDisc;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }
    
}
