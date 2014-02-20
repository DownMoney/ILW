import java.util.*;

public class Search {
	public User user;
	
	public Vector<Route> findPaths(City city, City end, double maxCost, TimeDate start, TimeDate maxTime,
			int N, User u) {
		user = u;
		Vector<Route> fin = new Vector<Route>(); 
		Vector<Node> next = new Vector<Node>();
		next.add(new Node(city, start));
		List<Node> curr = new Vector<Node>();
		while (!next.isEmpty()) {
			filter(next, N);
			filter(fin, N);
			curr = next;
			next = new Vector<Node>();
			while (!curr.isEmpty()) {
				Node top = curr.get(curr.size() - 1);
				Vector<Event> events = getAllEvents(top.city, top.time, maxTime);
				for(Event ei : events){
					if(checkRoute(top.route, ei, maxCost, maxTime)){
						next.add(new Node(ei, top.route, top.city));
					}
				}
				Vector<Transport> trans = getAllTrans(top.city, top.time);
				for(Transport ti : trans){
					if(checkRoute(top.route, ti, maxCost, maxTime, end)){
						Route nr = top.route.copy();
						nr.addActivity(ti);
						next.add(new Node(ti.end, nr));
					}
				}
				fin.add(top.route);
			}
		}
		return fin;
	}
	
	public Vector<Event> getAllEvents(City c, TimeDate s, TimeDate e){
		System.setProperty("http.agent", "");
		return EventAPI.getEvents(c, s, e);
	}
	
	public <T extends Comparable<T>> void filter(Vector<T> v, int N){
		if(v.size() <= N) return;
		Collections.sort(v);
		v.setSize(N);
	}

	public boolean checkRoute(Route r, Transport t, double mc, TimeDate mt, City e){
		Route nr = r.copy();
		nr.addActivity(t);
		nr.addActivity(t.end, getScore(t.end), t.in);
		if(nr.cost > mc || TimeDate.diffSZ(mt, nr.time).toSecs() < 0 || r.lastCity.equals(e)){
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkRoute(Route r, Event e, double mc, TimeDate mt){
		Route nr = r.copy();
		nr.addActivity(e, getScore(e));
		if(nr.cost > mc || TimeDate.diffSZ(mt, nr.time).toSecs() < 0){
			return false;
		} else {
			return true;
		}
	}
	
	public double getScore(City c) {
		double res = 0;
		for (Event e : c.events) {
			res += getScore(e);
		}
		return res;
	}

	public double getScore(Event e) {
		return e.pop;
	}

	private class Node implements Comparable<Node> {
		City city;
		Route route;
		Event event;
		TimeDate time;
		
		public Node(City c, TimeDate t){
			init();
			city = c;
		}
		public Node(City c, Route r){
			init();
			city = c;
			route = r.copy();
			time = r.time;
			route.addActivity(c, getScore(c), time);
		}
		
		public Node(Event e, Route r, City c){
			init();
			event = e;
			route = r.copy();
			route.addActivity(e, getScore(e));
			city = c;
			time = e.end;
		}
		public void init(){
			if(route == null){
				route = new Route();
			}
		}
		public int compareTo(Node n){
			return route.compareTo(n.route);
		}
	}
}
