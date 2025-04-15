//戸高　①（予約検索）予約希望日・時間・人数を受け取り、空きプランを取得してJSPに渡す。
package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.ReservDesign;

@WebServlet("/ReservationSearchServlet")
public class ReservationSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		      throws IOException, ServletException {
		
		// 前の検索条件をセッションから取得 backToSearch パラメータをチェック
	    String backToSearch = req.getParameter("backToSearch");
	    
	    if ("true".equals(backToSearch)) {
	        // ReservDesign インスタンス生成
	        ReservDesign rde = new ReservDesign();
	        
	        // backToSearch メソッドを呼び出して空きプランを再検索
	        List<Map<String, Object>> availablePlans = rde.backToSearch(req);
	        
	        // 検索結果をリクエストスコープにセット
	        req.setAttribute("availablePlans", availablePlans);
	        
	        // searchResults.jsp に転送
	        RequestDispatcher rd = req.getRequestDispatcher("/searchResults.jsp");
	        rd.forward(req, res);
	        
	    } else {
		
			//パラメータから受け取って処理するクラス(パラメータ予約希望日、時間、人数を取得)
			ReservDesign rde = new ReservDesign();
			
			//パラメータ値取得
			rde.setReservationSearchServleParams(req);
			
			//パラメータ入力チェック
			String erMsg = rde.validateInput();
			
			//入力チェックでエラー無の場合
			if (erMsg.equals("")) {
				
				//セッションオブジェクト生成sessionIDが振られる
				HttpSession session = req.getSession(true);
			
				//セッションメソッド呼び出しsession渡して値をセット
				rde.setSession(session,req);
				
				//空きプランを検索メソッド呼び出し
				List<Map<String,Object>> availablePlans = rde.timeSet();
	
				
				// 取得したデータをリクエストスコープに保存し、JSPへ渡す
				req.setAttribute("availablePlans", availablePlans);
				RequestDispatcher rd = req.getRequestDispatcher("/searchResults.jsp");
				rd.forward(req, res);
				
			} else {
				req.setAttribute("erMsg", erMsg);
	            RequestDispatcher rd = req.getRequestDispatcher("/KarakuriTop.jsp");
	            rd.forward(req, res);
			}				
		}
	}
    //doGetメソッド
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

}
