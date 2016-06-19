/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.stock.stock_adgesment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author dell
 */
public class DAOStockAdjestment {
//set Table

    private static final String TABAL = "StockAdjestmentHeader";
    private static final String TABALHISTORY = "StockAdjestmentHistory";

    static void save(OBJAdjestment obj, Connection conn, int Act) throws SQLException {
        if (Act != 1) {
            String sql = "delete  from stockadjestmentheader where stockadjestmentheader.adjNo = '" + obj.getAdjNo() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.execute();

        }
        String sql = "INSERT INTO " + TABAL + " ( adjNo, Store, AdjDate, systemValue, manualValue, PrepBy, AppBy, Remarks, User) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, obj.getAdjNo());
        st.setString(2, obj.getStore());
        st.setString(3, obj.getDate());
        st.setString(4, obj.getStockValue());
        st.setString(5, obj.getAdjestValue());
        st.setString(6, obj.getPrepBy());
        st.setString(7, obj.getAppBy());
        st.setString(8, obj.getRemarks());
        st.setString(9, obj.getUser());
        st.execute();

//        } else {
//            String sql = "UPDATE " + TABAL + " SET Store = ?, AdjDate = ?, systemValue = ?, manualValue = ?, PrepBy = ?, AppBy = ?, Remarks = ?, User = ? WHERE adjNo = '" + obj.getAdjNo() + "'";
//            PreparedStatement st = conn.prepareStatement(sql);
//            st.setString(1, obj.getStore());
//            st.setString(2, obj.getDate());
//            st.setString(3, obj.getStockValue());
//            st.setString(4, obj.getAdjestValue());
//            st.setString(5, obj.getPrepBy());
//            st.setString(6, obj.getAppBy());
//            st.setString(7, obj.getRemarks());
//            st.setString(8, obj.getUser());
//
//            st.executeUpdate();
//        }
        saveHistory(obj, conn, Act);
    }

    static void saveHistory(OBJAdjestment obja, Connection conn, int Act) throws SQLException {
        if (Act == 1) {

        }
        ArrayList<OBJAdjestmentHistory> adjestmentHistorys = obja.getAjestmentHistorys();

        for (OBJAdjestmentHistory obj : adjestmentHistorys) {
            double def = 0;
            if(Act != 1){
               def = getDefarence(obj, conn);
            }
            
            
            String sql = "UPDATE `stock` SET `StockInHand` = '" + Double.parseDouble(obj.getManualStock()) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "' AND `Store` = '" + obja.getStore() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.execute();

            sql = "UPDATE `itemmaster` SET `StockInHand` =  `StockInHand` + '" + (Double.parseDouble(obj.getDeference()) - def) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "'";
            st = conn.prepareStatement(sql);
            st.execute();

            sql = "delete  from stockadjestmenthistory where stockadjestmenthistory.adjNo = '" + obj.getAdjNo() + "' and stockadjestmenthistory.itemCode = '"+obj.getItemCode()+"'";
            st = conn.prepareStatement(sql);
            st.execute();

            sql = "INSERT INTO " + TABALHISTORY + " ( AdjNo, ItemCode, Description, systemStock, manualStock, deference, systemValue, manualValue) VALUES (?,?,?,?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, obj.getAdjNo());
            st.setString(2, obj.getItemCode());
            st.setString(3, obj.getDescription());
            st.setString(4, obj.getSystemStock());
            st.setString(5, obj.getManualStock());
            st.setString(6, obj.getDeference());
            st.setString(7, obj.getSystemValue());
            st.setString(8, obj.getManualValue());
            st.execute();

            st.close();
        }
    }

    private static double getDefarence(OBJAdjestmentHistory obj, Connection conn) throws SQLException {
        double def = 0;
        String sql = "SELECT deference As val FROM " + TABALHISTORY + " WHERE adjNo = '"+obj.getAdjNo()+"' and itemCode = '"+obj.getItemCode()+"'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            //index++;
            def = result.getDouble("val");
        }
        return def;
    }

    static OBJAdjestment getNavi(Connection conn, int Ix) throws SQLException {

        OBJAdjestment obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY QuotationNo ASC";

        //MySql
        //Ix -= 1;
        String sql = "SELECT * FROM " + TABAL + " WHERE Status = '0' ORDER BY AdjNo ASC LIMIT " + Ix + ",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJAdjestment(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    null);
        }

        return obj;
    }

    static OBJAdjestment serch(Connection conn, String code) throws SQLException {

        OBJAdjestment obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE adjNo = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJAdjestment(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    null);
        }

        return obj;
    }

    static ArrayList<OBJAdjestmentHistory> adjHistory(Connection conn, String code) throws SQLException {

        ArrayList<OBJAdjestmentHistory> obja = new ArrayList<>();
        OBJAdjestmentHistory obj = null;
        String sql = "SELECT * FROM " + TABALHISTORY + "," + TABAL + " WHERE " + TABALHISTORY + ".AdjNo = '" + code + "' AND " + TABALHISTORY + ".adjNo = " + TABAL + ".adjNo";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJAdjestmentHistory(
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9));
            obja.add(obj);
        }

        return obja;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) As val FROM " + TABAL + " WHERE Status = '0'";

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
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE adjNo = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "000001";
        String sql = "SELECT adjNo FROM " + TABAL + " ORDER BY adjNo DESC LIMIT 1";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        int i = 0;
        while (result.next()) {
            ID = result.getString("adjNo");
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

//    static OBJStockTransfer getTablInfo(Connection con, String itc, String text) throws SQLException {
//
//        OBJStockTransfer obj = null;
//
//        String sql = "SELECT ItemMaster.ShortName AS n, ItemMaster.MinLevel AS min, ItemMaster.UnitCode AS uc, ItemMaster.SellingRate AS sr, ItemMaster.MinSellingRate AS msr, ItemMaster.Warranty AS wa, ItemMaster.Discount AS dis, Stock.StockInHand AS st FROM ItemMaster, Stock WHERE ItemMaster.ItemCode = '" + itc + "' AND Stock.ItemCode = '" + itc + "' AND Stock.Store = '" + text + "' LIMIT 1";
//
//        PreparedStatement st = con.prepareStatement(sql);
//        st.execute();
//        ResultSet result = st.getResultSet();
//        if (result.next()) {
//            obj = new OBJStockTransfer(
//                    result.getString("n"),
//                    result.getString("uc"),
//                    result.getString("sr"),
//                    result.getString("msr"),
//                    result.getString("wa"),
//                    result.getString("dis"));
//            if (result.getDouble("st") <= result.getDouble("min")) {
//                JOptionPane.showMessageDialog(null, "There are " + result.getDouble("st") + " items in this store...");
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Item not found in this store...");
//            obj = new OBJStockTransfer(
//                    "",
//                    "",
//                    "0.00",
//                    "",
//                    "",
//                    "");
//        }
//
//        return obj;
//    }
    static ArrayList<OBJAdjestmentHistory> loadTable(Connection conn, String code) throws SQLException {
        ArrayList<OBJAdjestmentHistory> obja = new ArrayList<>();
        OBJAdjestmentHistory obj = null;
        String sql = "select itemmaster.ItemCode, itemmaster.Description, stock.StockInHand, stock.SellingRate from itemmaster \n"
                + "	left join stock on stock.ItemCode = itemmaster.ItemCode\n"
                + "where stock.store = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJAdjestmentHistory(
                    result.getString(2),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(3),
                    "0",
                    (result.getDouble(4) * result.getDouble(3)) + "",
                    (result.getDouble(4) * result.getDouble(3)) + "");
            obja.add(obj);
        }

        return obja;
    }
}
