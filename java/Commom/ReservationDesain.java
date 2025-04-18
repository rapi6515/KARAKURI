package Commom;

import java.sql.Time;
import java.text.ParseException; // sdf.parse()用
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.K_DTO;
import entity.Reservation_DTO;

public class ReservationDesain {
	private K_DAO dao = new K_DAO();

	/** 予約を作成する */
	public int createReservation(K_DTO reservation) {
		String query = "INSERT INTO academy.reserve (reserve_day, reserve_time, plan_id, reserve_comment) VALUES (?, ?, ?, ?)";
		Map<Integer, Object> params = new HashMap<>();

		// 日付変換
		try {
			if (reservation.getReserve_day() != null && !reservation.getReserve_day().isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date parsedDate = sdf.parse(reservation.getReserve_day());
				params.put(1, new java.sql.Date(parsedDate.getTime()));
			} else {
				params.put(1, null); 
			}
		} catch (ParseException e) { // より具体的な例外を捕捉
			e.printStackTrace(); 
			params.put(1, null); 
		}

		// 時間変換
		try {
			if (reservation.getReserve_time() != null && !reservation.getReserve_time().isEmpty()) {
				String timeStr = reservation.getReserve_time();
				if (timeStr.length() == 5) { // "HH:mm" 形式なら ":00" を補完
					timeStr += ":00";
				}
				params.put(2, Time.valueOf(timeStr)); 
			} else {
				params.put(2, null); 
			}
        } catch (IllegalArgumentException e) { // 具体的な例外を捕捉
            e.printStackTrace();
            params.put(2, null); 
		}

		// プランID変換
		try {
			if (reservation.getPlan_id() != null && !reservation.getPlan_id().isEmpty()) {
				params.put(3, Integer.parseInt(reservation.getPlan_id())); 
			} else {
				params.put(3, null); 
			}
		} catch (NumberFormatException e) {
			e.printStackTrace(); 
			params.put(3, null); 
		}

		// コメント
		params.put(4, reservation.getReserve_comment());

		// DAO呼び出し
		return dao.executeBundle(query, List.of(params)); 
	}

	/** 予約を更新する */
	public int updateReservation(K_DTO reservation, int id) {
        // SQL文 (カラム名はDB定義に合わせる)
		String query = "UPDATE academy.reserve SET reserve_day=?, reserve_time=?, plan_id=?, reserve_comment=? WHERE reserve_num=?";
		Map<Integer, Object> params = new HashMap<>();
		
        // パラメータ設定 (createReservationと同様の型変換を行う)
        try { // 日付 (パラメータ1番目)
            if (reservation.getReserve_day() != null && !reservation.getReserve_day().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = sdf.parse(reservation.getReserve_day());
                params.put(1, new java.sql.Date(parsedDate.getTime()));
            } else {
                params.put(1, null); 
            }
        } catch (ParseException e) { 
            e.printStackTrace(); 
            params.put(1, null); 
        }

        try { // 時間 (パラメータ2番目)
            if (reservation.getReserve_time() != null && !reservation.getReserve_time().isEmpty()) {
                String timeStr = reservation.getReserve_time();
                if (timeStr.length() == 5) { timeStr += ":00"; }
                params.put(2, Time.valueOf(timeStr)); 
            } else {
                params.put(2, null); 
            }
        } catch (IllegalArgumentException e) { 
            e.printStackTrace();
            params.put(2, null); 
		}

		try { // プランID (パラメータ3番目)
			if (reservation.getPlan_id() != null && !reservation.getPlan_id().isEmpty()) {
				params.put(3, Integer.parseInt(reservation.getPlan_id())); 
			} else {
				params.put(3, null); 
			}
		} catch (NumberFormatException e) {
			e.printStackTrace(); 
			params.put(3, null); 
		}

		// コメント (パラメータ4番目)
		params.put(4, reservation.getReserve_comment());
        
        // 予約ID (reserve_num) (パラメータ5番目)
        params.put(5, id); 

        // DAOのメソッドを呼び出してUPDATE実行
		return dao.executeBundle(query, List.of(params)); 
	}

	/** 予約を削除する */
	public int deleteReservation(int id) {
        // SQL文を修正 (テーブル名、カラム名)
		String query = "DELETE FROM academy.reserve WHERE reserve_num=?";
		Map<Integer, Object> params = new HashMap<>();
		params.put(1, id); // reserve_num
		return dao.executeBundle(query, List.of(params));
	}

