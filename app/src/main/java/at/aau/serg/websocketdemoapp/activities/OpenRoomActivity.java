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
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.RoomSetupMessage;
import at.aau.serg.websocketdemoapp.msg.TestMessage;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

public class OpenRoomActivity extends AppCompatActivity {

    Button backButton;

    EditText editTextRoomName;
    Spinner spinnerNumPlayers;
    EditText editTextCreator;
    Button buttonOpenRoomNow;

    //NEW
    WebSocketClient networkHandler;

    SharedPreferences sharedPreferences;

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
        // register a handler for received messages when setting up the connection
        networkHandler.connectToServer(this::messageReceivedFromServer);
    }

    //TODO: to handle this topic: use more than one message maybe... as atomic as possible


    private void sendMessage() {
        //read input
        String roomName = editTextRoomName.getText().toString();

        /*if (roomName.length() < 4 || roomName.length() > 8) {
            Toast.makeText(OpenRoomActivity.this, "Der Raumname muss zwischen 4 und 8 Zeichen lang sein.", Toast.LENGTH_SHORT).show();
            return; //exit aus dieser methode

        }*/

        String finalNumPlayers = spinnerNumPlayers.getSelectedItem().toString();
        String creatorName = editTextCreator.getText().toString();

        //setMessage
        RoomSetupMessage roomSetupMessage = new RoomSetupMessage(roomName, finalNumPlayers, creatorName);
        roomSetupMessage.setRoomID("emptyRoomID");
        roomSetupMessage.setPlayerID("emptyPlayerID");

        //json
        String jsonMessage = new Gson().toJson(roomSetupMessage);
        networkHandler.sendMessageToServer(jsonMessage);


    }

    @Override
    protected void onResume() {
        super.onResume();
        connectToWebSocketServer();
    }

    private void messageReceivedFromServer(String message) {
        // TODO handle received messages
        //Gson gson = new Gson();
        //Log.d("Network", message);
        //textViewServerResponse.setText(message);
        //testMessage testMessage = gson.fromJson(message, TestMessage.class);
        System.out.println(message);
        runOnUiThread(() -> {
            Log.d("Network", "from server: " + message);
            //textViewServerResponse.setText(message);
        });
    }





}