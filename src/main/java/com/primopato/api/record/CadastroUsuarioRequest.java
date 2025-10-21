package com.primopato.api.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroUsuarioRequest(
        @NotBlank(message = "usuario é obrigatório")
        String usuario,

        @NotBlank(message = "senha é obrigatório")
        @Size(min = 6, message = "senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "nome é obrigatório")
        String nome
) {}