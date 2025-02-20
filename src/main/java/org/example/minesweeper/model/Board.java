package org.example.minesweeper.model;

import lombok.Data;

@Data
public class Board {
    private int width;
    private int height;
    private int minesCount;
    private String[][] field;
    private int[][] neighborCount;
    private boolean[][] visited;
    private int openedCells;

}
