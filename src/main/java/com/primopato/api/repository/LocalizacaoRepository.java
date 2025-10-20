package com.primopato.api.repository;

import com.primopato.api.entity.Coordenadas;
import com.primopato.api.entity.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {

    Optional<Localizacao> findByCoordenadas(Coordenadas coordenadas);
}
