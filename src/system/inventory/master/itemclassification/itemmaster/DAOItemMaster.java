/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.itemclassification.itemmaster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author dell
 */
public class DAOItemMaster {
//set Table
    private static final String TABAL = "ItemMaster";
    
    static void save(OBJItemMaster obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" (ItemCode, Description, ShortName, UnitCode, GroupCode, CatCode, MinLevel, ReorderLevel, StockInHand, OnOrder, CostRate, OpeningStock, OpCostRate, Batch, OpeningDate, Remarks, costcode, ItemType, SellingRate, MinSellingRate, Warranty, Discount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getDescrip());
            st.setString(3, obj.getName());
            st.setString(4, obj.getUnitcode());
            st.setString(5, obj.getGroupcode());
            st.setString(6, obj.getCatcode());
            st.setString(7, obj.getMinlevel());
            st.setString(8, obj.getReclevel());
            st.setString(9, obj.getStockinhand());
            st.setString(10, obj.getOnorder());
            st.setString(11, obj.getOpcostrate());
            st.setString(12, obj.getOpstock());
            st.setString(13, obj.getOpcostrate());
            st.setString(14, obj.getBatch());
            st.setString(15, obj.getOpdate());
            st.setString(16, obj.getRemarks());
            st.setString(17, obj.getCostcenter());
            st.setString(18, "C");
            st.setString(19, obj.getSalePrice());
            st.setString(20, obj.getMinSalePrice());
            st.setString(21, obj.getWarranty());
            st.setString(22, obj.getDisc());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET costcode = ?, Description = ?, ShortName = ?, UnitCode = ?, GroupCode = ?, CatCode = ?, MinLevel = ?, ReorderLevel = ?, StockInHand = ?, OnOrder = ?, CostRate = ?, OpeningStock = ?, OpCostRate = ?, Batch = ?, OpeningDate = ?, Remarks = ?, SellingRate = ?, MinSellingRate = ?, Warranty = ?, Discount = ? WHERE ItemCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCostcenter());
            st.setString(2, obj.getDescrip());
            st.setString(3, obj.getName());
            st.setString(4, obj.getUnitcode());
            st.setString(5, obj.getGroupcode());
            st.setString(6, obj.getCatcode());
            st.setString(7, obj.getMinlevel());
            st.setString(8, obj.getReclevel());
            st.setString(9, obj.getStockinhand());
            st.setString(10, obj.getOnorder());
            st.setString(11, obj.getOpcostrate());
            st.setString(12, obj.getOpstock());
            st.setString(13, obj.getOpcostrate());
            st.setString(14, obj.getBatch());
            st.setString(15, obj.getOpdate());
            st.setString(16, obj.getRemarks());
            st.setString(17, obj.getSalePrice());
            st.setString(18, obj.getMinSalePrice());
            st.setString(19, obj.getWarranty());
            st.setString(20, obj.getDisc());
            st.execute();
        }
    }

    static OBJItemMaster getNavi(Connection conn, int Ix) throws SQLException {

        OBJItemMaster obj = null;
        // SQL Server
        // String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY ItemCode ASC";

        //MySql  
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" WHERE Status = '0' ORDER BY ItemCode ASC LIMIT "+Ix+",1";
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJItemMaster(
                    result.getString(1),
                    result.getString(3),
                    result.getString(2),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(12),
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(20),
                    result.getString(21),
                    result.getString(24),
                    result.getString(23));
        }

        return obj;
    }

    static OBJItemMaster serch(Connection conn, String code) throws SQLException {

        OBJItemMaster obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE ItemCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJItemMaster(
                    result.getString(1),
                    result.getString(3),
                    result.getString(2),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(12),
                    result.getString(13),
                    result.getString(14),
                    result.getString(15),
                    result.getString(16),
                    result.getString(17),
                    result.getString(20),
                    result.getString(21),
                    result.getString(24),
                    result.getString(23));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS inx FROM "+TABAL+"";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            index = result.getInt("inx");
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE ItemCode = '" + code + "'");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "001";
        String sql = "SELECT ItemCode FROM "+TABAL+" ORDER BY ItemCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("ItemCode");
            int i = Integer.parseInt(cd);
            i++;
            cd = i+"";
            i = cd.length();
            for (int j = i; j < 3; j++) {
                cd = "0"+cd;
            }
            ID = cd;
        }
        return ID;
    }

    static String genName(Connection conn, String tble, String code, String col) throws SQLException{
        String name = "";
        
         String sql = "SELECT Description FROM "+tble+" WHERE "+col+" = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            name = result.getString("Description");
        }
        
        return name;
    }

    static Vector getUnit(Connection conn) throws SQLException{
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

    static String getUnitName(Connection conn, String UOMCode) throws SQLException{
        String Uname = null;
         String sql = "SELECT Description FROM UOM WHERE UOMCode = '"+UOMCode+"'";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("Description");
            Uname = cd;
        }
        return Uname;
    }

    static void saveStock(Connection conn, OBJItemMaster obj) throws SQLException {
        String sql = " UPDATE `stock` SET `StockInHand` = '" + obj.getStockinhand() + "', `SalsePrice` = '"+obj.getSalePrice()+"' WHERE `ItemCode` = '" + obj.getCode() + "' AND `Store` = '" + obj.getStore() + "'";

               PreparedStatement st = conn.prepareStatement(sql);
                int b = st.executeUpdate();
                System.out.println("Sales Price - " + obj.getSalePrice());
                if (b == 0) {
                    sql = " INSERT INTO `stock` (`StockInHand`, `ItemCode`, `Store`, `SalsePrice`, `warranty`) VALUES ('" + obj.getStockinhand() + "','" + obj.getCode() + "', '" + obj.getStore() + "', '"+obj.getSalePrice()+"', '"+obj.getWarranty()+"')";

                    st = conn.prepareStatement(sql);
                    st.execute();
                }
                st.close();
    }
    }

