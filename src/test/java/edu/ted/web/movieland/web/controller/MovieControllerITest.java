package edu.ted.web.movieland.web.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ted.web.movieland.annotation.FullSpringMvcTest;
import edu.ted.web.movieland.web.dto.MovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FullSpringMvcTest
public class MovieControllerITest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenAllMoviesRequestAndReturnsAllMovies_thenCorrect() throws Exception {
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(10))));
    }

    @Test
    public void whenMoviesByGenreRequestAndReturnsMovies_thenCorrect() throws Exception {
        mockMvc.perform(get("/movies/genre/63"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void givenMovieByIdRequest_whenMovieReturnedAndEnriched_thenCorrect() throws Exception {
        mockMvc.perform(get("/movies/105"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$.id", not(empty())))
                .andExpect(jsonPath("$.description", not(empty())))
                .andExpect(jsonPath("$.russianName", not(empty())))
                .andExpect(jsonPath("$.nativeName", not(empty())))
                .andExpect(jsonPath("$.releaseYear", not(empty())))
                .andExpect(jsonPath("$.rating", not(empty())))
                .andExpect(jsonPath("$.price", not(empty())))
                .andExpect(jsonPath("$.pictureUrl", not(empty())))
                .andExpect(jsonPath("$", hasKey("genres")))
                .andExpect(jsonPath("$", hasKey("genres")))
                .andExpect(jsonPath("$", hasKey("countries")))
                .andExpect(jsonPath("$", hasKey("reviews")))
                .andExpect(jsonPath("$.genres", not(emptyArray())))
                .andExpect(jsonPath("$.genres[0]", hasKey("id")))
                .andExpect(jsonPath("$.genres[0]", hasKey("name")))
                .andExpect(jsonPath("$.genres[0].id", not(empty())))
                .andExpect(jsonPath("$.genres[0].name", not(empty())))
                .andExpect(jsonPath("$.countries", not(emptyArray())))
                .andExpect(jsonPath("$.countries[0]", hasKey("id")))
                .andExpect(jsonPath("$.countries[0]", hasKey("name")))
                .andExpect(jsonPath("$.countries[0].id", not(empty())))
                .andExpect(jsonPath("$.countries[0].name", not(empty())))
                .andExpect(jsonPath("$.reviews", not(emptyArray())))
                .andExpect(jsonPath("$.reviews[0]", hasKey("id")))
                .andExpect(jsonPath("$.reviews[0]", hasKey("text")))
                .andExpect(jsonPath("$.reviews[0].id", not(empty())))
                .andExpect(jsonPath("$.reviews[0].text", not(empty())))
                .andExpect(jsonPath("$.reviews[0].user", not(empty())));
    }

    @Test
    public void givenAllMoviesRequestedWithSorting_whenSorted_thenCorrect() throws Exception {
        ResultActions performedAction = mockMvc.perform(get("/movies?rating=desc"));
        performedAction
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(5))));
        var resultContent = performedAction.andReturn().getResponse().getContentAsString();
        var moviesList = mapResponseMoviesList(resultContent);
        var rating = moviesList.get(0).getRating();
        for (var movieDTO : moviesList) {
            assertTrue(rating>=movieDTO.getRating());
        }
    }

    @Test
    public void givenAllMoviesRequestedWithSortingButWithNoOrder_whenSorted_thenCorrect() throws Exception {
        ResultActions performedAction = mockMvc.perform(get("/movies?rating2=desc"));
        performedAction
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(5))));
        String resultContent = performedAction.andReturn().getResponse().getContentAsString();
        List<MovieDto> moviesList = mapResponseMoviesList(resultContent);
        var rating = moviesList.get(0).getRating();
        for (var movieDTO : moviesList) {
            assertTrue(rating>=movieDTO.getRating());
        }
    }

    @Test
    public void when3RandomMoviesRequestAndReturns3RandomMovies_thenCorrect() throws Exception {
        var resultContent = getControllerResponse();
        var resultContentNext = getControllerResponse();
        var randomMoviesList = mapResponseMoviesList(resultContent);
        var randomMoviesListNext = mapResponseMoviesList(resultContentNext);
        var matchesCount = 0;
        for (var i = 0; i < randomMoviesList.size(); i++) {
            matchesCount += Objects.equals(randomMoviesList.get(i), randomMoviesListNext.get(i)) ? 1 : 0;
        }
        assertTrue(matchesCount < 3);
    }

    private List<MovieDto> mapResponseMoviesList(String resultContent) throws com.fasterxml.jackson.core.JsonProcessingException {
        return new ObjectMapper().readValue(resultContent, new TypeReference<>() {
        });
    }

    private String getControllerResponse() throws Exception {
        ResultActions performedAction = mockMvc.perform(get("/movies/random"));
        performedAction
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(3))));
        return performedAction.andReturn().getResponse().getContentAsString();
    }
}
