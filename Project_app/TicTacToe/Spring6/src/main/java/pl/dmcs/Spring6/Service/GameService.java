package pl.dmcs.Spring6.Service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.dmcs.Spring6.Exception.InvalidGameException;
import pl.dmcs.Spring6.Exception.InvalidParamException;
import pl.dmcs.Spring6.Exception.NotFoundException;
import pl.dmcs.Spring6.Model.Game;
import pl.dmcs.Spring6.Model.GamePlay;
import pl.dmcs.Spring6.Model.Player;
import pl.dmcs.Spring6.Model.TicToe;
import pl.dmcs.Spring6.Repository.PlayerRepository;
import pl.dmcs.Spring6.Storage.GameStorage;


import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import static pl.dmcs.Spring6.Model.GameStatus.NEW;
import static pl.dmcs.Spring6.Model.GameStatus.IN_PROGESS;
import static pl.dmcs.Spring6.Model.GameStatus.FINISHED;

@Service
@AllArgsConstructor
public class GameService {


    @Transactional
    public Game createGame(Player player) {


        Game game = new Game();
        game.setBoard(new int[3][3]);
        game.setGameId(UUID.randomUUID().toString());
        game.setPlayer1(player);
        game.setStatus(NEW);
        GameStorage.getInstance().setGame(game);



        return game;
    }
}



