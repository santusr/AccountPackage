/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.master.itemclassification.itemcategory;

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
public class OBJItemCategoryNGTest {
    
    public OBJItemCategoryNGTest() {
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
     * Test of getCatname method, of class OBJItemCategory.
     */
    @Test
    public void testGetCatname() {
        System.out.println("getCatname");
        OBJItemCategory instance = null;
        String expResult = "";
        String result = instance.getCatname();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCatname method, of class OBJItemCategory.
     */
    @Test
    public void testSetCatname() {
        System.out.println("setCatname");
        String catname = "";
        OBJItemCategory instance = null;
        instance.setCatname(catname);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCode method, of class OBJItemCategory.
     */
    @Test
    public void testGetCode() {
        System.out.println("getCode");
        OBJItemCategory instance = null;
        String expResult = "";
        String result = instance.getCode();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCode method, of class OBJItemCategory.
     */
    @Test
    public void testSetCode() {
        System.out.println("setCode");
        String code = "";
        OBJItemCategory instance = null;
        instance.setCode(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class OBJItemCategory.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        OBJItemCategory instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class OBJItemCategory.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        OBJItemCategory instance = null;
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
