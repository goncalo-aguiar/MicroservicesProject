package com.example.springbootWithPostgresql.dao.pong;

import com.example.springbootWithPostgresql.model.pong.PongUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PongUserRepository extends JpaRepository<PongUserEntity, Long> {

    List<PongUserEntity> findAllByOrderByBestScoreDesc();

}
