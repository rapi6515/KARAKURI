//戸高　サーブレット⑧予約内容変更処理
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Commom.K_Bean;
import entity.K_DTO;


@WebServlet("/ReservationUpdateServlet")
public class ReservationUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//セッション情報取得
		req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        K_Bean reservation = (K_Bean) session.getAttribute("reservation");
		
    	//既存予約情報取得できない場合のエラーメッセージ
	    if (reservation == null || reservation.getReserve_num() == null) {
            req.setAttribute("errorMsg", "予約情報がセッションに存在しません。");
            req.getRequestDispatcher("reservationConfirm.jsp").forward(req, res);
            return;
        }
	    
	    // DTOを使って予約情報を更新
        K_DTO kdto = new K_DTO();
        //DAOの実行
        boolean isUpdated = kdto.updateReservation(reservation);
		//処理実行の件数return
        if (isUpdated) {
        	req.setAttribute("successMsg", "予約内容を変更しました。");
        	req.setAttribute("mode", "editComplete");
        } else {
        	req.setAttribute("errorMsg","予約の変更に失敗しました。");
        	req.setAttribute("mode", "editComplete");
        }
        
        //予約確認画面reservationConfirm.jspへ転送
        req.getRequestDispatcher("reservationConfirm.jsp").forward(req, res);
		
	}

	
	//doGetメソッド
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			doPost(req, res);
	}

}
