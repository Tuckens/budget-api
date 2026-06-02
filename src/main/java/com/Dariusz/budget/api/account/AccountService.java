package com.Dariusz.budget.api.account;

import com.Dariusz.budget.api.account.dto.CreateAccountRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional

public class AccountService {
    private final AccountRepository accountRepository;

    public Account create (CreateAccountRequest request) {
        Account account = new Account(request.getName());
        return accountRepository.save(account);
    }
}
