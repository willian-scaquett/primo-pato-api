package com.primopato.api.repository;

import com.primopato.api.entity.Pato;
import com.primopato.api.record.PatoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatoRepository extends JpaRepository<Pato, Long> {

    @Query("""
    SELECT new com.primopato.api.record.PatoResponse(p)
    FROM Pato p
    LEFT JOIN FETCH p.localizacao l
    LEFT JOIN FETCH p.droneQueEncontrou d
    LEFT JOIN FETCH p.superPoder s
    WHERE
        :filtro = ''
        OR CONCAT(p.id, '') LIKE :filtro
        OR CONCAT(p.altura, '') LIKE :filtro
        OR CONCAT(p.peso, '') LIKE :filtro
        OR CONCAT(p.precisaoDoGpsQuandoEncontrado, '') LIKE :filtro
        OR CONCAT(p.estadoHibernacao, '') LIKE :filtro
        OR CONCAT(p.bpm, '') LIKE :filtro
        OR CONCAT(p.quantidadeMutacoes, '') LIKE :filtro
        OR l.endereco LIKE :filtro
        OR l.pontoReferencia LIKE :filtro
        OR CONCAT(l.coordenadas.latitude, '') LIKE :filtro
        OR CONCAT(l.coordenadas.longitude, '') LIKE :filtro
        OR l.cidade.nome LIKE :filtro
        OR l.cidade.estado.nome LIKE :filtro
        OR l.cidade.estado.pais.nome LIKE :filtro
        OR d.numeroSerie LIKE :filtro
        OR d.modelo.nome LIKE :filtro
        OR d.modelo.fabricante.nome LIKE :filtro
        OR s.nome LIKE :filtro
        OR CONCAT(s.tipo, '') LIKE :filtro
    """)
    List<PatoResponse> findAllByFiltro(@Param("filtro") String filtro);

}
