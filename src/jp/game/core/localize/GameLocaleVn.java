package jp.game.core.localize;

public class GameLocaleVn implements IGameLocale {

	@Override
	public String getImageDir() {
		// TODO Auto-generated method stub
		return "img";
	}

	@Override
	public String getCSSDir() {
		// TODO Auto-generated method stub
		return "css";
	}

	@Override
	public String getJSDir() {
		// TODO Auto-generated method stub
		return "js";
	}

	@Override
	public String getFixDataDir() {
		// TODO Auto-generated method stub
		return "/WEB-INF/" + getFixDataShortDir();
	}

	@Override
	public String getFixDataShortDir() {
		// TODO Auto-generated method stub
		return "data";
	}

	@Override
	public String getLocaleShortName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCSVCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

}
