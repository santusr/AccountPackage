/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.masters.Deponent;

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
public class SERDeponentNGTest {
    
    public SERDeponentNGTest() {
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
     * Test of save method, of class SERDeponent.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJDeponent obj = null;
        int Act = 0;
        SERDeponent.save(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERDeponent.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJDeponent expResult = null;
        OBJDeponent result = SERDeponent.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERDeponent.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERDeponent.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERDeponent.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERDeponent.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERDeponent.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJDeponent expResult = null;
        OBJDeponent result = SERDeponent.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERDeponent.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERDeponent.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SERDeponent.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String code = "";
        String tble = "";
        String col = "";
        String expResult = "";
        String result = SERDeponent.setName(code, tble, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
