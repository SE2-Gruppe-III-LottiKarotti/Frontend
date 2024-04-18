package at.aau.serg.websocketdemoapp.msg;

public class TestMessage {
    private final MessageType messageType = MessageType.TEST;

    private String text;

    public TestMessage() {
    }

    public TestMessage(String text) {
        this.text = text;
    }

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
