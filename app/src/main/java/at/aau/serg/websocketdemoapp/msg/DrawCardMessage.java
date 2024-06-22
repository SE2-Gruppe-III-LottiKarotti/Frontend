package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DrawCardMessage extends BaseMessage {

    private String roomID;
    private String playerID;
    private String card = "random";
    private String nextPlayerId;
    private ActionTypeDrawCard actionTypeDrawCard;

    public DrawCardMessage() {
        //default
        this.messageType = MessageType.DRAW_CARD;
    }

    public enum ActionTypeDrawCard {
        ASK_FOR_CARD,
        RETURN_CARD_OK,
        RETURN_CARD_ERR
    }
}