package jp.game.service;

import java.util.List;

import jp.game.common.methods.GameLog;
import jp.game.dao.CommonDaoFactory;
import jp.game.dao.DaoValue;
import jp.game.tbl.info.TblWorldInfo;

public class WorldService {
	public static World getWorldInfo() {
		TblWorldInfo tblWorldInfo = new TblWorldInfo();
		tblWorldInfo.Set("id", 1);
		
		List<DaoValue> listDaoValue = CommonDaoFactory.Select(tblWorldInfo);
		if (listDaoValue.isEmpty() || listDaoValue == null) {
			GameLog.getInstance().debug("World null");
			return null;
		}
		
		return new World((TblWorldInfo) listDaoValue.get(0));
	}
}
