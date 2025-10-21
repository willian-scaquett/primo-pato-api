package com.primopato.api.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.primopato.api.entity.Pato;

public record PatoResponse(
        Long id,
        String numeroSerieDrone,
        String modeloDrone,
        String fabricanteDrone,
        String paisDrone,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        float altura,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        float peso,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        double latitude,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        double longitude,
        String pais,
        String estado,
        String cidade,
        String pontoReferencia,
        String endereco,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
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