package jp.game.tbl.data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import jp.game.dao.DaoValueData;
import jp.game.dao.IDaoValue;

public final class TblItemData extends DaoValueData implements IDaoValue {
    private static final String TABLE_NAME    = "item_data";
    private static final String PRIMARY_KEY    = "id";
    private static final String SUB_KEY        = "";
    private static Map<String, String> mapForeignKey = new HashMap<String, String>();

    public final class Fields implements IDaoValue.Fields {
        /** id */
        public short id;

        /** name */
        public String name;

        /** description */
        public String desc;

        /** type */
        public int type;

        /** param1 */
        public int para1;

        /** param2 */
        public int para2;

        /**
         *
         */
        public Fields() {
            id		= 0;
            name	= "";
            desc	= "";
            type 	= 0;
            para1 	= 0;
            para2 	= 0;
        }
    }

    /** テーブル（比較用） */
    private Fields fieldRead;

    /** テーブル（更新用） */
    private Fields fieldWrite;

    /** Fields.classのメンバ変数保存用 */
    private static Field[] fields;

    /**
     *
     */
    public TblItemData() {
        fieldRead    = new Fields();
        fieldWrite    = new Fields();

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
        return (Object)fieldRead;
    }

    public Object getFieldWrite() {
        return (Object)fieldWrite;
    }

    public Fields getInstance() {
        return fieldWrite;
    }

    // Interface Definiton.
    public void Sync() {
    }
}