import "https://unpkg.com/leaflet@1.9.4/dist/leaflet.js";
import "https://unpkg.com/maplibre-gl@4.7.1/dist/maplibre-gl.js";
import "https://unpkg.com/@maplibre/maplibre-gl-leaflet@0.0.22/leaflet-maplibre-gl.js";

// Координаты центра Санкт-Петербурга
const middleOfSPB = [59.9343, 30.3351];

// Координаты Дома Бенуа (Широта и Долгота)
const houseOfBenuaLocation = [59.962417, 30.312833];

async function getLocation() {
  try {
    const response = await fetch("http://ip-api.com/json/");
    const json = await response.json();
    if (typeof json.lat === "number" && typeof json.lon === "number") {
      return [json.lat, json.lon];
    }
  } catch (error) {}
  return middleOfSPB; // возвращаем центр СПБ, если не удалось получить координаты
}

async function init() {
  const map = L
    .map('map')
    .setView(houseOfBenuaLocation, 18); // Устанавливаем вид на локацию и задаем масштаб

  L.maplibreGL({
    style: 'https://tiles.openfreemap.org/styles/liberty',
    attribution: `<a href="https://openfreemap.org" target="_blank">OpenFreeMap</a> <a href="https://www.openmaptiles.org/" target="_blank">© OpenMapTiles</a> Data from <a href="https://www.openstreetmap.org/copyright" target="_blank">OpenStreetMap</a>`,
  }).addTo(map);

  // Эффект выделения при наведении
  houseMarker.on('mouseover', function (e) {
    this.openPopup(); // Открывает всплывающее окно при наведении
    this.setOpacity(0.7); // Уменьшает непрозрачность метки
  });

  houseMarker.on('mouseout', function (e) {
    this.closePopup(); // Закрывает всплывающее окно при уходе мыши
    this.setOpacity(1); // Возвращает непрозрачность к норме
  });
}

init();

