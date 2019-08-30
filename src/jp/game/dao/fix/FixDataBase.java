/**
 *
 */
package jp.game.dao.fix;

import java.util.HashMap;
import java.util.Map;

public class FixDataBase {
	/**
	 * 各データクラスのインスタンスを格納しておく.
	 */
	private static Map<String, FixDataBase> data = new HashMap<String, FixDataBase>();

	/**
	 * ロックオブジェクト.
	 */
	private static Object _lock = new Object();

	/**
	 * インスタンスの生成.
	 */
	protected FixDataBase() {
		synchronized (_lock) {
			String className = this.getClass().getSimpleName().substring(3);
			if (data.get(className) != null) {
				throw new RuntimeException("Already created: " + className);
			}
			data.put(className, this);
		}
	}

	/**
	 * インスタンスの取得. インスタンスの生成されていないデータクラスを取得しようとした場合は、インスタンスの生成を行う.
	 *
	 * @param key
	 * @return
	 */
	public static FixDataBase getInstance(String key) {
		synchronized (_lock) {
			FixDataBase obj = (FixDataBase) data.get(key);
			if (obj == null) {
				try {
					Class<? extends Object> cls = Class.forName("jp.game.dao.data.Dao" + key);
					obj = (FixDataBase) cls.newInstance();
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(key + " is not found");
				} catch (IllegalAccessException e) {
					throw new RuntimeException(key + " cannot be accessed.");
				} catch (InstantiationException e) {
					throw new RuntimeException(key + " cannot be instantiated.");
				}
			}
			return obj;
		}
	}
}
