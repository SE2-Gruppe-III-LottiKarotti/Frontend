package at.aau.serg.websocketdemoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import at.aau.serg.websocketdemoapp.R;

public class StartActivity extends AppCompatActivity {

    Button buttonOpenRoom;
    Button buttonJoinRoom;

    Button buttonReJoinRoom;

    Button buttonInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonOpenRoom = findViewById(R.id.buttonOpenRoom);
        buttonJoinRoom = findViewById(R.id.buttonJoinRoom);
        buttonReJoinRoom = findViewById(R.id.buttonRejoinRoom);
        buttonInstructions = findViewById(R.id.buttonInstructions);

        buttonOpenRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOpenRoomActivity();
            }
        });

        buttonJoinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJoinRoomActivity();
            }
        });

        buttonReJoinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRejoinRoomActivity();
            }
        });

        buttonInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, Instructions.class);
                startActivity(intent);
            }
        });




    }

    private void openOpenRoomActivity() {
        Intent intent = new Intent(this, OpenRoomActivity.class);
        startActivity(intent);
    }

    private void openJoinRoomActivity() {
        Intent intent = new Intent(this, JoinRoomActivity.class);
        startActivity(intent);
    }

    private void openRejoinRoomActivity() {
        //Intent intent = new Intent(this, RejoinRoomActivity.class);
        //startActivity(intent);
    }

    private void openInstructionsActivity() {
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
    }


}