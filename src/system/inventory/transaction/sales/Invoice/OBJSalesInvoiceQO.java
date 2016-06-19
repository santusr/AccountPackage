/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.Invoice;

/**
 *
 * @author dell
 */
public class OBJSalesInvoiceQO {

    private String InvoNo;
    private String InvoDate;
    private String CussCode;
    private String CostCode;
    private String CrAcc;
    private String FCCode;
    private String FCRate;
    private String RepCode;
    private String StoreCode;
    private String AreaCode;
    private String PaymentTerms;
    private String PrepBy;
    private String ApproBy;
    private String Remarks;
    private String GrossAmount;
    private String TotalDiscount;
    private String DiscountRate;
    private String Other;
    private String NetAmount;
    private String PayAmount;
    private String DueAmount;
    private String UserId;
    private String InvoType;
    private String Ref;
    private String CPDate;
    private String loanNo;
    
    private String ItemCode;
    private String ItemDescription;
    private String Quantity;
    private String Rate;
    private String Discount;
    private String UnitCode;
    private String Net;
    private String Warranty;
    private String sn;
    private String cost_rate;
    private String status;
    
    private String Name;
    private String Unit;
    private String SellingRate;
    private String MinSellingRate;
    private String War;
    private String Dis;
    private String cost;
    private String minLeval;
    private String stockInHand;

    public OBJSalesInvoiceQO() {
    }

    public OBJSalesInvoiceQO(String InvoNo, String InvoDate, String CussCode, String CostCode, String CrAcc, String FCCode, String FCRate, String RepCode, String StoreCode, String AreaCode, String PaymentTerms, String PrepBy, String ApproBy, String Remarks, String GrossAmount, String TotalDiscount, String DiscountRate, String Other, String NetAmount, String PayAmount, String DueAmount, String UserId, String InvoType, String Ref,String CPDate, String loanNo) {
        this.InvoNo = InvoNo;
        this.InvoDate = InvoDate;
        this.CussCode = CussCode;
        this.CostCode = CostCode;
        this.CrAcc = CrAcc;
        this.FCCode = FCCode;
        this.FCRate = FCRate;
        this.RepCode = RepCode;
        this.StoreCode = StoreCode;
        this.AreaCode = AreaCode;
        this.PaymentTerms = PaymentTerms;
        this.PrepBy = PrepBy;
        this.ApproBy = ApproBy;
        this.Remarks = Remarks;
        this.GrossAmount = GrossAmount;
        this.TotalDiscount = TotalDiscount;
        this.DiscountRate = DiscountRate;
        this.Other = Other;
        this.NetAmount = NetAmount;
        this.PayAmount = PayAmount;
        this.DueAmount = DueAmount;
        this.UserId = UserId;
        this.InvoType = InvoType;
        this.Ref = Ref;
        this.CPDate = CPDate;
        this.loanNo = loanNo;
    }
    public OBJSalesInvoiceQO(String InvoNo, String ItemCode, String ItemDescription, String Quantity, String Rate, String Discount, String UnitCode, String Net, String Warranty, String sn, String StoreCode, String cost_rate, String status) {
        this.InvoNo = InvoNo;
        this.ItemCode = ItemCode;
        this.ItemDescription = ItemDescription;
        this.Quantity = Quantity;
        this.Rate = Rate;
        this.Discount = Discount;
        this.UnitCode = UnitCode;
        this.Net = Net;
        this.Warranty = Warranty;
        this.sn = sn;
        this.StoreCode = StoreCode;
        this.cost_rate = cost_rate;
        this.status = status;
    }

    public String getCost_rate() {
        return cost_rate;
    }

    public void setCost_rate(String cost_rate) {
        this.cost_rate = cost_rate;
    }

    public String getDis() {
        return Dis;
    }

    public void setDis(String Dis) {
        this.Dis = Dis;
    }

    public OBJSalesInvoiceQO(String ItemCode, String Name, String Unit, String SellingRate, String MinSellingRate, String War, String Dis, String cost, String minLeval, String stockInHand, String cost_rate) {
        this.ItemCode = ItemCode;
        this.Name = Name;
        this.Unit = Unit;
        this.SellingRate = SellingRate;
        this.MinSellingRate =  MinSellingRate;
        this.War = War;
        this.Dis = Dis;
        this.cost = cost;
        this.minLeval = minLeval;
        this.stockInHand = stockInHand;
        this.cost_rate = cost_rate;
    }

