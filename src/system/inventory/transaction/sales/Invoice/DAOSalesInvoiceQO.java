/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.Invoice;

import java.sql.CallableStatement;
import system.inventory.transaction.item_list.OBJItemSearch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import system.inventory.InvoiceStatus;
import system.inventory.ItemStatus;

/**
 *
 * @author dell
 */
public class DAOSalesInvoiceQO {
//set Table

    private static final String TABAL = "InvoiceHeader";
    private static final String TABALHISTORY = "InvoiceHistory";

    static void save(OBJSalesInvoiceQO obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO " + TABAL + " ( InvoNo, InvoDate, CustCode, CrCode, RepCode, CostCode, StoreCode, AreaCode, FCCode, FCRate, PaymentTerms, PreparedBy, ApprovedBy, Remark, GrossAmount, TotalDiscount, DiscountRate, Other, NetAmount, PayAmount, DueAmount, UserId, InvoType, Ref, CPDate, loan_no) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            st.setString(25, obj.getCPDate());
            st.setString(26, obj.getLoanNo());
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

    static void saveHistory(ArrayList<OBJSalesInvoiceQO> obja, String invoNo, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            for (OBJSalesInvoiceQO obj : obja) {
                String sql = "INSERT INTO " + TABALHISTORY + " ( InvoNo, ItemCode, ItemDescription, UnitCode, Qty, Rate, Discount, Amount, Warranty, SN, costRate, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, invoNo);
                st.setString(2, obj.getItemCode());
                st.setString(3, obj.getItemDescription());
                st.setString(4, obj.getUnitCode());
                st.setString(5, obj.getQuantity());
                st.setString(6, obj.getRate());
                st.setString(7, obj.getDiscount());
                st.setString(8, obj.getNet());
                st.setString(9, obj.getWarranty());
                st.setString(10, obj.getSn());
                st.setString(11, obj.getCost_rate());
                st.setString(12, obj.getStatus());
                st.execute();
//                st.addBatch(sql);
                sql = "UPDATE `stock` SET `StockInHand` =  `StockInHand` - '" + Double.parseDouble(obj.getQuantity()) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "' AND `Store` = '" + obj.getStoreCode() + "'";
                Statement cst = conn.createStatement();
//                st.addBatch(sql);
                cst.addBatch(sql);

                sql = "UPDATE `itemmaster` SET `StockInHand` =  `StockInHand` - '" + Double.parseDouble(obj.getQuantity()) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "'";
                cst.addBatch(sql);
//                st.addBatch(sql);
                cst.executeBatch();
//                st.executeBatch();

            }

        }
    }

