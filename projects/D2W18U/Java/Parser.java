package bead01;

public class Parser {
	private enum Rule {
		S, S2, l0, T1, T2, T3, T4, T5
	}
	
	private static Tokenizer t;
	private static Rule currentRule;
	private static boolean isSigned;
		
	// S -> S' | sS'
	private static void S(Terminal input) {
		if (input == Terminal.s) {
			isSigned = true;
		}		
		currentRule = Rule.S2;
	}

	// S' -> pl0 | zT2 | dT4
	private static void S2(Terminal input) {
		switch (input) {
		case p:
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

	// T1 -> dT1 | e
	private static void T1(Terminal input) {
		if (input == Terminal.d) {
			currentRule = Rule.T1;
		} else {
			throw new InvalidValueException();
		}
	}

	// T2 -> pT2 | e
	private static void T2(Terminal input) {
		if (input == Terminal.p) {
			currentRule = Rule.T3;
		} else {
			throw new InvalidValueException();
		}
	}

	// T3 -> dT3 | e
	private static void T3(Terminal input)   {
		if (input == Terminal.d) {
			currentRule = Rule.T3;
		} else {
			throw new InvalidValueException();
		}
	}
	
	// T4 -> dT4 | pT5 | e
	private static void T4(Terminal input)   {
		switch (input) {
		case d:
			currentRule = Rule.T4;
			break;
		case p:
			currentRule = Rule.T5;
			break;
		default:
			throw new InvalidValueException();
		}
	}
	
	// T5 -> dT5 | e
	private static void T5(Terminal input)  {
		if (input == Terminal.d) {
			currentRule = Rule.T5;
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
		case T5:
			T5(input); break;
		default:
			throw new InvalidValueException();
		}
	}
	
	public static String result() {
		String output = isSigned ? t.getStr().substring(1) : t.getStr();
		switch(currentRule) {
		case T2: case T3: case T5:
			return "OK ".concat(output);
		case T1:
			return "OK 0".concat(output);
		case T4:
			return "OK ".concat(output.concat(".0"));
		default:
			// there is no N -> e rule type rule in the currentRule
			return "FAIL";
		}
	}
	
	public static String Parse(String input) {
		t = new Tokenizer(input);
		isSigned = false;
		t.First();
		
		try {		
			currentRule = Rule.S;
			useRule(t.Current());
			
			// S rule decides whether the input is signed or not
			if (isSigned) {
				t.Next();
			}
			
			while(!t.End()) {
				useRule(t.Current());
				t.Next();
			}
		} catch ( InvalidValueException e) {
			return "FAIL";
		}

		return result();
	}
	
	public static void main(String[] args) {
		System.out.println(Parse("asd"));
	}

}
