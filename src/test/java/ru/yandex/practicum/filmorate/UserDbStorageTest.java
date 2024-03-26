package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendDaoImpl;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDbStorageTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindUserById() throws ParseException {
        // Подготавливаем данные для теста
        User newUser = new User("vanya123", "Ivan Petrov", "user@email.ru", LocalDate.of(1990, 1, 1));
        newUser.setFriends(null);
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate, new FriendDaoImpl(jdbcTemplate));
        User forId = userStorage.heandlerUser(newUser);

        // вызываем тестируемый метод
        User savedUser = userStorage.getUser(forId.getId());
        newUser.setId(forId.getId());
        newUser.setFriends(new HashSet<>());
        newUser.setActive(true);
        // проверяем утверждения
        assertThat(savedUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newUser);        // и сохраненного пользователя - совпадают
    }
}