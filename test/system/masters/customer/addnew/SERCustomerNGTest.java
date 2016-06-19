/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.masters.customer.addnew;

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
public class SERCustomerNGTest {
    
    public SERCustomerNGTest() {
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
     * Test of save method, of class SERCustomer.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJCustomer obj = null;
        int Act = 0;
        SERCustomer.save(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERCustomer.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJCustomer expResult = null;
        OBJCustomer result = SERCustomer.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERCustomer.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERCustomer.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERCustomer.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERCustomer.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERCustomer.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJCustomer expResult = null;
        OBJCustomer result = SERCustomer.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERCustomer.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERCustomer.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCusgroup method, of class SERCustomer.
     */
    @Test
    public void testGetCusgroup() {
        System.out.println("getCusgroup");
        Vector expResult = null;
        Vector result = SERCustomer.getCusgroup();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class SERCustomer.
     */
    @Test
    public void testGetCurrency_0args() {
        System.out.println("getCurrency");
        Vector expResult = null;
        Vector result = SERCustomer.getCurrency();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SERCustomer.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String code = "";
        String tble = "";
        String col = "";
        String expResult = "";
        String result = SERCustomer.setName(code, tble, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSRep method, of class SERCustomer.
     */
    @Test
    public void testGetSRep_0args() {
        System.out.println("getSRep");
        Vector expResult = null;
        Vector result = SERCustomer.getSRep();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSRep method, of class SERCustomer.
     */
    @Test
    public void testGetSRep_String() {
        System.out.println("getSRep");
        String code = "";
        String expResult = "";
        String result = SERCustomer.getSRep(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getcusGroup method, of class SERCustomer.
     */
    @Test
    public void testGetcusGroup() {
        System.out.println("getcusGroup");
        String code = "";
        String expResult = "";
        String result = SERCustomer.getcusGroup(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class SERCustomer.
     */
    @Test
    public void testGetCurrency_String() {
        System.out.println("getCurrency");
        String code = "";
        String expResult = "";
        String result = SERCustomer.getCurrency(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
