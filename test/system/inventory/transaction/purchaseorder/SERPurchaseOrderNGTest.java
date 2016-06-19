/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.purchaseorder;

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
public class SERPurchaseOrderNGTest {
    
    public SERPurchaseOrderNGTest() {
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
     * Test of save method, of class SERPurchaseOrder.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJPurchaseOrder obj = null;
        int Act = 0;
        SERPurchaseOrder.save(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveHistory method, of class SERPurchaseOrder.
     */
    @Test
    public void testSaveHistory() {
        System.out.println("saveHistory");
        ArrayList<OBJPurchaseOrder> obj = null;
        int Act = 0;
        SERPurchaseOrder.saveHistory(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getitemCat method, of class SERPurchaseOrder.
     */
    @Test
    public void testGetitemCat() {
        System.out.println("getitemCat");
        int Index = 0;
        OBJPurchaseOrder expResult = null;
        OBJPurchaseOrder result = SERPurchaseOrder.getitemCat(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERPurchaseOrder.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJPurchaseOrder expResult = null;
        OBJPurchaseOrder result = SERPurchaseOrder.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHistory method, of class SERPurchaseOrder.
     */
    @Test
    public void testGetHistory() {
        System.out.println("getHistory");
        String code = "";
        ArrayList expResult = null;
        ArrayList result = SERPurchaseOrder.getHistory(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERPurchaseOrder.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERPurchaseOrder.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERPurchaseOrder.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERPurchaseOrder.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERPurchaseOrder.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJPurchaseOrder expResult = null;
        OBJPurchaseOrder result = SERPurchaseOrder.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERPurchaseOrder.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERPurchaseOrder.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SERPurchaseOrder.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String code = "";
        String tble = "";
        String col = "";
        String expResult = "";
        String result = SERPurchaseOrder.setName(code, tble, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnit method, of class SERPurchaseOrder.
     */
    @Test
    public void testGetUnit() {
        System.out.println("getUnit");
        Vector expResult = null;
        Vector result = SERPurchaseOrder.getUnit();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUName method, of class SERPurchaseOrder.
     */
    @Test
    public void testGetUName() {
        System.out.println("getUName");
        String UOMCode = "";
        String expResult = "";
        String result = SERPurchaseOrder.getUName(UOMCode);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCurrency method, of class SERPurchaseOrder.
     */
    @Test
    public void testLoadCurrency() {
        System.out.println("loadCurrency");
        Vector expResult = null;
        Vector result = SERPurchaseOrder.loadCurrency();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTablInfo method, of class SERPurchaseOrder.
     */
    @Test
    public void testGetTablInfo() {
        System.out.println("getTablInfo");
        String itc = "";
        String text = "";
        OBJPurchaseOrder expResult = null;
        OBJPurchaseOrder result = SERPurchaseOrder.getTablInfo(itc, text);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFCRate method, of class SERPurchaseOrder.
     */
    @Test
    public void testGetFCRate() {
        System.out.println("getFCRate");
        String cc = "";
        double expResult = 0.0;
        double result = SERPurchaseOrder.getFCRate(cc);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
