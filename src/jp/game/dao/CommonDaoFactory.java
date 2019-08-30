package jp.game.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jp.game.common.methods.GameLog;
import jp.game.dao.fix.FixData;
import jp.game.dao.fix.FixDataBase;

/* ---------------------------------------------------------------- */
/*
/* 基本クエリ共通化のため（）

/*
/* ---------------------------------------------------------------- */

public class CommonDaoFactory{
	/**
	 * 各データクラスのインスタンスを格納しておく.
	 */
	private static Map<String, IDaoFactory> data = new HashMap<String, IDaoFactory>();

	/**
	 * ロックオブジェクト.
	 */
	private static Object _lock = new Object();

	/**
	 * インスタンスの取得. インスタンスの生成されていないデータクラスを取得しようとした場合は、インスタンスの生成を行う.
	 *
	 * @param self DaoValue
	 * @return IDaoFactory
	 */
	protected static IDaoFactory getDaoClass(DaoValue self) {
		if (self instanceof DaoValueData) {
			return (IDaoFactory)FixDataBase.getInstance(self.getClass().getSimpleName().substring(3));
		}

		synchronized (_lock) {
			String strKey = self.getClass().getSimpleName();
			IDaoFactory IDaoObj = (IDaoFactory)data.get(strKey);
			if (IDaoObj == null) {
				try {
					String strName = getDaoName(self.getClass().getName());
					Class<? extends Object> clsDao  = Class.forName(strName);

					IDaoObj = (IDaoFactory) clsDao.newInstance();

					data.put(strKey, IDaoObj);
					GameLog.getInstance().debug(String.format("[DB] IDaoFactory (%s) added.", strName));
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(strKey + " is not found");
				} catch (IllegalAccessException e) {
					throw new RuntimeException(strKey + " cannot be accessed.");
				} catch (InstantiationException e) {
					throw new RuntimeException(strKey + " cannot be instantiated.");
				}
			}
			return IDaoObj;
		}
	}

	public CommonDaoFactory() {
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static List<DaoValue> Select(List<DaoValue> self) {
		if (self == null) {
			return null;
		}

		if (self.size() < 1) {
			return null;
		}

		if (self.size() == 1) {
			return Select(self.get(0));
		}

		Connection _conn = null;
		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self.get(0)));
			_conn = _query.getConnectionRead();

