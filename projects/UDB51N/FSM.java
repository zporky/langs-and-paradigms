import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FSM {
	public static void main (String[] args){
		try {
			List<List<Character>> lines = read(args[0]);
			processContent(lines);
		} catch (IOException e) {
			System.out.println("Missing input file!");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Missing input file! Give it as command line argument!");
		}		
	}
	
	private static boolean isS (char c) {
		return c == '+' || c == '-';
	}
	
	private static boolean isP (char c) {
		return c == '.';
	}
	
	private static boolean isZ (char c) {
		return c == '0';
	}
	
	private static boolean isD (char c) {
		List<Character> numbers = new ArrayList<Character>();
		Collections.addAll(numbers, '1', '2', '3', '4', '5', '6', '7', '8', '9');
		return numbers.contains(c);
	}

	// State-transition function
	// params:	state	- current state
	//			c		- input character
	// return:	next state
	private static int transitions (int state, char c) {
		if (state == 0 && isS(c)) return 1;
		else if (state == 1) {
			if (isP(c)) return 2;
			else if (isZ(c)) return 4;
			else if (isD(c)) return 6;
			else return 8;
		}
		else if (state == 2) {
			if (isD(c)) return 3;
			else return 8;
		}
		
		else if (state == 3) {
			if (isD(c)) return 3;
			else return 8;
		}
		
		else if (state == 4) {
			if (isP(c)) return 5;
			else return 8;
		}
		
		else if (state == 5) {
			if (isD(c)) return 5;
			else return 8;
		}
		
		else if (state == 6) {
			if (isD(c)) return 6;
			else if (isP(c)) return 7;
			else return 8;
		}
		
		else if (state == 7) {
			if (isD(c)) return 7;
			else return 8;
		}
		
		else return 8;
	}
	
	// Get the content of the input file as list of lines
	// param: 	filename
	// return:	list of lines
	private static List<List<Character>> read(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<List<Character>> result = new ArrayList<List<Character>>();
	    try {
	        String line = br.readLine();
	        while (line != null) {
	        	StringBuilder sb = new StringBuilder();
	            sb.append(line);
	            List<Character> val = new ArrayList<Character>();
	            for (Character c : sb.toString().toCharArray()) {
	            	val.add(c);
	            }
	            result.add(val);
	            line = br.readLine();
	        }
	    } finally {
	        br.close();
	    }
	    return result;
	}
	
	// This method chooses the start state and call the processLine method for every line
	// param:	list of lines
	private static void processContent(List<List<Character>> lines) {
		for (List<Character> line : lines) {
			int state;
			if (isS(line.get(0))) state = 0;
			else state = 1;
			List<Integer> states = new ArrayList<Integer>();
			states.add(state);
			for (int i=0; i<line.size(); ++i) {
				state = transitions(state, line.get(i));
				states.add(state);
			}
			processLine(states, line);			
		}
	}
	
	// This method analyses the states sequence: accepts or rejects the input, and writes the result to the console
	// params:	states	- the current line's states sequence
	//			line 	- the current line as list of characters
	private static void processLine(List<Integer> states, List<Character> line){
		StringBuilder sb = new StringBuilder();		
		int last = states.size() - 1;
		if (states.get(last) == 1 || states.get(last) == 2 || states.get(last) == 8) {
			sb.append("FAIL");
			sb.append(System.getProperty("line.separator"));
		}
		else {
			sb.append( "OK ");
			if (states.get(0) == 0) line.remove(0);
			if (states.get(0) == 1 && states.get(1) == 2) sb.append('0');
			for (Character c : line) {
				sb.append(c);
			}
			if (states.get(last) == 6) sb.append( ".0");
			sb.append(System.getProperty("line.separator"));
		}
		System.out.print(sb.toString());
	}	
}
