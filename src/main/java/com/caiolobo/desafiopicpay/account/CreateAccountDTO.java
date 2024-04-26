package com.caiolobo.desafiopicpay.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateAccountDTO(Long id,
                               @NotBlank String name,
                               @Pattern(regexp = "^\\d{11}|\\d{14}$", message = "CPF ou CNPJ inválido") String document,
                               @Email String email,
                               @NotBlank String password) {
}
