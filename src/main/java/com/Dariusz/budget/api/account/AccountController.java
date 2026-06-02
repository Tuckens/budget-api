package com.Dariusz.budget.api.account;


import com.Dariusz.budget.api.account.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor

public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> create (@Valid @RequestBody CreateAccountRequest request) {
        Account saved = accountService.create(request);
        URI location = URI.create("/api/accounts/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

}
