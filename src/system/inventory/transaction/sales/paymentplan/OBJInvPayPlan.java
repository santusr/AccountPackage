/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.paymentplan;

/**
 *
 * @author Rabid
 */
public class OBJInvPayPlan {

    private String CreditId;
    private String InvoNo;
    private String CustCode;
    private String PayPlan;
    private String NoInstal;
    private String InterestRate;
    private String InvoCredit;
    private String TotInterest;
    private String LateCharge;
    private String TotPayble;
    private String TotPay;
    private String SDate;
    private String EDate;
    private String InvoType;
    private String InstalValue;
    private String dep1;
    private String dep2;
    private String SPDisc;
    private String SPDiscRate;

    public OBJInvPayPlan(String CreditId, String InvoNo, String PayPlan, String NoInstal, String InterestRate, String InvoCredit, String TotInterest, String LateCharge, String TotPayble, String TotPay, String SDate, String EDate, String InvoType, String InstalValue, String dep1, String dep2, String CustCode, String SPDisc, String SPDiscRate) {
        this.CreditId = CreditId;
        this.InvoNo = InvoNo;
        this.PayPlan = PayPlan;
        this.NoInstal = NoInstal;
        this.InterestRate = InterestRate;
        this.InvoCredit = InvoCredit;
        this.TotInterest = TotInterest;
        this.LateCharge = LateCharge;
        this.TotPayble = TotPayble;
        this.TotPay = TotPay;
        this.SDate = SDate;
        this.EDate = EDate;
        this.InvoType = InvoType;
        this.InstalValue = InstalValue;
        this.dep1 = dep1;
        this.dep2 = dep2;
        this.CustCode = CustCode;
        this.SPDisc = SPDisc;
        this.SPDiscRate = SPDiscRate;
    }

    public OBJInvPayPlan(String PayPlan, String NoInstal, String InterestRate) {
        this.PayPlan = PayPlan;
        this.NoInstal = NoInstal;
        this.InterestRate = InterestRate;
    }

    public String getSPDisc() {
        return SPDisc;
    }

    public void setSPDisc(String SPDisc) {
        this.SPDisc = SPDisc;
    }

    public String getSPDiscRate() {
        return SPDiscRate;
    }

    public void setSPDiscRate(String SPDiscRate) {
        this.SPDiscRate = SPDiscRate;
    }

    public String getCreditId() {
        return CreditId;
    }

    public void setCreditId(String CreditId) {
        this.CreditId = CreditId;
    }

    public String getCustCode() {
        return CustCode;
    }

    public void setCustCode(String CustCode) {
        this.CustCode = CustCode;
    }

    public String getDep1() {
        return dep1;
    }

    public void setDep1(String dep1) {
        this.dep1 = dep1;
    }
    
    public String getDep2() {
        return dep2;
    }

    public void setDep2(String dep2) {
        this.dep1 = dep2;
    }

    public String getEDate() {
        return EDate;
    }

    public void setEDate(String EDate) {
        this.EDate = EDate;
    }

    public String getInstalValue() {
        return InstalValue;
    }

    public void setInstalValue(String InstalValue) {
        this.InstalValue = InstalValue;
    }

    public String getInterestRate() {
        return InterestRate;
    }

    public void setInterestRate(String InterestRate) {
        this.InterestRate = InterestRate;
    }

    public String getInvoCredit() {
        return InvoCredit;
    }

    public void setInvoCredit(String InvoCredit) {
        this.InvoCredit = InvoCredit;
    }

    public String getInvoNo() {
        return InvoNo;
    }

    public void setInvoNo(String InvoNo) {
        this.InvoNo = InvoNo;
    }

    public String getInvoType() {
        return InvoType;
    }

    public void setInvoType(String InvoType) {
        this.InvoType = InvoType;
    }

    public String getLateCharge() {
        return LateCharge;
    }

    public void setLateCharge(String LateCharge) {
        this.LateCharge = LateCharge;
    }

    public String getNoInstal() {
        return NoInstal;
    }

    public void setNoInstal(String NoInstal) {
        this.NoInstal = NoInstal;
    }

    public String getPayPlan() {
        return PayPlan;
    }

    public void setPayPlan(String PayPlan) {
        this.PayPlan = PayPlan;
    }

    public String getSDate() {
        return SDate;
    }

    public void setSDate(String SDate) {
        this.SDate = SDate;
    }

    public String getTotInterest() {
        return TotInterest;
    }

    public void setTotInterest(String TotInterest) {
        this.TotInterest = TotInterest;
    }

    public String getTotPay() {
        return TotPay;
    }

    public void setTotPay(String TotPay) {
        this.TotPay = TotPay;
    }

    public String getTotPayble() {
        return TotPayble;
    }

    public void setTotPayble(String TotPayble) {
        this.TotPayble = TotPayble;
    }
    
}
