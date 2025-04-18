package Menu;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import Commom.T_Bean;
import entity.K_DTO;


public class LogRequest {
	private String staff_id;
	private String password;
	private ArrayList<String> staffIdErrorMsg =new ArrayList<>();
	private ArrayList<String> passErrorMsg =new ArrayList<>();

	
	private T_Bean tBean = new T_Bean();
	
	//DTOの初期化
	K_DTO dto = new K_DTO();
	
	//パラメータ取得、beanに設定
	public LogRequest(HttpServletRequest req) {
		
		try {
			req.setCharacterEncoding("utf-8");
		
			//スタッフID
			this.staff_id = req.getParameter("staff_id")==null ? "" : req.getParameter("staff_id");
			tBean.settStaff_id(staff_id);
			
			//パスワード
			this.password = req.getParameter("password") == null ? "" : req.getParameter("password");
		
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
	}
	

	public boolean logreqCheck() {
		boolean check =true;	
		
		// スタッフIDの入力チェック
	    if (staff_id.isEmpty()) {
	    	staffIdErrorMsg.add("スタッフIDを入力してください");
	        check = false;
	        
	    // スタッフIDの入力チェック（4桁の半角数字）
	    } else if (!staff_id.matches("\\d{4}")) {
	        	staffIdErrorMsg.add("スタッフIDは4桁の半角数字で入力してください");
	            check = false;
	    }

	    // パスワードの入力チェック
	    if (password.isEmpty()) {
	    	passErrorMsg.add("パスワードを入力してください");
	        check = false;
	    }
	
		return check;
	}
	
	//存在チェックメソッド
	public boolean checkUserExists() {
	    boolean userExists = false;

	    //スタッフ情報を取得（staff_id をキーに検索）
	    List<Map<String, Object>> staff = dto.seach(staff_id);

	    //スタッフ情報が空でないか確認
	    if (staff.isEmpty()) {
	    	staffIdErrorMsg.add("このスタッフIDは登録されていません。");
	        return false; // ユーザーが存在しない場合は false を返す
	    }

	    //取得したスタッフ情報の1件目を取得
	    Map<String, Object> staffData = staff.get(0);

	    //データベースから生年月日を取得
	    Object birthDayObj = staffData.get("birth_day");

	    //birthDayObj が java.sql.Date型かどうかをチェック
	    if (birthDayObj instanceof java.sql.Date birthDay) {
	        // データベースの生年月日を yyyy-MM-dd 形式の文字列に変換
	        String formattedBirthDay = birthDay.toString();

	        // 入力されたパスワード（yyyyMMdd）を同じ yyyy-MM-dd 形式に変換
	        String formattedPassword = convertPassword(this.password);


	        // 認証成功時
	        if (formattedBirthDay.equals(formattedPassword)) {
	        	Object nameObj = staffData.get("name");
	            Object poditionObj = staffData.get("podition");
	            Object rankIdObj = staffData.get("rank_id");
	            Object staffIdObj = staffData.get("staff_id");
	            Object avatarObj = staffData.get("avatar");

	            tBean.settName(nameObj != null ? nameObj.toString() : "");
	            tBean.settPodition(poditionObj != null ? poditionObj.toString() : "");
	            tBean.settRank_id(rankIdObj != null ? rankIdObj.toString() : "");
	            tBean.settStaff_id(staffIdObj != null ? staffIdObj.toString() : "");
	            tBean.settAvatar(avatarObj != null ? avatarObj.toString() : "");
	            userExists = true;
	        } else {
	            // パスワードが違う場合
	        	passErrorMsg.add("パスワードが間違えています。");
	        }
	    } else {
	        // 生年月日がnullまたは不正な型だった場合
	    	passErrorMsg.add("登録情報が不正です。管理者にお問い合わせください。");
	    }

	    return userExists;
	}
	
	//パスワード形式変換メソッド（yyyyMMdd）→（yyyy-MM-dd）
	private String convertPassword(String password) {
	    try {
	        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date parsedPassword = inputFormat.parse(password);
	        return outputFormat.format(parsedPassword);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return ""; // エラー時は空文字を返す（認証が失敗するようにする）
	    }
	}
	

 
	
	//LogBeanを返すgetterメソッド(ユーザーIDやパスワード)
	public T_Bean getBean() {
	    return tBean;
	}
	
	//スタッフidのエラーメッセージリストを返す
	public ArrayList<String> getStaffIdErrorMsg() {
	    return staffIdErrorMsg;
	}
	
	//パスワードのエラーメッセージリストを返す
		public ArrayList<String> getPassErrorMsg() {
		    return passErrorMsg;
		}
}
