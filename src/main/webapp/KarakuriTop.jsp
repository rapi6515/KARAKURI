<!-- 戸高　KarakuriTop.jsp画面 -->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>KARAKURIトップページ</title>
    
    <!-- integrity→CDNからダウンロードしたファイルが改ざんされていないかを確認するためのセキュリティ機能で -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <!-- Bootstrap Icons（予約ボタン用） -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
     
    <!-- Google Fonts（和風フォント） -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+JP&display=swap" rel="stylesheet">
          
    <!-- 自分のcss読込 -->
    <link rel="stylesheet" href="styles.css">
    
    <!-- jＱｕｅｒｙ読込 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!--Bootstrap 5.3.0のJavaScriptファイルを読み込  -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
            crossorigin="anonymous"></script>
</head>

<body style="font-family: 'Noto Serif JP', serif;">
    
    <header class="text-end p-3">
    
        <button id="openModal" dark shadow-sm">
        	<i class="bi bi-calendar-check-fill"></i>予約はこちら
        </button>
        
    </header>
    
    <!-- ヒーロー画像エリア -->
    <div class="hero-images container my-4">
        <div class="row justify-content-center g-3">
            <div class="col-10 col-md-4">
                <img src="images/kimono1.jpg" class="img-fluid rounded shadow" alt="着物1">
            </div>
            <div class="col-10 col-md-4">
                <img src="images/kimono2.jpg" class="img-fluid rounded shadow" alt="着物2">
            </div>
            <div class="col-10 col-md-4">
                <img src="images/kimono3.jpg" class="img-fluid rounded shadow" alt="着物3">
            </div>
        </div>
    </div>

    <div id="reservationModal" class="modal fade" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">予約プラン検索</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="閉じる"></button>
                </div>
                <div class="modal-body">
                	<form action="${pageContext.request.contextPath}/ReservationSearchServlet" method="post">
      
                    	<label class="form-label">予約希望日：</label>
                    	<input type="date" class="form-control" name="date">

                    	<label class="form-label mt-2">予約時間</label>
                    	<input type="time" class="form-control" name="time">

                    	<label class="form-label mt-2">予約人数</label>
                    	<input type="number" class="form-control" name="people" min="1">

                    	<p class="mt-3">※予約変更・取消の場合は予約番号を入力してください</p>
                   		<label class="form-label">予約番号</label>
                    	<input type="text" class="form-control" name="reserve_num">
                
                   		<div class="modal-footer">
                   			<button class="btn btn-primary" formaction="ReservationSearchServlet">プラン検索</button>
                            <button class="btn btn-warning" formaction="ReservationModifyServlet?mode=edit">変更</button>
                            <button class="btn btn-danger" formaction="ReservationModifyServlet?mode=delete">取消</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
                		</div>
               		</form>
               		 <!-- エラーメッセージ表示 -->
                    <% if (request.getAttribute("erMsg") != null) { %>
                        <div class="alert alert-danger mt-3">
                            <%= request.getAttribute("erMsg") %>
                        </div>
                    <% } %>
           		</div>
            </div>
        </div>
    </div>
    
    <!-- フッター -->
    <footer>
        <div class="footer-container">
	    	<div class="footer-info">
		      <p>〒123-4567 京都府京都市〇〇町1-2-3</p>
		      <p>TEL: 075-123-4567</p>
		      <p>MAIL: info@karakuri-kimono.jp</p>
		      <p>営業時間：10時～19時</p>
	    	</div>
	  		<div class="map-container">
	            <iframe
	                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d13131.79851786683!2d135.7512602!3d34.9858498!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x600108a23455ad0b%3A0xecc10c98228c9cf5!2z5Lqs6YO95aSn5a2m!5e0!3m2!1sja!2sjp!4v1712837337029!5m2!1sja!2sjp"
	                allowfullscreen=""
	                loading="lazy"
	                referrerpolicy="no-referrer-when-downgrade">
	            </iframe>
        	</div>
  		</div>
    </footer>
    <!-- モーダル動かすJＱｕｅｒｙ -->
    <script>
        $(document).ready(function () {
            $("#openModal").click(function () {
                $("#reservationModal").modal('show');
            });
            // erMsg があるならモーダルを自動で開く
            <% if (request.getAttribute("erMsg") != null) { %>
            $("#reservationModal").modal('show');
            <% } %>
        });
    </script>
</body>
</html>
