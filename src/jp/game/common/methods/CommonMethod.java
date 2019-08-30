/**
 *
 */
package jp.game.common.methods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import jp.game.common.CommonDefine;
import jp.game.core.GameProperties;

/**
 * 全体で使える便利関数.
 *
 * @author keigo-ogawa
 *
 */
public final class CommonMethod {

	/**
	 * インスタンス化できないように.
	 */
	private CommonMethod() {
	}

	/**
	 * ゲーム内で乱数の生成に使う.
	 */
	private static final Random rand = new Random();
	static {
		// デバッグモード時は種を固定する.
		if (GameProperties.DEBUG_MODE) {
			rand.setSeed(1L);
		}
	}

	/**
	 * @param max
	 * @return 0以上max未満のランダムな整数.
	 */
	public static int random(int max) {
		return rand.nextInt(max);
	}

	public static double randomGaussian() {
		return rand.nextGaussian();
	}

	/**
	 * @param date 時刻.
	 * @param pattern the pattern describing the date and time format.
	 * @return
	 */
	static public String getFormatString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * @param date 時刻.
	 * @return 指定した時刻の文字列.
	 */
	static public String getFormatString(Date date) {
		return getFormatString(date, CommonDefine.DATE_FORMAT_PATTERN);
	}

	/**
	 *
	 * @return 現在時刻の文字列.
	 */
	static public String getFormatStringNow() {
		return getFormatString(new Date(), CommonDefine.DATE_FORMAT_PATTERN);
	}

	/**
	 * 所要時刻表示用文字列の取得.
	 *
	 * @param date
	 * @return
	 */
	static public String getFormatStringTime(Date date) {
		return getFormatString(date, CommonDefine.DATE_FORMAT_REQUIRED_TIME);
	}

	/**
	 * 合戦開始時刻などの表示用文字列の取得.
	 *
	 * @param date
	 * @return
	 */
	static public String getSimpleFormatStringTime(Date date) {
		return getFormatString(date, CommonDefine.DATE_FORMAT_SIMPLE);
	}

	/**
	 * 日付のみ.
	 *
	 * @param date
	 * @return
	 */
	static public String getDateExceptTime(Date date) {
		return getFormatString(date, CommonDefine.DATE_FORMAT_EXCEPT_TIME);
	}

	/**
	 *
	 * @return 遠い将来.
	 */
	static public String getFormatStringFuture() {
		return "2099-12-31 23:59:59";
	}

	/**
	 *
	 * @param dateString
	 *            時刻を表す文字列.
	 * @return Dateクラス.
	 * @throws ParseException
	 *             不正な文字列のときに例外.
	 */
	static public Date getDate(String dateString) throws ParseException {
		return getDate(dateString, CommonDefine.DATE_FORMAT_PATTERN);
	}

	/**
	 * 日付のみ.
	 *
	 * @param date
	 * @return
	 */
	static public String getDatePatternDay(Date date) {
		return getFormatString(date, CommonDefine.DATE_FORMAT_PATTERN_DAY);
	}

	/**
	 * 日時のみ.
	 *
	 * @param date
	 * @return
	 */
	static public String getDatePatternTime(Date date) {
		return getFormatString(date, CommonDefine.DATE_FORMAT_PATTERN_TIME);
	}


	/**
	 * @param dateString
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dateString, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateString);
	}

	/**
	 * 秒数から残り時間の文字列取得.
	 *
	 * @param second
	 * @return hh:mm:ss 形式の文字列.
	 */
	static public String getLeftTimeString(long second) {

		long hour = second / 3600;
		second %= 3600;
		long min = second / 60;
		second %= 60;

		return String.format("%02d:%02d:%02d", hour, min, second);
	}

	/**
	 * @param cal1
	 * @param cal2
	 * @return 同じ日付かどうか.
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		return ((cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) && (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)));
	}

	/**
	 * @param cal1
	 * @param cal2
	 * @return 同じ月かどうか.
	 */
	public static boolean isSameMonth(Calendar cal1, Calendar cal2) {
		return ((cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)));
	}

	/**
	 * 過去の時間かのチェック
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static boolean isPassedDate(String dateString) {
		try {
			Date dateNow = getDate(getFormatStringNow());
			Date dateReq = getDate(dateString);

			return dateReq.before(dateNow);
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * min計算
	 * @param nMin 最小値
	 * @param nValue 数値
	 * @return min値
	 */
	static public int min(int nMin, int nValue) {
		if (nValue > nMin) {
			return nMin;
		}

		return nValue;
	}

	/**
	 * max計算
	 * @param nMax 最大値
	 * @param nValue 数値
	 * @return max値
	 */
	static public int max(int nMax, int nValue) {
		if (nValue < nMax) {
			return nMax;
		}

		return nValue;
	}

	/**
	 * minmax計算
	 * @param nMin 最小値
	 * @param nValue 数値
	 * @param nMax 最大値
	 * @return minmax値
	 */
	static public int minmax(int nMin, int nValue, int nMax) {
		if (nValue < nMin) {
			return nMin;
		}

		if (nValue > nMax) {
			return nMax;
		}

		return nValue;
	}

}
