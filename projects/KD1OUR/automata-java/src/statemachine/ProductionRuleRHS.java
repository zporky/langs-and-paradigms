package statemachine;

import statemachine.model.StateMachineState;
import statemachine.model.TerminalSymbol;

import java.util.HashMap;
import java.util.Map;

/**
 * Produkciós szabály jobb oldalát reprezentáló osztály.
 */
public class ProductionRuleRHS {

    private StateMachineState chainRuleNextState;
    private Map<TerminalSymbol, StateMachineState> ruleMap;

    /**
     * Létrehoz egy szabály jobboldalt.
     * Láncszabály a <code>setChainRule</code> metódussal állítható be.
     * Egyéb szabályok az <code>addRule</code> metódussal adhatók hozzá.
     */
    public ProductionRuleRHS(){
        this.ruleMap = new HashMap<>();
    }

    /**
     * Hozzáad egy egyszerű szabályt, ahol egy terminális szimbólumhoz hozzárendelünk egy rákövetkező állapotot.
     * Ha láncszabályt szeretnénk hozzáadni, használjuk az egyparaméteres változatot!
     *
     * @param symbol
     *      az adott szabályban elfogadott terminális szimbólum
     * @param state
     *      a szimbólum hatására bekövetkező állapotátmenet új állapota
     */
    public void addRule(TerminalSymbol symbol, StateMachineState state){
        if(symbol == null || state == null){
            throw new IllegalArgumentException("Both parameters are mandatory.");
        }

        this.ruleMap.put(symbol, state);
    }

    /**
     * Lekérdezi egy adott terminális szimbólumhoz tartozó új állapotot a szabály szerint.
     *
     * @param symbol
     *      a terminális szimbólum (nem lehet <code>null</code>; ha láncszabályra vagyunk kíváncsiak,
     *      használjuk a <code>getChainRule</code> metódust!
     * @return
     *      az állapotátmenet eredménye (azaz az új szabály), illetve <code>null</code>, ha
     *      a terminális szimbólum a szabály által nem elfogadott
     */
    public StateMachineState getNewState(TerminalSymbol symbol){
        if(symbol == null){
            throw new IllegalArgumentException("symbol argument is mandatory");
        }

        return this.ruleMap.get(symbol);
    }

    /**
     * Láncszabály beállítása (csak egy adható meg).
     *
     * @param chainRuleNextState
     *      A paraméter értéke az az állapot, amelybe láncszabály esetén állapotot váltunk.
     *      Ha nincs láncszabály, akkor e paraméter értéke <code>null</code>.
     *      Egyéb szabályok az <code>addRule</code> metódussal adhatók hozzá.
     */
    public void setChainRule(StateMachineState chainRuleNextState){
        this.chainRuleNextState = chainRuleNextState;
    }

    /**
     * @return
     * A függvény értéke az az állapot, amelybe láncszabály esetén állapotot váltunk.
     * Ha nincs láncszabály, akkor e függvény értéke <code>null</code>.
     */
    public StateMachineState getChainRuleNextState(){
        return this.chainRuleNextState;
    }

}
