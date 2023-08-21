package com.example.springbootWithPostgresql.service.tictactoe;

import com.example.springbootWithPostgresql.model.tictactoe.TicTacToeUserEntity;

import java.util.List;

public interface ticTacToeUserService {

    List<TicTacToeUserEntity> getAllTicTacToeUser();


    TicTacToeUserEntity getTicTacToeUserById(Long userId);


}
