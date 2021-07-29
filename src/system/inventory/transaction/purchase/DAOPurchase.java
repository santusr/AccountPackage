/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.purchase;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import system.inventory.InvoiceStatus;

/**
 *
 * @author dell
 */
public class DAOPurchase {
//set Table

    private static final String TABAL = "PurchaseHeader";
    private static final String TABALHISTORY = "PurchaseHistory";

    static void save(OBJPurchase obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO " + TABAL + " ( PurNo, PurDate, VendCode, CrCode, RepCode, CostCode, StoreCode, AreaCode, FCCode, FCRate, PaymentTerms, PreparedBy, ApprovedBy, Remark, GrossAmount, TotalDiscount, DiscountRate, Other, NetAmount, PayAmount, DueAmount, UserId, PurType, Ref, InvNo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getInvoNo());
            st.setString(2, obj.getInvoDate());
            st.setString(3, obj.getCussCode());
            st.setString(4, obj.getCrAcc());
            st.setString(5, obj.getRepCode());
            st.setString(6, obj.getCostCode());
            st.setString(7, obj.getStoreCode());
            st.setString(8, obj.getAreaCode());
            st.setString(9, obj.getFCCode());
            st.setString(10, obj.getFCRate());
            st.setString(11, obj.getPaymentTerms());
            st.setString(12, obj.getPrepBy());
            st.setString(13, obj.getApproBy());
            st.setString(14, obj.getRemarks());
            st.setString(15, obj.getGrossAmount());
            st.setString(16, obj.getTotalDiscount());
            st.setString(17, obj.getDiscountRate());
            st.setString(18, obj.getOther());
            st.setString(19, obj.getNetAmount());
            st.setString(20, obj.getPayAmount());
            st.setString(21, obj.getDueAmount());
            st.setString(22, obj.getUserId());
            st.setString(23, obj.getInvoType());
            st.setString(24, obj.getRef());
            st.setString(25, obj.getPInvoNo());
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

    static void saveHistory(ArrayList<OBJPurchase> obja, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            for (OBJPurchase obj : obja) {
                String sql = "INSERT INTO " + TABALHISTORY + " ( PurNo, ItemCode, ItemDescription, UnitCode, Qty, Rate, Discount, Amount, Warranty, SN) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, obj.getInvoNo());
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

                // st.close();
                sql = " UPDATE "
                        + "             `itemmaster` "
                        + "SET "
                        + "             `SellingRate` = '" + obj.getSellingRate() + "', "
                        + "             `minSellingRate` = '" + obj.getMinSellingRate() + "', "
                        + "             `CostRate` = ((CostRate * if(StockInHand > 0, StockInHand, '1'))+'" + Double.parseDouble(obj.getNet()) + "') / (if(StockInHand > 0, StockInHand, '1') + '" + Double.parseDouble(obj.getQuantity()) + "'), "
                        + "             `StockInHand` =  `StockInHand` + '" + obj.getQuantity() + "' "
                        + "WHERE "
                        + "             `ItemCode` = '" + obj.getItemCode() + "'";

                st = conn.prepareStatement(sql);
                st.executeUpdate();

                sql = " UPDATE "
                        + "             `stock` "
                        + "SET "
                        + "             `StockInHand` =  `StockInHand` + '" + obj.getQuantity() + "', "
                        + "             SalsePrice='" + obj.getSellingRate()+ "' "
                        + "WHERE "
                        + "             `ItemCode` = '" + obj.getItemCode() + "' AND "
                        + "             `Store` = '" + obj.getStoreCode() + "'";

                st = conn.prepareStatement(sql);
                int b = st.executeUpdate();

                if (b == 0) {
                    sql = " INSERT INTO `stock` (`StockInHand`, `ItemCode`, `Store`, `SalsePrice`) VALUES ('" + obj.getQuantity() + "', '" + obj.getItemCode() + "', '" + obj.getStoreCode() + "', '" + obj.getSellingRate() + "')";

                    st = conn.prepareStatement(sql);
                    st.execute();
                }
                st.close();
            }

        }
    }

