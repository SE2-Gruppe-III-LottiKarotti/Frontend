package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JoinRoomMessage extends BaseMessage {

    private String roomId;
    private String roomName;
    private String playerId;
    private String playerName;
    private ActionTypeJoinRoom actionTypeJoinRoom;

    public JoinRoomMessage() {
        //default
        this.messageType = MessageType.JOIN_ROOM;
    }

    public enum ActionTypeJoinRoom {
        /**join room*/
        JOIN_ROOM_ASK,
        JOIN_ROOM_OK,
        JOIN_ROOM_FULL,
        JOIN_ROOM_ERR
    }


}
