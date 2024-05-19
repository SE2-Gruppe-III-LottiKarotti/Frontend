package at.aau.serg.websocketdemoapp.msg;

//import lombok.Data;

//@Data
public class JoinRoomMessage {

    private final MessageType messageType = MessageType.JOIN_ROOM;
    String roomId;
    String roomName;
    String playerId;
    String playerName;
    ActionTypeJoinRoom actionTypeJoinRoom;

    public enum ActionTypeJoinRoom {
        /**join room*/
        JOIN_ROOM_ASK,
        JOIN_ROOM_OK,
        JOIN_ROOM_FULL,
        JOIN_ROOM_ERR
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ActionTypeJoinRoom getActionTypeJoinRoom() {
        return actionTypeJoinRoom;
    }

    public void setActionTypeJoinRoom(ActionTypeJoinRoom actionTypeJoinRoom) {
        this.actionTypeJoinRoom = actionTypeJoinRoom;
    }
}
