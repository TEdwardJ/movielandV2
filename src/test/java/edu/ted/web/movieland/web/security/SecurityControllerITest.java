package edu.ted.web.movieland.web.security;

import com.jayway.jsonpath.JsonPath;
import edu.ted.web.movieland.annotation.FullSpringMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FullSpringMvcTest
class passwordSecurityControllerITest {

    @Value("${testUser.email}")
    private String email;
    private String testUserPassword;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @Autowired
    public void setTestUserPassword(String testUserPassword) {
        this.testUserPassword = testUserPassword;
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void givenExistingUserEmailAndPassword_whenSessionUUIDisReturned_thenCorrect() throws Exception {
        String loginRequestBodyJson = getLoginRequestBody(email, testUserPassword);
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestBodyJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(jsonPath("$.token", not(empty())))
                .andExpect(jsonPath("$.username", equalTo("test_user@gmail.com")));
    }

    private String getLoginRequestBody(String email, String testUserPassword) throws JsonProcessingException {
        var loginRequest = Map.of("email", email, "password", testUserPassword);
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        return json;
    }

    @Test
    void givenNonExistingUserEmailAndPassword_whenSessionUUIDisReturned_thenCorrect() throws Exception {
        String loginRequestBodyJson = getLoginRequestBody(email + "12", testUserPassword + "12");
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestBodyJson))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenRandomUUID_whenBadRequest_thenCorrect() throws Exception {
        String uuid = UUID.randomUUID().toString();
        mockMvc.perform(delete("/logout").param("uuid", uuid))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenExistingSessionUUID_whenStatusIsOK_thenCorrect() throws Exception {
        String loginRequestBodyJson = getLoginRequestBody(email,  testUserPassword);
        var contentAsString = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestBodyJson))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String tokenReturned = JsonPath.read(contentAsString, "$.token");
        mockMvc.perform(delete("/logout").param("token", tokenReturned))
                .andExpect(status().isOk());
    }
}
