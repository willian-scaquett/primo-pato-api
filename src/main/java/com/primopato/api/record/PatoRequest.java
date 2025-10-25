package com.primopato.api.record;

import com.primopato.api.entity.Pato;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.utils.LocalizacaoUtils;
import com.primopato.api.utils.UnidadesUtils;
import jakarta.validation.constraints.*;

public record PatoRequest(
        @NotBlank(message = "numeroSerieDrone é obrigatório")
        String numeroSerieDrone,
        @NotBlank(message = "modeloDrone é obrigatório")
        String modeloDrone,
        @NotBlank(message = "fabricanteDrone é obrigatório")
        String fabricanteDrone,
        @NotBlank(message = "paisDrone é obrigatório")
        String paisDrone,
        @Positive(message = "altura deve ser maior que zero")
        float altura,
        @Positive(message = "peso deve ser maior que zero")
        float peso,
        @Min(value = -90, message = "latitude mínima é -90")
        @Max(value = 90, message = "latitude máxima é 90")
        double latitude,
        @Min(value = -180, message = "longitude mínima é -180")
        @Max(value = 180, message = "longitude máxima é 180")
        double longitude,
        String pais,
        String estado,
        String cidade,
        String pontoReferencia,
        String endereco,
        @DecimalMin(value = "0.03", message = "precisão mínima é 4cm")
        @DecimalMax(value = "30", message = "precisão máxima é 30m")
        float precisao,
        @NotNull(message = "estadoHibernacao é obrigatório")
        EstadoHibernacao estadoHibernacao,
        Integer bpm,
        @PositiveOrZero(message = "quantidadeMutacoes não pode ser negativo")
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
                LocalizacaoUtils.EUA.equals(pato.getDroneQueEncontrou().getModelo().getFabricante().getPais())
                        ? UnidadesUtils.centimetroParaPe(pato.getAltura())
                        : pato.getAltura(),
                LocalizacaoUtils.EUA.equals(pato.getDroneQueEncontrou().getModelo().getFabricante().getPais())
                        ? UnidadesUtils.gramaParaLibra(pato.getPeso())
                        : pato.getPeso(),
                pato.getLocalizacao().getCoordenadas().getLatitude(),
                pato.getLocalizacao().getCoordenadas().getLongitude(),
                pato.getLocalizacao().getCidade().getEstado().getPais().getNome(),
                pato.getLocalizacao().getCidade().getEstado().getNome(),
                pato.getLocalizacao().getCidade().getNome(),
                pato.getLocalizacao().getPontoReferencia(),
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