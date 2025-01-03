let currentIndex = 0; // Индекс для текущей группы фотографий
const photosPerView = 3; // Количество отображаемых фотографий
const photos = document.querySelectorAll('.locationPhoto'); // Получаем список фотографий

function updatePhotos() {
    // Скрываем все фотографии
    photos.forEach((photo, index) => {
        if (index >= currentIndex && index < currentIndex + photosPerView) {
            photo.style.display = 'block'; // Отображаем текущие фотографии
        } else {
            photo.style.display = 'none'; // Скрываем остальные фотографии
        }
    });
}

function showNext() {
    currentIndex += 1; // Переходим к следующему фото
    // Проверяем, не вышли ли мы за пределы
    if (currentIndex + photosPerView > photos.length) {
        currentIndex = 0; // Сброс индекса, если мы достигли конца
    }
    updatePhotos();
}

function showPrevious() {
    currentIndex -= 1; // Переходим к предыдущему фото
    // Проверяем, не вышли ли мы за пределы
    if (currentIndex < 0) {
        currentIndex = photos.length - photosPerView; // Зацикливаем с последними фото
    }
    updatePhotos();
}

// Инициализация при загрузке
document.addEventListener('DOMContentLoaded', () => {
    updatePhotos(); // Инициализация слайдера
});
