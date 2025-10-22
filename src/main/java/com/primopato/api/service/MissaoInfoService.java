package com.primopato.api.service;

import com.primopato.api.entity.MissaoInfo;
import com.primopato.api.entity.Pato;
import com.primopato.api.record.MissaoInfoResponse;
import com.primopato.api.repository.MissaoInfoRepository;
import com.primopato.api.service.assembler.MissaoInfoAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MissaoInfoService {

    private final PatoService patoService;
    private final MissaoInfoAssembler missaoInfoAssembler;
    private final MissaoInfoRepository missaoInfoRepository;

    public MissaoInfo obterOuCriarMissaoInfo(Long idPato, String usuario) {
        Pato pato = patoService.getPato(idPato, usuario);

        return missaoInfoRepository
                .findByPatoAndPato_Usuario_usuario(pato, usuario)
                .orElseGet(() -> {
                    log.info("Informação de missão não encontrada. Criando nova para pato ID: {}", idPato);
                    return missaoInfoRepository.save(
                        missaoInfoAssembler.montarMissaoInfo(pato, null)
                    );
                });
    }

    public MissaoInfoResponse montarMissaoInfoResponse(Long idPato, String usuario) {
        MissaoInfo missaoInfo = obterOuCriarMissaoInfo(idPato, usuario);

        double gastoCombustivelIda = missaoInfo.getGastoCombustivelIda();
        double gastoCombustivelVolta = missaoInfo.getGastoCombustivelVolta();
        double gastoCombustivelTotal = gastoCombustivelIda + gastoCombustivelVolta;

        return new MissaoInfoResponse(
                missaoInfo.getPato().getId(),
                missaoInfo.getDefesaDrone().getNome(),
                missaoInfo.getDistancia(),
                gastoCombustivelIda,
                gastoCombustivelVolta,
                gastoCombustivelTotal,
                missaoInfo.getRisco(),
                missaoInfo.getGanhoCientifico(),
                missaoInfo.getGanhoParanormal(),
                missaoInfo.getArmaDrone().getNome(),
                missaoInfo.getAbordagem().getNome(),
                missaoInfo.getTamanhoRede().getNome(),
                missaoInfo.getCusto(gastoCombustivelTotal)
        );
    }
}
