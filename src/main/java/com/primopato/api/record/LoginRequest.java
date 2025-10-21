package com.primopato.api.record;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "usuario é obrigatório")
        String usuario,

        @NotBlank(message = "senha é obrigatória")
        String senha
) {}