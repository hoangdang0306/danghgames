/**
 *
 */
package jp.game.core.localize;


/**
 * @author daisuke-ogihara
 *
 */
class GameLocaleJa implements IGameLocale {

	@Override
	public String getImageDir() {
		return "img";
	}

	@Override
	public String getCSSDir() {
		return "css";
	}

	@Override
	public String getFixDataDir() {
		return "/WEB-INF/" + getFixDataShortDir();
	}

	@Override
	public String getFixDataShortDir() {
		return "data";
	}

	@Override
	public String getLocaleShortName() {
		return "JP";
	}

	@Override
	public String getCSVCharacterEncoding() {
		return "Windows-31J";
	}

	@Override
	public String getJSDir() {
		// TODO Auto-generated method stub
		return null;
	}

}
