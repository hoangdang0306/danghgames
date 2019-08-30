package jp.game.pages.nyagaoh;

import java.util.List;

import org.apache.click.element.CssImport;

import jp.game.common.methods.GameLog;
import jp.game.pages.GamePage;

public class NyagaOhMain extends GamePage {

	private static final long serialVersionUID = 1L;

	public NyagaOhMain() {
		GameLog.getInstance().debug("NyagaOh Main Page");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();
		}
		
		String contextPath = getHostURL() + getContextPath();
		headElements.add(new CssImport(contextPath + "/css/nyagaoh/nyagaoh.css"));
		
		return headElements;
	}
	
	@Override
	public void onInit() {
		super.onInit();
		
	}

	
}
