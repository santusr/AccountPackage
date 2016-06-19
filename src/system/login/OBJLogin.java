/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.login;

/**
 *
 * @author dell
 */
public class OBJLogin {

    private String uase;
    private String pass;
    private String level;

    public OBJLogin() {
    }

    public OBJLogin(String uase, String pass) {
        this.uase = uase;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUase() {
        return uase;
    }

    public void setUase(String uase) {
        this.uase = uase;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return uase;
    }

}
