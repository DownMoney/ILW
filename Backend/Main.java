import java.util.*;
public class Main {
	final static int N = 100;
	static TimeDate end, start;
	static City out;
	static City in;
	double maxCost;
	static User user;
	public static void main(String[] args){
		Search s = new Search();
		/*TimeDate start = new TimeDate(10, 0, 0, 2014, 2, 15);
		TimeDate end = new TimeDate(10, 0, 0, 2014, 3, 15);
		Vector<Route> r = s.findPaths(new City("Edinburgh"), null, 500, start, end, 1000, null);
		System.out.println(r.size());
		//System.out.println(JSON.toJSON(r));
		 * */
		parseArgs(args[0]); 
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
		String[] cities = params[4].split(",");
		String[] cats = params[5].split(",");
		String[] keys = params[6].split(",");
	}
}
