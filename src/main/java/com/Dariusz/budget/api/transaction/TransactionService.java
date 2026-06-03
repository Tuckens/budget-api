package com.Dariusz.budget.api.transaction;


import com.Dariusz.budget.api.account.Account;
import com.Dariusz.budget.api.account.AccountRepository;
import com.Dariusz.budget.api.common.ResourceNotFoundException;
import com.Dariusz.budget.api.transaction.dto.CreateTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

}
