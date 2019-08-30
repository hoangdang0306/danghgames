/**
 *
 */
package jp.game.dao.fix;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.game.core.localize.GameLocale;
import jp.game.dao.DaoValue;

public abstract class FixData extends FixDataBase {

	private static Log log = LogFactory.getLog(FixData.class);

	/**
	 * 固定データ.
	 */
	private Map<Integer, DaoValue> fixdata = new LinkedHashMap<Integer, DaoValue>();
	private Object __lock = new Object();

	public Map<Integer, DaoValue> getData() {
		File file = loadFile();
		if (file != null) {
			synchronized (__lock) {
				if (isModified(file)) {
					update(file);
				}
			}
		}
		return fixdata;
	}

	/**
	 * @return 固定データの列.
	 */
	protected abstract Field[] getField();

	/**
	 * ファイルの更新時間.
	 */
	private Date lastModified = new Date(0);

	/**
	 * 固定データのファイルのパス.
	 */
	private String filePath = null;

	public void setFilePath(String path) {
		filePath = path;
	}

	/**
	 * @return
	 */
	private File loadFile() {
		File file = new File(filePath);
		if (!file.exists()) {
			log.error(filePath + " not found.");
			return null;
		}
		return file;
	}

	/**
	 * ファイルが更新されているかを確認する.
	 *
	 * @param file
	 * @return 更新されている場合、trueを返す.
	 */
	private boolean isModified(File file) {
		return (lastModified.compareTo(new Date(file.lastModified())) != 0);
	}

	/**
	 * メモリ上のデータを更新する.
	 *
	 * @param file
	 */
	private void update(File file) {
		if (!fixdata.isEmpty()) {
			fixdata.clear();
		}

		// ファイルのロード.
		BufferedReader breader = null;
		try {
			breader = getBufferReader(file);
			try {
				Class<? extends Object> retValue = Class.forName("jp.game.tbl.data.Tbl" + this.getClass().getSimpleName().substring(3));
				Field[] fields = getField();
				String line = null;
				while ((line = breader.readLine()) != null) {
					if (line.matches("^$")) { // 空行.
						continue;
					}
					if (line.matches("^#.*")) { // 行頭が#の行はコメント行.
						continue;
					}

					String[] tokens = line.split(",\\s*", fields.length);
					try {
						DaoValue retObject = (DaoValue) retValue.newInstance();
						int i = 0;
						for (String token : tokens) {
							Field field = fields[i++];
							try {
								if (field.getType() == String.class) {
									retObject.Set(field.getName(), token);
								} else if (field.getType() == Byte.TYPE) {
									retObject.Set(field.getName(), Byte.parseByte(token));
								} else if (field.getType() == Short.TYPE) {
									retObject.Set(field.getName(), Short.parseShort(token));
								} else if (field.getType() == Integer.TYPE) {
									retObject.Set(field.getName(), Integer.parseInt(token));
								} else if (field.getType() == Long.TYPE) {
									retObject.Set(field.getName(), Long.parseLong(token));
								} else {
									log.error("Invalid type(" + field.getType() + ")");
									retObject.Set(field.getName(), token);
								}
							} catch (NumberFormatException e) {
								log.error(line);
								log.error("[" + field.getName() + "(" + field.getType() + ")] <= " + token);
								e.printStackTrace();
							}
						}

						fixdata.put(Integer.valueOf(retObject.Get(retObject.getPrimaryKey()).toString()), retObject); // ID必須！.
					} catch (InstantiationException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				breader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 最終更新時刻の更新.
		lastModified.setTime(file.lastModified());

		log.info(filePath + " loaded(" + lastModified.toString() + ").");
	}

	private BufferedReader getBufferReader(File file) throws IOException {
		BufferedReader breader = null;
		if (GameLocale.isJa()) {
			InputStreamReader objIsr = new InputStreamReader(new FileInputStream(file),"UTF-8");
			breader = new BufferedReader(objIsr);
			return breader;
		}
		char[] key = "cannonblock".toCharArray();
		FileInputStream fis = new FileInputStream(file);
        int len = fis.available();
        int keyLen = key.length;
        byte[] bs = new byte[len];
        while (fis.read(bs) != -1) {
        	for (int i = 0; i < len / keyLen; i++) {
            	for (int j = 0; j < keyLen; j++) {
            		bs[i * keyLen + j] ^= key[j];
            	}
            }
        }
        fis.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(bs);
        InputStreamReader isr = new InputStreamReader(bais, "utf8");
        breader = new BufferedReader(isr);
		return breader;
	}
}
