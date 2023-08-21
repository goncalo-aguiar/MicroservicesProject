package com.example.springbootWithPostgresql.model.tictactoe;


import javax.persistence.*;

@Entity
@Table(name = "players")
public class TicTacToeUserEntity {

    @Id
    @Column(name = "login")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String login;

    @Column(name = "games_played")
    private Integer gamesPlayed;

    @Column(name = "games_won")
    private Integer gamesWon;

    public TicTacToeUserEntity() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    @Override
    public String toString() {
        return "Player{" +
                "login=" + login +
                ", gamesPlayed=" + gamesPlayed +
                ", gamesWon=" + gamesWon +
                '}';
    }




}
