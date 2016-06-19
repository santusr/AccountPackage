/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.vendore;

/**
 *
 * @author dell
 */
public class OBJVendore {
    private String code;
    private String custcode;
    private String name;
    private String id;
    private String contact1;
    private String contact2;
    private String mobi1;
    private String mobi2;
    private String mobi3;
    private String pbox;
    private String add1;
    private String add2;
    private String add3;
    private String teloff;
    private String telres;
    private String fax;
    private String email;
    private String web;
    private String srep;
    private String area;
    private String payterm;
    private String currency;
    private String balance;
    private String creditlimit;
    private String creditdays;
    private String remark;    
    private String type;
    private String PrintName;
    private String status;

    public OBJVendore(String code, String custcode, String name, String id, String contact1, String contact2, String mobi1, String mobi2, String mobi3, String pbox, String add1, String add2, String add3, String teloff, String telres, String fax, String email, String web, String srep, String area, String payterm, String currency, String balance, String creditlimit, String creditdays, String remark, String type, String PrintName) {
        this.code = code;
        this.custcode = custcode;
        this.name = name;
        this.id = id;
        this.contact1 = contact1;
        this.contact2 = contact2;
        this.mobi1 = mobi1;
        this.mobi2 = mobi2;
        this.mobi3 = mobi3;
        this.pbox = pbox;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.teloff = teloff;
        this.telres = telres;
        this.fax = fax;
        this.email = email;
        this.web = web;
        this.srep = srep;
        this.area = area;
        this.payterm = payterm;
        this.currency = currency;
        this.balance = balance;
        this.creditlimit = creditlimit;
        this.creditdays = creditdays;
        this.remark = remark;
        this.type = type;
        this.PrintName = PrintName;
    }

    public String getPrintName() {
        return PrintName;
    }

    public void setPrintName(String PrintName) {
        this.PrintName = PrintName;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd3() {
        return add3;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getId() {
        return id;
    }

    public void setContact3(String id) {
        this.id = id;
    }

    public String getCreditdays() {
        return creditdays;
    }

    public void setCreditdays(String creditdays) {
        this.creditdays = creditdays;
    }

    public String getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(String creditlimit) {
        this.creditlimit = creditlimit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustCode() {
        return custcode;
    }

    public void setCustCode(String custcode) {
        this.custcode = custcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobi1() {
        return mobi1;
    }

    public void setMobi1(String mobi1) {
        this.mobi1 = mobi1;
    }

    public String getMobi2() {
        return mobi2;
    }

    public void setMobi2(String mobi2) {
        this.mobi2 = mobi2;
    }

    public String getMobi3() {
        return mobi3;
    }

    public void setMobi3(String mobi3) {
        this.mobi3 = mobi3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayterm() {
        return payterm;
    }

    public void setPayterm(String payterm) {
        this.payterm = payterm;
    }

    public String getPbox() {
        return pbox;
    }

    public void setPbox(String pbox) {
        this.pbox = pbox;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSrep() {
        return srep;
    }

    public void setSrep(String srep) {
        this.srep = srep;
    }

    public String getTeloff() {
        return teloff;
    }

    public void setTeloff(String teloff) {
        this.teloff = teloff;
    }

    public String getTelres() {
        return telres;
    }

    public void setTelres(String telres) {
        this.telres = telres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
