package com.example.DTO;

import java.util.List;

public class LeaderboardDTO {

    private List<UserScoreDTO> ticTacToeLeaderboard;
    private List<UserScoreDTO> pongLeaderboard;

    public List<UserScoreDTO> getPongLeaderboard() {
        return pongLeaderboard;
    }

    public void setPongLeaderboard(List<UserScoreDTO> pongLeaderboard) {
        this.pongLeaderboard = pongLeaderboard;
    }

    public List<UserScoreDTO> getTicTacToeLeaderboard() {
        return ticTacToeLeaderboard;
    }

    public void setTicTacToeLeaderboard(List<UserScoreDTO> ticTacToeLeaderboard) {
        this.ticTacToeLeaderboard = ticTacToeLeaderboard;
    }
}
