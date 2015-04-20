
package pny2;

import pny2.fsm.State;
import pny2.fsm.Rule;
import java.util.Scanner;

/**
 * Pny2/hf2: finite state machine, extended version.
 * @author Viktor Varga
 */
public class Pny2 {
    
    private static final String FAIL = "FAIL";
    private static final String OK = "OK";
    
    private static final String STATE_ID_S = "S";
    private static final String STATE_ID_S2 = "S'";
    private static final String STATE_ID_I0 = "I0";
    private static final String STATE_ID_T135 = "T135";
    private static final String STATE_ID_T2 = "T2";
    private static final String STATE_ID_T4 = "T4";
    private static final String STATE_ID_I1 = "I1";
    private static final String STATE_ID_T6 = "T6";
    private static final String STATE_ID_I2 = "I2";
    
    private static final String[] TERMINALS_S = new String[]{"+","-"};
    private static final String[] TERMINALS_P = new String[]{"."};
    private static final String[] TERMINALS_Z = new String[]{"0"};
    private static final String[] TERMINALS_D = new String[]{"1","2","3","4","5","6","7","8","9"};
    
    private static final String[] TERMINALS_E = new String[]{"e","E"};
    
    private static final FloatParser parser;
    private static final State STARTING_STATE;
    
    static {
        STARTING_STATE = new State(false, STATE_ID_S);
        parser = new FloatParser(STARTING_STATE);
        parser.addState(STARTING_STATE);
    }
    
    private static void initFSM() throws Exception {
        //add states
        State state_s2 = new State(false, STATE_ID_S2);
        State state_i0 = new State(false, STATE_ID_I0);
        State state_t135 = new State(true, STATE_ID_T135);
        State state_t2 = new State(true, STATE_ID_T2);
        State state_t4 = new State(true, STATE_ID_T4);
        State state_i1 = new State(false, STATE_ID_I1);
        State state_t6 = new State(true, STATE_ID_T6);
        State state_i2 = new State(false, STATE_ID_I2);
        parser.addState(state_s2);
        parser.addState(state_i0);
        parser.addState(state_t135);
        parser.addState(state_t2);
        parser.addState(state_t4);
        parser.addState(state_i1);
        parser.addState(state_t6);
        parser.addState(state_i2);
        //add rules
        Rule r1 = new Rule(STARTING_STATE);
        r1.addRHS(TERMINALS_P, state_i0); //automaton was slightly rearranged (to avoid epsilon)
        r1.addRHS(TERMINALS_Z, state_t2);
        r1.addRHS(TERMINALS_D, state_t4);
        r1.addRHS(TERMINALS_S, state_s2);
        Rule r2 = new Rule(state_s2);
        r2.addRHS(TERMINALS_P, state_i0);
        r2.addRHS(TERMINALS_Z, state_t2);
        r2.addRHS(TERMINALS_D, state_t4);
        Rule r3 = new Rule(state_i0);
        r3.addRHS(TERMINALS_D, state_t135);
        Rule r4 = new Rule(state_t135);
        r4.addRHS(TERMINALS_D, state_t135);
        r4.addRHS(TERMINALS_E, state_i1);
        Rule r5 = new Rule(state_t2);
        r5.addRHS(TERMINALS_P, state_t135);
        r5.addRHS(TERMINALS_E, state_i1);
        Rule r6 = new Rule(state_t4);
        r6.addRHS(TERMINALS_D, state_t4);
        r6.addRHS(TERMINALS_P, state_t135);
        r6.addRHS(TERMINALS_E, state_i1);
        Rule r7 = new Rule(state_i1);
        r7.addRHS(TERMINALS_S, state_i2);
        r7.addRHS(TERMINALS_D, state_t6);
        Rule r8 = new Rule(state_t6);
        r8.addRHS(TERMINALS_D, state_t6);
        r8.addRHS(TERMINALS_Z, state_t6);
        Rule r9 = new Rule(state_i2);
        r9.addRHS(TERMINALS_D, state_t6);
        parser.addRule(r1);
        parser.addRule(r2);
        parser.addRule(r3);
        parser.addRule(r4);
        parser.addRule(r5);
        parser.addRule(r6);
        parser.addRule(r7);
        parser.addRule(r8);
        parser.addRule(r9);
    }

    private static boolean test() {
        boolean result = true;
        result &= (runParser("1").equals("OK 1.0"));
        result &= (runParser("-3.14").equals("OK -3.14"));
        result &= (runParser("0").equals("OK 0"));
        result &= (runParser("+0.1").equals("OK 0.1"));
        result &= (runParser(".1").equals("OK 0.1"));
        result &= (runParser("1").equals("OK 1.0"));
        result &= (runParser("+1.235").equals("OK 1.235"));
        result &= (runParser("1.02").equals("FAIL"));
        result &= (runParser("+0.02").equals("FAIL"));
        result &= (runParser("-0.230").equals("FAIL"));
        result &= (runParser(".").equals("FAIL"));
        result &= (runParser("+x1").equals("FAIL"));
        result &= (runParser("+ x1").equals("FAIL"));
        result &= (runParser("+ 1").equals("FAIL"));
        result &= (runParser("+").equals("FAIL"));
        result &= (runParser("-").equals("FAIL"));
        result &= (runParser("a").equals("FAIL"));
        result &= (runParser("abc2").equals("FAIL"));
        //bovites
        result &= (runParser("-3.14e2").equals("OK -3.14e+2"));
        result &= (runParser("1.12e+1").equals("OK 1.12e+1"));
        result &= (runParser("+0.1E10").equals("OK 0.1e+10"));
        result &= (runParser(".1E-4").equals("OK 0.1e-4"));
        result &= (runParser("1.E4").equals("OK 1.0e+4"));
        result &= (runParser("13e4").equals("OK 13.0e+4"));
        result &= (runParser("1e").equals("FAIL"));
        result &= (runParser("+1.235E0").equals("FAIL"));
        result &= (runParser("1e-0").equals("FAIL"));
        result &= (runParser("+1.235E+").equals("FAIL"));
        result &= (runParser("2+1").equals("FAIL"));
        result &= (runParser("2 e-4").equals("FAIL"));
        result &= (runParser("2e-04").equals("FAIL"));
        return result;
    }
    
    private static String runParser(String input) {
        parser.runOnInput(input);
        if (parser.isAccepted()) {
            return OK + " " + parser.createFloat();
        } else {
            return FAIL;
        }
    }
    
    public static void main(String[] args) {
        try {
            initFSM();
        } catch (Exception e) {
            System.err.println("Invalid initalization of machine: " + e.getMessage());
        }
        //System.out.println(test());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String result = runParser(scanner.nextLine());
            System.out.println(result);
        }
        scanner.close();
        
    }

}
