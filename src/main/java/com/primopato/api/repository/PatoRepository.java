package com.primopato.api.repository;

import com.primopato.api.entity.Pato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatoRepository extends JpaRepository<Pato, Long> {

}
