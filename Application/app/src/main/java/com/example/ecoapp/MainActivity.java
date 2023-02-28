package com.example.ecoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        String[] textviewHour = new String[100];
        String[] imageviewRisk = new String[100];
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                textviewHour[i*j] = "Day" + j + "Hour" + i;
                imageviewRisk[i*j] = "Day" + j + "Alert" + i;
            }
        }

        // Déclaration d'un tableau de LinearLayout
        GridLayout[] gridLayouts = new GridLayout[5];
        for (int i = 1; i <= 4; i++) {
            String viewImage = "Day" + i + "Layout";
            int resIDImage = getResources().getIdentifier(viewImage, "id", getPackageName());
            gridLayouts[i] = ((GridLayout) findViewById(resIDImage));
        }

        // ajouter les paramètres des textview pour 24 heures des 4 jours
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

        // ajouter les pastilles dans imageview pour 24 heures des 4 jours
        /*int risk = 1; // variable de la valeur du risques0 = vert, 1 = orange, 2 = rouge
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 4; j++) {
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(15, 15, 0, 0);
                imageView.setLayoutParams(lp);
                imageView.setId(imageviewRisk[i*j].hashCode());
                if(risk == 0) {
                    imageView.setImageResource(R.drawable.pastille_verte);
                } else if (risk == 1) {
                    imageView.setImageResource(R.drawable.pastille_orange);
                } else if (risk == 2) {
                    imageView.setImageResource(R.drawable.pastille_rouge);
                }
                imageView.setMinimumHeight(70);
                imageView.setMinimumWidth(70);
                imageView.setPaddingRelative(10, 10, 10, 10);
                // insert into layout
                gridLayouts[j].addView(imageView);
            }
        }*/

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

        // récupérer les données d'un site internet
        String html = "<p>An <a href='http://example.com/'><b>example</b></a>link.</p>";
        Document doc = Jsoup.parse(html);
        Element link = doc.select("a").first();

        String text = doc.body().text(); // "An example link"
        String linkHref = link.attr("href"); // "http://example.com/"
        String linkText = link.text(); // "example""
        String linkOuterH = link.outerHtml();
        // "<a href="http://example.com"><b>example</b></a>"
        String linkInnerH = link.html(); // "<b>example</b>"

        System.out.println(text);
        System.out.println(linkHref);
        System.out.println(linkText);
        System.out.println(linkOuterH);
        System.out.println(linkInnerH);


        //Read the GitHub files
        final String inputURL = "https://github.com/company/project/tree/master/XML/withData";
        //final String inputURL = "https://raw.githubusercontent.com/company/project/blob/master/XML/withData/myFileName.xml";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(new HttpGet(inputURL));
        StatusLine statusLine = response.getStatusLine();
        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        System.out.println("Response Code : " + statusLine.getStatusCode() + " ---- " + "Response Phrase :  " + statusLine.getReasonPhrase());
        System.out.println("Response body: " + responseBody);


    }

    // Méthode DisplayLegend
    public void DisplayEcoGestes (View view) {
        Intent intent = new Intent(this, EcoGestes.class);
        startActivity(intent);
    }
}