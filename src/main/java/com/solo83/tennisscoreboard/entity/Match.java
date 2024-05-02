package com.solo83.tennisscoreboard.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player1", referencedColumnName = "id")
    private Player firstPlayer;

    @ManyToOne
    @JoinColumn(name = "player2", referencedColumnName = "id")
    private Player secondPlayer;

    @ManyToOne
    @JoinColumn(name = "winner", referencedColumnName = "id")
    private Player winner;


    public Match(Integer id, Player firstPlayer, Player secondPlayer, Player winner) {
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }

    public Match() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", firstPlayer=" + firstPlayer +
                ", secondPlayer=" + secondPlayer +
                ", winner=" + winner +
                '}';
    }
}
