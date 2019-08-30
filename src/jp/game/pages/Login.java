package jp.game.pages;

import java.util.List;

import org.apache.click.element.CssImport;
import org.apache.click.element.JsImport;
import org.apache.commons.lang.StringUtils;

import jp.game.common.methods.GameLog;
import jp.game.service.Account;
import jp.game.service.AccountService;

public class Login extends TemplatePage{

	private static final long serialVersionUID = 1L;

	public Login() {
		super.onInit();
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
	public void onInit() {
		super.onInit();
		
	}
	
	@Override
	public void onPost() {
		super.onPost();
		
		String user = getContext().getRequestParameter("user");
		String pass = getContext().getRequestParameter("pass");
		
		if (StringUtils.isEmpty(user) || StringUtils.isEmpty(pass)) {
			GameLog.getInstance().debug("Login: wrong account");
			return;
		}
		
		Account account = AccountService.getAccountByName(user);
		if (account == null) {
			GameLog.getInstance().debug("Login: account null");
			return;
		}
		
		if (!account.getPassword().equals(pass)) {
			GameLog.getInstance().debug("Login: wrong pass");
			return;
		}
		
//		TblPlayerInfo playerInfo = new TblPlayerInfo();
//		playerInfo.
//		
//		if (PlayerService.registerPlayer(playerInfo)) {
//			
//		}
		
		createGameSession(account);
		
		setRedirect(Index.class);
	}
	
	@Override
	public void onRender() {
		super.onRender();
		
		addModel("action", getHostURL() + getContextPath() + "/login.htm");
	}
}
