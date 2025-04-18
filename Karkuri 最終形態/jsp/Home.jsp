<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ page import="Commom.T_Bean" %>
<%@page import="Karakuri.*"%>    
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8">
		<title>Karakuri System Home</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
		<link href="<%= request.getContextPath() %>/css/Home.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">	</head>	
		
		<body>
				<%@include file = "/jsp/Menu.jsp"%> 
				<!--セッションObjectからlogin情報取得-->
			<div class="content-wrapper container py-4">	
				<div class="card-custom fade-in mt-2">
					
					<h2 class="logUsername">
						<%= 
					                // tBeanがnullでない場合、氏名を表示
					                tBean != null ? tBean.gettName() : ""
					        	%> さん
		        	</h2>
		        	<div class="custom-message mt-4">
					  <i class="bi bi-emoji-smile me-2"></i>お疲れ様です。<br>ご予約の確認やプランの更新は、左上のメニューからどうぞ。
					</div>
				
					<%
					    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy年MM月dd日");
					    String today = sdf.format(new java.util.Date());
					%>
					<p class="fs-5 mt-3"><i class="bi bi-calendar me-2 mt-4"></i><%= today %></p>
					
					<!-- ▼ 天気情報 ▼ -->
					<p class="fs-5 mt-2" id="weather-info">
						<i class="bi bi-cloud-sun me-2"></i>天気情報を取得中...
					</p>
					
				</div>
				
				
				
			</div>
			<footer class="text-center text-light mt-5 small">
			  © 2025 Karakuri System | 着物レンタル管理システム
			</footer>
			
			<script src="<%= request.getContextPath() %>/js/Home.js"></script>
			
		</body>
</html>