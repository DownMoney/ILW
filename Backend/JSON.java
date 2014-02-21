import java.util.Vector;

public class JSON {
	public static String toJSON(Vector<Route> routes) {
		String res = "";
		StringBuffer buff = new StringBuffer();
		buff.append("[");
		for (int i = 0; i < routes.size(); i++) {
			buff.append(toJSON(routes.get(i), i));
			if(i != routes.size() - 1){
				buff.append(",");
			}
		}
		buff.append("]");
		res = buff.toString();
		return res;
	}

	public static String toJSON(Route r, int index) {
		String res = "";
		StringBuffer buff = new StringBuffer();
		buff.append(String.format("'%d':[", index));
		for (int i = 0; i < r.size; i++) {
			buff.append("{");
			String s = "";
			if (r.isCity(i)) {
				s = toJSON((City) r.getAt(i));
			} else if (r.isEvent(i)) {
				s = toJSON((Event) r.getAt(i));
			} else {
				s = toJSON((Transport) r.getAt(i));
			}
			buff.append(s);
			buff.append("}");
			if(i != r.size - 1){
				buff.append(",");
			}
		}
		buff.append("]");
		res = buff.toString();
		return res;
	}

	public static String toJSON(City c) {
		return String.format("'type' : 'city', 'name' : '%s'", c.name);
	}

	public static String toJSON(Event e) {
		return String
				.format("'type' : 'event', 'name':'%s', 'desc' : '%s', 'postal_code':'%s', 'lon':'%f', 'lat':'%f', 'start':'%s', 'end':'%s', 'id':'%s', 'url':'%s'",
						e.name, e.desc, e.postal, e.lon, e.lat,
						e.start.toDate(), e.getEnd().toDate(), e.id, e.url);
	}

	public static String toJSON(Transport t) {
		return String.format("'type':'transport', 'transport_type':'%s', 'from_city':'%s', 'to_city':'%s', 'out_time':'%s', 'in_time':'%s'", 
				t.getType(), t.start.name, t.end.name, t.out.toDate(), t.in.toDate());
	}
}