    static OBJPurchase getNavi(Connection conn, int Ix) throws SQLException {

        OBJPurchase obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY QuotationNo ASC";

        //MySql
        //Ix -= 1;
        String sql = "SELECT * FROM " + TABAL + " WHERE Status = '0' ORDER BY PurNo ASC LIMIT " + Ix + ",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJPurchase(
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
                    result.getString(26));
        }

        return obj;
    }

    static OBJPurchase serch(Connection conn, String code) throws SQLException {

        OBJPurchase obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE PurNo = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJPurchase(
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
                    result.getString(26));
        }

        return obj;
    }

    static ArrayList<OBJPurchase> InvoHistory(Connection conn, String code) throws SQLException {

        ArrayList<OBJPurchase> obja = new ArrayList<OBJPurchase>();
        OBJPurchase obj = null;
        String sql = "SELECT * FROM " + TABALHISTORY + "," + TABAL + " WHERE " + TABALHISTORY + ".PurNo = '" + code + "' AND " + TABAL + ".Status = '0' AND " + TABALHISTORY + ".PurNo = " + TABAL + ".PurNo";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJPurchase(
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
                    "",
                    "");
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

    static void doCancel(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE ItemCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE PurNo = '" + code + "'";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "1");
            st.execute();
        }
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "PUR000001";
        String sql = "SELECT PurNo FROM " + TABAL + " ORDER BY PurNo DESC LIMIT 1";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        int i = 0;
        while (result.next()) {
            ID = result.getString("PurNo");
            ID = ID.substring(4, 9);
            i = Integer.parseInt(ID);
        }
        i++;
        ID = i + "";
        i = ID.length();
        for (int j = i; j < 6; j++) {
            ID = "0" + ID;
        }

        return "PUR"+ID;
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

    static OBJPurchase getTablInfo(Connection con, String itc, String text) throws SQLException {

        OBJPurchase obj = null;

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
            obj = new OBJPurchase(
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
            sql = "SELECT ItemMaster.ShortName AS n, ItemMaster.MinLevel AS min, ItemMaster.UnitCode AS uc, ItemMaster.SellingRate AS sr, ItemMaster.minSellingRate AS msr, ItemMaster.Warranty AS wa, ItemMaster.Discount AS dis, ItemMaster.CostRate AS cost FROM ItemMaster, Stock WHERE ItemMaster.ItemCode = '" + itc + "' LIMIT 1";

            st = con.prepareStatement(sql);
            st.execute();
            result = st.getResultSet();
            if (result.next()) {
                obj = new OBJPurchase(
                        result.getString("n"),
                        result.getString("uc"),
                        result.getString("sr"),
                        result.getString("msr"),
                        result.getString("wa"),
                        result.getString("dis"),
                        result.getString("cost"));

                JOptionPane.showMessageDialog(null, "This is opening stock in this store...");

            } else {
                obj = new OBJPurchase(
                        result.getString(""),
                        result.getString(""),
                        result.getString("0.00"),
                        result.getString(""),
                        result.getString(""),
                        result.getString(""),
                        result.getString("0.00"));

                JOptionPane.showMessageDialog(null, "This item not found in this store...");
            }
        }
        return obj;
    }
    
    static void updateStock(ArrayList<OBJPurchase> purchases, String Store, Connection conn) throws SQLException {
        for (OBJPurchase obj : purchases) {

            String sql = "UPDATE `stock` SET `StockInHand` =  `StockInHand` - '" + Double.parseDouble(obj.getQuantity()) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "' AND `Store` = '" + Store + "'";
            Statement cst = conn.createStatement();
//                st.addBatch(sql);
            cst.addBatch(sql);

            sql = "UPDATE `itemmaster` SET `StockInHand` =  `StockInHand` - '" + Double.parseDouble(obj.getQuantity()) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "'";
            cst.addBatch(sql);
//                st.addBatch(sql);
            cst.executeBatch();
        }
    }
    
    static void doCancelTrans(String code, String transType, Connection conn) throws SQLException {
        CallableStatement cstmt = null;
        // Journal Cancel Structprocedure
        String SQL = "{call z_grn_cancel ('" + code + "', '" + transType + "')}";
        cstmt = conn.prepareCall(SQL);
        cstmt.executeQuery();
    }

}
