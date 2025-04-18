//戸高　③予約情報を取得しエラーチェック内容確認表示
package ecSite;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		      throws IOException, ServletException {
		
		
		//設計クラス
		ReservDesign rde = new ReservDesign();	
		//formからの入力値を取得メソッド
		rde.setReservationServletParams(req);
		//paramsをBeanに設定メソッド
		rde.beanSetParams();
		
		//入力エラーチェック実行
		String errorMsg = rde.validateForm();
		
		//エラーがあればFormに戻す
		if (!errorMsg.isEmpty()) {
			req.setAttribute("errorMsg", errorMsg);
			RequestDispatcher rd = req.getRequestDispatcher("jsp/reservationForm.jsp");
			rd.forward(req, res);
			return;  // 処理をここで終了
		}
		
		//エラーがなければセッション作成
		HttpSession session = req.getSession();
		//入力値のbeanを"reservation"という名でセッションへセット
		session.setAttribute("reservation", rde.getBean());

		//mode→既存の予約の表示変更取消のモードを管理
		String mode = req.getParameter("mode");
		req.setAttribute("mode", mode);
		
		
		// 確認画面に遷移
		RequestDispatcher rd = req.getRequestDispatcher("jsp/reservationConfirm.jsp");
		rd.forward(req, res);		
		
	}


	
	//doGetメソッド
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

}
