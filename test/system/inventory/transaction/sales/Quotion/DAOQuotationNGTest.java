/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.sales.Quotion;

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
public class DAOQuotationNGTest {
    
    public DAOQuotationNGTest() {
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
     * Test of save method, of class DAOQuotation.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJQuotation obj = null;
        Connection conn = null;
        int Act = 0;
        DAOQuotation.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveHistory method, of class DAOQuotation.
     */
    @Test
    public void testSaveHistory() throws Exception {
        System.out.println("saveHistory");
        ArrayList<OBJQuotation> obja = null;
        Connection conn = null;
        int Act = 0;
        DAOQuotation.saveHistory(obja, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class DAOQuotation.
     */
    @Test
    public void testGetNavi() throws Exception {
        System.out.println("getNavi");
        Connection conn = null;
        int Ix = 0;
        OBJQuotation expResult = null;
        OBJQuotation result = DAOQuotation.getNavi(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOQuotation.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJQuotation expResult = null;
        OBJQuotation result = DAOQuotation.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of QuotHistory method, of class DAOQuotation.
     */
    @Test
    public void testQuotHistory() throws Exception {
        System.out.println("QuotHistory");
        Connection conn = null;
        String code = "";
        ArrayList expResult = null;
        ArrayList result = DAOQuotation.QuotHistory(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class DAOQuotation.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Connection conn = null;
        int expResult = 0;
        int result = DAOQuotation.getValue(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAOQuotation.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Connection conn = null;
        String code = "";
        DAOQuotation.delete(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOQuotation.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOQuotation.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genName method, of class DAOQuotation.
     */
    @Test
    public void testGenName() throws Exception {
        System.out.println("genName");
        Connection conn = null;
        String tble = "";
        String code = "";
        String col = "";
        String expResult = "";
        String result = DAOQuotation.genName(conn, tble, code, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnit method, of class DAOQuotation.
     */
    @Test
    public void testGetUnit() throws Exception {
        System.out.println("getUnit");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOQuotation.getUnit(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnitName method, of class DAOQuotation.
     */
    @Test
    public void testGetUnitName() throws Exception {
        System.out.println("getUnitName");
        Connection conn = null;
        String UOMCode = "";
        String expResult = "";
        String result = DAOQuotation.getUnitName(conn, UOMCode);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCurrency method, of class DAOQuotation.
     */
    @Test
    public void testLoadCurrency() throws Exception {
        System.out.println("loadCurrency");
        Connection con = null;
        Vector expResult = null;
        Vector result = DAOQuotation.loadCurrency(con);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gatFCRate method, of class DAOQuotation.
     */
    @Test
    public void testGatFCRate() throws Exception {
        System.out.println("gatFCRate");
        Connection con = null;
        String cc = "";
        double expResult = 0.0;
        double result = DAOQuotation.gatFCRate(con, cc);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTablInfo method, of class DAOQuotation.
     */
    @Test
    public void testGetTablInfo() throws Exception {
        System.out.println("getTablInfo");
        Connection con = null;
        String itc = "";
        String text = "";
        OBJQuotation expResult = null;
        OBJQuotation result = DAOQuotation.getTablInfo(con, itc, text);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
