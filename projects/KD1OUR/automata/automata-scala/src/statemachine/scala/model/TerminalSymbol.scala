package statemachine.scala.model

/**
 * A terminális szimbúlumok fajtája, ahogy a feladatleírásban szerepelnek.
 */
object TerminalSymbol extends Enumeration {

  type TerminalSymbol = Value

  val DIGIT, POINT, SIGN, ZERO, E = Value

  /**
   * Egy karakterhez hozzárendeli a terminális szimbólum típusát.
   * @param char
     * bemeneti karakter
   * @return
     * terminális szimbólum típus, amennyiben egy érvényes terminális szimbólum, egyébként <code>null</code>
   */
  def getTerminalSymbol(char : Char) : Option[TerminalSymbol] = {
    if(char == '0') Some(ZERO)
    else if (char.isDigit) Some(DIGIT)
    else if (char == '.') Some(POINT)
    else if (char == '+'  || char == '-') Some(SIGN)
    else if (char == 'e' || char == 'E') Some(E)
    else None
  }

}
