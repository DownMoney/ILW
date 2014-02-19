
public class Transport {
	TimeDate out, in;
	City start, end;
	TransType type;
	double cost;
	String outAirport, inAirport;
	int s,e;
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
}
