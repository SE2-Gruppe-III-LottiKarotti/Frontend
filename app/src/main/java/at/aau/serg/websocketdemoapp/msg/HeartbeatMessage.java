package at.aau.serg.websocketdemoapp.msg;


public class HeartbeatMessage {
    private final MessageType messageType = MessageType.HEARTBEAT;
    private String text;// = "pong";

    public MessageType getMessageType() {
        return messageType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
