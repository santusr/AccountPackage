/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.masters.customer.search;

import java.util.ArrayList;
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
public class SERSearchCustomerNGTest {
    
    public SERSearchCustomerNGTest() {
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
     * Test of getContent method, of class SERSearchCustomer.
     */
    @Test
    public void testGetContent_String() {
        System.out.println("getContent");
        String r = "";
        ArrayList expResult = null;
        ArrayList result = SERSearchCustomer.getContent(r);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContent method, of class SERSearchCustomer.
     */
    @Test
    public void testGetContent_String_String() {
        System.out.println("getContent");
        String r = "";
        String s = "";
        ArrayList expResult = null;
        ArrayList result = SERSearchCustomer.getContent(r, s);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
