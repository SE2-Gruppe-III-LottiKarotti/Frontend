package at.aau.serg.websocketdemoapp.activities;

import static com.google.gson.internal.$Gson$Types.arrayOf;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;

import at.aau.serg.websocketdemoapp.R;

public class GameActivity extends AppCompatActivity {

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

        int[] fields = new int[26];

        ImageView rabbit1 = findViewById(R.id.rabbit1);
        ImageView rabbit2 = findViewById(R.id.rabbit2);
        ImageView rabbit3 = findViewById(R.id.rabbit3);
        ImageView rabbit4 = findViewById(R.id.rabbit4);

        TextView field1 = findViewById(R.id.field1);
        ImageView field2 = findViewById(R.id.field2);
        TextView field3 = findViewById(R.id.field3);
        TextView field4 = findViewById(R.id.field4);
        TextView field5 = findViewById(R.id.field5);
        TextView field6 = findViewById(R.id.field6);
        TextView field7 = findViewById(R.id.field7);
        TextView field8 = findViewById(R.id.field8);
        TextView field9 = findViewById(R.id.field9);
        TextView field10 = findViewById(R.id.field10);
        TextView field11 = findViewById(R.id.field11);
        TextView field12 = findViewById(R.id.field12);
        TextView field13 = findViewById(R.id.field13);
        TextView field14 = findViewById(R.id.field14);
        TextView field15 = findViewById(R.id.field15);
        TextView field16 = findViewById(R.id.field16);
        TextView field17 = findViewById(R.id.field17);
        TextView field18 = findViewById(R.id.field18);
        TextView field19 = findViewById(R.id.field19);
        TextView field20 = findViewById(R.id.field20);
        TextView field21 = findViewById(R.id.field21);
        TextView field22 = findViewById(R.id.field22);
        TextView field23 = findViewById(R.id.field23);
        TextView field24 = findViewById(R.id.field24);
        TextView field25 = findViewById(R.id.field25);
        TextView field26 = findViewById(R.id.field26);

        rabbit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field2.setImageResource(R.drawable.red_rabbit);
                rabbit1.setImageDrawable(null);
            }
        });

        rabbit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field2.setImageResource(R.drawable.red_rabbit);
                rabbit1.setImageDrawable(null);
            }
        });

        rabbit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field2.setImageResource(R.drawable.red_rabbit);
                rabbit1.setImageDrawable(null);
            }
        });

        rabbit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field2.setImageResource(R.drawable.red_rabbit);
                rabbit1.setImageDrawable(null);
            }
        });
    }

}