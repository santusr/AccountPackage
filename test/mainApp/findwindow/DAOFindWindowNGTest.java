/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainApp.findwindow;

import java.sql.Connection;
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
public class DAOFindWindowNGTest {
    
    public DAOFindWindowNGTest() {
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
     * Test of getData method, of class DAOFindWindow.
     */
    @Test
    public void testGetData_4args() throws Exception {
        System.out.println("getData");
        Connection conn = null;
        String table = "";
        String code = "";
        String name = "";
        ArrayList expResult = null;
        ArrayList result = DAOFindWindow.getData(conn, table, code, name);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class DAOFindWindow.
     */
    @Test
    public void testGetData_5args() throws Exception {
        System.out.println("getData");
        Connection conn = null;
        String table = "";
        String code = "";
        String name = "";
        String s = "";
        ArrayList expResult = null;
        ArrayList result = DAOFindWindow.getData(conn, table, code, name, s);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class DAOFindWindow.
     */
    @Test
    public void testGetData_6args() throws Exception {
        System.out.println("getData");
        Connection conn = null;
        String table = "";
        String code = "";
        String name = "";
        String s = "";
        String ss = "";
        ArrayList expResult = null;
        ArrayList result = DAOFindWindow.getData(conn, table, code, name, s, ss);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
