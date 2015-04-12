package statemachine.scala.model

import statemachine.scala.model.TerminalSymbol._
import statemachine.scala.model.StateMachineState._

/**
 * A produkciós szabályokat tartalmazza.
 */
object ProductionRules {

  /**
   * Az automata egy lépésének eredménye.
   *
   * @param newState
   *                 az új állapot, amelybe lépünk, amennyiben létezett érvényes állapot-átmenet
   *                 <code>None</code>, egyébként
   * @param terminalProcessed 
   *	<ul>
   *                 <li><code>true</code>, ha a terminális szimbólumot feldolgoztuk</li>
   *                 <li><code>false</code>, egyébként (pl. ha láncszabályt alkalmaztunk)</li>
   *	</ul>
   */
  case class RuleResult(newState: Option[StateMachineState], terminalProcessed : Boolean = true)

  val FAIL = RuleResult(None, false)

  // Innentől a produkciós szabályok:
  def applyRule(state : StateMachineState)(symbol : TerminalSymbol) : RuleResult = {
    state match {
      case START_SIGNED => symbol match {
        case SIGN => RuleResult(Some(START_UNSIGNED))
        case DIGIT | ZERO | POINT => RuleResult(Some(START_UNSIGNED), false)
        case _ => FAIL
      }
      case START_UNSIGNED => symbol match {
        case POINT => RuleResult(Some(ILLEGAL_STATE_0))
        case ZERO => RuleResult(Some(LEGAL_STATE_2))
        case DIGIT => RuleResult(Some(LEGAL_STATE_4))
        case SIGN => FAIL
      }
      case ILLEGAL_STATE_0 => symbol match {
        case DIGIT => RuleResult(Some(LEGAL_STATE_1))
        case _ => FAIL
      }
      case ILLEGAL_STATE_1 => symbol match {
        case SIGN => RuleResult(Some(ILLEGAL_STATE_0))
        case DIGIT => RuleResult(Some(ILLEGAL_STATE_0), false)
        case _ => FAIL
      }
      case LEGAL_STATE_1 => symbol match {
        case DIGIT => RuleResult(Some(LEGAL_STATE_1))
        case E => RuleResult(Some(ILLEGAL_STATE_1))
        case _ => FAIL
      }
      case LEGAL_STATE_2 => symbol match {
        case POINT => RuleResult(Some(LEGAL_STATE_3))
        case E => RuleResult(Some(ILLEGAL_STATE_1))
        case _ => FAIL
      }
      case LEGAL_STATE_3 => symbol match {
        case DIGIT => RuleResult(Some(LEGAL_STATE_3))
        case E => RuleResult(Some(ILLEGAL_STATE_1))
        case _ => FAIL
      }
      case LEGAL_STATE_4 => symbol match {
        case DIGIT => RuleResult(Some(LEGAL_STATE_4))
        case POINT => RuleResult(Some(LEGAL_STATE_5))
        case E => RuleResult(Some(ILLEGAL_STATE_1))
        case _ => FAIL
      }
      case LEGAL_STATE_5 => symbol match {
        case DIGIT => RuleResult(Some(LEGAL_STATE_5))
        case E => RuleResult(Some(ILLEGAL_STATE_1))
        case _ => FAIL
      }
    }
  }
}
