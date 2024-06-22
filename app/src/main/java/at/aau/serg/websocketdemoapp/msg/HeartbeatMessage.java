package at.aau.serg.websocketdemoapp.msg;


public class HeartbeatMessage extends BaseMessage{
    private String text;// = "ping";

    public HeartbeatMessage () {
        this.messageType = MessageType.HEARTBEAT;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
