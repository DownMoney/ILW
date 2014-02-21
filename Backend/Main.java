import java.util.*;
public class Main {
	final static int N = 5;
	static TimeDate end, start;
	static City out;
	static City in;
	static double maxCost;
	static User user;
	public static void main(String[] args){
		Search s = new Search();
		//TimeDate start = new TimeDate(10, 0, 0, 2014, 2, 21);
		//TimeDate end = new TimeDate(10, 0, 0, 2014, 2, 26);
		long t = System.currentTimeMillis();
		//Vector<Route> r = s.findPaths(new City("Athens"), null, 600, start, end, 5, user);
		//System.out.println(r.size());
		//System.out.println(JSON.toJSON(r));
		//for(Route ri : r){
			//System.out.println(ri.toString());
		//}
		//System.out.println(System.currentTimeMillis() - t);
		parseArgs(args[0]);
		Vector<Route> r = s.findPaths(out, in, maxCost, start, end, N, user);
		System.out.println(JSON.toJSON(r));
	}
	
	public static void parseArgs(String args){
		String[] params = args.split(";");
		out = new City(params[0]);
		if(params[1].equals("null")){
			in = null;
		} else {
			in = new City(params[1]);
		}
		start = new TimeDate(params[2]);
		end = new TimeDate(params[3]);
		HashMap<String, Short> cs = new HashMap<String, Short>();
		String[] cities = params[4].split(",");
		for(String si: cities){
			cs.put(si, ((short) 1));
		}
		String[] cats = params[5].split(",");
		String[] keys = params[6].split(",");
		user = new User(cs, cats, keys);
		maxCost = Double.parseDouble(params[7]);
	}
}
