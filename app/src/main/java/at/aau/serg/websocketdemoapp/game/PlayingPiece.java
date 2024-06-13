package at.aau.serg.websocketdemoapp.game;

import lombok.Data;

@Data
public class PlayingPiece {
    private int playingPiece;
    private String playerId;

    public PlayingPiece(int playingPiece, String playerId) {
        this.playingPiece = playingPiece;
        this.playerId = playerId;
    }
}
