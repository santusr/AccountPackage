/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.master.store;

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
public class SERStoreNGTest {
    
    public SERStoreNGTest() {
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
     * Test of save method, of class SERStore.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJStore obj = null;
        int Act = 0;
        SERStore.save(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERStore.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJStore expResult = null;
        OBJStore result = SERStore.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERStore.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERStore.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERStore.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERStore.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERStore.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJStore expResult = null;
        OBJStore result = SERStore.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERStore.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERStore.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
