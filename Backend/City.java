import java.util.*;
public class City {
	String name;
	String country;
	long lat, lon;
	Vector<Event> events;
	Vector<Transport> inTrans, outTrans;
	TimeDate time;
	long pop;
	int type;
	Vector<String> airports;
	public City(String name)
	{
		this.name = name;
		this.name = name.split(" ")[0];
		this.events = null;
		this.inTrans = null;
		this.outTrans = null;
	}
	public boolean equals(City c){
		if(c == null) return false;
		return name.equals(c.name);
	}
}
