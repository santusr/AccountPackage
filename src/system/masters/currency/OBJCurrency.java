/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.currency;

/**
 *
 * @author dell
 */
public class OBJCurrency {
    private String code;
    private String name;
    private String factore;

    public OBJCurrency(String code, String name, String factore) {
        this.code = code;
        this.name = name;
        this.factore = factore;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFactore() {
        return factore;
    }

    public void setFactore(String factore) {
        this.factore = factore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
