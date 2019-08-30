/**
 *
 */
package jp.game.pages.json;

import java.util.HashMap;
import java.util.Map;

import jp.game.pages.PageBase;
import net.arnx.jsonic.JSON;

public class JsonPageBase extends PageBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Object> jsondata = new HashMap<String, Object>();
	private boolean prettyPrint = false;


	@Override
	public String getContentType() {
		String charset = getContext().getRequest().getCharacterEncoding();

		if (charset == null) {
			return "application/json";
		} else {
			return "application/json; charset=" + charset;
		}
	}

	/**
	 * @return
	 */
	public String getHostURL() {
		return "";
	}

	/**
	 * @return コンテキスト・パス.
	 */
	public String getContextPath() {
		return getContext().getRequest().getContextPath();
	}


	@Override
	public void onRender() {
		super.onRender();

		if (!jsondata.isEmpty()) {
			addModel("jsondata", JSON.encode(jsondata, prettyPrint));
		}
	}

	/**
	 * @param key
	 * @param value
	 */
	protected Object putJsonData(String key, Object value) {
		return jsondata.put(key, value);
	}

	/**
	 * @param m
	 */
	protected void putAllJsonData(Map<String, Object> m) {
		jsondata.putAll(m);
	}

	/**
	 * @param prettyPrint セットする prettyPrint
	 */
	protected void setPrettyPrint(boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
	}
}
