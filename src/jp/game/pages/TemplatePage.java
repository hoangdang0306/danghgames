package jp.game.pages;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.click.control.Field;
import org.apache.click.element.JsImport;
import org.apache.commons.lang.StringUtils;

import jp.game.common.CommonDefine;
import jp.game.common.methods.Session;
import jp.game.service.Account;
import jp.game.service.AccountService;
import jp.game.service.Player;
import jp.game.service.PlayerService;

public class TemplatePage extends PageBase {

	private static final long serialVersionUID = 1L;
	private Player player;

	public TemplatePage() {
		super.onInit();
		addModel("title", getTitle());
	}

	public String getTitle() {
		return getMessage("MSG_GAME_TITLE");
	}
	
	@Override
	public void onRender() {
		super.onRender();
		
		addModel("root", getHostURL() + getContextPath());
	}

	@Override
	public String getTemplate() {
		return "/template_page.htm";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();
			
			String contextPath = getHostURL() + getContextPath();
			headElements.add(new JsImport(contextPath + "/js/lib/jquery/jquery-1.6.4.min.js"));
		}
		return headElements;
	}
	
	protected Player getPlayer() {
		if (player != null) {
			return player;
		}
		
		Session gameSession = getGameSession();
		if (gameSession == null) {
			return null;
		}
		
		player = PlayerService.getPlayerById(gameSession.getID());
		
		return player;
	}
	
	protected Account getAccout() {
		Session gameSession = (Session) getContext().getSessionAttribute(CommonDefine.GAME_SESSION);
		if (gameSession == null) {
			return null;
		}
		
		Account account = AccountService.getAccountByName(gameSession.getName());
		return account;
	}

	//Utility function for encrypting password
	protected static String encryptPassword(String strPasswordOriginal) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strPasswordOriginal.getBytes());

			StringBuilder sb = new StringBuilder();
			byte[] digest = md.digest();
			for (int i = 0; i < digest.length; i++) {
				int d = digest[i];

				if (d < 0) { // byte 128-255
					d += 256;
				}
				if (d < 16) { // 0-15 16
					sb.append("0");
				}
				sb.append(Integer.toString(d, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	protected static boolean checkBlank(Field fld){
		if (StringUtils.isBlank(fld.getValue())){
			fld.setError(fld.getLabel() + " cannot be blank");
			return true;
		}
		return false;
	}
	
	protected static boolean setFieldValue(Field fld, String value){
		if (fld == null){
			return false;
		}
		fld.setValue(value);
		return true;
	}
}
