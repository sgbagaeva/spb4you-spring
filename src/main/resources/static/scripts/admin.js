$(document).ready(function() {
    $('.sidebar-item').click(function(e) {
        e.preventDefault(); // Отменяем стандартное поведение ссылки
        var url = $(this).attr('href'); // Получаем URL из ссылки
        $('#main-content').load(url); // Загружаем содержимое в основной блок
    });
});
