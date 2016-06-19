/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.stock.stocktransfer;

/**
 *
 * @author dell
 */
public class OBJStockTransfer {

    private String TransNo;
    private String TransDate;
    private String StoreCodeF;
    private String StoreCodeT;
    private String PrepBy;
    private String ApproBy;
    private String Remarks;
    private String NetAmount;
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
    private String SellingRate;
    private String MinSellingRate;
    private String War;
    private String Disc;
    private String CostRate;

    public OBJStockTransfer(String TransNo, String TransDate, String StoreCodeF, String StoreCodeT, String PrepBy, String ApproBy, String Remarks, String NetAmount, String UserId) {
        this.TransNo = TransNo;
        this.TransDate = TransDate;
        this.StoreCodeF = StoreCodeF;
        this.StoreCodeT = StoreCodeT;
        this.PrepBy = PrepBy;
        this.ApproBy = ApproBy;
        this.Remarks = Remarks;
        this.NetAmount = NetAmount;
        this.UserId = UserId;
    }

    public OBJStockTransfer(String TransNo, String ItemCode, String ItemDescription, String Quantity, String Rate, String UnitCode, String Net, String Warranty, String sn, String SellingRate, String StoreCodeF, String StoreCodeT, String Discount) {
        this.TransNo = TransNo;
        this.ItemCode = ItemCode;
        this.ItemDescription = ItemDescription;
        this.Quantity = Quantity;
        this.Rate = Rate;
        this.UnitCode = UnitCode;
        this.Net = Net;
        this.Warranty = Warranty;
        this.sn = sn;
        this.SellingRate = SellingRate;
        this.StoreCodeF = StoreCodeF;
        this.StoreCodeT = StoreCodeT;
        this.Discount = Discount;
    }

    public OBJStockTransfer(String Name, String Unit, String SellingRate, String MinSellingRate, String War, String Disc, String CostRate) {
        this.Name = Name;
        this.Unit = Unit;
        this.SellingRate = SellingRate;
        this.MinSellingRate = MinSellingRate;
        this.War = War;
        this.Disc = Disc;
        this.CostRate = CostRate;
    }

    public String getApproBy() {
        return ApproBy;
    }

    public void setApproBy(String ApproBy) {
        this.ApproBy = ApproBy;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String Discount) {
        this.Discount = Discount;
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

    public String getSellingRate() {
        return SellingRate;
    }

    public void setSellingRate(String SellingRate) {
        this.SellingRate = SellingRate;
    }

    public String getStoreCodeF() {
        return StoreCodeF;
    }

    public void setStoreCodeF(String StoreCodeF) {
        this.StoreCodeF = StoreCodeF;
    }

    public String getStoreCodeT() {
        return StoreCodeT;
    }

    public void setStoreCodeT(String StoreCodeT) {
        this.StoreCodeT = StoreCodeT;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String TransDate) {
        this.TransDate = TransDate;
    }

    public String getTransNo() {
        return TransNo;
    }

    public void setTransNo(String TransNo) {
        this.TransNo = TransNo;
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

    public String getMinSellingRate() {
        return MinSellingRate;
    }

    public void setMinSellingRate(String MinSellingRate) {
        this.MinSellingRate = MinSellingRate;
    }

    public String getDisc() {
        return Disc;
    }

    public void setDisc(String Disc) {
        this.Disc = Disc;
    }

    public String getCostRate() {
        return CostRate;
    }

    public void setCostRate(String CostRate) {
        this.CostRate = CostRate;
    }
    
}
