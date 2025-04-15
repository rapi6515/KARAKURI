<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<jsp:useBean id="bean" scope="request" class="common.K_Bean" />

<!doctype html>
<html lang="ja">

<%
java.util.List<String> errList = (java.util.List<String>) request.getAttribute("errList");
%>

<head>
<!-- 必須のメタタグ -->
<meta charset="utf-8">
<title>プラン登録</title>
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
		<form action="PlanRegisterServlet" method="post">
			<div class="container w-80" style="max-width: 80%;">

				<!-- ここから内容 -->

				<h3>プラン登録フォーム</h3>

				<!------------------------------------------------------------------------------------>
				<div class="row align-items-center">
					<!-- 画像 -->
					<div class="col-md-5 mb-2">
						<%
						if (bean != null && bean.getImage() != null && !bean.getImage().isEmpty()) {
						%>
						<img src="<%=bean.getImage()%>" alt="プラン画像" width="300"
							height="250" class="img-thumbnail">
						<%
						} else {
						%>
						<p>画像がありません</p>
						<%
						}
						%>
					</div>

					<!-- フォームを縦方向に並べる -->
					<div class="col-md-7">
						<!-- プランID -->
						<div class="form-group mb-2">
							<label for="plan_id" class="form-label">プランID</label> <input
								type="text" class="form-control" id="plan_id" name="plan_id"
								value="<%=bean.getPlan_id() != null ? bean.getPlan_id() : ""%>">
							<%
							if (errList != null) {
								for (String error : errList) {
									if (error.contains("プランID")) {
								out.println("<div class='text-danger'>" + error + "</div>");
									}
								}
							}
							%>
						</div>

						<!-- プラン名 -->
						<div class="form-group mb-2">
							<label for="plan_name" class="form-label">プラン名</label> <input
								type="text" class="form-control" id="plan_name" name="plan_name"
								value="<%=bean.getPlan_name() != null ? bean.getPlan_name() : ""%>">

							<%
							if (errList != null) {
								for (String error : errList) {
									if (error.contains("プラン名")) {
								out.println("<div class='text-danger'>" + error + "</div>");
									}
								}
							}
							%>
						</div>

						<!-- 単価 -->
						<div class="form-group mb-2">
							<label for="cost" class="form-label">単価</label> <input
								type="number" class="form-control" id="cost" name="cost"
								value="<%=bean.getCost() != null ? bean.getCost() : ""%>">

							<%
							if (errList != null) {
								for (String error : errList) {
									if (error.contains("単価")) {
								out.println("<div class='text-danger'>" + error + "</div>");
									}
								}
							}
							%>
						</div>
					</div>
				</div>

				<!-- プラン画像 -->
				<div class="col-md-12 mb-2">
					<label for="image" class="form-label">画像パス</label> <input
						type="text" class="form-control" id="image" name="image"
						value="<%=bean.getImage() != null ? bean.getImage() : ""%>">

					<%
					if (errList != null) {
						for (String error : errList) {
							if (error.contains("画像パス")) {
						out.println("<div class='text-danger'>" + error + "</div>");
							}
						}
					}
					%>
				</div>

				<!-- 在庫 -->
				<div class="col-md-12 mb-2">
					<label for="stock" class="form-label">在庫</label> <input
						type="number" class="form-control" id="stock" name="stock"
						value="<%=bean.getStock() != null ? bean.getStock() : ""%>">

					<%
					if (errList != null) {
						for (String error : errList) {
							if (error.contains("在庫")) {
						out.println("<div class='text-danger'>" + error + "</div>");
							}
						}
					}
					%>
				</div>

				<!-- サブプラン名 -->
				<div class="col-md-12 mb-2">
					<label for="subplan" class="form-label">サブプラン</label> <input
						type="text" class="form-control" id="subplan" name="subplan"
						value="<%=bean.getSubplan() != null ? bean.getSubplan() : ""%>"
						placeholder="例：料金に含まれるもの：一式レンタル料（着物、帯、和装バッグ・・・）、着付け料、消費税">

					<%
					if (errList != null) {
						for (String error : errList) {
							if (error.contains("サブプラン")) {
						out.println("<div class='text-danger'>" + error + "</div>");
							}
						}
					}
					%>
				</div>

				<!-- プラン内容 -->
				<div class="col-md-12 mb-2">
					<label for="contents" class="form-label">プラン内容</label>
					<textarea class="form-control" cols="50" rows="20" id="contents"
						name="contents" placeholder="プランの詳細を入力してください"><%=bean.getContents() != null ? bean.getContents() : ""%></textarea>

					<%
					if (errList != null) {
						for (String error : errList) {
							if (error.contains("プラン内容")) {
						out.println("<div class='text-danger'>" + error + "</div>");
							}
						}
					}
					%>
				</div>

				<!------------------------------------------------------------------------------------>
				<div class="row">
					<!-- 正味所要 -->
					<div class="col-sm-6 mb-2">
						<label for="time_required" class="form-label">正味所要時間</label> <input
							type="text" class="form-control" id="time_required"
							name="time_required"
							value="<%=bean.getTime_required() != null ? bean.getTime_required() : ""%>"
							placeholder="00:00:00">

						<%
						if (errList != null) {
							for (String error : errList) {
								if (error.contains("正味所要時間")) {
							out.println("<div class='text-danger'>" + error + "</div>");
								}
							}
						}
						%>
					</div>

					<!-- プラン所要時間 -->
					<div class="col-sm-6 mb-2">
						<label for="plan_required" class="form-label">プラン所要時間</label> <input
							type="text" class="form-control" id="plan_required"
							name="plan_required"
							value="<%=bean.getPlan_required() != null ? bean.getPlan_required() : ""%>"
							placeholder="00:00:00">

						<%
						if (errList != null) {
							for (String error : errList) {
								if (error.contains("プラン所要時間")) {
							out.println("<div class='text-danger'>" + error + "</div>");
								}
							}
						}
						%>
					</div>
				</div>
				<!------------------------------------------------------------------------------------>

					<!-- 必要職種 -->
					<div class="col-md-4 mb-2">
						<label for="job_id1" class="form-label">職種ID(1)</label> <input
							type="number" class="form-control" id="job_id1" name="job_id1"
							value="<%=bean.getJob_id1() != null ? bean.getJob_id1() : ""%>">

						<%
						if (errList != null) {
							for (String error : errList) {
								if (error.contains("職種ID1")) {
							out.println("<div class='text-danger'>" + error + "</div>");
								}
							}
						}
						%>

						<label for="job_id2" class="form-label">職種ID(2)</label> <input
							type="number" class="form-control" id="job_id2" name="job_id2"
							value="<%=bean.getJob_id2() != null ? bean.getJob_id2() : ""%>">

						<%
						if (errList != null) {
							for (String error : errList) {
								if (error.contains("職種ID2")) {
							out.println("<div class='text-danger'>" + error + "</div>");
								}
							}
						}
						%>

						<label for="job_id3" class="form-label">職種ID(3)</label> <input
							type="number" class="form-control" id="job_id3" name="job_id3"
							value="<%=bean.getJob_id3() != null ? bean.getJob_id3() : ""%>">

						<%
						if (errList != null) {
							for (String error : errList) {
								if (error.contains("職種ID3")) {
							out.println("<div class='text-danger'>" + error + "</div>");
								}
							}
						}
						%>
					</div>

				<div class="row">
					<!-- 開催期間：開始日 -->
					<div class="col-sm-6 mb-2">
						<label for="periodstart" class="form-label">開催期間：開始日</label> <input
							type="text" class="form-control" id="periodstart"
							name="periodstart"
							value="<%=bean.getPeriodstart() != null ? bean.getPeriodstart() : ""%>"
							placeholder="yyyy/MM/dd">

						<%
						if (errList != null) {
							for (String error : errList) {
								if (error.contains("開始日")) {
							out.println("<div class='text-danger'>" + error + "</div>");
								}
							}
						}
						%>
					</div>

					<!-- 開催期間：終了日 -->
					<div class="col-sm-6 mb-2">
						<label for="periodend" class="form-label">開催期間：終了日</label> <input
							type="text" class="form-control" id="periodend" name="periodend"
							value="<%=bean.getPeriodend() != null ? bean.getPeriodend() : ""%>"
							placeholder="yyyy/MM/dd">

						<%
						if (errList != null) {
							for (String error : errList) {
								if (error.contains("終了日")) {
							out.println("<div class='text-danger'>" + error + "</div>");
								}
							}
						}
						%>
					</div>
				</div>

				<!-- スタッフメモ -->
				<div class="col-md-12 mb-2">
					<label for="staff_memo" class="form-label">スタッフメモ</label>
					<textarea class="form-control" cols="50" rows="4" id="staff_memo"
						name="staff_memo" placeholder="お客様へは表示されません"><%=bean.getStaff_memo() != null ? bean.getStaff_memo() : ""%></textarea>

					<%
					if (errList != null) {
						for (String error : errList) {
							if (error.contains("スタッフメモ")) {
						out.println("<div class='text-danger'>" + error + "</div>");
							}
						}
					}
					%>
				</div>

				<!-- ボタン -->
				<div class="row d-flex justify-content-end mb-2">
					<div class="col-auto">
						<button type="submit" name="actionbtn" value="insert"
							class="btn btn-dark">新規登録</button>
					</div>
					<div class="col-auto">
						<button type="submit" name="actionbtn" value="update"
							class="btn btn-dark">更新</button>
					</div>

					<div class="col-auto">
						<button type="submit" name="actionbtn" value="back"
							class="btn btn-dark">戻る</button>
					</div>
					<div class="col-auto">
						<button type="submit" name="actionbtn" value="delete"
							class="btn btn-dark" onclick="return confirm('本当に削除しますか？')">削除</button>
					</div>



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