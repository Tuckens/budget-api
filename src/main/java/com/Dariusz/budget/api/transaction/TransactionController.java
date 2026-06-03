package com.Dariusz.budget.api.transaction;


import com.Dariusz.budget.api.transaction.dto.CreateTransactionRequest;
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


    @PostMapping
    public ResponseEntity<Transaction> create (@Valid @RequestBody CreateTransactionRequest request){
        Transaction saved = transactionService.create(request);
        URI location = URI.create("/api/transactions/" + saved.getId());
        return ResponseEntity.created(location).body(saved);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById (@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();

    }


    @GetMapping
    public ResponseEntity<List<Transaction>> getAll (
        @RequestParam(required = false) String category,
        @RequestParam(required = false) LocalDate from,
        @RequestParam(required = false) LocalDate to) {

        List<Transaction> transactions = transactionService.getAllWithFilters(category, from, to);
        return ResponseEntity.ok(transactions);
    }
}
