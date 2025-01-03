function toggleLike(imgElement, locationId, userId) {
    // Проверка, что входные параметры корректные
    if (!locationId || !userId) {
        console.error('Invalid locationId or userId');
        return; // Прекращаем выполнение функции, если параметры некорректные
    }

    const currentSrc = imgElement.getAttribute('src');
    const isLiking = currentSrc.includes('notLiked.svg');

    // Изменяем изображение
    imgElement.setAttribute('src', isLiking ? '/elements/liked.svg' : '/elements/notLiked.svg');

    const url = isLiking
        ? `/users/${userId}/like-locations/${locationId}`
        : `/users/${userId}/unlike-locations/${locationId}`;

    console.log(`Sending request to: ${url}`);

    // Отправка запроса на сервер
    fetch(url, { method: 'POST' })
        .then(response => {
            if (response.ok) {
                console.log(isLiking ? 'Location liked successfully.' : 'Location unliked successfully.');
            } else {
                console.error('Error liking/unliking location:', response);
                // Восстанавливаем состояние изображения, если возникает ошибка
                imgElement.setAttribute('src', isLiking ? '/elements/notLiked.svg' : '/elements/liked.svg');
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
            // Восстанавливаем состояние изображения в случае ошибки
            imgElement.setAttribute('src', isLiking ? '/elements/notLiked.svg' : '/elements/liked.svg');
        });
}
