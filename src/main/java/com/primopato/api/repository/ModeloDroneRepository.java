package com.primopato.api.repository;

import com.primopato.api.entity.FabricanteDrone;
import com.primopato.api.entity.ModeloDrone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeloDroneRepository extends JpaRepository<ModeloDrone, Long> {

    Optional<ModeloDrone> findByNomeAndFabricante(String nome, FabricanteDrone fabricanteDrone);
}
