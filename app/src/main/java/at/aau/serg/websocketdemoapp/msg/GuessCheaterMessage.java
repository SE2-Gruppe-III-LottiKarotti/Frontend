package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GuessCheaterMessage extends BaseMessage{


    //the player who blame the other player to be a cheater (blamer)
    private String accusingPlayerId;
    private String accusingPlayerName;
    //the player who should be the cheater (cheater)
    private String playerToBlameName;
    private String playerToBlameId;
    private String roomId;

    public GuessCheaterMessage () {
        this.messageType = MessageType.CHEAT;
    }


}
