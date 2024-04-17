package at.aau.serg.websocketdemoapp.msg;

public class SpielerMessage {

    private final MessageType messageType = MessageType.SPIELER;
    private ActionType actionType;
    private String spielerID;
    private String name;

    //constructor or default constructor???
    public SpielerMessage () {

    }

    public enum ActionType {
        LOGIN, LOGIN_OK, LOGIN_ERR
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getSpielerID() {
        return spielerID;
    }

    public void setSpielerID(String spielerID) {
        this.spielerID = spielerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
