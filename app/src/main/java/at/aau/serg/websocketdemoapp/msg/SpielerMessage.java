package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;

@Data
public class SpielerMessage {
    private final MessageType messageType = MessageType.SPIELER;
    private ActionType actionType;
    private String spielerID;
    private String name;
}