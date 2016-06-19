/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.master.salesrep;

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
public class DAOSalesRepNGTest {
    
    public DAOSalesRepNGTest() {
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
     * Test of save method, of class DAOSalesRep.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJSalesRep obj = null;
        Connection conn = null;
        int Act = 0;
        DAOSalesRep.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class DAOSalesRep.
     */
    @Test
    public void testGetNavi() throws Exception {
        System.out.println("getNavi");
        Connection conn = null;
        int Ix = 0;
        OBJSalesRep expResult = null;
        OBJSalesRep result = DAOSalesRep.getNavi(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOSalesRep.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJSalesRep expResult = null;
        OBJSalesRep result = DAOSalesRep.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class DAOSalesRep.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Connection conn = null;
        int expResult = 0;
        int result = DAOSalesRep.getValue(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAOSalesRep.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Connection conn = null;
        String code = "";
        DAOSalesRep.delete(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOSalesRep.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOSalesRep.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genName method, of class DAOSalesRep.
     */
    @Test
    public void testGenName() throws Exception {
        System.out.println("genName");
        Connection conn = null;
        String tble = "";
        String code = "";
        String col = "";
        String expResult = "";
        String result = DAOSalesRep.genName(conn, tble, code, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
