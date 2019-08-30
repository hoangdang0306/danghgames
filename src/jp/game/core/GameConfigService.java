/**
 *
 */
package jp.game.core;

import org.apache.click.service.XmlConfigService;

/**
 * @author daisuke-ogihara
 *
 */
public final class GameConfigService extends XmlConfigService {

	/*
	 * (非 Javadoc)
	 *
	 * @see
	 * org.apache.click.service.XmlConfigService#isTemplate(java.lang.String)
	 */
	@Override
	public boolean isTemplate(String path) {
		boolean isTemplate = super.isTemplate(path);

		if (!isTemplate) {
			if (path.endsWith(".xml") || path.endsWith(".json") || path.endsWith(".csv")) {
				isTemplate = true;
			}
		}
		return isTemplate;
	}
}
