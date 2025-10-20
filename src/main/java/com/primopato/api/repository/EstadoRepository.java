package com.primopato.api.repository;

import com.primopato.api.entity.Estado;
import com.primopato.api.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
