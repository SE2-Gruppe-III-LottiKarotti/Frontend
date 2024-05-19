package at.aau.serg.websocketdemoapp.msg;

import java.util.UUID;

import lombok.Data;

@Data
public class RoomSetupMessage {


    private ActionType actionType;
    private String roomID;
    private String roomName;
    private String playerID;
    private String playerName;

    private String numPlayers;


    public RoomSetupMessage(String roomName, String finalNumPlayers, String creatorName) {
        this.roomName = roomName;
        this.numPlayers = finalNumPlayers;
        this.playerName = creatorName;
    }
}
