// Плавная прокрутка при клике на якорные ссылки
$(document).ready(function() {
    $("a[href^='#'").on('click', function(event) {
        var target = this.hash;
        if (target) {
            event.preventDefault();
            $('html, body').animate({
                scrollTop: $(target).offset().top
            }, 800) // время анимации в миллисекундах
        }
    })
})
