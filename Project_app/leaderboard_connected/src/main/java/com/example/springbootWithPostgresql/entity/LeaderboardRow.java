package com.example.springbootWithPostgresql.entity;

public class LeaderboardRow {

    private  Integer gamesPlayed;
    private Long login;
    private Integer gamesWon;

    private String username;
    private Integer bestScore;

    public LeaderboardRow(Long login, Integer gamesWon,Integer gamesPlayed, String username, Integer bestScore) {
        this.login = login;
        this.gamesWon = gamesWon;
        this.gamesPlayed = gamesPlayed;
        this.username = username;
        this.bestScore=bestScore;


    }

    public Long getLogin() {
        return login;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public Integer getBestScore() {
        return bestScore;
    }


    public int getScore() {
        if (gamesPlayed == null || gamesWon == null || gamesPlayed == 0) {
            return 0;
        }
        return (int) Math.round((double) gamesWon / gamesPlayed * 100);
    }
}
