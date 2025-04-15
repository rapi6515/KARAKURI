<!-- 戸高　searchResults.jsp 空きプランの表示-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Map" %>
<!--reserve_day を yyyy-MM-dd 形式の文字列に変換するSimpleDateFormat  -->
<%@ page import="java.text.SimpleDateFormat, java.sql.Date, java.sql.Time" %>
<html lang="ja">

<head>
	<meta charset="UTF-8">
	<title>検索結果</title>
	
	<!-- css読み取り -->
	<link rel="stylesheet" href="styles.css">
</head>
<body>
	<h2>空きプラン検索結果</h2>
	<table>
		<tr><!--←テーブルの行 -->
			<th>プラン名</th><!--←テーブルの見出し-->
			<th>予約日付</th>
			<th>予約時間</th>
			<th>残り枠</th>
		</tr>	
		<%
			List<Map<String, Object>> results = (List<Map<String, Object>>) request.getAttribute("availablePlans");
			if(results != null && !results.isEmpty()){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日付フォーマット
		        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); // 時間フォーマット
				for(Map<String, Object> row : results){
		%>	
		
		<tr>
		    <td><%= row.get("plan_name") != null ? row.get("plan_name") : "N/A" %></td>
		    <td><%= row.get("reserve_day") != null ? dateFormat.format((Date) row.get("reserve_day")) : "N/A" %></td>
		    <td><%= row.get("reserve_start_time") != null ? timeFormat.format((Time) row.get("reserve_start_time")) : "N/A"  %> 
		            ～ 
		        <%= row.get("reserve_end_time") != null ? timeFormat.format((Time) row.get("reserve_end_time")) : "N/A" %></td>
		    <td>
		    	<%= row.get("available_count") != null ? (Integer) row.get("available_count") : 0 %>
		    	<!-- プラン詳細ボタン -->
		    	<form action="PlanDetailServlet" method="post">
		    		<input type="hidden" name="plan_id" value="<%= row.get("plan_id") != null ? row.get("plan_id") : "" %>">
		    		<button type="submit">プランの詳細へ</button>
		    	</form>    
		    </td>
		</tr>
		<%
				}
			} else {
		%>
		<!-- テーブルの4つの列を結合してテキストをテーブルのセルに表示 -->
		<!-- 一般的に、テーブルで表示するデータが存在しない場合使用する（検索結果０件、データが存在しない場合、一致するデータが見つからなかった場合など） -->
		<tr>
			<td colspan="4">該当するプランはありません。</td>
		</tr>
		<% } %>
	</table>
	<br>
	<a href="KarakuriTop.jsp">TOPへ戻る</a>

</body>

</html>