package com.primopato.api.repository;

import com.primopato.api.entity.Drone;
import com.primopato.api.entity.ModeloDrone;
import com.primopato.api.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    Optional<Drone> findByNumeroSerieAndModelo(String nome, ModeloDrone modeloDrone);

    List<Drone> findAllByModelo_Id(Long idModelo);
}
