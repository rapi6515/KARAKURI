<!-- 戸高　reservationForm.jsp (顧客の情報入力フォーム）-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="Commom.K_Bean" %>
<html lang="ja">

<head>

<meta charset="UTF-8">
<title>予約フォーム</title>

<!-- 自分のcss読込 -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">

</head>


<body>
	<h2>予約情報入力</h2>
	
	<%
  	    // セッションから予約情報を取得
   		K_Bean reservation = (K_Bean) session.getAttribute("reservation");
	    String planId = (reservation != null) ? reservation.getPlan_id() : "";
	    String date = (reservation != null) ? reservation.getDate() : "";
	    String startTime = (reservation != null) ? reservation.getStart_time() : "";
   		// 初期値として空配列を定義（null対策）
		String[] selectedReasons = {};
   
   		if (reservation != null && reservation.getReason() != null) {
       		selectedReasons = reservation.getReason().split(",");
	 	}
   		
        // エラーメッセージ表示 -->
        
		String errorMsg = (String) request.getAttribute("errorMsg");
		
	%>
	
		<form action="<%= request.getContextPath() %>/ReservationServlet" method="post">
	    <label>お客様名</label>
		<input type="text" name="name" value="<%= reservation != null ? reservation.getName() : "" %>"><br>
		
		<label>氏名カナ</label>
		<input type="text" name="kana" value="<%= reservation != null ? reservation.getKana() : "" %>"><br>
		
		<label>ご連絡先（携帯電話番号）</label>
		<input type="tel" name="tel" placeholder="ハイフン（－）無しで入力" value="<%= reservation != null ? reservation.getTel() : "" %>"><br>
		
		<label>メールアドレス</label>
		<input type="email" name="email" value="<%= reservation != null ? reservation.getEmail() : "" %>"><br>
		
		
		<label>国籍</label>
		<input type="text" name="nationality" value="<%= reservation != null ? reservation.getNationality() : "" %>"><br>
		
		
		<p>ご予約のきっかけ（複数選択可）:</p>
		
		<label>
   			 <input type="checkbox" name="reason" value="SNS"
 				  <%= java.util.Arrays.asList(selectedReasons).contains("SNS") ? "checked" : "" %>> SNS
		</label>

		<label>
    		<input type="checkbox" name="reason" value="紹介"
       			 <%= java.util.Arrays.asList(selectedReasons).contains("紹介") ? "checked" : "" %>> 紹介
		</label>

		<label>
		    <input type="checkbox" name="reason" value="通りがかり"
		        <%= java.util.Arrays.asList(selectedReasons).contains("通りがかり") ? "checked" : "" %>> 通りがかり
		</label>
		
		<label>
		    <input type="checkbox" name="reason" value="その他"
		        <%= java.util.Arrays.asList(selectedReasons).contains("その他") ? "checked" : "" %>> その他
		</label>
		<br>
		
		<label>備考</label>
		<textarea name="reserve_comment" rows="10" placeholder="※必ず身長と体重を備考欄に入力ください。"><%= reservation != null && reservation.getReserve_comment() != null ? reservation.getReserve_comment() : "" %></textarea><br>
		
		<% if (errorMsg != null && !errorMsg.isEmpty()) { %>
		     <div class="error-message"><%= errorMsg.replaceAll("\n", "<br>") %></div>
		<% } %>
		
		
		<input type="hidden" name="plan_id" value="<%= planId %>">
		<input type="hidden" name="date" value="<%= date %>">
		<input type="hidden" name="start_time" value="<%= startTime %>">
        <input type="hidden" name="reserve_num" value="<%= reservation != null ? reservation.getReserve_num() : "" %>">
        <input type="hidden" name="mode" value="<%= request.getParameter("mode") != null ? request.getParameter("mode") : "" %>">
        <button type="submit">入力情報確認</button>
		
	</form>

</body>

</html>