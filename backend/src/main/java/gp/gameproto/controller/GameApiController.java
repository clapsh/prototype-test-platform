package gp.gameproto.controller;

import gp.gameproto.db.entity.Game;
import gp.gameproto.dto.AddGameRequest;
import gp.gameproto.dto.UpdateGameRequest;
import gp.gameproto.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/proto/game")
public class GameApiController {
    private final GameService gameService;

    // 게임 저장
    @PostMapping()
    public ResponseEntity<String> addGame (@RequestBody AddGameRequest request){
        String response = gameService.save(request);

        //예외처리
        if(response == "게임이 존재합니다."){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    // 게임 수정
    @PutMapping("/{gameId}")
    public ResponseEntity<Game> updateGame (@PathVariable("gameId") Long id,@RequestBody UpdateGameRequest request){
        Game game = gameService.update(request, id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(game);
    }
}
