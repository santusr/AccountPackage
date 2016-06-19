/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.sales.Quotion;

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
public class SERQuotationNGTest {
    
    public SERQuotationNGTest() {
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
     * Test of save method, of class SERQuotation.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJQuotation obj = null;
        int Act = 0;
        SERQuotation.save(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveHistory method, of class SERQuotation.
     */
    @Test
    public void testSaveHistory() {
        System.out.println("saveHistory");
        ArrayList<OBJQuotation> obj = null;
        int Act = 0;
        SERQuotation.saveHistory(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getitemCat method, of class SERQuotation.
     */
    @Test
    public void testGetitemCat() {
        System.out.println("getitemCat");
        int Index = 0;
        OBJQuotation expResult = null;
        OBJQuotation result = SERQuotation.getitemCat(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERQuotation.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJQuotation expResult = null;
        OBJQuotation result = SERQuotation.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERQuotation.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERQuotation.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERQuotation.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERQuotation.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERQuotation.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJQuotation expResult = null;
        OBJQuotation result = SERQuotation.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of QuotHistory method, of class SERQuotation.
     */
    @Test
    public void testQuotHistory() {
        System.out.println("QuotHistory");
        String code = "";
        ArrayList expResult = null;
        ArrayList result = SERQuotation.QuotHistory(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERQuotation.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERQuotation.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SERQuotation.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String code = "";
        String tble = "";
        String col = "";
        String expResult = "";
        String result = SERQuotation.setName(code, tble, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnit method, of class SERQuotation.
     */
    @Test
    public void testGetUnit() {
        System.out.println("getUnit");
        Vector expResult = null;
        Vector result = SERQuotation.getUnit();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUName method, of class SERQuotation.
     */
    @Test
    public void testGetUName() {
        System.out.println("getUName");
        String UOMCode = "";
        String expResult = "";
        String result = SERQuotation.getUName(UOMCode);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCurrency method, of class SERQuotation.
     */
    @Test
    public void testLoadCurrency() {
        System.out.println("loadCurrency");
        Vector expResult = null;
        Vector result = SERQuotation.loadCurrency();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFCRate method, of class SERQuotation.
     */
    @Test
    public void testGetFCRate() {
        System.out.println("getFCRate");
        String cc = "";
        double expResult = 0.0;
        double result = SERQuotation.getFCRate(cc);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTablInfo method, of class SERQuotation.
     */
    @Test
    public void testGetTablInfo() {
        System.out.println("getTablInfo");
        String itc = "";
        String text = "";
        OBJQuotation expResult = null;
        OBJQuotation result = SERQuotation.getTablInfo(itc, text);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
