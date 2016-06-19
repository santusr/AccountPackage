/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainApp;

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
public class MainFrameNGTest {
    
    public MainFrameNGTest() {
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
     * Test of loadNewCustomer method, of class MainFrame.
     */
    @Test
    public void testLoadNewCustomer() {
        System.out.println("loadNewCustomer");
        MainFrame instance = new MainFrame();
        instance.loadNewCustomer();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCustomer method, of class MainFrame.
     */
    @Test
    public void testLoadCustomer() {
        System.out.println("loadCustomer");
        String s = "";
        MainFrame instance = new MainFrame();
        instance.loadCustomer(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
