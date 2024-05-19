package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;

@Data
public class JoinRoomMessage {
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
}
