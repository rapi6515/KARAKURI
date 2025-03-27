<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0">
	<title>KARAKURIトップページ</title>
	
	<!-- BootstrapのCSS（デザイン用） -->
	<!--integrity → ファイルが改ざんされていないかチェック crossorigin="anonymous" → 認証情報なしでCDN(外部サーバ）リソースを取得  -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
     	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
   
    <!-- 自作のCSS --> 	
    <link rel="stylesheet" href="styles.css">
   
    <!-- jQuery読込　＄を使用したjQueryを使用可能に -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- BootstrapのJavaScript（モーダルを動かすために必要） -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
       	 integrity="sha384-w76Aq8E+YDDRvo1FqDs8peA6jQm4zV6FroDf2SDz6ncdKsLczFjIBh4FhZ1ANcf4"crossorigin="anonymous"></script>
   
   
</head>

<body>


	<header class="text-end p-3">
		<button id="openModal" class="btn btn-outline-primary">📅　予約はこちら</button>
	</header>
	
	<!--モーダル -->
	<div id="reservationModal" class="modal fade" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">予約プラン検索</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="閉じる"></button>
				</div>
				<div class="modal-body">
					<label class="form-label">予約日</label>予約変更・取消の場合は予約番号を入力してください</p>
                    <label class="form-label">予約番号：</label>
					<input type="date" class="form-control">
					
					<label class="form-label mt-2">予約時間</label>
					<input type="time" class="form-control">
					
					<label class="form-label mt-2">予約人数</label>
					<input type="number" class="form-control" min="1">
					
					<p class="mt-3">※予約変更・取消の場合は予約番号を入力してください</p>
					<label class="form-label">予約番号</label>
					<input type="text" class="form-control">
				</div>
				<div class="modal-footer">
				<button class="btn btn-primary">プラン検索</button>
				<button class="btn btn-warning">変更</button>
				<button class="btn btn-danger">取消</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
				</div>
			</div>
		</div>
		
	</div>
	
	<script>
		$(document).ready(function(){
			$("#openModal").click(function(){
				S("#reservationModal").modal('show');
			});
		});
	</script>
	
	
    
	
</body>

</html>
