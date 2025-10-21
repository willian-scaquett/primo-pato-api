package com.primopato.api.record;

import com.primopato.api.entity.Pato;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;

public record PatoResponse(
        Long id,
        String numeroSerieDrone,
        String modeloDrone,
        String fabricanteDrone,
        String paisDrone,
        float altura,
        float peso,
        double latitude,
        double longitude,
        String endereco,
        String pontoReferencia,
        float precisao,
        String estadoHibernacao,
        Integer bpm,
        int quantidadeMutacoes,
        String nomeSuperPoder,
        String tipoSuperPoder,
        boolean capturado
) {
    public PatoResponse(Pato pato) {
        this(
                pato.getId(),
                pato.getDroneQueEncontrou().getNumeroSerie(),
                pato.getDroneQueEncontrou().getModelo().getNome(),
                pato.getDroneQueEncontrou().getModelo().getFabricante().getNome(),
                pato.getDroneQueEncontrou().getModelo().getFabricante().getPais().getNome(),
                pato.getAltura(),
                pato.getPeso(),
                pato.getLocalizacao().getCoordenadas().getLatitude(),
                pato.getLocalizacao().getCoordenadas().getLongitude(),
                pato.getLocalizacao().getEndereco(),
                pato.getLocalizacao().getPontoReferencia(),
                pato.getPrecisaoDoGpsQuandoEncontrado(),
                pato.getEstadoHibernacao().getNome(),
                pato.getBpm(),
                pato.getQuantidadeMutacoes(),
                pato.getSuperPoder() != null ? pato.getSuperPoder().getNome() : null,
                pato.getSuperPoder() != null ? pato.getSuperPoder().getTipo().getNome() : null,
                pato.isCapturado()
        );
    }
}
