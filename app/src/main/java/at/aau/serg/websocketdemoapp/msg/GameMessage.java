package at.aau.serg.websocketdemoapp.msg;

import at.aau.serg.websocketdemoapp.game.Field;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GameMessage extends BaseMessage{

    public GameMessage() {
        this.messageType = MessageType.GAME;
    }

    Field fields[];
    /*@JsonProperty("fields")
    Field fields[];*/
}
