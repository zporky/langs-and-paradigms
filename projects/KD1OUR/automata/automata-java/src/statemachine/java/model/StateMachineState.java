package statemachine.java.model;

/**
 * Az automata állapota (a szabály neve, avagy nemterminális szimbólumok)
 */
public enum StateMachineState {
    START_SIGNED(false),
    START_UNSIGNED(false),
    ILLEGAL_STATE_0(false),
    LEGAL_STATE_1(true),
    LEGAL_STATE_2(true),
    LEGAL_STATE_3(true),
    LEGAL_STATE_4(true),
    LEGAL_STATE_5(true),
    ILLEGAL_STATE_6(false),
    LEGAL_STATE_7(true);

    private boolean finalState;

    private StateMachineState(boolean finalState){
        this.finalState = finalState;
    }

    public boolean isFinalState(){
        return this.finalState;
    }
}
