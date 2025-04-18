window.onload = function() {
	console.log("carousel.js 読み込まれた！");
	const cards = document.querySelectorAll(".card");
	console.log("カード数:", cards.length);
	const prevBtn = document.getElementById("prev");
	const nextBtn = document.getElementById("next");
	const pageIndicator = document.getElementById("pageIndicator");

	const cardsPerPage = 3;
	let currentPage = 0;
	const totalPages = Math.ceil(cards.length / cardsPerPage);

	function updateCarousel() {
		cards.forEach((card, index) => {
			if (index >= currentPage * cardsPerPage && index < (currentPage + 1) * cardsPerPage) {
				card.style.display = "block";
				card.style.animation = "fadeIn 0.3s ease-in-out"; // アニメーション追加
			} else {
				card.style.display = "none";
				card.style.animation = "none";
			}
		});

		// ページインジケーターの更新
		pageIndicator.textContent = `ページ ${currentPage + 1} / ${totalPages}`;

		// ボタンの有効/無効状態
		prevBtn.disabled = currentPage === 0;
		nextBtn.disabled = currentPage === totalPages - 1;
	}

	prevBtn.addEventListener("click", () => {
		if (currentPage > 0) {
			currentPage--;
			updateCarousel();
		}
	});

	nextBtn.addEventListener("click", () => {
		if (currentPage < totalPages - 1) {
			currentPage++;
			updateCarousel();
		}
	});

	updateCarousel(); // 初期表示
};