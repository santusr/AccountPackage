/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.payterms;

/**
 *
 * @author dell
 */
public class OBJPayTerm {
     private String code;
    private String name;

    public OBJPayTerm(String code, String name) {
        this.code = code;
        this.name = name;
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