    public String getMinSellingRate() {
        return MinSellingRate;
    }

    public void setMinSellingRate(String MinSellingRate) {
        this.MinSellingRate = MinSellingRate;
    }

    public String getApproBy() {
        return ApproBy;
    }

    public void setApproBy(String ApproBy) {
        this.ApproBy = ApproBy;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String AreaCode) {
        this.AreaCode = AreaCode;
    }

    public String getCostCode() {
        return CostCode;
    }

    public void setCostCode(String CostCode) {
        this.CostCode = CostCode;
    }

    public String getCrAcc() {
        return CrAcc;
    }

    public void setCrAcc(String CrAcc) {
        this.CrAcc = CrAcc;
    }

    public String getCussCode() {
        return CussCode;
    }

    public void setCussCode(String CussCode) {
        this.CussCode = CussCode;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String Discount) {
        this.Discount = Discount;
    }

    public String getDiscountRate() {
        return DiscountRate;
    }

    public void setDiscountRate(String DiscountRate) {
        this.DiscountRate = DiscountRate;
    }

    public String getDueAmount() {
        return DueAmount;
    }

    public void setDueAmount(String DueAmount) {
        this.DueAmount = DueAmount;
    }

    public String getFCCode() {
        return FCCode;
    }

    public void setFCCode(String FCCode) {
        this.FCCode = FCCode;
    }

    public String getFCRate() {
        return FCRate;
    }

    public void setFCRate(String FCRate) {
        this.FCRate = FCRate;
    }

    public String getGrossAmount() {
        return GrossAmount;
    }

    public void setGrossAmount(String GrossAmount) {
        this.GrossAmount = GrossAmount;
    }

    public String getInvoDate() {
        return InvoDate;
    }

    public void setInvoDate(String InvoDate) {
        this.InvoDate = InvoDate;
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

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String ItemCode) {
        this.ItemCode = ItemCode;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String ItemDescription) {
        this.ItemDescription = ItemDescription;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNet() {
        return Net;
    }

    public void setNet(String Net) {
        this.Net = Net;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String NetAmount) {
        this.NetAmount = NetAmount;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String Other) {
        this.Other = Other;
    }

    public String getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(String PayAmount) {
        this.PayAmount = PayAmount;
    }

    public String getPaymentTerms() {
        return PaymentTerms;
    }

    public void setPaymentTerms(String PaymentTerms) {
        this.PaymentTerms = PaymentTerms;
    }

    public String getPrepBy() {
        return PrepBy;
    }

    public void setPrepBy(String PrepBy) {
        this.PrepBy = PrepBy;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String Rate) {
        this.Rate = Rate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getRepCode() {
        return RepCode;
    }

    public void setRepCode(String RepCode) {
        this.RepCode = RepCode;
    }

    public String getSellingRate() {
        return SellingRate;
    }

    public void setSellingRate(String SellingRate) {
        this.SellingRate = SellingRate;
    }

    public String getStoreCode() {
        return StoreCode;
    }

    public void setStoreCode(String StoreCode) {
        this.StoreCode = StoreCode;
    }

    public String getTotalDiscount() {
        return TotalDiscount;
    }

    public void setTotalDiscount(String TotalDiscount) {
        this.TotalDiscount = TotalDiscount;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String UnitCode) {
        this.UnitCode = UnitCode;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getWar() {
        return War;
    }

    public void setWar(String War) {
        this.War = War;
    }

    public String getWarranty() {
        return Warranty;
    }

    public void setWarranty(String Warranty) {
        this.Warranty = Warranty;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getRef() {
        return Ref;
    }

    public void setRef(String Ref) {
        this.Ref = Ref;
    }

    public String getCPDate() {
        return CPDate;
    }

    public void setCPDate(String CPDate) {
        this.CPDate = CPDate;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMinLeval() {
        return minLeval;
    }

    public void setMinLeval(String minLeval) {
        this.minLeval = minLeval;
    }

    public String getStockInHand() {
        return stockInHand;
    }

    public void setStockInHand(String stockInHand) {
        this.stockInHand = stockInHand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ItemCode; //To change body of generated methods, choose Tools | Templates.
    }

}
