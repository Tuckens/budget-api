package com.Dariusz.budget.api.account;

import com.Dariusz.budget.api.account.dto.CreateAccountRequest;
import com.Dariusz.budget.api.common.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class AccountService {
    private final AccountRepository accountRepository;

    public Account create (CreateAccountRequest request) {
        Account account = new Account(request.getName());
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account getById (Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Konto o id " + id + "nie istnieje"));
    }

    @Transactional(readOnly = true)
    public List<Account> getAll () {
        return accountRepository.findAll();
    }
}
