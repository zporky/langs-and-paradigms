import statemachine.java.StateMachine;
import statemachine.java.model.ProductionRules;
import statemachine.java.model.StateMachineState;

import java.util.Scanner;

public class Main {

    public static StateMachine stateMachine = new StateMachine(StateMachineState.START_SIGNED, ProductionRules.RULES);

    public static void main(String[] args) {

        try(Scanner sc = new Scanner(System.in)) {
            while(sc.hasNext()){
                final String input = sc.nextLine();
                final String result = stateMachine.parse(input);
                System.out.println(result);
            }
        }
    }
}