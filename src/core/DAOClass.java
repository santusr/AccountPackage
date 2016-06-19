/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RoWi
 */
public class DAOClass {

    public static void main(String[] args) throws SQLException {
        String qry = "SELECT ItemMaster.ItemCode as item, ItemMaster.stockInHand as cost FROM ItemMaster";
        Connection con = accountpackage.AccountPackage.connect();
        PreparedStatement p = con.prepareStatement(qry);
        ResultSet r = p.executeQuery();
        ArrayList list = new ArrayList();
        while (r.next()) {
            OBJClass oBJClass = new OBJClass();
            oBJClass.setItemcode(r.getString("item"));
            oBJClass.setCost(r.getDouble("cost"));
            oBJClass.setStore("001");
            list.add(oBJClass);
        }

        for (Object object : list) {
            OBJClass oBJClass1 = (OBJClass) object;
            System.out.println(oBJClass1.getItemcode() + " - " + oBJClass1.getCost());
//            qry = "UPDATE stock SET SalsePrice = '"+oBJClass1.getCost()+"' WHERE ItemCode = '"+oBJClass1.getItemcode()+"'";
            qry = "UPDATE stock SET stockInHand = '"+oBJClass1.getCost()+"' WHERE ItemCode = '"+oBJClass1.getItemcode()+"'";
            PreparedStatement pp = con.prepareStatement(qry);
            pp.execute();
        }
    }
}
