import java.util.*;
public class MainTest {
	public static void main(String[] args){
		Search s = new Search();
		TimeDate start = new TimeDate(10, 0, 0, 2014, 2, 15);
		TimeDate end = new TimeDate(10, 0, 0, 2014, 3, 15);
		Vector<Route> r = s.findPaths(new City("Edinburgh"), null, 500, start, end, 1000, null);
		System.out.println(r.size());
		System.out.println(r.get(0).isCity(0));
		System.out.println(JSON.toJSON(r));
	}
}
