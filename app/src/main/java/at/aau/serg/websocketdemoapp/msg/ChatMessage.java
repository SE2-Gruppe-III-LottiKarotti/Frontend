package at.aau.serg.websocketdemoapp.msg;


public class ChatMessage extends BaseMessage{
    private String playerName;
    private String playerId;
    private String text;
    private String roomID;

    private ActionTypeChat actionTypeChat;

    /* message need to be broadcasted */


    public ChatMessage(String playerName, String playerId, String text, String roomID) {
        this.messageType = MessageType.CHAT;
        this.playerName = playerName;
        this.playerId = playerId;
        this.text = text;
        this.roomID = roomID;
    }

    public ChatMessage() {
        this.messageType = MessageType.CHAT;
        //default
    }

    public enum ActionTypeChat {
        CHAT_MSG_TO_SERVER,
        CHAT_MSG_TO_CLIENTS_OK,
        CHAT_MSG_TO_CLIENTS_ERR
    }

    public ActionTypeChat getActionTypeChat() {
        return actionTypeChat;
    }

    public void setActionTypeChat(ActionTypeChat actionTypeChat) {
        this.actionTypeChat = actionTypeChat;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}
