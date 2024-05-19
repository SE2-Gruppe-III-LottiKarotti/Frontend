package at.aau.serg.websocketdemoapp.networking;

public class Room {
    private String roomID;
    private String roomName;
    //private int maxPlayers;
    private String creator;
    private int availablePlayersSpace;

    //TODO: vll hier drinnen auch eine ArrayList mit Farben ablegen...
    /***/


    public Room() {

        //TODO: vll auch das gameboard hier initialisieren...
    }

    public int getAvailablePlayersSpace() {
        return availablePlayersSpace;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setAvailablePlayersSpace(int availablePlayersSpace) {
        this.availablePlayersSpace = availablePlayersSpace;
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
}
