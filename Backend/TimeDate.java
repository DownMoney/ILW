public class TimeDate {
	int hour, min, sec, mon, year, day;

	public TimeDate(int h, int mi, int s, int m, int y, int d) {
		hour = h;
		min = mi;
		sec = s;
		mon = m;
		year = y;
		day = d;
	}

	public static TimeDate diffSZ(TimeDate t1, TimeDate t2) {
		return new TimeDate(t1.hour - t2.hour, t1.min - t2.min,
				t1.sec - t2.sec, t1.mon - t2.mon, t1.year - t2.year, t1.day
						- t2.day);
	}

	public int toSecs() {
		return day * 24 * 3600 + hour * 3600 + min * 60 + sec;
	}
}
