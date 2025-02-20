package org.example.minesweeper.repository;

import org.example.minesweeper.model.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class GameRepository {
    private final Map<UUID, Game> games = new HashMap<>();

    public void addGame(Game game) {
        if (!games.containsKey(game.getGame_id())) {
            games.put(game.getGame_id(), game);
        } else {
            throw new IllegalArgumentException("Game already exists!");
        }
    }

    public Optional<Game> getGame(UUID game_id) {
        return Optional.ofNullable(games.get(game_id));
    }
}
