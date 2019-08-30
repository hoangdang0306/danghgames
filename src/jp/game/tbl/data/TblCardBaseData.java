package jp.game.tbl.data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import jp.game.dao.DaoValueData;
import jp.game.dao.IDaoValue;

public final class TblCardBaseData extends DaoValueData implements IDaoValue {
	private static final String TABLE_NAME = "card_base_data";
	private static final String PRIMARY_KEY = "id";
	private static final String SUB_KEY = "";
	private static Map<String, String> mapForeignKey = new HashMap<String, String>();

	public final class Fields implements IDaoValue.Fields {
		public int id;
		public String name;
		public int confident;
		public int knowledge;
		public int communication;
		public int stamina;
		public int leadership;
		public int salary;
		public int experience;
		public int skill_1;
		public int skill_2;
		public int skill_3;
		public int job_type;
		public int employee_level;
		public Fields() {
			id = 0;
			name = "";
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
			job_type = 0;
			employee_level = 0;
		}
	}

	private Fields fieldRead;

	private Fields fieldWrite;

	private static Field[] fields;

	public TblCardBaseData() {
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
	}
}