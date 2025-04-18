<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<%@ page import="entity.K_DTO"%>
<%@ page import="entity.Reservation_DTO"%>

<%
// `reservations` を一度だけ定義
List<K_DTO> reservations = (List<K_DTO>) request.getAttribute("reservations");
if (reservations == null) {
	reservations = new ArrayList<>();
}

// JSON 文字列を構築
StringBuilder jsonBuilder = new StringBuilder("[");
boolean first = true;
for (K_DTO res : reservations) {
	if (!first) {
		jsonBuilder.append(",");
	}
	first = false;
	jsonBuilder.append("{").append("\"reserve_day\":\"").append(res.getReserve_day()).append("\",")
	.append("\"plan_id\":\"").append(res.getPlan_id()).append("\",").append("\"reserve_comment\":\"")
	.append(res.getReserve_comment()).append("\"").append("}");
}
jsonBuilder.append("]");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約一覧</title>
<link
	href='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css'
	rel='stylesheet' />
<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js'></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/reservationstyle.css">
<script src="<%=request.getContextPath()%>/js/reservationscript.js"
	defer></script>
</head>
<body>
	<%@include file = "/jsp/Menu.jsp"%>
	<div class="content-wrapper">
	<div class="container"> 
		<!-- カレンダー -->
		<div id="calendar"></div>

		<!-- 予約表 -->
		<div class="schedule-container">
			<table class="schedule-table">
				<thead>
					<tr>
						<th>時間</th>
						<th>観着体</th>
						<th>観着体撮</th>
						<th>花魁体撮</th>
						<th>テスト</th>
					</tr>
				</thead>
				<tbody>
					<%
					// 1時間ごとのループに変更 (9:00 から 18:00 まで)
					for (int hour = 9; hour <= 18; hour++) {
						// 時間文字列を "HH:00" 形式で生成
						String time = String.format("%02d:00", hour); // 例: 9 -> "09:00", 10 -> "10:00"
					%>
					<tr>
						<td><%=time%></td>
						<%-- data-time 属性も "HH:00" 形式にする --%>
						<td data-time="<%=time%>"></td> <%-- プランA列 (インデックス1) --%>
						<td data-time="<%=time%>"></td> <%-- プランB列 (インデックス2) --%>
						<td data-time="<%=time%>"></td> <%-- プランC列 (インデックス3) --%>
						<td data-time="<%=time%>"></td> <%-- ★追加: テスト列 (インデックス4) ★--%>
					</tr>
					<%
					}
					%>
					<%-- 最後の18:00の行はループ内で生成されるので不要 --%>
				</tbody>
			</table>
		</div>

		<!-- 詳細エリアと予約履歴 -->
		<div class="detail-wrapper">
			<div id="detail-container">
				<h3>予約詳細</h3>
				<div id="detail">時間をクリックするとここに詳細が表示されます</div>
			</div>

			<div class="history-container">
				<h3>予約履歴</h3>
				<div id="history-list" class="scrollable">
					<%
					if (!reservations.isEmpty()) {
					%>
					<%
					for (K_DTO res : reservations) {
					%>
					<div class="history-item">
						<strong><%=res.getReserve_day()%></strong> - プランID:
						<%=res.getPlan_id()%>
						(予約日:
						<%=res.getReserve_comment()%>)
					</div>
					<%
					}
					%>
					<%
					} else {
					%>
					<div class="history-item">予約履歴はありません。</div>
					<%
					}
					%>
				</div>
			</div>
		</div>
	</div>

	<!-- 予約入力フォーム -->
	<form action="<%=request.getContextPath()%>/reservation" method="post"
		class="reservation-form">

		<input type="hidden" id="reserve_num" name="reserve_num" value="">

		<div class="form-row-horizontal">
			<div class="form-group">
				<label for="date">日付</label> <input type="date" id="date"
					name="date">
			</div>
			<div class="form-group">
				<label for="plan">プラン</label> <select id="plan" name="plan">
					<option value="">選択してください</option>
					<%-- 「選択してください」は残す --%>
					<%
					// Servletから渡されたプランリストを取得 (Object型なのでキャストが必要)
					List<Reservation_DTO> planList = (List<Reservation_DTO>) request.getAttribute("availablePlans");

					// リストが存在する場合のみループ処理を行う
					if (planList != null) {
						// 拡張for文を使ってリストの中の Plan_DTO を一つずつ取り出す
						for (Reservation_DTO planItem : planList) {
					%>
					<%-- ループの中で <option> タグを生成 --%>
					<%-- value属性にはプランID (planItem.getPlanId()) を設定 --%>
					<%-- 表示されるテキストにはプラン名 (planItem.getPlanName()) を設定 --%>
					<option value="<%=planItem.getPlanId()%>"><%=planItem.getPlanName()%></option>
					<%
					} // forループの終わり
					} // if (planList != null) の終わり
					%>
				</select>
			</div>
			<div class="form-group">
				<label for="time">時間</label> <input type="time" id="time"
					name="time" min="09:00" max="18:00" step="3600">
			</div>
		</div>

		<!-- コメント欄（幅いっぱいに） -->
		<div class="form-comment">
			<label for="comment">コメント</label>
			<textarea id="comment" name="comment"></textarea>
		</div>

		<!-- ボタン -->
		<div class="form-buttons">
			<button type="submit" name="action" value="search">検索</button>
			<button type="submit" name="action" value="registration">登録</button>
			<button type="submit" name="action" value="update">更新</button>
			<button type="submit" name="action" value="delete">削除</button>
		</div>
	</form>
 </div>

	<!-- ✅ JavaScript に予約データを渡す -->
	<script>
	  window.reservations = [ <%-- JavaScript配列の開始ブラケット --%>
	    <%// Servletから渡された予約リスト(List<K_DTO>)を取得
List<K_DTO> reservationListForJs = (List<K_DTO>) request.getAttribute("reservations");
boolean isFirstItem = true; // 最初の要素かどうかのフラグ

// リストがnullでなく、空でもない場合に処理
if (reservationListForJs != null && !reservationListForJs.isEmpty()) {
	// リストをループ
	for (K_DTO res : reservationListForJs) {
		// 2つ目以降の要素の場合、前にカンマを出力
		if (!isFirstItem) {
			out.print(",");
		}
		isFirstItem = false; // フラグを更新

		// JavaScriptオブジェクトリテラルの文字列を出力
		out.print("{"); // { 開始
		// "キー": 値 の形式で出力。数値はそのまま、文字列は""で囲む
		out.print("\"id\": " + res.getId() + ",");
		// 文字列はエスケープが必要な場合があるため、簡単な関数を使う
		out.print("\"reserve_day\": \"" + escapeJs(res.getReserve_day()) + "\",");
		out.print("\"reserve_time\": \"" + escapeJs(res.getReserve_time()) + "\",");
		out.print("\"plan_id\": \"" + escapeJs(res.getPlan_id()) + "\","); // K_DTOのplan_idはString型
		out.print("\"reserve_comment\": \"" + escapeJs(res.getReserve_comment()) + "\"");
		out.print("}"); // } 終了
	} // forループ終わり
} // if終わり%>
	  ]; <%-- JavaScript配列の終了ブラケット --%>

	</script>
	<%!// JavaScript文字列リテラル用に簡易エスケープするメソッド
	private String escapeJs(String s) {
		if (s == null) {
			return "";
		}
		// 最低限: バックスラッシュ、ダブルクォート、改行をエスケープ
		return s.replace("\\", "\\\\")
				.replace("\"", "\\\"")
				.replace("\r", "\\r")
				.replace("\n", "\\n");
	}%>
</body>
</html>