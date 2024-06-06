package com.solo83.tennisscoreboard.dto;

import com.solo83.tennisscoreboard.entity.Player;
import lombok.Data;

@Data
public class OngoingMatch {

    private Player firstPlayer;
    private Player secondPlayer;
    private Player winner;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
    private boolean isDraw;
    private boolean isTieBreak;
}
