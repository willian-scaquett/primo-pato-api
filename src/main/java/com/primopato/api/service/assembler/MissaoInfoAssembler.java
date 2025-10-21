package com.primopato.api.service.assembler;

import com.primopato.api.entity.Cidade;
import com.primopato.api.entity.Estado;
import com.primopato.api.entity.Pato;
import com.primopato.api.repository.CidadeRepository;
import com.primopato.api.repository.MissaoInfoRepository;
import com.primopato.api.service.PatoService;
import com.primopato.api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MissaoInfoAssembler {

    private final MissaoInfoRepository missaoInfoRepository;
    private final PatoService patoService;

    public void obterOuCriarMissaoInfo(Long idPato) {
        Pato pato = patoService.getPato(idPato);


    }
}
