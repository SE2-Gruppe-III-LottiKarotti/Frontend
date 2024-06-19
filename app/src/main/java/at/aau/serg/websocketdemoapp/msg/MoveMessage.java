package at.aau.serg.websocketdemoapp.msg;

import at.aau.serg.websocketdemoapp.game.Field;
import at.aau.serg.websocketdemoapp.game.PlayingPiece;
import lombok.Data;

@Data
public class MoveMessage {
    private final MessageType messageType = MessageType.MOVE;

    private String roomId;
    private String spielerId;
    private String card;
    private Field[] fields;
    private PlayingPiece playingPiece;
}
