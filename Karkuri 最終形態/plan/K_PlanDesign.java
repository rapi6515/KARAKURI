package plan;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import Commom.CharValidator;
import Commom.DateOpe;
import Commom.K_Bean;
import entity.K_DTO;

public class K_PlanDesign implements Serializable {

	private String plan_id; //プランID
	private String t_plan_id; //テーブル用プランID
	private String plan_name; //プラン名
	private String stock; //在庫
	private String cost; //単価
	private String subplan; //サブプラン
	private String contents; //プラン内容
	private String time_required; //正味時間
	private String plan_required; //プラン時間
	private String periodstart; //開始日
	private String periodend; //終了日
	private String staff_memo; //スタッフメモ
	private String job_id1; //職種ID1
	private String job_id2; //職種ID2
	private String job_id3; //職種ID3
	private String btn; //ボタン
	private String actionbtn; //ボタン(新規登録、更新)
	private String image; //画像
	private ArrayList<String> errList; //エラーメッセージリスト

	K_DTO kdto = new K_DTO();
	K_Bean kbean = new K_Bean();

	//コンストラクタ
	public K_PlanDesign(HttpServletRequest req) {
		this.plan_id = req.getParameter("plan_id") == null ? "" : req.getParameter("plan_id");
		this.t_plan_id = req.getParameter("t_plan_id") == null ? "" : req.getParameter("t_plan_id");
		this.plan_name = req.getParameter("plan_name") == null ? "" : req.getParameter("plan_name");
		this.stock = req.getParameter("stock") == null ? "" : req.getParameter("stock");
		this.cost = req.getParameter("cost") == null ? "" : req.getParameter("cost");
		this.subplan = req.getParameter("subplan") == null ? "" : req.getParameter("subplan");
		this.contents = req.getParameter("contents") == null ? "" : req.getParameter("contents");
		this.time_required = req.getParameter("time_required") == null ? "" : req.getParameter("time_required");
		this.plan_required = req.getParameter("plan_required") == null ? "" : req.getParameter("plan_required");
		this.periodstart = req.getParameter("periodstart") == null ? "" : req.getParameter("periodstart");
		this.periodend = req.getParameter("periodend") == null ? "" : req.getParameter("periodend");
		this.staff_memo = req.getParameter("staff_memo") == null ? "" : req.getParameter("staff_memo");
		this.job_id1 = req.getParameter("job_id1") == null ? "" : req.getParameter("job_id1");
		this.job_id2 = req.getParameter("job_id2") == null ? "" : req.getParameter("job_id2");
		this.job_id3 = req.getParameter("job_id3") == null ? "" : req.getParameter("job_id3");
		this.btn = req.getParameter("btn") == null ? "" : req.getParameter("btn");
		this.actionbtn = req.getParameter("actionbtn") == null ? "" : req.getParameter("actionbtn");
		this.image = req.getParameter("image") == null ? "" : req.getParameter("image");
		this.errList = new ArrayList<>(); //errListの初期化
	}

