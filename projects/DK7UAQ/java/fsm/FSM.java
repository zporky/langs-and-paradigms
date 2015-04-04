
package pny2.fsm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/** 
 * The finite state machine.
 * Recognizes type-3 grammars. No epsilon rules allowed.
 */
public class FSM {
    
    private final List<State> states;
    private final List<Rule> rules;
    private final State startingState;
    
    private State currentState;
    private StringBuilder remainingInput;
    private boolean accepted;
    
    private Rule lastMatchedRule;

    public FSM(State startingState) {
        this.states = new ArrayList<>();
        this.rules = new ArrayList<>();
        this.startingState = startingState;
    }

    public boolean isAccepted() {
        return accepted;
    }
    protected Rule getLastMatchedRule() {
        return lastMatchedRule;
    }
    protected String getLastMatchedTerminal() {
        return getLastMatchedRule().getLastMatchedRHS().getLastMatchedTerminal();
    } 
    
    public void addState(State toAdd) {
        states.add(toAdd);
    }
    public void addRule(Rule toAdd) {
        rules.add(toAdd);
    }
    
    /** Resets the automaton and tries the input on it. */
    public void runOnInput(String input) {
        accepted = false;
        currentState = startingState;
        remainingInput = new StringBuilder(input);
        while (remainingInput.length() > 0 || !currentState.isAccepted()) {
            if (!tryMatching()) {
                return;
            }
        }
        accepted = true;
    }
    
    /** 
     * Tries to match a rule once.
     * @return whether any rule could be matched
     */
    protected boolean tryMatching() {
        ListIterator<Rule> it = rules.listIterator();
        while (it.hasNext()) {
            Rule r = it.next();
            if (r.getLhs().equals(currentState)) {
                State resultState = r.tryRule(remainingInput);
                if (resultState != null) {
                    lastMatchedRule = r;
                    currentState = resultState;
                    return true;
                }
            }
        }
        return false;
    }
    
}
