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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimationBackground();
        String[] textviewHour = DeclareTextView();
        String[] imageviewRisk = DeclareImageView();
        GridLayout[] gridLayouts = DeclareGridLayout();

        // Récupérer les données de la base de données InfluxDB
        List<EcowattData> ecowattdata = ReadRiskDataInfluxDB();

        DisplayTextView(textviewHour, gridLayouts);
        AddPastille(imageviewRisk, gridLayouts, ecowattdata);



        // Ajouter les textview pour les jours
        TextView[] textviewDay = new TextView[5];
        SimpleDateFormat s = new SimpleDateFormat("dd/MM");
        Date date = new Date();
        for (int i = 1; i <= 4; i++) {
            String viewDay = "Day" + i;
            int resIDImage = getResources().getIdentifier(viewDay, "id", getPackageName());
            textviewDay[i] = ((TextView) findViewById(resIDImage));
            textviewDay[i].setText(s.format(date)+(i-1));
        }

    }

    // Méthode ReadRiskDataCSV pour lire le fichier CSV avec les données utilisées pour l'application
    /*public List<Risk> ReadRiskDataCSV() {
        List<Risk> risk = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        Risk riskData = null;
        try {
            // Step over headers
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                // Split by ','
                String[] tokens = line.split(",");
                // Read the data
                riskData = new Risk();
                riskData.setDay(tokens[0]);
                riskData.setRisk1(Integer.parseInt(tokens[1]));
                riskData.setRisk2(Integer.parseInt(tokens[2]));
                risk.add(riskData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(risk);
        return risk;
    }*/

    // Méthode avec la class InfluxDBClient pour lire les données de la base de données InfluxDB
    public List<EcowattData> ReadRiskDataInfluxDB() {
        List<EcowattData> ecowattData = new ArrayList<>();
        InfluxDBClient influxDBClient = new InfluxDBClient();
        influxDBClient.getAllEcowattData(new InfluxDBClient.OnDataFetchedListener() {
            @Override
            public void onDataFetched(List<QueryResult.Result> results) {
                // Faites ici ce que vous voulez avec les résultats récupérés
                for (QueryResult.Result result : results) {
                    System.out.println(result.toString());
                }
            }
        });
        return ecowattData;
    }

    // Déclaration d'un tableau de TextView
    private String[] DeclareTextView() {
        String[] textviewHour = new String[100];
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                textviewHour[i*j] = "Day" + j + "Hour" + i;
            }
        }
        return textviewHour;
    }

    // Déclaration d'un tableau de TextView
    private String[] DeclareImageView() {
        String[] imageviewRisk = new String[100];
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                imageviewRisk[i*j] = "Day" + j + "Alert" + i;
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
                textView.setId(textviewHour[i*j].hashCode());
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
    public void AddPastille(String[] imageviewRisk, GridLayout[] gridLayouts, List<EcowattData> risk) {
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                ImageView imageView = new ImageView(this);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.setMargins(15, 15, 0, 0);
                imageView.setLayoutParams(lp);
                imageView.setId(imageviewRisk[i*j].hashCode());
                int pas = risk.get(i*j).getPas(1); // récupère la valeur du pas pour cette pastille
                if (pas == 1) {
                    imageView.setImageResource(R.drawable.pastille_verte);
                } else if (pas == 2) {
                    imageView.setImageResource(R.drawable.pastille_orange);
                } else if (pas == 3) {
                    imageView.setImageResource(R.drawable.pastille_rouge);
                }
                imageView.setMinimumHeight(70);
                imageView.setMinimumWidth(70);
                imageView.setPaddingRelative(10, 10, 10, 10);
                // insert into layout
                gridLayouts[j].addView(imageView);
            }
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