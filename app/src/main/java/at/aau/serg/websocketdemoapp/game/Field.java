package at.aau.serg.websocketdemoapp.game;

import lombok.Data;

@Data
public class Field {
    private boolean isMoleHole;
    private boolean isOpen;
    private PlayingPiece playingPiece;
    private boolean isOccupiedByPlayingPiece;

}
