package com.example.ecoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Animation de fond
        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        // Déclaration d'un tableau de TextView et d'un tableau de ImageView
        String[] textview = new String[100];
        String[] imageview = new String[100];
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                textview[i*j] = "Day" + j + "Hour" + i;
                imageview[i*j] = "Day" + j + "Alert" + i;
            }
        }

        // Déclaration d'un tableau de LinearLayout
        LinearLayout[] linearLayouts = new LinearLayout[5];
        for (int i = 1; i <= 4; i++) {
            String viewImage = "Day" + i + "Layout";
            int resIDImage = getResources().getIdentifier(viewImage, "id", getPackageName());
            linearLayouts[i] = ((LinearLayout) findViewById(resIDImage));
        }

        // ajouter les paramètres des textview
        List<View> views = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                TextView textView = new TextView(this);
                textView.setId(textview[i*j].hashCode());
                if (i < 10) {
                    textView.setText("0" + i + "h");
                } else {
                    textView.setText(i + "h");
                }
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setTypeface(null, Typeface.BOLD);
                textView.setWidth(100);
                textView.setHeight(60);
                // insert into layout
                linearLayouts[j].addView(textView);
            }
        }

        // ajouter les paramètres des imageview
        int value = 1;
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if(i == 1) {
                    lp.setMargins(-2390, 90, 0, 0);
                } else {
                    lp.setMargins(20, 90, 0, 0);
                }
                imageView.setLayoutParams(lp);
                imageView.setId(imageview[i*j].hashCode());
                if(value == 0) {
                    imageView.setImageResource(R.drawable.pastille_verte);
                } else if (value == 1) {
                    imageView.setImageResource(R.drawable.pastille_orange);
                } else if (value == 2) {
                    imageView.setImageResource(R.drawable.pastille_rouge);
                }
                imageView.setMinimumHeight(80);
                imageView.setMinimumWidth(80);
                imageView.setPaddingRelative(10, 10, 10, 10);
                // insert into layout
                linearLayouts[j].addView(imageView);
            }
        }
    }

    // Méthode DisplayLegend
    public void DisplayEcoGestes (View view) {
        Intent intent = new Intent(this, EcoGestes.class);
        startActivity(intent);
    }
}