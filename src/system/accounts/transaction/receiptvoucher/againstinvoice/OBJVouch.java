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
public abstract class OBJVouch {
    private String VouNo;
    private String VouDate;
    private String TransType;
    private String CrAcc;
    private String Naretion;
    private String CostCenter;
    private String PayMod;
    private String PrepBy;
    private String AppBy;
    private String type;
    private String RefNo;

    public OBJVouch(String VouNo, String VouDate, String TransType, String CrAcc, String Naretion, String CostCenter, String PayMod, String PrepBy, String AppBy, String type, String RefNo) {
        this.VouNo = VouNo;
        this.VouDate = VouDate;
        this.TransType = TransType;
        this.CrAcc = CrAcc;
        this.Naretion = Naretion;
        this.CostCenter = CostCenter;
        this.PayMod = PayMod;
        this.PrepBy = PrepBy;
        this.AppBy = AppBy;
        this.type = type;
        this.RefNo = RefNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVouNo() {
        return VouNo;
    }

    public void setVouNo(String VouNo) {
        this.VouNo = VouNo;
    }

    public String getVouDate() {
        return VouDate;
    }

    public void setVouDate(String VouDate) {
        this.VouDate = VouDate;
    }

    public String getTransType() {
        return TransType;
    }

    public void setTransType(String TransType) {
        this.TransType = TransType;
    }

    public String getCrAcc() {
        return CrAcc;
    }

    public void setCrAcc(String CrAcc) {
        this.CrAcc = CrAcc;
    }

    public String getNaretion() {
        return Naretion;
    }

    public void setNaretion(String Naretion) {
        this.Naretion = Naretion;
    }

    public String getCostCenter() {
        return CostCenter;
    }

    public void setCostCenter(String CostCenter) {
        this.CostCenter = CostCenter;
    }

    public String getPayMod() {
        return PayMod;
    }

    public void setPayMod(String PayMod) {
        this.PayMod = PayMod;
    }

    public String getPrepBy() {
        return PrepBy;
    }

    public void setPrepBy(String PrepBy) {
        this.PrepBy = PrepBy;
    }

    public String getAppBy() {
        return AppBy;
    }

    public void setAppBy(String AppBy) {
        this.AppBy = AppBy;
    }

    public String getRefNo() {
        return RefNo;
    }

    public void setRefNo(String RefNo) {
        this.RefNo = RefNo;
    }

}
