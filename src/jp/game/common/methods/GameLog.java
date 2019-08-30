package jp.game.common.methods;

import javax.servlet.http.HttpSession;

import org.apache.click.service.ConsoleLogService;
import org.apache.click.util.HtmlStringBuffer;
import org.apache.commons.logging.LogFactory;

import jp.game.common.CommonDefine;

import org.apache.click.Context;

/**
 *
 */
public final class GameLog extends ConsoleLogService {

	/**
	 *
	 */
	private static final class SingletonHolder {
		static final GameLog singleton = new GameLog();
	}

	/**
	 *
	 */
	private GameLog() {
		this.name = "nya";
		this.setLevel(0); // for agent.
	}

	/**
	 * @return
	 */
	public static GameLog getInstance() {
		return SingletonHolder.singleton;
	}

	/**
	 * @param isProductionMode true if the application is in "production" mode
	 *
	 */
	public void adjustLogLevel(boolean isProductionMode) {
		if (isProductionMode) {
			this.setLevel(ConsoleLogService.INFO_LEVEL);
		} else {
			this.setLevel(ConsoleLogService.DEBUG_LEVEL);
		}
	}

	@Override
	public void setLevel(int level) {
		super.setLevel(level);
		System.out.println("GameLog.setLevel:" + level);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see org.apache.click.service.ConsoleLogService#log(int,
	 * java.lang.String, java.lang.Throwable)
	 */
	@Override
	protected void log(int level, String message, Throwable error) {
		if (logLevel == 4) { // !!! テストが終わったら削除
			LogFactory.getLog(GameLog.class).info(message);
			return;
		}
		if (level < logLevel) {
			return;
		}

		HtmlStringBuffer buffer = new HtmlStringBuffer();

		buffer.append("[");
		buffer.append(CommonMethod.getFormatStringNow());
		buffer.append("] ");
		buffer.append("[");
		buffer.append(name);
		buffer.append("]");

		buffer.append(LEVELS[level + 1]);

		if (Context.hasThreadLocalContext()) {
			Context context = Context.getThreadLocalContext();
			Session infoSession = null;

			HttpSession session = context.getSession();
			if (session != null) {
				infoSession = (Session) session.getAttribute(CommonDefine.GAME_SESSION);
			}

			if (infoSession != null) {
				message = String.format("[CAT:%d] ", infoSession.getID()) + message;
			}
		}

		buffer.append(message);

		if (error != null) {
			System.out.print(buffer.toString());
			error.printStackTrace(System.out);
		} else {
			System.out.println(buffer.toString());
		}
	}

}
