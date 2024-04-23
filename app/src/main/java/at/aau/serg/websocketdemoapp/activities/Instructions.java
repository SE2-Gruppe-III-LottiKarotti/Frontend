package at.aau.serg.websocketdemoapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import at.aau.serg.websocketdemoapp.R;

public class Instructions extends AppCompatActivity {
    private TextView instructionTextView;
    private Button nextButton;
    private int currentInstructionIndex = 0;
    private List<String> instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        // Initialisiere die Anweisungen
        instructions = new ArrayList<>();
        instructions.add("Schritt 1: Tippe auf den roten Knopf, um zu starten.");
        instructions.add("Schritt 2: Bewege das Gerät nach links oder rechts, um zu steuern.");
        instructions.add("Schritt 3: Sammle Sterne ein, um Punkte zu erhalten.");
        // Füge weitere Anweisungen hinzu...

        // Verknüpfe die Ansichten aus dem Layout
        instructionTextView = findViewById(R.id.instructionTextView);
        nextButton = findViewById(R.id.nextButton);

        // Zeige die erste Anweisung an
        showCurrentInstruction();

        // OnClickListener für die Weiter-Schaltfläche
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zeige die nächste Anweisung an
                currentInstructionIndex++;
                showCurrentInstruction();
            }
        });
    }

    // Methode zur Anzeige der aktuellen Anweisung
    private void showCurrentInstruction() {
        if (currentInstructionIndex < instructions.size()) {
            String currentInstruction = instructions.get(currentInstructionIndex);
            instructionTextView.setText(currentInstruction);
        } else {
            // Zeige eine Meldung an, dass alle Anweisungen abgeschlossen sind
            Toast.makeText(this, "Alle Anweisungen abgeschlossen", Toast.LENGTH_SHORT).show();
        }
    }
}

