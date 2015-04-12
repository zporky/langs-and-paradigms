package statemachine.java.model;

/**
 * Az automata állapota (a szabály neve, avagy nemterminális szimbólumok)
 */
public enum StateMachineState {
    START_SIGNED(false),     // S
    START_UNSIGNED(false),   // S'
    ILLEGAL_STATE_0(false),  // I_0
    ILLEGAL_STATE_1(false),  // I_1 (e, vagy E karakter utáni állapot)
    LEGAL_STATE_1(true),     // T_1
    LEGAL_STATE_2(true),     // T_2
    LEGAL_STATE_3(true),     // T_3
    LEGAL_STATE_4(true),     // T_4
    LEGAL_STATE_5(true);     // T_5

    private boolean finalState;

    private StateMachineState(boolean finalState){
        this.finalState = finalState;
    }

    public boolean isFinalState(){
        return this.finalState;
    }
}
