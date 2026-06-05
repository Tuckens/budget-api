package com.Dariusz.budget.api.account;

import com.Dariusz.budget.api.account.dto.CreateAccountRequest;
import com.Dariusz.budget.api.common.ApiError400;
import com.Dariusz.budget.api.common.ApiError404;
import com.Dariusz.budget.api.common.ApiError409;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Utwórz nowe konto")
    @ApiResponse(responseCode = "201", description = "Konto utworzone",
            content = @Content(schema = @Schema(implementation = Account.class)))
    @ApiError400
    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody CreateAccountRequest request) {
        Account saved = accountService.create(request);
        URI location = URI.create("/api/accounts/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Pobierz konto po ID")
    @ApiResponse(responseCode = "200", description = "Szczegóły konta",
            content = @Content(schema = @Schema(implementation = Account.class)))
    @ApiError404
    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @Operation(summary = "Lista wszystkich kont")
    @ApiResponse(responseCode = "200", description = "Lista kont (może być pusta)")
    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @Operation(summary = "Usuń konto (tylko gdy nie ma transakcji)")
    @ApiResponse(responseCode = "204", description = "Konto usunięte")
    @ApiError404
    @ApiError409
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}