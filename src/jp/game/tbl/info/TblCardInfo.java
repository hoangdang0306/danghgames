package jp.game.tbl.info;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import jp.game.dao.DaoValueInfo;
import jp.game.dao.IDaoValue;

public class TblCardInfo extends DaoValueInfo implements IDaoValue {
	private static final String TABLE_NAME = "card_info";
	private static final String PRIMARY_KEY = "id";
	private static final String SUB_KEY = "";
	private static Map<String, String> mapForeignKey = new HashMap<String, String>();

	public final class Fields implements IDaoValue.Fields {

		public int id;
		public int player_id;
		public int card_base_id;
		public short confident;
		public short knowledge;
		public short communication;
		public short stamina;
		public short leadership;
		public int salary;
		public short experience;
		public short skill_1;
		public short skill_2;
		public short skill_3;
		public short employee_level;
		public byte status;

		public Fields() {
			id = 0;
			player_id = 0;
			card_base_id = 0;
			confident = 0;
			knowledge = 0;
			communication = 0;
			stamina = 0;
			leadership = 0;
			salary = 0;
			experience = 0;
			skill_1 = 0;
			skill_2 = 0;
			skill_3 = 0;
			employee_level = 0;
			status = 0;
		}
	}

	private Fields fieldRead;

	private Fields fieldWrite;

	private static Field[] fields;

	/**
	 *
	 */
	public TblCardInfo() {
		fieldRead = new Fields();
		fieldWrite = new Fields();

		if (fields == null) {
			fields = fieldRead.getClass().getFields();
		}
	}

	public String getTblName() {
		return TABLE_NAME;
	}

	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}

	public String getSubKey() {
		return SUB_KEY;
	}

	public String getForeignKey(String strKey) {
		return mapForeignKey.get(strKey);
	}

	public Field[] getClassField() {
		return fields;
	}

	public Object getFieldRead() {
		return (Object) fieldRead;
	}

	public Object getFieldWrite() {
		return (Object) fieldWrite;
	}

	public Fields getInstance() {
		return fieldWrite;
	}

	public void Sync() {
		fieldRead.id			= fieldWrite.id;
		fieldRead.player_id		= fieldWrite.player_id;
		fieldRead.card_base_id 	= fieldWrite.card_base_id;
		fieldRead.confident		= fieldWrite.confident;
		fieldRead.knowledge		= fieldWrite.knowledge;
		fieldRead.communication = fieldWrite.communication;
		fieldRead.stamina		= fieldWrite.stamina;
		fieldRead.leadership	= fieldWrite.leadership;
		fieldRead.salary		= fieldWrite.salary;
		fieldRead.experience	= fieldWrite.experience;
		fieldRead.skill_1		= fieldWrite.skill_1;
		fieldRead.skill_2		= fieldWrite.skill_2;
		fieldRead.skill_3		= fieldWrite.skill_3;
		fieldRead.employee_level = fieldWrite.employee_level;
		fieldRead.status		= fieldWrite.status;
	}
}
