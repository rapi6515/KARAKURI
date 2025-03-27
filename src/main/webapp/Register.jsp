<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<!-- 必須のメタタグ -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>staff管理画面</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
	crossorigin="anonymous">
</head>
<!-- ボディ色指定 -->
<body class="bg-light">
	<!-- アコーディオン -->
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
					<strong>
						<table class="table">
							<thead>
								<tr>
									<th scope="col">ID</th>
									<th scope="col">役職</th>
									<th scope="col">氏名</th>
									<th scope="col">職種</th>
								</tr>
							</thead>
						</table>
					</strong>
				</div>
			</div>
		</div>
	</div>
	<!-- 横並びレイアウト -->
	<div class="container mt-4">
		<div class="row">
			<!-- ID -->
			<div class="col-md-6">
				<label for="formStaff_id" class="form-label">ID</label>
				<input type="email" class="form-control"
					id="formStaff_id"name="staff_id">
			</div>
			<!-- 役職 -->
			<div class="col-md-6">
				<label for="poditionSelect" class="form-label">役職</label> <select
					class="form-select" aria-label="Default select example"
					name="podition"id="poditionSelect">
					<option selected>役職</option>
					<option value="1">One</option>
					<option value="2">Two</option>
					<option value="3">Three</option>
				</select>
			</div>
		</div>
	</div>
	<div class="container mt-4">
		<div class="row">
			<!-- 名前 -->
			<div class="col-md-6">
				<label for="form_name" class="form-label">名前</label>
				<input type="text" class="form-control"
					id="form_name"name=name>
			</div>
			<!-- 職種 -->
			<div class="col-md-6">
				<label for="job_nameSelect" class="form-label">職種</label> <select
					class="form-select" aria-label="Default select example"
					name="job_name" id="job_nameSelect">
					<option selected>職種</option>
					<option value="1">One</option>
					<option value="2">Two</option>
					<option value="3">Three</option>
				</select>
			</div>
		</div>
	</div>
	<div class="container mt-4">
		<div class="row">
			<!-- TEL -->
			<div class="col-md-6">
				<label for="form_tel" class="form-label">TEL</label>
				<input type="text" class="form-control"name="tel"id="form_tel">
			</div>
			<!-- 性別 -->
			<div class="col-md-3">
				<label for="sex_Select" class="form-label">性別</label> <select
					class="form-select" aria-label="Default select example"
					name="sex"id="sex_Select">
					<option selected>性別</option>
					<option value="1">One</option>
					<option value="2">Two</option>
					<option value="3">Three</option>
				</select>
			</div>
			<!-- 権限 -->
			<div class="col-md-3">
				<label for="rank_Select" class="form-label">権限</label> <select
					class="form-select" aria-label="Default select example" 
					name="rank"id="rank_Select">
                    <option selected>権限</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                </select>
            </div>
        </div>
    </div>
    <!-- 住所 -->
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-12">
                <label for="form_ddress" class="form-label">住所</label>
                <input type="text" class="form-control" 
                name="address" id="form_address">
            </div>
        </div>
    </div>
    <!-- 建物名 -->
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-6">
                <label for="form_residence" class="form-label">建物名</label>
                <input type="text" class="form-control" name="residence" id="form_residence">
            </div>
    <!-- アバター -->
            <div class="col-md-6">
                <label for="form_avatar" class="form-label">アバター</label>
                <input type="text" class="form-control" name= "avatar" id="form_avatar">
            </div>
        </div>
    </div>
    <!-- ボタン -->
    <div class="row ms-1">
        <div class="d-flex justify-content-end gap-4">
            <!-- 検索 -->
            <div class="col-auto mt-5 md-4">
                <button class="btn btn-secondary" type="submit" name="btn" Value="検索">検索</button>
            </div>
            <!-- 登録 -->
            <div class="col-auto mt-5 md-4">
                <button class="btn btn-secondary" type="submit" name="btn" Value="登録">登録</button>
            </div>
            <!-- 更新 -->
            <div class="col-auto mt-5 md-4">
                <button class="btn btn-secondary" type="submit" name="btn" Value="更新">更新</button>
            </div>
            <!-- 削除 -->
            <div class="col-auto mt-5 md-4">
                <button class="btn btn-secondary me-5" type="submit" name="btn" Value="削除">削除</button>
            </div>
        </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous">
        </script>
</body>

</html>