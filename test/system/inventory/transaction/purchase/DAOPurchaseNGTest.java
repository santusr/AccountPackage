/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.purchase;

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
public class DAOPurchaseNGTest {
    
    public DAOPurchaseNGTest() {
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
     * Test of save method, of class DAOPurchase.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJPurchase obj = null;
        Connection conn = null;
        int Act = 0;
        DAOPurchase.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveHistory method, of class DAOPurchase.
     */
    @Test
    public void testSaveHistory() throws Exception {
        System.out.println("saveHistory");
        ArrayList<OBJPurchase> obja = null;
        Connection conn = null;
        int Act = 0;
        DAOPurchase.saveHistory(obja, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class DAOPurchase.
     */
    @Test
    public void testGetNavi() throws Exception {
        System.out.println("getNavi");
        Connection conn = null;
        int Ix = 0;
        OBJPurchase expResult = null;
        OBJPurchase result = DAOPurchase.getNavi(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOPurchase.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJPurchase expResult = null;
        OBJPurchase result = DAOPurchase.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of InvoHistory method, of class DAOPurchase.
     */
    @Test
    public void testInvoHistory() throws Exception {
        System.out.println("InvoHistory");
        Connection conn = null;
        String code = "";
        ArrayList expResult = null;
        ArrayList result = DAOPurchase.InvoHistory(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class DAOPurchase.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Connection conn = null;
        int expResult = 0;
        int result = DAOPurchase.getValue(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAOPurchase.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Connection conn = null;
        String code = "";
        DAOPurchase.delete(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOPurchase.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOPurchase.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genName method, of class DAOPurchase.
     */
    @Test
    public void testGenName() throws Exception {
        System.out.println("genName");
        Connection conn = null;
        String tble = "";
        String code = "";
        String col = "";
        String expResult = "";
        String result = DAOPurchase.genName(conn, tble, code, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnit method, of class DAOPurchase.
     */
    @Test
    public void testGetUnit() throws Exception {
        System.out.println("getUnit");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOPurchase.getUnit(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnitName method, of class DAOPurchase.
     */
    @Test
    public void testGetUnitName() throws Exception {
        System.out.println("getUnitName");
        Connection conn = null;
        String UOMCode = "";
        String expResult = "";
        String result = DAOPurchase.getUnitName(conn, UOMCode);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCurrency method, of class DAOPurchase.
     */
    @Test
    public void testLoadCurrency() throws Exception {
        System.out.println("loadCurrency");
        Connection con = null;
        Vector expResult = null;
        Vector result = DAOPurchase.loadCurrency(con);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gatFCRate method, of class DAOPurchase.
     */
    @Test
    public void testGatFCRate() throws Exception {
        System.out.println("gatFCRate");
        Connection con = null;
        String cc = "";
        double expResult = 0.0;
        double result = DAOPurchase.gatFCRate(con, cc);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTablInfo method, of class DAOPurchase.
     */
    @Test
    public void testGetTablInfo() throws Exception {
        System.out.println("getTablInfo");
        Connection con = null;
        String itc = "";
        String text = "";
        OBJPurchase expResult = null;
        OBJPurchase result = DAOPurchase.getTablInfo(con, itc, text);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
