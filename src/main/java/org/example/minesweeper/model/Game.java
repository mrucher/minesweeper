package org.example.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Game {
    private UUID game_id;
    private int width;
    private int height;
    private int mines_count;
    private boolean completed;
    private String[][] field;

    @JsonIgnore
    private Board board;
}
