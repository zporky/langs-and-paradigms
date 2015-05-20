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
			return;
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(file));
		
		String line = null;
		try {
			while ((line = br.readLine()) != null ) {
				System.out.println(Parser.Parse(line));
		
			}
		} catch ( IOException e ) {
			return;
		}
		
		try {
			br.close();
		} catch ( IOException e ) {
			return;
		}
	}

}
