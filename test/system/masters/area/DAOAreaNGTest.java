/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.masters.area;

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
public class DAOAreaNGTest {
    
    public DAOAreaNGTest() {
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
     * Test of save method, of class DAOArea.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJArea area = null;
        Connection conn = null;
        int Act = 0;
        DAOArea.save(area, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArea method, of class DAOArea.
     */
    @Test
    public void testGetArea_Connection_int() throws Exception {
        System.out.println("getArea");
        Connection conn = null;
        int Ix = 0;
        OBJArea expResult = null;
        OBJArea result = DAOArea.getArea(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serchArea method, of class DAOArea.
     */
    @Test
    public void testSerchArea() throws Exception {
        System.out.println("serchArea");
        Connection conn = null;
        String code = "";
        OBJArea expResult = null;
        OBJArea result = DAOArea.serchArea(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArea method, of class DAOArea.
     */
    @Test
    public void testGetArea_Connection() throws Exception {
        System.out.println("getArea");
        Connection conn = null;
        int expResult = 0;
        int result = DAOArea.getArea(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteArea method, of class DAOArea.
     */
    @Test
    public void testDeleteArea() throws Exception {
        System.out.println("deleteArea");
        Connection conn = null;
        String code = "";
        DAOArea.deleteArea(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOArea.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOArea.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