	//エラー表示
	public K_Bean reqCheck() {

		//ボタンの初期化
		if (actionbtn == null) {
			actionbtn = "";
		}

		//入力チェック(入力フォームすべての項目！)
		if (actionbtn.equals("insert") || actionbtn.equals("update")) {

			//プランID
			if (plan_id == null || plan_id.isEmpty()) {
				errList.add("プランIDを入力してください。");
				plan_id = "";
			} else if (!CharValidator.isInteger(plan_id)) {
				errList.add("プランIDを数字で入力してください。");
			//新規登録時のみ重複チェック
			} else if (actionbtn.equals("insert")) { 
			List<Map<String, Object>> NGID = kdto.getTable(plan_id);
			if (!NGID.isEmpty()) {
				errList.add("入力されたプランIDは既に存在します。");
			}
		}
		kbean.setPlan_id(plan_id);

		//プラン名
		if (plan_name == null || plan_name.isEmpty()) {
			errList.add("プラン名を入力してください。");
			plan_name = "";
		}
		kbean.setPlan_name(plan_name);

		//画像パス
		if (image == null || image.isEmpty()) {
			errList.add("画像パスを入力してください。");
			image = "";
		}
		kbean.setImage(image);

		//在庫
		if (stock == null || stock.isEmpty()) {
			errList.add("在庫数を入力してください。");
			stock = "";
		} else if (!CharValidator.isInteger(stock)) {
			errList.add("在庫数を数字で入力してください。");
		}
		kbean.setStock(stock);

		//単価
		if (cost == null || cost.isEmpty()) {
			errList.add("単価を入力してください。");
			cost = "";
		} else if (!CharValidator.isInteger(cost)) {
			errList.add("単価を数字で入力してください。");
		}
		kbean.setCost(cost);

		//サブプラン
		if (subplan == null || subplan.isEmpty()) {
			errList.add("サブプランを入力してください。");
			subplan = "";
		}
		kbean.setSubplan(subplan);

		//プラン内容
		if (contents == null || contents.isEmpty()) {
			errList.add("プラン内容を入力してください。");
			contents = "";
		}
		kbean.setContents(contents);

		//正味所要
		if (time_required == null || time_required.isEmpty()) {
			errList.add("正味所要時間を入力してください。");
			time_required = "";
		} else if (!CharValidator.isValidTime(time_required)) {
			errList.add("正味所要時間の形式が不正です。HH:mm:ss の形式で入力してください。");
		}
		kbean.setTime_required(time_required);

		//プラン所要
		if (plan_required == null || plan_required.isEmpty()) {
			errList.add("プラン所要時間を入力してください。");
			plan_required = "";
		} else if (!CharValidator.isValidTime(plan_required)) {
			errList.add("プラン所要時間の形式が不正です。HH:mm:ss の形式で入力してください。");
		}
		kbean.setPlan_required(plan_required);

		//職種ID1
		if (job_id1 == null || job_id1.isEmpty()) {
			errList.add("職種IDを入力してください。");
			job_id1 = "";
		} else if (!CharValidator.isInteger(job_id1)) {
			errList.add("職種IDを数字で入力してください。");
		}
		kbean.setJob_id1(job_id1);

		//職種ID2
		if (job_id2 == null || job_id2.isEmpty()) {
			job_id2 = "";
		}
		kbean.setJob_id2(job_id2);

		//職種ID3
		if (job_id3 == null || job_id3.isEmpty()) {
			job_id3 = "";
		}
		kbean.setJob_id3(job_id3);

		//開始日
		if (periodstart == null || periodstart.isEmpty()) {
			errList.add("開始日を入力してください。");
			periodstart = "";
		} else if (DateOpe.datenIspecttion(periodstart) != 0) {
			errList.add("開始日をyyyy/mm/ddの形で入力してください。");
		}
		kbean.setPeriodstart(periodstart);

		//終了日
		if (periodend == null || periodend.isEmpty()) {
			errList.add("終了日を入力してください。");
			periodend = "";
		} else if (DateOpe.datenIspecttion(periodend) != 0) {
			errList.add("終了日をyyyy/mm/ddの形で入力してください。");
		}
		kbean.setPeriodend(periodend);

		//コメント(自由入力)
		if (staff_memo == null || staff_memo.isEmpty()) {
			staff_memo = "";
		}
		kbean.setStaff_memo(staff_memo);
	}return kbean;

	}

	// エラーリスト取得メソッド
	public ArrayList<String> getErrList() {
		return errList;
	}

	public List<Map<String, Object>> dataList() {
		String p_plan_id = "";
		if (btn.equals("edit")) {
			p_plan_id = t_plan_id;
		} else {
			p_plan_id = plan_id;
		}
		
		//planIDを元にデータベースからデータを取得
		List<Map<String, Object>> planData = kdto.getTable(p_plan_id);

		if (planData != null && !planData.isEmpty()) {
			Map<String, Object> data = planData.get(0);
			kbean.setPlan_id((data.get("plan_id").toString()));
			kbean.setPlan_name((String) (data.get("plan_name")));
			kbean.setStock((String) (data.get("stock").toString()));
			kbean.setCost((String) (data.get("cost").toString()));
			kbean.setSubplan((String) (data.get("subplan")));
			kbean.setContents((String) (data.get("contents")));
			kbean.setTime_required((String) (data.get("time_required").toString()));
			kbean.setPlan_required((String) (data.get("plan_required").toString()));
			kbean.setPeriodstart((String) (data.get("periodstart").toString()));
			kbean.setPeriodend((String) (data.get("periodend").toString()));
			kbean.setStaff_memo((String) (data.get("staff_memo")));
			kbean.setJob_id1((String) (data.get("job_id1")));
			kbean.setJob_id2((String) (data.get("job_id2")));
			kbean.setJob_id3((String) (data.get("job_id3")));
			kbean.setImage((String) (data.get("image")));
		}
		return planData;
	}

	public K_Bean getKbean() {
		return kbean;
	}

	//データの表示(一覧表示)
	public List<Map<String, Object>> planDataList(String planId) {
		List<Map<String, Object>> data = null;

		//プランIDがすでに登録されている場合にデータ表示をする
		if (planId != null && !planId.isEmpty()) {
			data = kdto.getTable(planId);
		}
		//全件
		else {
			data = kdto.getTable("");
		}
		return data;
	}
}