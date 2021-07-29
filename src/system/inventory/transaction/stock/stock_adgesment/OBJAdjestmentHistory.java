/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.stock.stock_adgesment;

/**
 *
 * @author RoWi
 */
public class OBJAdjestmentHistory {

    private String adjNo;
    private String itemCode;
    private String description;
    private String systemStock;
    private String manualStock;
    private String deference;
    private String systemValue;
    private String manualValue;
    private String storeCode;

    public OBJAdjestmentHistory(String adjNo, String itemCode, String description, String systemStock, String manualStock, String deference, String systemValue, String manualValue, String storeCode) {
        this.adjNo = adjNo;
        this.itemCode = itemCode;
        this.description = description;
        this.systemStock = systemStock;
        this.manualStock = manualStock;
        this.deference = deference;
        this.systemValue = systemValue;
        this.manualValue = manualValue;
        this.storeCode = storeCode;
    }

    public String getAdjNo() {
        return adjNo;
    }

    public void setAdjNo(String adjNo) {
        this.adjNo = adjNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystemStock() {
        return systemStock;
    }

    public void setSystemStock(String systemStock) {
        this.systemStock = systemStock;
    }

    public String getManualStock() {
        return manualStock;
    }

    public void setManualStock(String manualStock) {
        this.manualStock = manualStock;
    }

    public String getDeference() {
        return deference;
    }

    public void setDeference(String deference) {
        this.deference = deference;
    }

    public String getSystemValue() {
        return systemValue;
    }

    public void setSystemValue(String systemValue) {
        this.systemValue = systemValue;
    }

    public String getManualValue() {
        return manualValue;
    }

    public void setManualValue(String manualValue) {
        this.manualValue = manualValue;
    }

    @Override
    public String toString() {
        return itemCode; //To change body of generated methods, choose Tools | Templates.
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

}
