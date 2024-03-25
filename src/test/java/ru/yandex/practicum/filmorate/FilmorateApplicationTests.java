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
    void shouldReturnErrorName() throws Exception {
        filmOne = new Film("", "description", LocalDate.of(2000, 10, 10), 40);

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setObjectToJson(filmOne)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
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
