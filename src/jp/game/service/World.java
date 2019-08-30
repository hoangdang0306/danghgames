package jp.game.service;

import jp.game.tbl.info.TblWorldInfo;

public class World {
	private TblWorldInfo tblWorldInfo;
	
	public World() {}
	
	public World(TblWorldInfo tbl) {
		this.tblWorldInfo = tbl;
	}
	
	public int getId() {
		return Integer.parseInt(this.tblWorldInfo.Get("id").toString());
	}
	
	public String getName() {
		return tblWorldInfo.Get("name").toString();
	}
	
	public void setName(String name) {
		this.tblWorldInfo.Set("name", name);
	}
	
	public int getEventFlag() {
		return Integer.parseInt(this.tblWorldInfo.Get("event_flag").toString());
	}
	
	public void setEventFlag(int event_flag) {
		this.tblWorldInfo.Set("event_flag", event_flag);
	}
}
