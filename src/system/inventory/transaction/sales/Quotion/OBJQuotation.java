/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.Quotion;

/**
 *
 * @author dell
 */
public class OBJQuotation {

    private String QuotNo;
    private String QuotDate;
    private String CussCode;
    private String RepCode;
    private String CostCode;
    private String StoreCode;
    private String AreaCode;
    private String FCCode;
    private String FCRate;
    private String PaymentTerms;
    private String DeliDate;
    private String PrepBy;
    private String ApproBy;
    private String Remarks;
    private String GrossAmount;
    private String TotalDiscount;
    private String DiscountRate;
    private String NetAmount;
    private String payAmount;
    private String balance;
    private String UserId;
    
    private String ItemCode;
    private String ItemDescription;
    private String Quantity;
    private String Rate;
    private String Discount;
    private String UnitCode;
    private String Net;
    private String Warranty;
    private String sn;
    
    private String Name;
    private String Unit;
    private Double SellingRate;
    private String War;
        
    public OBJQuotation(String QuotNo, String QuotDate, String CussCode, String RepCode, String CostCode, String StoreCode, String AreaCode, String FCCode, String FCRate, String PaymentTerms, String DeliDate, String PrepBy, String ApproBy, String Remarks, String GrossAmount, String TotalDiscount, String DiscountRate, String NetAmount, String payAmount, String balance, String UserId) {
        this.QuotNo = QuotNo;
        this.QuotDate = QuotDate;
        this.CussCode = CussCode;
        this.RepCode = RepCode;
        this.CostCode = CostCode;
        this.StoreCode = StoreCode;
        this.AreaCode = AreaCode;
        this.FCCode = FCCode;
        this.FCRate = FCRate;
        this.PaymentTerms = PaymentTerms;
        this.DeliDate = DeliDate;
        this.PrepBy = PrepBy;
        this.ApproBy = ApproBy;
        this.Remarks = Remarks;
        this.GrossAmount = GrossAmount;
        this.TotalDiscount = TotalDiscount;
        this.DiscountRate = DiscountRate;
        this.payAmount = payAmount;
        this.balance = balance;
        this.NetAmount = NetAmount;
        this.UserId = UserId;
    }

    public OBJQuotation(String QuotNo, String ItemCode, String ItemDescription, String Quantity, String Rate, String Discount, String UnitCode, String Net, String Warranty, String sn) {
        this.QuotNo = QuotNo;
        this.ItemCode = ItemCode;
        this.ItemDescription = ItemDescription;
        this.Quantity = Quantity;
        this.Rate = Rate;
        this.Discount = Discount;
        this.UnitCode = UnitCode;
        this.Net = Net;
        this.Warranty = Warranty;
        this.sn = sn;
    }

    public OBJQuotation(String Name, String Unit, Double SellingRate, String War) {
        this.Name = Name;
        this.Unit = Unit;
        this.SellingRate = SellingRate;
        this.War = War;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Double getSellingRate() {
        return SellingRate;
    }

    public void setSellingRate(Double SellingRate) {
        this.SellingRate = SellingRate;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public String getWar() {
        return War;
    }

    public void setWar(String War) {
        this.War = War;
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

    public String getCussCode() {
        return CussCode;
    }

    public void setCussCode(String CussCode) {
        this.CussCode = CussCode;
    }

    public String getDeliDate() {
        return DeliDate;
    }

    public void setDeliDate(String DeliDate) {
        this.DeliDate = DeliDate;
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

    public String getQuotDate() {
        return QuotDate;
    }

    public void setQuotDate(String QuotDate) {
        this.QuotDate = QuotDate;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getQuotNo() {
        return QuotNo;
    }

    public void setQuotNo(String QuotNo) {
        this.QuotNo = QuotNo;
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

}
