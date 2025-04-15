//戸高　④確約サーブレットCompleteReservationServlet
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Commom.K_Bean;
import entity.ReservDesign;

@WebServlet("/CompleteReservationServlet")
public class CompleteReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		      throws IOException, ServletException {
		
		
		
		//セッションからbean型の予約データ("reservation")を取得
		HttpSession session = req.getSession();
		K_Bean reservationBean = (K_Bean) session.getAttribute("reservation");
        
		//セッションからの情報が取れたら
 		if (reservationBean != null) {
 			
 			ReservDesign rde = new ReservDesign();//←設計クラス
 			rde.setBean(reservationBean);//beanを手動でセットするセッター作る
 			
 			//rdeクラスでDTOにbeanを引数にDAOを使用するから
 	    	// 予約をDBに登録method呼び出し〇or×
 			boolean success = rde.saveReservation();
 			
 			//DBへ予約情報登録に成功したら
 			if (success) {
 				req.setAttribute("reservation", reservationBean);
 				
 			    
 			    //確定画面JSPへフォワード
 			    RequestDispatcher rd = req.getRequestDispatcher("/reservationComplete.jsp");
 		        rd.forward(req, res);
 		        
 			} else {
 				//DBへ登録失敗時　エラー表示　入力確認画面へフォワード
 				req.setAttribute("errorMsg", "予約の登録に失敗しました。");
 				RequestDispatcher rd = req.getRequestDispatcher("/reservationConfirm.jsp");
 				rd.forward(req, res);
 			}	
 		} else {
 			req.setAttribute("errorMsg", "予約情報がセッションにありません。");
 		    RequestDispatcher rd = req.getRequestDispatcher("/reservationConfirm.jsp");
 		    rd.forward(req, res);
 		}
 		
	}
	//doGetメソッド
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

}
