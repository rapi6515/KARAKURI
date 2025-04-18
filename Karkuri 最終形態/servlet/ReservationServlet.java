package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Commom.ReservationDesain;
import entity.K_DTO;
import entity.Reservation_DTO;

@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReservationDesain reservationService = new ReservationDesain();

	// --- POSTリクエスト（フォーム送信時）の処理 ---
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字コード設定
		request.setCharacterEncoding("UTF-8");
		// どのボタンが押されたかを取得
		String action = request.getParameter("action");
		int result = 0; // DB操作の結果格納用
		String message = ""; // ユーザーへのメッセージ格納用

		// 更新・削除で使う可能性のあるIDを取得 (例)
		// int id = parseIntSafe(request.getParameter("reserve_num")); 

		// どのボタンが押されたかで処理を分岐
		if ("registration".equals(action)) {
			// フォームから入力された値を取得
			String date = request.getParameter("date");
			String time = request.getParameter("time"); // name="time" から取得
			String plan = request.getParameter("plan"); // name="plan" から取得 (plan_id)
			String comment = request.getParameter("comment");

			// 入力チェック
			if (date == null || date.isEmpty() ||
					time == null || time.isEmpty() || time.equals("") ||
					plan == null || plan.isEmpty() || plan.equals("")) {
				message = "日付、時間、プランは必須入力です。";
				result = -1; // エラーを示す
			} else {
				// データ登録処理
				K_DTO reservation = new K_DTO(0, date, time, plan, comment);
				result = reservationService.createReservation(reservation);
				// 実行結果に応じてメッセージを設定
				if (result > 0) {
					message = "予約を登録しました。";
				} else {
					message = "予約の登録に失敗しました。データベースエラーの可能性があります。";
				}
			}
		} else if ("update".equals(action)) {

			// フォームから更新対象のIDと新しい値を取得
			int id = parseIntSafe(request.getParameter("reserve_num")); // 隠しフィールドからID取得
			String date = request.getParameter("date");
			String time = request.getParameter("time");
			String plan = request.getParameter("plan");
			String comment = request.getParameter("comment");


			// IDが有効で、必須項目が入力されているかチェック
			if (id <= 0) {
				message = "更新対象の予約が特定できません。";
				result = -1;
			} else if (date == null || date.isEmpty() ||
					time == null || time.isEmpty() || time.equals("") ||
					plan == null || plan.isEmpty() || plan.equals("")) {
				message = "日付、時間、プランは必須入力です。";
				result = -1;
			} else {
				// 更新用DTOを作成 (IDも含む)
				K_DTO reservation = new K_DTO(id, date, time, plan, comment);

				// 更新処理を実行
				result = reservationService.updateReservation(reservation, id);

				// 実行結果に応じてメッセージを設定 (result > 0 だが、0件更新の場合もあるので注意)
				if (result > 0) { // 1件以上更新された場合
					message = "予約情報を更新しました。";
				} else if (result == 0) { // 更新対象が見つからなかった場合 or 変更なし
					message = "予約情報は更新されませんでした。(対象が見つからないか、変更がない可能性があります)";
				} else { // エラーが発生した場合
					message = "予約情報の更新に失敗しました。";
				}
			}
		} else if ("delete".equals(action)) {

			// フォームの隠しフィールドから削除対象のIDを取得
			int id = parseIntSafe(request.getParameter("reserve_num"));

			// IDが有効かチェック
			if (id > 0) {
				// 削除処理を実行
				result = reservationService.deleteReservation(id);

				// 実行結果に応じてメッセージを設定
				if (result > 0) { // 1件以上削除された場合
					message = "予約情報を削除しました。";
				} else if (result == 0) { // 削除対象が見つからなかった場合
					message = "削除対象の予約が見つかりませんでした。";
				} else { // エラーが発生した場合
					message = "予約情報の削除に失敗しました。";
				}
			} else {
				// IDが無効だった場合
				message = "削除対象の予約が特定できません。";
				result = -1; // エラーを示す
			}
		} else if ("search".equals(action)) {

			// フォームの <select name="plan"> から選択されたプランIDを取得
			String searchPlanId = request.getParameter("plan");

			// プランIDが選択されているかチェック
			if (searchPlanId != null && !searchPlanId.isEmpty()) {
				// ★ 検索条件 (プランID) をセッションに保存 ★
				//    (リダイレクトした後も doGet で使えるようにするため)
				request.getSession().setAttribute("searchPlanId", searchPlanId);
			} else {
				// ★ プランが選択されていない場合は、検索条件を解除 (セッションから削除) ★
				request.getSession().removeAttribute("searchPlanId");			
			}
			// 検索操作自体はDBエラーなど起きないので、便宜上 result=1 とする
			result = 1;
		}
		// ... (以降の else や、最後のメッセージ設定、リダイレクト処理は変更なし) ...

		// --- 処理結果メッセージとリダイレクト ---
		request.getSession().setAttribute("message", message);
		response.sendRedirect(request.getContextPath() + "/reservation"); // 変更なし
	} // doPostメソッドの終わり

	// --- GETリクエスト（ページ表示時、リダイレクト時）の処理 ---
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ★★★ セッションから検索条件(プランID)を取得 ★★★
		String searchPlanId = (String) request.getSession().getAttribute("searchPlanId");
		List<K_DTO> reservations; // JSPに渡す予約リストを入れる変数

		// ★★★ 検索条件があるかどうかで処理を分岐 ★★★
		if (searchPlanId != null && !searchPlanId.isEmpty()) {
			// Desainの検索メソッドを呼び出す
			reservations = reservationService.searchReservationsByPlan(searchPlanId);
			// (任意) JSPで「検索中」などを表示するために、検索条件をリクエストにもセット
			request.setAttribute("currentSearchPlanId", searchPlanId);
		} else {
			// Desainの全件取得メソッドを呼び出す
			reservations = reservationService.getReservations();
		}

		// 1. 取得/検索した予約リストをリクエストにセット
		//    (JSPの予約履歴や、JavaScriptへのデータ渡しで使う)
		request.setAttribute("reservations", reservations);

		// 2. プランリストを取得してリクエストにセット (これは常に必要)
		List<Reservation_DTO> availablePlans = reservationService.getAvailablePlans();
		request.setAttribute("availablePlans", availablePlans);

		// 3. JavaScriptへのデータ渡し部分はJSP側で <% ... %> を使って行うのでここでは何もしない

		// 4. セッションメッセージ処理 (変更なし)
		String message = (String) request.getSession().getAttribute("message");
		if (message != null) {
			request.setAttribute("message", message);
			request.getSession().removeAttribute("message");
		}

		// 5. JSPへフォワード (変更なし)
		request.getRequestDispatcher("/jsp/Reservelist.jsp").forward(request, response);
	} // doGetメソッドの終わり

	// --- 文字列を安全に整数に変換する補助メソッド ---
	private int parseIntSafe(String value) {
		try {
			// null や空文字列、前後の空白を考慮
			if (value == null || value.trim().isEmpty()) {
				return -1;
			}
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			// 必要であればエラーログを残す
			// System.err.println("parseIntSafe: 数値への変換に失敗しました。入力値=" + value); 
			return -1;
		}
	} 

} 