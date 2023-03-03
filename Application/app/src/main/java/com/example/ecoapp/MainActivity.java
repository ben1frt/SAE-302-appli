package com.example.ecoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import org.influxdb.dto.QueryResult;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimationBackground();
        String[] textviewHour = DeclareTextView();
        String[] imageviewRisk = DeclareImageView();
        TextView[] textViewDay = DeclareTextViewDay();
        GridLayout[] gridLayouts = DeclareGridLayout();


        new InfluxDBClient().getAllEcowattData(new InfluxDBClient.OnDataFetchedListener() {
            @Override
            public void onDataFetched(List<QueryResult.Result> results) {
                List<EcowattData> ecowattdata = ReadRiskDataInfluxDB(results);
                //AddPastille(imageviewRisk, gridLayouts, ecowattdata);
                AddDate(textViewDay, ecowattdata);
            }
        });

        DisplayTextView(textviewHour, gridLayouts);
    }

    // Méthode avec la class InfluxDBClient pour lire les données de la base de données InfluxDB
    public List<EcowattData> ReadRiskDataInfluxDB(List<QueryResult.Result> results) {
        List<EcowattData> ecowattData = new ArrayList<>();

        for (QueryResult.Result result : results) {
            System.out.println(result.toString());
            for (QueryResult.Series serie : result.getSeries()) {
                for (List<Object> values : serie.getValues()) {
                    String dateString = (String) values.get(0);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    format.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date timestamp;
                    try {
                        timestamp = format.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        timestamp = new Date();
                    }
                    int[] pas = new int[24];
                    for (int i = 0; i < 24; i++) {
                        pas[i] = Integer.parseInt(values.get(i + 1).toString());
                    }
                    ecowattData.add(new EcowattData(timestamp, pas));
                }
            }
        }

        return ecowattData;
    }


    // Déclaration d'un tableau de TextView
    private String[] DeclareTextView() {
        String[] textviewHour = new String[100];
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                textviewHour[i * j] = "Day" + j + "Hour" + i;
            }
        }
        return textviewHour;
    }

    // Déclaration d'un tableau de TextView pour les jours
    private TextView[] DeclareTextViewDay() {
        TextView[] TextViewDay = new TextView[5];
        for (int i = 1; i <= 4; i++) {
            String viewImage = "Day" + i;
            int resIDImage = getResources().getIdentifier(viewImage, "id", getPackageName());
            TextViewDay[i] = ((TextView) findViewById(resIDImage));
        }
        return TextViewDay;
    }

    // Déclaration d'un tableau de TextView
    private String[] DeclareImageView() {
        String[] imageviewRisk = new String[100];
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                imageviewRisk[i * j] = "Day" + j + "Alert" + i;
            }
        }
        return imageviewRisk;
    }

    // Déclaration d'un tableau de GridLayout
    private GridLayout[] DeclareGridLayout() {
        GridLayout[] GridLayouts = new GridLayout[5];
        for (int i = 1; i <= 4; i++) {
            String viewImage = "Day" + i + "Layout";
            int resIDImage = getResources().getIdentifier(viewImage, "id", getPackageName());
            GridLayouts[i] = ((GridLayout) findViewById(resIDImage));
        }
        return GridLayouts;
    }

    // ajouter les paramètres des textview pour 24 heures des 4 jours
    private void DisplayTextView(String[] textviewHour, GridLayout[] gridLayouts) {
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                TextView textView = new TextView(this);
                textView.setId(textviewHour[i * j].hashCode());
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
                gridLayouts[j].addView(textView);
            }
        }
    }

    // ajouter les pastilles dans imageview pour 24 heures des 4 jours
    public void AddPastille(String[] imageviewRisk, GridLayout[] gridLayouts, List<EcowattData> ecowattData) {
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                ImageView imageView = new ImageView(this);
                imageView.setId(imageviewRisk[i * j].hashCode());
                //System.out.println("Pas : " + imageviewRisk[i * j]);
                if (ecowattData.get(j - 1).getPas(i) == 1) {
                    imageView.setImageResource(R.drawable.pastille_verte);
                } else if (ecowattData.get(j - 1).getPas(i) == 2) {
                    imageView.setImageResource(R.drawable.pastille_orange);
                } else {
                    imageView.setImageResource(R.drawable.pastille_rouge);
                }
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setAdjustViewBounds(true);
                imageView.setPadding(0, 0, 0, 0);
                imageView.setMaxWidth(100);
                imageView.setMaxHeight(60);
                imageView.setMinimumWidth(100);
                imageView.setMinimumHeight(60);
                // insert into layout
                gridLayouts[j].addView(imageView);
            }
        }
    }

    // Ajouter la date au textView avec les données de influxDB
    public void AddDate(TextView[] textViewDay, List<EcowattData> ecowattData) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        for (int j = 1; j <= 4; j++) {
            Date date = ecowattData.get(j - 1).getTimestamp();
            textViewDay[j].setText(dateFormat.format(date));
        }
    }





    // Méthode DisplayLegend
    public void DisplayEcoGestes (View view) {
        Intent intent = new Intent(this, EcoGestes.class);
        startActivity(intent);
    }

    // Animation de fond
    private void AnimationBackground() {
        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }
}