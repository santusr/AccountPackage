/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.masters.currency;

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
public class OBJCurrencyNGTest {
    
    public OBJCurrencyNGTest() {
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
     * Test of getCode method, of class OBJCurrency.
     */
    @Test
    public void testGetCode() {
        System.out.println("getCode");
        OBJCurrency instance = null;
        String expResult = "";
        String result = instance.getCode();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCode method, of class OBJCurrency.
     */
    @Test
    public void testSetCode() {
        System.out.println("setCode");
        String code = "";
        OBJCurrency instance = null;
        instance.setCode(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFactore method, of class OBJCurrency.
     */
    @Test
    public void testGetFactore() {
        System.out.println("getFactore");
        OBJCurrency instance = null;
        String expResult = "";
        String result = instance.getFactore();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFactore method, of class OBJCurrency.
     */
    @Test
    public void testSetFactore() {
        System.out.println("setFactore");
        String factore = "";
        OBJCurrency instance = null;
        instance.setFactore(factore);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class OBJCurrency.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        OBJCurrency instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class OBJCurrency.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        OBJCurrency instance = null;
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
