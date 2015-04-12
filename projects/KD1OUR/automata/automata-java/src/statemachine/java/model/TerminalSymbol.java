package statemachine.java.model;

/**
 * A terminális szimbúlumok fajtája, ahogy a feladatleírásban szerepelnek.
 */
public enum TerminalSymbol {
    DIGIT, POINT, SIGN, ZERO, E;

    /**
     * Egy karakterhez hozzárendeli a terminális szimbólum típusát.
     * @param character
     *      bemeneti karakter
     * @return
     *      terminális szimbólum típus, amennyiben egy érvényes terminális szimbólum, egyébként <code>null</code>
     */
    public static TerminalSymbol valueOf(char character){
        if(character == '0'){
            return TerminalSymbol.ZERO;
        } else if(Character.isDigit(character)){
            return TerminalSymbol.DIGIT;
        } else if (character == '.') {
            return TerminalSymbol.POINT;
        } else if (character == '+' || character == '-') {
            return TerminalSymbol.SIGN;
        } else if (character == 'e' || character == 'E') {
            return TerminalSymbol.E;
        }

        return null;
    }
}
