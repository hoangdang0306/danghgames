/**
 *
 */
package jp.game.common;

/**
 * ゲーム内で使用する定数値.
 *
 * @author keigo-ogawa
 *
 */
public final class CommonDefine {

	/**
	 * インスタンス化できないように.
	 */
	private CommonDefine() {
	}

	/** SlaveDBの数 */
	public static final int MAX_SLAVE_DB = 3;
	public static final int MAX_SLAVE_DB_ERROR_COUNT = 5;

	/** 時刻のフォーマットパターン **/
	public static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";
	/** 日時フォーマット（伝言板用） */
	public static final String DATE_FORMAT_BBS_COMMENT = "MM/dd HH:mm:ss";
	/** 日時フォーマット（合戦開始時間など） */
	public static final String DATE_FORMAT_SIMPLE = "MM/dd HH:mm";
	/** 所要時刻表示用のフォーマットパターン **/
	public static final String DATE_FORMAT_REQUIRED_TIME = "HH:mm:ss";
	/** 日付フォーマット（お知らせ一覧など）  **/
	public static final String DATE_FORMAT_EXCEPT_TIME = "yyyy-MM-dd";
	/** 日時フォーマット（ねこ戦記時間など） */
	public static final String DATE_FORMAT_SO_SIMPLE = "HH:mm";
	/** XS DateTime dゲーム ActionRecord API用 */
	public static final String DATE_FORMAT_XS = "yyyy-MM-dd'T'HH:mm:ss";
	/** 日付フォーマット（フレンドコードなど）  **/
	public static final String DATE_FORMAT_ONLY_MONTH_DAY = "MM/dd";
	/** 日付フォーマット（城攻め一覧など）  **/
	public static final String DATE_FORMAT_PATTERN_DAY = "yyyy/MM/dd";
	/** 日時フォーマット（城攻め一覧など）  **/
	public static final String DATE_FORMAT_PATTERN_TIME = "HH:mm:ss";

	/** ミリセカンドの数値 **/
	public static final long MILLISECOND_SECOND = 1000;
	public static final long MILLISECOND_MINUTE = 1000 * 60;
	public static final long MILLISECOND_HOUR = MILLISECOND_MINUTE * 60;
	public static final long MILLISECOND_DAY = MILLISECOND_HOUR * 24;
	public static final long MILLISECOND_WEEK = MILLISECOND_DAY * 7;
	
	public static final String GAME_SESSION = "game_session";


}
