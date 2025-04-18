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
 * Servlet implementation class MenuServlet
 */
@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {   
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// セッション切れ、未ログインの場合はログイン画面へ
        if (SessionManager.timeout(req, res))return;
        	
        // リクエストパラメータ取得（ホーム or メニュー）
        String action = req.getParameter("action");

        // メニュー情報を取得してセッションにセット
        if (SessionManager.getAttribute(req, "menuList") == null) {
            MenuRequest mrt = new MenuRequest();
            SessionManager.setAttribute(req, "menuList", mrt.getMenuList());
        }

        // 表示先ページの初期値を「ホーム画面」に設定
        String nextPage = "/jsp/Home.jsp";

        // 「action=menu」の場合はメニュー画面に
        if ("menu".equals(action)) {
            nextPage = "/jsp/Menu.jsp";
        }

        // JSPにフォワード
        RequestDispatcher rd = req.getRequestDispatcher(nextPage);
        rd.forward(req, res);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }
}
	



