package com.example.springbootWithPostgresql.service.pong;

import com.example.springbootWithPostgresql.model.pong.PongUserEntity;

import java.util.List;

public interface PongUserService {
    List<PongUserEntity> getAllPongUser();
    PongUserEntity getPongUserById(Long id);

}
