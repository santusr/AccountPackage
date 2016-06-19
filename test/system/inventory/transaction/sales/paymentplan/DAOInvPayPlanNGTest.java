/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.sales.paymentplan;

import java.sql.Connection;
import java.util.ArrayList;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import system.accounts.transaction.receiptvoucher.againstinvoice.OBJAgInvo;

/**
 *
 * @author Administrator
 */
public class DAOInvPayPlanNGTest {
    
    public DAOInvPayPlanNGTest() {
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
     * Test of save method, of class DAOInvPayPlan.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        OBJInvPayPlan obj = null;
        Connection conn = null;
        int Act = 0;
        DAOInvPayPlan.save(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveGRN method, of class DAOInvPayPlan.
     */
    @Test
    public void testSaveGRN() throws Exception {
        System.out.println("saveGRN");
        OBJInvPayPlan obj = null;
        Connection conn = null;
        int Act = 0;
        DAOInvPayPlan.saveGRN(obj, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveSched method, of class DAOInvPayPlan.
     */
    @Test
    public void testSaveSched() throws Exception {
        System.out.println("saveSched");
        ArrayList<OBJPaySchedule> objs = null;
        Connection conn = null;
        int Act = 0;
        DAOInvPayPlan.saveSched(objs, conn, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateSchedule method, of class DAOInvPayPlan.
     */
    @Test
    public void testUpdateSchedule() throws Exception {
        System.out.println("updateSchedule");
        OBJAgInvo obj = null;
        Connection conn = null;
        DAOInvPayPlan.updateSchedule(obj, conn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class DAOInvPayPlan.
     */
    @Test
    public void testGetCurrency() throws Exception {
        System.out.println("getCurrency");
        Connection conn = null;
        int Ix = 0;
        OBJInvPayPlan expResult = null;
        OBJInvPayPlan result = DAOInvPayPlan.getCurrency(conn, Ix);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class DAOInvPayPlan.
     */
    @Test
    public void testSerch() throws Exception {
        System.out.println("serch");
        Connection conn = null;
        String code = "";
        OBJInvPayPlan expResult = null;
        OBJInvPayPlan result = DAOInvPayPlan.serch(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serchPP method, of class DAOInvPayPlan.
     */
    @Test
    public void testSerchPP() throws Exception {
        System.out.println("serchPP");
        Connection conn = null;
        String code = "";
        OBJInvPayPlan expResult = null;
        OBJInvPayPlan result = DAOInvPayPlan.serchPP(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class DAOInvPayPlan.
     */
    @Test
    public void testGetIndex() throws Exception {
        System.out.println("getIndex");
        Connection conn = null;
        int expResult = 0;
        int result = DAOInvPayPlan.getIndex(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deletePayPlan method, of class DAOInvPayPlan.
     */
    @Test
    public void testDeletePayPlan() throws Exception {
        System.out.println("deletePayPlan");
        Connection conn = null;
        String code = "";
        DAOInvPayPlan.deletePayPlan(conn, code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genID method, of class DAOInvPayPlan.
     */
    @Test
    public void testGenID() throws Exception {
        System.out.println("genID");
        Connection conn = null;
        String expResult = "";
        String result = DAOInvPayPlan.genID(conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPPDetails method, of class DAOInvPayPlan.
     */
    @Test
    public void testGetPPDetails() throws Exception {
        System.out.println("getPPDetails");
        Connection conn = null;
        String code = "";
        OBJInvPayPlan expResult = null;
        OBJInvPayPlan result = DAOInvPayPlan.getPPDetails(conn, code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadSchedule method, of class DAOInvPayPlan.
     */
    @Test
    public void testLoadSchedule() throws Exception {
        System.out.println("loadSchedule");
        String cid = "";
        Connection conn = null;
        ArrayList expResult = null;
        ArrayList result = DAOInvPayPlan.loadSchedule(cid, conn);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
