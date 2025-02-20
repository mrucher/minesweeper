package org.example.minesweeper.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Turn {
    private UUID game_id;
    private int col;
    private int row;
}
