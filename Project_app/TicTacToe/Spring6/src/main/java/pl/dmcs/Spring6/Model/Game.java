package pl.dmcs.Spring6.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Game {

    private Player player1;
    private Player player2;
    private String gameId;
    private GameStatus status;
    private int[][] board;
    private TicToe winner;


}
