package at.aau.serg.websocketdemoapp.msg;


import at.aau.serg.websocketdemoapp.game.Gameboard;
import lombok.Data;

@Data
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
}
