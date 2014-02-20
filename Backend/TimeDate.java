public class TimeDate {
	int hour, min, sec, mon, year, day;

	public TimeDate(int h, int mi, int s, int y, int m, int d) {
		hour = h;
		min = mi;
		sec = s;
		mon = m;
		year = y;
		day = d;
	}
	
	public TimeDate(String s){
		year = Integer.parseInt(s.substring(0, 4));
		mon = Integer.parseInt(s.substring(5, 7));
		day = Integer.parseInt(s.substring(8, 10));
		hour = Integer.parseInt(s.substring(11, 13));
		min = Integer.parseInt(s.substring(14, 16));
		sec = Integer.parseInt(s.substring(17, 19));
	}

	public static TimeDate diffSZ(TimeDate t1, TimeDate t2) {
		return new TimeDate(t1.hour - t2.hour, t1.min - t2.min,
				t1.sec - t2.sec, t1.year - t2.year, t1.mon - t2.mon, t1.day
						- t2.day);
	}

	public long toSecs() {
		return year * 365 + mon * 31 + day * 24 * 3600 + hour * 3600 + min * 60 + sec;
	}
	
	public String toDate(){
		return String.format("%d%s%s", year, getMonth(), getDay());
	}
	
	public String getMonth(){
		if(mon < 10) return String.format("0%d", mon);
		else return String.format("%d", mon);
	}
	public String getDay(){
		if(day < 10) return String.format("0%d", day);
		else return String.format("%d", day);
	}
	public boolean after(TimeDate t){
		return (toSecs() - t.toSecs()) > 0;
	}
	
	public boolean equals(TimeDate t){
		return (toSecs() - t.toSecs()) == 0;
	}
}
