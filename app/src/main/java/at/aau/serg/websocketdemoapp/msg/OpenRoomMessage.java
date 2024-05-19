package at.aau.serg.websocketdemoapp.msg;


//@Data
public class OpenRoomMessage {

    private final MessageType messageType = MessageType.OPEN_ROOM;
    private String roomId;
    private String roomName;
    private String playerId;
    private String playerName;
    private String numPlayers;
    private OpenRoomMessage.OpenRoomActionType openRoomActionType;

    public enum OpenRoomActionType {
        /**open room*/
        OPEN_ROOM_ASK,
        OPEN_ROOM_OK,
        OPEN_ROOM_ERR,

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

    public String getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(String numPlayers) {
        this.numPlayers = numPlayers;
    }

    public OpenRoomActionType getOpenRoomActionType() {
        return openRoomActionType;
    }

    public void setOpenRoomActionType(OpenRoomActionType openRoomActionType) {
        this.openRoomActionType = openRoomActionType;
    }
}
