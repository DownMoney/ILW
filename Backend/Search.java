import java.util.*;

public class Search {
	public User user;
	public HashMap<String, String> codes;

	public Search() {
		codes = CityCode.getCodes();
	}

	public Vector<Route> findPaths(City city, City end, double maxCost,
			TimeDate start, TimeDate maxTime, int N, User u) {

		user = u;
		Vector<Route> fin = new Vector<Route>();
		Vector<Node> next = new Vector<Node>();
		next.add(new Node(city, start));
		List<Node> curr = new Vector<Node>();
		boolean ready = false;
		while (!next.isEmpty() && !ready) {
			filter(next, N);
			filter(fin, N);
			curr = next;
			next = new Vector<Node>();
			// System.out.println("next");
			while (!curr.isEmpty() && !ready) {
				// System.out.println("curr");
				Node top = curr.get(curr.size() - 1);
				boolean cont = false;
				curr.remove(curr.size() - 1);
				Vector<Event> events = getAllEvents(top.city, top.time, maxTime);
				for (Event ei : events) {
					if (checkRoute(top.route, ei, maxCost, maxTime)) {
						next.add(new Node(ei, top.route, top.city));
						// System.out.println("Added: " + ei.name + " " +
						// ei.start.toString() + " " + ei.getEnd().toString());
						cont = true;
					}
				}
				Vector<Transport> trans = getAllTrans(top.city, top.time,
						maxTime);
				if (trans != null)
					for (Transport ti : trans) {
						if (checkRoute(top.route, ti, maxCost, maxTime, end,
								city)) {
							Route nr = top.route.copy();
							nr.addActivity(ti);
							next.add(new Node(ti.end, nr));
							// System.out.println("City: " + ti.end.name);
							cont = true;
						}
					}
				if (!cont) {
					if(end == null){
						fin.add(top.route);
					} else {
						Route nr = top.route.copy();
						nr.addActivity(new Transport(nr.lastCity, end, nr.time, nr.time.addH(2), 200));
						fin.add(nr);
					}
				}
				ready = fin.size() >= N;
			}
		}
		filter(fin, N);
		return fin;
	}

	public Vector<Transport> getAllTrans(City c, TimeDate start, TimeDate end) {
		if (c.outTrans != null && c.outTrans.size() > 0) {
			//System.out.println("Am I called? " + c.outTrans.size());
			return getAfterTrans(c.outTrans, start);
		}
		Vector<Transport> trans = new Vector<Transport>();
		String code = null;
		if (c.code == null) {
			code = codes.get(c.name);
		} else {
			code = c.code;
		}
		if (code == null) {
			return trans;
		}
		//System.out.println("Hi" + start.toDate());
		if(!start.after(new TimeDate(Calendar.getInstance())))
		trans = TransAPI.getAllTrans(c, code, start, end);
		c.outTrans = trans;
		return trans;
	}

	public Vector<Transport> getAfterTrans(Vector<Transport> trans, TimeDate t) {
		Vector<Transport> res = new Vector<Transport>();
		for (Transport ti : trans) {
			if (ti.out.after(t)) {
				res.add(ti);
			}
		}
		return res;
	}

	public Vector<Event> getAllEvents(City c, TimeDate s, TimeDate e) {
		System.setProperty("http.agent", "");
		return EventAPI.getEvents(c, s, e, user);
	}

	public <T extends Comparable<T>> void filter(Vector<T> v, int N) {
		if (v.size() <= N)
			return;
		Collections.sort(v);
		v.setSize(N);
	}

	public boolean checkRoute(Route r, Transport t, double mc, TimeDate mt,
			City c, City start) {
		Route nr = r.copy();
		nr.addActivity(t);
		nr.addActivity(t.end, getScore(t.end), t.in);
		if (nr.cost > mc || TimeDate.diffSZ(mt, nr.time).toSecs() < 0
				|| r.lastCity.equals(c)
				|| (r.contains(t.end) && !start.equals(c))) {
			// System.out.println("City Rejected: " + c.name);
			return false;
		} else {
			return true;
		}
	}

	public boolean checkRoute(Route r, Event e, double mc, TimeDate mt) {
		Route nr = r.copy();
		nr.addActivity(e, getScore(e));
		if (nr.cost > mc || TimeDate.diffSZ(mt, nr.time).toSecs() < 0
				|| r.contains(e)) {
			// System.out.println("Rejected: " + e.name + " " + r.contains(e));
			return false;
		} else {
			// System.out.println("Accepted: " + e.name + " " + r.contains(e));
			return true;
		}
	}

	public double getScore(City c) {
		double res = 10000;
		// if(user.cities.get(c.name) != null){
		// res += 10000;
		// }
		if (c.events != null)
			for (Event e : c.events) {
				res += getScore(e);
			}
		return res;
	}

	public double getScore(Event e) {
		if(e == null) return 0;
		return e.pop;
	}

	private class Node implements Comparable<Node> {
		City city;
		Route route;
		Event event;
		TimeDate time;

		public Node(City c, TimeDate t) {
			init();
			route = new Route();
			route.addActivity(c, getScore(c), t);
			city = c;
			time = t;
		}

		public Node(City c, Route r) {
			init();
			city = c;
			route = r.copy();
			time = r.time;
			route.addActivity(c, getScore(c), time);
		}

		public Node(Event e, Route r, City c) {
			init();
			event = e;
			route = r.copy();
			route.addActivity(e, getScore(e));
			city = c;
			time = e.getEnd().addH(1);
		}

		public void init() {
			if (route == null) {
				route = new Route();
			}
		}

		public int compareTo(Node n) {
			return route.compareTo(n.route);
		}
	}
}
