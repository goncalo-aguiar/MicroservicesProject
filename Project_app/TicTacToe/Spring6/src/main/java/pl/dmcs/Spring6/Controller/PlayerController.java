package pl.dmcs.Spring6.Controller;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import pl.dmcs.Spring6.Config.MQConfig;
import pl.dmcs.Spring6.Model.Player;
import pl.dmcs.Spring6.Repository.PlayerRepository;


@RestController
@CrossOrigin(origins = "http://localhost:8082")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    private RabbitTemplate template;




    @PostMapping("/publish")
    public String publishMessage(@RequestBody Message message) {
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, message);

        return "Message Published";
    }

    @PostMapping("/savePlayer")
    public ResponseEntity<Player> savePlayer(@RequestBody Player player) {

        Player savedplayer = playerRepository.save(player);
        return ResponseEntity.ok(savedplayer);
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/players/{username}/{win_lose}")
    public ResponseEntity<Player> getPlayerById(@PathVariable String username, @PathVariable Long win_lose) {
        Optional<Player> optionalPlayer = Optional.ofNullable(playerRepository.findByUser(username));
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setGamesPlayed(player.getGamesPlayed()+1);
            if (win_lose == 1) {
                player.setGamesWon(player.getGamesWon()+1);
            }
            Player savedplayer = playerRepository.save(player);
            Message message = new Message();
            message.setMessage("MENSAGEM!!!");
            //publishMessage(message);
            return ResponseEntity.ok(savedplayer);
        } else {
            Player newPlayer = new Player();
            newPlayer.setUser(username);
            if (win_lose == 1) {
                newPlayer.setGamesWon(1);
            }
            else{
                newPlayer.setGamesWon(0);
            }
            newPlayer.setGamesPlayed(1);
            Player savedplayer = playerRepository.save(newPlayer);
            Message message = new Message();
            message.setMessage("MENSAGEM!!!");
            //publishMessage(message);
            return ResponseEntity.ok(savedplayer);

        }
    }


}