package statemachine.scala.model

/**
 * Az automata állapota (a szabály neve, avagy nemterminális szimbólumok)
 */
object StateMachineState extends Enumeration {

  type StateMachineState = Value

  val START_SIGNED,
    START_UNSIGNED,
    ILLEGAL_STATE_0,
    LEGAL_STATE_1,
    LEGAL_STATE_2,
    LEGAL_STATE_3,
    LEGAL_STATE_4,
    LEGAL_STATE_5 = Value

  def isFinalState(state: StateMachineState): Boolean ={
    state == LEGAL_STATE_1 ||
    state == LEGAL_STATE_2 ||
    state == LEGAL_STATE_3 ||
    state == LEGAL_STATE_4 ||
    state == LEGAL_STATE_5
  }

}
