package com.solo83.tennisscoreboard.dto;

import lombok.Data;

@Data
public class Pageable {
    private final int RESULTS_PER_PAGE = 3;
    private int pageNumber;

    public int getSkipCount() {
        return (pageNumber - 1) * RESULTS_PER_PAGE;
    }

    public int getPagesQuantity(int numberOfRecords) {
        return (int)Math.ceil(numberOfRecords * 1.0 / RESULTS_PER_PAGE);
    }

}
