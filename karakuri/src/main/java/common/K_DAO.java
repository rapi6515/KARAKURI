package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class K_DAO {

	//プロパティファイル取得
	ResourceBundle bundle =
	ResourceBundle.getBundle("Demo");
	//定数DB-URL
	private final String DB_URL = bundle.getString("dbURL");
	private final String DB_USER = bundle.getString("dbUSER");
	private final String DB_PASS = bundle.getString("dbPASS");
	
	
	private Connection connection = null; //コネクション
	private Statement statement = null; //単一ステートメント
	private PreparedStatement prestatement = null; //一括ステートメント
	private ResultSet resultSet = null; //結果セット

	//DB接続メソッド
	public void connect() {
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//DB読込メソッド
	public List<Map<String, Object>> executeQuery(String query) {

		// 結果セットをマップに変換します。f
		List<Map<String, Object>> mapRec = new ArrayList<>();

		try {
			// データベースに接続します。
			connect();

			// クエリを実行します。
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Map<String, Object> row = new HashMap<>();

				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					String columnName = resultSet.getMetaData().getColumnName(i);
					Object value = resultSet.getObject(i);
					row.put(columnName, value);
				}
				mapRec.add(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//Resultを解放	
				if (resultSet != null)
					resultSet.close();

				//Statementを解放
				if (statement != null)
					statement.close();

				//⑤DBを切断			//Resultを解放	
				if (resultSet != null) {
					resultSet.close();
				}
				//Statementを解放
				if (statement != null) {
					statement.close();
				}
				//⑤DBを切断
				disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (mapRec.isEmpty()) {
				System.out.println("executeQuery: データが0件です！");
			}
		}
		return mapRec;
	}

	//単一行更新メソッド
	public int executeUpdate(String query) {
		int status = -1;
		try {
			// データベースに接続します。
			connect();

			// クエリを実行します。
			statement = connection.createStatement();
			status = statement.executeUpdate(query);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//Statementを解放
				if (statement != null) {
					statement.close();
				}
				//⑤DBを切断
				disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	//DB接続解放メソッド
	private void disconnect() {
		try {
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//一括更新メソッド
	public int executeBundle(String query, List<Map<Integer, Object>> mapList) {
		int[] status = null;
		int retVal = -1;

		try {
			// データベースに接続します。
			connect();

			// 自動コミットを無効化
			connection.setAutoCommit(false);

			// クエリを準備します。
			prestatement = connection.prepareStatement(query);

			// バッチに追加
			for (Map<Integer, Object> paramMap : mapList) {
				setParameters(prestatement, paramMap);
				prestatement.addBatch();
			}

			// バッチを実行します。
			status = prestatement.executeBatch();
			retVal = status.length;

			// コミットを実行します。
			connection.commit();

		} catch (SQLException e) {
			try {
				// ロールバックします。
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				// Statement を解放します。
				if (prestatement != null) {
					prestatement.close();
				}
				// データベース接続を切断します。
				disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}

	//パラメータ設定メソッド
	public void setParameters(PreparedStatement ps, Map<Integer, Object> paramMap) throws SQLException {
		for (Map.Entry<Integer, Object> entry : paramMap.entrySet()) {
			int index = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof String) {
				ps.setString(index, (String) value);
			} else if (value instanceof Integer) {
				ps.setInt(index, (Integer) value);
			} else if (value instanceof Long) {
				ps.setLong(index, (Long) value);
			} else if (value instanceof Float) {
				ps.setFloat(index, (Float) value);
			} else if (value instanceof Double) {
				ps.setDouble(index, (Double) value);
			} else if (value instanceof Boolean) {
				ps.setBoolean(index, (Boolean) value);
			} else if (value instanceof Date) {
				ps.setDate(index, new java.sql.Date(((Date) value).getTime()));
			} else if (value instanceof java.sql.Date) {
				ps.setDate(index, (java.sql.Date) value);
			} else if (value instanceof java.sql.Timestamp) {
				ps.setTimestamp(index, (java.sql.Timestamp) value);
			} else {
				ps.setObject(index, value);
			}
		}
	}
}