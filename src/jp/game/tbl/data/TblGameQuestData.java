package jp.game.tbl.data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import jp.game.dao.DaoValueData;
import jp.game.dao.IDaoValue;

public final class TblGameQuestData extends DaoValueData implements IDaoValue {
    private static final String TABLE_NAME    = "game_quest_data";
    private static final String PRIMARY_KEY    = "id";
    private static final String SUB_KEY        = "";
    private static Map<String, String> mapForeignKey = new HashMap<String, String>();

    public final class Fields implements IDaoValue.Fields {
        /** id */
        public short id;

        /** player_level_required */
        public int player_level;

        /** score_required */
        public int score;

        /**
         *
         */
        public Fields() {
            id                = 0;
            player_level    = 0;
            score            = 0;
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
    public TblGameQuestData() {
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