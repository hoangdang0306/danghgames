package jp.game.dao.fix;

import java.util.Map;

import javax.servlet.ServletContext;

import jp.game.core.localize.GameLocale;
import jp.game.dao.DaoValue;

public class FixDataLoader {

	private static final String[][] fixDataClassNames = {
		{ "CardBaseData", "card_base_data" },
	};

	/**
	 * @param context
	 */
	public static void init(ServletContext context) {
		// 固定データのファイルパスを設定する.
		for (String[] name : fixDataClassNames) {
			FixData data = (FixData) FixDataBase.getInstance(name[0]);
			data.setFilePath(context.getRealPath(GameLocale.getFixDataDir() + "/" + name[1] + ".csv"));
			context.setAttribute(name[1], data);

			// とりあえず全部読み込んでみる.
			FixData scdata = (FixData) context.getAttribute(name[1]);
			@SuppressWarnings("unused")
			Map<Integer, DaoValue> mapdata = scdata.getData();
		}
//		dumpMemoryUsage();
	}

	protected static void dumpMemoryUsage() {
		Runtime rt = Runtime.getRuntime();
		long max = rt.maxMemory();
		long total = rt.totalMemory();
		long free = rt.freeMemory();
		long used = (total - free);
		System.out.println("  maxMemory = " + (max / 1024.0 / 1024));
		System.out.println("totalMemory = " + (total / 1024.0 / 1024));
		System.out.println(" freeMemory = " + (free / 1024.0 / 1024));
		System.out.println(" usedMemory = " + (used / 1024.0 / 1024));
	}

	/**
	 * @param context
	 */
	public static void destroy(ServletContext context) {
		// 固定データをremoveしておく.
		for (String[] name : fixDataClassNames) {
			context.removeAttribute(name[1]);
		}
	}
}
