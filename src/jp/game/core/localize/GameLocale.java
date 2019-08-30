/**
 *
 */
package jp.game.core.localize;

import java.util.Locale;

/**
 * @author daisuke-ogihara
 *
 */
public abstract class GameLocale {

	/** */
	private static Locale locale;

	/** */
	private static IGameLocale game_locale;

	/**
	 * @param locale
	 */
	public static void create(Locale locale) {
		if (GameLocale.locale != null || game_locale != null) {
			// 2度目は呼ばないでね.
			assert false;
		}

		GameLocale.locale = locale;
		if (locale == null || locale.equals(Locale.JAPANESE) || locale.equals(Locale.JAPAN)) {
			// 日本語.
			game_locale = new GameLocaleVn();
		}  else {
			throw new RuntimeException("undefined Locale: " + locale.toString());
		}
	}

	/**
	 * @return
	 */
	public static String getLocaleName() {
		return locale == null ? "" : locale.toString();
	}

	/**
	 * @return
	 */
	public static String getImageDir() {
		return game_locale.getImageDir();
	}

	/**
	 * @return
	 */
	public static String getCSSDir() {
		return game_locale.getCSSDir();
	}
	
	public static String getJSDir() {
		return game_locale.getJSDir();
	}

	/**
	 * @return
	 */
	public static String getFixDataDir() {
		return game_locale.getFixDataDir();
	}

	/**
	 * @return
	 */
	public static String getFixDataShortDir() {
		return game_locale.getFixDataShortDir();
	}

	/**
	 * @return
	 */
	public static String getLocaleShortName() {
		return game_locale.getLocaleShortName();
	}

	/**
	 * @return
	 */
	public static String getCSVCharacterEncoding() {
		return game_locale.getCSVCharacterEncoding();
	}

	/**
	 * @return
	 */
	public static boolean isJa() {
		return (locale == null || locale.equals(Locale.JAPANESE) || locale.equals(Locale.JAPAN));
	}

	/**
	 * @return
	 */
	public static Locale getLocale() {
		return locale;
	}

}
