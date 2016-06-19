/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.master.itemclassification.itemmaster;

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
public class SERItemMasterNGTest {
    
    public SERItemMasterNGTest() {
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
     * Test of save method, of class SERItemMaster.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJItemMaster obj = null;
        int Act = 0;
        SERItemMaster.save(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getitemCat method, of class SERItemMaster.
     */
    @Test
    public void testGetitemCat() {
        System.out.println("getitemCat");
        int Index = 0;
        OBJItemMaster expResult = null;
        OBJItemMaster result = SERItemMaster.getitemCat(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERItemMaster.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJItemMaster expResult = null;
        OBJItemMaster result = SERItemMaster.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERItemMaster.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERItemMaster.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERItemMaster.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERItemMaster.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERItemMaster.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJItemMaster expResult = null;
        OBJItemMaster result = SERItemMaster.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERItemMaster.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERItemMaster.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SERItemMaster.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String code = "";
        String tble = "";
        String col = "";
        String expResult = "";
        String result = SERItemMaster.setName(code, tble, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnit method, of class SERItemMaster.
     */
    @Test
    public void testGetUnit() {
        System.out.println("getUnit");
        Vector expResult = null;
        Vector result = SERItemMaster.getUnit();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUName method, of class SERItemMaster.
     */
    @Test
    public void testGetUName() {
        System.out.println("getUName");
        String UOMCode = "";
        String expResult = "";
        String result = SERItemMaster.getUName(UOMCode);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveStock method, of class SERItemMaster.
     */
    @Test
    public void testSaveStock() {
        System.out.println("saveStock");
        OBJItemMaster obj = null;
        SERItemMaster.saveStock(obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
