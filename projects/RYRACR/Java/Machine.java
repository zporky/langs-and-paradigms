import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Machine {

	public static void validateInput(String input) throws InvalidTransationException {
		State currentState = State.START;
		for (Character c : input.toCharArray()) {
			Transition transation = Transition.getTransation(c);
			currentState = ProductionRules.getNextState(currentState, transation);
		}
		if (!currentState.isTerminating()) {
			throw new InvalidTransationException("Premature end of input");
		}
	}

	private static enum State {
		START(false),
		S_(false),
		I0(false),
		T1(true),
		T2(true),
		T3(true),
		T4(true),
		T5(true);

		private final boolean terminating;

		State(boolean terminating) {
			this.terminating = terminating;
		}

		public boolean isTerminating() {
			return terminating;
		}

	}

	private static enum Transition {
		s('+', '-'),
		p('.'),
		z('0'),
		d('1', '2', '3', '4', '5', '6', '7', '8', '9');

		private Character[] validValues; // could be regular expression

		Transition(Character... accepting) {
			this.validValues = accepting;
		}

		public boolean matches(Character c) {
			return Arrays.asList(validValues).contains(c);
		}

		// Luckily there is always one matching Translation, hence returning a
		// single one. However it's easy to extend...
		public static Transition getTransation(Character c) throws InvalidTransationException {
			for (Transition t : Transition.values()) {
				if (t.matches(c)) {
					return t;
				};
			}
			throw new InvalidTransationException("Input is not in alphabet.");
		}

	}

	private static class ProductionRules {

		// Double keyed map class should be created...
		private static Map<State, Map<Transition, State>> rules = new HashMap<>();

		static {
			for (State s : State.values()) {
				rules.put(s, new HashMap<Transition, State>());
			}
			rules.get(State.START).put(Transition.p, State.I0);
			rules.get(State.START).put(Transition.z, State.T2);
			rules.get(State.START).put(Transition.d, State.T4);
			rules.get(State.START).put(Transition.s, State.S_);

			rules.get(State.S_).put(Transition.p, State.I0);
			rules.get(State.S_).put(Transition.z, State.T2);
			rules.get(State.S_).put(Transition.d, State.T4);

			rules.get(State.I0).put(Transition.d, State.T1);
			rules.get(State.T2).put(Transition.p, State.T3);
			rules.get(State.T4).put(Transition.p, State.T5);
			rules.get(State.T4).put(Transition.d, State.T4);

			rules.get(State.T1).put(Transition.d, State.T1);
			rules.get(State.T3).put(Transition.d, State.T3);
			rules.get(State.T5).put(Transition.d, State.T5);

		}

		public static State getNextState(State currentState, Transition transation) throws InvalidTransationException {
			State state = rules.get(currentState).get(transation);
			if (state == null) {
				throw new InvalidTransationException(currentState + " state doesn't accept transation " + transation);
			}
			return state;
		}

	}

	public static class InvalidTransationException extends Exception {
		private static final long serialVersionUID = 1L;

		public InvalidTransationException(String message) {
			super(message);
		}
	}

}
