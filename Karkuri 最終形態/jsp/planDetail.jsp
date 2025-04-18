<!-- 戸高　planDetail.jsp空きプラン詳細表示-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Map" %>

<html lang="ja">

<head>
	<meta charset="UTF-8">
	<title>プラン詳細</title>
</head>
	<h2>プラン詳細</h2>
	
	<%
        Map<String, Object> planDetail = (Map<String, Object>) request.getAttribute("planDetail");
        if (planDetail != null) {
    %>
    
    	<p><strong>プラン名:</strong> <%= planDetail.get("plan_name") %></p>
        <p><strong>サブプラン名:</strong> <%= planDetail.get("subplan") %></p>
        <p><strong>金額:</strong> <%= planDetail.get("cost") %> 円</p>
        <p><strong>所要時間:</strong> <%= planDetail.get("plan_required") %></p>
    
    	<form action="<%= request.getContextPath() %>/ReservationSearchServlet" method="post">
            <input type="hidden" name="backToSearch" value="true">
            <input type="hidden" name="date" value="<%= session.getAttribute("date") %>">
            <input type="hidden" name="time" value="<%= session.getAttribute("time") %>">
            <input type="hidden" name="people" value="<%= session.getAttribute("people") %>">
            <button type="submit">前のページへ戻る</button>
        </form>
        
        <form action="<%= request.getContextPath() %>/jsp/reservationForm.jsp" method="post">
            <input type="hidden" name="planId" value="<%= request.getParameter("planId") %>">
            <button type="submit">予約に進む</button>
        </form>
     <%
         } else {
     %>
        <p>プラン情報が見つかりません。</p>
     <%
         } 
     %>
        
        
<body>

</body>
</html>