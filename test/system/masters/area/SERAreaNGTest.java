/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.masters.area;

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
public class SERAreaNGTest {
    
    public SERAreaNGTest() {
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
     * Test of save method, of class SERArea.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJArea area = null;
        int Act = 0;
        SERArea.save(area, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArea method, of class SERArea.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int Index = 0;
        OBJArea expResult = null;
        OBJArea result = SERArea.getArea(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERArea.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERArea.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERArea.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERArea.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serchArea method, of class SERArea.
     */
    @Test
    public void testSerchArea() {
        System.out.println("serchArea");
        String code = "";
        OBJArea expResult = null;
        OBJArea result = SERArea.serchArea(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERArea.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERArea.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
