package jp.game.service;

import jp.game.tbl.info.TblPlayerInfo;

public class Player {
	private TblPlayerInfo tblPlayerInfo;
	
	public Player() {
		this.tblPlayerInfo = new TblPlayerInfo();
	};
	
	public Player(TblPlayerInfo tbl) {
		this.tblPlayerInfo = tbl;
	}
	
	public void setId(int id) {
		this.tblPlayerInfo.Set("id", id);
	}
	
	public int getId() {
		return Integer.parseInt(this.tblPlayerInfo.Get("id").toString());
	}
	
	public String getName() {
		return tblPlayerInfo.Get("name").toString();
	}
	
	public void setName(String name) {
		this.tblPlayerInfo.Set("name", name);
	}
	
	public int getGold() {
		return Integer.parseInt(this.tblPlayerInfo.Get("gold").toString());
	}
	
	public void setGold(int gold) {
		this.tblPlayerInfo.Set("gold", gold);
	}
	
	public int getBattleId() {
		return Integer.parseInt(this.tblPlayerInfo.Get("battle_id").toString());
	}
	
	public void setBattleId(int battle_id) {
		this.tblPlayerInfo.Set("battle_id", battle_id);
	}
	
	public TblPlayerInfo getTblPlayerInfo() {
		return this.tblPlayerInfo;
	}
	
	public void setTblPlayerInfo(TblPlayerInfo tbl) {
		this.tblPlayerInfo = tbl;
	}
}
