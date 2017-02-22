package validate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mercer
 */
public class FloatFSMTest {

    public FloatFSMTest() {
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
     * Test of validate method, of class FloatFSM.
     */
    @Test
    public void testValidate() {
        FloatFSM instance = new FloatFSM();

        //task1
        assertEquals(instance.validate("1"), true);
        assertEquals(instance.validate("-3.14"), true);
        assertEquals(instance.validate("0"), true);
        assertEquals(instance.validate("+0.1"), true);
        assertEquals(instance.validate(".1"), true);
        assertEquals(instance.validate("."), false);
        assertEquals(instance.validate("+ x1"), false);
        //assertEquals(instance.validate("3.14e-2"), false);

        assertEquals(instance.validate("+0.1a"), false);
        assertEquals(instance.validate("--0"), false);
        assertEquals(instance.validate(""), false);
        assertEquals(instance.validate("0."), true);
        assertEquals(instance.validate("111."), true);

        //task2
        assertEquals(instance.validate("3.14e-2"), true);
        assertEquals(instance.validate("1e2"), true);
        assertEquals(instance.validate("2.4E-5"), true);
        assertEquals(instance.validate(".1e+3"), true);
        assertEquals(instance.validate("0e12"), true);

        assertEquals(instance.validate("ee"), false);
        assertEquals(instance.validate("1.0e"), false);
        assertEquals(instance.validate("3.14e-"), false);
        assertEquals(instance.validate(".1e"), false);
        assertEquals(instance.validate(".1e++3"), false);
        assertEquals(instance.validate(".1ee+3"), false);

    }

}
