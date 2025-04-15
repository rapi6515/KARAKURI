document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    // カレンダーを初期化
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        events: function(fetchInfo, successCallback, failureCallback) {
            fetch('<%= request.getContextPath() %>/ReservationHistoryServlet')
                .then(response => response.json())
                .then(data => {
                    // 予約データを FullCalendar のイベント形式に変換
                    let events = data.map(reservation => ({
                        title: `プランID: ${reservation.planId}`,
                        start: reservation.date, // 日付（YYYY-MM-DD 形式）
                        allDay: true
                    }));
                    successCallback(events); // カレンダーにイベントをセット
                })
                .catch(error => {
                    console.error("Error:", error);
                    failureCallback(error);
                });
        },

        // **日本時間の今日の日付を正しく強調**
        dayCellClassNames: function(arg) {
            let today = new Date();
            today.setHours(0, 0, 0, 0);  // **日本時間の0時にリセット**

            let cellDate = new Date(arg.date);
            cellDate.setHours(0, 0, 0, 0); // **セルの日付も0時にリセット**

            return cellDate.getTime() === today.getTime() ? ['highlight-today'] : [];
        }
    });

    calendar.render();

    // **予約履歴をロード**
    function loadReservationHistory() {
        fetch('<%= request.getContextPath() %>/ReservationHistoryServlet')
            .then(response => response.json())
            .then(data => {
                let historyList = document.getElementById("history-list");
                historyList.innerHTML = "";

                data.forEach(reservation => {
                    let div = document.createElement("div");
                    div.classList.add("history-item");
                    div.innerHTML = `<strong>${reservation.date}</strong> - プランID: ${reservation.planId} (予約日: ${reservation.createdDate})`;
                    historyList.appendChild(div);
                });
            })
            .catch(error => console.error("Error:", error));
    }

    // 予約履歴をロード
    loadReservationHistory();
});
