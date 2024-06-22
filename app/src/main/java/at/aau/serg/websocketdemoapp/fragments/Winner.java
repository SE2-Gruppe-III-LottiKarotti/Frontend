package at.aau.serg.websocketdemoapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.activities.MainActivity;

public class Winner extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_winner, container, false);

        TextView winner = view.findViewById(R.id.winnerText);
        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        // Get the text from the bundle
        Bundle args = getArguments();
        if (args != null) {
            String text = args.getString("player");
            winner.setText(text);
        }
        return view;
    }
}