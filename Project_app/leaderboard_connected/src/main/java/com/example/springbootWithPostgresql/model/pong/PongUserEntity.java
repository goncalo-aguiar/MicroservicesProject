package com.example.springbootWithPostgresql.model.pong;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class PongUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "best_score")
    private Integer bestScore;
    @Column(name = "username")
    private String username;
    public PongUserEntity(){}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}

    public Integer getBestScore() {
        return bestScore;
    }

    public void setBestScore(Integer bestScore) {
        this.bestScore = bestScore;
    }
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username=" + username +
                ", bestScore=" + bestScore +
                '}';
    }
}
