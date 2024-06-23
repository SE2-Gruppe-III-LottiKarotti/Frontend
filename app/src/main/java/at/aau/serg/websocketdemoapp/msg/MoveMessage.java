package at.aau.serg.websocketdemoapp.msg;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.aau.serg.websocketdemoapp.game.Field;
import at.aau.serg.websocketdemoapp.game.PlayingPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MoveMessage extends BaseMessage{

    private String roomId;
    private String spielerId;
    private String card;
    @JsonProperty("fields")
    Field fields[];
    private PlayingPiece playingPiece;

    public MoveMessage () {
        this.messageType = MessageType.MOVE;
    }
}
