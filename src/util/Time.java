package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {

	private static String defaultDateFormat = "yyyy-MM-dd'T'hh:mm:ss";

	public static void setDefaultDateFormat(String dateFormat) {
		defaultDateFormat = dateFormat;
	}

	public static String getDefaultDateFormat() {
		return defaultDateFormat;
	}

	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	/* 실제로 날짜 연산을 수행하는 메소드 */
	public static Date dateAdd(int amount, Date date) {
		Calendar cal = Calendar.getInstance(); /* 날짜 연산을 위해 Calendar 객체를 생성한다. */

		cal.setTime(date); /* Date형으로 넘어온 날짜를 Calendar 객체를 이용하여 현재 시간으로 설정한다 */

		cal.add(Calendar.DATE, amount); /*
										 * Calendar 객체의 add 메소드를 이용하여 날짜 연산을
										 * 수행한다.
										 */

		return cal.getTime(); /* Date형으로 연산된 날짜를 반환한다. */
	}

	/* 날짜가 String형일 경우에는 SimpleDateFormat 클래스를 이용해서 Date형으로 변환한다. */
	public static Date dateAdd(int amount, String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Time
					.getDefaultDateFormat());
			Date result = sdf.parse(date);
			result = Time.dateAdd(amount, result);

			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static Date dateAdd(int amount, String date, String inputDateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(inputDateFormat);
			Date result = sdf.parse(date);
			result = Time.dateAdd(amount, result);

			return result;
		} catch (Exception e) {
			return null;
		}
	}

	/* 연산된 날짜를 원하는 형태의 문자열로 변환하여 반환한다. */

	public static String dateAddFormat(int amount, Date date,
			String outputDateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(outputDateFormat);
			String formatDate = sdf.format(Time.dateAdd(amount, date));

			return formatDate;
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateAddFormat(int amount, Date date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Time
					.getDefaultDateFormat());
			String formatDate = sdf.format(Time.dateAdd(amount, date));

			return formatDate;
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateAddFormat(int amount, String date,
			String dateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			String formatDate = sdf.format(Time.dateAdd(amount, date,
					dateFormat));

			return formatDate;
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateAddFormat(int amount, String date,
			String inputDateFormat, String outputDateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(outputDateFormat);
			String formatDate = sdf.format(Time.dateAdd(amount, date,
					inputDateFormat));

			return formatDate;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		Date d = Time.now();

		System.out.println(d);
		System.out.println(Time.dateAdd(3, d));

		System.out.println(Time.dateAddFormat(3, d));

		System.out.println(Time.dateAddFormat(3, d, "yyyy-MM-dd"));
		
		System.out.println(Time.dateAddFormat(-1,CalUtil.getToday(),"yyyy/MM/dd"));
	}

}
