<!-- 戸高　予約情報入力後の確認画面 (reservationConfirm.jsp) -->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="Commom.K_Bean" %>
<html lang="ja">

<head>
	<meta charset="UTF-8">
	<title>入力内容確認</title>
	
	<!-- 自分のcss読込 -->
    <link rel="stylesheet" href="styles.css">
</head>

<body>

	<!-- エラーメッセージがある場合は表示 -->
	<%
		String mode = (String) request.getAttribute("mode");
		if (mode == null) mode = "view"; // デフォルトは確認モード
		String errorMsg = (String) request.getAttribute("errorMsg");
		String successMsg = (String) request.getAttribute("successMsg");
		K_Bean reservation = (K_Bean) session.getAttribute("reservation");
	%>
		
	<h2>
	<%= "edit".equals(mode) ? "予約内容の変更確認" :
	    "delete".equals(mode) ? "予約取消の確認" :
	    "editComplete".equals(mode) ? "変更完了" :
	    "deleteComplete".equals(mode) ? "取消完了" :
	    "予約内容確認" %>
	</h2>
	
		<%-- ✅ 成功・失敗メッセージ表示 --%>
	<% if (successMsg != null) { %>
	    <p class="success"><%= successMsg %></p>
	<% } %>
	<% if (errorMsg != null) { %>
	    <p class="error"><%= errorMsg %></p>
	<% } %>
	
    <%-- ✅ 完了モードではこれ以上出さない --%>
	<% if (!"editComplete".equals(mode) && !"deleteComplete".equals(mode) && reservation != null) { %>
	    <p>予約番号: <%= reservation.getReserve_num() %></p>
	    <p>お名前: <%= reservation.getName() %></p>
	    <p>カナ: <%= reservation.getKana() %></p>
	    <p>電話番号: <%= reservation.getTel() %></p>
	    <p>メールアドレス: <%= reservation.getEmail() %></p>
	    <p>国籍: <%= reservation.getNationality() %></p>
	    <p>ご予約のきっかけ: <%= reservation.getReason() %></p>
	    <p>備考: <%= reservation.getReserve_comment() %></p>
    
 		<%-- モードによって処理先を切り替える --%>
		<% if ("edit".equals(mode)) { %>		
			<form action="reservationForm.jsp" method="get">
			    <input type="hidden" name="mode" value="edit">
			    <button type="submit">①予約内容を修正入力画面へ</button>
			</form>
	
			<form action="ReservationUpdateServlet" method="post">
		        <button type="submit">②予約内容変更確定</button>
		    </form>
	        
        <% } else if ("delete".equals(mode)) { %> 
	        <form action="ReservationDeleteServlet" method="post" onsubmit="return confirm('本当に予約を取消しますか？');">
	            <button type="submit">予約を取消する</button>
	        </form>
        <% } else { %>  
	        
	        <form action="CompleteReservationServlet" method="post">
	            <button type="submit">予約確定</button>
	        </form>
        <% } %>
    <% } %>
    
    <!-- 取消の後の戻るはトップへ変更は入力フォームへ -->
    <% if ("deleteComplete".equals(mode)|| "editComplete".equals(mode)|| "edit".equals(mode)) { %>
	    <form action="KarakuriTop.jsp" method="get">
	        <button type="submit">TOPへ戻る</button>
	    </form>
	<% } else { %>
    <form action="reservationForm.jsp" method="post">
        <button type="submit">戻る</button>
    </form>
<% } %>
</body>
</html>