package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.FullSpringMvcTest;
import edu.ted.web.movieland.entity.UserToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FullSpringMvcTest
class ReviewControllerTest {
    @Autowired
    private String testUserEmail;

    @Autowired
    private String testUserPassword;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void givenNewReviewWithNoToken_whenNotAuthorizedCode_thenCorrect() throws Exception {
        mockMvc.perform(post("/review").param("movieId", "105").param("text", "test"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenNewReviewWithExistingToken_whenOKCode_thenCorrect() throws Exception {
        String uuid = sendAuthorizeRequest();
        mockMvc.perform(post("/review")
                .param("movieId", "105")
                .param("text", "test")
                .param("uuid", uuid))
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$", not(empty())));
    }

    private String sendAuthorizeRequest() throws Exception {
        var contentAsString = mockMvc.perform(post("/login").param("email", testUserEmail).param("password", testUserPassword))
                .andReturn().getResponse().getContentAsString();
        UserToken responseObject = new ObjectMapper().readValue(contentAsString, new TypeReference<UserToken>() {
        });
        return responseObject.getUuid().toString();
    }
}