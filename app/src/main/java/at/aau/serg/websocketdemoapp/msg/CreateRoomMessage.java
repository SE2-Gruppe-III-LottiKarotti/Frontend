package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;

@Data
public class CreateRoomMessage {
    MessageType messageType;
    String roomName;
    String playerName;
}