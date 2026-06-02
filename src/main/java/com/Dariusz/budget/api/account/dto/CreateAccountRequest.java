package com.Dariusz.budget.api.account.dto;


import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data

public class CreateAccountRequest {
    @NotBlank(message = "Nazwa konta nie może być pusta")
    private String name;
}
