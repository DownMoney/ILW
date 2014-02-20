
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
  
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
 	int d,m,y;
  	HashMap<String,City> cities;
  	Vector<Transport> trans;
  	List<Event> events;
  	
  	/*public static void main(String argvs[])
  	{
  		System.setProperty("http.agent", "");
  		ApiToDB api = new ApiToDB("GB", "GBP", "en-GB");
 		api.setTime(24,2,2014);
 		Vector<Transport> all = api.getAllTrans(new City("SIP"), new TimeDate(10,10,10,2014,2,24), new TimeDate(10,10,10,2014,3,05));
  		for(Transport t: all)
  		{
  			System.out.println(t.s);
  		}
 		///api.queryRoutes("UK", "SIP", "anytime", "anytime");
  	}*/
  	
 	public void setTime(int a, int b, int c)
 	{
 		d = a;
 		m = b;
 		y = c;
 	}
 	
 	public Vector<Transport> getAllTrans(City city, TimeDate date, TimeDate enddate)
 	{
 		queryRoutes(city.name, "anywhere", date.toDateB(), enddate.toDateB());
 		HashMap<Integer,String> cities1 = new HashMap<Integer,String>();
 		HashMap<String,City> cities2 = new HashMap<String,City>(cities);
 		Set<String> keys = cities2.keySet();
 		/*for(int i = 0; i < trans.size(); i++)
		{
				if(trans.get(i).start.type == 1)
				{
					System.out.println("Remove..."+trans.get(i).cost);
					trans.remove(i);
					i--;
				}
		}*/
 		System.out.println("All keys:");
 		
 		for(String k : keys)
 		{
 			System.out.println(k);
 			if(cities2.get(k).type == 1)
 			{
 				if(!IATAcodes.giveCode(cities2.get(k).name).equals(""))
 				{
 					queryRoutes(city.name, IATAcodes.giveCode(cities2.get(k).name), date.toDateB(), enddate.toDateB());
 	 				System.out.println("CODE");
 				}
 				
 			}
 			else
 			{
 				System.out.println("City");
 				cities1.put(Integer.parseInt(k), cities.get(k).name);
 			}
 		}
 		Vector<Transport> t = new Vector<Transport>();
 		t.addAll(trans);
 		trans.clear();
 		return t;
 	}
  	
  	public ApiToDB(String country, String currency, String locale)
  	{
  		this.country = country;
  		this.currency = currency;
  		this.locale = locale;
  		cities = new HashMap<String,City>();
  		trans = new Vector<Transport>();
  		events = new Vector<Event>();
  	}
  	public void queryQuotes(String from, String to, String indate, String outdate)
  	{
  		String query = "http://partners.api.skyscanner.net/apiservices/browsequotes/v1.0/"+country+"/"+currency+"/"+locale+"/"+from+"/"+to+"/"+indate+"/"+outdate+"?apiKey="+apikey;
  		sendQuery(query);
  	}
  	public void queryRoutes(String from, String to, String indate, String outdate)
  	{
  		String query = "http://partners.api.skyscanner.net/apiservices/browseroutes/v1.0/"+country+"/"+currency+"/"+locale+"/"+from+"/"+to+"/"+indate+"/"+outdate+"?apiKey="+apikey;
  		sendQuery(query);
  	}
  	public void queryDates(String from, String to, String indate, String outdate)
  	{
  		String query = "http://partners.api.skyscanner.net/apiservices/browsedates/v1.0/"+country+"/"+currency+"/"+locale+"/"+from+"/"+to+"/"+indate+"/"+outdate+"?apiKey="+apikey;
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
  		System.out.println(query);
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
  					int type = 0;
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
  							else if(inside.item(k).getNodeName().equals("Type"))
  							{
  								if(inside.item(k).getFirstChild().getNodeValue().equals("Station"))
  								{
  									type = 0;
  									System.out.println("Rport");
  								} else {
  									type = 1;
  									System.out.println("Country");
  								}
  							}
  						}
  					}
  					if(id != null)
  					{
  						City c = new City(name);
  						c.type = type;
  						System.out.println(id+"="+c.name);
  						cities.put(id, c);
  					}
  				}
  			}
  			else if(all.item(i).getNodeName().equals("Routes"))
  			{
  				System.out.println("Routes");
  				NodeList all_ = all.item(i).getChildNodes();
  				for(int j = 0; j < all_.getLength(); j++)
  				{
  					System.out.println("Routes1");
  					String origin = null;
  					String destination = null;
  					String price = null;
  					if(all_.item(j).getChildNodes().getLength() > 1)
  					{
  						System.out.println("Routes2");
  						NodeList inside = all_.item(j).getChildNodes();
  						for(int k = 0; k < inside.getLength(); k++)
  						{
  							System.out.println("Routes3");//+inside.item(k).getNodeName());
  							if(inside.item(k).getNodeName().equals("OriginId"))
  							{
  								origin = inside.item(k).getFirstChild().getNodeValue();
  							}
  							else if(inside.item(k).getNodeName().equals("DestinationId"))
  							{
  								destination = inside.item(k).getFirstChild().getNodeValue();
  							}
  							else if(inside.item(k).getNodeName().equals("Price"))
  							{
  								if(inside.item(k).getChildNodes().getLength() > 0)
  								{
  									price = inside.item(k).getFirstChild().getNodeValue();
  								} else {
  									price = "1";
  								}
  								
  							}
  						}
  					}
  					if(origin != null)
  					{
  						Transport c = new Transport(Integer.parseInt(origin), Integer.parseInt(destination), Double.parseDouble(price));
  						System.out.println("Got trans!");
  						c.type = TransType.FLIGHT;
 						Random rnd = new Random();
 						int day = d+rnd.nextInt(1);
 						int h = rnd.nextInt(19);
 						c.out = new TimeDate(h, rnd.nextInt(59), 0, y, m, day);
 						c.in = new TimeDate(h+2, rnd.nextInt(59), 0, y,m, day);
  						trans.add(c);
  					}
  				}
  			}
  				
  		}
  		
  		for(int i = 0; i < trans.size(); i++)
  		{
  			System.out.println("SIZE");
  			if(trans.get(i).start == null)
  			{
  				int s = trans.get(i).s;
  	  			int e = trans.get(i).e;
  	  			//System.out.println("Updating "+ s + " to " + cities.get(s).name);
  	  			trans.get(i).update(cities.get(s), cities.get(e));	
  			}
  		}
  		
  	}
  }