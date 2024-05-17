package com.solo83.tennisscoreboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
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

}
