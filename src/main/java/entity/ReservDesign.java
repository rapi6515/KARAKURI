//戸高　からくり設計クラス
package entity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import Commom.CharValidator;
import Commom.K_Bean;

public class ReservDesign {
	
	//パラメータ予約希望日、時間、人数を取得
	private String date; // 予約希望日
	private String time; // "10:00～11:00" のようなフォーマット
	private String peopleStr;// 予約人数（文字列）
	private String name;//予約者の名前
	private String kana;//カナ
	private String tel;//連絡先
	private String email;//メアド
	private String nationality;//国籍
	private String[] reason;// チェックボックスの値
	private String reserve_comment;//備考欄コメント
	private String planId;//プランID
	private String reserve_num;//自動採番の予約番号
	
	//K_Beanインスタンス生成
	K_Bean bean = new K_Bean();
	//K_DTOインスタンス生成
	K_DTO kdto = new K_DTO();
	
	//パラメータ値取得メソッド（ReservationSearchServle①用
	public void setReservationSearchServleParams(HttpServletRequest req) throws UnsupportedEncodingException {
			req.setCharacterEncoding("utf-8");
			date = req.getParameter("date"); // 予約希望日
		    time = req.getParameter("time"); // "10:00～11:00" のようなフォーマ
		    peopleStr = req.getParameter("people"); // 予約人数（文字列）		     
	}
	
	//検索条件の入力エラーチェックメソッド
	public String validateInput() {
	    String erMsg = "";
	    
		if (date == null || date.trim().isEmpty()) {
	        erMsg = "日付が未入力です\t";
	    } 
	    
	    if (time == null || time.trim().isEmpty()) {
	        erMsg += "時間が未入力です\t";
	    }
	    if (peopleStr == null || peopleStr.trim().isEmpty()) {
	        erMsg += "人数が未入力です\t";
	    }
	    return erMsg;	    
	}
	
	// セッションが存在しない場合にのみ初期値設定、受け取った値をセッションにセットするメソッド
	public void setSession(HttpSession session, HttpServletRequest req) {
	    session.setAttribute("date", req.getParameter("date"));
	    session.setAttribute("time", req.getParameter("time"));
	    session.setAttribute("people", req.getParameter("people"));
	}
	
