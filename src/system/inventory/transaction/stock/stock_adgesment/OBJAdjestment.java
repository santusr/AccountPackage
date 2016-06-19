/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.stock.stock_adgesment;

import java.util.ArrayList;

/**
 *
 * @author RoWi
 */
public class OBJAdjestment {
    private String AdjNo;
    private String store;
    private String date;
    private String stockValue;
    private String adjestValue;
    private String PrepBy;
    private String AppBy;
    private String remarks;
    private String user;
    private ArrayList<OBJAdjestmentHistory> ajestmentHistorys;

    public OBJAdjestment(String AdjNo, String store, String date, String stockValue, String adjestValue, String PrepBy, String AppBy, String remarks, String user, ArrayList<OBJAdjestmentHistory> ajestmentHistorys) {
        this.AdjNo = AdjNo;
        this.store = store;
        this.date = date;
        this.stockValue = stockValue;
        this.adjestValue = adjestValue;
        this.PrepBy = PrepBy;
        this.AppBy = AppBy;
        this.remarks = remarks;
        this.user = user;
        this.ajestmentHistorys = ajestmentHistorys;
    }

    public String getAdjNo() {
        return AdjNo;
    }

    public void setAdjNo(String AdjNo) {
        this.AdjNo = AdjNo;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStockValue() {
        return stockValue;
    }

    public void setStockValue(String stockValue) {
        this.stockValue = stockValue;
    }

    public String getAdjestValue() {
        return adjestValue;
    }

    public void setAdjestValue(String adjestValue) {
        this.adjestValue = adjestValue;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<OBJAdjestmentHistory> getAjestmentHistorys() {
        return ajestmentHistorys;
    }

    public void setAjestmentHistorys(ArrayList<OBJAdjestmentHistory> ajestmentHistorys) {
        this.ajestmentHistorys = ajestmentHistorys;
    }
    
    
}
