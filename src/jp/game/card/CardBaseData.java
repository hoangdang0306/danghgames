package jp.game.card;

import jp.game.enums.JobType;
import jp.game.tbl.data.TblCardBaseData;

public class CardBaseData {
	private TblCardBaseData tblCardBaseData;
	
	public CardBaseData(TblCardBaseData tbl) {
		this.tblCardBaseData = tbl;
	}
	public int getId() {
		return Integer.parseInt(this.tblCardBaseData.Get("id").toString());
	}
	
	public String getName() {
		return this.tblCardBaseData.Get("name").toString();
	}
	
	public short getConfident() {
		return Short.parseShort(this.tblCardBaseData.Get("confident").toString());
	}
	
	public short getKnowledge() {
		return Short.parseShort(this.tblCardBaseData.Get("knowledge").toString());
	}
	
	public short getCommunication() {
		return Short.parseShort(this.tblCardBaseData.Get("communication").toString());
	}
	
	public short getStamina() {
		return Short.parseShort(this.tblCardBaseData.Get("stamina").toString());
	}
	
	public short getLeadership() {
		return Short.parseShort(this.tblCardBaseData.Get("leadership").toString());
	}
	
	public short getSalary() {
		return Short.parseShort(this.tblCardBaseData.Get("salary").toString());
	}
	
	public short getExperience() {
		return Short.parseShort(this.tblCardBaseData.Get("experience").toString());
	}
	
	public short getSkill1() {
		return Short.parseShort(this.tblCardBaseData.Get("skill_1").toString());
	}
	
	public short getSkill2() {
		return Short.parseShort(this.tblCardBaseData.Get("skill_2").toString());
	}
	
	public short getSkill3() {
		return Short.parseShort(this.tblCardBaseData.Get("skill_3").toString());
	}
	
	public short getEmployeeLevel() {
		return Short.parseShort(this.tblCardBaseData.Get("employee_level").toString());
	}
	
	public JobType getJobType() {
		int job = Integer.parseInt(this.tblCardBaseData.Get("job_type").toString());
		return JobType.getJobTypeById(job);
	}
}
