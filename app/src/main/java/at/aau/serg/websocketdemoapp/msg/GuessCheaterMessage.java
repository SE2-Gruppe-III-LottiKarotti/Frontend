package at.aau.serg.websocketdemoapp.msg;

import lombok.Data;

@Data
public class GuessCheaterMessage {

    private final MessageType messageType = MessageType.CHEAT;
    //der spieler, welcher beschuldigt
    private String accusingPlayerId;
    private String accusingPlayerName;
    //der spieler, welcher beschuldigt wird
    private String playerToBlameName;
    private String playerToBlameId;
    private String roomId;


}
