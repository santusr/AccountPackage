/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.itemclassification.itemmaster;

/**
 *
 * @author dell
 */
public class OBJItemMaster {
    private String code;
    private String name;
    private String Descrip;
    private String unitcode;
    private String groupcode;
    private String catcode;
    private String minlevel;
    private String reclevel;
    private String stockinhand;
    private String onorder;
    private String opstock;
    private String opcostrate;
    private String batch;
    private String opdate;
    private String remarks;
    private String costcenter;
    private String SalePrice;
    private String MinSalePrice;
    private String Disc;
    private String Warranty;
    private String Store;

    public OBJItemMaster(String code, String name, String Descrip, String unitcode, String groupcode, String catcode, String minlevel, String reclevel, String stockinhand, String onorder, String opstock, String opcostrate, String batch, String opdate, String remarks, String costcenter, String SalePrice, String MinSalePrice, String Disc, String Warranty) {
        this.code = code;
        this.name = name;
        this.Descrip = Descrip;
        this.unitcode = unitcode;
        this.groupcode = groupcode;
        this.catcode = catcode;
        this.minlevel = minlevel;
        this.reclevel = reclevel;
        this.stockinhand = stockinhand;
        this.onorder = onorder;
        this.opstock = opstock;
        this.opcostrate = opcostrate;
        this.batch = batch;
        this.opdate = opdate;
        this.remarks = remarks;
        this.costcenter = costcenter;
        this.SalePrice = SalePrice;
        this.MinSalePrice = MinSalePrice;
        this.Disc = Disc;
        this.Warranty = Warranty;
    }

    public OBJItemMaster(String code, String stockinhand, String Store, String salePrice, String warranty) {
        this.code = code;
        this.stockinhand = stockinhand;
        this.Store = Store;
        this.SalePrice = salePrice;
        this.Warranty = warranty;
    }

    public String getStore() {
        return Store;
    }

    public void setStore(String Store) {
        this.Store = Store;
    }

    public String getDescrip() {
        return Descrip;
    }

    public void setDescrip(String Descrip) {
        this.Descrip = Descrip;
    }

    public String getWarranty() {
        return Warranty;
    }

    public void setWarranty(String Warranty) {
        this.Warranty = Warranty;
    }

    public String getDisc() {
        return Disc;
    }

    public void setDisc(String Disc) {
        this.Disc = Disc;
    }

    public String getMinSalePrice() {
        return MinSalePrice;
    }

    public void setMinSalePrice(String MinSalePrice) {
        this.MinSalePrice = MinSalePrice;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String SalePrice) {
        this.SalePrice = SalePrice;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCatcode() {
        return catcode;
    }

    public void setCatcode(String catcode) {
        this.catcode = catcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCostcenter() {
        return costcenter;
    }

    public void setCostcenter(String costcenter) {
        this.costcenter = costcenter;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getMinlevel() {
        return minlevel;
    }

    public void setMinlevel(String minlevel) {
        this.minlevel = minlevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnorder() {
        return onorder;
    }

    public void setOnorder(String onorder) {
        this.onorder = onorder;
    }

    public String getOpcostrate() {
        return opcostrate;
    }

    public void setOpcostrate(String opcostrate) {
        this.opcostrate = opcostrate;
    }

    public String getOpdate() {
        return opdate;
    }

    public void setOpdate(String opdate) {
        this.opdate = opdate;
    }

    public String getOpstock() {
        return opstock;
    }

    public void setOpstock(String opstock) {
        this.opstock = opstock;
    }

    public String getReclevel() {
        return reclevel;
    }

    public void setReclevel(String reclevel) {
        this.reclevel = reclevel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStockinhand() {
        return stockinhand;
    }

    public void setStockinhand(String stockinhand) {
        this.stockinhand = stockinhand;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

}
