/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.master.itemclassification.itemgroup;

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
public class OBJItemGroupNGTest {
    
    public OBJItemGroupNGTest() {
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
     * Test of getCatname method, of class OBJItemGroup.
     */
    @Test
    public void testGetCatname() {
        System.out.println("getCatname");
        OBJItemGroup instance = null;
        String expResult = "";
        String result = instance.getCatname();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCatname method, of class OBJItemGroup.
     */
    @Test
    public void testSetCatname() {
        System.out.println("setCatname");
        String catname = "";
        OBJItemGroup instance = null;
        instance.setCatname(catname);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCode method, of class OBJItemGroup.
     */
    @Test
    public void testGetCode() {
        System.out.println("getCode");
        OBJItemGroup instance = null;
        String expResult = "";
        String result = instance.getCode();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCode method, of class OBJItemGroup.
     */
    @Test
    public void testSetCode() {
        System.out.println("setCode");
        String code = "";
        OBJItemGroup instance = null;
        instance.setCode(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class OBJItemGroup.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        OBJItemGroup instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class OBJItemGroup.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        OBJItemGroup instance = null;
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
