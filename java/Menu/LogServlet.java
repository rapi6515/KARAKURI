package Menu;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Commom.SessionManager;

/**
 * Servlet implementation class LogServlet
 */
@WebServlet("/LogServlet")
public class LogServlet extends HttpServlet {
       
    
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
				
		//リクエストパラメータの取得
        String action = req.getParameter("action");

        //ログアウト処理
        if ("logout".equals(action)) {
            // セッションを全て無効化
        	SessionManager.invalidate(req);
            // ログイン画面にリダイレクト
            res.sendRedirect(req.getContextPath() +"/jsp/Login.jsp");
            return;
        }
		
        //ログイン処理
		//リクエスタ処理オブジェクト生成、パラメータ取得
		LogRequest lrt = new LogRequest(req);
		
		
		//入力チェックメソッド処理
		boolean Status =lrt.logreqCheck();
	
		if(Status) {
		 // 存在チェックを実行
			Status = lrt.checkUserExists();

        }	
		
		//ログイン成功の場合
		if (Status) {
			//タイムアウト時間を設定（30分）
			req.getSession(true).setMaxInactiveInterval(30);
			//ユーザー情報をセッションにセット
			SessionManager.setAttribute(req, "tBean", lrt.getBean());
            //メニュー表示用サーブレットへ
            res.sendRedirect(req.getContextPath() +"/MenuServlet");
        //ログイン失敗の時
        } else {
        	//リクエスタにエラーメッセージをセット
            req.setAttribute("staffIdErrorMsg", lrt.getStaffIdErrorMsg());
            req.setAttribute("passErrorMsg", lrt.getPassErrorMsg());
            //ログイン画面へ戻す
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/Login.jsp");
            rd.forward(req, res);
        }

	}

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, res);
	}

}
