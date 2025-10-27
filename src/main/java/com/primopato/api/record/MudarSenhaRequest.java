package com.primopato.api.record;

import jakarta.validation.constraints.NotBlank;

public record MudarSenhaRequest(
        @NotBlank(message = "A senha atual é obrigatória")
        String senhaAtual,
        @NotBlank(message = "A senha nova é obrigatória")
        String senhaNova) {}
