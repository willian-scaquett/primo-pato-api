package com.primopato.api.repository;

import com.primopato.api.entity.FabricanteDrone;
import com.primopato.api.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FabricanteDroneRepository extends JpaRepository<FabricanteDrone, Long> {

    Optional<FabricanteDrone> findByNomeAndPais(String nome, Pais pais);
}
