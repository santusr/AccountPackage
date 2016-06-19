/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.purchaseorder;

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
public class DAOPurchaseOrder {
//set Table

    private static final String TABAL = "POHeader";
    private static final String TABALHISTORY = "POHistory";

    static void save(OBJPurchaseOrder obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO " + TABAL + " (   OrderNo, OrderDate, VendCode, RepCode, CostCode ,StoreCode, FCCode, FCRate, PaymentTerms, DeliveryDate, Remark, GrossAmount ,TotalDiscount, DiscountRate, TotExpence, NetAmount, UserId, ReqNo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//            String sql = "INSERT INTO " + TABAL + " ( OrderNo, OrderDate, DeliveryDate, VendCode, CostCode, AreaCode, PaymentTerms, Shipment, GrossAmount, TotalDiscount, FCCode, FCRate, UserId, Details, ReqNo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getOrderNo());
            st.setString(2, obj.getOrderDate());
            st.setString(3, obj.getVendCode());
            st.setString(4, obj.getRepCode());
            st.setString(5, obj.getCostCode());
            st.setString(6, obj.getStoreCode());
            st.setString(7, obj.getFCCode());
            st.setString(8, obj.getFCRate());
            st.setString(9, obj.getPaymentTerms());
            st.setString(10, obj.getDueDate());
            st.setString(11, obj.getComment());
            st.setString(12, obj.getGrossAmount());
            st.setString(13, obj.getTotalDiscount());
            st.setString(14, obj.getDiscountRate());
            st.setString(15, obj.getExpence());
            st.setString(16, obj.getNetAmount());
            st.setString(17, obj.getUserId());
            st.setString(18, obj.getRequestNo());
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

    static void saveHistory(ArrayList<OBJPurchaseOrder> obja, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            for (OBJPurchaseOrder obj : obja) {
                String sql = "INSERT INTO " + TABALHISTORY + " ( OrderNo, ItemCode, ItemDescription, UnitCode, Qty, Rate, Discount, Amount) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, obj.getOrderNo());
                st.setString(2, obj.getItemCode());
                st.setString(3, obj.getItemDescription());
                st.setString(4, obj.getUnitCode());
                st.setString(5, obj.getQuantity());
                st.setString(6, obj.getRate());
                st.setString(7, obj.getDiscount());
                st.setString(8, obj.getAmount());
                st.execute();
            }

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

    static OBJPurchaseOrder getNavi(Connection conn, int Ix) throws SQLException {

        OBJPurchaseOrder obj = null;
        //SQL Server
        //String sql = "SELECT TOP(" + Ix + ") * FROM " + TABAL + " ORDER BY OrderNo ASC";
        
        //MySql
        //Ix =- 1;
        String sql = "SELECT * FROM " + TABAL + " ORDER BY OrderNo ASC LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJPurchaseOrder(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(11),
                    result.getString(12),
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(18));
        }

        return obj;
    }

    static OBJPurchaseOrder serch(Connection conn, String code) throws SQLException {

        OBJPurchaseOrder obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE OrderNo = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJPurchaseOrder(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(11),
                    result.getString(12),
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(18));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM " + TABAL + "";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            //index++;
            index = result.getInt("val");
        }
        return index;
    }

    static ArrayList <OBJPurchaseOrder> POHistory(Connection conn, String code) throws SQLException {

       ArrayList <OBJPurchaseOrder> obja = new ArrayList<OBJPurchaseOrder>();
       OBJPurchaseOrder obj = null;
        String sql = "SELECT * FROM "+TABALHISTORY+" WHERE OrderNo = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJPurchaseOrder(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8));
            obja.add(obj);
        }

        return obja;
    }

    static void delete(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM " + TABAL + " WHERE OrderNo = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE OrderNo = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "000001";
        String sql = "SELECT OrderNo FROM " + TABAL + " ORDER BY OrderNo";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("OrderNo");
            int i = Integer.parseInt(cd);
            i++;
            cd = i + "";
            i = cd.length();
            for (int j = i; j < 6; j++) {
                cd = "0" + cd;
            }
            ID = cd;
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
    static OBJPurchaseOrder getTablInfo(Connection con, String itc, String text) throws SQLException {

        OBJPurchaseOrder obj = null;

        String sql = "SELECT ItemMaster.ShortName AS n, ItemMaster.MinLevel AS min, ItemMaster.UnitCode AS uc, Stock.SellingRate AS sr, Stock.Warranty AS wa, Stock.StockInHand AS st FROM ItemMaster, Stock WHERE ItemMaster.ItemCode = '" + itc + "' AND Stock.ItemCode = '" + itc + "' AND Stock.Store = '" + text + "' LIMIT 1";

        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJPurchaseOrder(
                    result.getString("n"),
                    result.getString("uc"),
                    result.getDouble("sr"),
                    result.getString("wa"));
            if (result.getDouble("st") <= result.getDouble("min")) {
                JOptionPane.showMessageDialog(null, "There are " + result.getDouble("st") + " items in this store...");
            }
        }

        return obj;
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

}
