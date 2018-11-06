

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Smadi
 */
public class LogicTest {
    
    public LogicTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findMe1 method, of class Logic.
     */
    @Test
    public void testFindMe1() {
        System.out.println("findMe1");
        Logic instance = new Logic();
        boolean expResult = true;
        boolean result = instance.findMe1();
        assertEquals(expResult, result);
    }

    /**
     * Test of findMe2 method, of class Logic.
     */
    @Test
    public void testFindMe2() {
        System.out.println("findMe2");
        Logic instance = new Logic();
        int expResult = 2;
        int result = instance.findMe2();
        assertEquals(expResult, result);
    }

    /**
     * Test of findMe3 method, of class Logic.
     */
    @Test
    public void testFindMe3() {
        System.out.println("findMe3");
        Logic instance = new Logic();
        boolean expResult = true;
        boolean result = instance.findMe3();
        assertEquals(expResult, result);
    }

    /**
     * Test of findMe4 method, of class Logic.
     */
    @Test
    public void testFindMe4() {
        System.out.println("findMe4");
        Logic instance = new Logic();
        boolean expResult = true;
        boolean result = instance.findMe4();
        assertEquals(expResult, result);
    }

    /**
     * Test of findMeWithException method, of class Logic.
     */
    @Test
    public void testFindMeWithException() {
        System.out.println("findMeWithException");
        boolean check = false;
        Logic instance = new Logic();
        boolean expResult = false;
        boolean result = instance.findMeWithException(check);
        assertEquals(expResult, result);
    }

    /**
     * Test of dontFindMe1 method, of class Logic.
     */
    @Test
    public void testDontFindMe1() {
        System.out.println("dontFindMe1");
        Logic instance = new Logic();
        boolean expResult = true;
        boolean result = instance.dontFindMe1();
        assertEquals(expResult, result);
    }

    /**
     * Test of dontFindMe2 method, of class Logic.
     */
    @Test
    public void testDontFindMe2() {
        System.out.println("dontFindMe2");
        Logic instance = new Logic();
        boolean expResult = true;
        boolean result = instance.dontFindMe2();
        assertEquals(expResult, result);
    }
    
}
