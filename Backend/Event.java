
public class Event {
	String name;
	String desc;
	TimeDate start, end;
	City city;
	double cost;
	int pop;
	public Event(String n, String d, TimeDate s, TimeDate e, City c, double co, int p){
		name = n;
		desc = d;
		start = s;
		end = e;
		city  = c;
		cost = co;
		pop = p;
	}
	
}
