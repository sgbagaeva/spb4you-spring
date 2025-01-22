document.addEventListener('DOMContentLoaded', function () {
    var userId = document.getElementById('cabinetContainer').dataset.userId;

    if (!userId) {
        console.error('Не удалось получить userId');
        return;
    }

    console.log('Динамически полученный User ID:', userId);

    const eyeImg = document.querySelector('.eye-img');
    const pwdInput = document.getElementById('user-pwd');

    if (eyeImg) {
        eyeImg.addEventListener('click', function () {
            const pwdFieldType = pwdInput.type;
            if (pwdFieldType === 'password') {
                pwdInput.type = 'text'; // Меняем тип на текст
                eyeImg.src = 'elements/openedEye.svg'; // Меняем изображение
            } else {
                pwdInput.type = 'password'; // Меняем тип обратно на пароль
                eyeImg.src = 'elements/closedEye.svg'; // Меняем изображение обратно
            }
        });
    }

    var buttonContainer = document.querySelector('.buttonContainer');
    var yourActions = document.querySelector('.cabinetYourActionsWrapper');
    var cabinetHomeBtnDisappear = document.getElementById('cabinetHomeBtnDisappear');
    var cabinetFolder = document.getElementById('editButton');
    var originalPhoto = null;

    cabinetFolder.addEventListener('click', function () {
        console.log('Edit button clicked');
        originalPhoto = document.querySelector('.cabinetPhoto').src;

        buttonContainer.style.display = 'flex';
        cabinetHomeBtnDisappear.style.display = 'none';
        yourActions.style.display = 'none';
        cabinetFolder.style.display = 'none';

        var infoElements = document.querySelectorAll('.cabinetInfo');
        infoElements.forEach(function (element) {
            var input = document.createElement('input');
            input.type = 'text';
            input.placeholder = element.textContent;

            element.replaceWith(input);
        });

        document.getElementById('photoUpload').style.opacity = 1;
    });

    document.getElementById('cancelButton').addEventListener('click', function () {
        buttonContainer.style.display = 'none';
        yourActions.style.display = 'flex';
        cabinetHomeBtnDisappear.style.display = 'flex';
        cabinetFolder.style.display = 'flex';

        document.querySelector('.cabinetPhoto').src = originalPhoto;
        document.getElementById('photoUpload').style.opacity = 0;

        var inputs = document.querySelectorAll('input[placeholder]');
        inputs.forEach(function (input) {
            var p = document.createElement('p');
            p.className = 'cabinetInfo';
            p.textContent = input.placeholder;

            input.replaceWith(p);
        });
    });

    document.getElementById('photoUpload').addEventListener('change', function () {
        var file = this.files[0];
        var reader = new FileReader();

        reader.onload = function (event) {
            document.querySelector('.cabinetPhoto').src = event.target.result;
            document.getElementById('photoUpload').style.opacity = 0;
        };

        reader.readAsDataURL(file);
    });

    document.getElementById('saveButton').addEventListener('click', function () {
        var formData = new FormData();

        // Получение элементов формы
        const firstNameInput = document.querySelector('input[placeholder="firstName"]');
        const lastNameInput = document.querySelector('input[placeholder="lastName"]');
        const usernameInput = document.querySelector('input[placeholder="username"]');

        // Проверка наличия элементов
        if (firstNameInput && lastNameInput && usernameInput) {
            // Добавляем значения в FormData
            formData.append("username", usernameInput.value);
            formData.append("firstName", firstNameInput.value);
            formData.append("lastName", lastNameInput.value);

            // Обработка файла (если выбран)
            var file = document.getElementById('photoUpload').files[0];
            if (file) {
                formData.append('photo', file);
            }

            var userId = document.getElementById('cabinetContainer').dataset.userId;

            // Отправка данных на сервер
            fetch(`/users/${userId}/update`, {
                method: 'PUT',
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text || 'Ошибка при обновлении данных');
                    });
                }
                return response.json();
            })
            .then(updatedUser => {
                alert('Данные успешно обновлены');
                // Обновление интерфейса
                $('.cabinetInfo').eq(0).text(updatedUser.firstName);
                $('.cabinetInfo').eq(1).text(updatedUser.lastName);
                $('.cabinetInfo').eq(2).text(updatedUser.username);
                document.querySelector('.cabinetPhoto').src = `/elements/avatars/${updatedUser.avatarPath}`;
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert('Не удалось обновить данные. Пожалуйста, попробуйте позже.');
            });
        } else {
            console.error('Не удалось найти одно из полей формы');
            alert('Пожалуйста, заполните все обязательные поля.');
        }
    });
});
