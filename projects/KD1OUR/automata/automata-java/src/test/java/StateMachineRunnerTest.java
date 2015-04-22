import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import statemachine.java.StateMachine;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class StateMachineRunnerTest {

    private StateMachine testSubject = Main.stateMachine;

    @Parameterized.Parameter(0)
    public String input;

    @Parameterized.Parameter(1)
    public String expected;

    @Parameterized.Parameters(name = "{0} >> {1}")
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][] {
                        // Specified
                        {"1", "OK 1.0"},
                        {"-3.14", "OK 3.14"},
                        {"0", "OK 0"},
                        {"+0.1", "OK 0.1"},
                        {".1", "OK 0.1"},
                        {"1", "OK 1.0"},
                        {".", "FAIL"},
                        {"+x1", "FAIL"},
                        {"-", "FAIL"},
                        {"3.14e-2", "OK 3.14e-2"}, // Changed

                        // Discussed
                        {"1.", "OK 1.0"},
                        {"1.0", "OK 1.0"},

                        // Extra
                        {"01.0", "FAIL"},
                        {"12.12.12", "FAIL"},
                        {"..1", "FAIL"},
                        {"--1", "FAIL"},
                        {".-3", "FAIL"},
                        {"100.0", "OK 100.0"},
                        {"100.001", "OK 100.001"},
                        {"1.00000", "OK 1.00000"}, // Later this could be nicer
                        {"00", "FAIL"}, // Not supported yet based on the state machine
                        {"-.4", "OK 0.4"},

                        // Task 2
                        {"1e2", "OK 1e2"},
                        {"1E2", "OK 1e2"},
                        {"2.5e-4", "OK 2.5e-4"},
                        {".5e+3", "OK 0.5e3"},
                        {"1000e100", "OK 1000e100"},
                        {"-.5e+3", "OK 0.5e3"},
                        {"e3", "FAIL"},
                        {"3e", "FAIL"},
                        {"1e2e3", "FAIL"},
                        {"0e0", "OK 0e0"},
                        {"2e.3", "FAIL"},
                }
        );
    }

    @Test
    public void testScenario() {
        Assert.assertEquals(expected, testSubject.parse(input));
    }
}
