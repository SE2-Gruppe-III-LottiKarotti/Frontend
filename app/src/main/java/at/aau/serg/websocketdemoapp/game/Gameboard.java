package at.aau.serg.websocketdemoapp.game;
import lombok.Data;
@Data
public class Gameboard {
    private Field[] fields;
    private int[] holes;
}
