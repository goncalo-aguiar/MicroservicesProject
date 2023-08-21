package pl.dmcs.Spring6.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY strategy for auto generation
    @Column(name = "login")
    private Long login;

    @Column(name = "username")
    private String user;

    @Column(name = "games_played")
    private Integer gamesPlayed;

    @Column(name = "games_won")
    private Integer gamesWon;

    public Player() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }

    public Long getLogin() {
        return login;
    }

    public void setLogin(Long login) {
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


