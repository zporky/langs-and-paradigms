package bead01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		FileInputStream file;
		
		try {
			file = new FileInputStream(args[0]);
		} catch (FileNotFoundException e) {
			System.exit(-1);
			return;
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(file));
		String ln = null;
		
		try {
			while ((ln = br.readLine()) != null ) {
				String Result = Parser.Parse(ln);
				System.out.println(Result);
			}
			
			br.close();
		} catch ( IOException e ) {
			System.exit(-1);
			return;
		}
	}

}
