package at.aau.serg.websocketdemoapp.activities;

import static at.aau.serg.websocketdemoapp.msg.DrawCardMessage.ActionTypeDrawCard.ASK_FOR_CARD;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import at.aau.serg.websocketdemoapp.fragments.Carrot;
import at.aau.serg.websocketdemoapp.fragments.Rabbit1;
import at.aau.serg.websocketdemoapp.fragments.Rabbit2;
import at.aau.serg.websocketdemoapp.fragments.Rabbit3;
import at.aau.serg.websocketdemoapp.msg.DrawCardMessage;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.util.Objects;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.MessageType;
import at.aau.serg.websocketdemoapp.msg.OpenRoomMessage;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

public class GameActivity extends AppCompatActivity {
    WebSocketClient networkHandler;
    DrawCardMessage drawCardMessage;
    DrawCardMessage drawCardMessageReceived;
    Gson gson = new Gson();
    Button button;

    Spinner spinner;

    SharedPreferences sharedPreferences;

    TextView playerName1;
    TextView roomNameTitle;
    TextView playerTurn;
    private String currentPlayerId;
    private String nextPlayerId;
    private String roomId;
    private String roomName;
    private String playerId;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);

        networkHandler = new WebSocketClient();
        connectToServer();

        button = findViewById(R.id.btn_draw);

        drawCardMessage = new DrawCardMessage();

        drawCardMessageReceived = new DrawCardMessage();

        spinner = findViewById(R.id.spinnerDrawMode);

        playerName1 = findViewById(R.id.playerNameInput);

        roomNameTitle = findViewById(R.id.roomName);

        playerTurn = findViewById(R.id.playerTurn);

        roomId = sharedPreferences.getString("roomId", null);
        roomName = sharedPreferences.getString("roomName", null);
        playerId = sharedPreferences.getString("playerId", null);
        playerName = sharedPreferences.getString("playerName", null);
        currentPlayerId = sharedPreferences.getString("playerToStart", null);

        playerName1.setText(playerName);
        roomNameTitle.setText(roomName);

        updatePlayerTurnText();

        button.setOnClickListener((view) -> {
            sendMessageDraw();
        });


        String[] drawOptions = new String[]{"random", "1", "2", "3", "carrot"};
        ArrayAdapter<String> playerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, drawOptions);
        spinner.setAdapter(playerAdapter);
    }


    private void connectToServer() {
        networkHandler.connectToServer(this::receiveDrawMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectToServer();
    }

    private void updatePlayerTurnText() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (playerId.equals(currentPlayerId)) {
                    playerTurn.setText(playerName + " ist dran");
                    button.setEnabled(true);
                    button.postInvalidate();
                } else {
                    playerTurn.setText("Warten auf den anderen Spieler");
                    button.setEnabled(false);
                    button.postInvalidate();
                }
            }
        });
    }

    private <T> void receiveDrawMessage(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;

            drawCardMessageReceived = gson.fromJson(jsonString, DrawCardMessage.class);
            nextPlayerId = drawCardMessageReceived.getNextPlayerId();
            String serverResponse = drawCardMessageReceived.getCard();
            showPopup(serverResponse);

            currentPlayerId = nextPlayerId;
            updatePlayerTurnText();
        }
    }

    private void showPopup(String serverResponse) {
        if (playerId.equals(currentPlayerId)) {
            runOnUiThread(new Runnable() {
                public void run() {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Handle default case
                    if (serverResponse != null) {
                        switch (serverResponse) {
                            case "ONE":
                                Rabbit1 rabbit1 = new Rabbit1();
                                fragmentTransaction.add(R.id.fragmentContainer, rabbit1, "Rabbit1Tag");
                                break;
                            case "TWO":
                                Rabbit2 rabbit2 = new Rabbit2();
                                fragmentTransaction.add(R.id.fragmentContainer, rabbit2, "Rabbit2Tag");
                                break;
                            case "THREE":
                                Rabbit3 rabbit3 = new Rabbit3();
                                fragmentTransaction.add(R.id.fragmentContainer, rabbit3, "Rabbit3Tag");
                                break;
                            case "CARROT":
                                Carrot carrot = new Carrot();
                                fragmentTransaction.add(R.id.fragmentContainer, carrot, "CarrotTag");
                                break;
                            default:
                                break;
                        }
                        fragmentTransaction.commit();
                    }
                }
            });
        }
    }

    private void sendMessageDraw() {
        {
            updatePlayerTurnText();
            drawCardMessage.setMessageType(MessageType.DRAW_CARD);
            drawCardMessage.setPlayerID(playerId);
            drawCardMessage.setRoomID(roomId);
            drawCardMessage.setCard(spinner.getSelectedItem().toString());
            drawCardMessage.setActionTypeDrawCard(ASK_FOR_CARD);

            String jsonMessage = new Gson().toJson(drawCardMessage);
            networkHandler.sendMessageToServer(jsonMessage);
        }
    }
}

