package com.primopato.api.repository;

import com.primopato.api.entity.MissaoInfo;
import com.primopato.api.entity.Pato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MissaoInfoRepository extends JpaRepository<MissaoInfo, Long> {

    Optional<MissaoInfo> findByPatoAndPato_Usuario_usuario(Pato pato, String usuario);
}
