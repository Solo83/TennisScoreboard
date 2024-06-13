package com.solo83.tennisscoreboard.dto;

import lombok.Data;

import java.util.List;

@Data
public class Page <Match>{
    private List<Match> matches;
    private int pagesQuantity;


}
