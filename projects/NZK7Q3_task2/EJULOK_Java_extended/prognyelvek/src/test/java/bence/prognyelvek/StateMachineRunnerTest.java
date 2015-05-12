package bence.prognyelvek;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class StateMachineRunnerTest {

    private StateMachineRunner testSubject;

    @Parameterized.Parameter(0)
    public String input;

    @Parameterized.Parameter(1)
    public String expected;

    @Parameterized.Parameters
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
                        {"3.14e-2", "FAIL"},

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
                }
        );
    }

    @Before
    public void setUp() {
        testSubject = new StateMachineRunner(StateMachineBuilder.build());
    }

    @Test
    public void testScenario() {
        Assert.assertEquals(expected, testSubject.process(input));
    }
}
