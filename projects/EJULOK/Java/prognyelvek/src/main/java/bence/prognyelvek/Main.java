package bence.prognyelvek;

import java.util.Scanner;

public class Main {

    public static void main(final String[] args) {
        final StateMachineRunner runner = new StateMachineRunner(StateMachineBuilder.build());
        String input, result;

        try(Scanner sc = new Scanner(System.in)) {
            while(sc.hasNext()){
                input = sc.nextLine();
                result = runner.process(input);
                System.out.println(result);
            }
        }
    }
}
