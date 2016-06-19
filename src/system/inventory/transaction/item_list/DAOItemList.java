/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.item_list;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author RoWi
 */
class DAOItemList {

    static ArrayList<OBJItemSearch> getItems(Connection conn, String code, String store) throws SQLException {
        OBJItemSearch obj = null;
        ArrayList<OBJItemSearch> searchs = new ArrayList<>();
        String sql
                = "SELECT \n"
                + "				ItemMaster.ItemCode AS ic, \n"
                + "				ItemMaster.ShortName AS n, \n"
                + "				ItemMaster.SellingRate AS rate, \n"
                + "				ItemMaster.UnitCode AS ucode, \n"
                + "				ItemMaster.Warranty AS warranty, \n"
                + "				ItemMaster.CostRate AS cost, \n"
                + "                             Stock.store \n"
                + "FROM \n"
                + "				ItemMaster  \n"
                + "				LEFT JOIN Stock ON stock.ItemCode = ItemMaster.ItemCode \n"
                + "WHERE \n"
                + "				ItemMaster.status = '0' AND \n"
                + "                             Stock.store = '"+store+"' AND \n"
                + "				ItemMaster.ItemCode LIKE '" + code + "%'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJItemSearch();
            obj.setCode(result.getString("ic"));
            obj.setName(result.getString("n"));
            obj.setRate(result.getString("rate"));
            obj.setUnitCode(result.getString("ucode"));
            obj.setWarranty(result.getString("warranty"));
            obj.setCost_rate(result.getString("cost"));
            searchs.add(obj);
        }
        return searchs;
    }

    static ArrayList<OBJItemSearch> getItems(Connection conn, String store) throws SQLException {
        OBJItemSearch obj = null;
        ArrayList<OBJItemSearch> searchs = new ArrayList<>();
        String sql
                = "SELECT \n"
                + "				ItemMaster.ItemCode AS ic, \n"
                + "				ItemMaster.ShortName AS n, \n"
                + "				ItemMaster.SellingRate AS rate, \n"
                + "				ItemMaster.UnitCode AS ucode, \n"
                + "				ItemMaster.Warranty AS warranty, \n"
                + "				ItemMaster.CostRate AS cost, \n"
                + "                             Stock.store \n"
                + "FROM \n"
                + "				ItemMaster  \n"
                + "				LEFT JOIN Stock ON stock.ItemCode = ItemMaster.ItemCode \n"
                + "WHERE \n"
                + "				ItemMaster.status = '0' AND \n"
                + "                             Stock.store = '"+store+"'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJItemSearch();
            obj.setCode(result.getString("ic"));
            obj.setName(result.getString("n"));
            obj.setRate(result.getString("rate"));
            obj.setUnitCode(result.getString("ucode"));
            obj.setWarranty(result.getString("warranty"));
            obj.setCost_rate(result.getString("cost"));
            searchs.add(obj);
        }
        return searchs;
    }
}
