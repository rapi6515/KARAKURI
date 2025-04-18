package entity;

import java.util.List;
import java.util.Map;

import Commom.K_Bean;
import Commom.K_DAO;

public class K_DTO {
	private int id; // 予約ID
	private String reserve_day; // 予約日
	private String reserve_time; // 予約時間
	private String plan_id; // プランID
	private String reserve_comment; // コメント

	/** デフォルトコンストラクタ（引数なし） */
	public K_DTO() {
	}

	/** 全フィールドを初期化するコンストラクタ */
	public K_DTO(int id, String reserve_day, String reserve_time, String plan_id, String reserve_comment) {
		this.id = id;
		this.reserve_day = reserve_day;
		this.reserve_time = reserve_time;
		this.plan_id = plan_id;
		this.reserve_comment = reserve_comment;
	}

	public String getOnlyDate() {
		if (reserve_day != null && reserve_day.contains("T")) {
			return reserve_day.split("T")[0];
		}
		return reserve_day;
	}

	// --- Getter (データを取得するメソッド) ---
	public int getId() {
		return id;
	}

	public String getReserve_day() {
		return reserve_day;
	}

	public String getReserve_time() {
		return reserve_time;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public String getReserve_comment() {
		return reserve_comment;
	}

	// --- Setter (データをセットするメソッド) ---
	public void setId(int id) {
		this.id = id;
	}

	public void setReserve_day(String reserve_day) {
		this.reserve_day = reserve_day;
	}

	public void setReserve_time(String reserve_time) {
		this.reserve_time = reserve_time;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public void setReserve_comment(String reserve_comment) {
		this.reserve_comment = reserve_comment;
	}

	K_DAO dao = new K_DAO();
	List<Map<String, Object>> list = null;

	//form_select
	public List<Map<String, Object>> select(String key) {

		String sql = "SELECT * FROM typevalue \n";//\nで改行
		sql += "WHERE key ='" + key + "' \n";
		sql += "ORDER BY seq";

		return dao.executeQuery(sql);
	}

	//IDで検索に一致するスタッフ情報の取得
	public List<Map<String, Object>> selectStaffId(String staff_id) {

		String sql = "SELECT stf.*, \n";
		sql += "       job.textvalue AS job_name, \n";
		sql += "       rank.textvalue AS rank_name \n";
		sql += "FROM staff stf \n";
		sql += "LEFT JOIN typevalue job ON job.key = 'job_name'\n";
		sql += "                         AND job.seq = stf.job_id \n";
		sql += "LEFT JOIN typevalue rank ON rank.key = 'rank' \n";
		sql += "                         AND rank.seq = stf.rank_id \n";
		sql += "LEFT JOIN typevalue sex ON sex.key = 'sex'\n";
		sql += "						AND sex.seq = stf.sex \n";
		sql += "WHERE 1=1 \n";

		if (!staff_id.isEmpty()) {
			sql += "AND staff_id = " + staff_id;
		}

		return dao.executeQuery(sql);

	}

	// 全件表示（役職名・職種名・権限名も取得する）
	public List<Map<String, Object>> selectAll(String staff_id) {

		String sql = "SELECT stf.*, \n";
		sql += "       job.textvalue AS job_name, \n";
		sql += "       rank.textvalue AS rank_name, \n";
		sql += "       pod.textvalue AS podition_name \n";
		sql += "FROM staff stf \n";
		sql += "LEFT JOIN typevalue job ON job.key = 'job_name' \n";
		sql += "                       AND job.seq = stf.job_id \n";
		sql += "LEFT JOIN typevalue rank ON rank.key = 'rank' \n";
		sql += "                       AND rank.seq = stf.rank_id \n";
		sql += "LEFT JOIN typevalue pod ON pod.key = 'podition' \n";
		sql += "                       AND pod.seq = stf.podition \n";
		sql += "WHERE 1 = 1 \n";

		// staff_id が指定されていれば条件に追加
		if (staff_id != null && staff_id.matches("\\d+")) {
			sql += "AND stf.staff_id = " + staff_id + " \n";
		}

		sql += "ORDER BY stf.staff_id";

		return dao.executeQuery(sql);

	}

	// ページネーション付き 全件取得
	public List<Map<String, Object>> selectAllWithPagination(int offset, int limit) {

		String sql = "SELECT stf.*, \n";
		sql += "       job.textvalue AS job_name, \n";
		sql += "       rank.textvalue AS rank_name, \n";
		sql += "       pod.textvalue AS podition_name \n";
		sql += "FROM staff stf \n";
		sql += "LEFT JOIN typevalue job ON job.key = 'job_name' \n";
		sql += "                       AND job.seq = stf.job_id \n";
		sql += "LEFT JOIN typevalue rank ON rank.key = 'rank' \n";
		sql += "                       AND rank.seq = stf.rank_id \n";
		sql += "LEFT JOIN typevalue pod ON pod.key = 'podition' \n";
		sql += "                       AND pod.seq = stf.podition \n";
		sql += "ORDER BY stf.staff_id \n";
		sql += "LIMIT " + limit + " OFFSET " + offset;

		return dao.executeQuery(sql);
	}

	// 全スタッフ件数を取得（ページ数計算用）
	public int getTotalStaffCount() {
		String sql = "SELECT COUNT(*) AS total FROM staff";
		List<Map<String, Object>> result = dao.executeQuery(sql);

		if (result != null && !result.isEmpty()) {
			Object count = result.get(0).get("total");
			if (count != null) {
				return Integer.parseInt(count.toString());
			}
		}
		return 0;
	}

	//登録
	public int insert(K_Bean bean) {

		String sql = "INSERT INTO staff (\n";
		sql += " staff_id \n";
		sql += ",podition \n";
		sql += ",job_id \n";
		sql += ",sex \n";
		sql += ",rank_id \n";
		sql += ",name \n";
		sql += ",tel \n";
		sql += ",address \n";
		sql += ",residence \n";
		sql += ",birth_day \n";
		sql += ",avatar \n";
		sql += ") VALUES ( \n";
		sql += bean.getStaff_id() + "\n";
		sql += "," + bean.getPodition() + "\n";
		sql += "," + bean.getJob_name() + "\n";
		sql += "," + bean.getSex() + "\n";
		sql += "," + bean.getRank() + "\n";
		sql += ",'" + bean.getName() + "'\n";
		sql += ",'" + bean.getTel() + "'\n";
		sql += ",'" + bean.getAddress() + "'\n";
		sql += ",'" + bean.getResidence() + "'\n";
		sql += ",'" + bean.getBirth_day() + "'\n";
		sql += ",'" + bean.getAvatar() + "'\n";
		sql += ")";

		return dao.executeUpdate(sql);
	}

	//ID指定して更新
	public int update(K_Bean bean) {

		String sql = "UPDATE staff SET \n";
		sql += "podition = " + bean.getPodition() + "\n";
		sql += ",name = '" + bean.getName() + "'\n";
		sql += ",job_id = " + bean.getJob_name() + "\n ";
		sql += ",tel = '" + bean.getTel() + "'\n";
		sql += ",sex = " + bean.getSex() + "\n";
		sql += ",rank_id = " + bean.getRank() + " \n";
		sql += ",address = '" + bean.getAddress() + "'\n ";
		sql += ",birth_day = '" + bean.getBirth_day() + "'\n ";
		sql += ",residence = '" + bean.getResidence() + "'\n ";
		sql += ",avatar = '" + bean.getAvatar() + "'\n";
		sql += "WHERE staff_id = " + bean.getStaff_id();

		return dao.executeUpdate(sql);
	}

	public int delete(String staff_id) {

		String sql = "DELETE FROM staff \n";
		sql += "WHERE staff_id = '" + staff_id + "'";

		return dao.executeUpdate(sql);

	}

	//DAOのオブジェクト生成
	K_DAO Kdao = new K_DAO();
	K_Bean bean = new K_Bean();

	//テーブルデータ取得
	public List<Map<String, Object>> getTable(String plan_id) {
		String sql = "SELECT * FROM plan WHERE 1=1 ";

		if (plan_id != null && !plan_id.isEmpty()) {
			sql += "AND plan_id = '" + plan_id + "' ";
		}

		sql += "ORDER BY plan_id ASC";

		List<Map<String, Object>> data = Kdao.executeQuery(sql);

		return data;
	}

	// 新規登録
	public int planinsert(K_Bean bean) {

		String sql = "INSERT INTO plan (\n";
		sql += "plan_id \n";
		sql += ",plan_name \n";
		sql += ",stock \n";
		sql += ",cost \n";
		sql += ",image \n";
		sql += ",subplan \n";
		sql += ",contents \n";
		sql += ",time_required \n";
		sql += ",plan_required \n";
		sql += ",periodstart \n";
		sql += ",periodend \n";
		sql += ",staff_memo \n";
		sql += ",job_id1 \n";
		sql += ",job_id2 \n";
		sql += ",job_id3 \n";
		sql += ") VALUES ( \n";
		sql += bean.getPlan_id() + " \n"; //numeric
		sql += ",'" + bean.getPlan_name() + "' \n";
		sql += ", " + bean.getStock() + " \n"; //numeric
		sql += ", " + bean.getCost() + " \n"; //numeric
		sql += ",'" + bean.getImage() + "'\n";
		sql += ",'" + bean.getSubplan() + "' \n";
		sql += ",'" + bean.getContents() + "' \n";
		sql += ",'" + bean.getTime_required() + "' \n";
		sql += ",'" + bean.getPlan_required() + "' \n";
		sql += ",'" + bean.getPeriodstart() + "' \n";
		sql += ",'" + bean.getPeriodend() + "' \n";
		sql += ",'" + bean.getStaff_memo() + "' \n";
		sql += ",'" + bean.getJob_id1() + "' \n";
		sql += ",'" + bean.getJob_id2() + "' \n";
		sql += ",'" + bean.getJob_id3() + "' \n";
		sql += " )";

		return Kdao.executeUpdate(sql);
	}

	//更新
	public int planupdate(K_Bean bean) {

		String sql = "UPDATE plan SET \n";
		sql += "plan_name = '" + bean.getPlan_name() + "', \n";
		sql += "stock = " + bean.getStock() + ", \n";
		sql += "cost = " + bean.getCost() + ", \n";
		sql += "image = '" + bean.getImage() + "', \n";
		sql += "subplan = '" + bean.getSubplan() + "', \n";
		sql += "contents = '" + bean.getContents() + "', \n";
		sql += "time_required = '" + bean.getTime_required() + "', \n";
		sql += "plan_required = '" + bean.getPlan_required() + "', \n";
		sql += "periodstart = '" + bean.getPeriodstart() + "', \n";
		sql += "periodend = '" + bean.getPeriodend() + "', \n";
		sql += "staff_memo = '" + bean.getStaff_memo() + "', \n";
		sql += "job_id1 = '" + bean.getJob_id1() + "', \n";
		sql += "job_id2 = '" + bean.getJob_id2() + "', \n";
		sql += "job_id3 = '" + bean.getJob_id3() + "' \n";
		sql += "WHERE plan_id = " + bean.getPlan_id(); //plan_idを元に登録、編集！？

		return Kdao.executeUpdate(sql);
	}

	// 全件取得
	public List<Map<String, Object>> getAllTable() {
		String sql = "SELECT * FROM plan";

		return Kdao.executeQuery(sql);
	}

	//削除
	public int plandelete(String plan_id) {
		String sql = "DELETE FROM plan \n";
		sql += "WHERE plan_id = " + plan_id;

		return Kdao.executeUpdate(sql);
	}


	// create by okada(スタッフ情報全件検索)
	public List<Map<String, Object>> seach(String staff_id) {
		String sql = "";
		sql += "SELECT \n";
		sql += "staff_id \n"; //スタッフID
		sql += ",birth_day \n"; //生年月日
		sql += ",name \n"; //名前
		sql += ",podition \n"; //役職
		sql += ",rank_id \n"; //権限
		sql += ",avatar \n";
		sql += "FROM staff \n";
		sql += "WHERE 1 = 1";

		if (!staff_id.isEmpty()) {
			sql += "AND staff_id = '" + staff_id + "'";
		}

		return kdao.executeQuery(sql);
	}

	// create by okada（メニューリスト取得）
	public List<Map<String, Object>> getmenu() {
		String sql = "";
		sql += "SELECT \n";
		sql += "seq \n";
		sql += ",textvalue \n";
		sql += ",remarks \n";
		sql += ",comment \n";
		sql += "FROM \n";
		sql += "typevalue \n";
		sql += "WHERE \n";
		sql += "key='menu' \n";
		sql += "ORDER BY \n";
		sql += "seq \n";

		return kdao.executeQuery(sql);

	}
	
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

