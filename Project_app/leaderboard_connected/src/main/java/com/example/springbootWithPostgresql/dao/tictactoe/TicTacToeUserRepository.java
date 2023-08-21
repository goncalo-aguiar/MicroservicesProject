package com.example.springbootWithPostgresql.dao.tictactoe;

import com.example.springbootWithPostgresql.model.tictactoe.TicTacToeUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TicTacToeUserRepository extends JpaRepository<TicTacToeUserEntity, Long> {

    List<TicTacToeUserEntity> findAllByOrderByGamesWonDesc();

}