    static OBJSalesInvoiceQO getNavi(Connection conn, int Ix) throws SQLException {

        OBJSalesInvoiceQO obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY QuotationNo ASC";

        //MySql
        //Ix -= 1;
        String sql = "SELECT * FROM " + TABAL + " WHERE Status = '" + InvoiceStatus.ACTIVE + "' ORDER BY InvoNo ASC LIMIT " + Ix + ",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJSalesInvoiceQO(
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

    static OBJSalesInvoiceQO serch(Connection conn, String code) throws SQLException {
        OBJSalesInvoiceQO obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE InvoNo = '" + code + "' AND Status = '" + InvoiceStatus.ACTIVE + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJSalesInvoiceQO(
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

    /////SUSPEND SEARCH
    static OBJSalesInvoiceQO serchSuspend(Connection conn, String code) throws SQLException {
        OBJSalesInvoiceQO obj = null;
        String sql = "SELECT * FROM " + TABAL + " WHERE InvoNo = '" + code + "' AND Status = '" + InvoiceStatus.SUSPEND + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJSalesInvoiceQO(
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

    static ArrayList<OBJSalesInvoiceQO> InvoHistorySuspend(Connection conn, String code) throws SQLException {

        ArrayList<OBJSalesInvoiceQO> obja = new ArrayList<OBJSalesInvoiceQO>();
        OBJSalesInvoiceQO obj = null;
        String sql = "SELECT \n"
                + "				invoicehistory.*,\n"
                + "				invoiceheader.StoreCode,\n"
                + "				IFNULL(invoicehistory.Qty - salesreturn.ReturnQty, invoicehistory.Qty) AS av_qty \n"
                + "FROM \n"
                + "				invoicehistory \n"
                + "				LEFT JOIN invoiceheader ON invoicehistory.InvoNo = invoiceheader.InvoNo\n"
                + "				LEFT JOIN salesreturn ON salesreturn.ItemCode = invoicehistory.ItemCode AND salesreturn.InvoNo = invoicehistory.InvoNo \n"
                + "				\n"
                + "WHERE 	\n"
                + "				IFNULL(invoicehistory.Qty - salesreturn.ReturnQty, invoicehistory.Qty) > 0 AND\n"
                + "				invoicehistory.InvoNo = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            double qty = Double.parseDouble(result.getString("invoicehistory.Qty"));
            double net = Double.parseDouble(result.getString("invoicehistory.Amount"));
            double avQty = Double.parseDouble(result.getString("av_qty"));
            double newNet = (net / qty) * avQty;

            obj = new OBJSalesInvoiceQO(
                    result.getString("InvoNo"),
                    result.getString("ItemCode"),
                    result.getString("ItemDescription"),
                    result.getString("av_qty"),
                    result.getString("Rate"),
                    result.getString("Discount"),
                    result.getString("UnitCode"),
                    newNet + "",
                    result.getString("Warranty"),
                    result.getString("SN"),
                    result.getString("StoreCode"),
                    result.getString("CostRate"),
                    ItemStatus.RECALL);
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

    static void doCancel(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE ItemCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE InvoNo = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, InvoiceStatus.CANCEL);
        st.execute();
        st.close();
    }

    static void updateRecall(String code, Connection conn) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE ItemCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE InvoNo = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, InvoiceStatus.RECALLED);
        st.execute();
        st.close();
    }

    static void CancleInvoice(String code, Connection conn) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE ItemCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE InvoNo = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, InvoiceStatus.CANCEL);
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "INV000001";
        String sql = "SELECT InvoNo FROM " + TABAL + " ORDER BY InvoNo DESC LIMIT 1";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        int i = 0;
        while (result.next()) {
            ID = result.getString("InvoNo");
            ID = ID.substring(4, 9);
            i = Integer.parseInt(ID);
        }
        i++;
        ID = i + "";
        i = ID.length();
        for (int j = i; j < 6; j++) {
            ID = "0" + ID;
        }

        return "INV" + ID;
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

        String sql
                = "SELECT \n"
                + "				ItemMaster.ItemCode AS code, \n"
                + "				ItemMaster.ShortName AS n, \n"
                + "				ItemMaster.ReorderLevel AS rl, \n"
                + "				ItemMaster.UnitCode AS uc, \n"
                + "				stock.SalsePrice AS sr, \n"
                + "				ItemMaster.MinSellingRate AS msr, \n"
                + "				ItemMaster.Warranty AS wa, \n"
                + "				ItemMaster.Discount AS dis, \n"
                + "				Stock.StockInHand AS st, \n"
                + "				ItemMaster.CostRate AS cost \n"
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
            obj = new OBJSalesInvoiceQO(
                    result.getString("code"),
                    result.getString("n"),
                    result.getString("uc"),
                    result.getString("sr"),
                    result.getString("msr"),
                    result.getString("wa"),
                    result.getString("dis"),
                    result.getString("cost"),
                    result.getString("rl"),
                    result.getString("st"),
                    result.getString("cost"));
//            if (result.getDouble("st") <= result.getDouble("min")) {
//                JOptionPane.showMessageDialog(null, "There are " + result.getDouble("st") + " items in this store...");
//            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found in this store...");
            // Commented at 26/07/2021
//            obj = new OBJSalesInvoiceQO(
//                    "",
//                    "",
//                    "",
//                    "0.00",
//                    "",
//                    "",
//                    "",
//                    "",
//                    "",
//                    "",
//                    "");
        }

        return obj;
    }

    static Vector getItem(Connection conn, String toString) throws SQLException {
        OBJItemSearch obj = null;
        Vector v = new Vector();
        String sql
                = "SELECT \n"
                + "				ItemMaster.ItemCode AS ic, \n"
                + "				ItemMaster.ShortName AS n \n"
                + "FROM \n"
                + "				ItemMaster \n"
                + "WHERE \n"
                + "				ItemMaster.ItemCode LIKE '" + toString + "%'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            obj = new OBJItemSearch();
            obj.setCode(result.getString("ic"));
            obj.setName(result.getString("n"));
            v.add(obj);
        }
        return v;
    }

    static void updateStock(ArrayList<OBJSalesInvoiceQO> invoiceQOs, String Store, Connection conn) throws SQLException {
        for (OBJSalesInvoiceQO obj : invoiceQOs) {

            String sql = "UPDATE `stock` SET `StockInHand` =  `StockInHand` + '" + Double.parseDouble(obj.getQuantity()) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "' AND `Store` = '" + Store + "'";
            Statement cst = conn.createStatement();
//                st.addBatch(sql);
            cst.addBatch(sql);

            sql = "UPDATE `itemmaster` SET `StockInHand` =  `StockInHand` + '" + Double.parseDouble(obj.getQuantity()) + "' WHERE `ItemCode` = '" + obj.getItemCode() + "'";
            cst.addBatch(sql);
//                st.addBatch(sql);
            cst.executeBatch();
        }
    }

    static void doCancelTrans(String code, String transType, Connection conn) throws SQLException {
        CallableStatement cstmt = null;
        // Journal Cancel Structprocedure
        String SQL = "{call z_invoice_cancel ('" + code + "', '" + transType + "')}";
        cstmt = conn.prepareCall(SQL);
        cstmt.executeQuery();
    }

}
