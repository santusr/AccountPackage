/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.paymentvoucher.againstinvoice;

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
public class DAOInvoListNGTest {
    
    public DAOInvoListNGTest() {
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
     * Test of getList method, of class DAOInvoList.
     */
    @Test
    public void testGetList() throws Exception {
        System.out.println("getList");
        String Cust = "";
        Connection con = null;
        ArrayList expResult = null;
        ArrayList result = DAOInvoList.getList(Cust, con);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstall method, of class DAOInvoList.
     */
    @Test
    public void testGetInstall() throws Exception {
        System.out.println("getInstall");
        String invoNo = "";
        Connection con = null;
        OBJInstallPay expResult = null;
        OBJInstallPay result = DAOInvoList.getInstall(invoNo, con);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of UpdateCr method, of class DAOInvoList.
     */
    @Test
    public void testUpdateCr() throws Exception {
        System.out.println("UpdateCr");
        OBJInstallPay objIP = null;
        Connection conn = null;
        DAOInvoList.UpdateCr(objIP, conn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
