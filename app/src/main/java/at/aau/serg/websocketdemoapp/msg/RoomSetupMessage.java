package at.aau.serg.websocketdemoapp.msg;

import java.util.UUID;

public class RoomSetupMessage {

    private final MessageType messageType = MessageType.SETUP_ROOM;

    private ActionType actionType;
    private String roomID;
    private String roomName;
    private String playerID;
    private String playerName;

    private String numPlayers;


    public RoomSetupMessage() {
        //default constructor
    }

    public RoomSetupMessage(String roomName, String numPlayers, String playerName) {
        this.roomName = roomName;
        this.numPlayers = numPlayers;
        this.playerName = playerName;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public enum ActionType {
        OPEN_ROOM, OPEN_ROOM_OK, OPEN_ROOM_ERR, ROOM_FULL
    }


    public String getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(String numPlayers) {
        this.numPlayers = numPlayers;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
