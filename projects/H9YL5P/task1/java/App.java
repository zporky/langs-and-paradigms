package finiteStateMachine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//import StateMachine;
//import FloatConstants;

public class App {

	public static void main(String[] args) {
		int p;
		
		StateMachine m1 = new StateMachine();
		String input = "";
		String event = "";
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		input = "";
		try {
			while ((input = bufferRead.readLine())!= null) {
				if (input.length() >0) {    
					m1.init(input.substring(0,1));
				    for (int i=0; i<input.length(); i++) {
				    	//System.out.println("Input symbol: ."+input.substring(i,i+1)+".");
				    	event = m1.processInput(input.substring(i, i+1));
				    	if (event == FloatConstants.unknown) {m1.setState(FloatConstants.I);break;}
				    	p=m1.nextState(event, input.substring(i,i+1));
				    	if (p<0) {m1.setState(FloatConstants.I);break;}
				    	//System.out.println("Current state: "+m1.getState());
				    }
				    if (m1.getState().substring(0,1).equals("T")) {
				    	System.out.println("OK "+m1.getNumber());
				    } else { 
				    	System.out.println("FAIL");
				    }
				}
			}
			bufferRead.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		
	}

}
