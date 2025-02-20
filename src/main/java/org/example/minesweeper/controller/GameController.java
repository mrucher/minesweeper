package org.example.minesweeper.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.example.minesweeper.model.Game;
import org.example.minesweeper.model.GameCreateParameters;
import org.example.minesweeper.model.Turn;
import org.example.minesweeper.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @CrossOrigin
    @PostMapping("/new")
    public ResponseEntity<?> newGame(
            @RequestBody GameCreateParameters createParameters) {
        try {
            Game game = gameService.createNewGame(createParameters);
            return ResponseEntity.ok(game);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(buildError(e));
        }

    }

    @CrossOrigin
    @PostMapping("/turn")
    public ResponseEntity<?> newGame(
            @RequestBody Turn turn) {
        try {
            Game game = gameService.makeTurn(turn);
            return ResponseEntity.ok(game);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(buildError(e));
        }

    }

    private ObjectNode buildError(IllegalArgumentException e) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("error", e.getMessage());
    }
}

