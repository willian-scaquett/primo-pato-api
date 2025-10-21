package com.primopato.api.record;

import com.primopato.api.entity.Pato;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;

public record PatoRequest(
        String numeroSerieDrone,
        String modeloDrone,
        String fabricanteDrone,
        String paisDrone,
        float altura,
        float peso,
        double latitude,
        double longitude,
        String endereco,
        float precisao,
        EstadoHibernacao estadoHibernacao,
        Integer bpm,
        int quantidadeMutacoes,
        String nomeSuperPoder,
        TipoSuperPoder tipoSuperPoder,
        boolean capturado
) {
    public PatoRequest(Pato pato) {
        this(
                pato.getDroneQueEncontrou().getNumeroSerie(),
                pato.getDroneQueEncontrou().getModelo().getNome(),
                pato.getDroneQueEncontrou().getModelo().getFabricante().getNome(),
                pato.getDroneQueEncontrou().getModelo().getFabricante().getPais().getNome(),
                pato.getAltura(),
                pato.getPeso(),
                pato.getLocalizacao().getCoordenadas().getLatitude(),
                pato.getLocalizacao().getCoordenadas().getLongitude(),
                pato.getLocalizacao().getEndereco(),
                pato.getPrecisaoDoGpsQuandoEncontrado(),
                pato.getEstadoHibernacao(),
                pato.getBpm(),
                pato.getQuantidadeMutacoes(),
                pato.getSuperPoder() != null ? pato.getSuperPoder().getNome() : null,
                pato.getSuperPoder() != null ? pato.getSuperPoder().getTipo() : null,
                pato.isCapturado()
        );
    }
}
