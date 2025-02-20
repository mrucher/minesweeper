package org.example.minesweeper.service;

import lombok.RequiredArgsConstructor;
import org.example.minesweeper.model.Board;
import org.example.minesweeper.model.Game;
import org.example.minesweeper.model.GameCreateParameters;
import org.example.minesweeper.model.Turn;
import org.example.minesweeper.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final BoardService boardService;

    public Game createNewGame(GameCreateParameters createParameters) {
        Game game = new Game();
        game.setGame_id(UUID.randomUUID());
        game.setCompleted(false);
        game.setWidth(createParameters.getWidth());
        game.setHeight(createParameters.getHeight());
        game.setMines_count(createParameters.getMines_count());
        game.setBoard(boardService.newBoard(
                createParameters.getWidth(),
                createParameters.getHeight(),
                createParameters.getMines_count()));
        game.setField(game.getBoard().getField());

        gameRepository.addGame(game);


        return game;
    }

    public Game makeTurn(Turn turn) {
        if (gameRepository.getGame(turn.getGame_id()).isPresent()) {
            Game game = gameRepository.getGame(turn.getGame_id()).get();
            if (game.isCompleted()) {
                throw new IllegalArgumentException("Игра уже завершена");
            }
            game.setBoard(boardService.makeTurn(game.getBoard(), turn.getCol(), turn.getRow()));
            game.setField(game.getBoard().getField());

            if (game.getBoard().getOpenedCells() == -1 || game.getBoard().getOpenedCells() == game.getWidth() * game.getHeight() - game.getMines_count()) {
                game.setCompleted(true);
            }
            return game;
        } else {
            throw new IllegalArgumentException("Игра с id = " + turn.getGame_id() + " не была найдена или уже не актуальна");
        }


    }


}
