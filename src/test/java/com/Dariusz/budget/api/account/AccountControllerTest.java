package com.Dariusz.budget.api.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAccountAndReturn201() throws Exception {
        String json = objectMapper.writeValueAsString(Map.of("name", "Testowe konto"));

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Testowe konto"))
                .andExpect(jsonPath("$.balance").value("0"));
    }

    @Test
    void shouldReturn400WhenNameIsEmpty() throws Exception {
        String json = objectMapper.writeValueAsString(Map.of("name", ""));

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Błąd walidacji"));
    }

    @Test
    void shouldReturn404ForNonExistingAccount() throws Exception {
        mockMvc.perform(get("/api/accounts/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Nie znaleziono zasobu"));
    }

    @Test
    void shouldReturnEmptyListWhenNoAccounts() throws Exception {
        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}