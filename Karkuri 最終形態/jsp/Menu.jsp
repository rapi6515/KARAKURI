<%@ page language="java"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ page import="Commom.T_Bean" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
	    <meta charset="UTF-8">
	    <title>メニュー画面</title>
	    <!-- Bootstrap 5 CDN -->
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	    <!-- Bootstrap Icons -->
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
	    <!-- CSS -->
		<link href="<%= request.getContextPath() %>/css/MenuHeader.css" rel="stylesheet">
	</head>

	<body class="menu-page">
		<!--セッションObjectからlogin情報取得-->
		<% T_Bean tBean = (T_Bean) session.getAttribute("tBean"); %>
		
		<!--セッションObjectからmenu取得-->
		<% List<Map<String, Object>> menuList = (List<Map<String, Object>>) session.getAttribute("menuList");%>
		
		<!-- ヘッダー -->
    	<nav class="navbar navbar-light d-flex justify-content-between p-3 fixed-top">
        <!-- オフキャンバス起動ボタン -->
        <button class="btn btn-outline-light" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasMenu" aria-label="Close">
            <i class="bi bi-list me-2"></i>メニュー
        </button>

        <span class="navbar-brand">Karakuri System</span>
        <!-- ログインユーザー表示 -->
        <div class="text-white dropdown">
        	<button class="btn btn-link text-white dropdown-toggle" type="button" id="userDropdownButton" data-bs-toggle="dropdown" aria-label="ユーザー情報メニューを開く" aria-expanded="false" style="text-decoration: none;">
		        <!-- ユーザーアイコン -->
			    <%
			        String avatarPath = (tBean != null && tBean.gettAvatar() != null && !tBean.gettAvatar().isEmpty()) 
			                            ? tBean.gettAvatar() 
			                            : null;
			    %>
			
			    <% if (avatarPath != null) { %>
			        <!-- 画像がある場合は表示 -->
			        <img src="<%= avatarPath %>" class="me-2 user-avatar" alt="User Image">
			    <% } else { %>
			        <!-- 画像がない場合は Bootstrap アイコンを表示 -->
			        <i class="bi bi-person-circle fs-4 me-2"></i>
			    <% } %>
			    
			    <!-- こんにちはメッセージ -->
			    <span class="greeting-two-line-small">
			        こんにちは！<br><strong>
			        <span class="user-name-dropdown">
				        <%= 
				                // tBeanがnullでない場合、氏名を表示
				                tBean != null ? tBean.gettName() : ""
				        %>
				        さん<i class="bi bi-caret-down-fill"></i></strong>
				    </span>
			    </span>
		    </button>
		    <!-- ドロップダウン 中身-->
		    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdownButton">
		    	<!-- ユーザー名 -->
                <li>
                	<div class="dropdown-info-box">
			            <i class="bi bi-person me-2"></i>
			            <strong><%= tBean != null ? tBean.gettName() : "" %></strong>
			        </div>
                <!--  権限の表示 -->
                <li><hr class="dropdown-divider"></li>
                <li>
			        <div class="dropdown-info-box">
			            <span>権限 :</span>
			            <span class="badge bg-info text-dark ms-2 dropdown-role-badge">
			                <%
			                    String authorityText = "参照";
			                    if (tBean != null && tBean.gettRank_id() != null) {
			                        authorityText = "0".equals(tBean.gettRank_id()) ? "管理" : "参照";
			                    }
			                %>
			                <%= authorityText %>
			            </span>
			        </div>
			    </li>
				
				<li><hr class="dropdown-divider"></li>
				
				<!-- ログアウトボタン -->
                <li>
                	<div class="dropdown-info-btn">
			    		<form id="logout-form" action="<%= request.getContextPath() %>/LogServlet" method="post">
	                		<button class="dropdown-item" type="submit" name="action" value="logout">
	                        	<i class="bi bi-box-arrow-right me-2"></i>ログアウト
	                    	</button>
	           			</form>
           			</div>
           		</li>
            </ul>
		</div>
    </nav>

    <!-- オフキャンバス メニュー -->
    <div class="offcanvas offcanvas-start " tabindex="-1" id="offcanvasMenu">
        <div class="offcanvas-header">
            
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas"></button>
        </div>
        	
			<!-- メニュー項目取得とアイコン表示 -->
	        <% if (menuList != null && !menuList.isEmpty()) { %>
		    <div class="offcanvas-body">
		    	<div class="list-group">
		    		<form method="post" action= "<%= request.getContextPath() %>/MenuServlet">
				        <button class="list-group-item list-group-item-action menu-btn ps-4" type="submit" name="action" value="home">
				            <i class="bi bi-house-door ms-2 me-2"></i>ホーム
				        </button>
				    </form>
			        <%
			        for (Map<String, Object> menu : menuList) {
			            int seq = ((java.math.BigDecimal) menu.get("seq")).intValue(); // seqの値を取得
			            String iconClass = "bi bi-chevron-right"; // デフォルト
		                switch (seq) {
		                    case 1:
		                        iconClass = "bi bi-file-earmark-text"; // プラン
		                        break;
		                    case 2:
		                        iconClass = "bi bi-calendar-check"; // 予約一覧
		                        break;
		                    case 3:
		                        iconClass = "bi bi-cart"; // ECサイト
		                        break;
		                    case 4:
		                        iconClass = "bi bi-people"; // スタッフ
		                        break;
		                    default:
		                        iconClass = "bi bi-chevron-right";
		                        break;
		                        
		                }
			        %>
			            <form method="post" action="<%= request.getContextPath() + "/" + menu.get("comment") %>">
			                <button class="list-group-item list-group-item-action menu-btn ps-4" type="submit"
	                            name="menuRemarks" value="<%= menu.get("comment") %>">
	                             <i class="<%= iconClass %> ms-2 me-2"></i><%= menu.get("textvalue") %>
	                        </button>
			            </form>
			        <%
			        }
			        %>
			        <% }else { %>
				    <p>メニューがありません。</p>
				    <% } %>
			     </div>
		    </div>
			
        
    </div>

    
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>