package com.company.SummativeProject1.controllers;

import com.company.SummativeProject1.controller.MagicController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MagicController.class)
public class MagicControllerTests {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnAnswerAndQuestionWhenQuestionProvided() throws Exception {
        Map<String, String> questionMap = new HashMap<>();
        questionMap.put("question", "Test Question");

        String requestBody = mapper.writeValueAsString(questionMap);

        mockMvc.perform(post("/magic")
                        .contentType("application/json")
                        .content(requestBody))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.question").value("Test Question"))
                .andExpect(jsonPath("$.answer").exists());
    }

    @Test
    public void shouldReturnAnswerEvenWithoutQuestion() throws Exception {
        mockMvc.perform(post("/magic")
            .contentType("application/json")
            .content(""))

            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.question").doesNotExist())
            .andExpect(jsonPath("$.answer").exists());
    }

    @Test
    public void shouldReturnAnswerEvenWithoutRequestBody() throws Exception {
        mockMvc.perform(post("/magic")
            .contentType("application/json"))

            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.question").doesNotExist())
            .andExpect(jsonPath("$.answer").exists());
    }
}