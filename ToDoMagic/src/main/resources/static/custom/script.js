document.addEventListener('DOMContentLoaded', function() {
    const todoForm = document.getElementById('todo-form');
    const addButton = document.getElementById('add-task-button'); // Кнопка "Add Task"
    const searchButton = document.getElementById('search-button'); // Кнопка "Search"

    // Функция для проверки валидности формы и активации кнопки "Add Task"
    function checkValidityAndToggleButton() {
        if (todoForm.checkValidity()) {
            addButton.disabled = false; // Активируем кнопку "Add Task", если форма валидна
        } else {
            addButton.disabled = true; // Деактивируем кнопку "Add Task", если форма не валидна
        }
    }
searchButton.addEventListener('click', function(event) {
    event.preventDefault(); // Предотвращаем стандартное действие кнопки (если нужно)

    window.location.href = 'searchTasks.html';
});



    // Обработчик отправки формы для добавления задачи
    todoForm.addEventListener('submit', async function(event) {
        event.preventDefault(); // Предотвращаем стандартное поведение отправки формы

        let taskName = document.getElementById('name').value;
        let taskDescription = document.getElementById('description').value;

        if (!taskName.trim()) {
            alert('Task name is required');
            return;
        }

        let data = {
            name: taskName,
            description: taskDescription
        };

        try {
            const response = await fetch('/task', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                // При успешном добавлении задачи переходим на страницу с уведомлением
                window.location.href = 'taskSuccessNotification.html';
            } else {
                // Выводим сообщение об ошибке при добавлении задачи
                alert('Error adding task. Please try again.');
            }
        } catch (error) {
            console.error('An error occurred:', error);
            alert('An error occurred. Please try again.');
        }
    });

    // Обработчик изменений в полях ввода для проверки валидности формы
    todoForm.addEventListener('input', function() {
        checkValidityAndToggleButton();
    });

    // При загрузке страницы проверяем валидность и активируем/деактивируем кнопку "Add Task"
    checkValidityAndToggleButton();
});






























