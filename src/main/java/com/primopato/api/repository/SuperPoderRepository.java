package com.primopato.api.repository;

import com.primopato.api.entity.SuperPoder;
import com.primopato.api.enumerated.TipoSuperPoder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperPoderRepository extends JpaRepository<SuperPoder, Long> {

    Optional<SuperPoder> findByNomeAndTipo(String nome, TipoSuperPoder tipoSuperPoder);
}
