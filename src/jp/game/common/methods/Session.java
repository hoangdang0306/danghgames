package jp.game.common.methods;

import java.io.Serializable;

public final class Session implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;


	/**
	 *
	 */
	public Session(int id, String name) {
		this.id		= id;
		this.name	= name;
	}

	/**
	 *
	 * @return
	 */
	public int getID() {
		return this.id;
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return this.name;
	}
}




