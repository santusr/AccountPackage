/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.cheque;

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
public class DAOChequeNGTest {
    
    public DAOChequeNGTest() {
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
     * Test of save method, of class DAOCheque.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJCheque obj = null;
        Connection conn = null;
        int Act = 0;
        DAOCheque.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class DAOCheque.
     */
    @Test
    public void testGetNavi() throws Exception {
        System.out.println("getNavi");
        Connection conn = null;
        int Ix = 0;
        OBJCheque expResult = null;
        OBJCheque result = DAOCheque.getNavi(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOCheque.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJCheque expResult = null;
        OBJCheque result = DAOCheque.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serchCNo method, of class DAOCheque.
     */
    @Test
    public void testSerchCNo() throws Exception {
        System.out.println("serchCNo");
        Connection conn = null;
        String code = "";
        OBJCheque expResult = null;
        OBJCheque result = DAOCheque.serchCNo(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class DAOCheque.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Connection conn = null;
        int expResult = 0;
        int result = DAOCheque.getValue(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAOCheque.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Connection conn = null;
        String code = "";
        DAOCheque.delete(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOCheque.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOCheque.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class DAOCheque.
     */
    @Test
    public void testGetCurrency_Connection() throws Exception {
        System.out.println("getCurrency");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOCheque.getCurrency(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genName method, of class DAOCheque.
     */
    @Test
    public void testGenName() throws Exception {
        System.out.println("genName");
        Connection conn = null;
        String tble = "";
        String code = "";
        String col = "";
        String expResult = "";
        String result = DAOCheque.genName(conn, tble, code, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSrep method, of class DAOCheque.
     */
    @Test
    public void testGetSrep_Connection() throws Exception {
        System.out.println("getSrep");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOCheque.getSrep(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSrep method, of class DAOCheque.
     */
    @Test
    public void testGetSrep_Connection_String() throws Exception {
        System.out.println("getSrep");
        Connection conn = null;
        String code = "";
        String expResult = "";
        String result = DAOCheque.getSrep(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getcusGroup method, of class DAOCheque.
     */
    @Test
    public void testGetcusGroup() throws Exception {
        System.out.println("getcusGroup");
        Connection conn = null;
        String code = "";
        String expResult = "";
        String result = DAOCheque.getcusGroup(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class DAOCheque.
     */
    @Test
    public void testGetCurrency_Connection_String() throws Exception {
        System.out.println("getCurrency");
        Connection conn = null;
        String code = "";
        String expResult = "";
        String result = DAOCheque.getCurrency(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gatFCRate method, of class DAOCheque.
     */
    @Test
    public void testGatFCRate() throws Exception {
        System.out.println("gatFCRate");
        Connection con = null;
        String cc = "";
        double expResult = 0.0;
        double result = DAOCheque.gatFCRate(con, cc);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
