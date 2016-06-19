/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.login;

import accountpackage.AccountPackage;
import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class SERLogin {

    static boolean login(OBJLogin objlogin) {
        boolean status = false;
        try {
            Connection con = AccountPackage.connect;
            status = DAOLogin.login(objlogin, con);
        } catch (Exception ex) {
            Exp.Handle(ex);
        }
        return status;
    }
    
    static ArrayList<OBJCostCenter> getCostCenters() {
        ArrayList<OBJCostCenter> costCenters = null;
        try {
            costCenters = DAOLogin.getCoseCentrs();
        } catch (Exception ex) {
            Exp.Handle(ex);
        }
        return costCenters;
    }
    
    
}
