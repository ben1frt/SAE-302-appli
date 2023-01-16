package com.example.ecoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Méthode DisplayLegend
    public void DisplayLegend (View view) {
        Intent intent = new Intent(this, LegendEcoApp.class);
        startActivity(intent);
    }
}