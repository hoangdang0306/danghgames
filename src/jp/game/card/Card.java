package jp.game.card;

import jp.game.enums.CardStatus;
import jp.game.enums.JobType;
import jp.game.tbl.data.TblCardBaseData;
import jp.game.tbl.info.TblCardInfo;

public class Card {
	private TblCardInfo tblCardInfo;
	private TblCardBaseData tblCardBaseData;
	
	public Card() {
		
	}
	
	public Card(TblCardInfo tblCardInfo, TblCardBaseData tblCardBaseData) {
		this.tblCardInfo = tblCardInfo;
		this.tblCardBaseData = tblCardBaseData;
	}
	
	public int getId() {
		return Integer.parseInt(this.tblCardInfo.Get("id").toString());
	}
	
	public int getPlayerId() {
		return Integer.parseInt(this.tblCardInfo.Get("player_id").toString());
	}
	
	public void setPlayerId(int playerId) {
		this.tblCardInfo.Set("player_id", playerId);
	}
	
	public String getName() {
		return this.tblCardBaseData.Get("name").toString();
	}
	
	public int getCardBaseId() {
		return Integer.parseInt(this.tblCardInfo.Get("card_base_id").toString());
	}
	
	public void setCardBaseId(int id) {
		this.tblCardInfo.Set("card_base_id", id);
	}
	
	public short getConfident() {
		return Short.parseShort(this.tblCardInfo.Get("confident").toString());
	}
	
	public void setConfident(short confident) {
		this.tblCardInfo.Set("confident", confident);
	}
	
	public short getKnowledge() {
		return Short.parseShort(this.tblCardInfo.Get("knowledge").toString());
	}
	
	public void setKnowledge(short knowledge) {
		this.tblCardInfo.Set("knowledge", knowledge);
	}
	
	public short getCommunication() {
		return Short.parseShort(this.tblCardInfo.Get("communication").toString());
	}
	
	public void setCommunication(short communication) {
		this.tblCardInfo.Set("communication", communication);
	}
	
	public short getStamina() {
		return Short.parseShort(this.tblCardInfo.Get("stamina").toString());
	}
	
	public void setStamina(short stamina) {
		this.tblCardInfo.Set("stamina", stamina);
	}
	
	public short getLeadership() {
		return Short.parseShort(this.tblCardInfo.Get("leadership").toString());
	}
	
	public void setLeadership(short leadership) {
		this.tblCardInfo.Set("leadership", leadership);
	}
	
	public int getSalary() {
		return Integer.parseInt(this.tblCardInfo.Get("salary").toString());
	}
	
	public void setSalary(int salary) {
		this.tblCardInfo.Set("salary", salary);
	}
	
	public short getExperience() {
		return Short.parseShort(this.tblCardInfo.Get("experience").toString());
	}
	
	public void setExperience(short experience) {
		this.tblCardInfo.Set("experience", experience);
	}
	
	public short getSkill1() {
		return Short.parseShort(this.tblCardInfo.Get("skill_1").toString());
	}
	
	public void setSkill1(short skill_1) {
		this.tblCardInfo.Set("skill_1", skill_1);
	}
	
	public short getSkill2() {
		return Short.parseShort(this.tblCardInfo.Get("skill_2").toString());
	}
	
	public void setSkill2(short skill_2) {
		this.tblCardInfo.Set("skill_2", skill_2);
	}
	
	public short getSkill3() {
		return Short.parseShort(this.tblCardInfo.Get("skill_3").toString());
	}
	
	public void setSkill3(short skill_3) {
		this.tblCardInfo.Set("skill_3", skill_3);
	}
	
	public short getEmployeeLevel() {
		return Short.parseShort(this.tblCardInfo.Get("employee_level").toString());
	}
	
	public void setEmployeeLevel(short employee_level) {
		this.tblCardInfo.Set("employee_level", employee_level);
	}
	
	public JobType getJobType() {
		int job = Integer.parseInt(this.tblCardBaseData.Get("job_type").toString());
		return JobType.getJobTypeById(job);
	}
	
	public CardStatus getCardStatus() {
		int status = Integer.parseInt(this.tblCardInfo.Get("status").toString());
		return CardStatus.getCardStatusById(status);
	}
	
	public TblCardInfo getTblCardInfo() {
		return this.tblCardInfo;
	}
}
