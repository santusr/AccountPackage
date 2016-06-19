/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.customergroup;

/**
 *
 * @author dell
 */
public class OBJCustomerGroup {
    private String code;
    private String Name;
    private String add1;
    private String add2;
    private String add3;
    private String pbox;
    private String tel;
    private String Fax;
    private String mobi;
    private String email;
    private String id;

    public OBJCustomerGroup(String code, String Name, String add1, String add2, String add3, String pbox, String tel, String Fax, String mobi, String email, String id) {
        this.code = code;
        this.Name = Name;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.pbox = pbox;
        this.tel = tel;
        this.Fax = Fax;
        this.mobi = mobi;
        this.email = email;
        this.id = id;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobi() {
        return mobi;
    }

    public void setMobi(String mobi) {
        this.mobi = mobi;
    }

    public String getPbox() {
        return pbox;
    }

    public void setPbox(String pbox) {
        this.pbox = pbox;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
