package jp.game.pages;

import java.util.List;

import jp.game.service.Player;

public class GamePage extends TemplatePage {

	private static final long serialVersionUID = 1L;
	private Player player;
	public GamePage() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();

		}
		return headElements;
	}

	@Override
	public boolean onSecurityCheck() {
		player = getPlayer();
		if (player == null) {
			return false;
		}
		return true;
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onRender() {
	}
}
