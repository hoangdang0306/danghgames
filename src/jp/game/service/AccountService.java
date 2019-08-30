package jp.game.service;

import java.util.List;

import jp.game.common.methods.GameLog;
import jp.game.dao.CommonDaoFactory;
import jp.game.dao.DaoValue;
import jp.game.tbl.info.TblAccountInfo;

public class AccountService {
	public static Account getAccountByName(String user) {
		TblAccountInfo tblAccountInfo = new TblAccountInfo();
		tblAccountInfo.Set("username", user);
		
		List<DaoValue> resultList = CommonDaoFactory.Select(tblAccountInfo);
		if (resultList.isEmpty() || resultList == null) {
			GameLog.getInstance().debug("Account Service: resultlist empty");
			return null;
		}
		
		return new Account((TblAccountInfo) resultList.get(0));
	}
}
