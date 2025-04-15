package entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import common.K_Bean;
import common.K_DAO;

public class K_DTO implements Serializable {

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
}
