package at.aau.serg.websocketdemoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    ImageView imageView;

    ProgressBar progressBar;

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

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        imageView.setImageResource(R.drawable.img);

        //Gson gson = new Gson();

        /*
        Button buttonConnect = findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(v -> connectToWebSocketServer());

        Button buttonSendMsg = findViewById(R.id.buttonSendMsg);
        buttonSendMsg.setOnClickListener(v -> sendMessage());

        //btn login
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(v -> openLoginActivity());

        textViewServerResponse = findViewById(R.id.textViewResponse);*/

        networkHandler = new WebSocketClient();
        //networkHandler.connectToServer();
        connectToWebSocketServer();
        sendMessage();


        progressBar.setVisibility(ProgressBar.VISIBLE);
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(ProgressBar.GONE);
            goToStartActivity();
        }, 4000);
    }

    private void goToStartActivity() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    /*private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }*/

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
            //textViewServerResponse.setText(message);
        });
    }
}