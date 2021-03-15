package edu.ted.web.movieland.web.controller;

import com.jayway.jsonpath.JsonPath;
import edu.ted.web.movieland.annotation.FullSpringMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FullSpringMvcTest
class ReviewControllerTest {
    @Value("${testUser.email}")
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
    }

    private String sendAuthorizeRequest() throws Exception {
        var contentAsString = mockMvc.perform(post("/login").param("email", testUserEmail).param("password", testUserPassword))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return (String)JsonPath.read(contentAsString, "$.uuid");
    }
}