<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>予約一覧</title>
    <link href='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css' rel='stylesheet' />
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js'></script>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
    <script src="<%= request.getContextPath() %>/js/script.js"></script>
</head>
<body>
<div class="container">
    <!-- カレンダー -->
    <div id="calendar"></div>

    <!-- 予約表 -->
    <div class="schedule-container">
        <table class="schedule-table">
            <thead>
                <tr>
                    <th>時間</th>
                    <th>プランA</th>
                    <th>プランB</th>
                    <th>プランC</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // 9:00～18:00までを30分刻みで表示
                    for (int hour = 9; hour < 18; hour++) { 
                        for (int min = 0; min < 60; min += 30) {
                            String time = hour + ":" + (min == 0 ? "00" : "30");
                %>
                <tr>
                    <td><%= time %></td>
                    <td data-time="<%= time %>"></td>
                    <td data-time="<%= time %>"></td>
                    <td data-time="<%= time %>"></td>
                </tr>
                <%      } 
                    } 
                    // 18:00だけ追加表示する場合
                    String time = "18:00";
                %>
                <tr>
                    <td><%= time %></td>
                    <td data-time="<%= time %>"></td>
                    <td data-time="<%= time %>"></td>
                    <td data-time="<%= time %>"></td>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- 詳細エリアと予約履歴を縦並びにする -->
    <div class="detail-wrapper">
        <!-- 予約詳細エリア -->
        <div id="detail-container">
            <h3>予約詳細</h3>
            <div id="detail">時間をクリックするとここに詳細が表示されます</div>
        </div>
        
        <!-- 予約履歴エリア -->
        <div class="history-container">
            <h3>予約履歴</h3>
            <div id="history-list" class="scrollable">
                <!-- JavaScript で動的に予約履歴を追加 -->
            </div>
        </div>
    </div>
</div>

<!-- 予約入力フォーム -->
<div class="reservation-form">
    <!-- 日付・プラン・時間 -->
    <div class="form-row">
        <label>日付</label>
        <input type="date" name="date">
        <label>プラン</label>
        <select class="form-select form-select-sm" id="plan" name="plan">
        <option value="">選択してください</option>
        <label>時間</label>
        <input type="time" name="time">
    </div>

    <!-- コメント -->
    <div class="form-comment">
        <label>コメント</label>
        <input type="text" name="comment">
    </div>

    <!-- ボタン -->
    <div class="form-buttons">
    	<button type="button">検索</button>
        <button type="button">登録</button>
        <button type="button">更新</button>
        <button type="button">削除</button>
    </div>
</div>
</body>
</html>
