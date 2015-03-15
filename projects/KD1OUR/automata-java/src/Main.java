import statemachine.StateMachine;
import statemachine.model.ProductionRules;
import statemachine.model.StateMachineState;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try(Scanner sc = new Scanner(System.in)) {
            StateMachine sm = new StateMachine(StateMachineState.START_SIGNED, ProductionRules.RULES);
            String input, result;
            while(sc.hasNext()){
                input = sc.nextLine();
                result = sm.parse(input);
                System.out.println(result);
            }
        }

    }
}