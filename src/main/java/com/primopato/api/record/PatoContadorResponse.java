package com.primopato.api.record;

public record PatoContadorResponse(
        Long quantidadeCapturado,
        Long quantidadeNaoCapturado,
        Float porcentagemGanhoCientifico,
        Float porcentagemGanhoParanormal
) {}
