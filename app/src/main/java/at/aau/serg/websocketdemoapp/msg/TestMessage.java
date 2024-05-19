package at.aau.serg.websocketdemoapp.msg;

public class TestMessage {
    private final MessageType messageType = MessageType.TEST;

    private String text;

    String messageIdentifier;

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

    public String getMessageIdentifier() {
        return messageIdentifier;
    }

    public void setMessageIdentifier(String messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
    }
}
