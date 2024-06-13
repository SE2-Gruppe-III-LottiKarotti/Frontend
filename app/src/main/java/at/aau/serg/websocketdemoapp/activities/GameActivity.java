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
import at.aau.serg.websocketdemoapp.game.Field;
import at.aau.serg.websocketdemoapp.game.Gameboard;
import at.aau.serg.websocketdemoapp.game.PlayingPiece;
import at.aau.serg.websocketdemoapp.msg.DrawCardMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.GameMessage;
import at.aau.serg.websocketdemoapp.msg.MessageType;
import at.aau.serg.websocketdemoapp.msg.MoveMessage;
import at.aau.serg.websocketdemoapp.msg.OpenRoomMessage;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

public class GameActivity extends AppCompatActivity {
    //Client/Server variables
    WebSocketClient networkHandler;
    Gson gson = new Gson();
    DrawCardMessage drawCardMessage;
    DrawCardMessage drawCardMessageReceived;
    GameMessage gameMessage;
    GameMessage gameMessageReceived;
    MoveMessage moveMessage;
    MoveMessage moveMessageReceived;

    //Android variables
    Button buttonDraw;
    Button buttonStart;
    Spinner spinnerCheat;
    TextView playerNameView;
    TextView roomNameTitleView;
    TextView playerTurnView;
    ImageView rabbit1;
    ImageView rabbit2;
    ImageView rabbit3;
    ImageView rabbit4;

    //Data variables
    SharedPreferences sharedPreferences;

    //Player variables
    private String currentPlayerId;
    private String nextPlayerId;
    private String roomId;
    private String roomName;
    private String playerId;
    private String playerName;

    //Game variables
    private ImageView[] fields = new ImageView[27];
    Field[] rabbitPosition;
    PlayingPiece playingPiece1;

    //Boolean variables
    boolean firstClick1 = true;
    boolean FirstClick2 = false;
    boolean firstClick3 = false;
    boolean isFirstClick4 = false;


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
        networkHandler.addMessageHandler("draw_card", this::receiveDrawMessage);
        networkHandler.addMessageHandler("game", this::receiveGameMessage);
        networkHandler.addMessageHandler("move", this::receiveMoveMessage);
        networkHandler.connectToServer();

        drawCardMessage = new DrawCardMessage();
        drawCardMessageReceived = new DrawCardMessage();
        gameMessage = new GameMessage();
        gameMessageReceived = new GameMessage();
        moveMessage = new MoveMessage();
        moveMessageReceived = new MoveMessage();

        buttonDraw = findViewById(R.id.btn_draw);
        buttonStart = findViewById(R.id.btn_chat);
        spinnerCheat = findViewById(R.id.spinnerDrawMode);
        playerNameView = findViewById(R.id.playerNameInput);
        roomNameTitleView = findViewById(R.id.roomName);
        playerTurnView = findViewById(R.id.playerTurn);

        String[] drawOptions = new String[]{"RANDOM", "ONE", "TWO", "THREE", "CARROT"};
        ArrayAdapter<String> playerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, drawOptions);
        spinnerCheat.setAdapter(playerAdapter);

        roomId = sharedPreferences.getString("roomId", null);
        roomName = sharedPreferences.getString("roomName", null);
        playerId = sharedPreferences.getString("playerId", null);
        playerName = sharedPreferences.getString("playerName", null);
        currentPlayerId = sharedPreferences.getString("playerToStart", null);

        playerNameView.setText(playerName);
        roomNameTitleView.setText(roomName);

        updatePlayerTurnText();

        rabbit1 = findViewById(R.id.rabbit1);
        rabbit2 = findViewById(R.id.rabbit2);
        rabbit3 = findViewById(R.id.rabbit3);
        rabbit4 = findViewById(R.id.rabbit4);

        ImageView field1 = findViewById(R.id.field1);
        ImageView field2 = findViewById(R.id.field2);
        ImageView field3 = findViewById(R.id.field3);
        ImageView field4 = findViewById(R.id.field4);
        ImageView field5 = findViewById(R.id.field5);
        ImageView field6 = findViewById(R.id.field6);
        ImageView field7 = findViewById(R.id.field7);
        ImageView field8 = findViewById(R.id.field8);
        ImageView field9 = findViewById(R.id.field9);
        ImageView field10 = findViewById(R.id.field10);
        ImageView field11 = findViewById(R.id.field11);
        ImageView field12 = findViewById(R.id.field12);
        ImageView field13 = findViewById(R.id.field13);
        ImageView field14 = findViewById(R.id.field14);
        ImageView field15 = findViewById(R.id.field15);
        ImageView field16 = findViewById(R.id.field16);
        ImageView field17 = findViewById(R.id.field17);
        ImageView field18 = findViewById(R.id.field18);
        ImageView field19 = findViewById(R.id.field19);
        ImageView field20 = findViewById(R.id.field20);
        ImageView field21 = findViewById(R.id.field21);
        ImageView field22 = findViewById(R.id.field22);
        ImageView field23 = findViewById(R.id.field23);
        ImageView field24 = findViewById(R.id.field24);
        ImageView field25 = findViewById(R.id.field25);
        ImageView field26 = findViewById(R.id.field26);
        ImageView field27 = findViewById(R.id.field27);

        fields = new ImageView[]{field1, field2, field3, field4, field5, field6, field7, field8, field9,
                field10, field11, field12, field13, field14, field15, field16, field17, field18, field19,
                field20, field21, field22, field23, field24, field25, field26, field27};

        //ClickListeners
        buttonDraw.setOnClickListener((view) -> {
            sendMessageDraw();
        });

        buttonStart.setOnClickListener((view) -> {
            sendMessageGame();
        });


        rabbit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playingPiece1 = new PlayingPiece(1, playerId);
                sendMessageMove(playingPiece1);
            }
        });

