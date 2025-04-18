<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="bean.*"%>
<%@ page import="Commom.K_Bean"%>
<jsp:useBean id="bean" scope="request" class="Commom.K_Bean" />
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>staff管理画面</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
	crossorigin="anonymous">
<!-- CSS -->
<link rel="stylesheet" href="css/Register.css" />

</head>
	
<body class="bg-light">
	<%@include file = "/jsp/Menu.jsp"%>
	<%
	List<Map<String, Object>> podition = (List<Map<String, Object>>) session.getAttribute("podition");
	List<Map<String, Object>> job_name = (List<Map<String, Object>>) session.getAttribute("job_name");
	List<Map<String, Object>> sex = (List<Map<String, Object>>) session.getAttribute("sex");
	List<Map<String, Object>> rank = (List<Map<String, Object>>) session.getAttribute("rank");
	List<Map<String, Object>> list = (List<Map<String, Object>>) request.getAttribute("list");//１データ
	List<Map<String, Object>> allList = (List<Map<String, Object>>) request.getAttribute("allList");//全件
	String msg = (String) session.getAttribute("msg");
	String action = request.getParameter("action"); // actionパラメータを取得
	String mg = "";
	String ops = "";
	%>
<div class="content-wrapper container py-4">	
	<div class="accordion accordion-flush" id="accordionFlushExample">
		<div class="accordion-item">
			<h2 class="accordion-header">
				<button class="accordion-button collapsed" type="button"
					data-bs-toggle="collapse" data-bs-target="#flush-collapseOne"
					aria-expanded="false" aria-controls="flush-collapseOne">
					Staff</button>
			</h2>
			<div id="flush-collapseOne" class="accordion-collapse collapse"
				data-bs-parent="#accordionFlushExample">
				<div class="accordion-body">
					<div class="mt-5">
						<div class="card-wrapper">
							<%
							int index = 0;
							if (allList != null && !allList.isEmpty()) {
								for (Map<String, Object> staff : allList) {
									String avatar = staff.get("avatar") != null ? staff.get("avatar").toString() : "";
									String staff_id = staff.get("staff_id") != null ? staff.get("staff_id").toString() : "";
									String name = staff.get("name") != null ? staff.get("name").toString() : "";
									String podition_name = staff.get("podition_name") != null ? staff.get("podition_name").toString() : "";
									String job_name_str = staff.get("job_name") != null ? staff.get("job_name").toString() : "";
							%>
							<div class="card">
								<img src="<%=avatar%>" alt="avatar">
								<h3><%=name%></h3>
								<p>
									スタッフID:
									<%=staff_id%></p>
								<p>
									役職:
									<%=podition_name%></p>
								<p>
									職種:
									<%=job_name_str%></p>
							</div>
							<%
							}
							}
							%>
						</div>
						<!-- ↓ カードの下にナビゲーション配置 -->
						<div
							class="pagination-buttons mt-4 d-flex justify-content-center align-items-center gap-3">
							<button id="prev" class="btn btn-outline-primary">＜</button>
							<span id="pageIndicator">ページ 1 / 1</span>
							<button id="next" class="btn btn-outline-primary">＞</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%
	msg = (String) session.getAttribute("msg");
	if (msg != null && !msg.isEmpty()) {
	%>
	<p style="color: green;"><%=msg%></p>
	<%
	session.removeAttribute("msg"); // メッセージは一度だけ表示
	}
	%>
	<form action="Register" method="post">

		<div class="container mt-4">
			<div class="row">
				<div class="col-md-6">
					<label for="formStaff_id" class="form-label">ID</label> <input
						type="text" class="form-control" id="formStaff_id" name="staff_id"
						value="<%=(bean != null) ? bean.getStaff_id() : ""%>">
					<%
					if (bean != null && bean.getErrStf() != null) {
					%>
					<div class="text-danger"><%=bean.getErrStf()%></div>
					<%
					}
					%>
				</div>
				<div class="col-md-6">
					<label for="poditionSelect" class="form-label">役職</label> <select
						class="form-select" name="podition" id="poditionSelect">
						<option selected>役職</option>
						<%
						if (podition != null) {
							for (Map<String, Object> listP : podition) {
								String po = "";
								// bean.getPodition()がnullでないことを確認し、一致する場合にselectedを設定
								if (bean != null && listP.get("seq").toString().equals(bean.getPodition())) {
							po = "selected";
								}
						%>
						<option value="<%=listP.get("seq")%>" <%=po%>><%=listP.get("textvalue")%></option>
						<%
						}
						}
						%>
					</select>
					<%
					if (bean != null && bean.getErrPod() != null) {
					%>
					<div class="text-danger"><%=bean.getErrPod()%></div>
					<%
					}
					%>
				</div>
			</div>
		</div>
		<div class="container mt-4">
			<div class="row">
				<div class="col-md-6">
					<label for="form_name" class="form-label">名前</label> <input
						type="text" class="form-control" id="form_name" name="name"
						value="<%=bean.getName()%>">
					<%
					if (bean != null && bean.getErrName() != null) {
					%>
					<div class="text-danger"><%=bean.getErrName()%></div>
					<%
					}
					%>
				</div>
				<div class="col-md-6">
					<label for="jobSelect" class="form-label">職種</label> <select
						class="form-select" aria-label="Default select example"
						name="job_name" id="jobSelect">
						<option selected>職種</option>
						<%
						if (job_name != null) {
							for (Map<String, Object> listJ : job_name) {
								String jn = "";
								if (listJ.get("seq").toString().equals(bean.getJob_name())) {
							jn = "selected";
								}
						%>
						<option value="<%=listJ.get("seq")%>" <%=jn%>><%=listJ.get("textvalue")%></option>
						<%
						}
						}
						%>
					</select>
					<%
					if (bean != null && bean.getErrJob() != null) {
					%>
					<div class="text-danger"><%=bean.getErrJob()%></div>
					<%
					}
					%>

				</div>
			</div>
		</div>
		<div class="container mt-4">
			<div class="row">
				<div class="col-md-6">
					<label for="form_tel" class="form-label">TEL</label> <input
						type="text" class="form-control" name="tel" id="form_tel"
						value="<%=bean.getTel()%>">
					<%
					if (bean != null && bean.getErrTel() != null) {
					%>
					<div class="text-danger"><%=bean.getErrTel()%></div>
					<%
					}
					%>

				</div>
				<div class="col-md-3">
					<label for="sex_Select" class="form-label">性別</label> <select
						class="form-select" aria-label="Default select example" name="sex"
						id="sex_Select">
						<option selected>性別</option>
						<%
						if (sex != null) {
							for (Map<String, Object> listS : sex) {
								String sx = "";
								if (listS.get("seq").toString().equals(bean.getSex())) {
							sx = "selected";
								}
						%>
						<option value="<%=listS.get("seq")%>" <%=sx%>><%=listS.get("textvalue")%>
						</option>
						<%
						}
						}
						%>
					</select>
					<%
					if (bean != null && bean.getErrSex() != null) {
					%>
					<div class="text-danger"><%=bean.getErrSex()%></div>
					<%
					}
					%>
				</div>
				<div class="col-md-3">
					<label for="rank_Select" class="form-label">権限</label> <select
						class="form-select" aria-label="Default select example"
						name="rank" id="rank_Select">
						<option selected>権限</option>
						<%
						if (rank != null) {
							for (Map<String, Object> listR : rank) {
								String ms = "";
								if (listR.get("seq").toString().equals(bean.getRank())) {
							ms = "selected";
								}
						%>
						<option value="<%=listR.get("seq")%>" <%=ms%>><%=listR.get("textvalue")%></option>
						<%
						}
						}
						%>
					</select>
					<%
					if (bean != null && bean.getErrRank() != null) {
					%>
					<div class="text-danger"><%=bean.getErrRank()%></div>
					<%
					}
					%>
				</div>
			</div>
		</div>
		<div class="container mt-4">
			<div class="row">
				<div class="col-md-6">
					<label for="form_ddress" class="form-label">住所</label> <input
						type="text" class="form-control" name="address" id="form_address"
						value="<%=bean.getAddress()%>">
					<%
					if (bean != null && bean.getErrAddress() != null) {
					%>
					<div class="text-danger"><%=bean.getErrAddress()%></div>
					<%
					}
					%>
				</div>
				<div class="col-md-6">
					<label for="form_birth_day" class="form-label">生年月日</label> <input
						type="text" class="form-control" name="birth_day"
						id="form_birth_day" value="<%=bean.getBirth_day()%>">
					<%
					if (bean != null && bean.getErrBirth() != null) {
					%>
					<div class="text-danger"><%=bean.getErrBirth()%></div>
					<%
					}
					%>
				</div>
			</div>
		</div>
		<div class="container mt-4">
			<div class="row">
				<div class="col-md-6">
					<label for="form_residence" class="form-label">建物名</label> <input
						type="text" class="form-control" name="residence"
						id="form_residence" value="<%=bean.getResidence()%>">
					<%
					if (bean != null && bean.getErrResidence() != null) {
					%>
					<div class="text-danger"><%=bean.getErrResidence()%></div>
					<%
					}
					%>
				</div>
				<div class="col-md-6">
					<label for="form_avatar" class="form-label">アバター</label> <input
						type="text" class="form-control" name="avatar" id="form_avatar"
						value="<%=bean.getAvatar()%>">
					<%
					if (bean != null && bean.getErrAvatar() != null) {
					%>
					<div class="text-danger"><%=bean.getErrAvatar()%></div>
					<%
					}
					%>
				</div>
			</div>
		</div>
		<div class="row ms-1">
			<div class="d-flex justify-content-end gap-4">
				<div class="col-auto mt-5 md-4">
					<button class="btn btn-secondary" type="submit" name="btn"
						value="検索">検索</button>
				</div>
				<div class="col-auto mt-5 md-4">
					<button class="btn btn-secondary" type="submit" name="btn"
						value="登録">登録</button>
				</div>
				<div class="col-auto mt-5 md-4">
					<button class="btn btn-secondary" type="submit" name="btn"
						value="更新">更新</button>
				</div>
				<div class="col-auto mt-5 md-4">
					<button class="btn btn-secondary me-5" type="submit" name="btn"
						value="削除">削除</button>
				</div>
			</div>
		</div>
	</form>
</div>	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
		crossorigin="anonymous">
		
	</script>
	<!-- JSをここに移動！ -->
	<script src="${pageContext.request.contextPath}/js/Register.js"></script>
</body>
</html>