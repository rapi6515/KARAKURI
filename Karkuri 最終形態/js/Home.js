const apiKey = "43f628e259f6e4c4982032af9ef9fc5f";
const city = "Kyoto";

fetch(`https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${apiKey}&units=metric&lang=ja`)
    .then(response => response.json())
    .then(data => {
        const weather = data.weather[0].description;
        const temp = data.main.temp;
        document.getElementById("weather-info").innerHTML =
            `<i class="bi bi-cloud-sun me-2"></i> ${city}の天気：${weather}（${temp}℃）`;
    })
    .catch(error => {
        console.error("天気情報の取得に失敗しました", error);
        document.getElementById("weather-info").innerHTML = "天気情報の取得に失敗しました";
    });
    
