/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.retern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import system.inventory.InvoiceStatus;
import system.inventory.ItemStatus;
import system.inventory.transaction.sales.Invoice.OBJSalesInvoiceQO;

/**
 *
 * @author dell
 */
public class DAOSalesRetern {
//set Table

    private static final String TABAL = "invoiceheader";
    private static final String TABALHISTORY = "invoicehistory";

    static void save(String s, ArrayList<OBJSalesReturnItem> obj, String date, Connection conn) throws SQLException {
        String sql = "UPDATE " + TABAL + " SET status = '" + InvoiceStatus.SUSPEND + "' WHERE invoNo = '"+s+"'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.execute();
        saveHistory(s, obj, date, conn);

    }

    static void saveHistory(String s, ArrayList<OBJSalesReturnItem> obja, String date, Connection conn) throws SQLException {

        String Status = ItemStatus.SUSPEND;
        for (OBJSalesReturnItem obj : obja) {
            if (Integer.parseInt(obj.getReturnQty()) > 0) {
                Status = ItemStatus.RETURNED;
            } else {
                Status = ItemStatus.SUSPEND;
            }
            String sql = "UPDATE " + TABALHISTORY + " SET status = '" + Status + "' WHERE invoNo = '"+obj.getInvoNo()+"' AND ItemCode = '"+obj.getItemCode()+"'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.execute();

            if (Status.equals(ItemStatus.RETURNED)) {
                sql = "INSERT INTO salesreturn ( InvoNo, RetDate, StoreCode, ItemCode, qty, ReturnQty) VALUES (?,?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, obj.getInvoNo());
            st.setString(2, date);
            st.setString(3, obj.getStoreCode());
            st.setString(4, obj.getItemCode());
            st.setString(5, obj.getQuantity());
            st.setString(6, obj.getReturnQty());
            st.execute();

            }
            sql = "UPDATE `stock` SET `StockInHand` =  `StockInHand` + '" + Double.parseDouble(obj.getReturnQty()) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "' AND `Store` = '" + obj.getStoreCode() + "'";
            st = conn.prepareStatement(sql);
            st.execute();

            sql = "UPDATE `itemmaster` SET `StockInHand` =  `StockInHand` + '" + Double.parseDouble(obj.getReturnQty()) + "', "
                    + "`CostRate` = ((CostRate * StockInHand)+'" + (Double.parseDouble(obj.getNet()) / Double.parseDouble(obj.getQuantity()) * Double.parseDouble(obj.getReturnQty())) + "') / (StockInHand + '" + Double.parseDouble(obj.getQuantity()) + "') "
                    + "WHERE `ItemCode` = '" + obj.getItemCode() + "'";
            st = conn.prepareStatement(sql);
            st.execute();
            
            sql = "UPDATE `credit` SET `status` =  '3' WHERE invoNo = '"+s+"' AND `status` = '0'";
            st = conn.prepareStatement(sql);
            st.execute();
            
            sql = "UPDATE `payschedule` SET `status` = '3' WHERE invoNo = '"+s+"' AND `status` = '0'";
            st = conn.prepareStatement(sql);
            st.execute();
            
            st.close();
        }

    }

