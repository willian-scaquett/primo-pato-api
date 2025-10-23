package com.primopato.api.record;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.primopato.api.entity.Pato;
import com.primopato.api.utils.Float2CasasSerializer;

public record PatoResponse(
        Long id,
        String numeroSerieDrone,
        String modeloDrone,
        String fabricanteDrone,
        String paisDrone,
        @JsonSerialize(using = Float2CasasSerializer.class)
        float altura,
        @JsonSerialize(using = Float2CasasSerializer.class)
        float peso,
        double latitude,
        double longitude,
        String pais,
        String estado,
        String cidade,
        String pontoReferencia,
        String endereco,
        @JsonSerialize(using = Float2CasasSerializer.class)
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
                pato.getLocalizacao().getCidade().getEstado().getPais().getNome(),
                pato.getLocalizacao().getCidade().getEstado().getNome(),
                pato.getLocalizacao().getCidade().getNome(),
                pato.getLocalizacao().getPontoReferencia(),
                pato.getLocalizacao().getEndereco(),
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