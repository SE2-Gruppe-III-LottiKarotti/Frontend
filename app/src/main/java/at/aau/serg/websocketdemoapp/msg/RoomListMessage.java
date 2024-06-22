package at.aau.serg.websocketdemoapp.msg;

import java.util.ArrayList;

import at.aau.serg.websocketdemoapp.networking.RoomInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomListMessage extends BaseMessage {

    public RoomListMessage () {
        //default
        this.messageType = MessageType.LIST_ROOMS;
    }

    private ArrayList<RoomInfo> roomInfoArrayList;
    private ActionTypeRoomListMessage actionTypeRoomListMessage;
    public enum ActionTypeRoomListMessage {
        ASK_FOR_ROOM_LIST,
        ANSWER_ROOM_LIST_OK,
        ANSWER_ROOM_LIST_ERR
    }


}
