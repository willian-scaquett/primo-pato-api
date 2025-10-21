package com.primopato.api.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroUsuarioRequest(
        @NotBlank(message = "usuario é obrigatório")
        @Size(min = 3, max = 50, message = "usuario deve ter entre 3 e 50 caracteres")
        String usuario,

        @NotBlank(message = "senha é obrigatório")
        @Size(min = 6, message = "senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "nome é obrigatório")
        String nome
) {}