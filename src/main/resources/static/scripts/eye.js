//document.addEventListener('DOMContentLoaded', function () {
//    // Обработка клика на изображение глаза
//    const eyeImg = document.querySelector('.eye-img');
//    const pwdInput = document.getElementById('user-pwd');
//
//    if (eyeImg) {
//        eyeImg.addEventListener('click', function () {
//            const pwdFieldType = pwdInput.type;
//            if (pwdFieldType === 'password') {
//                pwdInput.type = 'text'; // Меняем тип на текст
//                eyeImg.src = 'elements/openedEye.svg'; // Меняем изображение
//            } else {
//                pwdInput.type = 'password'; // Меняем тип обратно на пароль
//                eyeImg.src = 'elements/closedEye.svg'; // Меняем изображение обратно
//            }
//        });
//    }
//
//})