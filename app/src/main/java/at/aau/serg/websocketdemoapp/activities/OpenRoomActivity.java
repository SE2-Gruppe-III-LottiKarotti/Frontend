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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.CreateRoomMessage;
import at.aau.serg.websocketdemoapp.msg.MessageType;
import at.aau.serg.websocketdemoapp.msg.OpenRoomMessage;
import at.aau.serg.websocketdemoapp.msg.RoomSetupMessage;
import at.aau.serg.websocketdemoapp.msg.TestMessage;
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

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


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

        //verbindung aufrufen
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
        networkHandler.connectToServer(this::messageReceivedFromServer);
    }

    //TODO: to handle this topic: use more than one message maybe... as atomic as possible


    private void sendMessage() {
        /*CreateRoomMessage createRoomMessage = new CreateRoomMessage();
        createRoomMessage.setMessageType(MessageType.CREATE_ROOM);
        createRoomMessage.setRoomName(editTextCreator.getText().toString());
        createRoomMessage.setRoomName(editTextRoomName.getText().toString());

        String jsonMessage = new Gson().toJson(createRoomMessage);
        networkHandler.sendMessageToServer(jsonMessage);*/

        //read input
        String roomName = editTextRoomName.getText().toString();

        //check for size

        if (roomName.length() < 4 || roomName.length() > 10) {
            Toast.makeText(OpenRoomActivity.this, "Der Raumname muss zwischen 4 und 10 Zeichen lang sein.", Toast.LENGTH_SHORT).show();
            return;
        }

        //messageIdentifierOpenRoom = UUID.randomUUID().toString();

        //set message starts here...
        String finalNumPlayers = spinnerNumPlayers.getSelectedItem().toString();
        String creatorName = editTextCreator.getText().toString();


        OpenRoomMessage openRoomMessage = new OpenRoomMessage();
        openRoomMessage.setPlayerName(creatorName);
        openRoomMessage.setNumPlayers(finalNumPlayers);
        openRoomMessage.setRoomName(roomName);

        //wichtig, um danach die message beim Empfang zu identifizieren


        //json
        String jsonMessage = new Gson().toJson(openRoomMessage);
        networkHandler.sendMessageToServer(jsonMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectToWebSocketServer();
    }

    /*private void messageReceivedFromServer(String message) {
        // TODO handle received messages
        runOnUiThread(() -> {
            Log.d("OPEN ROOM", message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            Intent intent =  new Intent(this, GameActivity.class);
            startActivity(intent);
        });
    }*/
    private <T> void messageReceivedFromServer(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;
            Log.d("LOGG", "reached entry messageReceivedFromServer");
            // Versuche, die Nachricht als TestMessage zu deserialisieren
            try {
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


                        // Redirect to the next activity
                        Intent intent = new Intent(OpenRoomActivity.this, GameActivity.class);
                        intent.putExtra("roomId", roomId);
                        intent.putExtra("roomName", roomName);
                        intent.putExtra("playerId", playerId);
                        intent.putExtra("playerName", playerName);
                        startActivity(intent);
                        finish(); // Close this activity
                    });
                } else if (openRoomMessage.getOpenRoomActionType() == OpenRoomMessage.OpenRoomActionType.OPEN_ROOM_ERR) {
                    // Handle OPEN_ROOM_ERR action type
                    runOnUiThread(() -> {
                        Log.d("Network", "OPEN_ROOM_ERR received: " + jsonString);
                        Log.d("LOGG", "reached tryBlock OPEN_ROOM_ERR messageReceivedFromServer");
                        responseMessage.setText(jsonString);

                        // Display error message to the user
                        Toast.makeText(OpenRoomActivity.this, "Error: " + jsonString, Toast.LENGTH_SHORT).show();
                    });
                }

            } catch (JsonSyntaxException e) {
                // Falls die Deserialisierung fehlschlägt, zeige den gesamten Text der Nachricht an
                runOnUiThread(() -> {
                    Log.d("Network", "Failed to parse JSON message: " + jsonString, e);
                    Log.d("LOGG", "reached errorBlock messageReceivedFromServer");
                    responseMessage.setText(jsonString);
                });
            }
        }

    }





}