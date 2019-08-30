package jp.game.tbl.info;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import jp.game.dao.DaoValueInfo;
import jp.game.dao.IDaoValue;

public class TblPlayerInfo extends DaoValueInfo implements IDaoValue {
	private static final String TABLE_NAME = "player_info";
	private static final String PRIMARY_KEY = "id";
	private static final String SUB_KEY = "";
	private static Map<String, String> mapForeignKey = new HashMap<String, String>();
	
	public final class Fields implements IDaoValue.Fields {

		public int id;
		public String name;
		public int gold;
		public int battle_id;

		public Fields() {
			id			= 0;
			name		= "";
			gold		= 0;
			battle_id	= 0;
		}
	}

	private Fields fieldRead;

	private Fields fieldWrite;

	private static Field[] fields;

	/**
	 *
	 */
	public TblPlayerInfo() {
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
		fieldRead.name			= fieldWrite.name;
		fieldRead.gold			= fieldWrite.gold;
		fieldRead.battle_id		= fieldWrite.battle_id;
	}
}
