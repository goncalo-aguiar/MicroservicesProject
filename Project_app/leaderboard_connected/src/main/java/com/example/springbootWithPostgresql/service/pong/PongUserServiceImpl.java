package com.example.springbootWithPostgresql.service.pong;

import com.example.springbootWithPostgresql.dao.pong.PongUserRepository;
import com.example.springbootWithPostgresql.model.pong.PongUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PongUserServiceImpl implements PongUserService{
    private final PongUserRepository pongUserRepository;

    @Autowired
    public PongUserServiceImpl(PongUserRepository pongUserRepository) {

        this.pongUserRepository = pongUserRepository;
    }



    @Transactional("pongTransactionManager")
    public List<PongUserEntity> getAllPongUser() {
        return pongUserRepository.findAllByOrderByBestScoreDesc();
    }


    @Transactional("pongTransactionManager")
    public PongUserEntity getPongUserById(Long id) {
        return pongUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }
}
