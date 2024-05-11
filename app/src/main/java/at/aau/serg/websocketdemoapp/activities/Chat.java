package at.aau.serg.websocketdemoapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.aau.serg.websocketdemoapp.R;

public class Chat extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<String> messageList;

    private EditText editTextMessage;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize views
        recyclerView = findViewById(R.id.recycler_view_chat);
        editTextMessage = findViewById(R.id.edit_text_message);
        buttonSend = findViewById(R.id.button_send);

        // Set up RecyclerView
        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        // Set up button click listener to send message
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    // Method to send message
    private void sendMessage() {
        String message = editTextMessage.getText().toString().trim();
        if (!message.isEmpty()) {
            // Add message to the list
            messageList.add("You: " + message);
            // Notify adapter that data set has changed
            chatAdapter.notifyDataSetChanged();
            // Clear input field
            editTextMessage.setText("");
        } else {
            Toast.makeText(this, "Please type a message", Toast.LENGTH_SHORT).show();
        }
    }
}
