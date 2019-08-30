package jp.game.enums;

public enum AccountFlagEnum {
	ADMIN(0x01),
	NYAGAOH(0x02),
	OLYMPIA(0x04),
	CANNON(0x08)
	;
	
	private final int id;
	
	AccountFlagEnum(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
}
