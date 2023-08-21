
package pl.dmcs.Spring6.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dmcs.Spring6.Model.Game;
import pl.dmcs.Spring6.Model.Player;
import pl.dmcs.Spring6.Service.GameService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor

@RequestMapping("/game")
public class GameController {

    private  GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player) {
        log.info("start game request: {}", player);
        return ResponseEntity.ok(gameService.createGame(player));
    }


}