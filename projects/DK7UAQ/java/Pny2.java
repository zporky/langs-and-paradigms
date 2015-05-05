
package pny2;

import pny2.fsm.State;
import pny2.fsm.Rule;
import java.util.Scanner;
import pny2.fsm.FSM;

/**
 * Pny2/hf1: finite state machine.
 * @author Viktor Varga
 */
public class Pny2 {
    
    private static final String FAIL = "FAIL";
    private static final String OK = "OK";
    
    private static final String STATE_ID_S = "S";
    private static final String STATE_ID_S2 = "S'";
    private static final String STATE_ID_I0 = "I0";
    private static final String STATE_ID_T1 = "T1";
    private static final String STATE_ID_T2 = "T2";
    private static final String STATE_ID_T4 = "T4";

    //extend
    private static final String STATE_ID_T5 = "T5";
    private static final String STATE_ID_T6 = "T6";
    private static final String STATE_ID_T7 = "T7";    
    
    private static final String[] TERMINALS_S = new String[]{"+","-"};
    private static final String[] TERMINALS_P = new String[]{"."};
    private static final String[] TERMINALS_Z = new String[]{"0"};
    private static final String[] TERMINALS_D = new String[]{"1","2","3","4","5","6","7","8","9"};
    private static final String[] TERMINALS_E = new String[]{"e", "E"};
    
    private static final FSM parser;
    private static final State STARTING_STATE;
    
    static {
        STARTING_STATE = new State(false, STATE_ID_S);
        parser = new FSM(STARTING_STATE);
        parser.addState(STARTING_STATE);
    }
    
    private static void initFSM() throws Exception {
        //add states
        State state_s2 = new State(false, STATE_ID_S2);
        State state_i0 = new State(false, STATE_ID_I0);
        State state_t1 = new State(true, STATE_ID_T1);
        State state_t2 = new State(true, STATE_ID_T2);
        State state_t4 = new State(true, STATE_ID_T4);
	
        State state_t5 = new State(false, STATE_ID_T5);
        State state_t6 = new State(false, STATE_ID_T6);
        State state_t7 = new State(true,  STATE_ID_T7);
	
        parser.addState(state_s2);
        parser.addState(state_i0);
        parser.addState(state_t1);
        parser.addState(state_t2);
        parser.addState(state_t4);

	//extend
        parser.addState(state_t5);
        parser.addState(state_t6);
        parser.addState(state_t7);
	
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
        r3.addRHS(TERMINALS_D, state_t1);
        Rule r4 = new Rule(state_t1);
        r4.addRHS(TERMINALS_D, state_t1);
        r4.addRHS(TERMINALS_E, state_t5);			
        Rule r5 = new Rule(state_t2);
        r5.addRHS(TERMINALS_P, state_t1);
        r5.addRHS(TERMINALS_E, state_t5);			
        Rule r6 = new Rule(state_t4);
        r6.addRHS(TERMINALS_D, state_t4);
        r6.addRHS(TERMINALS_P, state_t1);
        r6.addRHS(TERMINALS_E, state_t5);			

	// extend	
	Rule r7 = new Rule (state_t5);
	r7.addRHS(TERMINALS_S, state_t6);
	r7.addRHS(TERMINALS_D, state_t7);	
	Rule r8 = new Rule (state_t6);
	r8.addRHS(TERMINALS_D, state_t7);
	Rule r9 = new Rule (state_t7);
	r9.addRHS(TERMINALS_D, state_t7);
	r9.addRHS(TERMINALS_Z, state_t7);
		
        parser.addRule(r1);
        parser.addRule(r2);
        parser.addRule(r3);
        parser.addRule(r4);
        parser.addRule(r5);
        parser.addRule(r6);

	//extend
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
        // result &= (runParser("3.14e-2").equals("FAIL")); -- this becomes OK with the extension
        result &= (runParser("a").equals("FAIL"));
        result &= (runParser("abc2").equals("FAIL"));

	//extend
        result &= (runParser("3.14e-2").equals("OK 3.14e-2"));
	result &= (runParser("2e+2").equals("OK 2e2"));
	result &= (runParser("2e-2").equals("OK 2e-2"));
	result &= (runParser("-2e+2").equals("OK -2e2"));
	result &= (runParser("2E+2").equals("OK 2e2"));
	result &= (runParser("2E+0").equals("FAIL"));
	
        return result;
    }
    
    private static String runParser(String input) {
        parser.runOnInput(input);
        if (parser.isAccepted()) {
            return OK + " " + formatInput(input);
        } else {
            return FAIL;
        }
    }

    // Formatting consistent with the unextended version of the program
    private static String formatInput(String input){
	if (input == "0"){
	    return "0";
	}
	String input2 = input.replace("+", "").replace("E", "e");
	if ( !input2.contains(".") && !input2.contains("E") && !input2.contains("e")){
	    return input2 + ".0";
	} else if (input2.charAt(0) == '.') {
	    return "0" + input2;
	} else {
	    return input2;
	}	    
    }
    
    public static void main(String[] args) {
        try {
            initFSM();
        } catch (Exception e) {
            System.err.println("Invalid initalization of machine: " + e.getMessage());
        }
        // System.out.println(test());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String result = runParser(scanner.nextLine());
            System.out.println(result);
        }
        scanner.close();        
    }
}
