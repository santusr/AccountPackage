/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.File;
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
public class PrintLogNGTest {
    
    public PrintLogNGTest() {
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
     * Test of out method, of class PrintLog.
     */
    @Test
    public void testOut() {
        System.out.println("out");
        Throwable er = null;
        PrintLog.out(er);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomStackTrace method, of class PrintLog.
     */
    @Test
    public void testGetCustomStackTrace() {
        System.out.println("getCustomStackTrace");
        Throwable aThrowable = null;
        String expResult = "";
        String result = PrintLog.getCustomStackTrace(aThrowable);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class PrintLog.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        PrintLog.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDir method, of class PrintLog.
     */
    @Test
    public void testDeleteDir() {
        System.out.println("deleteDir");
        File dir = null;
        boolean expResult = false;
        boolean result = PrintLog.deleteDir(dir);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
