import java.util.*;
public class City {
	String name;
	String country;
	long lat, lon;
	Vector<Event> events;
	Vector<Transport> inTrans, outTrans;
	TimeDate time;
	long pop;
	Vector<String> airports;
	public City(String name)
	{
		this.name = name;
	}
	public boolean equals(City c){
		if(c == null) return false;
		return name.equals(c.name);
	}
}
