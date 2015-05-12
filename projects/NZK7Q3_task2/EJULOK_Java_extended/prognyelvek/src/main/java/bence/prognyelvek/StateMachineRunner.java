package bence.prognyelvek;

import java.util.List;

public class StateMachineRunner {

    private final static String ANSWER_OK = "OK";

    private final static String ANSWER_FAIL = "FAIL";

    private final StateMachine<String, Character, Character> stateMachine;

    public StateMachineRunner(final StateMachine<String, Character, Character> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public String process(final String input) {
        final StringBuilder result = new StringBuilder();

        try {
            final List<Character> output = stateMachine.process(input);
            result.append(ANSWER_OK).append(' ');

            for(final Character character : output) {
                result.append(character);
            }

        } catch (IllegalStateException e) {
            result.append(ANSWER_FAIL);
        }

        return result.toString();
    }
}
