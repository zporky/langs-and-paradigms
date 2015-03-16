package statemachine.model;

import statemachine.ProductionRuleRHS;

import java.util.HashMap;
import java.util.Map;

/**
 * átmenet függvény (nyelvtani szabályok)
 */
public final class ProductionRules {

    private ProductionRules(){
    }

    public static Map<StateMachineState, ProductionRuleRHS> RULES;

    static {
        ProductionRules.RULES = new HashMap<>();

        // START_SIGNED (S)
        ProductionRuleRHS startSignedRhs = new ProductionRuleRHS();
        startSignedRhs.setChainRule(StateMachineState.START_UNSIGNED);
        startSignedRhs.addRule(TerminalSymbol.SIGN, StateMachineState.START_UNSIGNED);
        ProductionRules.RULES.put(StateMachineState.START_SIGNED, startSignedRhs);

        // START_UNSIGNED (S')
        ProductionRuleRHS startUnsignedRhs = new ProductionRuleRHS();
        startUnsignedRhs.addRule(TerminalSymbol.POINT, StateMachineState.ILLEGAL_STATE_0);
        startUnsignedRhs.addRule(TerminalSymbol.ZERO, StateMachineState.LEGAL_STATE_2);
        startUnsignedRhs.addRule(TerminalSymbol.DIGIT, StateMachineState.LEGAL_STATE_4);
        ProductionRules.RULES.put(StateMachineState.START_UNSIGNED, startUnsignedRhs);

        // ILLEGAL_STATE_0 (I_0)
        ProductionRuleRHS illegalRhs = new ProductionRuleRHS();
        illegalRhs.addRule(TerminalSymbol.DIGIT, StateMachineState.LEGAL_STATE_1);
        ProductionRules.RULES.put(StateMachineState.ILLEGAL_STATE_0, illegalRhs);

        // LEGAL_STATE_1 (T_1)
        ProductionRuleRHS legal1Rhs = new ProductionRuleRHS();
        legal1Rhs.addRule(TerminalSymbol.DIGIT, StateMachineState.LEGAL_STATE_1);
        ProductionRules.RULES.put(StateMachineState.LEGAL_STATE_1, legal1Rhs);

        // LEGAL_STATE_2 (T_2)
        ProductionRuleRHS legal2Rhs = new ProductionRuleRHS();
        legal2Rhs.addRule(TerminalSymbol.POINT, StateMachineState.LEGAL_STATE_3);
        ProductionRules.RULES.put(StateMachineState.LEGAL_STATE_2, legal2Rhs);

        // LEGAL_STATE_3 (T_3)
        ProductionRuleRHS legal3Rhs = new ProductionRuleRHS();
        legal3Rhs.addRule(TerminalSymbol.DIGIT, StateMachineState.LEGAL_STATE_3);
        ProductionRules.RULES.put(StateMachineState.LEGAL_STATE_3, legal3Rhs);

        // LEGAL_STATE_4 (T_4)
        ProductionRuleRHS legal4Rhs = new ProductionRuleRHS();
        legal4Rhs.addRule(TerminalSymbol.DIGIT, StateMachineState.LEGAL_STATE_4);
        legal4Rhs.addRule(TerminalSymbol.POINT, StateMachineState.LEGAL_STATE_5);
        ProductionRules.RULES.put(StateMachineState.LEGAL_STATE_4, legal4Rhs);

        // LEGAL_STATE_5 (T_5)
        ProductionRuleRHS legal5Rhs = new ProductionRuleRHS();
        legal5Rhs.addRule(TerminalSymbol.DIGIT, StateMachineState.LEGAL_STATE_5);
        ProductionRules.RULES.put(StateMachineState.LEGAL_STATE_5, legal5Rhs);

    }

}
