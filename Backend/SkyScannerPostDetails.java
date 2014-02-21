import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;


public class SkyScannerPostDetails {
	private String apikey = "ilw03824094427015676662223000993";
	String type = "application/x-www-form-urlencoded";
	String currency;
	String locale;
	String country;
	public static void main(String argvs[])
	{
		System.setProperty("http.agent", "");
		SkyScannerPostDetails api = new SkyScannerPostDetails("GB", "GBP", "en-GB");
		api.get("edi", "lon", "2014-04-04");
	}
	public SkyScannerPostDetails(String country, String locale, String currency)
	{
		this.country = country;
		this.currency = currency;
		this.locale = locale;
	}
	public String get(String origin, String dest, String outbounddate)
	{
		String data = "locationschema=iata&cabinclass=economy&adults=1&apikey="+apikey+"&country="+country+"&currency="+currency+"&locale="+locale+"&originplace="+origin+
				"&destinationplace="+dest+"&outbounddate="+outbounddate;
		URL url = null;
		BufferedReader reader = null;
		HttpURLConnection con = null;
		OutputStream os = null;
		String line;
		try {
			//data = URLEncoder.encode(data, "utf-8");
			url = new URL("http://partners.api.skyscanner.net/apiservices/pricing/v1.0");
			con = (HttpURLConnection) url.openConnection();
		    con.setDoOutput(true);
		    con.setRequestMethod("POST");
		    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Konrad was nie lubi)");
		    con.setRequestProperty("Content-Length",  String.valueOf(data.length()));
		    con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		
		    os = con.getOutputStream();
	        os.write(data.getBytes());
			
			//writer.write(data);
			//writer.flush();
			//con.connect();
			//System.out.println(con.getResponseCode());
		} catch(Exception e)
		{
			System.out.println("Erra2");
		}
		try {
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			while ((line = reader.readLine()) != null) {
			    //System.out.println(line);
			}
			
			os.close();
			reader.close(); 
		} catch(Exception e)
		{
			System.out.println("Erra!"+e.getMessage());
		}
		return "";
	}
}
