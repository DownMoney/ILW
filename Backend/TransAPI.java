import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class TransAPI {
	public final static String SKYSCANNER = "http://partners.api.skyscanner.net/apiservices/browsequotes/v1.0/GB/GBP/en-GB/";
	public final static String KEY = "ilw03824094427015676662223000993";
	public final static int N = 50;
	
	public static Vector<Transport> getAllTrans(City c, String code,
			TimeDate s, TimeDate e) {
		String query = SKYSCANNER + "/" + code + "/anywhere/" + s.toDateB()
				+ "/" + e.toDateB() + "?apiKey=" + KEY;
		Document doc = getDoc(query);
		Vector<Transport> trans;
		if (doc == null)
			return null;
		trans = parseXml(doc);
		//System.out.println("Hey: " + trans.size());
		return trans;
	}

	private static Document getDoc(String q) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		URL url;
		URLConnection con;
		// System.out.println(q);
		try {
			url = new URL(q);
			con = url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (ILW DATA HACK)");
			con.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			db = dbf.newDocumentBuilder();
			con.connect();
			InputStream in = con.getInputStream();
			doc = db.parse(in);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		return doc;
	}

	private static Vector<Transport> parseXml(Document doc) {
		Vector<Transport> trans = new Vector<Transport>();
		NodeList topNodes = doc.getFirstChild().getChildNodes();
		HashMap<Integer, City> cities = new HashMap<Integer, City>();
		NodeList places = null;
		NodeList flights = null;
		for (int i = 0; i < topNodes.getLength(); i++) {
			if (topNodes.item(i).getNodeName().equals("Places")) {
				places = topNodes.item(i).getChildNodes();
			} else if (topNodes.item(i).getNodeName().equals("Quotes")) {
				flights = topNodes.item(i).getChildNodes();
			}
		}
		if (places == null)
			return trans;
		if (flights == null)
			return trans;
		for (int i = 0; i < places.getLength(); i++) {
			NodeList params = places.item(i).getChildNodes();
			String type = "";
			int id = 0;
			String name = "";
			String code = "";
			for (int e = 0; e < params.getLength(); e++) {
				if (params.item(e).getNodeName().equals("Type")) {
					type = getValue(params.item(e).getChildNodes());
				} else if (params.item(e).getNodeName().equals("PlaceId")) {
					id = Integer.parseInt(getValue(params.item(e)
							.getChildNodes()));
				} else if (params.item(e).getNodeName().equals("IataCode")) {
					code = getValue(params.item(e).getChildNodes());
				} else if (params.item(e).getNodeName().equals("Name")) {
					name = getValue(params.item(e).getChildNodes());
				}
			}
			if (type.equals("Station")) {
				// System.out.println("Added: " + name + " " + id + " " + code);
				City c = new City(name);
				c.code = code;
				cities.put(id, c);
			}
		}
		for (int i = 0; i < flights.getLength(); i++) {
			if (flights.item(i).getNodeName().equals("QuoteDto")) {
				NodeList data = flights.item(i).getChildNodes();
				double cost = 50;
				boolean direct = false;
				for (int e = 0; e < data.getLength(); e++) {
					if (data.item(e).getNodeName().equals("MinPrice")) {
						cost = Double.parseDouble(getValue(data.item(e)
								.getChildNodes()));
					} else if (data.item(e).getNodeName().equals("Direct")) {
						direct = getValue(data.item(e).getChildNodes()).equals(
								"true");
					} else if (data.item(e).getNodeName().equals("OutboundLeg")) {
						NodeList params = data.item(e).getChildNodes();
						int ido = 0;
						int idd = 0;
						String date = "";
						for (int j = 0; j < params.getLength(); j++) {
							if (params.item(j).getNodeName().equals("OriginId")) {
								ido = Integer.parseInt(getValue(params.item(j)
										.getChildNodes()));
							} else if (params.item(j).getNodeName()
									.equals("DestinationId")) {
								idd = Integer.parseInt(getValue(params.item(j)
										.getChildNodes()));
							} else if (params.item(j).getNodeName()
									.equals("DepartureDate")) {
								date = getValue(params.item(j).getChildNodes());
							}
						}
						if (!date.equals("") && ido != 0 && idd != 0 && direct) {
							TimeDate start = fromSC(date);
							TimeDate end = start.addH(2);
							City out = cities.get(ido);
							City in = cities.get(idd);
							if (start != null && end != null && out != null
									&& in != null) {
								trans.add(new Transport(out, in, start, end,
										cost));
							}
							if(trans.size() >= N){
								break;
							}
						}
					}
				}
			}
		}
		return trans;
	}

	public static String getValue(NodeList n) {
		String s = "";
		for (int i = 0; i < n.getLength(); i++) {
			s += n.item(i).getNodeValue();
		}
		s = s.replace("\n", "");
		return s;
	}

	public static TimeDate fromSC(String t) {
		int y = Integer.parseInt(t.substring(0, 4));
		int mon = Integer.parseInt(t.substring(5, 7));
		int d = Integer.parseInt(t.substring(8, 10));
		Random r = new Random();
		int h = r.nextInt(24);
		int min = r.nextInt(60);
		int s = r.nextInt(60);
		return new TimeDate(h, min, s, y, mon, d);

	}
}
