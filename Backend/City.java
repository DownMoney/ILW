import java.util.*;
public class City {
	String name;
	String country;
	long lat, lon;
	Vector<Event> events;
	Vector<Transport> inTrans, outTrans;
	TimeDate time;
	long pop;
	int type; //0 = airport, 1 = country
	Vector<String> airports;
	String code;
	public City(String name)
	{
		this.name = name;
		this.events = null;
		this.inTrans = null;
		this.outTrans = null;
		code = null;
	}
	public boolean equals(City c){
		if(c== null) return false;
		return name.equals(c.name);
	}
}
