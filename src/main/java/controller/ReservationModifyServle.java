//戸高　⑤（予約変更・取消）予約番号を受け取り、既存の予約情報を取得してJSPに渡す。
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
import entity.K_DTO;

@WebServlet("/ReservationModifyServlet")
public class ReservationModifyServle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		      throws IOException, ServletException {
			
		req.setCharacterEncoding("UTF-8");
		
		//表示、変更、取消のモードを取得
		String mode = req.getParameter("mode");
		// 入力された予約番号
        String reserve_num = req.getParameter("reserve_num");
		
        //予約番号入力されてない場合のエラーメッセージ
        if (reserve_num == null || reserve_num.trim().isEmpty()) {
            req.setAttribute("erMsg", "予約番号を入力してください。");
            RequestDispatcher rd = req.getRequestDispatcher("/KarakuriTop.jsp");
            rd.forward(req, res);
            return;
        }
        
        //既存の予約番号の取得
        K_DTO dto = new K_DTO();
        K_Bean bean = dto.getReservationByNumber(reserve_num);
			
		if (bean != null) {
			// セッションにセット（変更・取消共通で使える）
            HttpSession session = req.getSession();
            session.setAttribute("reservation", bean);
		
            // reservationConfirm.jspに遷移（予約内容確認表示）
            req.setAttribute("mode", mode); // 表示モード: 確認・変更・取消で出し分け
            RequestDispatcher rd = req.getRequestDispatcher("/reservationConfirm.jsp");
            rd.forward(req, res);
		} else {
			//予約番号取得エラー時　TOP画面jspでエラー表示
			req.setAttribute("erMsg", "指定された予約番号の情報が見つかりませんでした。");
	        RequestDispatcher rd = req.getRequestDispatcher("/KarakuriTop.jsp");
	        rd.forward(req, res);
		}
			
			
			
			
	}
    //doGetメソッド
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			doPost(req, res);
	}


}
