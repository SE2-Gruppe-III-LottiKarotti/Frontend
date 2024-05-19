package at.aau.serg.websocketdemoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import at.aau.serg.websocketdemoapp.R;

public class Instructions extends StartActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        configureNextButton();
    }
    private void configureNextButton(){
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Instructions.this, Instructions2.class));
            }
        });
    }
}
