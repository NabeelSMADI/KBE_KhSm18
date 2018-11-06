package de.htw.ai.kbe.runmerunner;


import de.htw.ai.kbe.runmerunner.Main;
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
public class MainTest {

    public MainTest() {
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
     * Test of checkArgs method, of class Main. "-c className -o outPut.txt"
     */
    @Test
    public void testCheckArgs01() {
        System.out.println("Test of checkArgs method, of class Main. \"-c className -o outPut.txt\"");
        String testArgs = "-c Logic -o outPut.txt";
        String[] args = testArgs.split(" ");
        Main instance = new Main();
        String expResult = "True";
        String result = instance.checkArgs(args);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkArgs method, of class Main. "-c"
     */
    @Test
    public void testCheckArgs02() {
        System.out.println("Test of checkArgs method, of class Main. \"-c\"");
        String testArgs = "-c";
        String[] args = testArgs.split(" ");
        Main instance = new Main();
        String expResult = "Missing argument for option: c";
        String result = instance.checkArgs(args);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkArgs method, of class Main. ""
     */
    @Test
    public void testCheckArgs03() {
        System.out.println("Test of checkArgs method, of class Main. \"\"");
        String testArgs = "";
        String[] args = testArgs.split(" ");
        Main instance = new Main();
        String expResult = "Missing required option: c";
        String result = instance.checkArgs(args);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkArgs method, of class Main. "-c className -o"
     */
    @Test
    public void testCheckArgs04() {
        System.out.println("Test of checkArgs method, of class Main. \"-c className -o\"");
        String testArgs = "-c Logic -o";
        String[] args = testArgs.split(" ");
        Main instance = new Main();
        String expResult = "Missing argument for option: o";
        String result = instance.checkArgs(args);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkArgs method, of class Main. "-c className"
     */
    @Test
    public void testCheckArgs05() {
        System.out.println("Test of checkArgs method, of class Main. \"-c className\"");
        String testArgs = "-c Logic";
        String[] args = testArgs.split(" ");
        Main instance = new Main();
        String expResult = "True";
        String result = instance.checkArgs(args);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkArgs method, of class Main. "-c className"
     */
    @Test
    public void testCheckArgs06() {
        System.out.println("Test of checkArgs method, of class Main. \"-c className\"");
        String testArgs = "-c WrongClassName";
        String[] args = testArgs.split(" ");
        Main instance = new Main();
        String expResult = "ClassNotFoundException";
        String result = instance.checkArgs(args);
        assertEquals(expResult, result);
    }

    /**
     * Test of readWithoutRunMe method, of class Main.
     */
    @Test
    public void testReadWithoutRunMe() throws ClassNotFoundException {
        System.out.println("readWithoutRunMe");
        Main instance = new Main();
        instance.setClassName(Class.forName("Logic"));
        int expResult = 2;
        int result = instance.readWithoutRunMe().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of readRunMe method, of class Main.
     */
    @Test
    public void testReadRunMe() throws ClassNotFoundException {
        System.out.println("readRunMe");
        Main instance = new Main();
        instance.setClassName(Class.forName("Logic"));
        int expResult = 5;
        int result = instance.readRunMe().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of readRunMeNotInvokebar method, of class Main.
     */
    @Test
    public void testReadRunMeNotInvokebar() throws ClassNotFoundException {
        System.out.println("readRunMeNotInvokebar");
        Main instance = new Main();
        instance.setClassName(Class.forName("Logic"));
        int expResult = 1;
        int result = instance.readRunMeNotInvokebar().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of log method, of class Main.
     */
    @Test
    public void testLog() {
        System.out.println("log");
        String message = "test";
        Main instance = new Main();
        instance.setOuputFile("Test.txt");
        instance.log(message);
    }
}
