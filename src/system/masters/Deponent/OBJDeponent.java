/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.Deponent;


/**
 *
 * @author dell
 */
public class OBJDeponent {
     private String code;
     private String id;
    private String name;
    private String Area;
    private String Add1;
    private String Add2;
    private String Add3;
    private String Pbox;
    private String TellOff;
    private String Fax;
    private String Mobi;
    private String Email;
    private String Target;

    public OBJDeponent(String code, String id, String name, String Area, String Add1, String Add2, String Add3, String Pbox, String TellOff, String Fax, String Mobi, String Email, String Target) {
        this.code = code;
        this.id = id;
        this.name = name;
        this.Area = Area;
        this.Add1 = Add1;
        this.Add2 = Add2;
        this.Add3 = Add3;
        this.Pbox = Pbox;
        this.TellOff = TellOff;
        this.Fax = Fax;
        this.Mobi = Mobi;
        this.Email = Email;
        this.Target = Target;
    }

    public String getAdd1() {
        return Add1;
    }

    public void setAdd1(String Add1) {
        this.Add1 = Add1;
    }

    public String getAdd2() {
        return Add2;
    }

    public void setAdd2(String Add2) {
        this.Add2 = Add2;
    }

    public String getAdd3() {
        return Add3;
    }

    public void setAdd3(String Add3) {
        this.Add3 = Add3;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobi() {
        return Mobi;
    }

    public void setMobi(String Mobi) {
        this.Mobi = Mobi;
    }

    public String getPbox() {
        return Pbox;
    }

    public void setPbox(String Pbox) {
        this.Pbox = Pbox;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String Target) {
        this.Target = Target;
    }

    public String getTellOff() {
        return TellOff;
    }

    public void setTellOff(String TellOff) {
        this.TellOff = TellOff;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
