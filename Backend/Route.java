import java.util.*;
public class Route implements Comparable<Route> {
	Vector<Activity> route;
	double cost;
	TimeDate time;
	double score;
	int index = 0;
	int size = 0;
	City lastCity;
	HashMap<String, Event> events;
	HashMap<String, City> cities;
	public Route(){
		events = new HashMap<String, Event>();
		cities = new HashMap<String, City>();
		lastCity = null;
		time = null;
		score = 0;
		cost = 0;
		route = new Vector<Activity>();
	}
	public Route(Vector<Activity> r, double co, TimeDate t, double s, int i, int si, City lc, HashMap<String, City> cs, HashMap<String, Event> es){
		route = r;
		cost = co;
		time = t;
		score = s;
		index = i;
		size = si;
		lastCity = lc;
		cities = new HashMap<String, City>(cs);
		events = new HashMap<String, Event>(es);
	}
	public String toString(){
		StringBuffer buff = new StringBuffer();
		for(Activity ai: route){
			if(ai.event != null){
				buff.append("Event ->");
			} else if (ai.city != null){
				buff.append(ai.city.name + " ->");
			}
		}
		return buff.toString();
	}
	public void setUpHash(){
		events = new HashMap<String, Event>();
		cities = new HashMap<String, City>();
		if(route != null)
		for(Activity ai : route){
			if(ai.city != null){
				cities.put(ai.city.name, ai.city);
			} else if(ai.event != null){
				events.put(ai.event.id, ai.event);
			}
		}
	}
	
	public void addActivity(City c, double s, TimeDate t){
		route.add(new Activity(c, t));
		lastCity = c;
		score += s;
		size++;
		time = t;
		cities.put(c.name, c);
	}
	public void addActivity(Transport t){
		route.add(new Activity(t));
		cost += t.cost;
		time = t.in;
		size++;
	}
	public void addActivity(Event e, double s){
		route.add(new Activity(e));
		cost += e.cost;
		TimeDate tt = e.getEnd().addH(1);
		if(time.after(tt)){
			time = time.addH(2);
		}
		score += s;
		size++;
		events.put(e.name, e);
	}
	public boolean isCity(int i){
		return (route.get(i).city != null);
	}
	public boolean isEvent(int i){
		return (route.get(i).event != null);
	}
	public Object getNext(){
		index++;
		return (Object) (isCity(index) ? route.get(index).city : (isEvent(index)? route.get(index).event : route.get(index).trans));
	}
	public void setIndex(int i){
		index = i;
	}
	public Object getCurr(){
		return (Object) (isCity(index) ? route.get(index).city : (isEvent(index)? route.get(index).event : route.get(index).trans));
	}
	public Object getAt(int i){
		return (Object) (isCity(i) ? route.get(i).city : (isEvent(i)? route.get(i).event : route.get(i).trans));
	}
	public int compareTo(Route r){
		return -(new Double(score)).compareTo(new Double(r.score));
	}
	public void pop(){
		Activity a = route.get(size-1);
		route.remove(size-1);
		size--;
		cost -= a.cost;
		time = route.get(size-1).time;
	}
	public Route copy(){
		Vector<Activity> nr = new Vector<Activity>();
		if(route != null)
		for(Activity ai : route){
			nr.add(ai);
		}
		return new Route(nr, cost, time, score, index, size, lastCity, cities, events);
	}
	public boolean contains(City c){
		return !(cities.get(c.name) == null);
	}
	public boolean contains(Event e){
		return !(events.get(e.name) == null);
	}
	private class Activity{
		City city;
		Transport trans;
		Event event;
		double cost;
		TimeDate time;
		public Activity(Event e){
			event = e;
			cost = e.cost;
			time = e.getEnd().addH(1);
		}
		public Activity(City c, TimeDate t){
			city = c;
			cost = 0;
			time = t;
		}
		public Activity(Transport t){
			trans = t;
			cost = t.cost;
			time = t.in;
		}
		public boolean isCity(){
			return !(city == null);
		}
	}
}
