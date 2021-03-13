package edu.ted.web.movieland.web.security;

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

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FullSpringMvcTest
class SecurityControllerITest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void givenExistingUserEmailAndPassword_whenSessionUUIDisReturned_thenCorrect() throws Exception {
        mockMvc.perform(post("/login").param("email","dennis.craig82@example.com").param("password","coldbeer"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.uuid", not(empty())))
                .andExpect(jsonPath("$.nickname", not(empty())))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(jsonPath("$.nickname", equalTo("Деннис Крейг")));
    }

    @Test
    void givenRandomUUID_whenBadRequest_thenCorrect() throws Exception {
        String uuid = UUID.randomUUID().toString();
        mockMvc.perform(delete("/logout").param("uuid", uuid))
        .andExpect(status().isBadRequest());
    }

    @Test
    void givenExistingSessionUUID_whenStatusIsOK_thenCorrect() throws Exception {
        String uuid = UUID.randomUUID().toString();
        var contentAsString = mockMvc.perform(post("/login").param("email", "dennis.craig82@example.com").param("password", "coldbeer"))
                .andReturn().getResponse().getContentAsString();
        UserToken responseObject = new ObjectMapper().readValue(contentAsString, new TypeReference<UserToken>(){});
        mockMvc.perform(delete("/logout").param("uuid", responseObject.getUuid().toString()))
                .andExpect(status().isOk());
    }
}
