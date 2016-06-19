/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.login;


/**
 *
 * @author dell
 */
public class OBJCostCenter {

    private String code;
    private String name;
    private String WorkingDate;

    public OBJCostCenter(String code, String name, String WorkingDate) {
        this.code = code;
        this.name = name;
        this.WorkingDate = WorkingDate;
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

    public String getWorkingDate() {
        return WorkingDate;
    }

    public void setWorkingDate(String WorkingDate) {
        this.WorkingDate = WorkingDate;
    }

    @Override
    public String toString() {
        return  code + " - " + name;
    }
    
}
