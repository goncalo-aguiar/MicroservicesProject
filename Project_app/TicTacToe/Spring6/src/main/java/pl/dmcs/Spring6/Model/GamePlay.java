package pl.dmcs.Spring6.Model;


import lombok.Data;

@Data
public class GamePlay {

    private TicToe type;
    private Integer coordinateX;
    private Integer coordinateY;
    private String gameId;

    public String getGameId() {
        return gameId;
    }

    public int getCoordinateX() {
        return this.coordinateX;
    }

    public Integer getCoordinateY() {
        return this.coordinateY;
    }

    public TicToe getType() {
        return this.type;
    }
}