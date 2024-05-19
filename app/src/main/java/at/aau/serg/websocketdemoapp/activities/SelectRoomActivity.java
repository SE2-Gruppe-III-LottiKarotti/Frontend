package at.aau.serg.websocketdemoapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.util.ArrayList;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;
import at.aau.serg.websocketdemoapp.networking.WebSocketMessageHandler;

public class SelectRoomActivity extends AppCompatActivity {


    private static final String SHARED_PREFERENCES_NAME = "PlayersPref";
    private static final String KEY_PLAYER_ID = "PlayerID";

    private ListView listViewRooms;
    private EditText editTextPlayerName;
    private Button buttonSend;
    private WebSocketClient webSocketClient;

    private String selectedRoom;
    private String playerName;
    private String playerID;

    private ArrayList<String> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
/*
        listViewRooms = findViewById(R.id.listViewRooms);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);
        buttonSend = findViewById(R.id.buttonSend);
*/
        //init
        webSocketClient = new WebSocketClient();

        //TODO: if activity will be used, implementation should be from here...
        //TODO: take a look, how the basic implementation is working in OpenRoomActivity
        //TODO: before the JoinRoomActivity will be used, and SelectRoom is smth like a more smooth way to join...


    }




}