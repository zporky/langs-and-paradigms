import org.junit.Test;
import static org.junit.Assert.*;

public class MachineTest {

    public MachineTest() {
    }

    @Test
    public void testValidateInput() throws Exception {

        Machine.validateInput("1");
        Machine.validateInput("-3.14");
        Machine.validateInput("0");
        Machine.validateInput("+0.1");
        Machine.validateInput(".1");
        Machine.validateInput("0.");
        Machine.validateInput("111.");

        Machine.validateInput("3.14e-2");
        Machine.validateInput("1e2");
        Machine.validateInput("2.4E-5");
        Machine.validateInput("1e+3");
        Machine.validateInput("0e12");
    }

    @Test(expected = Machine.InvalidTransationException.class)
    public void testInvalidateTransationException0() throws Machine.InvalidTransationException {
        Machine.validateInput("");
    }

    @Test(expected = Machine.InvalidTransationException.class)
    public void testInvalidateTransationException1() throws Machine.InvalidTransationException {
        Machine.validateInput("e");
    }

    @Test(expected = Machine.InvalidTransationException.class)
    public void testInvalidateTransationException2() throws Machine.InvalidTransationException {
        Machine.validateInput("1.0e");
    }

    @Test(expected = Machine.InvalidTransationException.class)
    public void testInvalidateTransationException3() throws Machine.InvalidTransationException {
        Machine.validateInput("3.14e-");
    }
}
