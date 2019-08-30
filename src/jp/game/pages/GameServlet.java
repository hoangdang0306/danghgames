package jp.game.pages;

import java.util.Map;

import javax.servlet.ServletException;

import jp.game.common.methods.GameLog;
import jp.game.core.GameProperties;
import jp.game.core.localize.GameLocale;
import jp.game.dao.fix.FixDataLoader;

import org.apache.click.ClickServlet;
import org.apache.click.Page;

public class GameServlet extends ClickServlet {

	/** */
	private static final long serialVersionUID = 1L;

	/** ロードするプロパティファイル */
	private static final String PROPERTIES_FILE_NAME = "game.properties";

	@Override
	public void init() throws ServletException {
		super.init();
		
		GameLog.getInstance().adjustLogLevel(getConfigService().isProductionMode());

		// nobunyaga.propertiesをロードする.
		GameProperties.loadProperties(PROPERTIES_FILE_NAME);

		// localeの設定.
		GameLocale.create(getConfigService().getLocale());

		// Fixデータのロード.
		FixDataLoader.init(getServletContext());

	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes"})
	protected Map createTemplateModel(final Page page) {
		final Map model = super.createTemplateModel(page);

		Object pop = model.put("IMAGE_DIR", GameLocale.getImageDir());
		if (pop != null && !page.isStateful()) {
			String msg = page.getClass().getName() + " on " + page.getPath() + " model contains an object keyed with reserved name \"IMAGE_DIR\". The page model object " + pop
					+ " has been replaced with the image directory";
			logger.warn(msg);
		}

		pop = model.put("CSS_DIR", GameLocale.getCSSDir());
		if (pop != null && !page.isStateful()) {
			String msg = page.getClass().getName() + " on " + page.getPath() + " model contains an object keyed with reserved name \"CSS_DIR\". The page model object " + pop
					+ " has been replaced with the CSS directory";
			logger.warn(msg);
		}

		pop = model.put("HOSTNAME", "");
		if (pop != null && !page.isStateful()) {
			String msg = page.getClass().getName() + " on " + page.getPath() + " model contains an object keyed with reserved name \"HOSTNAME\". The page model object " + pop
					+ " has been replaced with the GameProperties.SERVICE_HOSTNAME";
			logger.warn(msg);
		}

		return model;
	}

	@Override
	public void destroy() {
		// Fixデータの削除.
		FixDataLoader.destroy(getServletContext());

		super.destroy();
	}

}