	/** 予約リストを取得する */
	public List<K_DTO> getReservations() {
		// 予約日、時間の昇順で取得するようにORDER BYを追加 (推奨)
		String query = "SELECT * FROM academy.reserve ORDER BY reserve_day, reserve_time"; 
		List<Map<String, Object>> results = dao.executeQuery(query);
		List<K_DTO> reservations = new ArrayList<>();
		for (Map<String, Object> record : results) {
			try {
                // デフォルトコンストラクタ + setter の方が null 安全な場合がある
				K_DTO dto = new K_DTO(); 
                
                // 各カラムの値を取得してDTOにセット (nullチェック追加)
                if (record.get("reserve_num") != null) {
				    // DBの数値型(serial, int, numericなど)を int に変換
                    dto.setId(((Number) record.get("reserve_num")).intValue());
                }
                if (record.get("reserve_day") != null) {
                    // DBのDate型 -> String (YYYY-MM-DD形式になるはず)
				    dto.setReserve_day(String.valueOf(record.get("reserve_day"))); 
                }
                if (record.get("reserve_time") != null) {
                    // DBのTime型 -> String (HH:mm:ss形式になるはず)
				    dto.setReserve_time(String.valueOf(record.get("reserve_time")));
                }
                if (record.get("plan_id") != null) {
                    // DBの数値型 -> String
				    dto.setPlan_id(String.valueOf(record.get("plan_id")));
                }
                // コメントは元々Stringなのでキャストのみ (nullチェックは任意)
				dto.setReserve_comment((String) record.get("reserve_comment"));
                
				reservations.add(dto);
			} catch (Exception e) {
                // エラーが発生したレコードがあっても処理を継続するが、ログは残す
				System.err.println("予約データのDTO変換中にエラー発生: " + record);
				e.printStackTrace(); 
			}
		}
		return reservations;
	}

	/** 利用可能なプランリストを取得する */
	public List<Reservation_DTO> getAvailablePlans() {
		List<Map<String, Object>> results = dao.getAllPlans(); 
		List<Reservation_DTO> plans = new ArrayList<>();
		for (Map<String, Object> record : results) {
			try {
				Reservation_DTO dto = new Reservation_DTO();
                if (record.get("plan_id") != null) {
                    // DBの数値型 -> int
				    dto.setPlanId(((Number) record.get("plan_id")).intValue()); 
                }
                if (record.get("plan_name") != null) {
                    // DBの文字列型 -> String
				    dto.setPlanName((String) record.get("plan_name"));
                }
				plans.add(dto);
			} catch (Exception e) {
				System.err.println("プラン情報のDTO変換中にエラー発生: " + record);
				e.printStackTrace();
			}
		}
		return plans;
	}

	/** 予約済み日付の一覧を取得する (特定の用途向け) */
	public List<String> getReservedDays() {
		String query = "SELECT DISTINCT reserve_day FROM academy.reserve ORDER BY reserve_day";
		List<Map<String, Object>> results = dao.executeQuery(query);
		List<String> reservedDates = new ArrayList<>();
		for (Map<String, Object> record : results) {
			try {
                if (record.get("reserve_day") != null) {
                    // DBのDate型 -> String (YYYY-MM-DD)
                    reservedDates.add(String.valueOf(record.get("reserve_day"))); 
                }
			} catch (Exception e) {
				System.err.println("予約済み日付の処理中にエラー発生: " + record);
				e.printStackTrace();
			}
		}
		return reservedDates;
	}
	
    public List<K_DTO> searchReservationsByPlan(String planId) {
        // 検索結果を入れるための空のリストを準備
        List<K_DTO> reservations = new ArrayList<>(); 
        
        // 1. plan_id で絞り込むためのSQL文を準備
        //    WHERE句の plan_id は academy.reserve テーブルのカラム名です
        //    プレースホルダ (?) を使います
        String query = "SELECT * FROM academy.reserve WHERE plan_id = ? ORDER BY reserve_day, reserve_time";
        
        // 2. SQL文の ? にセットするパラメータを準備
        Map<Integer, Object> params = new HashMap<>();
        try {
            // Servletから渡された planId (文字列) を Integer に変換してセット
            // データベースの plan_id カラムが数値型 (numeric, integer など) である想定
            params.put(1, Integer.parseInt(planId)); // 1番目の ? に planId をセット
        } catch (NumberFormatException e) {
             // planId が数値に変換できない場合 (例: "" や不正な文字列)
             System.err.println("検索のためのプランID数値変換に失敗: " + planId);
             e.printStackTrace();
             return reservations; // エラー時は空のリストを返す
        }
        
        // 3. ★★★ DAOの新しいメソッド(executeQueryWithParams)を呼び出し ★★★
        List<Map<String, Object>> results = dao.executeQueryWithParams(query, params); 
        System.out.println("[DEBUG Desain#searchByPlan] DAO検索結果件数: " + results.size()); // デバッグログ
        
        // 4. --- DAOから返ってきたMapリストを K_DTO リストに変換 ---
        //    (この部分は getReservations メソッドとほぼ同じです)
        for (Map<String, Object> record : results) {
            try {
                K_DTO dto = new K_DTO();
                // 各カラムの値を取得してDTOにセット (nullチェック推奨)
                if (record.get("reserve_num") != null) {
                    dto.setId(((Number) record.get("reserve_num")).intValue());
                }
                if (record.get("reserve_day") != null) {
                    dto.setReserve_day(String.valueOf(record.get("reserve_day"))); 
                }
                if (record.get("reserve_time") != null) {
                    dto.setReserve_time(String.valueOf(record.get("reserve_time")));
                }
                if (record.get("plan_id") != null) {
                    // DBから取得した plan_id (数値のはず) を String に変換してセット
                    dto.setPlan_id(String.valueOf(record.get("plan_id"))); 
                }
                dto.setReserve_comment((String) record.get("reserve_comment"));
                
                reservations.add(dto); // リストに追加
            } catch (Exception e) {
                System.err.println("予約データのDTO変換中にエラー発生(検索時): " + record);
                e.printStackTrace(); 
            }
        }
        // 5. 変換後の K_DTO リストを返す
        return reservations;
    }

} // ReservationDesainクラスの終わり