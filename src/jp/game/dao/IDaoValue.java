/**
 *
 */
package jp.game.dao;

import java.lang.reflect.Field;

/**
 *
 * @author kanghyon-kim
 * @comment unsinged数値がなければSet()とGet()でいけそうだが、
 * やむ得なくテーブルのそれぞれフィールドのSet()とGet()を作る
 */
public interface IDaoValue {
	/**
	 *
	 * テーブルフィールド
	 */
	public interface Fields {
	}

	/**
	 *
	 * テーブル同期化
	 */
	public void Sync();

	/**
	 *
	 * テーブルの名前を取得する
	 * @return テーブル名前
	 */
	public String getTblName();

	/**
	 *
	 * テーブルのプライマリキーを取得
	 * @return プライマリキー
	 */
	public String getPrimaryKey();

	/**
	 *
	 * テーブルのサブキーを取得
	 * @return サブキー
	 */
	public String getSubKey();

	/**
	 *
	 * テーブルのフォリンキーを取得
	 * @param strKey テーブル名
	 * @return フォリンキー
	 */
	public String getForeignKey(String strKey);

	/**
	 *
	 * テーブルの変数データ取得
	 * @return テーブルの変数データ（Field）
	 */
	public Field[] getClassField();

	/**
	 *
	 * テーブルの読み込み用のデータ取得
	 * @return 読み込み用のデータ
	 */
	public Object getFieldRead();

	/**
	 *
	 * テーブルの書き込み用のデータ取得
	 * @return 書き込み用のデータ
	 */
	public Object getFieldWrite();

	/**
	 *
	 * テーブルの直接書き込み用
	 * @return 書き込み用のデータ
	 */
	public Fields getInstance();
}