    static OBJSalesRetern getNavi(Connection conn, int Ix) throws SQLException {

        OBJSalesRetern obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY QuotationNo ASC";

        //MySql
        //Ix -= 1;
        String sql = "SELECT * FROM " + TABAL + " WHERE Status = '" + InvoiceStatus.ACTIVE + "' ORDER BY InvoNo ASC LIMIT " + Ix + ",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJSalesRetern(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(6),
                    result.getString(4),
                    result.getString(9),
                    result.getString(10),
                    result.getString(5),
                    result.getString(7),
                    result.getString(8),
                    result.getString(11),
                    result.getString(12),
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(18),
                    result.getString(19),
                    result.getString(20),
                    result.getString(21),
                    result.getString(22),
                    result.getString(24),
                    result.getString(25),
                    result.getString(26),
                    result.getString(28));
        }

        return obj;
    }

    static OBJSalesRetern serch(Connection conn, String code) throws SQLException {
        OBJSalesRetern obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE InvoNo = '" + code + "' AND Status = '" + InvoiceStatus.ACTIVE + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJSalesRetern(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(6),
                    result.getString(5),
                    result.getString(9),
                    result.getString(10),
                    result.getString(4),
                    result.getString(7),
                    result.getString(8),
                    result.getString(11),
                    result.getString(12),
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(18),
                    result.getString(19),
                    result.getString(20),
                    result.getString(21),
                    result.getString(22),
                    result.getString(24),
                    result.getString(25),
                    result.getString(26),
                    result.getString(28));
        }
        return obj;
    }

    static ArrayList<OBJSalesInvoiceQO> InvoHistory(Connection conn, String code) throws SQLException {

        ArrayList<OBJSalesInvoiceQO> obja = new ArrayList<OBJSalesInvoiceQO>();
        OBJSalesInvoiceQO obj = null;
        String sql = "SELECT * FROM " + TABALHISTORY + "," + TABAL + " WHERE " + TABALHISTORY + ".InvoNo = '" + code + "' AND " + TABAL + ".Status = '" + InvoiceStatus.ACTIVE + "' AND " + TABALHISTORY + ".InvoNo = " + TABAL + ".InvoNo";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJSalesInvoiceQO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(4),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    "",
                    result.getString(13),
                    result.getString(11));
            obja.add(obj);
        }

        return obja;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) As val FROM " + TABAL + " WHERE Status = '" + InvoiceStatus.ACTIVE + "'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            //index++;
            index = result.getInt("val");
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE ItemCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE InvoNo = '" + code + "'";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, InvoiceStatus.RECALLED);
            st.execute();
        }
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "000001";
        String sql = "SELECT InvoNo FROM " + TABAL + " ORDER BY InvoNo DESC LIMIT 1";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        int i = 0;
        while (result.next()) {
            ID = result.getString("InvoNo");
            i = Integer.parseInt(ID);
        }
        i++;
        ID = i + "";
        i = ID.length();
        for (int j = i; j < 6; j++) {
            ID = "0" + ID;
        }

        return ID;
    }

    static String genName(Connection conn, String tble, String code, String col) throws SQLException {
        String name = "";

        String sql = "SELECT Description FROM " + tble + " WHERE " + col + " = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            name = result.getString("Description");
        }

        return name;
    }

    static Vector getUnit(Connection conn) throws SQLException {
        Vector uomCode = new Vector();
        String sql = "SELECT UOMCode FROM UOM";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("UOMCode");
            uomCode.add(cd);
        }
        return uomCode;
    }

    static String getUnitName(Connection conn, String UOMCode) throws SQLException {
        String Uname = null;
        String sql = "SELECT Description FROM UOM WHERE UOMCode = '" + UOMCode + "'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("Description");
            Uname = cd;
        }
        return Uname;
    }

    static Vector loadCurrency(Connection con) throws SQLException {
        Vector v = new Vector();
        String sql = "SELECT FCCode FROM fcurrency WHERE status = '0'";

        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            System.out.println(result.getString("FCCode"));
            v.addElement(result.getString("FCCode"));
        }
        return v;
    }

    static double gatFCRate(Connection con, String cc) throws SQLException {
        double d = 0.00;
        String sql = "SELECT Factor FROM fcurrency WHERE FCCode = '" + cc + "' LIMIT 1";

        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            d = result.getDouble("Factor");
        }
        return d;
    }

    static OBJSalesInvoiceQO getTablInfo(Connection con, String itc, String text) throws SQLException {

        OBJSalesInvoiceQO obj = null;

        String sql = "SELECT ItemMaster.ItemCode AS code, ItemMaster.ShortName AS n, ItemMaster.MinLevel AS min, ItemMaster.UnitCode AS uc, ItemMaster.SellingRate AS sr, ItemMaster.MinSellingRate AS msr, ItemMaster.Warranty AS wa, ItemMaster.Discount AS dis, Stock.StockInHand AS st, ItemMaster.CostRate AS cost FROM ItemMaster, Stock WHERE ItemMaster.ItemCode = '" + itc + "' AND Stock.ItemCode = '" + itc + "' AND Stock.Store = '" + text + "' LIMIT 1";

        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        if (result.next()) {
            obj = new OBJSalesInvoiceQO(
                    result.getString("code"),
                    result.getString("n"),
                    result.getString("uc"),
                    result.getString("sr"),
                    result.getString("msr"),
                    result.getString("wa"),
                    result.getString("dis"),
                    result.getString("cost"),
                    result.getString("min"),
                    result.getString("st"),
                    result.getString("cost"));
//            if (result.getDouble("st") <= result.getDouble("min")) {
//                JOptionPane.showMessageDialog(null, "There are " + result.getDouble("st") + " items in this store...");
//            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found in this store...");
        }

        return obj;
    }
}
