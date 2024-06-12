package at.aau.serg.websocketdemoapp.msg;

import at.aau.serg.websocketdemoapp.game.Field;
import at.aau.serg.websocketdemoapp.game.Gameboard;
import lombok.Data;

@Data
public class GameMessage {
    private final MessageType messageType = MessageType.GAME;

    Field fields[];
}
