package com.Dariusz.budget.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor


public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;

    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

   public Account(String name) {
       this.name = name;
   }
}
