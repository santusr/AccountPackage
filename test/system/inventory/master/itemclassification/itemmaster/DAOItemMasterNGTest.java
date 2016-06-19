/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.master.itemclassification.itemmaster;

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
public class DAOItemMasterNGTest {
    
    public DAOItemMasterNGTest() {
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
     * Test of save method, of class DAOItemMaster.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJItemMaster obj = null;
        Connection conn = null;
        int Act = 0;
        DAOItemMaster.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class DAOItemMaster.
     */
    @Test
    public void testGetNavi() throws Exception {
        System.out.println("getNavi");
        Connection conn = null;
        int Ix = 0;
        OBJItemMaster expResult = null;
        OBJItemMaster result = DAOItemMaster.getNavi(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOItemMaster.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJItemMaster expResult = null;
        OBJItemMaster result = DAOItemMaster.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class DAOItemMaster.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Connection conn = null;
        int expResult = 0;
        int result = DAOItemMaster.getValue(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAOItemMaster.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Connection conn = null;
        String code = "";
        DAOItemMaster.delete(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOItemMaster.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOItemMaster.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genName method, of class DAOItemMaster.
     */
    @Test
    public void testGenName() throws Exception {
        System.out.println("genName");
        Connection conn = null;
        String tble = "";
        String code = "";
        String col = "";
        String expResult = "";
        String result = DAOItemMaster.genName(conn, tble, code, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnit method, of class DAOItemMaster.
     */
    @Test
    public void testGetUnit() throws Exception {
        System.out.println("getUnit");
        Connection conn = null;
        Vector expResult = null;
        Vector result = DAOItemMaster.getUnit(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnitName method, of class DAOItemMaster.
     */
    @Test
    public void testGetUnitName() throws Exception {
        System.out.println("getUnitName");
        Connection conn = null;
        String UOMCode = "";
        String expResult = "";
        String result = DAOItemMaster.getUnitName(conn, UOMCode);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveStock method, of class DAOItemMaster.
     */
    @Test
    public void testSaveStock() throws Exception {
        System.out.println("saveStock");
        Connection conn = null;
        OBJItemMaster obj = null;
        DAOItemMaster.saveStock(conn, obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
