package jp.game.service;

import jp.game.enums.AccountFlagEnum;
import jp.game.tbl.info.TblAccountInfo;

public class Account {
	private TblAccountInfo tblAccountInfo;
	
	public Account() {}
	
	public Account(TblAccountInfo tbl) {
		this.tblAccountInfo = tbl;
	}
	
	public int getId() {
		return Integer.parseInt(this.tblAccountInfo.Get("id").toString());
	}
	
	public void setId(int id) {
		this.tblAccountInfo.Set("id", id);
	}
	
	public String getUsername() {
		return this.tblAccountInfo.Get("username").toString();
	}
	
	public void setUsername(String username) {
		this.tblAccountInfo.Set("username", username);
	}
	
	public String getPassword() {
		return this.tblAccountInfo.Get("password").toString();
	}
	
	public void setPassword(String password) {
		this.tblAccountInfo.Set("password", password);
	}
	
	public int getType() {
		return Integer.parseInt(this.tblAccountInfo.Get("type").toString());
	}
	
	public void setType(int type) {
		this.tblAccountInfo.Set("type", type);
	}
	
	public boolean isAdmin() {
		return ((getType() & AccountFlagEnum.ADMIN.getId()) != 0);
	}
	
	public boolean isType(AccountFlagEnum type) {
		return ((getType() & type.getId()) != 0);
	}
}
