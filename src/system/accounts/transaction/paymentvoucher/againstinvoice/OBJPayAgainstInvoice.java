/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.paymentvoucher.againstinvoice;

import system.accounts.transaction.receiptvoucher.againstinvoice.*;

/**
 *
 * @author dell
 */
public class OBJPayAgainstInvoice {
    private String VouNo;
    private String VouDate;
    private String TransType;
    private String CustAccNo;
    private String CostCenter;
    private String Naration;

    public OBJPayAgainstInvoice(String VouNo, String VouDate, String TransType, String CustAccNo, String CostCenter, String Naration) {
        this.VouNo = VouNo;
        this.VouDate = VouDate;
        this.TransType = TransType;
        this.CustAccNo = CustAccNo;
        this.CostCenter = CostCenter;
        this.Naration = Naration;
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

    public String getCustAccNo() {
        return CustAccNo;
    }

    public void setCustAccNo(String CustAccNo) {
        this.CustAccNo = CustAccNo;
    }

    public String getCostCenter() {
        return CostCenter;
    }

    public void setCostCenter(String CostCenter) {
        this.CostCenter = CostCenter;
    }

    public String getNaration() {
        return Naration;
    }

    public void setNaration(String Naration) {
        this.Naration = Naration;
    }

}
