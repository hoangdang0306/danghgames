package jp.game.enums;

public enum JobType {
	NoJob(0, ""),
	Boss(1, "MSG_GAME_JOB_BOSS"),
	Administrator(2, ""),
	CG(3, "MSG_GAME_JOB_CG"),
	DEV(4, "MSG_GAME_JOB_DEV"),
	QA(5, "MSG_GAME_JOB_QA"),
	BossOversea(6, "MSG_GAME_JOB_BOSS_OVERSEA"),
	AdministratorOversea(7, "MSG_GAME_JOB_ADMINISTRATOR_OVERSEA"),
	CGOversea(8, "MSG_GAME_JOB_CG_OVERSEA"),
	DEVOversea(9, "MSG_GAME_JOB_DEV_OVERSEA"),
	QAOversea(10, "MSG_GAME_JOB_QA_OVERSEA"),
	;
	
	private final int jobId;
	private final String jobName;
	
	JobType(int jobId, String jobName) {
		this.jobId = jobId;
		this.jobName = jobName;
	}
	
	public int getId() {
		return this.jobId;
	}
	
	public String getJobName() {
		return this.jobName;
	}
	
	public static JobType getJobTypeById(int id) {
		for (int i = 0; i < JobType.values().length; i++) {
			if (id == JobType.values()[i].getId()) {
				return JobType.values()[i];
			}
		}
		
		return JobType.NoJob;
	}
}
