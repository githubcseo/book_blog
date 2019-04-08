package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalUtil {
	public static void main(String[] args) throws ParseException {
		System.out.println(isWeekend(getToday()));
	}

	public static String getToday() {

		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd",
				Locale.KOREA);

		Date currentTime = new Date();

		String mTime = mSimpleDateFormat.format(currentTime);

		return mTime;

	}

	public static String diffDay(String criteriaDay, int diff) {
		return Time.dateAddFormat(diff, criteriaDay, "yyyy/MM/dd");
	}

	public static boolean isWeekend(String datestr) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse(datestr);

		Calendar cal = Calendar.getInstance();

		cal.clear();

		cal.setTime(date);

		int ret = cal.get(Calendar.DAY_OF_WEEK);


		if (ret == 1 || ret == 7) {
			return true;
		}
		return false;

	}

}
