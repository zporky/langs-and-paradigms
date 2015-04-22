package validate;

import java.util.Scanner;

public class Float {

    public static void main(String[] args) {

        FloatFSM fsm = new FloatFSM();
        Scanner s = new Scanner(System.in);

        while (s.hasNext()) {
            String str = s.next();
            fsm.validate(str);
            System.out.println(str + "\t" + fsm.result);
        }
    }

}
