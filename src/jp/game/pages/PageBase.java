package jp.game.pages;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.click.Context;
import org.apache.click.Page;
import org.apache.click.util.ClickUtils;
import org.apache.click.util.HtmlStringBuffer;

import jp.game.common.CommonDefine;
import jp.game.common.methods.GameLog;
import jp.game.common.methods.Session;
import jp.game.service.Account;
import jp.game.service.World;
import jp.game.service.WorldService;

public class PageBase extends Page implements IPageBase {

	private static final long serialVersionUID = 1L;
	
	protected World world = null;
	
	public PageBase() {
		logPageParameters();
	}
	
	protected void createGameSession(Account account) {
		Session session = new Session(account.getId(), account.getUsername());
		getContext().getSession().setAttribute(CommonDefine.GAME_SESSION, session);
	}

	protected Session getGameSession() {
		return (Session) getContext().getSession().getAttribute(CommonDefine.GAME_SESSION);
	}
	
	protected void removeGameSession() {
		getContext().getSession().removeAttribute(CommonDefine.GAME_SESSION);
	}

	public World getWorldInfo () {
		if (world == null) {
			world = WorldService.getWorldInfo();
		}
		
		return world;
	}
	
	protected void logPageParameters() {
		if (GameLog.getInstance().isInfoEnabled()) {
			String strParameter = "";
			Context context = getContext();
			HttpServletRequest request = context.getRequest();
			Map<String, String[]> parameterMap = request.getParameterMap();
			for (Iterator<String> it = parameterMap.keySet().iterator(); it.hasNext();) {
				String _key = it.next();
				String[] _value = parameterMap.get(_key);
				for (String __value : _value) {
					if (strParameter.equals("")) {
						strParameter += _key + "=" + __value;
					} else {
						strParameter += "&" + _key + "=" + __value;
					}
				}
			}

			if (strParameter.equals("") == false) {
				strParameter = "?" + strParameter;
			}
			GameLog.getInstance().info(String.format("[PAGE] %s : %s%s", request.getMethod(), context.getResourcePath(), strParameter));
		}
	}

	@Override
	public String getHostURL() {
		return "";
	}

	@Override
	public String getContextPath() {
		return getContext().getRequest().getContextPath();
	}

	protected String getPagePath(Class<? extends Page> pageClass) {
		return getPagePath(pageClass, null);
	}

	protected String getPagePath(Class<? extends Page> pageClass, Map<String, String> params) {

		String path = getContext().getRequest().getContextPath() + getContext().getPagePath(pageClass);
		if (params != null && !params.isEmpty()) {
			HtmlStringBuffer buffer = new HtmlStringBuffer();

			for (Iterator<String> i = params.keySet().iterator(); i.hasNext();) {
				String paramName = i.next().toString();
				Object paramValue = params.get(paramName);

				// Check for multivalued parameter
				if (paramValue instanceof String[]) {
					String[] paramValues = (String[]) paramValue;
					for (int j = 0; j < paramValues.length; j++) {
						buffer.append(paramName);
						buffer.append("=");
						buffer.append(ClickUtils.encodeUrl(paramValues[j], getContext()));
						if (j < paramValues.length - 1) {
							buffer.append("&amp;");
						}
					}
				} else {
					if (paramValue != null) {
						buffer.append(paramName);
						buffer.append("=");
						buffer.append(ClickUtils.encodeUrl(paramValue, getContext()));
					}
				}
				if (i.hasNext()) {
					buffer.append("&amp;");
				}
			}
			if (buffer.length() > 0) {
				if (path.contains("?")) {
					path += "&amp;" + buffer.toString();
				} else {
					path += "?" + buffer.toString();
				}
			}
		}
		return path;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setRedirect(String location, Map params) {
		String redirect = getRedirect();

		super.setRedirect(location, params);

		if (GameLog.getInstance().isDebugEnabled() && location != null) {
			if (redirect != null) {
				GameLog.getInstance().debug("[PAGE] REDIRECT : null => " + getRedirect());
			} else {
				GameLog.getInstance().debug("[PAGE] REDIRECT : " + redirect + " => " + getRedirect());
			}
		}
	}
}
