package jp.game.service;

import java.util.List;

import jp.game.common.methods.GameLog;
import jp.game.dao.CommonDaoFactory;
import jp.game.dao.DaoValue;
import jp.game.tbl.info.TblPlayerInfo;

public class PlayerService {
	public static Player getPlayerById(int id) {
		Player player = new Player();
		player.setId(id);
		
		List<DaoValue> list = CommonDaoFactory.Select(player.getTblPlayerInfo());
		if (list == null) {
			return null;
		}
		
		if (list.isEmpty()) {
			return null;
		}
		
		return new Player((TblPlayerInfo) list.get(0));
	}
	
	public static boolean registerPlayer(TblPlayerInfo playerInfo) {
		if (playerInfo == null) {
			return false;
		}
		
		if (CommonDaoFactory.Insert(playerInfo) < 0) {
			GameLog.getInstance().debug("Player Service: Insert Player Failed");
			return false;
		}
		
		return true;
	}
}
