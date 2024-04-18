package at.aau.serg.websocketdemoapp.activities;

import android.os.Bundle;
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

import at.aau.serg.websocketdemoapp.R;

public class OpenRoomActivity extends AppCompatActivity {

    EditText editTextRoomName;
    Spinner spinnerNumPlayers;
    EditText editTextCreator;
    Button buttonOpenRoomNow;

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

        editTextRoomName = findViewById(R.id.editTextRoomName);
        spinnerNumPlayers = findViewById(R.id.spinnerNumPlayers);
        editTextCreator = findViewById(R.id.editTextCreatorName);
        buttonOpenRoomNow = findViewById(R.id.buttonOpenRoomNow);

        String[] playerOptions = new String [] {"2", "3", "4"};
        ArrayAdapter<String> playerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, playerOptions);
        spinnerNumPlayers.setAdapter(playerAdapter);

        buttonOpenRoomNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoom();
            }
        });
    }

    private void openRoom() {
        String roomName = editTextRoomName.getText().toString();
        String finalNumPlayers = spinnerNumPlayers.getSelectedItem().toString();
        String creatorName = editTextCreator.getText().toString();

        //print to user
        Toast.makeText(OpenRoomActivity.this, "Opening Room...\nRoom Name: " + roomName + "\nNumber of Players: " + finalNumPlayers + "\nCreator Name: " + creatorName, Toast.LENGTH_SHORT).show();

        //TODO: to handle this topic: use more than one message maybe... as atomic as possible
    }


}