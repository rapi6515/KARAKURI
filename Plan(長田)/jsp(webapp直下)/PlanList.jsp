<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<jsp:useBean id="bean" scope="request" class="common.K_Bean" />

<!DOCTYPE html>
<html lang="ja">

<%
List<Map<String, Object>> plantabledata = (List<Map<String, Object>>) request.getAttribute("tabledata");
%>

<head>
<!-- 必須のメタタグ -->
<meta charset="UTF-8">
<title>プラン一覧</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
	crossorigin="anonymous">
</head>
<!-- 背景色 -->
<div class="p-3 mb-2 bg-secondary-subtle text-emphasis-secondary">

	<body>
		<form action="K_PlanServlet" method="post">
			<div class="container w-80" style="max-width: 80%;">



				<h3>プラン一覧</h3>

				<!-- プランID -->
				<div class="col-md-3 mb-2">
					<label for="plan_id" class="form-label">プランID</label> <input
						type="text" class="form-control" id="plan_id" name="plan_id">
				</div>


				<!-- ボタン -->
				<div class="row d-flex justify-content-end mb-2">
					<div class="col-auto">
						<button type="submit" name="btn" value="search"
							class="btn btn-dark">検索</button>
					</div>
					<div class="col-auto">
						<button type="submit" name="btn" value="register"
							class="btn btn-dark">新規登録</button>
					</div>
				</div>

				<!-- テーブル -->

				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th></th>
							<th>プランID</th>
							<th>プラン名</th>
							<th>開催期間</th>
							<th>単価</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<%
						if (plantabledata != null) {
							for (Map<String, Object> plantabledata2 : plantabledata) {
						%>
						<tr>
							<td><img src="<%=plantabledata2.get("image")%>"
								alt="プラン画像" width="100" height="100" class="img-thumbnail"></td>
							<td><%=plantabledata2.get("plan_id")%></td>
							<td><%=plantabledata2.get("plan_name")%></td>
							<td><%=plantabledata2.get("periodstart")%> - <%=plantabledata2.get("periodend")%></td>
							<td><%=plantabledata2.get("cost")%></td>
							<td>
								<form action="K_PlanServlet" method="post">
									<input type="hidden" name="t_plan_id"
										value="<%=plantabledata2.get("plan_id")%>">
									<button type="submit" name="btn" value="edit"
										class="btn btn-dark">編集</button>
								</form>
							</td>
						</tr>
						<%
						}
						}
						%>
					</tbody>
				</table>
			</div>
		</form>
		<!-- 内容ここまで -->

		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
			crossorigin="anonymous">
			
		</script>
	</body>
</div>

</html>