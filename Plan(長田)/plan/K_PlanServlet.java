package plan;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.K_Bean;

@WebServlet("/K_PlanServlet")
public class K_PlanServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		// リクエストの文字コード指定
		req.setCharacterEncoding("utf-8");

		//リクエストオブジェクト生成
		K_PlanDesign kpd = new K_PlanDesign(req);

		String btn = req.getParameter("btn");
		RequestDispatcher rd;
		List<Map<String, Object>> tabledata = new ArrayList<>();

		//空bean
		K_Bean kbean = kpd.getKbean();

		//プランID取得インスタンス
		//String planId = kpd.getPlan_id();

		//検索ボタン
		if (btn != null && btn.equals("search")) {
			//データ取得
			tabledata = kpd.dataList();
			//プランIDに基いたデータをリクエストに設定
			req.setAttribute("tabledata", tabledata);
			//一覧ページ
			rd = req.getRequestDispatcher("/jsp/PlanList.jsp");

			//編集ボタン
		} else if (btn != null && btn.equals("edit")) {
			//データ取得
			kpd.dataList();
			//Beanをリクエストに設定
			req.setAttribute("bean", kbean);
			//編集ページ
			rd = req.getRequestDispatcher("/jsp/PlanRegister.jsp");

			//新規登録ボタン
		} else if (btn != null && btn.equals("register")) {
			//Beanをリクエストに設定
			req.setAttribute("bean", kbean);
			//新規登録ページ
			rd = req.getRequestDispatcher("/jsp/PlanRegister.jsp");

		} else {
			//全件テーブル表示
			tabledata = kpd.planDataList("");
			req.setAttribute("tabledata", tabledata);
			//プラン一覧ページ
			rd = req.getRequestDispatcher("/jsp/PlanList.jsp");
		}

		// JSPにフォワード
		rd.forward(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doPost(req, res);
	}
}
