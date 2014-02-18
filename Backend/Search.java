import java.util.*;

public class Search {
	public User user;

	public Route[] findPaths(City[] cities, double maxCost, TimeDate start, TimeDate maxTime,
			int N, User u) {
		user = u;
		List<Node> next = new Vector<Node>();
		next.add(new Node(cities[0], start));
		List<Node> curr = new Vector<Node>();
		while (!next.isEmpty()) {
			filter(next, N);
			curr = next;
			next = new Vector<Node>();
			while (!curr.isEmpty()) {
				Node top = curr.get(curr.size() - 1);
				checkRoutes(top, maxCost, maxTime);
				if (top.routes.size() == 0) {
					continue;
				}
				Vector<Event> events = getAllEvents(top.city, top.time);
				for(Event ei : events){
					next.add(new Node(top.routes, top.city, ei));
				}
				Vector<Transport> trans = getAllTrans(top.city, top.time);
				for(Transport ti : trans){
					next.add(new Node(top.routes, ti));
				}
			}
		}
		return null;
	}
	
	public void filter(List<Node> v, int N){
		if(v.size() <= N) return;
		List<Route> rs = new Vector<Route>();
		for(Node ni : v){
			rs.addAll(ni.routes);
		}
		Collections.sort(rs);
		v.clear();
		for(int i = 0; i < N; i++){
			v.add(new Node(rs.get(i)));
		}
	}
	
	public void checkRoutes(Node n, double mc, TimeDate mt){
		for(Route ri : n.routes){
			if(ri.cost > mc || TimeDate.diffSZ(mt, ri.time).toSecs() > 0){
				n.routes.remove(ri);
			}
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

	private class Node {
		Vector<Route> routes;
		City city;
		Event event;
		TimeDate time;
		boolean ev = false;

		public Node(City c, TimeDate t) {
			city = c;
			time = t;
			init();
		}

		public Node(Vector<Route> r, Transport t) {
			city = t.end;
			routes = r;
			time = t.in;
			for (Route ri : routes) {
				ri.addActivity(city, getScore(city));
			}
		}

		public Node(Vector<Route> r, City c, Event e) {
			routes = r;
			city = c;
			event = e;
			time = e.end;
			for (Route ri : routes) {
				ri.addActivity(e, getScore(e));
			}
		}
		
		public Node(Route rs){
			time = rs.time;
			routes = new Vector<Route>();
			routes.add(rs);
			if(rs.isCity(rs.size-1)){
				city = (City)rs.getAt(rs.size-1);
			} else if(rs.isEvent(rs.size-1)){
				int i = rs.size - 2;
				while(i >= 0 && !rs.isCity(i)){
					i--;
				}
				if(i < 0) city = null;
				else city = (City)rs.getAt(i);
			}
			
		}

		public void init() {
			if (routes == null) {
				routes = new Vector<Route>();
			}
		}
	}
}
