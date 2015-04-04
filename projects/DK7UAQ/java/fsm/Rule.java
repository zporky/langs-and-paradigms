
package pny2.fsm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Rule format is A -> bC | dE | ... , where
 * <ul>
 *   <li>A is the LHS state</li>
 *   <li>b, d, ... are a set of terminal words of positive length</li>
 *   <li>C, E, ... are the RHS states</li>
 * </ul>
 * <p>
 * The rule format A -> b is not allowed. Instead each state can be accepted or not.
 * If a state is accepted, it means that the automaton can terminate when its current
 * state is that one.
 */
public class Rule {

    private final State lhs;
    private final List<RHS> rhss;
    
    private RHS lastMatchedRHS;

    public Rule(State lhs) {
        this.lhs = lhs;
        rhss = new ArrayList<>();
    }
    
    public void addRHS(String[] terminals, State state) throws Exception {
        rhss.add(new RHS(terminals, state));
    }
    
    protected State getLhs() {
        return lhs;
    }
    protected RHS getLastMatchedRHS() {
        return lastMatchedRHS;
    }
    
    /** 
     * @return the result state if there was a successful match; null otherwise
     * @param input will contain the remaining input after a successful match
     */
    State tryRule(StringBuilder input) {
        String inputS = input.toString();
        ListIterator<RHS> it = rhss.listIterator();
        while (it.hasNext()) {
            RHS rhs = it.next();
            int matchLen = rhs.matches(inputS);
            if (matchLen > 0) {
                input.delete(0, matchLen);
                lastMatchedRHS = rhs;
                return rhs.getState();
            }
        }
        return null;
    }
    
    protected static class RHS {
        
        /** contains the set of possible terminals in this RHS */
        private final String[] terminals;
        private final State state;
        
        private String lastMatchedTerminal;

        RHS(String[] terminals, State state) throws Exception {
            for (String s: terminals) {
                if (s.length() == 0) {
                    throw new Exception("Error! A terminal is found to be zero long. No epsilon transitions supported. ");
                }
            }
            this.terminals = terminals;
            this.state = state;
        }

        protected String getLastMatchedTerminal() {
            return lastMatchedTerminal;
        }
        protected State getState() {
            return state;
        }
        
        /** @return the length of the matched string; 0 if it could not be matched */
        int matches(String toMatch) {
            for (String s: terminals) {
                if (toMatch.startsWith(s)) {
                    lastMatchedTerminal = s;
                    return s.length();
                }
            }
            return 0;
        }
        
        
    }
    
}
