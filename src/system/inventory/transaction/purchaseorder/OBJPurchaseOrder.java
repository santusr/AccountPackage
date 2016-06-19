/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.purchaseorder;

/**
 *
 * @author dell
 */
public class OBJPurchaseOrder {

    private String OrderNo;
    private String OrderDate;
    private String VendCode;
    private String RepCode;
    private String CostCode;
    private String StoreCode;
    private String FCCode;
    private String FCRate;
    private String PaymentTerms;
    private String DueDate;
    private String Comment;
    private String GrossAmount;
    private String TotalDiscount;
    private String DiscountRate;
    private String Expence;
    private String NetAmount;
    private String UserId;
    private String RequestNo;
    private String ItemCode;
    private String ItemDescription;
    private String UnitCode;
    private String Quantity;
    private String Rate;
    private String Discount;
    private String Amount;
    
    
    private String Name;
    private String Unit;
    private Double SellingRate;
    private String War;

    public OBJPurchaseOrder(String OrderNo, String OrderDate, String VendCode, String RepCode, String CostCode, String StoreCode, String FCCode, String FCRate, String PaymentTerms, String DueDate, String Comment, String GrossAmount, String TotalDiscount, String DiscountRate, String Expence, String NetAmount, String UserId, String RequestNo) {
        this.OrderNo = OrderNo;
        this.OrderDate = OrderDate;
        this.VendCode = VendCode;
        this.RepCode = RepCode;
        this.CostCode = CostCode;
        this.StoreCode = StoreCode;
        this.FCCode = FCCode;
        this.FCRate = FCRate;
        this.PaymentTerms = PaymentTerms;
        this.DueDate = DueDate;
        this.Comment = Comment;
        this.GrossAmount = GrossAmount;
        this.TotalDiscount = TotalDiscount;
        this.DiscountRate = DiscountRate;
        this.Expence = Expence;
        this.NetAmount = NetAmount;
        this.UserId = UserId;
        this.RequestNo = RequestNo;
    }

    public OBJPurchaseOrder(String OrderNo, String ItemCode, String ItemDescription, String UnitCode, String Quantity, String Rate, String Discount, String Amount) {
        this.OrderNo = OrderNo;
        this.ItemCode = ItemCode;
        this.ItemDescription = ItemDescription;
        this.UnitCode = UnitCode;
        this.Quantity = Quantity;
        this.Rate = Rate;
        this.Discount = Discount;
        this.Amount = Amount;
    }

    public OBJPurchaseOrder(String Name, String Unit, Double SellingRate, String War) {
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

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public String getCostCode() {
        return CostCode;
    }

    public void setCostCode(String CostCode) {
        this.CostCode = CostCode;
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

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    public String getExpence() {
        return Expence;
    }

    public void setExpence(String Expence) {
        this.Expence = Expence;
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

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String NetAmount) {
        this.NetAmount = NetAmount;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getPaymentTerms() {
        return PaymentTerms;
    }

    public void setPaymentTerms(String PaymentTerms) {
        this.PaymentTerms = PaymentTerms;
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

    public String getRepCode() {
        return RepCode;
    }

    public void setRepCode(String RepCode) {
        this.RepCode = RepCode;
    }
    
    public String getRequestNo() {
        return RequestNo;
    }

    public void setRequestNo(String RequestNo) {
        this.RequestNo = RequestNo;
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

    public String getVendCode() {
        return VendCode;
    }

    public void setVendCode(String VendCode) {
        this.VendCode = VendCode;
    }

}