/*
        rabbit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRabbit(rabbit2, currentRabbitPosition(2), 2);
            }
        });

        rabbit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRabbit(rabbit3, currentRabbitPosition(3), 3);
            }
        });

        rabbit4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           moveRabbit(rabbit4, currentRabbitPosition(4), 4);
           }
           });
 */

        connectToServer();
    }

    private void connectToServer() {
        networkHandler.connectToServer();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updatePlayerTurnText() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (playerId.equals(currentPlayerId)) {
                    playerTurnView.setText(playerName + " its your turn");
                    buttonDraw.setEnabled(true);
                    buttonDraw.postInvalidate();
                } else {
                    playerTurnView.setText("Waiting for the other player");
                    buttonDraw.setEnabled(false);
                    buttonDraw.postInvalidate();
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

    private <T> void receiveGameMessage(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;

            gameMessageReceived = gson.fromJson(jsonString, GameMessage.class);
            rabbitPosition = gameMessageReceived.getFields();

            Log.d("Gameboard", Arrays.toString(rabbitPosition));
        }
    }

    private <T> void receiveMoveMessage(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;

            moveMessageReceived = gson.fromJson(jsonString, MoveMessage.class);
            rabbitPosition = moveMessageReceived.getFields();

            Log.d("GameboardK", String.valueOf(firstClick1));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    moveRabbit(rabbit1, playingPiece1, firstClick1);
                }
            });
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
            drawCardMessage.setCard(spinnerCheat.getSelectedItem().toString());
            drawCardMessage.setActionTypeDrawCard(ASK_FOR_CARD);

            String jsonMessage = new Gson().toJson(drawCardMessage);
            networkHandler.sendMessageToServer(jsonMessage);
        }
    }

    private void sendMessageGame() {
        String jsonMessage = new Gson().toJson(gameMessage);
        networkHandler.sendMessageToServer(jsonMessage);
    }

    private void sendMessageMove(PlayingPiece playingPiece) {

        String card = "";
        switch (drawCardMessageReceived.getCard()) {
            case "ONE":
                card = "1";
                break;
            case "TWO":
                card = "2";
                break;
            case "THREE":
                card = "3";
                break;
            default:
                break;
        }
        moveMessage.setCard(card);
        moveMessage.setFields(rabbitPosition);
        moveMessage.setSpielerId(playerId);
        moveMessage.setRoomId(roomId);
        moveMessage.setPlayingPiece(playingPiece);

        String jsonMessage = new Gson().toJson(moveMessage);
        networkHandler.sendMessageToServer(jsonMessage);
    }

    private void moveRabbit(ImageView clickedRabbit, PlayingPiece playingPiece, boolean firstClick) {
        ViewGroup parentLayout = (ViewGroup) findViewById(R.id.relative_layout3);
        ViewGroup currentParent = (ViewGroup) clickedRabbit.getParent();
        currentParent.removeView(clickedRabbit);

        Log.d("GameboardMove", Arrays.toString(rabbitPosition));
        int count = -1;
        for (Field field : rabbitPosition) {
            count++;
            Log.d("GameboardPiece", String.valueOf(playingPiece));
            if(field.getPlayingPiece().equals(playingPiece))
                break;
        }
        Log.d("Gameboard1", Arrays.toString(rabbitPosition));
        Log.d("Gameboard", String.valueOf(playingPiece));
        Log.d("Gameboard", String.valueOf(count));

        Log.d("Gameboard", String.valueOf(firstClick));
        if(firstClick) {
            int rabbitWidth = clickedRabbit.getWidth();
            int rabbitHeight = clickedRabbit.getHeight();
            int fieldWidth = fields[count].getWidth();
            int fieldHeight = fields[count].getHeight();

            int xPos = (int)fields[count].getX() + (fieldWidth - rabbitWidth) / 2;
            int yPos = (int)fields[count].getY() + (fieldHeight - rabbitHeight) / 2;

            // Add the rabbit ImageView to the parent layout of the fields
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(rabbitWidth, rabbitHeight);
            layoutParams.leftMargin = xPos;
            layoutParams.topMargin = yPos;
            parentLayout.addView(clickedRabbit, layoutParams);
            }

            else {
                parentLayout.addView(clickedRabbit);
                float targetX = fields[count].getX();
                float targetY = fields[count].getY();
                clickedRabbit.setX(targetX);
                clickedRabbit.setY(targetY);
            }

    }

    /*
    private int currentRabbitPosition(int rabbitNumber) {
        for (int i = 0; i < rabbitPosition.length; i++) {
            if (rabbitPosition[i] == rabbitNumber) {
                rabbitPosition[i] = 0;
                return i;
            }
        }
        return -1;
    }*/

    /*
    private boolean isFieldFree (int fieldToGo) {
        return rabbitPosition[fieldToGo] == 0;
    }*/


    /*
    private void moveRabbit(ImageView clickedRabbit, int currentPosition, int rabbitNumber) {

        SecureRandom rand = new SecureRandom();
        int fieldToGo;
        switch (drawCardMessageReceived.getCard()) {
            case "ONE":
                fieldToGo = 1;
                break;
            case "TWO":
                fieldToGo = 2;
                break;
            case "THREE":
                fieldToGo = 3;
                break;
            default:
                fieldToGo = 0;
        }

        if(fieldToGo + currentPosition >= fields.length){
            fieldToGo = rand.nextInt(fields.length - currentPosition);
        }

        ViewGroup parentLayout = (ViewGroup) findViewById(R.id.relative_layout3);
        ViewGroup currentParent = (ViewGroup) clickedRabbit.getParent();
        currentParent.removeView(clickedRabbit);


        // Add the rabbit ImageView to the parent layout of the field
        for (Field field : rabbitPosition) {
        }

        if(currentPosition==-1) {
            int rabbitWidth = clickedRabbit.getWidth();
            int rabbitHeight = clickedRabbit.getHeight();
            int fieldWidth = fields[0].getWidth();
            int fieldHeight = fields[0].getHeight();
            while(!(isFieldFree(currentPosition+fieldToGo))) {
                int newTarget = currentPosition+fieldToGo;

                fieldToGo++;
            }
            int xPos = (int)fields[currentPosition + fieldToGo].getX() + (fieldWidth - rabbitWidth) / 2;
            int yPos = (int)fields[currentPosition + fieldToGo].getY() + (fieldHeight - rabbitHeight) / 2;

            // Add the rabbit ImageView to the parent layout of the fields
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(rabbitWidth, rabbitHeight);
            layoutParams.leftMargin = xPos;
            layoutParams.topMargin = yPos;
            parentLayout.addView(clickedRabbit, layoutParams);
        }
        else {
            parentLayout.addView(clickedRabbit);
            while(!(isFieldFree(currentPosition+fieldToGo))) {
                int newTarget = currentPosition+fieldToGo;

                fieldToGo++;
            }

            float targetX = fields[currentPosition+fieldToGo].getX();
            float targetY = fields[currentPosition+fieldToGo].getY();
            clickedRabbit.setX(targetX);
            clickedRabbit.setY(targetY);
        }
        rabbitPosition[currentPosition+fieldToGo]=rabbitNumber;

    }
*/
}

