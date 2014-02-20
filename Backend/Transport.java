public class Transport {
	TimeDate out, in;
	City start, end;
	TransType type;
	double cost;
	String outAirport, inAirport;
	int s,e;
	public String toString()
	{
		return "A transportation from " + start.name + " (" + s + ") to " + end.name + " (" + e + ") at cost of £" + cost;
	}
 	public Transport(City start, City end, double cost)
 	{
 		this.start = start;
 		this.end = end;
 		this.cost = cost;
 	}
 	public Transport(int start, int end, double cost)
 	{
 		this.s = start;
 		this.e = end;
 		this.cost = cost;
 	}
 	public void update(City start, City end)
 	{
 		this.start = start;
 		this.end = end;
 	}
	public String getType(){
		switch(type){
			case  FLIGHT:
				return "plane";
			case BUS:
				return "bus";
			case CAR:
				return "car";
			default:
				return "none";
		}
	}
	
	
}
