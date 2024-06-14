package at.aau.serg.websocketdemoapp.game;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Field {
    @SerializedName("moleHole")
    private boolean isMoleHole;
    @SerializedName("open")
    private boolean isOpen;
    private PlayingPiece playingPiece;
    private boolean isOccupiedByPlayingPiece;
}
