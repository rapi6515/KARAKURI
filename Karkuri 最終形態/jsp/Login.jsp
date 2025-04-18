<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="Karakuri.*"%>

<!--requestObjectからスタッフidエラーメッセージ取得-->
<%
    ArrayList<String> staffIdErrorMsg = (ArrayList<String>) request.getAttribute("staffIdErrorMsg");
%>

<!--requestObjectからパスワード入力エラーメッセージ取得-->
<%
    ArrayList<String> passErrorMsg = (ArrayList<String>) request.getAttribute("passErrorMsg");
%>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
		<link href="<%= request.getContextPath() %>/css/Login.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
		<title>ログインページ</title>
	</head>
	<body class="login-page">
		<div class="container">
			<form action="<%= request.getContextPath() %>/LogServlet" method="post">
				<%
				    Boolean timeout = (Boolean) session.getAttribute("timeout");
				    if (timeout != null && timeout) {
				%>
				     <div class="error-container">
				        <div class="error-header">
					        <span class="error-icon">
					            <i class="fa fa-exclamation-circle"></i>
					        </span>
					        <span class="error-title">エラー</span>
					        <span class="timeout-message">セッションがタイムアウトしました。</span>
					    </div>
					    <div class="error-body">
					        <p>再度ログインしてください。</p>
					    </div>
				    </div>
				<%
				        session.removeAttribute("timeout"); // 一度表示したら削除
				    }
				%>
			 
			
				<div class="login-box">
				    <h2>LOGIN</h2>
					<div class="mb-3 text-start">
					    <label for="staff_id" class="form-label">Staff ID</label>
					    <input type="text" class="form-control <%= (staffIdErrorMsg != null && !staffIdErrorMsg.isEmpty()) ? "error-input" : "" %>" id="staff_id" placeholder="スタッフIDを入力" name="staff_id" style="height: 50px;">
					    <%
					            // スタッフidエラーメッセージリストを取得
					            if (staffIdErrorMsg != null && !staffIdErrorMsg.isEmpty()) {
					                // エラーメッセージがある場合
					                for (String msg : staffIdErrorMsg) {
					        %><div class="error-message"><i class="fa fa-exclamation-triangle"></i><%= msg %></div>
					        <%
					                }
					            }%>
					 </div>
					
					 <div class="mb-3 text-start">
					    <label for="password" class="form-label">Password</label>
					    <input type="password" class="form-control <%= (passErrorMsg != null && !passErrorMsg.isEmpty()) ? "error-input" : "" %>" id="password" placeholder="パスワードを入力" name="password" style="height: 50px;">
					    <%
					            // パスワードエラーメッセージリストを取得
					            if (passErrorMsg != null && !passErrorMsg.isEmpty()) {
					                // エラーメッセージがある場合
					                for (String msg : passErrorMsg) {
					        %><div class="error-message"><i class="fa fa-exclamation-triangle"></i><%= msg %></div>
					        <%
					                }
					            }%>
					 </div>
					
					  <button type="submit" class="btn btn-primary w-100" style="width: 120%; height: 50px;">ログイン</button>
				</div>
					
		
		
	
			</form>
		</div>
	</body>
	
	
</html>