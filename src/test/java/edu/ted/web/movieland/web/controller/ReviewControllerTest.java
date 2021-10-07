package edu.ted.web.movieland.web.controller;

import com.jayway.jsonpath.JsonPath;
import edu.ted.web.movieland.annotation.FullSpringMvcTest;
import edu.ted.web.movieland.common.SecurityConstants;
import edu.ted.web.movieland.configuration.SecurityConfiguration;
import edu.ted.web.movieland.entity.Review;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.Filter;

import java.util.Map;

import static edu.ted.web.movieland.common.SecurityConstants.AUTHORIZATION_HEADER_NAME;
import static edu.ted.web.movieland.common.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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

    @Autowired
    private Filter reviewSecurityFilter;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                //.addFilters(reviewSecurityFilter)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithAnonymousUser
    void givenNewReviewWithNoToken_whenNotAuthorizedCode_thenCorrect() throws Exception {
        String json = getReviewJson();
        mockMvc.perform(post("/review")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(unauthenticated())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void givenNewReviewWithExistingToken_whenOKCode_thenCorrect() throws Exception {
        String uuid = sendAuthorizeRequest();
        String json = getReviewJson();
        mockMvc.perform(post("/review")
                        .header(AUTHORIZATION_HEADER_NAME, TOKEN_PREFIX + uuid)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    private String getReviewJson() throws JsonProcessingException {
        var review = new Review(105, "test");

        return new ObjectMapper().writeValueAsString(review);
    }

    private String sendAuthorizeRequest() throws Exception {
        var loginRequest = Map.of("email", testUserEmail, "password", testUserPassword);
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        var contentAsString = mockMvc.perform(post("/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return JsonPath.read(contentAsString, "$.token");
    }
}