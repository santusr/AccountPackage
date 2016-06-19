/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.master.accountcreation;

import java.sql.Connection;
import java.util.ArrayList;
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
public class DAOAccountcreationNGTest {
    
    public DAOAccountcreationNGTest() {
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
     * Test of save method, of class DAOAccountcreation.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJAccountCreation obj = null;
        Connection conn = null;
        int Act = 0;
        DAOAccountcreation.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class DAOAccountcreation.
     */
    @Test
    public void testGetNavi() throws Exception {
        System.out.println("getNavi");
        Connection conn = null;
        int Ix = 0;
        OBJAccountCreation expResult = null;
        OBJAccountCreation result = DAOAccountcreation.getNavi(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOAccountcreation.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJAccountCreation expResult = null;
        OBJAccountCreation result = DAOAccountcreation.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class DAOAccountcreation.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Connection conn = null;
        int expResult = 0;
        int result = DAOAccountcreation.getValue(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAOAccountcreation.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Connection conn = null;
        String code = "";
        DAOAccountcreation.delete(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOAccountcreation.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String l1 = "";
        String l2 = "";
        String l3 = "";
        String expResult = "";
        String result = DAOAccountcreation.genID(conn, l1, l2, l3);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCusgroup method, of class DAOAccountcreation.
     */
    @Test
    public void testGetCusgroup() throws Exception {
        System.out.println("getCusgroup");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOAccountcreation.getCusgroup(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class DAOAccountcreation.
     */
    @Test
    public void testGetCurrency_Connection() throws Exception {
        System.out.println("getCurrency");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOAccountcreation.getCurrency(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genName method, of class DAOAccountcreation.
     */
    @Test
    public void testGenName() throws Exception {
        System.out.println("genName");
        Connection conn = null;
        String tble = "";
        String code = "";
        String col = "";
        String expResult = "";
        String result = DAOAccountcreation.genName(conn, tble, code, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSrep method, of class DAOAccountcreation.
     */
    @Test
    public void testGetSrep_Connection() throws Exception {
        System.out.println("getSrep");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOAccountcreation.getSrep(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSrep method, of class DAOAccountcreation.
     */
    @Test
    public void testGetSrep_Connection_String() throws Exception {
        System.out.println("getSrep");
        Connection conn = null;
        String code = "";
        String expResult = "";
        String result = DAOAccountcreation.getSrep(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getcusGroup method, of class DAOAccountcreation.
     */
    @Test
    public void testGetcusGroup() throws Exception {
        System.out.println("getcusGroup");
        Connection conn = null;
        String code = "";
        String expResult = "";
        String result = DAOAccountcreation.getcusGroup(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class DAOAccountcreation.
     */
    @Test
    public void testGetCurrency_Connection_String() throws Exception {
        System.out.println("getCurrency");
        Connection conn = null;
        String code = "";
        String expResult = "";
        String result = DAOAccountcreation.getCurrency(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadTbl method, of class DAOAccountcreation.
     */
    @Test
    public void testLoadTbl() throws Exception {
        System.out.println("loadTbl");
        Connection conn = null;
        ArrayList expResult = null;
        ArrayList result = DAOAccountcreation.loadTbl(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
