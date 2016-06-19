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
public class SERCurrencyNGTest {
    
    public SERCurrencyNGTest() {
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
     * Test of save method, of class SERCurrency.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJCurrency currency = null;
        int Act = 0;
        SERCurrency.save(currency, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class SERCurrency.
     */
    @Test
    public void testGetCurrency() {
        System.out.println("getCurrency");
        int Index = 0;
        OBJCurrency expResult = null;
        OBJCurrency result = SERCurrency.getCurrency(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERCurrency.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERCurrency.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERCurrency.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERCurrency.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serchCurrency method, of class SERCurrency.
     */
    @Test
    public void testSerchCurrency() {
        System.out.println("serchCurrency");
        String code = "";
        OBJCurrency expResult = null;
        OBJCurrency result = SERCurrency.serchCurrency(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERCurrency.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERCurrency.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
