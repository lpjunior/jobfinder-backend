package br.senac.jobfinder.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserEdit(
        @NotEmpty(message = "Nome é obrigatório") String firstName,
        @NotEmpty(message = "Sobrenome é obrigatório") String lastName,
        @Email(message = "E-mail inválido")
        @NotEmpty(message = "E-mail é obrigatório") String email
) {}
