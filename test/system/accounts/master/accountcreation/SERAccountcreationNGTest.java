/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.master.accountcreation;

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
public class SERAccountcreationNGTest {
    
    public SERAccountcreationNGTest() {
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
     * Test of save method, of class SERAccountcreation.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJAccountCreation obj = null;
        int Act = 0;
        SERAccountcreation.save(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERAccountcreation.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJAccountCreation expResult = null;
        OBJAccountCreation result = SERAccountcreation.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERAccountcreation.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERAccountcreation.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERAccountcreation.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERAccountcreation.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERAccountcreation.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJAccountCreation expResult = null;
        OBJAccountCreation result = SERAccountcreation.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERAccountcreation.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String l1 = "";
        String l2 = "";
        String l3 = "";
        String expResult = "";
        String result = SERAccountcreation.getID(l1, l2, l3);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCusgroup method, of class SERAccountcreation.
     */
    @Test
    public void testGetCusgroup() {
        System.out.println("getCusgroup");
        Vector expResult = null;
        Vector result = SERAccountcreation.getCusgroup();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class SERAccountcreation.
     */
    @Test
    public void testGetCurrency_0args() {
        System.out.println("getCurrency");
        Vector expResult = null;
        Vector result = SERAccountcreation.getCurrency();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SERAccountcreation.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String code = "";
        String tble = "";
        String col = "";
        String expResult = "";
        String result = SERAccountcreation.setName(code, tble, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSRep method, of class SERAccountcreation.
     */
    @Test
    public void testGetSRep_0args() {
        System.out.println("getSRep");
        Vector expResult = null;
        Vector result = SERAccountcreation.getSRep();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSRep method, of class SERAccountcreation.
     */
    @Test
    public void testGetSRep_String() {
        System.out.println("getSRep");
        String code = "";
        String expResult = "";
        String result = SERAccountcreation.getSRep(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getcusGroup method, of class SERAccountcreation.
     */
    @Test
    public void testGetcusGroup() {
        System.out.println("getcusGroup");
        String code = "";
        String expResult = "";
        String result = SERAccountcreation.getcusGroup(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class SERAccountcreation.
     */
    @Test
    public void testGetCurrency_String() {
        System.out.println("getCurrency");
        String code = "";
        String expResult = "";
        String result = SERAccountcreation.getCurrency(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadTbl method, of class SERAccountcreation.
     */
    @Test
    public void testLoadTbl() {
        System.out.println("loadTbl");
        ArrayList expResult = null;
        ArrayList result = SERAccountcreation.loadTbl();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
