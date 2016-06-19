/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.masters.currency;

import java.sql.Connection;
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
public class DAOCurrencyNGTest {
    
    public DAOCurrencyNGTest() {
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
     * Test of save method, of class DAOCurrency.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJCurrency currency = null;
        Connection conn = null;
        int Act = 0;
        DAOCurrency.save(currency, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class DAOCurrency.
     */
    @Test
    public void testGetCurrency_Connection_int() throws Exception {
        System.out.println("getCurrency");
        Connection conn = null;
        int Ix = 0;
        OBJCurrency expResult = null;
        OBJCurrency result = DAOCurrency.getCurrency(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serchCurrency method, of class DAOCurrency.
     */
    @Test
    public void testSerchCurrency() throws Exception {
        System.out.println("serchCurrency");
        Connection conn = null;
        String code = "";
        OBJCurrency expResult = null;
        OBJCurrency result = DAOCurrency.serchCurrency(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class DAOCurrency.
     */
    @Test
    public void testGetCurrency_Connection() throws Exception {
        System.out.println("getCurrency");
        Connection conn = null;
        int expResult = 0;
        int result = DAOCurrency.getCurrency(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCurrency method, of class DAOCurrency.
     */
    @Test
    public void testDeleteCurrency() throws Exception {
        System.out.println("deleteCurrency");
        Connection conn = null;
        String code = "";
        DAOCurrency.deleteCurrency(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOCurrency.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOCurrency.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
