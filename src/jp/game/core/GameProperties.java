/**
 *
 */
package jp.game.core;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class GameProperties {

	private static Log log = LogFactory.getLog(GameProperties.class);

	/** ゲームの根幹に関わる項目 */
	public static boolean DEBUG_MODE = true;


	/** ユーザーパブリッシャー（※固定） */
	public static int USER_PROVIDER_DEV		= 0;

	public static String SERVICE_HOSTNAME = "localhost:8080";
	public static String SERVICE_PROVIDER = "DEV";


	/** エージェント用設定 */
	public static boolean AGENT_MODE = false;
	public static String AGENT_MYSQL_DATA_SOURCE_MASTER = "";
	public static String AGENT_MYSQL_DATA_SOURCE_SLAVE = "";
	public static String AGENT_DB_USER = "";
	public static String AGENT_DB_PASS = "";

	/**
	 *
	 */
	public GameProperties() {
	}

	/**
	 * @param strFilename
	 */
	public static void loadProperties(String strFilename) {

		Properties properties = new Properties();
		try {
			properties.load(GameProperties.class.getClassLoader().getResourceAsStream(strFilename));

			if (properties.containsKey("DEBUG_MODE")) {
				DEBUG_MODE = Boolean.parseBoolean(properties.getProperty("DEBUG_MODE"));
				log.info("DEBUG_MODE <= " + Boolean.toString(DEBUG_MODE));
			}

			if (properties.containsKey("SERVICE_HOSTNAME")) {
				SERVICE_HOSTNAME = properties.getProperty("SERVICE_HOSTNAME");
				log.info("SERVICE_HOSTNAME <= " + SERVICE_HOSTNAME);
			}
			if (properties.containsKey("SERVICE_PROVIDER")) {
				SERVICE_PROVIDER = properties.getProperty("SERVICE_PROVIDER");
				log.info("SERVICE_PROVIDER <= " + SERVICE_PROVIDER);
			}

			/** エージェント用設定 */
			if (properties.containsKey("AGENT_MODE")) {
				AGENT_MODE = Boolean.parseBoolean(properties.getProperty("AGENT_MODE"));
				log.info("AGENT_MODE <= " + AGENT_MODE);
			}

			if (properties.containsKey("AGENT_MYSQL_DATA_SOURCE_MASTER")) {
				AGENT_MYSQL_DATA_SOURCE_MASTER = properties.getProperty("AGENT_MYSQL_DATA_SOURCE_MASTER");
				log.info("AGENT_MYSQL_DATA_SOURCE_MASTER <= " + AGENT_MYSQL_DATA_SOURCE_MASTER);
			}
			if (properties.containsKey("AGENT_MYSQL_DATA_SOURCE_SLAVE")) {
				AGENT_MYSQL_DATA_SOURCE_SLAVE = properties.getProperty("AGENT_MYSQL_DATA_SOURCE_SLAVE");
				log.info("AGENT_MYSQL_DATA_SOURCE_SLAVE <= " + AGENT_MYSQL_DATA_SOURCE_SLAVE);
			}

			if (properties.containsKey("AGENT_DB_USER")) {
				AGENT_DB_USER = properties.getProperty("AGENT_DB_USER");
				log.info("AGENT_DB_USER <= " + AGENT_DB_USER);
			}
			if (properties.containsKey("AGENT_DB_PASS")) {
				AGENT_DB_PASS = properties.getProperty("AGENT_DB_PASS");
				log.info("AGENT_DB_PASS <= " + AGENT_DB_PASS);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
