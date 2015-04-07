
package pny2;

import pny2.fsm.FSM;
import pny2.fsm.State;

public class FloatParser extends FSM {
    
    private boolean signIsPositive;
    private StringBuilder integer;
    private StringBuilder fraction;
    
    private boolean afterDecimalPoint;

    public FloatParser(State startingState) {
        super(startingState);
    }

    @Override
    public void runOnInput(String input) {
        afterDecimalPoint = false;
        signIsPositive = true;
        integer = new StringBuilder();
        fraction = new StringBuilder();
        super.runOnInput(input);
    }

    @Override
    protected boolean tryMatching() {
        boolean success = super.tryMatching();
        if (success) {
            addTerminalToFloat(getLastMatchedTerminal());
        }
        return success;
    }

    protected void addTerminalToFloat(String terminal) {
        if (terminal == null) {return;}
        switch (terminal) {
            case "+":
                signIsPositive = true;
                break;
            case "-":
                signIsPositive = false;
                break;
            case ".":
                afterDecimalPoint = true;
                break;
            case "0": case "1": case "2":
            case "3": case "4": case "5":
            case "6": case "7": case "8":
            case "9":
                if (afterDecimalPoint) {
                    fraction.append(terminal.charAt(0));
                } else {
                    integer.append(terminal.charAt(0));
                }
                break;
        }
    }
    public String createFloat() {
        if (!isAccepted()) {
            return "";
        }
        //special case 0 -> 0 (intentional?)
        if (integer.toString().equals("0") && fraction.length() == 0) {
            return "0";
        }
        //
        StringBuilder sb = new StringBuilder();
        if (!signIsPositive) {
            sb.append("-");
        }
        if (integer.length() == 0) {
            integer.append("0");
        }
        sb.append(integer.toString());
        sb.append(".");
        if (fraction.length() == 0) {
            fraction.append("0");
        }
        sb.append(fraction.toString());
        return sb.toString();
    }

}
