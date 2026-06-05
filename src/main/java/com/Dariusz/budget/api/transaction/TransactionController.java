package com.Dariusz.budget.api.transaction;

import com.Dariusz.budget.api.common.ApiError400;
import com.Dariusz.budget.api.common.ApiError404;
import com.Dariusz.budget.api.transaction.dto.CreateTransactionRequest;
import com.Dariusz.budget.api.transaction.dto.SummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor

public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Dodaj nową transakcję (saldo konta aktualizuje się automatycznie)")
    @ApiResponse(responseCode = "201", description = "Transakcja utworzona",
            content = @Content(schema = @Schema(implementation = Transaction.class)))
    @ApiError400
    @ApiError404
    @PostMapping
    public ResponseEntity<Transaction> create(@Valid @RequestBody CreateTransactionRequest request) {
        Transaction saved = transactionService.create(request);
        URI location = URI.create("/api/transactions/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }



    @Operation(summary = "Pobierz transakcję po ID")
    @ApiResponse(responseCode = "200", description = "Szczegóły transakcji",
            content = @Content(schema = @Schema(implementation = Transaction.class)))
    @ApiError404
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }



    @Operation(summary = "Usuń transakcję (saldo konta cofa się)")
    @ApiResponse(responseCode = "204", description = "Transakcja usunięta")
    @ApiError404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "Lista transakcji z opcjonalnymi filtrami: ?category=, ?from=, ?to=, ?accountId=")
    @ApiResponse(responseCode = "200", description = "Lista transakcji (może być pusta)")
    @GetMapping
    public ResponseEntity<List<Transaction>> getAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        List<Transaction> transactions = transactionService.getAllWithFilters(category, accountId, from, to);
        return ResponseEntity.ok(transactions);
    }



    @Operation(summary = "Podsumowanie: łączne przychody, wydatki, wydatki pogrupowane po kategorii")
    @ApiResponse(responseCode = "200", description = "Podsumowanie",
            content = @Content(
                    schema = @Schema(implementation = SummaryResponse.class),
                    examples = @ExampleObject(value = """
                            {
                              "totalIncome": 5000.00,
                              "totalExpense": 1200.00,
                              "expensesByCategory": {
                                "Jedzenie": 800.00,
                                "Transport": 400.00
                              }
                            }
                            """)
            )
    )
    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummary(
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        SummaryResponse summary = transactionService.getSummary(accountId, from, to);
        return ResponseEntity.ok(summary);
    }
}