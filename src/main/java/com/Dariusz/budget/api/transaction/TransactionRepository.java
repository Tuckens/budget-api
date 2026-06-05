package com.Dariusz.budget.api.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE " +
            "(:category IS NULL OR t.category = :category) AND " +
            "(t.date >= :from AND t.date <= :to) AND " +
            "(t.date <= :to) AND " +
            "(:accountId IS NULL OR t.account.id = :accountId)")

    List<Transaction> findAllWithFilters(@Param("category") String category,
                                         @Param("from") LocalDateTime from,
                                         @Param("to") LocalDateTime to,
                                         @Param("accountId") Long accountId);
}
