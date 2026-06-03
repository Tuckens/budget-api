package com.Dariusz.budget.api.transaction;


import com.Dariusz.budget.api.account.Account;
import com.Dariusz.budget.api.account.AccountRepository;
import com.Dariusz.budget.api.common.ResourceNotFoundException;
import com.Dariusz.budget.api.transaction.dto.CreateTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional


public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;


    public Transaction create (CreateTransactionRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Konto o id " + request.getAccountId() + "nie istnieje"));



        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setDate(request.getDate() != null ? request.getDate() : LocalDateTime.now());
        transaction.setDescription(request.getDescription());

       if(request.getType() == Transaction.TransactionType.INCOME) {
           account.setBalance(account.getBalance().add(request.getAmount()));

           } else {
           account.setBalance(account.getBalance().subtract(request.getAmount()));
       }
       accountRepository.save(account);

       return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transakcja o id " + id + " nie istnieje"));
    }

    @Transactional
    public void deleteById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transakcja o id " + id + " nie istnieje"));

        Account account = transaction.getAccount();

        if (transaction.getType() == Transaction.TransactionType.INCOME) {
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        } else {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        }

        accountRepository.save(account);
        transactionRepository.delete(transaction);
    }


    @Transactional(readOnly = true)
    public List<Transaction> getAll() {
        return transactionRepository.findAll();

    }
}
