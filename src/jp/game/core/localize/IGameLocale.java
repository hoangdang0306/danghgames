/**
 *
 */
package jp.game.core.localize;

/**
 * @author daisuke-ogihara
 *
 */
public interface IGameLocale {

	/**
	 * @return 画像フォルダの取得.
	 */
	public String getImageDir();

	/**
	 * @return cssファイル.
	 */
	public String getCSSDir();
	
	public String getJSDir();

	/**
	 * @return fixed data path
	 */
	public String getFixDataDir();

	/**
	 * @return fixed data path (without /WEB-INF/) Used by AgentFixData.contextInitialized()
	 */
	public String getFixDataShortDir();

	/**
	 * @return the short name of the locale. E.g. TW, JP, EN
	 */
	public String getLocaleShortName();


	/**
	 * @return csv output character set
	 */
	public String getCSVCharacterEncoding();

}
