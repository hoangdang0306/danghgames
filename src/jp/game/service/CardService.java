package jp.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.game.card.Card;
import jp.game.common.methods.GameLog;
import jp.game.dao.CommonDaoFactory;
import jp.game.dao.DaoValue;
import jp.game.dao.fix.FixData;
import jp.game.dao.fix.FixDataBase;
import jp.game.enums.CardStatus;
import jp.game.tbl.data.TblCardBaseData;
import jp.game.tbl.info.TblCardInfo;

public class CardService {

	public static List<TblCardBaseData> getCardDataList() {
		FixData fixData = (FixData) FixDataBase.getInstance("CardBaseData");
		Map<Integer, DaoValue> cardBaseDataMap = fixData.getData();
		List<TblCardBaseData> list = new ArrayList<>();
		
		for(Map.Entry<Integer, DaoValue> entry : cardBaseDataMap.entrySet()) {
			list.add((TblCardBaseData) entry.getValue());
		}
		
		return list;
	}
	
	public static TblCardBaseData getTblCardDataById(int id) {
		FixData fixData = (FixData) FixDataBase.getInstance("CardBaseData");
		Map<Integer, DaoValue> cardBaseDataMap = fixData.getData();
		
		for(Map.Entry<Integer, DaoValue> entry : cardBaseDataMap.entrySet()) {
			if (Integer.parseInt(entry.getValue().Get("id").toString()) == id) {
				return (TblCardBaseData) entry.getValue();
			}
		}
		
		return null;
	}
	
	public static List<TblCardInfo> getCardInfoListByPlayerId(int playerId) {
		if (playerId <= 0) {
			return null;
		}
		
		TblCardInfo tblCardInfo = new TblCardInfo();
		tblCardInfo.Set("player_id", playerId);
		
		List<DaoValue> list = CommonDaoFactory.Select(tblCardInfo);
		if (list == null) {
			GameLog.getInstance().debug("list card info null");
			return null;
		}
		
		if (list.isEmpty()) {
			GameLog.getInstance().debug("list card info null");
			return null;
		}
		
		List<TblCardInfo> listResult = new ArrayList<>();
		for(DaoValue daoValue : list) {
			if (daoValue instanceof TblCardInfo) {
				listResult.add((TblCardInfo) daoValue);
			}
		}
		
		return listResult;
	}
	
	public static List<Card> getListCardByPlayerId(int playerId) {
		if (playerId <= 0) {
			return null;
		}
		
		TblCardInfo tblCardInfo = new TblCardInfo();
		tblCardInfo.Set("player_id", playerId);
		
		List<DaoValue> list = CommonDaoFactory.Select(tblCardInfo);
		if (list == null) {
			GameLog.getInstance().debug("list card info null");
			return null;
		}
		
		if (list.isEmpty()) {
			GameLog.getInstance().debug("list card info null");
			return null;
		}
		
		List<Card> listCard = new ArrayList<>();
		for(DaoValue daoValue : list) {
			if (daoValue instanceof TblCardInfo) {
				int cardBaseId = Integer.parseInt(daoValue.Get("id").toString());
				TblCardBaseData tblCardBaseData = getTblCardDataById(cardBaseId);
				
				if (tblCardBaseData == null) {
					return null;
				}
				
				Card card = new Card((TblCardInfo) daoValue, tblCardBaseData);
				listCard.add(card);
			}
		}
		
		return listCard;
	}
	
	public static void initCard(TblCardBaseData tblCardBaseData, TblCardInfo tblCardInfo, int playerId) {
		if (tblCardBaseData == null || tblCardInfo == null) {
			return;
		}
		
		tblCardInfo.Set("player_id", playerId);
		tblCardInfo.Set("card_base_id", tblCardBaseData.Get("id"));
		tblCardInfo.Set("confident", tblCardBaseData.Get("confident"));
		tblCardInfo.Set("knowledge", tblCardBaseData.Get("knowledge"));
		tblCardInfo.Set("communication", tblCardBaseData.Get("communication"));
		tblCardInfo.Set("stamina", tblCardBaseData.Get("stamina"));
		tblCardInfo.Set("leadership", tblCardBaseData.Get("leadership"));
		tblCardInfo.Set("salary", tblCardBaseData.Get("salary"));
		tblCardInfo.Set("experience", tblCardBaseData.Get("experience"));
		tblCardInfo.Set("skill_1", tblCardBaseData.Get("skill_1"));
		tblCardInfo.Set("skill_2", tblCardBaseData.Get("skill_2"));
		tblCardInfo.Set("skill_3", tblCardBaseData.Get("skill_3"));
		tblCardInfo.Set("employee_level", tblCardBaseData.Get("employee_level"));
		tblCardInfo.Set("status", CardStatus.RESERVE.getId());
	}
}
