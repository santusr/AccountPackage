/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.paymentvoucher.againstinvoice;

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
public class SERInvoListNGTest {
    
    public SERInvoListNGTest() {
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
     * Test of getList method, of class SERInvoList.
     */
    @Test
    public void testGetList() {
        System.out.println("getList");
        String Cust = "";
        ArrayList expResult = null;
        ArrayList result = SERInvoList.getList(Cust);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstall method, of class SERInvoList.
     */
    @Test
    public void testGetInstall() {
        System.out.println("getInstall");
        String invoNo = "";
        OBJInstallPay expResult = null;
        //OBJInstallPay result = SERInvoList.getInstall(invoNo);
        //assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of UpdateIP method, of class SERInvoList.
     */
    @Test
    public void testUpdateIP() {
        System.out.println("UpdateIP");
        OBJInstallPay objIP = null;
        SERInvoList.UpdateIP(objIP);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
