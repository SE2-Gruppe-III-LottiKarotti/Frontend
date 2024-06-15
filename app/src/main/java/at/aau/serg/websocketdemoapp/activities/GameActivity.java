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
import at.aau.serg.websocketdemoapp.game.PlayingPiece;
import at.aau.serg.websocketdemoapp.msg.DrawCardMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import java.util.Arrays;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.GameMessage;
import at.aau.serg.websocketdemoapp.msg.MessageType;
import at.aau.serg.websocketdemoapp.msg.MoveMessage;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    ImageView rabbit5;
    ImageView rabbit6;
    ImageView rabbit7;
    ImageView rabbit8;

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

    //Boolean variables
    boolean firstClick1 = true;
    boolean firstClick2 = true;
    boolean firstClick3 = true;
    boolean firstClick4 = true;
    boolean firstClick5 = true;
    boolean firstClick6 = true;
    boolean firstClick7 = true;
    boolean firstClick8 = true;


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
        rabbit5 = findViewById(R.id.rabbit5);
        rabbit6 = findViewById(R.id.rabbit6);
        rabbit7 = findViewById(R.id.rabbit7);
        rabbit8 = findViewById(R.id.rabbit8);

        if (currentPlayerId.equals(playerId)) {
            rabbit5.setVisibility(View.INVISIBLE);
            rabbit6.setVisibility(View.INVISIBLE);
            rabbit7.setVisibility(View.INVISIBLE);
            rabbit8.setVisibility(View.INVISIBLE);
            rabbit1.setVisibility(View.VISIBLE);
            rabbit2.setVisibility(View.VISIBLE);
            rabbit3.setVisibility(View.VISIBLE);
            rabbit4.setVisibility(View.VISIBLE);
        } else {
            rabbit1.setVisibility(View.INVISIBLE);
            rabbit2.setVisibility(View.INVISIBLE);
            rabbit3.setVisibility(View.INVISIBLE);
            rabbit4.setVisibility(View.INVISIBLE);
            rabbit5.setVisibility(View.VISIBLE);
            rabbit6.setVisibility(View.VISIBLE);
            rabbit7.setVisibility(View.VISIBLE);
            rabbit8.setVisibility(View.VISIBLE);
        }


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

        if (currentPlayerId.equals(playerId)) {
            rabbit1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayingPiece playingPiece1 = new PlayingPiece(1, currentPlayerId);
                    try {
                        sendMessageMove(playingPiece1);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            rabbit2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayingPiece playingPiece2 = new PlayingPiece(2, currentPlayerId);
                    try {
                        sendMessageMove(playingPiece2);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            rabbit3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayingPiece playingPiece3 = new PlayingPiece(3, currentPlayerId);
                    try {
                        sendMessageMove(playingPiece3);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            rabbit4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayingPiece playingPiece4 = new PlayingPiece(4, currentPlayerId);
                    try {
                        sendMessageMove(playingPiece4);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        else {
            rabbit5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayingPiece playingPiece1 = new PlayingPiece(5, currentPlayerId);
                    try {
                        sendMessageMove(playingPiece1);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            rabbit6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayingPiece playingPiece2 = new PlayingPiece(6, currentPlayerId);
                    try {
                        sendMessageMove(playingPiece2);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            rabbit7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayingPiece playingPiece3 = new PlayingPiece(7, currentPlayerId);
                    try {
                        sendMessageMove(playingPiece3);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            rabbit8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayingPiece playingPiece4 = new PlayingPiece(8, currentPlayerId);
                    try {
                        sendMessageMove(playingPiece4);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

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

        }
    }

    private <T> void receiveGameMessage(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;

            gameMessageReceived = gson.fromJson(jsonString, GameMessage.class);
            rabbitPosition = gameMessageReceived.getFields();

            Log.d("Gameboard", Arrays.toString(rabbitPosition));
            setMoleHoleImageViews(rabbitPosition);
        }
    }

    private <T> void receiveMoveMessage(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;

            moveMessageReceived = gson.fromJson(jsonString, MoveMessage.class);
            rabbitPosition = moveMessageReceived.getFields();
            PlayingPiece playingPiece = moveMessageReceived.getPlayingPiece();

            runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (playingPiece.getPlayingPiece() == 1) {
                    moveRabbit(rabbit1, playingPiece, firstClick1);
                }
                if (playingPiece.getPlayingPiece() == 2) {
                    moveRabbit(rabbit2, playingPiece, firstClick2);
                }
                if (playingPiece.getPlayingPiece() == 3) {
                    moveRabbit(rabbit3, playingPiece, firstClick3);
                }
                if (playingPiece.getPlayingPiece() == 4) {
                    moveRabbit(rabbit4, playingPiece, firstClick4);
                }
                if (playingPiece.getPlayingPiece() == 5) {
                    moveRabbit(rabbit5, playingPiece, firstClick5);
                }
                if (playingPiece.getPlayingPiece() == 6) {
                    moveRabbit(rabbit6, playingPiece, firstClick6);
                }
                if (playingPiece.getPlayingPiece() == 7) {
                    moveRabbit(rabbit7, playingPiece, firstClick7);
                }
                if (playingPiece.getPlayingPiece() == 8) {
                    moveRabbit(rabbit8, playingPiece, firstClick8);
                }
            }
            });
            currentPlayerId = nextPlayerId;
            updatePlayerTurnText();
        }
    }

    private void setMoleHoleImageViews(Field[] RabbitFields) {
        Log.d("RabbitField", Arrays.toString(RabbitFields));
        for (int i = 0; i < RabbitFields.length; i++) {
            Log.d("MoleholeTrue?", String.valueOf(rabbitPosition[i].isMoleHole()));
            if (RabbitFields[i].isMoleHole() && RabbitFields[i].isOpen()) {
                fields[i].setBackgroundResource(R.color.black);
            } else {
                fields[i].setBackgroundResource(R.color.yellow);
            }
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

    private void sendMessageMove(PlayingPiece playingPiece) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        if(playerId.equals(currentPlayerId)) {
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
            String jsonMessage = mapper.writeValueAsString(moveMessage);

            networkHandler.sendMessageToServer(jsonMessage);
        }
    }

    private void moveRabbit(ImageView clickedRabbit, PlayingPiece playingPiece, boolean firstClick) {
        ViewGroup parentLayout = (ViewGroup) findViewById(R.id.relative_layout3);
        ViewGroup currentParent = (ViewGroup) clickedRabbit.getParent();
        currentParent.removeView(clickedRabbit);
        clickedRabbit.setVisibility(View.VISIBLE);

        int count = -1;
        for (Field field : rabbitPosition) {
        count++;
        if (field.getPlayingPiece() != null && field.getPlayingPiece().equals(playingPiece))
            break;
        }

        if (firstClick) {
            int rabbitWidth = clickedRabbit.getWidth();
            int rabbitHeight = clickedRabbit.getHeight();
            int fieldWidth = fields[0].getWidth();
            int fieldHeight = fields[0].getHeight();

            int xPos = (int) fields[count].getX() + (fieldWidth - rabbitWidth) / 2;
            int yPos = (int) fields[count].getY() + (fieldHeight - rabbitHeight) / 2;

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
}

