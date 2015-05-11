
public class Machine {
	State state;
	double value;
	int sign;
	double wholeWeight;
	double partialWeight;
	
	boolean push(char symbol) {
		boolean s = symbol == '+' || symbol == '-';
		boolean p = symbol == '.';
		boolean z = symbol == '0';
		boolean d = symbol >= '1' && symbol <= '9';

		if (symbol == '-') {
			sign = -1;
		}

		if (p) {
			wholeWeight = 1;
			partialWeight = 0.1;
		}

		if (d) {
			value = value*wholeWeight + sign*(symbol - '0')*partialWeight;

			if (wholeWeight == 1) {
				partialWeight *= 0.1;
			}
		}

		if (state == State.S && !s) {
			state = State.S1;
		}

		switch (state)
		{
		case S:
			state = s || p || z || d ? State.S1 : State.E;
			break;
		case S1:
			state = p ? State.I0 : z ? State.T2 : d ? State.T4 : State.E;
			break;
		case I0:
			state = d ? State.T1 : State.E;
			break;
		case T1:
			state = d ? State.T1 : State.E;
			break;
		case T2:
			state = p ? State.T3 : State.E;
			break;
		case T3:
			state = d ? State.T3 : State.E;
			break;
		case T4:
			state = d ? State.T4 : p ? State.T5 : State.E;
			break;
		case T5:
			state = d ? State.T5 : State.E;
			break;
		default:
			state = State.E;
			break;
		}

		return state != State.E;
	}
	
	public Machine() {
		init();
	}
	
	public void init() {
		state = State.S;
		value = 0;
		sign = 1;
		wholeWeight = 10;
		partialWeight = 1;
	}
	
	public boolean check(String s) {
		boolean res = true;

		for (int i = 0; i < s.length(); i++) {
			if (!push(s.charAt(i))) {
				res = false;
				break;
			}
		}

		return res && (state == State.T1 || state == State.T2 || 
				state == State.T3 || state == State.T4 || state == State.T5);
	}
	
	public double getValue() {
		return value; 
	}	
}
