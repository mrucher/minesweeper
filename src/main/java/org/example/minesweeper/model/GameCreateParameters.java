package org.example.minesweeper.model;

import lombok.Data;

@Data
public class GameCreateParameters {
    private int height;
    private int width;
    private int mines_count;
}
