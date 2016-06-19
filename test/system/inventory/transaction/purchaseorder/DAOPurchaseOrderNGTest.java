/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.purchaseorder;

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
public class DAOPurchaseOrderNGTest {
    
    public DAOPurchaseOrderNGTest() {
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
     * Test of save method, of class DAOPurchaseOrder.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJPurchaseOrder obj = null;
        Connection conn = null;
        int Act = 0;
        DAOPurchaseOrder.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveHistory method, of class DAOPurchaseOrder.
     */
    @Test
    public void testSaveHistory() throws Exception {
        System.out.println("saveHistory");
        ArrayList<OBJPurchaseOrder> obja = null;
        Connection conn = null;
        int Act = 0;
        DAOPurchaseOrder.saveHistory(obja, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class DAOPurchaseOrder.
     */
    @Test
    public void testGetNavi() throws Exception {
        System.out.println("getNavi");
        Connection conn = null;
        int Ix = 0;
        OBJPurchaseOrder expResult = null;
        OBJPurchaseOrder result = DAOPurchaseOrder.getNavi(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOPurchaseOrder.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJPurchaseOrder expResult = null;
        OBJPurchaseOrder result = DAOPurchaseOrder.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class DAOPurchaseOrder.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Connection conn = null;
        int expResult = 0;
        int result = DAOPurchaseOrder.getValue(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of POHistory method, of class DAOPurchaseOrder.
     */
    @Test
    public void testPOHistory() throws Exception {
        System.out.println("POHistory");
        Connection conn = null;
        String code = "";
        ArrayList expResult = null;
        ArrayList result = DAOPurchaseOrder.POHistory(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAOPurchaseOrder.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Connection conn = null;
        String code = "";
        DAOPurchaseOrder.delete(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOPurchaseOrder.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOPurchaseOrder.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genName method, of class DAOPurchaseOrder.
     */
    @Test
    public void testGenName() throws Exception {
        System.out.println("genName");
        Connection conn = null;
        String tble = "";
        String code = "";
        String col = "";
        String expResult = "";
        String result = DAOPurchaseOrder.genName(conn, tble, code, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnit method, of class DAOPurchaseOrder.
     */
    @Test
    public void testGetUnit() throws Exception {
        System.out.println("getUnit");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOPurchaseOrder.getUnit(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnitName method, of class DAOPurchaseOrder.
     */
    @Test
    public void testGetUnitName() throws Exception {
        System.out.println("getUnitName");
        Connection conn = null;
        String UOMCode = "";
        String expResult = "";
        String result = DAOPurchaseOrder.getUnitName(conn, UOMCode);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCurrency method, of class DAOPurchaseOrder.
     */
    @Test
    public void testLoadCurrency() throws Exception {
        System.out.println("loadCurrency");
        Connection con = null;
        Vector expResult = null;
        Vector result = DAOPurchaseOrder.loadCurrency(con);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTablInfo method, of class DAOPurchaseOrder.
     */
    @Test
    public void testGetTablInfo() throws Exception {
        System.out.println("getTablInfo");
        Connection con = null;
        String itc = "";
        String text = "";
        OBJPurchaseOrder expResult = null;
        OBJPurchaseOrder result = DAOPurchaseOrder.getTablInfo(con, itc, text);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gatFCRate method, of class DAOPurchaseOrder.
     */
    @Test
    public void testGatFCRate() throws Exception {
        System.out.println("gatFCRate");
        Connection con = null;
        String cc = "";
        double expResult = 0.0;
        double result = DAOPurchaseOrder.gatFCRate(con, cc);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
