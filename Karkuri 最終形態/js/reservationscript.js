document.addEventListener('DOMContentLoaded', function() {
	// ★デバッグ用: スクリプト実行開始を確認
	console.log("--- script.js 実行開始 ---");

	var calendarEl = document.getElementById('calendar');

	// ✅ JSP からのデータ取得 (念のため配列かチェック)
	var reservations = Array.isArray(window.reservations) ? window.reservations : [];
	console.log("予約データ (JSON):", reservations);

	// --- 最後にクリックされた日付を保持する変数 ---
	let currentlySelectedDate = null;

	// --- 時間割表の予約表示を更新する関数 (デバッグログ強化版) ---
	function updateScheduleTable(clickedDate) {
		currentlySelectedDate = clickedDate;
		console.log("時間割表更新 (日付保持):", currentlySelectedDate);

		// 1. クリックされた日付の予約データをフィルタリング
		const todaysReservations = reservations.filter(res => {
			const reservationDate = res.reserve_day ? res.reserve_day.split("T")[0] : null;
			return reservationDate === clickedDate;
		});
		// ★ログ追加：見つかった予約の件数と内容を確認
		console.log("該当日の予約 (件数):", todaysReservations.length);
		// (内容が多い場合があるので、件数だけでも確認)
		// console.log("該当日の予約 (内容):", JSON.stringify(todaysReservations)); 

		// 2. 時間割表の tbody を取得
		const scheduleTableBody = document.querySelector(".schedule-table tbody");
		if (!scheduleTableBody) {
			console.error("エラー: .schedule-table tbody が見つかりません");
			return;
		}

		// 3. 既存の印をクリア
		console.log("時間割表クリア開始..."); // ★ログ追加
		scheduleTableBody.querySelectorAll("td[data-time]").forEach(td => {
			td.innerHTML = "";
		});
		console.log("時間割表クリア完了."); // ★ログ追加

		// 4. 該当日の予約データでループして印をつける
		console.log("予約データに基づいて印をつけるループ開始..."); // ★ログ追加
		if (todaysReservations.length === 0) {
			console.log(" -> 該当日の予約がないため、ループ処理なし。"); // ★ログ追加
		}

		todaysReservations.forEach((res, index) => { // ループカウンタ index を追加
			console.log(`--- ループ ${index + 1} 開始 ---`); // ★ログ追加
			console.log("  処理中の予約データ:", JSON.stringify(res)); // ★ログ追加

			// 4a. 時間を "HH:00" 形式に変換
			const reserveHourStr = res.reserve_time ? res.reserve_time.substring(0, 2) : null;
			if (!reserveHourStr) {
				console.warn("  -> 予約時間が取得できないためスキップ"); // ★ログ追加
				console.log(`--- ループ ${index + 1} 終了 (スキップ) ---`); // ★ログ追加
				return; // 次の予約へ
			}
			const scheduleTime = reserveHourStr + ":00";
			console.log(`  -> 計算された表示時間 (scheduleTime): "${scheduleTime}"`); // ★ログ追加

			// プランIDから列インデックスを決定
			const planId = res.plan_id;
			let columnIndex = -1;

			// ↓↓↓ 文字列に変換した後、前後の空白を除去 (.trim()) する ★★★
			const planIdStr = String(res.plan_id).trim();

			// ★デバッグログ追加：trim後の値を確認
			console.log(`  -> planIdStr (trim後): "${planIdStr}"`);

			// 条件比較 (ここは変更なし)
			if (planIdStr === '1001') { columnIndex = 1; }
			else if (planIdStr === '1002') { columnIndex = 2; }
			else if (planIdStr === '1003') { columnIndex = 3; }
			else if (planIdStr === '1004') { columnIndex = 4; }

			// ★デバッグログ移動：columnIndexが確定した後に表示
			console.log(`  -> 計算されたcolumnIndex: ${columnIndex}`);

			// 4c. 列インデックスが有効ならセルを探して印をつける
			if (columnIndex !== -1) {
				// ↓↓↓ 文字列連結 (+) で組み立てる方法に書き換える ↓↓↓
				const targetCellSelector = 'td[data-time="' + scheduleTime + '"]:nth-child(' + (columnIndex + 1) + ')';
				// ↓↓↓ ログ出力もシンプルな形にする ↓↓↓
				console.log("  -> 検索するセルセレクタ (連結版):", targetCellSelector);

				// セルを検索
				const targetCell = scheduleTableBody.querySelector(targetCellSelector);

				// 4d. セルが見つかったかどうかで処理分岐
				if (targetCell) {
					// セルが見つかった！印をつける
					targetCell.innerHTML = "○";
					targetCell.style.color = "blue";
					targetCell.style.textAlign = "center";
					console.log(`  -> ★★★ 印つけ成功！ ★★★`); // ★ログ追加
				} else {
					// セルが見つからなかった場合の警告
					console.warn(`  -> !!! セルが見つかりません !!!`); // ★ログ追加
					console.warn(`      確認点1: JSPの<tbody>ループで、data-time="${scheduleTime}" を持つ ${columnIndex + 1} 番目の <td> が正しく生成されていますか？`);
					console.warn(`      確認点2: 時間 (<span class="math-inline">\{scheduleTime\}\) や列番号 \(</span>{columnIndex + 1}) は正しいですか？`);
				}
			} else {
				// 対応する列インデックスが見つからなかった場合 (プランIDが 1001-1004 以外など)
				console.warn(`  -> 不明または未対応のプランIDのためスキップ`); // ★ログ追加
			}
			console.log(`--- ループ ${index + 1} 終了 ---`); // ★ログ追加
		}); // forEach ループの終わり
		console.log("ループ処理終了."); // ★ログ追加
	} // updateScheduleTable 関数の終わり




	// --- ★★★ ここから handleScheduleClick 関数全体を修正・確認 ★★★ ---
	function handleScheduleClick(clickedCell) {
		console.log("--- handleScheduleClick 開始 ---");
		if (!currentlySelectedDate) {
			console.warn("詳細表示エラー: まずカレンダーで日付を選択してください。");
			return;
		}
		console.log("保持されている日付:", currentlySelectedDate);

		const clickedTime = clickedCell.dataset.time; // "HH:00"
		const clickedColumnIndex = clickedCell.cellIndex;
		console.log(`クリックされたセルの情報: 時間=${clickedTime}, 列インデックス=${clickedColumnIndex}`);

		let clickedPlanId = null;
		// 注意: cellIndex は0始まり。時間列が0、プランAが1、プランBが2、プランCが3、テストが4
		if (clickedColumnIndex === 1) {       // 2番目の列がクリックされたら ID=1001
			clickedPlanId = '1001';
		} else if (clickedColumnIndex === 2) { // 3番目の列がクリックされたら ID=1002
			clickedPlanId = '1002';
		} else if (clickedColumnIndex === 3) { // 4番目の列がクリックされたら ID=1003
			clickedPlanId = '1003';
		} else if (clickedColumnIndex === 4) { // ★追加: 5番目の列がクリックされたら ID=1004
			clickedPlanId = '1004';
		}
		console.log(`推定されたプランID: ${clickedPlanId}`);

		if (!clickedPlanId) {
			console.warn("クリックされた列からプランIDを特定できませんでした。");
			return;
		}

		console.log("予約データを検索します...");
		const targetReservation = reservations.find(res => {
			const reservationDate = res.reserve_day ? res.reserve_day.split("T")[0] : null;
			// 時間比較: 予約時間の "HH:00" とクリックされたセルの "HH:00" を比較
			const reserveHourStr = res.reserve_time ? res.reserve_time.substring(0, 2) : null;
			const reservationScheduleTime = reserveHourStr ? reserveHourStr + ":00" : null;
			const planIdStr = String(res.plan_id);

			return reservationDate === currentlySelectedDate &&
				reservationScheduleTime === clickedTime &&
				planIdStr === clickedPlanId;
		});

		console.log("検索で見つかった予約データ:", targetReservation);

		// --- 詳細表示エリアとフォーム要素を取得 ---
		const detailArea = document.getElementById("detail");
		const formDate = document.getElementById("date");
		const formTime = document.getElementById("time"); // JSPで <input type="time"> になっている想定
		const formPlan = document.getElementById("plan");
		const formComment = document.getElementById("comment");
		const formReserveNum = document.getElementById("reserve_num"); // 隠しフィールド

		// --- ★修正点1: 取得した要素が存在するかチェックを追加 ---
		if (!detailArea || !formDate || !formTime || !formPlan || !formComment || !formReserveNum) {
			console.error("エラー: 詳細表示エリアまたはフォーム要素(ID=date, time, plan, comment, reserve_num)が見つかりません。JSPのIDを確認してください。");
			return; // 要素がなければここで終了
		}

		// --- 予約が見つかった場合の処理 ---
		if (targetReservation) {
			// ★修正点2: 表示用に時間を "HH:mm" 形式にする変数を作成
			const reserveTimeDisplay = targetReservation.reserve_time ? targetReservation.reserve_time.substring(0, 5) : '';

			// 1. 詳細エリアに表示 (IDも表示)
			const detailHtml = `
                <p><strong>予約ID:</strong> ${targetReservation.id}</p> 
                <p><strong>日付:</strong> ${currentlySelectedDate}</p>
                <p><strong>時間:</strong> ${reserveTimeDisplay || '時間不明'}</p> 
                <p><strong>プランID:</strong> ${targetReservation.plan_id}</p>
                <p><strong>コメント:</strong> ${targetReservation.reserve_comment || 'コメントなし'}</p>
                `;
			detailArea.innerHTML = detailHtml;

			// 2. フォームに値をセット
			console.log("[HSC] フォームへの値セット開始...");
			formDate.value = currentlySelectedDate;
			console.log("[HSC] 日付セット完了:", formDate.value);

			// <input type="time"> に "HH:mm" をセット
			formTime.value = reserveTimeDisplay;
			console.log("[HSC] 時間セット完了:", formTime.value);

			formPlan.value = targetReservation.plan_id; // <select> の value に合わせる
			console.log("[HSC] プランセット完了:", formPlan.value);
			formComment.value = targetReservation.reserve_comment || '';
			console.log("[HSC] コメントセット完了:", formComment.value);
			formReserveNum.value = targetReservation.id; // 隠しフィールドに ID セット
			console.log("[HSC] 隠しフィールドIDセット完了:", formReserveNum.value);

			// ★修正点3: 完了ログを追加 (以前の指示通り)
			console.log("フォームにデータをセットしました。予約ID:", targetReservation.id);

		} else {
			// --- 予約が見つからなかった場合の処理 ---
			detailArea.innerHTML = "<p>詳細情報の取得に失敗しました。データが見つかりません。</p>";

			// ★修正点4: フォームをクリアする処理を追加
			formDate.value = "";
			formTime.value = "";
			formPlan.value = ""; // プランドロップダウンを未選択に
			formComment.value = "";
			formReserveNum.value = ""; // 隠しフィールドもクリア
			console.warn("該当する予約データが見つからなかったため、フォームをクリアしました。");

			console.warn("該当する予約データが見つかりませんでした。検索条件:", currentlySelectedDate, clickedTime, clickedPlanId);
		}
		console.log("--- handleScheduleClick 終了 ---");
	} // handleScheduleClick 関数の終わり
	// --- ★★★ handleScheduleClick 関数の修正はここまで ★★★ ---


	// --- FullCalendar の設定 ---
	var calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		locale: 'ja',
		headerToolbar: {
			left: 'prev,next today',
			center: 'title',
			right: 'dayGridMonth,timeGridWeek,timeGridDay'
		},
		dateClick: function(info) {
			updateScheduleTable(info.dateStr);
		},
		events: reservations.map(res => ({
			title: "○",
			start: res.reserve_day ? res.reserve_day.split("T")[0] : null,
			allDay: true,
		})),

		eventContent: function(arg) {
			let el = document.createElement("div");
			el.style.color = "red";
			el.style.fontSize = "16px";
			el.innerText = arg.event.title;
			return { domNodes: [el] };
		}, // ← eventContent の終わりにカンマを追加 (重要)

		// ↓↓↓ ここに dayCellClassNames を追加 ↓↓↓
		dayCellClassNames: function(arg) {
			let today = new Date();
			today.setHours(0, 0, 0, 0); // 時刻をリセット

			let cellDate = new Date(arg.date);
			cellDate.setHours(0, 0, 0, 0); // 時刻をリセット

			// 日付が一致したら 'highlight-today' を返す
			return cellDate.getTime() === today.getTime() ? ['highlight-today'] : [];
		} // ← dayCellClassNames の終わり (この後に設定がなければカンマ不要)

	});



	calendar.render(); // カレンダー描画
	console.log("カレンダーイベント:", calendar.getEvents());

	// --- 予約履歴のロード ---
	function loadReservationHistory() {
		// ... (変更なし) ...
	}
	loadReservationHistory();

	// --- 時間割表へのクリックイベントリスナー設定 ---
	const scheduleTableBody = document.querySelector(".schedule-table tbody");
	console.log("scheduleTableBody 要素:", scheduleTableBody); // デバッグ用
	if (scheduleTableBody) {
		console.log("イベントリスナーを設定します..."); // デバッグ用
		scheduleTableBody.addEventListener('click', function(event) {
			// console.log("★ TBODY がクリックされました！ ★", event.target); // 詳細すぎるのでコメントアウトしてもOK
			console.log("時間割表クリックイベント発生！");
			const targetCell = event.target.closest('td');
			// console.log("クリックされた要素:", event.target); // デバッグ完了したら消してもOK
			// console.log("最も近いTD要素:", targetCell); // デバッグ完了したら消してもOK

			if (targetCell && targetCell.hasAttribute('data-time')) {
				// console.log("クリックされたTDのinnerHTML:", targetCell.innerHTML); // デバッグ完了したら消してもOK
				if (targetCell.innerHTML.includes("○")) {
					console.log("○を含むセルです。handleScheduleClickを呼び出します。");
					handleScheduleClick(targetCell);
				} else {
					console.log("○を含まないセルです。");
				}
			} else {
				console.log("クリックされたのは時間/プランのセルではありません。");
			}
		});
		console.log("イベントリスナー設定完了！"); // デバッグ用
	} else {
		console.error("イベントリスナー設定エラー: .schedule-table tbody が見つかりません");
	}

}); // DOMContentLoaded の終わり