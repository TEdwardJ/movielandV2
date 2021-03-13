package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.FullSpringTestConfiguration;
import edu.ted.web.movieland.entity.UserToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@WebAppConfiguration("")
@ContextConfiguration(classes = {FullSpringTestConfiguration.class})
class ReviewControllerTest {

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
        String uuid = UUID.randomUUID().toString();
        var contentAsString = mockMvc.perform(post("/login").param("email", "dennis.craig82@example.com").param("password", "coldbeer"))
                .andReturn().getResponse().getContentAsString();
        UserToken responseObject = new ObjectMapper().readValue(contentAsString, new TypeReference<UserToken>() {
        });
        return responseObject.getUuid().toString();
    }
}