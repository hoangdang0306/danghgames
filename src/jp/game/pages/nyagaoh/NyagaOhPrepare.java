package jp.game.pages.nyagaoh;

import java.util.List;

import org.apache.click.element.CssImport;

import jp.game.card.Card;
import jp.game.common.methods.GameLog;
import jp.game.pages.GamePage;
import jp.game.service.CardService;
import jp.game.service.Player;

public class NyagaOhPrepare extends GamePage {
	private static final long serialVersionUID = 1L;

	public NyagaOhPrepare() {
		GameLog.getInstance().debug("NyagaOh Prepare Page");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();
		}
		
		String contextPath = getHostURL() + getContextPath();
		headElements.add(new CssImport(contextPath + "/css/nyagaoh/nyagaoh.css"));
		
		return headElements;
	}
	
	@Override
	public boolean onSecurityCheck() {
		return super.onSecurityCheck();
	}
	
	@Override
	public void onInit() {
		super.onInit();
		
//		FixData fixData = (FixData) FixDataBase.getInstance("CardBaseData");
//		Map<Integer, DaoValue> cardBaseDataMap = fixData.getData();
//		
//		List<DaoValue> listInsert = new ArrayList<>();
//		for(Map.Entry<Integer, DaoValue> entry : cardBaseDataMap.entrySet()) {
//			TblCardInfo tblCardInfo = new TblCardInfo();
//			CardService.initCard((TblCardBaseData) entry.getValue(), tblCardInfo, getPlayer().getId());
//			
//			listInsert.add(tblCardInfo);
//		}
//		
//		if (CommonDaoFactory.Insert(listInsert) < 0) {
//			GameLog.getInstance().debug(0);
//		}
	}
	
	@Override
	public void onRender() {
		super.onRender();
		
		printReserveCard();
	}
	
	private void printReserveCard() {
		Player player = getPlayer();
		if (player == null) {
			return;
		}
		
		List<Card> listCard = CardService.getListCardByPlayerId(player.getId());
		if (listCard == null) {
			GameLog.getInstance().debug("NyagaOhPrepare list card null");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		for (Card card : listCard) {
			sb.append("<div class=\"reserve-shadow frame\" id=\"card" + card.getId() + "\">" + card.getName() + "</div>");
		}
		
		addModel("info", sb.toString());
	}
}
