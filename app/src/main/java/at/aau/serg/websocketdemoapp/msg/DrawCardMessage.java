package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;

@Data
public class DrawCardMessage {
    /*MessageType messageType;
    String roomID;
    String playerID;
    String card;*/
    private MessageType messageType;
    private String roomID;
    private String playerID;
    private String card;
    private String nextPlayerId;
    private ActionTypeDrawCard actionTypeDrawCard;

    /*message maybe need to be broadcasted*/


    public enum ActionTypeDrawCard {
        ASK_FOR_CARD,
        RETURN_CARD_OK,
        RETURN_CARD_ERR
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public ActionTypeDrawCard getActionTypeDrawCard() {
        return actionTypeDrawCard;
    }

    public void setActionTypeDrawCard(ActionTypeDrawCard actionTypeDrawCard) {
        this.actionTypeDrawCard = actionTypeDrawCard;
    }
    public String getNextPlayerId() {
        return nextPlayerId;
    }


}