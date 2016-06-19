/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.itemclassification.itemgroup;

/**
 *
 * @author dell
 */
public class OBJItemGroup {
    private String code;
    private String name;
    private String catname;

    public OBJItemGroup(String code, String name, String catname) {
        this.code = code;
        this.name = name;
        this.catname = catname;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
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
