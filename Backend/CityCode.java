import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class CityCode {
	public static HashMap<String, String> getCodes() {
		HashMap<String, String> codes = new HashMap<String, String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"airportCodes.txt"));
			String line = br.readLine();
			while (line != null) {
				String[] t = line.split(",");
				if (t.length == 2) {
					codes.put(t[1], t[0]);
				}
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return codes;
	}
}
