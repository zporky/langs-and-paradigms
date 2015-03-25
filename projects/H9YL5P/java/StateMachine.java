package finiteStateMachine;

//import FloatConstants;

public class StateMachine {
	private String myNumber;
	private String myState;
	
	StateMachine() {
		myNumber = "";
		myState = FloatConstants.S0;
	}
	
	void init(String startSymbol) {
		setNumber("");
		if (startSymbol == "+" || startSymbol == "-") {
			setState(FloatConstants.S0);
		} else {
			setState(FloatConstants.S1);
		}
	}
	
	String getState() { return myState; }
	void setState(String state) {myState = state;}
	
	String getNumber() { return myNumber; }
	void setNumber(String number) { myNumber = number; }
	void appendNumber(String number) { myNumber += number; }
	
	String processInput(String symbol) {
		if (symbol.equals("1") || symbol.equals("2") || symbol.equals("3")
			    || symbol.equals("4") || symbol.equals("5") || symbol.equals("6")
			    || symbol.equals("7") || symbol.equals("8") || symbol.equals("9")) {
			return FloatConstants.decimal;
		} else if (symbol.equals("0")) {
			return FloatConstants.zero;
		} else if (symbol.equals(".")) {
			return FloatConstants.point;
		} else if (symbol.equals("+") || symbol.equals("-")) {
			return FloatConstants.sign;
		} else {
			//unkown symbol...
			return FloatConstants.unknown;
		}
	}
	
	int nextState(String event, String symbol) {
		//System.out.println("state:"+getState()+" event:"+event+" symbol"+symbol);
		if (getState() == FloatConstants.S0 && event == FloatConstants.sign) {
			appendNumber(symbol);
			setState(FloatConstants.S1);
			return 0;
		} else if (getState() == FloatConstants.S1 && event == FloatConstants.point) {
			appendNumber(symbol);
			setState(FloatConstants.I);
			return 0;
		} else if (getState() == FloatConstants.S1 && event == FloatConstants.zero) {
			appendNumber(symbol);
			setState(FloatConstants.T2);
			return 0;
		} else if (getState() == FloatConstants.S1 && event == FloatConstants.decimal) {
			appendNumber(symbol);
			setState(FloatConstants.T4);
			return 0;
		} else if (getState() == FloatConstants.T4 && event == FloatConstants.point) {
			appendNumber(symbol);
			setState(FloatConstants.T5);
			return 0;
		} else if (getState() == FloatConstants.T2 && event == FloatConstants.point) {
			appendNumber(symbol);
			setState(FloatConstants.T3);
			return 0;
		} else if (getState() == FloatConstants.I && event == FloatConstants.decimal) {
			appendNumber(symbol);
			setState(FloatConstants.T1);
			return 0;
		} else if ((getState() == FloatConstants.T1 
			         || getState() == FloatConstants.T3 
			         || getState() == FloatConstants.T5
			         || getState() == FloatConstants.T4)
		           && event == FloatConstants.decimal) {
			appendNumber(symbol);
			return 0;
		}
		else {
			//invalid state
			System.err.println("Invalid state/event combination.");
			return -1;
		}
	}
	
}
