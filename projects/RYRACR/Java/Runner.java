import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Runner {

	/**
	 * @param args first element needs to be the input file
	 */
	public static void main(String[] args) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(args[0]);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find input file");
			System.exit(-1);
			return;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				try {
					Machine.validateInput(line);
				} catch (Machine.InvalidTransationException e) {
					System.out.println("FAIL " + line);
					continue;
				}
				System.out.println("OK " + line);
			}
		} catch (IOException e) {
			System.err.println("Error while reading file.");
			System.exit(-2);
		}

		try {
			br.close();
		} catch (IOException e) {
			System.err.println("Could not close file...");
		}

	}

}
