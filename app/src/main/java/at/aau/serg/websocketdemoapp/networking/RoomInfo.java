package at.aau.serg.websocketdemoapp.networking;

import java.util.ArrayList;

public class RoomInfo {
    private String roomID;
    private String roomName;

    private String creator;
    //private int maxPlayers;
    private int availablePlayersSpace;

    private ArrayList<Room> availableRooms;

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /*public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }*/

    public int getAvailablePlayersSpace() {
        return availablePlayersSpace;
    }

    public void setAvailablePlayersSpace(int availablePlayersSpace) {
        this.availablePlayersSpace = availablePlayersSpace;
    }

    public ArrayList<Room> getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(ArrayList<Room> availableRooms) {
        this.availableRooms = availableRooms;
    }
}
