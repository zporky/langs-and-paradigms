package statemachine.scala

import statemachine.scala.model.StateMachineState._
import statemachine.scala.model.TerminalSymbol._
import statemachine.scala.model.ProductionRules._

/**
 * Implementation of the state machine.
 */
object StateMachine {

  def process(input : String) : String = {

    def processSymbols(input : Seq[Option[TerminalSymbol]], state : StateMachineState) : Option[StateMachineState] = {
      // we are finished, just return the last state
      if (input.isEmpty) Some(state)
      else {
        // we had an invalid character in the input, return None
        if (input contains None) None
        else {
          // apply the corresponding rule, see if the application was successful
          // (the terminal character is accepted in the current state)
          val stepResult = applyRule(state)(input.head.get)
          if (!stepResult.newState.isDefined) None
          else {
            if(stepResult.terminalProcessed) processSymbols(input.tail, stepResult.newState.get)
            else processSymbols(input, stepResult.newState.get)
          }
        }
      }
    }

    // parse characters as terminal symbols, and call processSymbols with start state
    val terminalSymbols = input.map(x => getTerminalSymbol(x))
    val lastState = processSymbols(terminalSymbols, START_SIGNED)

    // check if all the terminal were valid, and if the last state is a final state
    if(!lastState.isDefined || !isFinalState(lastState.get)) "FAIL" else {
      // some formatting if the input was valid
      val output = if(terminalSymbols.head.get == SIGN) input.tail else input

      lastState.get match {
        case LEGAL_STATE_1 => "OK 0" + output
        case LEGAL_STATE_2 => "OK 0"
        case LEGAL_STATE_3 => if (terminalSymbols.last.get != DIGIT) "OK 0" else "OK " + output
        case LEGAL_STATE_4 => "OK " + output + ".0"
        case LEGAL_STATE_5 => "OK " + output + (if (terminalSymbols.last.get != DIGIT) "0" else "")
        case _ => "FAIL"
      }
    }
  }
}
