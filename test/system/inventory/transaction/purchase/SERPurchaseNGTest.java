/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.purchase;

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
public class SERPurchaseNGTest {
    
    public SERPurchaseNGTest() {
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
     * Test of save method, of class SERPurchase.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJPurchase obji = null;
        int Act = 0;
        SERPurchase.save(obji, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveHistory method, of class SERPurchase.
     */
    @Test
    public void testSaveHistory() {
        System.out.println("saveHistory");
        ArrayList<OBJPurchase> obj = null;
        int Act = 0;
        SERPurchase.saveHistory(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getitemCat method, of class SERPurchase.
     */
    @Test
    public void testGetitemCat() {
        System.out.println("getitemCat");
        int Index = 0;
        OBJPurchase expResult = null;
        OBJPurchase result = SERPurchase.getitemCat(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERPurchase.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJPurchase expResult = null;
        OBJPurchase result = SERPurchase.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERPurchase.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERPurchase.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERPurchase.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERPurchase.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERPurchase.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJPurchase expResult = null;
        OBJPurchase result = SERPurchase.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of InvoHistory method, of class SERPurchase.
     */
    @Test
    public void testInvoHistory() {
        System.out.println("InvoHistory");
        String code = "";
        ArrayList expResult = null;
        ArrayList result = SERPurchase.InvoHistory(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERPurchase.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERPurchase.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SERPurchase.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String code = "";
        String tble = "";
        String col = "";
        String expResult = "";
        String result = SERPurchase.setName(code, tble, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnit method, of class SERPurchase.
     */
    @Test
    public void testGetUnit() {
        System.out.println("getUnit");
        Vector expResult = null;
        Vector result = SERPurchase.getUnit();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUName method, of class SERPurchase.
     */
    @Test
    public void testGetUName() {
        System.out.println("getUName");
        String UOMCode = "";
        String expResult = "";
        String result = SERPurchase.getUName(UOMCode);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCurrency method, of class SERPurchase.
     */
    @Test
    public void testLoadCurrency() {
        System.out.println("loadCurrency");
        Vector expResult = null;
        Vector result = SERPurchase.loadCurrency();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFCRate method, of class SERPurchase.
     */
    @Test
    public void testGetFCRate() {
        System.out.println("getFCRate");
        String cc = "";
        double expResult = 0.0;
        double result = SERPurchase.getFCRate(cc);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTablInfo method, of class SERPurchase.
     */
    @Test
    public void testGetTablInfo() {
        System.out.println("getTablInfo");
        String itc = "";
        String text = "";
        OBJPurchase expResult = null;
        OBJPurchase result = SERPurchase.getTablInfo(itc, text);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
