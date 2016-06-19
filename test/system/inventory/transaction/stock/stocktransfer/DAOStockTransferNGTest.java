/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.stock.stocktransfer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Administrator
 */
public class DAOStockTransferNGTest {
    
    public DAOStockTransferNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of save method, of class DAOStockTransfer.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJStockTransfer obj = null;
        Connection conn = null;
        int Act = 0;
        DAOStockTransfer.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveHistory method, of class DAOStockTransfer.
     */
    @Test
    public void testSaveHistory() throws Exception {
        System.out.println("saveHistory");
        ArrayList<OBJStockTransfer> obja = null;
        Connection conn = null;
        int Act = 0;
        DAOStockTransfer.saveHistory(obja, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class DAOStockTransfer.
     */
    @Test
    public void testGetNavi() throws Exception {
        System.out.println("getNavi");
        Connection conn = null;
        int Ix = 0;
        OBJStockTransfer expResult = null;
        OBJStockTransfer result = DAOStockTransfer.getNavi(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOStockTransfer.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJStockTransfer expResult = null;
        OBJStockTransfer result = DAOStockTransfer.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of InvoHistory method, of class DAOStockTransfer.
     */
    @Test
    public void testInvoHistory() throws Exception {
        System.out.println("InvoHistory");
        Connection conn = null;
        String code = "";
        ArrayList expResult = null;
        ArrayList result = DAOStockTransfer.InvoHistory(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class DAOStockTransfer.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Connection conn = null;
        int expResult = 0;
        int result = DAOStockTransfer.getValue(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAOStockTransfer.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Connection conn = null;
        String code = "";
        DAOStockTransfer.delete(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOStockTransfer.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOStockTransfer.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genName method, of class DAOStockTransfer.
     */
    @Test
    public void testGenName() throws Exception {
        System.out.println("genName");
        Connection conn = null;
        String tble = "";
        String code = "";
        String col = "";
        String expResult = "";
        String result = DAOStockTransfer.genName(conn, tble, code, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnit method, of class DAOStockTransfer.
     */
    @Test
    public void testGetUnit() throws Exception {
        System.out.println("getUnit");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOStockTransfer.getUnit(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnitName method, of class DAOStockTransfer.
     */
    @Test
    public void testGetUnitName() throws Exception {
        System.out.println("getUnitName");
        Connection conn = null;
        String UOMCode = "";
        String expResult = "";
        String result = DAOStockTransfer.getUnitName(conn, UOMCode);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCurrency method, of class DAOStockTransfer.
     */
    @Test
    public void testLoadCurrency() throws Exception {
        System.out.println("loadCurrency");
        Connection con = null;
        Vector expResult = null;
        Vector result = DAOStockTransfer.loadCurrency(con);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gatFCRate method, of class DAOStockTransfer.
     */
    @Test
    public void testGatFCRate() throws Exception {
        System.out.println("gatFCRate");
        Connection con = null;
        String cc = "";
        double expResult = 0.0;
        double result = DAOStockTransfer.gatFCRate(con, cc);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTablInfo method, of class DAOStockTransfer.
     */
    @Test
    public void testGetTablInfo() throws Exception {
        System.out.println("getTablInfo");
        Connection con = null;
        String itc = "";
        String text = "";
        OBJStockTransfer expResult = null;
        OBJStockTransfer result = DAOStockTransfer.getTablInfo(con, itc, text);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
