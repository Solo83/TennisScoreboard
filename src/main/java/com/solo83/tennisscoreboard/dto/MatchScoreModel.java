package com.solo83.tennisscoreboard.dto;

import com.solo83.tennisscoreboard.entity.Match;
import lombok.Data;

@Data
public class MatchScoreModel {
    private Match match;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
    private boolean isDraw;
    private boolean isTieBreak;
}
