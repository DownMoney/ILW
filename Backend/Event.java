import java.util.Calendar;

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
		if(cost == 0) cost = 25;
		pop = p;
		lat = la;
		lon = lo;
		postal = pc;
		url = u;
		id = i;
		ad = a;
	}
	
	public TimeDate getEnd(){
		if(end != null && end.after(start)){
			return end;
		} else if(start != null) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, start.year);
			cal.set(Calendar.MONTH, start.mon);
			cal.set(Calendar.DATE, start.day);
			cal.set(Calendar.HOUR_OF_DAY, start.hour);
			cal.set(Calendar.MINUTE, start.min);
			cal.set(Calendar.SECOND, start.sec);
			cal.add(Calendar.HOUR_OF_DAY, 3);
			return new TimeDate(cal);
		} else {
			return null;
		}
	}

}
