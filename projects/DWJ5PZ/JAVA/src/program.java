import java.util.Scanner;
import java.text.DecimalFormat;

public class program {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		DecimalFormat df = new DecimalFormat("0.0######");
		
		String s = null;
		while (in.hasNextLine()) {
			s = in.nextLine();

			if (s != null)
			{
				Machine machine = new Machine();

				if (machine.check(s)) {
					String formatted = df.format(machine.getValue()); 
					System.out.format("OK %s%n", formatted);
				}
				else {
					System.out.println("FAIL");
				}
			}
		}
		
		in.close();
	}	
}
