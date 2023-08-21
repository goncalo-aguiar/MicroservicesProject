package com.example.springbootWithPostgresql.controller;


import com.example.springbootWithPostgresql.dao.DTO.LeaderboardDTO;
import com.example.springbootWithPostgresql.dao.DTO.UserScoreDTO;
import com.example.springbootWithPostgresql.model.pong.PongUserEntity;
import com.example.springbootWithPostgresql.model.tictactoe.TicTacToeUserEntity;
import com.example.springbootWithPostgresql.service.pong.PongUserService;
import com.example.springbootWithPostgresql.service.tictactoe.ticTacToeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class UserController {


    private ticTacToeUserService ticTacToeUserService;

    private PongUserService pongUserService;
    @Autowired
    public UserController(ticTacToeUserService ticTacToeUserService, PongUserService pongUserService) {

        this.ticTacToeUserService = ticTacToeUserService;
        this.pongUserService=pongUserService;
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<LeaderboardDTO> getLeaderboard() {
        List<UserScoreDTO> ticTacToeLeaderboard = ticTacToeUserService.getAllTicTacToeUser()
                .stream()
                .map(user -> {
                    UserScoreDTO dto = new UserScoreDTO();
                    dto.setUsername(user.getLogin());
                    dto.setScore(user.getGamesWon());
                    return dto;
                })
                .collect(Collectors.toList());
        List<UserScoreDTO> pongLeaderboard = pongUserService.getAllPongUser()
                .stream()
                .map(user -> {
                    UserScoreDTO dto = new UserScoreDTO();
                    dto.setUsername(user.getUsername());
                    dto.setScore(user.getBestScore());
                    return dto;
                })
                .collect(Collectors.toList());

        LeaderboardDTO leaderboardDTO = new LeaderboardDTO();
        leaderboardDTO.setTicTacToeLeaderboard(ticTacToeLeaderboard);
        leaderboardDTO.setPongLeaderboard(pongLeaderboard);

        return ResponseEntity.ok(leaderboardDTO);
    }

    @GetMapping("/pong")
    public List<String> getAllUsernames() {
        return pongUserService.getAllPongUser()
                .stream()
                .map(PongUserEntity::getUsername)
                .collect(Collectors.toList());
    }

    @GetMapping("/tictac")
    public List<String> getAllTicTacUsernames() {
        return ticTacToeUserService.getAllTicTacToeUser()
                .stream()
                .map(TicTacToeUserEntity::getLogin)
                .collect(Collectors.toList());
    }
}
