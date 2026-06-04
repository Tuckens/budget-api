package com.Dariusz.budget.api.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class SummaryResponse {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private Map<String, BigDecimal> expensesByCategory;
}
