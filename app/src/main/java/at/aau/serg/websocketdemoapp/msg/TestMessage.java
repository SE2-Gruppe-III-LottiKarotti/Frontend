package at.aau.serg.websocketdemoapp.msg;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TestMessage extends BaseMessage {

    private String text;

    private String messageIdentifier;

    public TestMessage() {
        //default
        this.messageType = MessageType.TEST;
    }


}
