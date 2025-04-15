
package entity;

import java.util.List;
import java.util.Map;

import Commom.K_Bean;
import Commom.K_DAO;

public class K_DTO {
	K_DAO kdao = new K_DAO();
	
	
	//予約可能（空き）プランを検索するメソッド
	public List<Map<String, Object>> searchAvailablePlans(String date, String reserveStartTime, String reserveEndTime, int people) {
	    String sql = "SELECT ";
		sql += "p.plan_id, ";
	    sql += "p.plan_name, ";
	    sql += "ps.reserve_day, ";
	    sql += "ps.reserve_start_time, ";
	    sql += "ps.reserve_end_time, ";
	    sql += "ps.available_count ";
	    sql += "FROM ";
	    sql += "plan_stock ps ";
	    sql += "JOIN plan p ON ps.plan_id = p.plan_id ";
	    sql += "WHERE ";
	    sql += "ps.reserve_day = '" + date + "' "; // 指定された日付
	    sql += "AND ps.reserve_start_time <= TIME '" + reserveStartTime + "' "; // 予約希望時間以前に開始
        sql += "AND ps.reserve_end_time > TIME '" + reserveStartTime + "' ";  // 予約希望時間が終了時間前
	    
	    sql += "AND ps.available_count >= " + people; // 必要人数以上の在庫がある
	    
	    // 実行するSQLをログに出力
	    System.out.println("実行するSQL: " + sql);
	    
	    return kdao.executeQuery(sql);
	}
	
	
	
	
	//planテーブルから詳細情報を取得するSQL
	public Map<String, Object> getPlanDetailById(String planId) {
		String sql = "SELECT p.plan_name, ";
		       sql += "p.subplan, ";
			   sql += "p.cost, ";
			   sql += "p.plan_required ";
			   sql += "FROM plan p ";
			   sql += "WHERE plan_id = '" + planId + "'";
			   
			   System.out.println(sql);
		 List<Map<String,Object>> result = kdao.executeQuery(sql);
		//結果が一件であれば最初のレコードを取得
		 if (!result.isEmpty()) { //情報がからでなければ
			 return result.get(0);//1件目のレコードを返す
		 } else {
			 return null; //データが見つからない場合はnullを返す
		 }
				
	}
	
	
	
	public boolean saveReservation(K_Bean bean) {
	    // INSERT＋RETURNINGを1つのSQLで処理（executeQueryで実行）
	    String sql = "INSERT INTO reserve (plan_id, reserve_name, reserve_kana, reserve_tel, reserve_email, reserve_nationality, reserve_media, reserve_comment) " +
	                 "VALUES (" + bean.getPlan_id() + ", '" + bean.getName() + "', '" + bean.getKana() + "', '" + bean.getTel() + "', '" +
	                 bean.getEmail() + "', '" + bean.getNationality() + "', '" + bean.getReason() + "', '" + bean.getReserve_comment() +
	                 "') RETURNING reserve_num";

	    System.out.println("★ 実行するINSERT SQL: " + sql);

	   
	    List<Map<String, Object>> result = kdao.executeQuery(sql);

	    if (result != null && !result.isEmpty()) {
	        Object reserveNum = result.get(0).get("reserve_num");
	        if (reserveNum != null) {
	            bean.setReserve_num(reserveNum.toString());
	            System.out.println("✅ 登録成功！予約番号: " + reserveNum);
	            return true;
	        }
	    }

	    System.err.println("❌ 登録失敗または予約番号取得失敗");
	    return false;
	}
	
	public K_Bean getReservationByNumber(String reserve_num) {
		// SQL構文：指定された予約番号で1件検索
		String sql = "SELECT * ";
		       sql += "FROM reserve ";
		       sql += "WHERE reserve_num = " + reserve_num;
		       // SQL実行
		       List<Map<String, Object>> result = kdao.executeQuery(sql);
		       // 結果がある場合
		       if(!result.isEmpty()) {
		       
		       // DBから取ってきた値をK_Beanにセット
		       Map<String, Object> row = result.get(0);
		       K_Bean bean = new K_Bean();
		       bean.setReserve_num(reserve_num);
		       bean.setName((String) row.get("reserve_name"));
		       bean.setKana((String) row.get("reserve_kana"));
		       bean.setTel((String) row.get("reserve_tel"));
		       bean.setEmail((String) row.get("reserve_email"));
		       bean.setNationality((String) row.get("reserve_nationality"));
		       bean.setReason((String) row.get("reserve_media"));
		       bean.setReserve_comment((String) row.get("reserve_comment"));
		       bean.setPlan_id(row.get("plan_id").toString()); // numeric → Stringに変換
		       return bean;
		       } else {
		    	   return null;// データがなければnull
		       }
	   }
	
	// 予約を予約番号で削除するメソッド　serve⑦
	public boolean deleteReservation(String reserve_num) {
		//予約番号で予約情報を削除
		String sql = "DELETE FROM reserve ";
		       sql += "WHERE reserve_num = " + reserve_num;
		//sql実行　DAOから件数return
        int result = kdao.executeUpdate(sql);
       
        return result > 0;
	}
	
	//予約番号で予約内容の更新メソッド　serve⑧
	public boolean updateReservation(K_Bean reservation) {
		
		String sql = "UPDATE reserve SET ";
	           sql += "reserve_name = '" + reservation.getName() + "', ";
	           sql += "reserve_kana = '" + reservation.getKana() + "', ";
	           sql += "reserve_tel = '" + reservation.getTel() + "', ";
	           sql += "reserve_email = '" + reservation.getEmail() + "', ";
	           sql += "reserve_nationality = '" + reservation.getNationality() + "', ";
			   sql += "reserve_media = '" + reservation.getReason() + "', ";
			   sql += "reserve_comment = '" + reservation.getReserve_comment() + "' ";
			   sql += "WHERE reserve_num = " + reservation.getReserve_num();
	   //SQL実行
	   int result = kdao.executeUpdate(sql);
	   return result > 0;//処理件数return
	}
	
	
}


	
	


