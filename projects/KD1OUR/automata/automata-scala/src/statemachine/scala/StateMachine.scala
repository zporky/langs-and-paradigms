package statemachine.scala

import statemachine.scala.model.StateMachineState._
import statemachine.scala.model.TerminalSymbol._
import statemachine.scala.model.ProductionRules._

/**
 * Az automata algoritmusának implementációja.
 */
object StateMachine {

  def process(input : String) : String = {

    def processSymbols(input : Seq[Option[TerminalSymbol]], state : StateMachineState) : Option[StateMachineState] = {
      // végeztünk, adjuk vissza az utolsó állapotot
      if (input.isEmpty) Some(state)
      else {
        // érvénytelen karakter volt a stringben (nincs hozzá definiált terminális szimbólum)
        if (input contains None) None
        else {
          // az állapotnak és szimbólumnak megfelelően alkalmazzuk a szabályt
          // ellenőrizzük, hogy az adott állapotban az adott szimbólumhoz tartozik-e állapot-átmenet
          val stepResult = applyRule(state)(input.head.get)
          if (!stepResult.newState.isDefined) None
          else {
			// nézzük meg, hogy az adott állapot-átmenet feldolgozza-e a karaktert, vagy láncszabály
            if(stepResult.terminalProcessed) processSymbols(input.tail, stepResult.newState.get)
            else processSymbols(input, stepResult.newState.get)
          }
        }
      }
    }

    // a karaktereket megfeleltetjük terminális szimbúloknak, majd feldolgozzuk őket a fent definiált process függvénnyel
    val terminalSymbols = input.map(x => getTerminalSymbol(x))
    val lastState = processSymbols(terminalSymbols, START_SIGNED)

    // amennyiben valamely karakter érvénytelen volt (vagy mert nem egy érvényes terminális, vagy mert olyan állapotban
	// következett, ahol nem következhet), vagy az utolsó állapot nem elfogadó állapot, akkor az eredmény "FAIL"
    if(!lastState.isDefined || !isFinalState(lastState.get)) "FAIL" else {
      // egy kis output formázás, ha az automata elfogadta az inputot
	  // előjel levágása
      val output = if(terminalSymbols.head.get == SIGN) input.tail else input

	  // hiányzó 0-k pótlása a szám elejéről/végéről
	  // a korrigálás mikéntje kitalálható az utolsó állapot és terminális szimbólum ismeretében
      lastState.get match {
        case LEGAL_STATE_1 => "OK 0" + output
        case LEGAL_STATE_2 => "OK 0"
        case LEGAL_STATE_3 => if (terminalSymbols.last.get != DIGIT) "OK 0" else "OK " + output
        case LEGAL_STATE_4 => "OK " + output + ".0"
        case LEGAL_STATE_5 => "OK " + output + (if (terminalSymbols.last.get != DIGIT) "0" else "")
        case LEGAL_STATE_7 => "OK " + output
        case _ => "FAIL"
      }
    }
  }
}
