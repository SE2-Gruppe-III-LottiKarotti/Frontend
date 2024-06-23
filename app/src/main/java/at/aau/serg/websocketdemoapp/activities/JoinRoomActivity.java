package at.aau.serg.websocketdemoapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.BaseMessage;
import at.aau.serg.websocketdemoapp.msg.JoinRoomMessage;
import at.aau.serg.websocketdemoapp.msg.MessageType;
import at.aau.serg.websocketdemoapp.msg.RoomListMessage;
import at.aau.serg.websocketdemoapp.networking.RoomInfo;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

public class JoinRoomActivity extends AppCompatActivity {

    WebSocketClient networkHandler;

    EditText roomNameEditText;
    EditText playerNameEditText;
    Button refreshButton;
    Button joinButton;
    Button backButton;
    SharedPreferences sharedPreferences;

    private final Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_join_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);

        playerNameEditText = findViewById(R.id.playerNameEditText);
        roomNameEditText = findViewById(R.id.roomNameEditText);
        refreshButton = findViewById(R.id.refreshButton);
        joinButton = findViewById(R.id.joinButton);
        backButton = findViewById(R.id.backButton);

        networkHandler = new WebSocketClient();
        connectToWebSocketServer();

        //init List
        initRoomList();

        //buttons
        refreshButton.setOnClickListener(v -> sendMessageRoomList());
        joinButton.setOnClickListener(v -> sendMessageJoinRoom());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinRoomActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initRoomList() {
        //
        //RoomSetupMessage message = new RoomSetupMessage();


    }

    private void connectToWebSocketServer() {
        networkHandler.addMessageHandler("JOIN_ROOM", this::messageReceivedFromServer);
        networkHandler.connectToServer();

    }

    private void sendMessageRoomList() {

        //messageIdentifierJoinRoom = UUID.randomUUID().toString();
        /**die räume können noch nicht am screen in einer View ausgegeben werden*/

        RoomListMessage askForRoomMessage = new RoomListMessage();
        askForRoomMessage.setActionTypeRoomListMessage(RoomListMessage.ActionTypeRoomListMessage.ASK_FOR_ROOM_LIST);
        //askForRoomMessage.setMessageIdentifier(messageIdentifierJoinRoom);


        String jsonMessage = new Gson().toJson(askForRoomMessage);
        networkHandler.sendMessageToServer(jsonMessage);



    }

    private void sendMessageJoinRoom() {

        //messageIdentifierJoinRoom = UUID.randomUUID().toString();
        /**da die eingabe beim Telefon noch spinnt bzw. der emulator nicht
         * einwandfrei funktioniert sind vorübergehend noch dummy werte hinterlegt
         * */

        JoinRoomMessage joinRoomMsg = new JoinRoomMessage();
        joinRoomMsg.setActionTypeJoinRoom(JoinRoomMessage.ActionTypeJoinRoom.JOIN_ROOM_ASK);
        String roomNameToTransfer = roomNameEditText.getText().toString();
        //default value of default db room
        //dummy val
        if (roomNameEditText.getText().toString().isEmpty()) {
            roomNameToTransfer = "TestRoom";
        }

        //askForRoomMessage.setMessageIdentifier(messageIdentifierJoinRoom);
        joinRoomMsg.setRoomName(roomNameToTransfer);
        joinRoomMsg.setPlayerName(playerNameEditText.getText().toString());
        //dummy val
        if(playerNameEditText.getText().toString().isEmpty()) {
            joinRoomMsg.setPlayerName("dummy1");
        }
        String jsonMessage = new Gson().toJson(joinRoomMsg);
        networkHandler.sendMessageToServer(jsonMessage);



    }

    @Override
    protected void onResume() {
        super.onResume();
        connectToWebSocketServer();
    }


    private void handleRoomListMessage(RoomListMessage message) {
        //
        /**mit der liste gibt es noch probleme*/

        if (message.getActionTypeRoomListMessage() == RoomListMessage.ActionTypeRoomListMessage.ANSWER_ROOM_LIST_OK) {
            List<RoomInfo> receivedRooms = message.getRoomInfoArrayList();
            Log.d("MSG", "received room list " + message);
        }
        else {
            runOnUiThread(()-> Toast.makeText(JoinRoomActivity.this, "error", Toast.LENGTH_SHORT).show());
            Log.d("MSG", "received room list error " + message);

        }
    }

    private void handleJoinRoomMessage(JoinRoomMessage message) {

        String roomId = message.getRoomId();
        String roomName = message.getRoomName();
        String playerId = message.getPlayerId();
        String playerName = message.getPlayerName();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("roomId", roomId);
        editor.putString("roomName", roomName);
        editor.putString("playerId", playerId);
        editor.putString("playerName", playerName);
        editor.putString("start", "player2joined");
        editor.apply();

        switch(message.getActionTypeJoinRoom()) {
            case JOIN_ROOM_OK:
                //CustomSharedPreferences.saveRoomIDToSharedPreferences(this, message.getRoomId());
                //CustomSharedPreferences.savePlayerIDToSharedPreferences(this, message.getPlayerId());
                Log.d("MSG", "log join room ok " + message);

                runOnUiThread(() -> {
                    Toast.makeText(JoinRoomActivity.this, "Successfully joined the room", Toast.LENGTH_SHORT).show();


                });
                Intent intent = new Intent(JoinRoomActivity.this, GameActivity.class);
                startActivity(intent);
                break;
            case JOIN_ROOM_ERR:
                runOnUiThread(() -> Toast.makeText(JoinRoomActivity.this, "error when joining the room", Toast.LENGTH_SHORT).show());
                Log.d("MSG", "log join room ok " + message);
            default:
                Log.e("Error", "Unknown action type: " + message.getActionTypeJoinRoom());
                break;

        }
    }

    private <T> void messageReceivedFromServer(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;
            // Versuche, die Nachricht als RoomMSG zu deserialisieren
            try {
                //TODO
                BaseMessage baseMessage = gson.fromJson(jsonString, BaseMessage.class);
                MessageType messageType = baseMessage.getMessageType();

                switch(messageType) {
                    case JOIN_ROOM:
                        JoinRoomMessage joinRoomMessage = gson.fromJson(jsonString, JoinRoomMessage.class);
                        handleJoinRoomMessage(joinRoomMessage);
                        break;
                    case LIST_ROOMS:
                        RoomListMessage roomListMessage = gson.fromJson(jsonString, RoomListMessage.class);
                        handleRoomListMessage(roomListMessage);
                        break;
                    default:
                        Log.d("error", "unknown message type " + jsonString);
                }

            } catch (JsonSyntaxException e) {
                // Falls die Deserialisierung fehlschlägt, zeige den gesamten Text der Nachricht an
                runOnUiThread(() -> {
                    Log.e("Error", "Failed to parse JSON message: " + jsonString, e);
                    //textViewServerResponse.setText(jsonString);
                });
            }
        } else {
            Log.e("Error", "Received message is not a String");
        }
    }
}