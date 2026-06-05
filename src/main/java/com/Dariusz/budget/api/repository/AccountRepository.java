package com.Dariusz.budget.api.repository;

import com.Dariusz.budget.api.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT COUNT(t) > 0 FROM Transaction t WHERE t.account.id = :accountId ")
    boolean hasTransactions(@Param("accountId") Long accountId);
}
