/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.cheque;

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
public class SERChequeNGTest {
    
    public SERChequeNGTest() {
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
     * Test of save method, of class SERCheque.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        OBJCheque obj = null;
        int Act = 0;
        SERCheque.save(obj, Act);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNavi method, of class SERCheque.
     */
    @Test
    public void testGetNavi() {
        System.out.println("getNavi");
        int Index = 0;
        OBJCheque expResult = null;
        OBJCheque result = SERCheque.getNavi(Index);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class SERCheque.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        int expResult = 0;
        int result = SERCheque.getIndex();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SERCheque.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String code = "";
        SERCheque.delete(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serch method, of class SERCheque.
     */
    @Test
    public void testSerch() {
        System.out.println("serch");
        String code = "";
        OBJCheque expResult = null;
        OBJCheque result = SERCheque.serch(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of serchCNo method, of class SERCheque.
     */
    @Test
    public void testSerchCNo() {
        System.out.println("serchCNo");
        String cno = "";
        OBJCheque expResult = null;
        OBJCheque result = SERCheque.serchCNo(cno);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class SERCheque.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        String expResult = "";
        String result = SERCheque.getID();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class SERCheque.
     */
    @Test
    public void testGetCurrency_0args() {
        System.out.println("getCurrency");
        Vector expResult = null;
        Vector result = SERCheque.getCurrency();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SERCheque.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String code = "";
        String tble = "";
        String col = "";
        String expResult = "";
        String result = SERCheque.setName(code, tble, col);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSRep method, of class SERCheque.
     */
    @Test
    public void testGetSRep_0args() {
        System.out.println("getSRep");
        Vector expResult = null;
        Vector result = SERCheque.getSRep();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSRep method, of class SERCheque.
     */
    @Test
    public void testGetSRep_String() {
        System.out.println("getSRep");
        String code = "";
        String expResult = "";
        String result = SERCheque.getSRep(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class SERCheque.
     */
    @Test
    public void testGetCurrency_String() {
        System.out.println("getCurrency");
        String code = "";
        String expResult = "";
        String result = SERCheque.getCurrency(code);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFCRate method, of class SERCheque.
     */
    @Test
    public void testGetFCRate() {
        System.out.println("getFCRate");
        String cc = "";
        double expResult = 0.0;
        double result = SERCheque.getFCRate(cc);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