	//パラメータ値取得メソッド（ReservationServlet③用
	public void setReservationServletParams(HttpServletRequest req) throws UnsupportedEncodingException {
		req.setCharacterEncoding("utf-8");
		planId = req.getParameter("plan_id");
		name = req.getParameter("name");//予約者の名前
		kana = req.getParameter("kana");//カナ
		tel = req.getParameter("tel");//連絡先
		email = req.getParameter("email");//メアド
		nationality = req.getParameter("nationality");//国籍
		reason = req.getParameterValues("reason");// チェックボックスの値
		reserve_comment = req.getParameter("reserve_comment");//備考欄コメント		
		reserve_num = req.getParameter("reserve_num");
		
	}
	
	
	//希望日時を受取空きプランを検索メソッドサーブれ①
	public List<Map<String, Object>> timeSet() {
		try {		    
	        // peopleStr が null または空文字かチェック
	        if (peopleStr == null || peopleStr.trim().isEmpty()) {
	            throw new IllegalArgumentException("人数が未入力です");
	        }
	        
	        // 文字列を整数に変換
	        int people = Integer.parseInt(peopleStr);
	        
	        // 時間を分割して開始時間・終了時間を取得
	        String[] timeParts = time.split("～"); // "10:00～11:00" → ["10:00", "11:00"]
	        String reserveStartTime = timeParts.length > 0 ? timeParts[0] : "";
	        String reserveEndTime = timeParts.length > 1 ? timeParts[1] : "";
	        
	        return kdto.searchAvailablePlans(date, reserveStartTime, reserveEndTime, people);		
	        
	    } catch (NumberFormatException e) {
	        System.err.println("Error: 数値変換に失敗しました！peopleStr='" + peopleStr + "'");
	        e.printStackTrace();
	        throw new IllegalArgumentException("人数が不正な値です: " + peopleStr);
	    } catch (IllegalArgumentException e) {
	        System.err.println("Error: " + e.getMessage());
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	// backToSearchメソッドを追加（セッションから情報取得し再度プラン表示させる
    public List<Map<String, Object>> backToSearch(HttpServletRequest req) {
        // セッションから検索条件を取得
        HttpSession session = req.getSession();
        date = (String) session.getAttribute("date");
        time = (String) session.getAttribute("time");
        peopleStr = (String) session.getAttribute("people");
        
        // 空きプランを再検索
        List<Map<String, Object>> availablePlans = null;
        if (date != null && time != null && peopleStr != null) {
            availablePlans = timeSet();  // 空きプラン検索メソッド
        }
        
        return availablePlans;
    }
    
	
	//プラン詳細情報取得のためのメソッドサーブレット②用
	public Map<String, Object> getPlanDetail(String planId) {
	    return kdto.getPlanDetailById(planId);
	}
	
	
	//受け取ったFormのパラメタをbeanにセットメソッド　serve③で使用
	public void beanSetParams() {
		bean.setPlan_id(planId);
		bean.setName(name);
		bean.setKana(kana);
		bean.setTel(tel);
		bean.setEmail(email);
		bean.setNationality(nationality);
		bean.setReserve_num(reserve_num);
		
		//reasonをStringに変換
		String reasonStr = "";
		if(reason != null) {
			for(String s : reason) {
				reasonStr += s + ",";
			}
			if(reasonStr.length() > 0) {
				reasonStr =reasonStr.substring(0,reasonStr.length() - 1);
			}
		}
		bean.setReason(reasonStr);
		bean.setReserve_comment(reserve_comment);	
	}
	
	
	//beanを呼び出しするメソッド　サーブレット③で使用
	public K_Bean getBean() {
        return bean;
    }
	
	
	//K_Beanを引数にしてエラーチェッㇰするメソッド　サーブレット③で使用
	public String validateForm() {
		String errorMsg ="";
		
		//氏名（name)のエラーチェック
		if(bean.getName().isEmpty() || !CharValidator.isFull(bean.getName()) ) {
			errorMsg = "氏名は全角で入力してください\n";
		}
		
		//カナ（kana）のエラーチェック
		if(bean.getKana().isEmpty() || !CharValidator.isKanaFull(bean.getKana())) {
			errorMsg += "カナは全角カタカナで入力してください\n";
		}
		
		//電話番号(tel）のエラーチェック
		if (bean.getTel().isEmpty() || !CharValidator.isInteger(bean.getTel())) {
			errorMsg += "電話番号（ハイフン）なし、半角数字で入力してください\n";
		}
		
		//メールアドレス（email）のエラーチェック
		if (bean.getEmail() == null || bean.getEmail().isEmpty()){ //&& !CharValidator.isHalf(bean.getEmail())) {
			errorMsg += "emailは半角英数字で入力してください\n";
		}
		
		//国籍（nationality）のエラーチェック
		if (bean.getNationality().isEmpty() || !CharValidator.isKanaFull(bean.getNationality())) {
            errorMsg += "国籍は全角で入力してください\n";
        }
		
		//備考欄（reserve_comment）のエラーチェック（全角200文字以内）
		if (bean.getReserve_comment() == null || bean.getReserve_comment().isEmpty() || bean.getReserve_comment().length() > 200 || !CharValidator.isFull(bean.getReserve_comment())) {
			errorMsg += "備考欄は全角200文字以内で入力してください";
		}
		
		return errorMsg;
	}
	
	
	//手動でセッション情報をbeanにセットするメソッド　serve④で使用
	public void setBean(K_Bean bean) {
	    this.bean = bean;
	}
	
	
	// DTOを使用して予約情報をデータベースに登録　サーブレット④で使用
	public boolean saveReservation() {
	    boolean isSaved = kdto.saveReservation(bean);
	    if (isSaved) {
	        // 保存された予約番号を取得してセット
	        String reservationNum = bean.getReserve_num();
	        // 予約番号を画面に表示するため、予約番号をbeanにセット（必要な場合）
	        System.out.println("予約番号: " + reservationNum);
	    }
	    return isSaved;
	}
	
	
}




