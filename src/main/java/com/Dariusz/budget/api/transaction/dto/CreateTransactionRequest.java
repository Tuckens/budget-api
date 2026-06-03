package com.Dariusz.budget.api.transaction.dto;

import com.Dariusz.budget.api.transaction.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data

public class CreateTransactionRequest {
    @NotNull(message = "Kwota nie może być pusta")
    @Positive(message = "Kwota musi być większa niż 0")
    private BigDecimal amount;
    @NotNull
    private Transaction.TransactionType type;
    @NotBlank(message = "Kategoria nie może być pusta")
    private String category;
    private String description;
    private LocalDateTime date;
    @NotNull(message = "Id konta nie może być puste")
    private Long accountId;
}
