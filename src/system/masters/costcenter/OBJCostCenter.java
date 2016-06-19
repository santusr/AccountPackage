/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.costcenter;

/**
 *
 * @author dell
 */
public class OBJCostCenter {

    private String code;
    private String name;
    private String workingDate;

    public OBJCostCenter(String code, String name, String workingDate) {
        this.code = code;
        this.name = name;
        this.workingDate = workingDate;
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
        return workingDate;
    }

    public void setWorkingDate(String workingDate) {
        this.workingDate = workingDate;
    }
    
}
