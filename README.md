Accelerometer & Firebase.
Програма призначена для збору даних з Accelerometer і збереження цих даних на віддаленому сервері Firebase.
Створити проект на Firebase console.

На першому екрані інтерфейс для авторизації/створення користувача.
На головному екрані програми панель керування сервісом для збору даних(Start/Stop).
Щоб записувати дані користувач має бути авторизованим.

Fragment1 для відображення збережених даних списком.
Один елемент списку містить час коли дані були зібрані і дані Accelerometer по трьом координатам(x, y, z).

Fragment2 для відображення збережених даних за допомогою графіка. по кожній із координат (tOx, tOy, tOz)

Переключення між фрагментами на ваш вибір.
по усмотрени показувати обидва фрагменти для landscape.

Service зчитує дані по  X, Y, Z з Accelerometer пристрою кожну секунду і записує ці дані у Firebase.

по усмотрению. Вибирати інтервал запису даних на головному екрані перед стартом Service, тобто зчитувати дані кожну 1, 2, 5, 10 сек.
по усмотрению. Можливість задати час запуску сервісу.
по усмотрению. Можливість задати довжину сесії, наприклад якщо вказати довжину сесії 1 хвилина і запустити сервіс, то через 1 хвилину після запуску він автоматично завершиться.
по усмотрению. Зберігати історію даних для пристрою/користувача, Кожен старт service як окрема сесія, елемент історії. Можливість переглядати як список сесій, так і детальну інформацію про окрему сесію.

