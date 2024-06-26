package at.aau.serg.websocketdemoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.UUID;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.MessageType;
import at.aau.serg.websocketdemoapp.msg.TestMessage;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

public class MainActivity extends AppCompatActivity {



    TextView textViewServerResponse;

    WebSocketClient networkHandler;

    ImageView imageView;

    ProgressBar progressBar;

    String messageIdentifierMainActivity;

    private final Gson gson = new Gson();

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

        textViewServerResponse = findViewById(R.id.textViewResponse);

        networkHandler = new WebSocketClient();
        connectToWebSocketServer();
        sendMessage();


        progressBar.setVisibility(ProgressBar.VISIBLE);
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(ProgressBar.GONE);
            goToStartActivity();
        }, 2000);
    }

    private void goToStartActivity() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }


    private void connectToWebSocketServer() {
        // register a handler for received messages when setting up the connection
        networkHandler.addMessageHandler(MessageType.TEST.toString(), this::messageReceivedFromServer);
        networkHandler.connectToServer();
    }

    private void sendMessage() {
        //testmessage
        messageIdentifierMainActivity = UUID.randomUUID().toString();
        TestMessage testMessage = new TestMessage();
        testMessage.setMessageIdentifier(messageIdentifierMainActivity);
        testMessage.setText("test message");
        String jsonMessage = gson.toJson(testMessage);
        networkHandler.sendMessageToServer(jsonMessage);
        Log.d("Network", "to server: " + jsonMessage);
    }

    private <T> void messageReceivedFromServer(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;
            // deserialize
            try {
                TestMessage testMessage = gson.fromJson(jsonString, TestMessage.class);
                String text = testMessage.getText();
                String compare = "juhu";
                //positive case
                if (text.equals(compare)) {
                    //if it was for the device
                    if (testMessage.getMessageIdentifier().equals(messageIdentifierMainActivity)) {
                        runOnUiThread(() -> {
                            Log.d("Network", "from server: " + jsonString);
                            String response = "client connecting to server...";
                            textViewServerResponse.setText(response);
                            goToStartActivity();
                        });
                    }
                    else {
                        //if it was for another device
                        runOnUiThread(() -> {
                            Log.d("Network", "from server: " + jsonString + "but not for this device");
                            String response = "client connected to server";
                            textViewServerResponse.setText(response);
                        });
                    }

                }

            } catch (JsonSyntaxException e) {
                //error case
                runOnUiThread(() -> {
                    Log.e("Error", "Failed to parse JSON message: " + jsonString, e);
                    textViewServerResponse.setText(jsonString);
                });
            }
        } else {
            Log.e("Error", "Received message is not a String");
        }
    }
}