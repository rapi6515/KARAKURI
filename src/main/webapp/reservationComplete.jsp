<!-- 戸高　reservationComplete.jsp(予約情報入力の完了確約表示画面 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
	<meta charset="UTF-8">
	<title>予約完了</title>
	<link rel="stylesheet" href="styles.css">
</head>

<body>

	<h2>ご予約ありがとうございました</h2>
    <p>以下の内容でご予約を確定しました。</p>
    
    <p><strong>予約番号:</strong> ${reservation.reserve_num}</p>
    <p><strong>お名前:</strong> ${reservation.name}</p>
    <p><strong>カナ:</strong> ${reservation.kana}</p>
    <p><strong>電話番号:</strong> ${reservation.tel}</p>
    <p><strong>メールアドレス:</strong> ${reservation.email}</p>
    <p><strong>国籍:</strong> ${reservation.nationality}</p>
    <p><strong>ご予約のきっかけ:</strong> ${reservation.reason}</p>
    <p><strong>備考:</strong> ${reservation.reserve_comment}</p>
    
    <p>ご予約の変更・キャンセルは、予約番号を使用して行えます。</p>
    <form action="BackToTopServlet" method="post">
    	<button type="submit">TOPページに戻る</button>
    </form>

</body>


</html>