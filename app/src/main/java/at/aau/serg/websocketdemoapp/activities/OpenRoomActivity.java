package at.aau.serg.websocketdemoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import at.aau.serg.websocketdemoapp.msg.MessageType;
import at.aau.serg.websocketdemoapp.msg.OpenRoomMessage;
import at.aau.serg.websocketdemoapp.networking.Heartbeat;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

public class OpenRoomActivity extends AppCompatActivity {

    Button backButton;

    EditText editTextRoomName;
    Spinner spinnerNumPlayers;
    EditText editTextCreator;
    Button buttonOpenRoomNow;

    TextView responseMessage;

    //NEW
    WebSocketClient networkHandler;

    SharedPreferences sharedPreferences;

    Heartbeat heartbeat;

    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_open_room);
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

        editTextRoomName = findViewById(R.id.editTextRoomName);
        spinnerNumPlayers = findViewById(R.id.spinnerNumPlayers);
        editTextCreator = findViewById(R.id.editTextCreatorName);
        buttonOpenRoomNow = findViewById(R.id.buttonOpenRoomNow);
        backButton = findViewById(R.id.backButton);
        responseMessage = findViewById(R.id.textViewResponse);

        String[] playerOptions = new String [] {"2", "3", "4"};
        ArrayAdapter<String> playerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, playerOptions);
        spinnerNumPlayers.setAdapter(playerAdapter);

        //NEW
        networkHandler = new WebSocketClient();
        /*heartbeat*/
        heartbeat = new Heartbeat(networkHandler);
        heartbeat.start();

        //call connection
        connectToWebSocketServer();

        buttonOpenRoomNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openRoom();
                sendMessage();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenRoomActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //NEW
    private void connectToWebSocketServer() {
        networkHandler.addMessageHandler(MessageType.OPEN_ROOM.toString(), this::messageReceivedFromServer);
        networkHandler.addMessageHandler(MessageType.HEARTBEAT.toString(), this::messageReceivedFromServer);
        networkHandler.connectToServer();
    }


    private void sendMessage() {


        //read input
        String roomName = editTextRoomName.getText().toString();

        //check for size

        if (roomName.length() < 4 || roomName.length() > 10) {
            Toast.makeText(OpenRoomActivity.this, "room name must have between 5 and 9 chars.", Toast.LENGTH_SHORT).show();
            return;
        }


        String finalNumPlayers = spinnerNumPlayers.getSelectedItem().toString();
        String creatorName = editTextCreator.getText().toString();

        if (creatorName.length() < 4 || creatorName.length() > 10) {
            Toast.makeText(OpenRoomActivity.this, "player name must have between 5 and 9 chars.", Toast.LENGTH_SHORT).show();
            return;
        }


        OpenRoomMessage openRoomMessage = new OpenRoomMessage();
        openRoomMessage.setPlayerName(creatorName);
        openRoomMessage.setNumPlayers(finalNumPlayers);
        openRoomMessage.setRoomName(roomName);


        //json
        String jsonMessage = new Gson().toJson(openRoomMessage);
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


    private <T> void messageReceivedFromServer(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;
            Log.d("LOGG", "reached entry messageReceivedFromServer");
            // deserialize
            try {
                BaseMessage baseMessage = gson.fromJson(jsonString, BaseMessage.class);

                if (baseMessage.getMessageType().equals(MessageType.HEARTBEAT)) {
                    HeartbeatMessage hbMessage = gson.fromJson(jsonString, HeartbeatMessage.class);
                    Log.d("LOGG", "HEARTBEAT message received: " + hbMessage.getText()); // Add this line
                    if (hbMessage.getText().equals("pong")) {
                        Log.d("heartbeat", jsonString);
                        return;
                    }
                }
                if (baseMessage.getMessageType().equals(MessageType.OPEN_ROOM)) {
                    //
                    OpenRoomMessage openRoomMessage = gson.fromJson(jsonString, OpenRoomMessage.class);

                    if (openRoomMessage.getOpenRoomActionType() == OpenRoomMessage.OpenRoomActionType.OPEN_ROOM_OK) {
                        // Handle OPEN_ROOM_OK action type
                        runOnUiThread(() -> {
                            Log.d("Network", "OPEN_ROOM_OK received: " + jsonString);
                            Log.d("LOGG", "reached tryBlock OPEN_ROOM_OK messageReceivedFromServer");
                            Log.d("LOGG", jsonString);

                            responseMessage.setText(jsonString);

                            String roomId = openRoomMessage.getRoomId();
                            String roomName = openRoomMessage.getRoomName();
                            String playerId = openRoomMessage.getPlayerId();
                            String playerName = openRoomMessage.getPlayerName();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("roomId", roomId);
                            editor.putString("roomName", roomName);
                            editor.putString("playerId", playerId);
                            editor.putString("playerName", playerName);
                            editor.putString("playerToStart", playerId);
                            editor.putString("start", "player1joined");
                            editor.apply();

                            Intent intent = new Intent(OpenRoomActivity.this, GameActivity.class);

                            startActivity(intent);
                            finish();
                        });
                    } else if (openRoomMessage.getOpenRoomActionType() == OpenRoomMessage.OpenRoomActionType.OPEN_ROOM_ERR) {
                        // Handle OPEN_ROOM_ERR action type
                        runOnUiThread(() -> {
                            Log.d("Network", "OPEN_ROOM_ERR received: " + jsonString);
                            Log.d("LOGG", "reached tryBlock OPEN_ROOM_ERR messageReceivedFromServer");
                            responseMessage.setText(jsonString);

                            // display error msg
                            Toast.makeText(OpenRoomActivity.this, "Error: " + jsonString, Toast.LENGTH_SHORT).show();
                        });
                    }
                }



            } catch (JsonSyntaxException e) {
                // json error
                runOnUiThread(() -> {
                    Log.d("Network", "Failed to parse JSON message: " + jsonString, e);
                    Log.d("LOGG", "reached errorBlock messageReceivedFromServer");
                    responseMessage.setText(jsonString);
                });
            }
        }

    }





}