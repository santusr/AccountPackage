/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.masters.customer.addnew;

import java.sql.Connection;
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
public class DAOCustomerNGTest {
    
    public DAOCustomerNGTest() {
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
     * Test of save method, of class DAOCustomer.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJCustomer obj = null;
        Connection conn = null;
        int Act = 0;
        DAOCustomer.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class DAOCustomer.
     */
    @Test
    public void testGetNavi() throws Exception {
        System.out.println("getNavi");
        Connection conn = null;
        int Ix = 0;
        OBJCustomer expResult = null;
        OBJCustomer result = DAOCustomer.getNavi(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOCustomer.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJCustomer expResult = null;
        OBJCustomer result = DAOCustomer.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class DAOCustomer.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Connection conn = null;
        int expResult = 0;
        int result = DAOCustomer.getValue(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAOCustomer.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Connection conn = null;
        String code = "";
        DAOCustomer.delete(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOCustomer.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOCustomer.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCusgroup method, of class DAOCustomer.
     */
    @Test
    public void testGetCusgroup() throws Exception {
        System.out.println("getCusgroup");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOCustomer.getCusgroup(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class DAOCustomer.
     */
    @Test
    public void testGetCurrency_Connection() throws Exception {
        System.out.println("getCurrency");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOCustomer.getCurrency(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genName method, of class DAOCustomer.
     */
    @Test
    public void testGenName() throws Exception {
        System.out.println("genName");
        Connection conn = null;
        String tble = "";
        String code = "";
        String col = "";
        String expResult = "";
        String result = DAOCustomer.genName(conn, tble, code, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSrep method, of class DAOCustomer.
     */
    @Test
    public void testGetSrep_Connection() throws Exception {
        System.out.println("getSrep");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOCustomer.getSrep(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSrep method, of class DAOCustomer.
     */
    @Test
    public void testGetSrep_Connection_String() throws Exception {
        System.out.println("getSrep");
        Connection conn = null;
        String code = "";
        String expResult = "";
        String result = DAOCustomer.getSrep(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getcusGroup method, of class DAOCustomer.
     */
    @Test
    public void testGetcusGroup() throws Exception {
        System.out.println("getcusGroup");
        Connection conn = null;
        String code = "";
        String expResult = "";
        String result = DAOCustomer.getcusGroup(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class DAOCustomer.
     */
    @Test
    public void testGetCurrency_Connection_String() throws Exception {
        System.out.println("getCurrency");
        Connection conn = null;
        String code = "";
        String expResult = "";
        String result = DAOCustomer.getCurrency(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
