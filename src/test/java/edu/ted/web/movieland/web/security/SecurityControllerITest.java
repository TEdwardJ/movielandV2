package edu.ted.web.movieland.web.security;

import com.jayway.jsonpath.JsonPath;
import edu.ted.web.movieland.annotation.FullSpringMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FullSpringMvcTest
class SecurityControllerITest {

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
        mockMvc.perform(post("/login").param("email", email).param("password", testUserPassword))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(jsonPath("$.uuid", not(empty())))
                .andExpect(jsonPath("$.nickname", equalTo("testUser")));
    }

    @Test
    void givenNonExistingUserEmailAndPassword_whenSessionUUIDisReturned_thenCorrect() throws Exception {
        mockMvc.perform(post("/login")
                    .param("email", email + "12")
                    .param("password", testUserPassword + "12"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenRandomUUID_whenBadRequest_thenCorrect() throws Exception {
        String uuid = UUID.randomUUID().toString();
        mockMvc.perform(delete("/logout").param("uuid", uuid))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenExistingSessionUUID_whenStatusIsOK_thenCorrect() throws Exception {
        var contentAsString = mockMvc.perform(post("/login").param("email", email).param("password", testUserPassword))
                .andReturn().getResponse().getContentAsString();
        String uuidReturned = JsonPath.read(contentAsString, "$.uuid");
        mockMvc.perform(delete("/logout").param("uuid", uuidReturned))
                .andExpect(status().isOk());
    }
}