			return _query.select(_conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoFactory.releaseConnection(_conn);
		}
		return null;
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static List<DaoValue> Select(Connection conn, List<DaoValue> self) {
		if (self == null) {
			return null;
		}

		if (self.size() < 1) {
			return null;
		}

		if (conn == null) {
			return Select(self);
		}

		if (self.size() == 1) {
			return Select(conn, self.get(0));
		}

		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self.get(0)));

			return _query.select(conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static List<DaoValue> Select(DaoValue self) {
		if (self == null) {
			return null;
		}

		Connection _conn = null;
		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			_conn = _query.getConnectionRead();

			return _query.select(_conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoFactory.releaseConnection(_conn);
		}
		return null;
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static List<DaoValue> Select(Connection conn, DaoValue self) {
		if (conn == null) {
			return Select(self);
		}
		if (self == null) {
			return null;
		}
		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));

			return _query.select(conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static int Transaction(List<DaoValue> self) {
		Connection _conn = null;
		if (self.get(0) instanceof DaoValueInfo) {
			_conn = DaoFactory.getConnectionMain();
			if (_conn == null) {
				return -1;
			}
		} else {
			return -1;
		}

		int nResult = Transaction(_conn, self);
		DaoFactory.releaseConnection(_conn);
		return nResult;
	}

	/**
	 * @param conn
	 * @param self
	 * @return
	 */
	private static int Transaction(Connection conn, List<DaoValue> self) {
		GameLog.getInstance().info(String.format("[DB] Transaction Query Start"));
		String strTransation = getTransactionQuery(self);

		int nResult = _Transaction(conn, strTransation);
		GameLog.getInstance().info(String.format("[DB] Transaction Query Result : %d", nResult));
		return nResult;
	}

	/**
	 * @param self
	 * @return
	 */
	protected static String getTransactionQuery(List<DaoValue> self) {
		String strTransation = "";
		String strQuery = "";
		for (DaoValue _value : self) {
			if (_value.isInsert()) {
				strQuery = "INSERT INTO " + _value.getTblName() + " VALUES(" + _value.GetInsertValuesClause() + ")";
			}
			else if (_value.isDelete()) {
				strQuery = "DELETE FROM " + _value.getTblName() + " WHERE " + _value.GetUpdateWhereClause() + _value.GetDeleteOption();
			}
			else {
				String strWhere = _value.GetUpdateWhereClause();
				String strSet = _value.GetUpdateSetClause();
				if (strSet.equals("")) {
					continue;
				}
				strQuery = "UPDATE " + _value.getTblName() + " SET " + strSet + _value.GetUpdateSetClauseOption() + " WHERE " + strWhere + _value.GetUpdateOption();
			}

			GameLog.getInstance().info(String.format("[DB] Added: %s", strQuery));
			strTransation += (strQuery + ";\\;");
		}
		return strTransation;
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static int Insert(List<DaoValue> self) {
		if (self == null) {
			return -1;
		}

		if (self.size() < 1) {
			return -1;
		}

		if (self.size() == 1) {
			return Insert(self.get(0));
		}

		return Transaction(self);
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static int Insert(DaoValue self) {
		if (self == null) {
			return -1;
		}

		Connection _conn = null;
		if (self instanceof DaoValueInfo) {
			_conn = DaoFactory.getConnectionMain();
			if (_conn == null) {
				return -1;
			}
		}

		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			return _query.insert(_conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoFactory.releaseConnection(_conn);
		}
		return -1;
	}

	/**
	 *
	 * Insert(List<DaoValue> self)と同じ
	 * @param self
	 * @return
	 */
	public static int Update(List<DaoValue> self) {
		if (self == null) {
			return -1;
		}

		if (self.size() < 1) {
			return -1;
		}

		if (self.size() == 1) {
			return Update(self.get(0));
		}

		return Transaction(self);
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static int Update(DaoValue self) {
		if (self == null) {
			return -1;
		}

		Connection _conn = null;
		if (self instanceof DaoValueInfo) {
			_conn = DaoFactory.getConnectionMain();
			if (_conn == null) {
				return -1;
			}
		}

		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			return _query.update(_conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoFactory.releaseConnection(_conn);
		}

		return -1;
	}

	/**
	 * @param conn
	 * @param self
	 * @return
	 */
	public static int Update(Connection conn, List<DaoValue> self) {
		if (self == null) {
			return -1;
		}

		if (self.size() < 1) {
			return -1;
		}

		if (self.size() == 1) {
			return Update(conn, self.get(0));
		}

		if (conn == null) {
			return Transaction(self);
		}
		return Transaction(conn, self);
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static int Update(Connection conn, DaoValue self) {
		if (conn == null) {
			return Update(self);
		}
		if (self == null) {
			return -1;
		}
		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			return _query.update(conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 *
	 * Insert(List<DaoValue> self)と同じ
	 * @param self
	 * @return
	 */
	public static int Delete(List<DaoValue> self) {
		if (self == null) {
			return -1;
		}

		if (self.size() < 1) {
			return -1;
		}

		if (self.size() == 1) {
			return Delete(self.get(0));
		}

		return Transaction(self);
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static int Delete(DaoValue self) {
		if (self == null) {
			return -1;
		}

		Connection _conn = null;
		if (self instanceof DaoValueInfo) {
			_conn = DaoFactory.getConnectionMain();
			if (_conn == null) {
				return -1;
			}
		}

		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			return _query.delete(_conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoFactory.releaseConnection(_conn);
		}

		return -1;
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static int Count(DaoValue self) {
		if (self == null) {
			return -1;
		}

		Connection _conn = null;
		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			_conn = _query.getConnectionRead();

			return _query.count(_conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoFactory.releaseConnection(_conn);
		}

		return 0;
	}

	/**
	 * @param _conn Connection
	 * @param self
	 * @return
	 */
	public static int Count(Connection _conn, DaoValue self) {
		if (_conn == null) {
			return Count(self);
		}

		if (self == null) {
			return -1;
		}

		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			return _query.count(_conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static long Max(DaoValue self) {
		if (self == null) {
			return -1;
		}

		Connection _conn = null;
		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			_conn = _query.getConnectionRead();

			return _query.max(_conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoFactory.releaseConnection(_conn);
		}

		return 0;
	}

	/**
	 *
	 * @param self
	 * @return
	 */
	public static long Min(DaoValue self) {
		if (self == null) {
			return -1;
		}

		Connection _conn = null;
		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			_conn = _query.getConnectionRead();

			return _query.min(_conn, self);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoFactory.releaseConnection(_conn);
		}

		return 0;
	}


	private static String getDaoName(String TblName) {
		String daoName = TblName.replace("Tbl", "Dao");
		daoName = daoName.replace("tbl", "dao");
		return daoName;
	}

	private static String getSelectMultiWhere(List<DaoValue> self, int nStart, int nCount, boolean bAddTableName) {
		List<String> fieldID = new ArrayList<String>();
		List<Integer> fieldCount = new ArrayList<Integer>();
		List<String> fieldValue = new ArrayList<String>();

		for (int i = nStart; i < (nStart + nCount); i++) {
			List<String> strWhereList = self.get(i).GetSelectWhereClause();
			if (strWhereList == null) {
				continue;
			}

			for (String strWhereUnit : strWhereList) {
				String strToken[] = strWhereUnit.split(" = ");

				boolean isExist = false;
				for (int j = 0; j < fieldCount.size(); j++) {
					String strUnit = fieldID.get(j);
					if (!strUnit.equals(strToken[0])) {
						continue;
					}

					String strValue = fieldValue.get(j);
					Integer intValue = fieldCount.get(j);
					if ((intValue > 1) || (strValue.equals(strToken[1]) == false)) {
						strValue = strValue + "," + strToken[1];
						fieldValue.set(j, strValue);

						intValue += 1;
						fieldCount.set(j, intValue);
					}

					isExist = true;
					break;
				}

				if (isExist == false) {
					fieldID.add(strToken[0]);
					fieldCount.add(1);
					fieldValue.add(strToken[1]);
				}
			}
		}

		int nIndex = 0;
		String strTableName = "";
		if (bAddTableName == true) {
			strTableName = self.get(0).getTblName() + ".";
		}

		String strQueryWhere = "";
		for (Integer intCount : fieldCount) {
			if (strQueryWhere != "") {
				strQueryWhere += " AND ";
			}

			if (intCount == 1) {
				strQueryWhere += strTableName + fieldID.get(nIndex) + " = " + fieldValue.get(nIndex);
			} else {
				strQueryWhere += strTableName + fieldID.get(nIndex) + " IN ( " + fieldValue.get(nIndex) + " )";
			}

			nIndex++;
		}

		return strQueryWhere;
	}

	/**
	 *
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※
	 * @param conn
	 * @param self
	 * @return
	 */
	public static List<DaoValue> _Select(Connection conn, List<DaoValue> self) {
		if (conn == null) {
			return null;
		}

		try {
			// 何個のテーブルがあるかな
			int nTableCount = self.size();

			if (nTableCount == 1) {
				return _Select(conn, self.get(0));
			}

			int nSameTableCount = 1;
			for (int i = 1; i < nTableCount; i++) {
				if (self.get(0).getTblName().toString().equals(self.get(i).getTblName().toString())) {
					nSameTableCount++;
				}
			}

			String strMainWhereClause = "";
			strMainWhereClause = getSelectMultiWhere(self, 0, nSameTableCount, (nSameTableCount != nTableCount));

			String strQuery = "SELECT [REPLACE] FROM " + self.get(0).getTblName();
			String strSelectField = self.get(0).GetSelectFieldWithTableName();
			for (int i = nSameTableCount; i < nTableCount; i++) {
				strQuery += " LEFT JOIN " + self.get(i).getTblName() + " ON ";
				strQuery += self.get(0).getTblName() + "." + self.get(0).getForeignKey(self.get(i).getTblName()) + " = ";
				strQuery += self.get(i).getTblName() + "." + self.get(i).getPrimaryKey();

				strSelectField += "," + self.get(i).GetSelectFieldWithTableName();
			}
			strQuery = strQuery.replace("[REPLACE]", strSelectField);

			String strQueryWhere = strMainWhereClause;
			for (int i = nSameTableCount; i < nTableCount; i++) {
				List<String> strWhereList = self.get(i).GetSelectWhereClause();
				if (strWhereList == null) {
					continue;
				}

				for (String strWhereUnit : strWhereList) {
					if (strQueryWhere != "") {
						strQueryWhere += " AND ";
					}
					strQueryWhere += self.get(i).getTblName() + "." + strWhereUnit;
				}
			}

			for (int i = nSameTableCount; i < nTableCount; i++) {
				if (strQueryWhere != "") {
					strQueryWhere += " AND ";
				}
				strQueryWhere += self.get(i).getTblName() + "." + self.get(i).getPrimaryKey() + " IS NOT NULL";
			}

			if (strQueryWhere != "") {
				strQuery += " WHERE " + strQueryWhere;
			}

			strQuery += self.get(0).GetSelectOption();

			GameLog.getInstance().info(String.format("[DB] Join Query : %s", strQuery));

			List<DaoValue> retValueList = new ArrayList<DaoValue>();
			try {
				Statement st = conn.createStatement();
				try {
					ResultSet rs = st.executeQuery(strQuery);
					while (rs.next()) {
						int nColumnCount = 1;
						for (int i = (nSameTableCount - 1); i < nTableCount; i++) {
							int nTableFieldCount = self.get(i).getTableFieldCount();
							Class<? extends Object> retValue = Class.forName(self.get(i).getClass().getName());
							Method retMethod = retValue.getMethod("Set", new String().getClass(), new Object().getClass());
							Object retObject = retValue.newInstance();

							for (int j = 0; j < nTableFieldCount; j++) {
								if (rs.getMetaData().getColumnType(nColumnCount) == java.sql.Types.VARBINARY) {
									retMethod.invoke(retObject, rs.getMetaData().getColumnName(nColumnCount).toString(), (new String((byte[])rs.getObject(nColumnCount), "utf-8")));
								}
								else {
									retMethod.invoke(retObject, rs.getMetaData().getColumnName(nColumnCount).toString(), rs.getObject(nColumnCount));
								}
								nColumnCount++;
							}

							((DaoValue) retObject).Sync();
							retValueList.add((DaoValue) retObject);
						}
					}
					rs.close();
				} catch (InvocationTargetException e) {
					// invoke()の投げるInvocationTargetException例外は特殊らしい...
					e.getCause().printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return retValueList;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 *
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※
	 * @param conn
	 * @param self
	 * @return
	 */
	public static List<DaoValue> _Select(Connection conn, DaoValue self) {
		if (conn == null) {
			return null;
		}

		if (self instanceof DaoValueData) {
		} else {
			try {
				List<String> strWhereList = self.GetSelectWhereClause();
				String strWhere = "";

				if (strWhereList != null) {
					for (int i = 0; i < strWhereList.size(); i++) {
						if (strWhere != "") {
							strWhere += " AND ";
						}
						strWhere += strWhereList.get(i);
					}
				}

				if (strWhere != "") {
					strWhere = " WHERE " + strWhere;
				}

				String strQuery = "SELECT " + self.GetSelectField() + " FROM " + self.getTblName() + strWhere + self.GetSelectOption();

				GameLog.getInstance().info(String.format("[DB] Select Query : %s", strQuery));

				List<DaoValue> retValueList = new ArrayList<DaoValue>();
				try {
					Statement st = conn.createStatement();
					try {
						ResultSet rs = st.executeQuery(strQuery);
						while (rs.next()) {
							// 入力メソッド取得＆実行.
							Class<? extends Object> retValue = Class.forName(self.getClass().getName());
							Method retMethod = retValue.getMethod("Set", new String().getClass(), new Object().getClass());
							Object retObject = retValue.newInstance();

							for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
								if (rs.getMetaData().getColumnType(i) == java.sql.Types.VARBINARY) {
									retMethod.invoke(retObject, rs.getMetaData().getColumnName(i).toString(), (new String((byte[])rs.getObject(i), "utf-8")));
								}
								else {
									retMethod.invoke(retObject, rs.getMetaData().getColumnName(i).toString(), rs.getObject(i));
								}
							}

							((DaoValue) retObject).Sync();
							retValueList.add((DaoValue) retObject);
						}
						rs.close();
					} catch (InvocationTargetException e) {
						// invoke()の投げるInvocationTargetException例外は特殊らしい...
						e.getCause().printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				return retValueList;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 *
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※
	 * @param self
	 * @return
	 */
	public static List<DaoValue> _SelectFix(List<DaoValue> self) {

		int nTableCount = self.size();

		if (nTableCount == 1) {
			return Select(self.get(0));
		}

		FixData fixData = (FixData) FixDataBase.getInstance(self.get(0).getClass().getSimpleName().substring(3));
		Map<Integer, DaoValue> mapData = fixData.getData();

		Field[] variables = self.get(0).getClassField();
		try {
			List<DaoValue> retValueList = new ArrayList<DaoValue>();
			Iterator<Integer> it = mapData.keySet().iterator();
			while (it.hasNext()) {
				Integer key = it.next();
				DaoValue data = mapData.get(key);

				boolean found = true;
				for (Field variable : variables) {
					boolean match = true;
					boolean need = false;
					for (int j = 0; j < nTableCount; j++) {
						if ((j != 0) && !self.get(0).getTblName().toString().equals(self.get(j).getTblName().toString())) {
							continue;
						}

						Object objRead = variable.get(self.get(j).getFieldRead());
						Object objWrite = variable.get(self.get(j).getFieldWrite());

						if ((objRead.equals(objWrite)) == true) {
							continue;
						}

						need = true;
						if (!data.Get(variable.getName()).equals(objWrite)) {
							match = false;
							break;
						}
					}
					if (need && !match) {
						found = false;
						break;
					}
				}
				if (found) {
					retValueList.add(data);
				}
			}

			// 別テーブルへの操作.
			List<DaoValue> _retValueList = new ArrayList<DaoValue>();
			int retNum = 0;
			for (int i = 0; i < retValueList.size(); i++) {
				_retValueList.add(retNum++, retValueList.get(i));
				for (int j = 1; j < nTableCount; j++) {
					if (self.get(0).getTblName().toString().equals(self.get(j).getTblName().toString())) {
						continue;
					}
					DaoValue _value = self.get(j);
					_value.Set(self.get(j).getPrimaryKey(), retValueList.get(i).Get(self.get(0).getForeignKey(self.get(j).getTblName())));
					List<DaoValue> _ret = Select(_value);
					if (_ret != null) {
						if (_ret.size() > 0) {
							_retValueList.add(retNum++, _ret.get(0));
						}
						else {
							for (int k = 0; k < j; k++) {
								_retValueList.remove(--retNum);
							}
							break;
						}
					} else {
						for (int k = 0; k < j; k++) {
							_retValueList.remove(--retNum);
						}
						break;
					}
				}
			}
			return _retValueList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 *
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※
	 * @param self
	 * @return
	 */
	public static List<DaoValue> _SelectFix(DaoValue self) {

		FixData fixData = (FixData) FixDataBase.getInstance(self.getClass().getSimpleName().substring(3));
		Map<Integer, DaoValue> mapData = fixData.getData();

		Field[] variables = self.getClassField();
		try {
			List<DaoValue> retValueList = new ArrayList<DaoValue>();
			Iterator<Integer> it = mapData.keySet().iterator();
			while (it.hasNext()) {
				Integer key = it.next();
				DaoValue data = mapData.get(key);

				boolean found = true;
				for (int i = 0; i < variables.length; i++) {
					Object objRead = variables[i].get(self.getFieldRead());
					Object objWrite = variables[i].get(self.getFieldWrite());

					if ((objRead.equals(objWrite)) == true) {
						continue;
					}

					if (!data.Get(variables[i].getName()).equals(objWrite)) {
						found = false;
						break;
					}
				}
				if (found) {
					retValueList.add(data);
				}
			}
			return retValueList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 *
	 * マルチフルトランザクションクエリのSPを実行.
	 *
	 * @param conn
	 * @param strQuery
	 * @return
	 */
	public static int _Transaction(Connection conn, String strQuery) {
		int result = -1;
		try {
			CallableStatement st = conn.prepareCall("call funcTransaction(?, ?)");
			st.setString(1, strQuery);
			st.registerOutParameter(2, Types.TINYINT);
			try {
				st.execute();
				result = st.getByte(2);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 *
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※
	 * @param conn
	 * @param self
	 * @return
	 */
	public static int _Insert(Connection conn, DaoValue self) {
		int result = -1;
		try {
			String strValues = self.GetInsertValuesClause();

			try {
				Statement st = conn.createStatement();
				try {
					String strQuery = "INSERT INTO " + self.getTblName() + " VALUES(" + strValues + ")";
					GameLog.getInstance().info(String.format("[DB] Insert Query : %s", strQuery));

					if (st.executeUpdate(strQuery, Statement.RETURN_GENERATED_KEYS) < 1) {
						return -1;
					}
					ResultSet rs = st.getGeneratedKeys();
					if (rs.next()) {
						result = rs.getInt(1);
					} else {
						result = 0;
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 *
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※
	 * @param conn
	 * @param self
	 * @return
	 */
	public static int _Update(Connection conn, DaoValue self) {
		try {
			String strWhere = self.GetUpdateWhereClause();
			String strSet = self.GetUpdateSetClause();

			//System.out.println("SET : " + strSet);
			//System.out.println("WHERE : " + strWhere);

			if (strSet.equals("")) {
				return 0;
			}

			try {
				Statement st = conn.createStatement();
				try {
					String strQuery = "UPDATE " + self.getTblName() + " SET " + strSet + " WHERE " + strWhere + self.GetUpdateOption();
					GameLog.getInstance().info(String.format("[DB] Update Query : %s", strQuery));

					if (st.executeUpdate(strQuery) < 1) {
						return -1;
					}

					self.Sync();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 *
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※
	 * @param conn
	 * @param self
	 * @return
	 */
	public static int _Delete(Connection conn, DaoValue self) {
		try {
			String strWhere = self.GetUpdateWhereClause();

			try {
				Statement st = conn.createStatement();
				try {
					String strQuery = "DELETE FROM " + self.getTblName() + " WHERE " + strWhere;
					GameLog.getInstance().info(String.format("[DB] Delete Query : %s", strQuery));

					if (st.executeUpdate(strQuery) < 1) {
						return -1;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 *
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※
	 * @param self
	 * @return
	 */
	public static int _CountFix(DaoValue self) {
		if (self instanceof DaoValueData) {
			FixData fixData = (FixData) FixDataBase.getInstance(self.getClass().getSimpleName().substring(3));
			return fixData.getData().size();
		}
		System.out.println("[ERROR] CommonDaoFacotry#Count() invalid instance.");
		return 0;
	}

	/**
	 *
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※
	 * @param conn
	 * @param self
	 * @return
	 */
	public static int _Count(Connection conn, DaoValue self) {
		if (conn == null) {
			return -1;
		}

		if (self instanceof DaoValueData) {
			// TODO ログ追加.
			System.out.println("[ERROR] CommonDaoFacotry#Count() invalid instance.");
			return -1;
		}
		try {
			List<String> strWhereList = self.GetSelectWhereClause();
			String strWhere = "";
			if (strWhereList != null) {
				for (int i = 0; i < strWhereList.size(); i++) {
					if (strWhere != "") {
						strWhere += " AND ";
					}
					strWhere += strWhereList.get(i);
				}
			}

			if (strWhere != "") {
				strWhere = " WHERE " + strWhere;
			}

			int result = 0;
			try {
				Statement st = conn.createStatement();
				try {
					String strQuery = "SELECT COUNT(" + self.getPrimaryKey() + ") as count FROM " + self.getTblName() + strWhere;
					strQuery += self.GetSelectOption();

					GameLog.getInstance().info(String.format("[DB] Select Query : %s", strQuery));

					ResultSet rs = st.executeQuery(strQuery);
					while (rs.next()) {
						result = rs.getInt("count");
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static int _Count(Connection conn, String strQuery) {
		if (conn == null) {
			return -1;
		}

		int result = 0;
		try {
			Statement st = conn.createStatement();
			try {

				GameLog.getInstance().info(String.format("[DB] Select Query : %s", strQuery));

				ResultSet rs = st.executeQuery(strQuery);
				while (rs.next()) {
					result = rs.getInt("count");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * @param conn
	 * @param self
	 * @return
	 */
	public static long _Max(Connection conn, DaoValue self) {
		if (conn == null) {
			return -1;
		}

		if (self instanceof DaoValueData) {
			System.out.println("[ERROR] CommonDaoFacotry#_Max() invalid instance.");
			return -1;
		}
		try {
			List<String> strWhereList = self.GetSelectWhereClause();
			String strWhere = "";
			if (strWhereList != null) {
				for (int i = 0; i < strWhereList.size(); i++) {
					if (strWhere != "") {
						strWhere += " AND ";
					}
					strWhere += strWhereList.get(i);
				}
			}

			if (strWhere != "") {
				strWhere = " WHERE " + strWhere;
			}

			int result = 0;
			try {
				Statement st = conn.createStatement();
				try {
					String strQuery = "SELECT MAX(" + self.getPrimaryKey() + ") as max FROM " + self.getTblName() + strWhere;
					strQuery += self.GetSelectOption();

					GameLog.getInstance().info(String.format("[DB] Select Query : %s", strQuery));

					ResultSet rs = st.executeQuery(strQuery);
					while (rs.next()) {
						result = rs.getInt("max");
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * @param conn
	 * @param self
	 * @return
	 */
	public static long _Min(Connection conn, DaoValue self) {
		if (conn == null) {
			return -1;
		}

		if (self instanceof DaoValueData) {
			System.out.println("[ERROR] CommonDaoFacotry#_Min() invalid instance.");
			return -1;
		}
		try {
			List<String> strWhereList = self.GetSelectWhereClause();
			String strWhere = "";
			if (strWhereList != null) {
				for (int i = 0; i < strWhereList.size(); i++) {
					if (strWhere != "") {
						strWhere += " AND ";
					}
					strWhere += strWhereList.get(i);
				}
			}

			if (strWhere != "") {
				strWhere = " WHERE " + strWhere;
			}

			int result = 0;
			try {
				Statement st = conn.createStatement();
				try {
					String strQuery = "SELECT MIN(" + self.getPrimaryKey() + ") AS min FROM " + self.getTblName() + strWhere;
					strQuery += self.GetSelectOption();

					GameLog.getInstance().info(String.format("[DB] Select Query : %s", strQuery));

					ResultSet rs = st.executeQuery(strQuery);
					while (rs.next()) {
						result = rs.getInt("min");
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static long getNumRows(DaoValue self) {
		if (self == null || (self instanceof DaoValueData)) {
			return -1;
		}

		Connection _conn = null;
		try {
			DaoFactory _query = new DaoFactory(getDaoClass(self));
			_conn = _query.getConnectionRead();

			if (_conn == null) {
				return -1;
			}

			long result = -1L;
			try {
				List<String> strWhereList = self.GetSelectWhereClause();
				String strWhere = "";

				if (strWhereList != null) {
					for (int i = 0; i < strWhereList.size(); i++) {
						if (strWhere != "") {
							strWhere += " AND ";
						}
						strWhere += strWhereList.get(i);
					}
				}

				if (strWhere != "") {
					strWhere = " WHERE " + strWhere;
				}

				String strQuery = "SELECT COUNT(*) FROM (SELECT " + self.GetSelectField() + " FROM " + self.getTblName() + strWhere + self.GetSelectOption() + ") AS temp";

				GameLog.getInstance().info(String.format("[DB] Select Query : %s", strQuery));

				try {
					Statement st = _conn.createStatement();
					try {
						ResultSet rs = st.executeQuery(strQuery);
						while (rs.next()) {
							result = rs.getLong(1);
						}
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DaoFactory.releaseConnection(_conn);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @param _conn Connection
	 * @param sql 実行したいquery.
	 * @return
	 */
	public static List<List<Object>> executeQuery(Connection _conn, String sql) {
		if (StringUtils.isBlank(sql)) {
			return null;
		}

		if (_conn == null) {
			return null;
		}

		try {
			List<List<Object>> result = new ArrayList<List<Object>>();
			try {
				GameLog.getInstance().info(String.format("[DB] Select Query : %s", sql));
				try {
					Statement st = _conn.createStatement();
					try {
						ResultSet rs = st.executeQuery(sql);
						while (rs.next()) {
							List<Object> row = new ArrayList<Object>();
							for (int index = 1; index <= rs.getMetaData().getColumnCount(); index++) {
								row.add(rs.getObject(index));
							}
							result.add(row);
						}
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param _conn
	 * @param sql
	 * @return
	 */
	public static int executeUpdate(Connection _conn, String sql) {
		if (StringUtils.isBlank(sql)) {
			return -1;
		}

		if (_conn == null) {
			return -1;
		}

		int result = -1;
		try {
			GameLog.getInstance().info(String.format("[DB] Update Query : %s", sql));
			Statement st = null;
			try {
				st = _conn.createStatement();
				result = st.executeUpdate(sql);
				if (result < 1) {
					return -1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				result = -1;
			} finally {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※.
	 * あまり高速でないので定数を用意できるならそちらを使ったほうがいい.
	 *
	 * @param self
	 * @return
	 */
	public static long _MaxFix(DaoValue self) {
		if (self instanceof DaoValueData) {
			FixData fixData = (FixData) FixDataBase.getInstance(self.getClass().getSimpleName().substring(3));
			Map<Integer, DaoValue> dataMap = fixData.getData();
			long max = 0;
			for (Iterator<Integer> it = dataMap.keySet().iterator(); it.hasNext();) {
				Integer key = (Integer) it.next();
				if (key > max) {
					max = key;
				}
			}
			return max;
		}
		System.out.println("[ERROR] CommonDaoFacotry#_MaxFix() invalid instance.");
		return 0;
	}

	/**
	 * ※class DaoXxxInfo or DaoXxxData 以外から呼び禁止※.
	 * あまり高速でないので定数を用意できるならそちらを使ったほうがいい.
	 *
	 * @param self
	 * @return
	 */
	public static long _MinFix(DaoValue self) {
		if (self instanceof DaoValueData) {
			FixData fixData = (FixData) FixDataBase.getInstance(self.getClass().getSimpleName().substring(3));
			Map<Integer, DaoValue> dataMap = fixData.getData();
			long min = Long.MAX_VALUE;
			for (Iterator<Integer> it = dataMap.keySet().iterator(); it.hasNext();) {
				Integer key = (Integer) it.next();
				if (key < min) {
					min = key;
				}
			}
			return min;
		}
		System.out.println("[ERROR] CommonDaoFacotry#_MinFix() invalid instance.");
		return 0;
	}

}
