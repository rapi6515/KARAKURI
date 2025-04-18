//戸高　サーブレット⑦予約取消処理
package ecSite;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Commom.K_Bean;
import entity.K_DTO;


@WebServlet("/ReservationDeleteServlet")
public class ReservationDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		req.setCharacterEncoding("UTF-8");
		//セッションオブジェクト生成　bean型予約情報セッションの取得
	    HttpSession session = req.getSession();
		K_Bean reservation = (K_Bean) session.getAttribute("reservation");
		
		//既存予約情報取得できない場合のエラーメッセージ
	    if (reservation == null || reservation.getReserve_num() == null) {
            req.setAttribute("errorMsg", "予約情報がセッションに存在しません。");
            req.getRequestDispatcher("jsp/reservationConfirm.jsp").forward(req, res);
            return;
        }
	    
	    // DTOの予約削除処呼び出し
        K_DTO kdto = new K_DTO();
        boolean isDeleted = kdto.deleteReservation(reservation.getReserve_num());
		
        if (isDeleted) {
            session.removeAttribute("reservation");
            req.setAttribute("successMsg", "予約を取り消しました。");
            req.setAttribute("mode", "deleteComplete");
        } else {
            req.setAttribute("errorMsg", "予約の取消に失敗しました。");
            req.setAttribute("mode", "deleteComplete");
        }

        // 確認画面を再表示（同じJSPでメッセージ表示）
        req.getRequestDispatcher("jsp/reservationConfirm.jsp").forward(req, res);
    }
		

	
	//doGetメソッド
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			doPost(req, res);
	}

}
