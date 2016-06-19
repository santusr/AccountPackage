/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.item_list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RoWi
 */
class SERItemList {

    static ArrayList<OBJItemSearch> itemList(String code, String store) {
        ArrayList<OBJItemSearch> searchs = new ArrayList<>();
        Connection conn = accountpackage.AccountPackage.connect();
        try {
            searchs = DAOItemList.getItems(conn, code, store);
        } catch (SQLException ex) {
            Logger.getLogger(SERItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchs;
    }

    static ArrayList<OBJItemSearch> itemList(String store) {
            ArrayList<OBJItemSearch> searchs = new ArrayList<>();
        Connection conn = accountpackage.AccountPackage.connect();
        try {
            searchs = DAOItemList.getItems(conn, store);
        } catch (SQLException ex) {
            Logger.getLogger(SERItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchs;
    }

}
