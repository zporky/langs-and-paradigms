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
    private TerminalSymbol currentTerminal;
    private StringBuilder sb;

    public StateMachine(StateMachineState start, Map<StateMachineState, ProductionRuleRHS> productionRules){
        this.start = start;
        this.productionRules = productionRules;
    }

    public String parse(String input){
        this.sb = new StringBuilder();
        this.currentTerminal = null;

        this.state = this.start;

        // input ketté bontása (e, vagy E karakter előtti rész, és a többi)
        int indexOfE = Integer.max(input.indexOf('e'), input.indexOf('E'));
        String inputUntilE = (indexOfE > -1) ? input.substring(0, indexOfE) : input;
        String outputFromE = (indexOfE > -1) ? input.substring(indexOfE) : "";

        // E karakterig futtatjuk az automatát
        boolean validBeforeE = this.runMachine(inputUntilE);
        // elmentjük a köztes inputot és állapotot
        String outputBeforeE = sb.toString();
        StateMachineState stateBeforeE = this.state;
        // majd tovább futtatjuk az automatát
        boolean validAfterE = (indexOfE > -1) ? this.runMachine(outputFromE) : true;

        // végeztünk az input feldolgozásával, megnézzük, hogy elfogadó állapotba jutottunk-e
        if(validBeforeE && validAfterE){
            // a befejező állapottól függően még picit alakítani kell az eredményen
            switch(stateBeforeE){
                case LEGAL_STATE_1:
                    return "OK 0" + outputBeforeE + outputFromE;
                case LEGAL_STATE_2:
                    return "OK 0" + outputFromE;
                case LEGAL_STATE_4:
                    return "OK " + outputBeforeE + ".0" + outputFromE;
                case LEGAL_STATE_3:
                    return (this.currentTerminal != TerminalSymbol.DIGIT) ? "OK 0" : ("OK " + outputBeforeE) + outputFromE;
                case LEGAL_STATE_5:
                    return "OK " + outputBeforeE + (this.currentTerminal != TerminalSymbol.DIGIT ? "0" : "") + outputFromE;
            }
        }

        return "FAIL";
    }

    /**
     * Lefuttatja az automatát a megadott inputra, és visszaadja, hogy a szó elfogadott-e.
     * Kiindulási állapotnak a this.state osztálymezőt veszi alapul.
     *
     * @param input
     *      az input karaktersorozat
     * @return
     *      az automata elfogadja-e a megadott inputot
     */
    private boolean runMachine(String input){

        char currentChar;
        ProductionRuleRHS currentRule;
        StateMachineState nextState;

        for(int i = 0; i < input.length(); i++){
            currentChar = input.charAt(i);
            this.currentTerminal = TerminalSymbol.valueOf(currentChar);

            // érvénytelen terminális esetén FAIL
            if(this.currentTerminal == null){
                return false;
            }

            currentRule = this.productionRules.get(this.state);
            nextState = currentRule.getNewState(currentTerminal);

            // ha a terminálist nem fogadja el a szabály
            if(nextState == null){
                // megnézzük van-e láncszabály
                nextState = currentRule.getChainRuleNextState();
                if(nextState == null){
                    // ha ez sincs, akkor érvénytelen input
                    return false;
                } else {
                    // egyébként marad az eddigi input és állapotot váltunk
                    this.state = nextState;
                    i--;
                    continue;
                }
            }

            // egyébként (ha találtunk érvényes állapot-átmenetet):
            // eltároljuk a karaktert (hozzá fog tartozni az eredményhez, ha nem előjel), és állapotot váltunk
            if(currentTerminal != TerminalSymbol.SIGN) {
                sb.append(currentChar);
            }
            this.state = nextState;
        }

        return this.state.isFinalState();
    }


}
