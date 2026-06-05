package com.Dariusz.budget.api.service;

import com.Dariusz.budget.api.repository.AccountRepository;
import com.Dariusz.budget.api.dto.CreateAccountRequest;
import com.Dariusz.budget.api.exception.AccountHasTransactionsException;
import com.Dariusz.budget.api.exception.ResourceNotFoundException;
import com.Dariusz.budget.api.models.Account;
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

    @Transactional
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Konto o id " + id + " nie istnieje"));

        if (accountRepository.hasTransactions(id)) {
            throw new AccountHasTransactionsException("Nie mozna usunuąć konta z transakcjami!");
        }
        accountRepository.delete(account);

    }
}
