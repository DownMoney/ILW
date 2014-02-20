public class Event {
	String name;
	String desc;
	TimeDate start, end;
	City city;
	double cost;
	int pop;
	double lat, lon;
	String postal;
	String url;
	String id;
	int ad;

	public Event(String n, String d, TimeDate s, TimeDate e, City c, double co,
			int p, double la, double lo, String pc, String u, String i, int a) {
		name = n;
		desc = d;
		start = s;
		end = e;
		city = c;
		cost = co;
		pop = p;
		lat = la;
		lon = lo;
		postal = pc;
		url = u;
		id = i;
		ad = a;
	}

}
