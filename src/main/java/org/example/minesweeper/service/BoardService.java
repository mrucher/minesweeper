package org.example.minesweeper.service;

import org.example.minesweeper.model.Board;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BoardService {
    public Board newBoard(int width, int height, int mines_count) {
        validateBoardParams(width, height, mines_count);
        return createBoard(width, height, mines_count);
    }

    public Board makeTurn(Board board, int col, int row) {
        boolean[][] visited = board.getVisited();
        int[][] neighborCount = board.getNeighborCount();
        int openedCells = board.getOpenedCells();

        validateTurnParams(visited, col, row);

        if (neighborCount[row][col] == -1) {
            board.setField(calculateField(visited, neighborCount, true, true));
            board.setOpenedCells(-1);
            return board;
        } else {
            openedCells += updateVisited(col, row, visited, neighborCount, 0);
            if (openedCells == board.getWidth() * board.getHeight() - board.getMinesCount()) {
                board.setField(calculateField(visited, neighborCount, false, true));
            } else {
                board.setField(calculateField(visited, neighborCount, false, false));
            }
            board.setVisited(visited);
            board.setOpenedCells(openedCells);
        }
        return board;
    }

    private String[][] calculateField(boolean[][] visited, int[][] neighborCount, boolean isLoose, boolean isEnd) {
        String[][] result = new String[neighborCount.length][neighborCount[0].length];
        for (int i = 0; i < neighborCount.length; i++) {
            for (int j = 0; j < neighborCount[0].length; j++) {
                if (visited[i][j] || isEnd) {
                    result[i][j] = updateFieldCeil(neighborCount[i][j], isLoose);
                } else {
                    result[i][j] = " ";
                }
            }
        }
        return result;
    }

    private int updateVisited(int col, int row, boolean[][] visited, int[][] neighborCount, int count) {
        if (!visited[row][col]) {
            visited[row][col] = true;
            int result = 1;
            if (neighborCount[row][col] == 0) {
                int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
                int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};


                for (int d = 0; d < 8; d++) {
                    int x = col + dx[d];
                    int y = row + dy[d];

                    if (x >= 0 && x < neighborCount[0].length && y >= 0 && y < neighborCount.length ) {
                        result += updateVisited(x, y, visited, neighborCount, count + 1);
                    }
                }
            }
            return result;
        } else {
            return 0;
        }
    }


    private String updateFieldCeil(int value, boolean isLoose) {
        if (value == -1) {
            return isLoose ? "X" : "M";
        } else {
            return String.valueOf(value);
        }
    }



    private Board createBoard(int width, int height, int minesCount) {
        Board board = new Board();
        board.setWidth(width);
        board.setHeight(height);
        board.setMinesCount(minesCount);
        board.setVisited(new boolean[height][width]);
        board.setField(createEmptyField(width, height));
        board.setNeighborCount(createMinesAndCalculateNeighborCount(width, height, minesCount));
        board.setOpenedCells(0);
        return board;
    }

    private int[][] createMinesAndCalculateNeighborCount(int width, int height, int minesCount) {
        int[][] neighborCount = new int[height][width];

        //Creating mines with Random
        int k = minesCount;
        Random random = new Random();
        while (k > 0) {
            int col = random.nextInt(width);
            int row = random.nextInt(height);

            if (neighborCount[row][col] == 0) {
                neighborCount[row][col] = -1;
                k--;
            }
        }

        //Calculate
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (neighborCount[i][j] == 0) {
                    int count = 0;
                    for (int d = 0; d < 8; d++) {
                        int x = i + dx[d];
                        int y = j + dy[d];

                        if (x >= 0 && x < height && y >= 0 && y < width && neighborCount[x][y] == -1) {
                            count++;
                        }
                    }
                    neighborCount[i][j] = count;
                }
            }
        }

        return neighborCount;
    }

    private String[][] createEmptyField(int width, int height) {
        String[][] field = new String[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                field[x][y] = " ";
            }
        }
        return field;
    }

    private void validateBoardParams(int width, int height, int mines_count) {
        if (width < 2 || width > 30 ) {
            throw new IllegalArgumentException("Ширина поля должна быть не менее 2 и не более 30");
        }
        if (height < 2 || height > 30 ) {
            throw new IllegalArgumentException("Высота поля должна быть не менее 2 и не более 30");
        }
        if (mines_count < 1 || mines_count > width * height - 1) {
            throw new IllegalArgumentException("Количество мин должно быть не менее 1 и не более " + (width * height - 1));
        }
    }

    private void validateTurnParams(boolean[][] visited, int col, int row) {
        if (visited[row][col]) {
            throw new IllegalArgumentException("Ячейка уже открыта");
        }
    }


}
