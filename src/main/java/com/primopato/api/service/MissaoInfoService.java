package com.primopato.api.service;

import com.primopato.api.entity.MissaoInfo;
import com.primopato.api.record.MissaoInfoResponse;
import com.primopato.api.service.assembler.MissaoInfoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MissaoInfoService {

    private final MissaoInfoAssembler missaoInfoAssembler;

    public MissaoInfo getMissaoInfo(Long idPato, String usuario) {
        return missaoInfoAssembler.obterOuCriarMissaoInfo(idPato, usuario);
    }

    public MissaoInfoResponse montarMissaoInfoResponse(Long idPato, String usuario) {
        MissaoInfo missaoInfo = getMissaoInfo(idPato, usuario);

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
