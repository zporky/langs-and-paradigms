package bead01;

public class Parser {
		
	private enum Rule {
		S, S2, l0, l1, T1, T2, T3, T4, E, E2 
	}
	
	private static Tokenizer t;
	private static Rule currentRule;
		
	// S -> S' | sS'
	private static void S(Terminal input) {
		if ( input == Terminal.s )
			t.RemoveLeadSign();
		currentRule = Rule.S2;
	}

	// S' -> pl0 | zT2 | dT4
	private static void S2(Terminal input) {
		switch (input) {
		case p:
			t.AddLeadZero();
			t.Next();
			currentRule = Rule.l0;			
			break;
		case z:
			currentRule = Rule.T2;
			break;
		case d:
			currentRule = Rule.T4;
			break;
		default:
			throw new InvalidValueException();
		}
	}

	// l0 -> dT1
	private static void l0(Terminal input) {
		if (input == Terminal.d) {
			currentRule = Rule.T1;
		} else {
			throw new InvalidValueException();
		}		
		
	}

	// T1 -> dT1 | xE | e
	private static void T1(Terminal input) {
		switch(input) {
		case d:
			currentRule = Rule.T1;
			break;
		case x:
			currentRule = Rule.E;
			break;
		default:
			throw new InvalidValueException();
		}
	}

	// T2 -> pT3 | xE | e
	private static void T2(Terminal input) {
		switch(input) {
		case p:
			currentRule = Rule.T3;
			break;
		case x:
			currentRule = Rule.E;
			break;
		default:
			throw new InvalidValueException();
		}
	}

	// T3 -> dT1 | e
	private static void T3(Terminal input)   {
		if (input == Terminal.d) {
			currentRule = Rule.T1;
		} else {
			throw new InvalidValueException();
		}
	}
	
	// T4 -> dT4 | pT3 | xE | e
	private static void T4(Terminal input)   {
		switch (input) {
		case d:
			currentRule = Rule.T4;
			break;
		case p:
			currentRule = Rule.T3;
			break;
		case x:
			currentRule = Rule.E;
			break;
		default:
			throw new InvalidValueException();
		}
	}
	
	// E  -> sl1 | l1
	private static void E(Terminal input) {
		if (input != Terminal.s)
			t.AddESign();
			
		currentRule = Rule.l1;	
	}
	
	// l1 -> dE'
	private static void l1(Terminal input) {
		if (input == Terminal.d) {
			currentRule = Rule.E2;
		} else {
			throw new InvalidValueException();
		}		
	}
	
	// E' -> dE' | e
	private static void E2(Terminal input)   {
		if (input == Terminal.d) {
			currentRule = Rule.E2;
		} else {
			throw new InvalidValueException();
		}
	}
	
	private static void useRule(Terminal input) {
		switch(currentRule) {
		case S:
			S(input); break;
		case S2:
			S2(input); break;
		case l0:
			l0(input); break;
		case T1:
			T1(input); break;
		case T2:
			T2(input); break;
		case T3:
			T3(input); break;
		case T4:
			T4(input); break;
		case E:
			E(input); break;
		case l1:
			l1(input); break;
		case E2:
			E2(input); break;
		default:
			throw new InvalidValueException();
		}
	}
	
	public static String getResult() {
		switch(currentRule) {
		case T4:
			t.AddEndZero();
		case T1: case T2: case T3: case E2:
			return "OK "+t.getStr();
		default:
			return "FAIL";
		}
		
	}
	
	public static String Parse(String input) {
		t = new Tokenizer(input);
		t.First();
		currentRule = Rule.S;
		try {
			
			useRule(t.Current()); // use S rule
			
			for(;!t.End();t.Next())
				useRule(t.Current());
			
			return getResult();
		} catch ( InvalidValueException e) {
			return "FAIL";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Parse("2.4E-5"));
	}

}