//戸高　⑤BackToTopServletセッションリセットサーブレット
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BackToTopServlet")
public class BackToTopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    // セッションが あれば取得、なければ null を返すセッションが存在する場合だけ何かしたい時に使います。
		HttpSession session = req.getSession(false);
		
		if (session != null) {
	        session.invalidate(); // セッション全削除
	    }
		res.sendRedirect("KarakuriTop.jsp"); // リダイレクト
	}


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doPost(req, res);
	}

}
