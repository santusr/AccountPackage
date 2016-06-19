/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.receiptvoucher.againstinvoice;

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
public class DAOVouchNGTest {
    
    public DAOVouchNGTest() {
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
     * Test of doSave method, of class DAOVouch.
     */
    @Test
    public void testDoSave() throws Exception {
        System.out.println("doSave");
        OBJAgInvo obj = null;
        Connection conn = null;
        int Act = 0;
        DAOVouch.doSave(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVouch method, of class DAOVouch.
     */
    @Test
    public void testGetVouch() throws Exception {
        System.out.println("getVouch");
        Connection conn = null;
        int Ix = 0;
        OBJAgInvo expResult = null;
        OBJAgInvo result = DAOVouch.getVouch(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOVouch.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJAgInvo expResult = null;
        OBJAgInvo result = DAOVouch.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class DAOVouch.
     */
    @Test
    public void testGetIndex() throws Exception {
        System.out.println("getIndex");
        Connection conn = null;
        int expResult = 0;
        int result = DAOVouch.getIndex(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteVouch method, of class DAOVouch.
     */
    @Test
    public void testDeleteVouch() throws Exception {
        System.out.println("deleteVouch");
        Connection conn = null;
        String code = "";
        DAOVouch.deleteVouch(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOVouch.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOVouch.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
