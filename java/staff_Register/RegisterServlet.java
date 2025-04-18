package staff_Register;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Register") //アノテーション
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Post処理メソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//Designクラスオブジェクト生成
		RegisterDesign dsn = new RegisterDesign(request);

		//セッションオブジェクトの生成
		HttpSession session = request.getSession(true);

		//セッション初期化
		dsn.sessionList(session);

		//入力チェック
		boolean check = dsn.check();

		//trueなら処理実行
		int count = 0;
		String msg = "";//メッセージを追加

		if (check) {
			count = dsn.process();
			if (count > 0) {
				String action = dsn.getActionType();
				if (action.equals("insert")) {
					msg = "登録が完了しました";
				} else if (action.equals("update")) {
					msg = "更新が完了しました。";
				} else if (action.equals("delete")) {
					msg = "削除が完了しました。";

				}
				session.setAttribute("msg", msg);
			}
		}
		request.setAttribute("allList", dsn.getAllList());
		request.setAttribute("bean", dsn.getBean());
		//JSPにフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/Register.jsp");
		rd.forward(request, response);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
}