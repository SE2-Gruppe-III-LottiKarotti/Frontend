package at.aau.serg.websocketdemoapp.msg;

import java.util.ArrayList;

import at.aau.serg.websocketdemoapp.networking.RoomInfo;
//import lombok.Data;

//@Data
public class RoomListMessage {

    private final MessageType messageType = MessageType.LIST_ROOMS;

    ArrayList<RoomInfo> roomInfoArrayList;
    ActionTypeRoomListMessage actionTypeRoomListMessage;
    public enum ActionTypeRoomListMessage {
        ASK_FOR_ROOM_LIST,
        ANSWER_ROOM_LIST_OK,
        ANSWER_ROOM_LISR_ERR
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public ArrayList<RoomInfo> getRoomInfoArrayList() {
        return roomInfoArrayList;
    }

    public void setRoomInfoArrayList(ArrayList<RoomInfo> roomInfoArrayList) {
        this.roomInfoArrayList = roomInfoArrayList;
    }

    public ActionTypeRoomListMessage getActionTypeRoomListMessage() {
        return actionTypeRoomListMessage;
    }

    public void setActionTypeRoomListMessage(ActionTypeRoomListMessage actionTypeRoomListMessage) {
        this.actionTypeRoomListMessage = actionTypeRoomListMessage;
    }
}
