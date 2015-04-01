package bence.prognyelvek;

public class Main {

    public static void main(final String[] args) {
        final StateMachineRunner runner = new StateMachineRunner(StateMachineBuilder.build());
        String line;

        do {
            line = System.console().readLine();

            if (line.length() != 0) {
                System.out.println(runner.process(line));
            }
        } while (line.length() != 0);
    }
}
