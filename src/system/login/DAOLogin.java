/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.login;

import accountpackage.AccountPackage;
import core.Locals;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class DAOLogin {

    static boolean login(OBJLogin objlogin, Connection con) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean status = false;
        String que = "SELECT * FROM user_account WHERE Name = '" + objlogin.getUase() + "' AND Password = '" + Locals.passwordGen(objlogin.getPass()) + "' AND Status = '0'";
        PreparedStatement preparedStmt = con.prepareStatement(que);
        preparedStmt.execute();
        ResultSet rs = preparedStmt.getResultSet();
        while (rs.next()) {
            status = true;
            accountpackage.AccountPackage.userlevel = rs.getString("ulevel");
        }

        return status;
    }

    static ArrayList<OBJCostCenter> getCoseCentrs() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        ArrayList<OBJCostCenter> costCenters = new ArrayList<>();
        String que = "SELECT * FROM costcenter WHERE Status = '0'";
        PreparedStatement preparedStmt = AccountPackage.connect.prepareStatement(que);
        preparedStmt.execute();
        ResultSet rs = preparedStmt.getResultSet();
        while (rs.next()) {
          OBJCostCenter costCenter = new OBJCostCenter(
                    rs.getString(1), 
                    rs.getString(2),
                    rs.getString(4));
            costCenters.add(costCenter);
        }

        return costCenters;
    }

//else {
//                  this.dispose();
//                new ReceptionConsole(user).setVisible(true); 
//                 String urll = "INSERT INTO login_history(user,action,time,location) VALUES (?,?,CURRENT_TIMESTAMP,?)";
//
//                PreparedStatement save1 = cn.prepareStatement(urll);
//                save1.setString(1, jTextField1.getText());
//                save1.setString(2, "login");  
//                InetAddress in= InetAddress.getLocalHost();
//                 save1.setString(3, ""+in);
//
//                save1.executeUpdate();
//                save1.close();
//                
//                }
//            }
//            }
//            cn.close();
}
