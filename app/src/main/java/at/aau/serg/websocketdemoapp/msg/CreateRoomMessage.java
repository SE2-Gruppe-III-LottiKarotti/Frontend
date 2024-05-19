package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;

@Data
public class CreateRoomMessage {
    private MessageType messageType;
    private String roomName;
    private String playerName;
}