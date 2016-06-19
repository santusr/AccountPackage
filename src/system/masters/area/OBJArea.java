/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.area;

/**
 *
 * @author dell
 */
public class OBJArea {
     private String code;
    private String name;
    private String desc;
    private String city;
    private String country;

    public OBJArea(String code, String name, String desc, String city, String country) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
