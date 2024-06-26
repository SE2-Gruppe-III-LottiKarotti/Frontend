package at.aau.serg.websocketdemoapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.security.GeneralSecurityException;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.BaseMessage;
import at.aau.serg.websocketdemoapp.msg.HeartbeatMessage;
import at.aau.serg.websocketdemoapp.msg.JoinRoomMessage;
import at.aau.serg.websocketdemoapp.msg.MessageType;
import at.aau.serg.websocketdemoapp.msg.RoomListMessage;
import at.aau.serg.websocketdemoapp.networking.Heartbeat;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

public class JoinRoomActivity extends AppCompatActivity {

    WebSocketClient networkHandler;

    EditText roomNameEditText;
    EditText playerNameEditText;
    Button joinButton;
    Button backButton;
    SharedPreferences sharedPreferences;

    Heartbeat heartbeat;

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

        Context context = getApplicationContext();

        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    "GamePrefs",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        playerNameEditText = findViewById(R.id.playerNameEditText);
        roomNameEditText = findViewById(R.id.roomNameEditText);
        joinButton = findViewById(R.id.joinButton);
        backButton = findViewById(R.id.backButton);

        networkHandler = new WebSocketClient();
        /*heartbeat*/
        heartbeat = new Heartbeat(networkHandler);
        heartbeat.start();
        connectToWebSocketServer();


        //buttons
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


    private void connectToWebSocketServer() {
        networkHandler.addMessageHandler(MessageType.JOIN_ROOM.toString(), this::messageReceivedFromServer);
        networkHandler.addMessageHandler(MessageType.HEARTBEAT.toString(), this::messageReceivedFromServer);
        networkHandler.connectToServer();

    }


    private void sendMessageJoinRoom() {

        JoinRoomMessage joinRoomMsg = new JoinRoomMessage();
        joinRoomMsg.setActionTypeJoinRoom(JoinRoomMessage.ActionTypeJoinRoom.JOIN_ROOM_ASK);
        String roomNameToTransfer = roomNameEditText.getText().toString();

        //input roomname check
        if (roomNameToTransfer.length() < 4 || roomNameToTransfer.length() > 10) {
            Toast.makeText(JoinRoomActivity.this, "room name must have between 5 and 9 chars.", Toast.LENGTH_SHORT).show();
            return;
        }

        joinRoomMsg.setRoomName(roomNameToTransfer);
        joinRoomMsg.setPlayerName(playerNameEditText.getText().toString());

        //input player check
        if (playerNameEditText.length() < 4 || playerNameEditText.length() > 10) {
            Toast.makeText(JoinRoomActivity.this, "player name must have between 5 and 9 chars.", Toast.LENGTH_SHORT).show();
            return;
        }

        String jsonMessage = new Gson().toJson(joinRoomMsg);
        networkHandler.sendMessageToServer(jsonMessage);



    }

    @Override
    protected void onResume() {
        super.onResume();
        connectToWebSocketServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (heartbeat != null) {
            heartbeat.stop();
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

    private void handleHeartbeatMessage(HeartbeatMessage hBmessage) {
        Log.d("LOGG", "HEARTBEAT message received: " + hBmessage.getText()); // Add this line
        if (hBmessage.getText().equals("pong")) {
            Log.d("heartbeat", hBmessage.toString());
            return;
        }
    }

    private <T> void messageReceivedFromServer(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;
            // deserialize
            try {
                BaseMessage baseMessage = gson.fromJson(jsonString, BaseMessage.class);
                MessageType messageType = baseMessage.getMessageType();

                switch(messageType) {
                    case JOIN_ROOM:
                        JoinRoomMessage joinRoomMessage = gson.fromJson(jsonString, JoinRoomMessage.class);
                        handleJoinRoomMessage(joinRoomMessage);
                        break;
                    case LIST_ROOMS:
                        RoomListMessage roomListMessage = gson.fromJson(jsonString, RoomListMessage.class);
                        //handleRoomListMessage(roomListMessage);
                        /*not implemented at the moment*/
                        break;
                    case HEARTBEAT:
                        HeartbeatMessage heartbeatMessageIncoming = gson.fromJson(jsonString, HeartbeatMessage.class);
                        handleHeartbeatMessage(heartbeatMessageIncoming);
                        break;
                    default:
                        Log.d("error", "unknown message type " + jsonString);
                }

            } catch (JsonSyntaxException e) {
                // error case
                runOnUiThread(() -> {
                    Log.e("Error", "Failed to parse JSON message: " + jsonString, e);

                });
            }
        } else {
            Log.e("Error", "Received message is not a String");
        }
    }
}