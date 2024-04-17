package at.aau.serg.websocketdemoapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.SpielerMessage;
import at.aau.serg.websocketdemoapp.msg.TestMessage;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

public class MainActivity extends AppCompatActivity {



    TextView textViewServerResponse;

    WebSocketClient networkHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Gson gson = new Gson();

        Button buttonConnect = findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(v -> connectToWebSocketServer());

        Button buttonSendMsg = findViewById(R.id.buttonSendMsg);
        buttonSendMsg.setOnClickListener(v -> sendMessage());

        textViewServerResponse = findViewById(R.id.textViewResponse);

        networkHandler = new WebSocketClient();
    }

    private void connectToWebSocketServer() {
        // register a handler for received messages when setting up the connection
        networkHandler.connectToServer(this::messageReceivedFromServer);
    }

    private void sendMessage() {
        //testmessage
        Gson gson = new Gson();
        TestMessage testMessage = new TestMessage();
        testMessage.setText("test message");
        String jsonMessage = gson.toJson(testMessage);
        //networkHandler.sendMessageToServer("test message");
        networkHandler.sendMessageToServer(jsonMessage);
    }

    private void messageReceivedFromServer(String message) {
        // TODO handle received messages
        //Gson gson = new Gson();
        //Log.d("Network", message);
        //textViewServerResponse.setText(message);
        //estMessage testMessage = gson.fromJson(message, TestMessage.class);
        System.out.println(message);
        runOnUiThread(() -> {
            Log.d("Network", "from server: " + message);
            textViewServerResponse.setText(message);
        });
    }
}