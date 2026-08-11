package com.skala.quiz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JavaQuizApplicationTests {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void registrationDuplicateCheckAndProgressStorageWork() throws Exception {
        String body = "{\"username\":\"test_rookie\",\"password\":\"secret123\"}";
        String response = mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("test_rookie"))
                .andReturn().getResponse().getContentAsString();
        JsonNode session = objectMapper.readTree(response);
        String token = session.get("accessToken").asText();

        mvc.perform(get("/api/users/check").param("username", "TEST_ROOKIE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false));

        mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict());

        mvc.perform(put("/api/progress/quiz-flow")
                        .header("X-Access-Token", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":\"2\",\"passed\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passed").value(true));

        mvc.perform(get("/api/progress").header("X-Access-Token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].questionKey").value("quiz-flow"));
    }
}
