package at.aau.serg.websocketdemoapp.msg;

public class BaseMessage implements BaseMessageInterface{
    protected MessageType messageType;

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

}
