package at.aau.serg.websocketdemoapp.msg;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.aau.serg.websocketdemoapp.game.Field;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GuessCheaterMessage extends BaseMessage{

    private String accusingPlayerId;
    private String playerToBlameName;
    private String playerToBlameId;
    private String roomId;
    private String cheater;
    @JsonProperty("fields")
    Field fields[];

    public GuessCheaterMessage () {
        this.messageType = MessageType.CHEAT;
    }


}
