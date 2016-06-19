/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.sales.paymentplan;

import java.util.ArrayList;
import java.util.Calendar;
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
public class SERInvPayPlanNGTest {
    
    public SERInvPayPlanNGTest() {
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
     * Test of save method, of class SERInvPayPlan.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJInvPayPlan currency = null;
        int Act = 0;
        SERInvPayPlan.save(currency, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveGRN method, of class SERInvPayPlan.
     */
    @Test
    public void testSaveGRN() {
        System.out.println("saveGRN");
        OBJInvPayPlan obj = null;
        int Act = 0;
        SERInvPayPlan.saveGRN(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERInvPayPlan.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJInvPayPlan expResult = null;
        OBJInvPayPlan result = SERInvPayPlan.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERInvPayPlan.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERInvPayPlan.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERInvPayPlan.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERInvPayPlan.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERInvPayPlan.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJInvPayPlan expResult = null;
        OBJInvPayPlan result = SERInvPayPlan.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serchPP method, of class SERInvPayPlan.
     */
    @Test
    public void testSerchPP() {
        System.out.println("serchPP");
        String code = "";
        OBJInvPayPlan expResult = null;
        OBJInvPayPlan result = SERInvPayPlan.serchPP(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERInvPayPlan.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERInvPayPlan.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPPDetails method, of class SERInvPayPlan.
     */
    @Test
    public void testGetPPDetails() {
        System.out.println("getPPDetails");
        String code = "";
        OBJInvPayPlan expResult = null;
        OBJInvPayPlan result = SERInvPayPlan.getPPDetails(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSchedule method, of class SERInvPayPlan.
     */
    @Test
    public void testGetSchedule() {
        System.out.println("getSchedule");
        Calendar paydate = null;
        String noins = "";
        String amo = "";
        ArrayList expResult = null;
        ArrayList result = SERInvPayPlan.getSchedule(paydate, noins, amo);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveSched method, of class SERInvPayPlan.
     */
    @Test
    public void testSaveSched() {
        System.out.println("saveSched");
        ArrayList<OBJPaySchedule> objs = null;
        int Act = 0;
        SERInvPayPlan.saveSched(objs, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateSchedule method, of class SERInvPayPlan.
     */
    @Test
    public void testUpdateSchedule() {
        System.out.println("updateSchedule");
        OBJAgInvo objin = null;
        SERInvPayPlan.updateSchedule(objin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadSchedule method, of class SERInvPayPlan.
     */
    @Test
    public void testLoadSchedule() {
        System.out.println("loadSchedule");
        String cid = "";
        ArrayList expResult = null;
        ArrayList result = SERInvPayPlan.loadSchedule(cid);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
