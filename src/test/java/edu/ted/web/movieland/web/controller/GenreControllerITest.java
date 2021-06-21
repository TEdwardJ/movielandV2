package edu.ted.web.movieland.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ted.web.movieland.annotation.FullSpringMvcTest;
import edu.ted.web.movieland.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FullSpringMvcTest
class GenreControllerITest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAllGenres() throws Exception {
        var performedAction = mockMvc.perform(get("/genres"));
        performedAction
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(10))))
                .andExpect(jsonPath("$[0].id", not(empty())))
                .andExpect(jsonPath("$[0].name", not(empty())));

        var resultContent = performedAction.andReturn().getResponse().getContentAsString();
        var genreList = mapResponseGenreList(resultContent);
        for (var genre : genreList) {
            assertTrue(genre.getId() > 0);
            assertNotNull(genre.getName());
            assertFalse(genre.getName().isEmpty());
        }
    }

    private List<Genre> mapResponseGenreList(String resultContent) throws JsonProcessingException {
        return new ObjectMapper().readValue(resultContent, new TypeReference<>() {
        });
    }
}