/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.login;

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
public class OBJLoginNGTest {
    
    public OBJLoginNGTest() {
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
     * Test of getPass method, of class OBJLogin.
     */
    @Test
    public void testGetPass() {
        System.out.println("getPass");
        OBJLogin instance = null;
        String expResult = "";
        String result = instance.getPass();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPass method, of class OBJLogin.
     */
    @Test
    public void testSetPass() {
        System.out.println("setPass");
        String pass = "";
        OBJLogin instance = null;
        instance.setPass(pass);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUase method, of class OBJLogin.
     */
    @Test
    public void testGetUase() {
        System.out.println("getUase");
        OBJLogin instance = null;
        String expResult = "";
        String result = instance.getUase();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUase method, of class OBJLogin.
     */
    @Test
    public void testSetUase() {
        System.out.println("setUase");
        String uase = "";
        OBJLogin instance = null;
        instance.setUase(uase);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
