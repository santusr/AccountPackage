/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.bankmaster;

/**
 *
 * @author dell
 */
public class OBJBankMaster {

    private String code;
    private String name;
    private String branch;
    private String contperson;
    private String address;
    private String pbox;
    private String tel;
    private String fax;
    private String email;

    public OBJBankMaster(String code, String name, String branch, String contperson, String address, String pbox, String tel, String fax, String email) {
        this.code = code;
        this.name = name;
        this.branch = branch;
        this.contperson = contperson;
        this.address = address;
        this.pbox = pbox;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContperson() {
        return contperson;
    }

    public void setContperson(String contperson) {
        this.contperson = contperson;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
