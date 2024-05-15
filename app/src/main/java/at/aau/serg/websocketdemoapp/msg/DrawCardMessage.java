package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;

@Data
public class DrawCardMessage {
    MessageType messageType;
    String roomID;
    String playerID;
    String card;
}