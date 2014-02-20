
public class Transport {
	TimeDate out, in;
	City start, end;
	TransType type;
	double cost;
	String outAirport, inAirport;
	
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
