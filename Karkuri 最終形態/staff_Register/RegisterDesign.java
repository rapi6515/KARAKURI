package staff_Register;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import Commom.CharValidator;
import Commom.K_Bean;
import entity.K_DTO;

public class RegisterDesign {
	//パラメーター
	private String staff_id = ""; //スタッフID
	private String podition = ""; //役職
	private String name = ""; //氏名
	private String job_name = ""; //職種名
	private String tel = ""; //電話番号
	private String birth_day = "";//誕生日
	private String sex = ""; //性別
	private String rank = ""; //権限
	private String address = ""; //住所
	private String residence = ""; //建物名
	private String avatar = ""; //アバター
	private String btn = ""; //パラメーターボタン
	private String actionType = ""; //何の処理かを覚える変数
	//private boolean firstFlg = false; //初回判定フラグ
	int count = -1;
	private List<Map<String, Object>> list = new ArrayList<>();//１リスト
	private List<Map<String, Object>> allList = new ArrayList<>();//全件リスト

	//DTOオブジェクト生成＊DB操作用
	K_DTO dto = new K_DTO();

	//Beanオブジェクト生成＊入力値保持用
	K_Bean bean = new K_Bean();

	//コンストラクタ(リクエストからパラメータ取得

	public RegisterDesign(HttpServletRequest request) {//コンストラクタ
		try {
			request.setCharacterEncoding("utf-8");//文字コード設定

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		//インスタンス生成しJSPのnameからパラメーター取得
		// Bean に各値をセット
		//nullだったら空文字に置き換えてNULLじゃなかったらビーンにせっと
		//条件？式1true：式２false

		this.staff_id = request.getParameter("staff_id") == null ? "" : request.getParameter("staff_id");
		bean.setStaff_id(staff_id);

		this.podition = request.getParameter("podition") == null ? "" : request.getParameter("podition");
		bean.setPodition(podition);
		this.name = request.getParameter("name") == null ? "" : request.getParameter("name");
		bean.setName(name);
		this.job_name = request.getParameter("job_name") == null ? "" : request.getParameter("job_name");
		bean.setJob_name(job_name);
		this.tel = request.getParameter("tel") == null ? "" : request.getParameter("tel");
		bean.setTel(tel);
		this.birth_day = request.getParameter("birth_day") == null ? "" : request.getParameter("birth_day");
		bean.setBirth_day(birth_day);
		this.sex = request.getParameter("sex") == null ? "" : request.getParameter("sex");
		bean.setSex(sex);
		this.rank = request.getParameter("rank") == null ? "" : request.getParameter("rank");
		bean.setRank(rank);
		this.address = request.getParameter("address") == null ? "" : request.getParameter("address");
		bean.setAddress(address);
		this.residence = request.getParameter("residence") == null ? "" : request.getParameter("residence");
		bean.setResidence(residence);
		this.avatar = request.getParameter("avatar") == null ? "" : request.getParameter("avatar");
		bean.setAvatar(avatar);
		this.btn = request.getParameter("btn") == null ? "" : request.getParameter("btn");
		bean.setBtn(btn);

	}

	//セッションリスト
	public void sessionList(HttpSession session) {
		//podition<役職>
		List<Map<String, Object>> podition = (List<Map<String, Object>>) session.getAttribute("podition");//初回あくせす
		if (podition == null) {
			List<Map<String, Object>> listP = dto.select("podition");
			session.setAttribute("podition", listP);
			//firstFlg=true;
		}

		//job_name<職種>
		List<Map<String, Object>> job_name = (List<Map<String, Object>>) session.getAttribute("job_name");

		if (job_name == null) {
			List<Map<String, Object>> listJ = dto.select("job_name");
			session.setAttribute("job_name", listJ);
		}

		//sex<性別>
		List<Map<String, Object>> sex = (List<Map<String, Object>>) session.getAttribute("sex");

		if (sex == null) {
			List<Map<String, Object>> listS = dto.select("sex");
			session.setAttribute("sex", listS);
		}

		//rank<権限>     
		List<Map<String, Object>> rank = (List<Map<String, Object>>) session.getAttribute("rank");
		if (rank == null) {
			List<Map<String, Object>> listR = dto.select("rank");
			session.setAttribute("rank", listR);
		}
	}

	//入力チェック

	public boolean check() {

		//初回起動時(sessionオブジェクト未設定の時、チェック無し)
		boolean ok = true;//変数名
		if (btn == "") {
			return !ok;
		}

		//IDチェック（検索/登録/更新/削除/必須入力)

		if (staff_id == null || staff_id.isEmpty()) {
			ok = false;

			bean.setErrStf("StaffIDを入力してください");
		} else if (!CharValidator.isInteger(staff_id)) {
			ok = false;

			bean.setErrStf("StaffIDは半角数字で入力してください");
		}

		// IDがすでに存在しているかチェック（登録時のみ）
		if (btn.equals("登録")) {
			List<Map<String, Object>> checkList = dto.selectStaffId(staff_id);
			if (checkList != null && !checkList.isEmpty()) {
				ok = false;

				bean.setErrStf("このStaffIDは既に存在しています。別のIDを入力してください\n");
			}
		}

		if (btn.equals("検索") || btn.equals("削除")) {
			return ok;
		}

		// 名前チェック(登録/必須)

		if (name.isEmpty()) {
			ok = false;

			bean.setErrName("名前を入力してください");

		} else if (!CharValidator.isFull(name)) {
			ok = false;

			bean.setErrName("全角文字で入力してください");
		}

		//職種チェック(登録/必須)

		if (!CharValidator.isInteger(job_name)) {
			ok = false;

			bean.setErrJob("職種を選択してください");

		}

		//性別チェック(登録/必須)

		if (!CharValidator.isInteger(sex)) {
			ok = false;

			bean.setErrSex("性別を選択してください");

		}

		//権限チェック(登録/必須)

		if (!CharValidator.isInteger(rank)) {
			ok = false;

			bean.setErrRank("権限を選択してください");

		}

		//役職チェック(登録/必須)

		if (!CharValidator.isInteger(podition)) {
			ok = false;

			bean.setErrPod("役職を選択してください");
		}

		// 電話番号チェック（数字とハイフンのみ可登録/必須）

		if (tel.isEmpty()) {
			ok = false;

			bean.setErrTel("電話番号を入力してください");

		} else if (!CharValidator.isTel(tel)) {
			ok = false;

			bean.setErrTel("電話番号は数字とハイフンのみで入力してください");

		}

		// 誕生日（必須／yyyy-MM-dd形式）
		if (name.isEmpty()) {
			ok = false;

			bean.setErrBirth("誕生日を入力してください");
		} else if (!CharValidator.isBirthday(birth_day)) {
			ok = false;

			bean.setErrBirth("誕生日はyyyy-MM-dd形式で入力してください");

		}

		//住所チェック(登録/必須)全角文字・半角数字のみ可
		if (address.isEmpty()) {
			ok = false;

			address = "";
			bean.setErrAddress("住所を入力してください");

		} else if (!CharValidator.isAddressValid(address)) {
			ok = false;

			bean.setErrAddress("住所は日本語、全角記号、数字のみで入力してください");
		}

		// 建物名チェック（任意入力）
		if (!residence.isEmpty()) {
			if (!CharValidator.isBuildingNameValid(residence)) {
				ok = false;

				bean.setErrResidence("建物名に使用できない文字が含まれています");
			}
		}

		// アバターチェック（任意入力）
		if (!avatar.isEmpty()) {
			if (!CharValidator.isBuildingNameValid(avatar)) {
				ok = false;
				bean.setErrAvatar("アバター名に使用できない文字が含まれています");

			}
		}

		return ok;// 全てのチェックが成功した場合にtrueを返す

	}

	//ボタン処理実行

	public int process() {

		//入力されたIDで情報取得

		if (btn.equals("検索")) {
			
			 actionType = "search";
			 
			list = dto.selectStaffId(staff_id);
			count = list.size();//取得件数を返す
			setBean();//検索結果をbeanにセット

			//新規staffを登録

		} else if (btn.equals("登録")) {
			
			actionType = "insert";
			
			count = dto.insert(bean);

			//指定されたIDの更新

		} else if (btn.equals("更新")) {
			
			 actionType = "update";

			count = dto.update(bean);

			//指定されたIDの削除

		} else if (btn.equals("削除")) {
			
			actionType = "delete";
			
			count = dto.delete(staff_id);
		}

		return count;

	}

	//取得したデータ1件だけBeanにSet＊詳細表示用
	public void setBean() {
		if (list != null && list.size() == 1) {
			Map<String, Object> data = list.get(0);

			bean.setStaff_id(getStr(data, "staff_id"));
			bean.setPodition(getStr(data, "podition"));
			bean.setName(getStr(data, "name"));
			bean.setJob_name(getStr(data, "job_id"));
			bean.setTel(getStr(data, "tel"));
			bean.setBirth_day(getStr(data, "birth_day"));
			bean.setSex(getStr(data, "sex"));
			bean.setRank(getStr(data, "rank_id"));
			bean.setAddress(getStr(data, "address"));
			bean.setResidence(getStr(data, "residence"));
			bean.setAvatar(getStr(data, "avatar"));

		} else {
			//1件じゃなかったらBeanをクリア
			bean.setPodition("");
			bean.setName("");
			bean.setJob_name("");
			bean.setTel("");
			bean.setBirth_day("");
			bean.setSex("");
			bean.setRank("");
			bean.setAddress("");
			bean.setResidence("");
			bean.setAvatar("");
		}
	}

	//nullチェック
	private String getStr(Map<String, Object> map, String key) {
		Object value = map.get(key);
		return value != null ? value.toString() : "";
	}

	//Allリスト取得
	public List<Map<String, Object>> getAllList() {
		allList = dto.selectAll(null);

		return allList;
	}

	//1リストを取得
	public List<Map<String, Object>> getList() {
		list = dto.selectStaffId(staff_id);

		return list;
	}

	//Beanを取得
	public Object getBean() {
		list = dto.selectStaffId(staff_id);
		setBean();
		return bean;
	}
	
	public String getActionType() {
	    return actionType;
	}

	

}
