package validate;

import validate.FloatFSM;
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
        
        assertEquals(instance.validate("1"), true);
        assertEquals(instance.validate("-3.14"), true);
        assertEquals(instance.validate("0"), true);
        assertEquals(instance.validate("+0.1"), true);
        assertEquals(instance.validate(".1"), true);
        assertEquals(instance.validate("."), false);
        assertEquals(instance.validate("+ x1"), false);
        assertEquals(instance.validate("3.14e-2"), false);
        
        assertEquals(instance.validate("+0.1a"), false);
        assertEquals(instance.validate("--0"), false);
        assertEquals(instance.validate(""), false);
        assertEquals(instance.validate("0."), true);
        assertEquals(instance.validate("111."), true);   
    }
    
}
