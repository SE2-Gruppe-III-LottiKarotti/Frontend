package at.aau.serg.websocketdemoapp.activities;

import static com.google.gson.internal.$Gson$Types.arrayOf;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.Random;

import at.aau.serg.websocketdemoapp.R;

public class GameActivity extends AppCompatActivity {
    ImageView[] fields;

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
    }

    private int currentRabbitPosition(int rabbitNumber) {
        for (int i = 0; i < rabbitPosition.length; i++) {
            if (rabbitPosition[i] == rabbitNumber) {
                rabbitPosition[i] = 0;
                return i;
            }
        }
        return 0;
    }

    private boolean isFieldFree (int fieldToGo) {
        return rabbitPosition[fieldToGo] == 0;
    }

    private void moveRabbit(ImageView clickedRabbit, int currentPosition, int rabbitNumber) {
        Random rand = new Random();
        int fieldToGo = rand.nextInt(4)+1;

        if(fieldToGo + currentPosition >= fields.length){
            fieldToGo = rand.nextInt(fields.length - currentPosition) +1;
        }

        ViewGroup parentLayout = (ViewGroup) findViewById(R.id.relative_layout3);
        ViewGroup currentParent = (ViewGroup) clickedRabbit.getParent();
        currentParent.removeView(clickedRabbit);

        // Add the rabbit ImageView to the parent layout of the fields
        parentLayout.addView(clickedRabbit);

        while(!(isFieldFree(currentPosition+fieldToGo))) {
            int newTarget = currentPosition+fieldToGo;

            fieldToGo++;
        }

        float targetX = fields[currentPosition+fieldToGo].getX();
        float targetY = fields[currentPosition+fieldToGo].getY();
        clickedRabbit.setX(targetX);
        clickedRabbit.setY(targetY);

        rabbitPosition[currentPosition+fieldToGo]=rabbitNumber;
        }
    }
