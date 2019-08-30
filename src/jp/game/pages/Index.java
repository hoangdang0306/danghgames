package jp.game.pages;

import java.util.List;

import org.apache.click.element.CssImport;
import org.apache.click.element.JsImport;

import jp.game.service.Account;
import jp.game.service.Player;

public class Index extends TemplatePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player = null;
	private Account account = null;
	
	public Index() {
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();
			
			String contextPath = getHostURL() + getContextPath();
			headElements.add(new CssImport(contextPath + "/css/login/login.css"));
			headElements.add(new JsImport(contextPath + "/js/login/login.js"));
		}
		return headElements;
	}
	
	@Override
	public boolean onSecurityCheck() {
		if (getPlayer() == null) {
			setRedirect(Login.class);
			return false;
		}
		return true;
	}
	
	@Override
	public void onInit() {
		super.onInit();
		
		player = getPlayer();
		if (player == null) {
			setRedirect(Login.class);
		}
		
		account = getAccout();
		if (account == null) {
			setRedirect(Login.class);
		}
	}

	@Override
	public void onPost() {
		super.onPost();
		
	}
	
	@Override
	public void onRender() {
		super.onRender();
		
		createGameIcon();
	}
	
	private void createGameIcon() {
		StringBuilder gameIconStr = new StringBuilder();
		
		gameIconStr.append("<button class='game-icon' value='" + getHostURL() + getContextPath() + "/nyagaoh/nyaga_oh_prepare.htm" + "' onclick='enterGame(this);'>Nyaga Oh</button>");
		
		addModel("gameIcon", gameIconStr);
	}
}
