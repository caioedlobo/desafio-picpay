package com.caiolobo.desafiopicpay;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUsuarioDTO(Long id,
                               @NotBlank String nome,
                               @Pattern(regexp = "^\\d{11}|\\d{14}$", message = "CPF ou CNPJ inválido") String documento,
                               @Email String email,
                               @NotBlank String senha) {
}
