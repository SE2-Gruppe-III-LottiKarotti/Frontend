package at.aau.serg.websocketdemoapp.msg;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.aau.serg.websocketdemoapp.game.Field;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GameMessage extends BaseMessage{

    public GameMessage() {
        this.messageType = MessageType.GAME;
    }

    String playerId;
    String roomId;
    String[] playerNames;
    @JsonProperty("fields")
    Field fields[];
}
