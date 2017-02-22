package statemachine.java;

import statemachine.java.model.StateMachineState;
import statemachine.java.model.TerminalSymbol;

import java.util.Map;

/**
 * Az automata működését magába foglaló osztály.
 */
public class StateMachine {

    private StateMachineState state;
    private final StateMachineState start;
    private final Map<StateMachineState, ProductionRuleRHS> productionRules;

    public StateMachine(StateMachineState start, Map<StateMachineState, ProductionRuleRHS> productionRules){
        this.start = start;
        this.productionRules = productionRules;
    }

    public String parse(String input){
        StringBuilder sb = new StringBuilder();

        char currentChar;
        TerminalSymbol currentTerminal = null;
        ProductionRuleRHS currentRule;
        StateMachineState nextState;

        this.state = this.start;

        for(int i = 0; i < input.length(); i++){
            currentChar = input.charAt(i);
            currentTerminal = TerminalSymbol.valueOf(currentChar);

            // érvénytelen terminális esetén FAIL
            if(currentTerminal == null){
                return "FAIL";
            }

            currentRule = this.productionRules.get(this.state);
            nextState = currentRule.getNewState(currentTerminal);

            // ha a terminálist nem fogadja el a szabály
            if(nextState == null){
                // megnézzük van-e láncszabály
                nextState = currentRule.getChainRuleNextState();
                if(nextState == null){
                    // ha ez sincs, akkor érvénytelen input
                    return "FAIL";
                } else {
                    // egyébként marad az eddigi input és állapotot váltunk
                    this.state = nextState;
                    i--;
                    continue;
                }
            }

            if (nextState == StateMachineState.ILLEGAL_STATE_0 && sb.length() == 0) {
                sb.append('0');
            }

            // egyébként (ha találtunk érvényes állapot-átmenetet):
            // eltároljuk a karaktert (hozzá fog tartozni az eredményhez, ha nem előjel), és állapotot váltunk
            if (currentTerminal == TerminalSymbol.EXPONENTIAL) {
                sb.append('e');
            } else if(currentChar == '-' && nextState == StateMachineState.LEGAL_STATE_6) {
                sb.append(currentChar);
            } else if(currentTerminal != TerminalSymbol.SIGN) {
                sb.append(currentChar);
            }


            this.state = nextState;
        }

        // végeztünk az input feldolgozásával, megnézzük, hogy elfogadó állapotba jutottunk-e
        if(this.state.isFinalState()){
            // a befejező állapottól függően még picit alakítani kell az eredményen
            switch(this.state){
                case LEGAL_STATE_1:
                    return "OK " + sb.toString();
                case LEGAL_STATE_2:
                    return "OK 0";
                case LEGAL_STATE_4:
                    return "OK " + sb.toString() + ".0";
                case LEGAL_STATE_3:
                    return (currentTerminal != TerminalSymbol.DIGIT) ? "OK 0" : ("OK " + sb.toString());
                case LEGAL_STATE_5:
                    return "OK " + sb.toString() + (currentTerminal != TerminalSymbol.DIGIT && currentTerminal != TerminalSymbol.ZERO ? "0" : "");
                case LEGAL_STATE_6:
                    return "OK " + sb.toString();
            }
        }

        return "FAIL";
    }
}
