package finiteStateMachine;

public class FloatConstants {
	private FloatConstants() {}
	
	//valid states
	public static final String S0 = "S0"; //start symbol of signed float
	public static final String S1 = "S1"; //start symbol of unsigned float
	public static final String I = "I"; //non-accepted states (non-terminals)
	public static final String T1 = "T1"; //.123
	public static final String T2 = "T2"; //0
	public static final String T3 = "T3"; //0.123
	public static final String T4 = "T4"; //123
	public static final String T5 = "T5"; //123.123
	
	//valid events
	//z (0), d (1..9), p (.), s (+, -)
	public static final String zero = "z";
	public static final String decimal = "d";
	public static final String point = "p";
	public static final String sign = "s";
	public static final String unknown = "u";
}
