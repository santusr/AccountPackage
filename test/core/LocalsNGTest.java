/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.util.Date;
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
public class LocalsNGTest {
    
    public LocalsNGTest() {
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
     * Test of passwordGen method, of class Locals.
     */
    @Test
    public void testPasswordGen() throws Exception {
        System.out.println("passwordGen");
        String text = "";
        String expResult = "";
        String result = Locals.passwordGen(text);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of currentDate_F1 method, of class Locals.
     */
    @Test
    public void testCurrentDate_F1() {
        System.out.println("currentDate_F1");
        String expResult = "";
        String result = Locals.currentDate_F1();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of currentDate_F2 method, of class Locals.
     */
    @Test
    public void testCurrentDate_F2() {
        System.out.println("currentDate_F2");
        String expResult = "";
        String result = Locals.currentDate_F2();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDateFormat method, of class Locals.
     */
    @Test
    public void testSetDateFormat() {
        System.out.println("setDateFormat");
        Date date = null;
        String expResult = "";
        String result = Locals.setDateFormat(date);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of currencyFormat method, of class Locals.
     */
    @Test
    public void testCurrencyFormat() {
        System.out.println("currencyFormat");
        double d = 0.0;
        String expResult = "";
        String result = Locals.currencyFormat(d);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sCurrencyFormat method, of class Locals.
     */
    @Test
    public void testSCurrencyFormat() {
        System.out.println("sCurrencyFormat");
        String d = "";
        String expResult = "";
        String result = Locals.sCurrencyFormat(d);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toDate method, of class Locals.
     */
    @Test
    public void testToDate() {
        System.out.println("toDate");
        String IDate = "";
        Date expResult = null;
        Date result = Locals.toDate(IDate);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ignoCase method, of class Locals.
     */
    @Test
    public void testIgnoCase() {
        System.out.println("ignoCase");
        double d = 0.0;
        Double expResult = null;
        Double result = Locals.ignoCase(d);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
