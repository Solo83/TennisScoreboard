package com.solo83.tennisscoreboard.dto;

import lombok.Data;

@Data
public class GetPlayerRequestDTO {
    private final String name;

    public GetPlayerRequestDTO(String name) {
        this.name = name;
    }

}
