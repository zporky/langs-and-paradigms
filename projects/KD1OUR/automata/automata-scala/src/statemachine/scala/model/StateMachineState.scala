package statemachine.scala.model

/**
 * Az automata állapota (a szabály neve, avagy nemterminális szimbólumok)
 */
object StateMachineState extends Enumeration {

  type StateMachineState = Value

  val START_SIGNED,           // S
    START_UNSIGNED,           // S'
    ILLEGAL_STATE_0,          // I_0
    ILLEGAL_STATE_1,          // I_1 (e, vagy E karakter utáni állapot)
    LEGAL_STATE_1,            // T_1
    LEGAL_STATE_2,            // T_2
    LEGAL_STATE_3,            // T_3
    LEGAL_STATE_4,            // T_4
    LEGAL_STATE_5 = Value     // T_5

  def isFinalState(state: StateMachineState): Boolean ={
    state == LEGAL_STATE_1 ||
    state == LEGAL_STATE_2 ||
    state == LEGAL_STATE_3 ||
    state == LEGAL_STATE_4 ||
    state == LEGAL_STATE_5
  }

}
