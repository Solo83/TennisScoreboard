package com.solo83.tennisscoreboard.dto;

public class GetPlayerRequestDTO {
    private final String name;

    public GetPlayerRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
