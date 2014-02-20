import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class EventAPI {
	private static final String KEY = "vd93CbRJDN8TgsPG";
	private static final String eventful = "http://api.evdb.com/rest/events/search?";

	public static Vector<Event> getEvents(City city, TimeDate start,
			TimeDate end) {
		if(city.events != null){
			return getAfter(city.events, start);
		}
		String query = eventful + "app_key=" + KEY + "&" + "location="
				+ getCityName(city) + "&" + "date=" + getDate(start, end);
		Document doc = getDoc(query);
		Vector<Event> events;
		events = fromXml(doc, city);
		city.events = events;
		return events;

	}
	
	public static Vector<Event> getAfter(Vector<Event> evs, TimeDate t){
		Vector<Event> res = new Vector<Event>();
		for(Event ei : evs){
			if(ei.start.after(t)){
				res.add(ei);
			}
		}
		return res;
	}
	
	private static String getCityName(City c) {
		String s = c.name;
		s = s.replace(" ", "+");
		return s;
	}

	private static String getDate(TimeDate s, TimeDate e) {
		return String.format("%s00-%s00", s.toDate(), e.toDate());
	}

	private static Document getDoc(String q) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		URL url;
		URLConnection con;
		System.out.println(q);
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
			System.out.println("Hi");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return doc;
	}

	public static Vector<Event> fromXml(Document doc, City c) {
		Vector<Event> events = new Vector<Event>();
		NodeList nodes = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeName().equals("total_items")) {
				// events.setSize(Integer.parseInt(nodes.item(i).getFirstChild().getNodeValue()));
			}
			if (nodes.item(i).getNodeName().equals("events")) {
				NodeList evs = nodes.item(i).getChildNodes();
				for (int e = 0; e < evs.getLength(); e++) {
					if (evs.item(e).getNodeName().equals("event")) {
						NodeList info = evs.item(e).getChildNodes();
						String name = "";
						String desc = "";
						TimeDate start = null;
						TimeDate end = null;
						City city = c;
						double cost = 0;
						int pop = 0;
						double lat = 0;
						double lon = 0;
						String postal = "";
						String url = "";
						String id = "";
						int ad = 0;
						id = evs.item(e).getAttributes().getNamedItem("id")
								.getNodeValue();
						for (int j = 0; j < info.getLength(); j++) {
							if (info.item(j).getNodeName().equals("title")) {
								name = getValue(info.item(j).getChildNodes());
							} else if (info.item(j).getNodeName().equals("url")) {
								url = getValue(info.item(j).getChildNodes());
							} else if (info.item(j).getNodeName()
									.equals("description")) {
								desc = getValue(info.item(j).getChildNodes());
							} else if (info.item(j).getNodeName()
									.equals("start_time")) {
								String t = getValue(info.item(j)
										.getChildNodes());
								if (t.equals("") || t == null) {
									start = null;
								} else {
									start = new TimeDate(t);
								}
							} else if (info.item(j).getNodeName()
									.equals("stop_time")) {
								String t = getValue(info.item(j)
										.getChildNodes());
								if (t.equals("") || t == null) {
									end = null;
								} else {
									end = new TimeDate(t);
								}
							} else if (info.item(j).getNodeName()
									.equals("city_name")) {

							} else if (info.item(j).getNodeName()
									.equals("latitude")) {
								lat = Double.parseDouble(getValue(info.item(j)
										.getChildNodes()));
							} else if (info.item(j).getNodeName()
									.equals("longitude")) {
								lon = Double.parseDouble(getValue(info.item(j)
										.getChildNodes()));
							} else if (info.item(j).getNodeName()
									.equals("postal_code")) {
								postal = getValue(info.item(j).getChildNodes());
							} else if (info.item(j).getNodeName()
									.equals("all_day")) {
								ad = Integer.parseInt(getValue(info.item(j)
										.getChildNodes()));
							}
						}
						System.out.println(name);
						events.add(new Event(name, desc, start, end, city,
								cost, pop, lat, lon, postal, url, id, ad));
					}
				}
			}
		}

		return events;
	}

	public static String getValue(NodeList n) {
		String s = "";
		for (int i = 0; i < n.getLength(); i++) {
			s += n.item(i).getNodeValue();
		}
		s = s.replace("\n", "");
		return s;
	}
}
