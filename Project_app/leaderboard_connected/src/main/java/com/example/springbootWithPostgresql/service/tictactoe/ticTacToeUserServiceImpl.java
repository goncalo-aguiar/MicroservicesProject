package com.example.springbootWithPostgresql.service.tictactoe;

import com.example.springbootWithPostgresql.dao.tictactoe.TicTacToeUserRepository;
import com.example.springbootWithPostgresql.model.tictactoe.TicTacToeUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ticTacToeUserServiceImpl implements ticTacToeUserService {

    private final TicTacToeUserRepository ticTacToeUserRepository;


    @Autowired
    public ticTacToeUserServiceImpl(TicTacToeUserRepository ticTacToeUserRepository) {
        this.ticTacToeUserRepository = ticTacToeUserRepository;

    }

    @Transactional("ticTacToeTransactionManager")
    public List<TicTacToeUserEntity> getAllTicTacToeUser() {
        return ticTacToeUserRepository.findAllByOrderByGamesWonDesc();
    }



    @Transactional("ticTacToeTransactionManager")
    public TicTacToeUserEntity getTicTacToeUserById(Long userId) {
        return ticTacToeUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
    }


}

