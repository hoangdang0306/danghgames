package jp.game.dao.data;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;

import jp.game.dao.CommonDaoFactory;
import jp.game.dao.DaoValue;
import jp.game.dao.IDaoFactory;
import jp.game.dao.fix.FixData;
import jp.game.tbl.data.TblCardBaseData;

public final class DaoItemData extends FixData implements IDaoFactory {
    public List<DaoValue> select(Connection conn, List<DaoValue> self) {
        return CommonDaoFactory._SelectFix(self);
    }

    public List<DaoValue> select(Connection conn, DaoValue self) {
        return CommonDaoFactory._SelectFix(self);
    }

    public int insert(Connection conn, DaoValue self) {
        return 0;
    }

    public int delete(Connection conn, DaoValue self) {
        return 0;
    }

    public int update(Connection conn, DaoValue self) {
        return 0;
    }

    public int count(Connection conn, DaoValue self) {
        return CommonDaoFactory._CountFix(self);
    }

    @Override
    public long max(Connection conn, DaoValue self) {
        return CommonDaoFactory._MaxFix(self);
    }

    @Override
    public long min(Connection conn, DaoValue self) {
        return CommonDaoFactory._MinFix(self);
    }

    public int getReadKey() {
        return 0;
    }

    @Override
    protected Field[] getField() {
        TblCardBaseData tbl = new TblCardBaseData();
        return tbl.getClassField();
    }
}
