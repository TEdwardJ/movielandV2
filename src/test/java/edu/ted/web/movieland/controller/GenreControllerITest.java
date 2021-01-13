package edu.ted.web.movieland.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import edu.ted.web.movieland.configuration.WebMovieLandJavaConfiguration;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.MovieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration("")
@ContextConfiguration(classes = {MovieLandJavaConfiguration.class, WebMovieLandJavaConfiguration.class})
class GenreControllerITest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void getAllGenres() throws Exception {
        final ResultActions performedAction = mockMvc.perform(get("/v1/movie/genre"));
        performedAction
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(10))))
                .andExpect(jsonPath("$[0].id", not(empty())))
                .andExpect(jsonPath("$[0].name", not(empty())));

        String resultContent = performedAction.andReturn().getResponse().getContentAsString();
        List<Genre> genreList = mapResponseGenreList(resultContent);
        for (Genre genre : genreList) {
            assertNotNull(genre.getId());
            assertNotNull(genre.getName());
            assertFalse(genre.getName().isEmpty());
        }
    }

    private List<Genre> mapResponseGenreList(String resultContent) throws JsonProcessingException {
        return new ObjectMapper().readValue(resultContent, new TypeReference<List<Genre>>() {
        });
    }
}