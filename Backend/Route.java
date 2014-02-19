import java.util.*;
public class Route implements Comparable<Route> {
	Vector<Activity> route;
	double cost;
	TimeDate time;
	double score;
	int index = 0;
	int size = 0;
	public Route(){}
	public void addActivity(City c, double s){
		route.add(new Activity(c));
		score += s;
		size++;
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
		time = e.end;
		score += s;
		size++;
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
		return (new Double(score)).compareTo(new Double(r.score));
	}
	
	private class Activity{
		City city;
		Transport trans;
		Event event;
		public Activity(Event e){
			event = e;
		}
		public Activity(City c){
			city = c;
		}
		public Activity(Transport t){
			trans = t;
		}
		public boolean isCity(){
			return !(city == null);
		}
	}
}
