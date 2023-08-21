package com.example.domain;

public class UserScoreResponse {    private Long id;
    private int bestScore;

    public UserScoreResponse(Long id, int bestScore) {
        this.id = id;
        this.bestScore = bestScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

}
