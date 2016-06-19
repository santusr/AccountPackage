/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.retern;

/**
 *
 * @author Administrator
 */
public class OBJSalesReturnItem {

    private String InvoNo;
    private String ItemCode;
    private String ItemDescription;
    private String Quantity;
    private String Rate;
    private String Discount;
    private String UnitCode;
    private String Net;
    private String Warranty;
    private String sn;
    private String returnQty;
    private String StoreCode;

    public OBJSalesReturnItem(String InvoNo, String ItemCode, String ItemDescription, String Quantity, String Rate, String Discount, String UnitCode, String Net, String Warranty, String sn, String returnQty, String StoreCode) {
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
        this.returnQty = returnQty;
        this.StoreCode = StoreCode;
    }

    public String getInvoNo() {
        return InvoNo;
    }

    public void setInvoNo(String InvoNo) {
        this.InvoNo = InvoNo;
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

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String Discount) {
        this.Discount = Discount;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String UnitCode) {
        this.UnitCode = UnitCode;
    }

    public String getNet() {
        return Net;
    }

    public void setNet(String Net) {
        this.Net = Net;
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

    public String getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(String returnQty) {
        this.returnQty = returnQty;
    }

    public String getStoreCode() {
        return StoreCode;
    }

    public void setStoreCode(String StoreCode) {
        this.StoreCode = StoreCode;
    }
    
}
