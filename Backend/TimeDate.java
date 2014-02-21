import java.util.Calendar;

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
	
	public String toString(){
		return String.format("%d-%d-%d %d:%d:%d", year, mon, day, hour, min, sec);
	}
	
	public TimeDate(String s) {
		year = Integer.parseInt(s.substring(0, 4));
		mon = Integer.parseInt(s.substring(5, 7));
		day = Integer.parseInt(s.substring(8, 10));
		hour = Integer.parseInt(s.substring(11, 13));
		min = Integer.parseInt(s.substring(14, 16));
		sec = Integer.parseInt(s.substring(17, 19));
	}

	public static TimeDate diffSZ(TimeDate t1, TimeDate t2) {
		if (t1 == null && t2 == null)
			return new TimeDate(12, 12, 12, 2014, 2, 17);
		if (t1 == null)
			return t2;
		if (t2 == null)
			return t1;
		return new TimeDate(t1.hour - t2.hour, t1.min - t2.min,
				t1.sec - t2.sec, t1.year - t2.year, t1.mon - t2.mon, t1.day
						- t2.day);
	}

	public long toSecs() {
		return year * 365*24*3600 + mon * 31*24*3600 + day * 24 * 3600 + hour * 3600 + min * 60
				+ sec;
	}

	public String toDate() {
		return String.format("%d%s%s", year, getMonth(), getDay());
	}
	
	public String toDateB(){
		return String.format("%d-%s-%s", year, getMonth(), getDay());
	}
	
	public String getMonth(){
		if(mon < 10) return String.format("0%d", mon);
		else return String.format("%d", mon);
	}

	public String getDay() {
		if (day < 10)
			return String.format("0%d", day);
		else
			return String.format("%d", day);
	}

	public boolean after(TimeDate t) {
		return (toSecs() - t.toSecs()) > 0;
	}

	public boolean equals(TimeDate t) {
		return (toSecs() - t.toSecs()) == 0;
	}

	public TimeDate addH(int h) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, mon);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.add(Calendar.HOUR_OF_DAY, h);
		return new TimeDate(cal);
	}

	public TimeDate(Calendar c) {
		day = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);
		sec = c.get(Calendar.SECOND);
		year = c.get(Calendar.YEAR);
		mon = c.get(Calendar.MONTH);
		day = c.get(Calendar.DATE);
	}

}
