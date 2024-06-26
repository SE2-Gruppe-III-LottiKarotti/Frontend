package at.aau.serg.websocketdemoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import at.aau.serg.websocketdemoapp.R;

public class Rabbit3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rabbit3, container, false);

        ImageButton closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().remove(Rabbit3.this).commit();
        });
        return view;
    }
}