/**
 *
 */
package jp.game.dao.info;

import java.sql.Connection;
import java.util.List;

import jp.game.dao.CommonDaoFactory;
import jp.game.dao.DaoValue;
import jp.game.dao.IDaoFactory;

/**
 * @author daisuke-ogihara
 *
 */
public class DaoPlayerInfo implements IDaoFactory {
	private static final int nReadKey = 1;

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.nobunyaga.dao.IDaoFactory#select(java.sql.Connection,
	 * java.util.List)
	 */
	public List<DaoValue> select(Connection conn, List<DaoValue> self) {
		return CommonDaoFactory._Select(conn, self);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.nobunyaga.dao.IDaoFactory#select(java.sql.Connection,
	 * jp.nobunyaga.dao.DaoValue)
	 */
	public List<DaoValue> select(Connection conn, DaoValue self) {
		return CommonDaoFactory._Select(conn, self);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.nobunyaga.dao.IDaoFactory#insert(java.sql.Connection,
	 * jp.nobunyaga.dao.DaoValue)
	 */
	public int insert(Connection conn, DaoValue self) {
		return CommonDaoFactory._Insert(conn, self);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.nobunyaga.dao.IDaoFactory#delete(java.sql.Connection,
	 * jp.nobunyaga.dao.DaoValue)
	 */
	public int delete(Connection conn, DaoValue self) {
		return CommonDaoFactory._Delete(conn, self);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.nobunyaga.dao.IDaoFactory#update(java.sql.Connection,
	 * jp.nobunyaga.dao.DaoValue)
	 */
	public int update(Connection conn, DaoValue self) {
		return CommonDaoFactory._Update(conn, self);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.nobunyaga.dao.IDaoFactory#count(java.sql.Connection,
	 * jp.nobunyaga.dao.DaoValue)
	 */
	public int count(Connection conn, DaoValue self) {
		return CommonDaoFactory._Count(conn, self);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.nobunyaga.dao.IDaoFactory#max(java.sql.Connection,
	 * jp.nobunyaga.dao.DaoValue)
	 */
	@Override
	public long max(Connection conn, DaoValue self) {
		return CommonDaoFactory._Max(conn, self);
	}

	/* (非 Javadoc)
	 * @see jp.nobunyaga.dao.IDaoFactory#min(java.sql.Connection, jp.nobunyaga.dao.DaoValue)
	 */
	@Override
	public long min(Connection conn, DaoValue self) {
		return CommonDaoFactory._Min(conn, self);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.nobunyaga.dao.IDaoFactory#getReadKey()
	 */
	public int getReadKey() {
		return nReadKey;
	}

}
