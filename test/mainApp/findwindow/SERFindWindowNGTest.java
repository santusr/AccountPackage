/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainApp.findwindow;

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
public class SERFindWindowNGTest {
    
    public SERFindWindowNGTest() {
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
     * Test of getData method, of class SERFindWindow.
     */
    @Test
    public void testGetData_3args() {
        System.out.println("getData");
        String table = "";
        String code = "";
        String name = "";
        ArrayList expResult = null;
        ArrayList result = SERFindWindow.getData(table, code, name);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class SERFindWindow.
     */
    @Test
    public void testGetData_4args() {
        System.out.println("getData");
        String table = "";
        String code = "";
        String name = "";
        String s = "";
        ArrayList expResult = null;
        ArrayList result = SERFindWindow.getData(table, code, name, s);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class SERFindWindow.
     */
    @Test
    public void testGetData_5args() {
        System.out.println("getData");
        String table = "";
        String code = "";
        String name = "";
        String s = "";
        String ss = "";
        ArrayList expResult = null;
        ArrayList result = SERFindWindow.getData(table, code, name, s, ss);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
