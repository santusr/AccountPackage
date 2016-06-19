/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.store;

/**
 *
 * @author dell
 */
public class OBJStore {
     private String code;
    private String name;
    private String Desc;

    public OBJStore(String code, String name, String Desc) {
        this.code = code;
        this.name = name;
        this.Desc = Desc;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
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
