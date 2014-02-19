import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ApiToDB {
	private String apikey = "ilw03824094427015676662223000993";
	String type = "application/x-www-form-urlencoded";
	String currency;
	String locale;
	String country;
	HashMap<String,City> cities;
	List<Transport> trans;
	List<Event> events;
	
	public static void main(String argvs[])
	{
		System.setProperty("http.agent", "");
		ApiToDB api = new ApiToDB("GB", "GBP", "en-GB");
		api.queryQuotes("EDI", "SIP", "anytime", "anytime");
	}
	
	
	public ApiToDB(String country, String currency, String locale)
	{
		this.country = country;
		this.currency = currency;
		this.locale = locale;
		cities = new HashMap<String,City>();
		//trans = new List<Transport>();
		//events = new List<Event>();
	}
	public void queryQuotes(String from, String to, String indate, String outdate)
	{
		String query = "http://partners.api.skyscanner.net/apiservices/browsequotes/v1.0/"+country+"/"+currency+"/"+locale+"/"+from+"/"+to+"/"+indate+"/"+outdate+"?apiKey="+apikey;
		sendQuery(query);
	}
	public void queryRoads(String from, String to, String indate, String outdate)
	{
		String query = "http://partners.api.skyscanner.net/apiservices/browseroutes/v1.0/"+country+"/"+currency+"/"+locale+"/"+from+"/"+to+"/"+indate+"/"+outdate+"?apiKey="+apikey;
		sendQuery(query);
	}
	public void sendQuery(String query)
	{
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		URL url = null;
		URLConnection con = null;
		String xml = "";
		try {
			url = new URL(query);
			con = url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (ILW DATA HACK)");
			con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			db = dbf.newDocumentBuilder();
			con.connect();
			InputStream is = con.getInputStream();
			doc = db.parse(is);
			analyzeXML(doc);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void analyzeXML(Document xml)
	{
		System.out.println("Analzye");
		NodeList all = xml.getFirstChild().getChildNodes();
		for(int i = 0; i < all.getLength(); i++)
		{
			if(all.item(i).getNodeName().equals("Currency"))
			{
				//do nothing
			}
			else if(all.item(i).getNodeName().equals("Quotes"))
			{
				NodeList all_ = all.item(i).getChildNodes();
				for(int j = 0; j < all_.getLength(); j++)
				{
					
				}
			}
			else if(all.item(i).getNodeName().equals("Dates"))
			{
				NodeList all_ = all.item(i).getChildNodes();
				for(int j = 0; j < all_.getLength(); j++)
				{
					
				}
			}
			else if(all.item(i).getNodeName().equals("Places"))
			{
				NodeList all_ = all.item(i).getChildNodes();
				for(int j = 0; j < all_.getLength(); j++)
				{
					String id = null;
					String name = null;
					if(all_.item(j).getChildNodes().getLength() > 1)
					{
						NodeList inside = all_.item(j).getChildNodes();
						for(int k = 0; k < inside.getLength(); k++)
						{
							if(inside.item(k).getNodeName().equals("PlaceId"))
							{
								id = inside.item(k).getFirstChild().getNodeValue();
							}
							else if(inside.item(k).getNodeName().equals("Name"))
							{
								name = inside.item(k).getFirstChild().getNodeValue();
							}
						}
					}
					if(id != null)
					{
						City c = new City(name);
						System.out.println(id+"="+c.name);
						cities.put(id, c);
					}
				}
			}
			else if(all.item(i).getNodeName().equals("Routes"))
			{
				NodeList all_ = all.item(i).getChildNodes();
				for(int j = 0; j < all_.getLength(); j++)
				{
					String origin = null;
					String destination = null;
					String price = null;
				}
			}
				
		}
	}
}