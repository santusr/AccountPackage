/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.stock.stocktransfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class DAOStockTransfer {
//set Table

    private static final String TABAL = "TransferHeader";
    private static final String TABALHISTORY = "TransferHistory";

    static void save(OBJStockTransfer obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO " + TABAL + " ( TransNo, TransDate, StoreCodeF, StoreCodeT, PreparedBy, ApprovedBy, Remark, NetAmount, UserId) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getTransNo());
            st.setString(2, obj.getTransDate());
            st.setString(3, obj.getStoreCodeF());
            st.setString(4, obj.getStoreCodeT());
            st.setString(5, obj.getPrepBy());
            st.setString(6, obj.getApproBy());
            st.setString(7, obj.getRemarks());
            st.setString(8, obj.getNetAmount());
            st.setString(9, obj.getUserId());
            st.execute();

//        } else {
//            String sql = "UPDATE "+TABAL+" SET costcode = ?, Description = ?, ShortName = ?, UnitCode = ?, GroupCode = ?, CatCode = ?, MinLevel = ?, ReorderLevel = ?, StockInHand = ?, OnOrder = ?, CostRate = ?, OpeningStock = ?, OpCostRate = ?, Batch = ?, OpeningDate = ?, Remarks = ? WHERE GroupCode = '" + obj.getCode() + "'";
//            PreparedStatement st = conn.prepareStatement(sql);
//            st.setString(1, obj.getCostcenter());
//            st.setString(2, obj.getCatname());
//            st.setString(3, obj.getName());
//            st.setString(4, obj.getUnitcode());
//            st.setString(5, obj.getGroupcode());
//            st.setString(6, obj.getCatcode());
//            st.setString(7, obj.getMinlevel());
//            st.setString(8, obj.getReclevel());
//            st.setString(9, obj.getStockinhand());
//            st.setString(10, obj.getOnorder());
//            st.setString(11, obj.getOpcostrate());
//            st.setString(12, obj.getOpstock());
//            st.setString(13, obj.getOpcostrate());
//            st.setString(14, obj.getBatch());
//            st.setString(15, obj.getOpdate());
//            st.setString(16, obj.getRemarks());
//
//            st.executeUpdate();
        }
    }

    static void saveHistory(ArrayList<OBJStockTransfer> obja, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            for (OBJStockTransfer obj : obja) {
                String sql = "INSERT INTO " + TABALHISTORY + " ( TransNo, ItemCode, ItemDescription, UnitCode, Qty, Rate, Discount, Amount, Warranty, SN) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, obj.getTransNo());
                st.setString(2, obj.getItemCode());
                st.setString(3, obj.getItemDescription());
                st.setString(4, obj.getUnitCode());
                st.setString(5, obj.getQuantity());
                st.setString(6, obj.getRate());
                st.setString(7, obj.getDiscount());
                st.setString(8, obj.getNet());
                st.setString(9, obj.getWarranty());
                st.setString(10, obj.getSn());
                st.execute();

                sql = "UPDATE `stock` SET `StockInHand` =  `StockInHand` - '" + Double.parseDouble(obj.getQuantity()) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "' AND `Store` = '" + obj.getStoreCodeF() + "'";
                st = conn.prepareStatement(sql);
                st.execute();

                sql = "UPDATE `stock` SET `StockInHand` =  `StockInHand` + '" + Double.parseDouble(obj.getQuantity()) + "', SalsePrice='" + obj.getRate() + "' WHERE `ItemCode` = '" + obj.getItemCode() + "' AND `Store` = '" + obj.getStoreCodeT() + "'";
                st = conn.prepareStatement(sql);
                int i = st.executeUpdate();

                if (i == 0) {
                    sql = " INSERT INTO `stock` (`StockInHand`, `ItemCode`, `Store`,`SalsePrice`) VALUES ('" + obj.getQuantity() + "', '" + obj.getItemCode() + "', '" + obj.getStoreCodeT() + "', '" + obj.getRate() + "')";

                    st = conn.prepareStatement(sql);
                    st.execute();
                }

                st.close();
            }

        }
    }

    static OBJStockTransfer getNavi(Connection conn, int Ix) throws SQLException {

        OBJStockTransfer obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY QuotationNo ASC";

        //MySql
        //Ix -= 1;
        String sql = "SELECT * FROM " + TABAL + " WHERE Status = '0' ORDER BY TransNo ASC LIMIT " + Ix + ",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJStockTransfer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9));
        }

        return obj;
    }

    static OBJStockTransfer serch(Connection conn, String code) throws SQLException {

        OBJStockTransfer obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE TransNo = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJStockTransfer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9));
        }

        return obj;
    }

    static ArrayList<OBJStockTransfer> InvoHistory(Connection conn, String code) throws SQLException {

        ArrayList<OBJStockTransfer> obja = new ArrayList<OBJStockTransfer>();
        OBJStockTransfer obj = null;
        String sql = "SELECT * FROM " + TABALHISTORY + "," + TABAL + " WHERE " + TABALHISTORY + ".TransNo = '" + code + "' AND " + TABAL + ".Status = '0' AND " + TABALHISTORY + ".TransNo = " + TABAL + ".TransNo";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJStockTransfer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(5),
                    result.getString(6),
                    result.getString(4),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(6),
                    "",
                    "",
                    result.getString(7));
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
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE TransNo = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "000001";
        String sql = "SELECT TransNo FROM " + TABAL + " ORDER BY TransNo DESC LIMIT 1";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        int i = 0;
        while (result.next()) {
            ID = result.getString("TransNo");
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

    static OBJStockTransfer getTablInfo(Connection con, String itc, String text) throws SQLException {

        OBJStockTransfer obj = null;

        String sql = "SELECT \n"
                + "				ItemMaster.ShortName AS n, \n"
                + "				ItemMaster.MinLevel AS min, \n"
                + "				ItemMaster.UnitCode AS uc, \n"
                + "				stock.SalsePrice AS sr, \n"
                + "				ItemMaster.MinSellingRate AS msr, \n"
                + "				ItemMaster.Warranty AS wa, \n"
                + "				ItemMaster.Discount AS dis, \n"
                + "				ItemMaster.CostRate AS cost, \n"
                + "				Stock.StockInHand AS st \n"
                + "FROM \n"
                + "				ItemMaster \n"
                + "				LEFT JOIN Stock ON stock.ItemCode = ItemMaster.ItemCode\n"
                + "WHERE \n"
                + "				ItemMaster.ItemCode = '" + itc + "' AND \n"
                + "				Stock.ItemCode = '" + itc + "' AND \n"
                + "				Stock.Store = '" + text + "' \n"
                + "LIMIT 1";

        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        if (result.next()) {
            obj = new OBJStockTransfer(
                    result.getString("n"),
                    result.getString("uc"),
                    result.getString("sr"),
                    result.getString("msr"),
                    result.getString("wa"),
                    result.getString("dis"),
                    result.getString("cost"));
            if (result.getDouble("st") <= result.getDouble("min")) {
                JOptionPane.showMessageDialog(null, "There are " + result.getDouble("st") + " items in this store...");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found in this store...");
            obj = new OBJStockTransfer(
                    "",
                    "",
                    "0.00",
                    "",
                    "",
                    "",
                    "");
        }

        return obj;
    }
}
