package edu.ted.web.movieland.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import edu.ted.web.movieland.web.configuration.WebMovieLandJavaConfiguration;
import edu.ted.web.movieland.web.entity.MovieDTO;
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
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration("")
@ContextConfiguration(classes = {MovieLandJavaConfiguration.class, WebMovieLandJavaConfiguration.class})
public class MovieControllerITest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void whenAllMoviesRequestAndReturnsAllMovies_thenCorrect() throws Exception {
        mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(10))));
    }

    @Test
    public void whenMoviesByGenreRequestAndReturnsMovies_thenCorrect() throws Exception {
        mockMvc.perform(get("/movie/genre/63"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void givenAllMoviesRequestedWithSorting_whenSorted_thenCorrect() throws Exception {
        ResultActions performedAction = mockMvc.perform(get("/movie?rating=desc"));
        performedAction
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(5))));
        String resultContent = performedAction.andReturn().getResponse().getContentAsString();
        List<MovieDTO> moviesList = mapResponseMoviesList(resultContent);
        double rating = moviesList.get(0).getRating();
        for (MovieDTO movieDTO : moviesList) {
            assertTrue(rating>=movieDTO.getRating());
        }
    }

    @Test
    public void givenAllMoviesRequestedWithSortingButWithNoOrder_whenSorted_thenCorrect() throws Exception {
        ResultActions performedAction = mockMvc.perform(get("/movie?rating2=desc"));
        performedAction
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(5))));
        String resultContent = performedAction.andReturn().getResponse().getContentAsString();
        List<MovieDTO> moviesList = mapResponseMoviesList(resultContent);
        double rating = moviesList.get(0).getRating();
        for (MovieDTO movieDTO : moviesList) {
            assertTrue(rating>=movieDTO.getRating());
        }
    }

    @Test
    public void when3RandomMoviesRequestAndReturns3RandomMovies_thenCorrect() throws Exception {
        String resultContent = getControllerResponse();
        String resultContentNext = getControllerResponse();
        List<MovieDTO> randomMoviesList = mapResponseMoviesList(resultContent);
        List<MovieDTO> randomMoviesListNext = mapResponseMoviesList(resultContentNext);
        int matchesCount = 0;
        for (int i = 0; i < randomMoviesList.size(); i++) {
            matchesCount += Objects.equals(randomMoviesList.get(i), randomMoviesListNext.get(i)) ? 1 : 0;
        }
        assertTrue(matchesCount < 3);
    }

    private List<MovieDTO> mapResponseMoviesList(String resultContent) throws com.fasterxml.jackson.core.JsonProcessingException {
        return new ObjectMapper().readValue(resultContent, new TypeReference<List<MovieDTO>>() {
        });
    }

    private String getControllerResponse() throws Exception {
        ResultActions performedAction = mockMvc.perform(get("/movie/random"));
        performedAction
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(3))));
        String resultContent = performedAction.andReturn().getResponse().getContentAsString();
        return resultContent;
    }
}
