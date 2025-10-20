package com.primopato.api.service.assembler;

import com.primopato.api.entity.Estado;
import com.primopato.api.entity.Pais;
import com.primopato.api.repository.EstadoRepository;
import com.primopato.api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EstadoAssembler {

    private final EstadoRepository estadoRepository;

    public Estado obterOuCriarEstado(String nome, Pais pais) {
        String nomeFormatado = StringUtils.formataIncialMaiuscula(nome);
        return pais.getEstados().stream()
                .filter(e -> e.getNome().equals(nomeFormatado))
                .findFirst()
                .orElseGet(() -> estadoRepository.save(new Estado(nomeFormatado, pais)));
    }
}
