package com.Dariusz.budget.api.transaction;

import com.Dariusz.budget.api.account.Account;
import com.Dariusz.budget.api.account.AccountRepository;
import com.Dariusz.budget.api.transaction.dto.CreateTransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    private Long accountId;

    @BeforeEach
    void setUp() {
        Account account = new Account();
        account.setName("Testowe konto");
        account.setBalance(BigDecimal.ZERO);
        account = accountRepository.save(account);
        accountId = account.getId();
    }

    @Test
    void shouldCreateIncomeAndIncreaseBalance() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAmount(new BigDecimal("200.00"));
        request.setType(Transaction.TransactionType.INCOME);
        request.setCategory("Wynagrodzenie");
        request.setDescription("Pensja");
        request.setAccountId(accountId);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value("200.0"))
                .andExpect(jsonPath("$.type").value("INCOME"));
    }

    @Test
    void shouldDeleteTransactionAndRevertBalance() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAmount(new BigDecimal("100.00"));
        request.setType(Transaction.TransactionType.INCOME);
        request.setCategory("Test");
        request.setDescription("Opis");
        request.setAccountId(accountId);

        String response = mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long transactionId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/transactions/" + transactionId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/accounts/" + accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(0));
    }

    @Test
    void shouldReturn404ForNonExistingTransaction() throws Exception {
        mockMvc.perform(get("/api/transactions/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Nie znaleziono zasobu"));
    }

    @Test
    void shouldReturnSummaryWithCorrectTotals() throws Exception {
        CreateTransactionRequest income = new CreateTransactionRequest();
        income.setAmount(new BigDecimal("300.00"));
        income.setType(Transaction.TransactionType.INCOME);
        income.setCategory("Wynagrodzenie");
        income.setDescription("Pensja");
        income.setAccountId(accountId);
        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(income)));

        CreateTransactionRequest expense = new CreateTransactionRequest();
        expense.setAmount(new BigDecimal("50.00"));
        expense.setType(Transaction.TransactionType.EXPENSE);
        expense.setCategory("Jedzenie");
        expense.setDescription("Obiad");
        expense.setAccountId(accountId);
        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expense)));

        mockMvc.perform(get("/api/transactions/summary")
                        .param("accountId", accountId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIncome").value("300.0"))
                .andExpect(jsonPath("$.totalExpense").value("50.0"))
                .andExpect(jsonPath("$.expensesByCategory.Jedzenie").value("50.0"));
    }
}