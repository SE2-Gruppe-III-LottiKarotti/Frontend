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

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.msg.MessageType;
import at.aau.serg.websocketdemoapp.msg.OpenRoomMessage;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

public class GameActivity extends AppCompatActivity {
    ImageView[] fields;

    WebSocketClient networkHandler;
    DrawCardMessage drawCardMessage;
    Gson gson =  new Gson();
    Button button;

    Spinner spinner;

    SharedPreferences sharedPreferences;

    int[] rabbitPosition = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

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

        drawCardMessage =  new DrawCardMessage();

        spinner = findViewById(R.id.spinnerDrawMode);

        button.setOnClickListener((view) -> {
            sendMessageDraw();
        });

        //Test for drawing a card

        /*
        button.setOnClickListener(view -> {
            SecureRandom rand = new SecureRandom();
            int random = rand.nextInt(4)+1;
            String serverResponse = Integer.toString(random);
            showPopup(serverResponse);
        });
         */

        String[] drawOptions = new String [] {"random", "1", "2", "3", "Karotte"};
        ArrayAdapter<String> playerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, drawOptions);
        spinner.setAdapter(playerAdapter);

        ImageView rabbit1 = findViewById(R.id.rabbit1);
        ImageView rabbit2 = findViewById(R.id.rabbit2);
        ImageView rabbit3 = findViewById(R.id.rabbit3);
        ImageView rabbit4 = findViewById(R.id.rabbit4);

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

        rabbit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRabbit(rabbit1, currentRabbitPosition(1), 1);
            }
        });

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

        String roomId = getIntent().getStringExtra("roomId");
        String roomName = getIntent().getStringExtra("roomName");
        String playerId = getIntent().getStringExtra("playerId");
        String playerName = getIntent().getStringExtra("playerName");

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("roomId", roomId);
        editor.putString("roomName", roomName);
        editor.putString("playerId", playerId);
        editor.putString("playerName", playerName);

        editor.apply();
    }


    private void connectToServer() {
        networkHandler.connectToServer(this::receiveDrawMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectToServer();
    }


    private <T> void receiveDrawMessage(T message) {
        if (message instanceof String) {
            String jsonString = (String) message;

            DrawCardMessage drawCardMessage1 = gson.fromJson(jsonString, DrawCardMessage.class);
            String serverResponse = drawCardMessage1.getCard();

            showPopup(serverResponse);
        }
    }

    private void showPopup(String serverResponse) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Handle default case
        if (serverResponse != null) {
            switch (serverResponse) {
                case "1":
                    Rabbit1 rabbit1 = new Rabbit1();
                    fragmentTransaction.add(R.id.fragmentContainer, rabbit1, "Rabbit1Tag");
                    break;
                case "2":
                    Rabbit2 rabbit2 = new Rabbit2();
                    fragmentTransaction.add(R.id.fragmentContainer, rabbit2, "Rabbit2Tag");
                    break;
                case "3":
                    Rabbit3 rabbit3 = new Rabbit3();
                    fragmentTransaction.add(R.id.fragmentContainer, rabbit3, "Rabbit3Tag");
                    break;
                case "4":
                    Carrot carrot = new Carrot();
                    fragmentTransaction.add(R.id.fragmentContainer, carrot, "CarrotTag");
                    break;
                default:
                    break;
            }
        }
        fragmentTransaction.commit();
    }

    private void sendMessageDraw() {
        drawCardMessage.setMessageType(MessageType.DRAW_CARD);
        drawCardMessage.setPlayerID(sharedPreferences.getString("playerId", null));
        drawCardMessage.setRoomID(sharedPreferences.getString("roomId", null));
        drawCardMessage.setCard("random");
        drawCardMessage.setActionTypeDrawCard(ASK_FOR_CARD);

        String jsonMessage = new Gson().toJson(drawCardMessage);
        networkHandler.sendMessageToServer(jsonMessage);
    }

    private int currentRabbitPosition(int rabbitNumber) {
        for (int i = 0; i < rabbitPosition.length; i++) {
            if (rabbitPosition[i] == rabbitNumber) {
                rabbitPosition[i] = 0;
                return i;
            }
        }
        return -1;
    }

    private boolean isFieldFree (int fieldToGo) {
        return rabbitPosition[fieldToGo] == 0;
    }

    private void moveRabbit(ImageView clickedRabbit, int currentPosition, int rabbitNumber) {
        SecureRandom rand = new SecureRandom();
        int fieldToGo = rand.nextInt(4)+1;

        if(fieldToGo + currentPosition >= fields.length){
            fieldToGo = rand.nextInt(fields.length - currentPosition);
        }

        ViewGroup parentLayout = (ViewGroup) findViewById(R.id.relative_layout3);
        ViewGroup currentParent = (ViewGroup) clickedRabbit.getParent();
        currentParent.removeView(clickedRabbit);

        // Add the rabbit ImageView to the parent layout of the field
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
}

