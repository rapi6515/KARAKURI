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
import entity.K_DTO;

@WebServlet("/PlanRegisterServlet")
public class K_PlanRegisterServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		// リクエストの文字コード指定
		req.setCharacterEncoding("utf-8");

		//リクエストオブジェクト生成
		K_PlanDesign kpd = new K_PlanDesign(req);
		K_DTO kdto = new K_DTO();
		List<Map<String, Object>> tabledata = new ArrayList<>();

		String action = req.getParameter("actionbtn");
		RequestDispatcher rd = null;

		//空bean
		K_Bean kbean = kpd.getKbean();

		//入力チェック呼び出し
		kpd.reqCheck();

		//入力チェック後のエラーリスト
		ArrayList<String> errList = kpd.getErrList();
		req.setAttribute("errList", errList);

		//入力にエラーがあった場合、フォームに戻す
		if (!errList.isEmpty()) {
			//フォームに値を再表示
			req.setAttribute("bean", kpd.getKbean());
			req.getRequestDispatcher("/jsp/PlanRegister.jsp").forward(req, res);
			return;
		}

		//登録ボタンが押された場合の処理
		if ("insert".equals(action)) {
			int result = kdto.planinsert(kbean);
			if (result > 0) {
				// 登録成功後の処理
				req.setAttribute("message", "登録が完了しました。");
				//登録後にプラン一覧へ戻る
				tabledata = kpd.planDataList("");
				//データ取得
				req.setAttribute("tabledata", tabledata);
				//一覧ページ
				rd = req.getRequestDispatcher("/jsp/PlanList.jsp");
			} else {
				//登録失敗後の処理
				req.setAttribute("error", "登録に失敗しました。");
				//Beanをリクエストに設定
				req.setAttribute("bean", kbean);
				//フォームに値を再表示
				rd = req.getRequestDispatcher("/jsp/PlanRegister.jsp");
			}
			
		//更新ボタンが押された場合の処理
		} else if ("update".equals(action)) {

			int result = kdto.planupdate(kbean);
			if (result > 0) {
				//更新成功後の処理
				req.setAttribute("message", "更新が完了しました。");
				//更新後にプラン一覧へ戻る
				tabledata = kpd.planDataList("");
				//データ取得
				req.setAttribute("tabledata", tabledata);
				//一覧ページ
				rd = req.getRequestDispatcher("/jsp/PlanList.jsp");
			} else {
				//更新失敗後の処理
				req.setAttribute("error", "更新に失敗しました。");
				//Beanをリクエストに設定
				req.setAttribute("bean", kbean);
				//プラン登録ページに戻る
				rd = req.getRequestDispatcher("/jsp/PlanRegister.jsp");
			}
			
			//削除ボタンが押された場合の処理
		} else if ("delete".equals(action)) {
		    // 削除対象のプランIDを取得
		    String planIdToDelete = req.getParameter("plan_id");

		    if (planIdToDelete != null && !planIdToDelete.isEmpty()) {
		        int result = kdto.plandelete(planIdToDelete);

		        if (result > 0) {
		            //削除後
		            req.setAttribute("message", "プランID: " + planIdToDelete + " を削除しました。");
		            tabledata = kpd.planDataList("");
		            //データ取得
		            req.setAttribute("tabledata", tabledata);
		            //削除後にプラン一覧へ戻る
		            rd = req.getRequestDispatcher("/jsp/PlanList.jsp");
		        }
		    } else {
		        //削除失敗後の処理
		        req.setAttribute("error", "プランID: " + planIdToDelete + " の削除に失敗しました。");
		        //Beanをリクエストに設定
		        req.setAttribute("bean", kbean);
		        //登録ページ
		        rd = req.getRequestDispatcher("/jsp/PlanRegister.jsp");
		    }
		    
		//戻るボタンの処理
		} else if ("back".equals(action)) {
		    tabledata = kpd.planDataList("");
		    //データ取得
		    req.setAttribute("tabledata", tabledata);
		  //プラン一覧へ戻る
		    rd = req.getRequestDispatcher("/jsp/PlanList.jsp");
		} else {
		    req.setAttribute("error", "不正な操作です。");
		}
		// JSPにフォワード
	    rd.forward(req, res);
}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doPost(req, res);
	}
}
