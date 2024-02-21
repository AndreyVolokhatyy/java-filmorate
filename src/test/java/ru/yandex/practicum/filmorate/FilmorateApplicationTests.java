package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.data.DataControllers;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class FilmorateApplicationTests {

    private ObjectMapper mapper;
    private User userOne;
    private User userTwo;

    private Film filmOne;
    private Film filmTwo;

    @Autowired
    private MockMvc mockMvc;

    public FilmorateApplicationTests() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Test
    void shouldReturnEmptyMessage() throws Exception {
        mockMvc.perform(get("/users")).andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[]"));

        mockMvc.perform(get("/films")).andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[]"));
    }

    @Test
    void shouldReturnObjectUser() throws Exception {
        userOne = new User("aasd@asd.asd", "xxx", "mister", LocalDate.of(2000, 10, 10));

        MvcResult mvcResult = this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(userOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        User userTest = getObjectFromJson(response, User.class);
        userOne.setId(1);
        Assertions.assertTrue(userOne.equals(userTest));
    }

    @Test
    void shouldReturnObjectsUser() throws Exception {
        userOne = new User("aasd@asd.asd", "xxx", "mister", LocalDate.of(2000, 10, 10));
        userTwo = new User("aa@as.as", "zzz", "mister", LocalDate.of(2001, 10, 10));

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(userOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(userTwo)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        MvcResult mvcResult = this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().is2xxSuccessful())
                .andReturn();
        userOne.setId(1);
        userTwo.setId(2);
        String response = mvcResult.getResponse().getContentAsString();
        List<User> userTest = getObjectFromJsonList(response, User.class);

        Assertions.assertTrue(userOne.equals(userTest.get(0)));
        Assertions.assertTrue(userTwo.equals(userTest.get(1)));
    }

    @Test
    void shouldReturnUpdateObjectUser() throws Exception {
        userOne = new User("aasd@asd.asd", "xxx", "mister", LocalDate.of(2000, 10, 10));

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(userOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());


        userOne.setId(1);
        userOne.setEmail("new@new.ru");

        MvcResult mvcResult = this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(userOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        User userTest = getObjectFromJson(response, User.class);
        Assertions.assertTrue(userOne.equals(userTest));
    }

    @Test
    void shouldReturnErrorEmail() throws Exception {
        userOne = new User("aasdasd.asd", "xxx", "mister", LocalDate.of(2000, 10, 10));

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(userOne)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnErrorLogin() throws Exception {
        userOne = new User("aasd@asd.asd", "xx x", "mister", LocalDate.of(2000, 10, 10));

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(userOne)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnErrorDate() throws Exception {
        userOne = new User("aasd@asd.asd", "xxx", "mister", LocalDate.of(2045, 10, 10));

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(userOne)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnFilledName() throws Exception {
        userOne = new User("aasd@asd.asd", "xxx", "", LocalDate.of(2000, 10, 10));

        MvcResult mvcResult = mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(userOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        User userTest = getObjectFromJson(response, User.class);
        userOne.setId(1);
        userOne.setName("xxx");
        Assertions.assertTrue(userOne.equals(userTest));
    }

    @Test
    void shouldReturnObjectFilm() throws Exception {
        filmOne = new Film("name", "description", LocalDate.of(2000, 10, 10), 40);

        MvcResult mvcResult = this.mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Film filmTest = getObjectFromJson(response, Film.class);
        filmOne.setId(1);
        Assertions.assertTrue(filmOne.equals(filmTest));
    }

    @Test
    void shouldReturnObjectsFilm() throws Exception {
        filmOne = new Film("name", "description", LocalDate.of(2000, 10, 10), 40);
        filmTwo = new Film("day", "description day", LocalDate.of(1900, 10, 10), 400);

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmTwo)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        MvcResult mvcResult = this.mockMvc.perform(get("/films")).andDo(print()).andExpect(status().is2xxSuccessful())
                .andReturn();
        filmOne.setId(1);
        filmTwo.setId(2);
        String response = mvcResult.getResponse().getContentAsString();
        List<Film> filmTest = getObjectFromJsonList(response, Film.class);

        Assertions.assertTrue(filmOne.equals(filmTest.get(0)));
        Assertions.assertTrue(filmTwo.equals(filmTest.get(1)));
    }

    @Test
    void shouldReturnUpdateObjectFilm() throws Exception {
        filmOne = new Film("name", "description", LocalDate.of(2000, 10, 10), 40);

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        filmOne.setId(1);
        filmOne.setDescription("new@new.ru");

        MvcResult mvcResult = this.mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Film filmTest = getObjectFromJson(response, Film.class);
        Assertions.assertTrue(filmOne.equals(filmTest));
    }

    @Test
    void shouldReturnErrorName() throws Exception {
        filmOne = new Film("", "description", LocalDate.of(2000, 10, 10), 40);

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnCreateIn200Description() throws Exception {
        filmOne = new Film("xxx", "The implementation of the method body creates and returns a " +
                "new Greeting object with id and content attributes based on the next value from " +
                "the counter and formats the given name by using the greeting",
                LocalDate.of(2000, 10, 10), 40);

        MvcResult mvcResult = this.mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Film filmTest = getObjectFromJson(response, Film.class);
        filmOne.setId(1);
        Assertions.assertTrue(filmOne.equals(filmTest));
    }

    @Test
    void shouldReturnCreateIn199Description() throws Exception {
        filmOne = new Film("xxx", "The implementation of the method body creates and returns a " +
                "new Greeting object with id and content attributes based on the next value from " +
                "the counter and formats the given name by using the greetin",
                LocalDate.of(2000, 10, 10), 40);

        MvcResult mvcResult = this.mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Film filmTest = getObjectFromJson(response, Film.class);
        filmOne.setId(1);
        Assertions.assertTrue(filmOne.equals(filmTest));
    }

    @Test
    void shouldReturnErrorMaxDescription() throws Exception {
        filmOne = new Film("xxx", "The implementation of the method body creates and returns a " +
                "new Greeting object with id and content attributes based on the next value from " +
                "the counter and formats the given name by using the greeting ",
                LocalDate.of(2000, 10, 10), 40);

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnErrorDateFilm() throws Exception {
        filmOne = new Film("", "description", LocalDate.of(1985, 12, 27), 40);

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnCreateDateFilm() throws Exception {
        filmOne = new Film("", "description", LocalDate.of(1985, 12, 28), 40);

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnErrorDuration() throws Exception {
        filmOne = new Film("", "description", LocalDate.of(1985, 12, 28), 9);

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    private <T> T getObjectFromJson(String json, Class<T> t) {
        try {
            return mapper.readValue(json, t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> getObjectFromJsonList(String json, Class<T> t) {
        try {
            JSONArray array = new JSONArray(json);
            List l = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                l.add(mapper.readValue(o.toString(), t));
            }
            return l;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String setObjectToJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void clear() {
        DataControllers.clearData();
    }

}
