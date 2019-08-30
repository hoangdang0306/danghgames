package jp.game.enums;

public enum CardStatus {
	NONE(0),
	DECK(1),
	RESERVE(2),
	;
	
	private final int id;
	
	CardStatus(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	public static CardStatus getCardStatusById(int id) {
		for (int i = 0; i < CardStatus.values().length; i++) {
			if (id == CardStatus.values()[i].getId()) {
				return CardStatus.values()[i];
			}
		}
		
		return CardStatus.NONE;
	}
}
