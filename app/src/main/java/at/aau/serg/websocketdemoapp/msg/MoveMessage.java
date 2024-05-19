package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;

@Data
public class MoveMessage {
    private final MessageType messageType = MessageType.MOVE;

    private String roomId;
    private String spielerId;
    private String card;


}
