/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class Exp {

    public static void Handle(Exception e) {
        e.printStackTrace();
        PrintLog.out(e);
        JOptionPane.showMessageDialog(null, "Error in transaction.", "ERROR..!", JOptionPane.ERROR_MESSAGE);
    }
}
