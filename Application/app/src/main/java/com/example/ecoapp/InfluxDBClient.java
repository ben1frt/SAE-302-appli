package com.example.ecoapp;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class InfluxDBClient {
    private static final String INFLUXDB_URL = "http://192.168.64.6:8086";
    private static final String INFLUXDB_DATABASE = "SAE32";

    public interface OnDataFetchedListener {
        void onDataFetched(List<QueryResult.Result> results);
    }


    public List<EcowattData> getAllEcowattData(final OnDataFetchedListener listener) {
        final List<EcowattData> ecowattData = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Connexion à la base de données InfluxDB
                InfluxDB influxDB = InfluxDBFactory.connect(INFLUXDB_URL);
                influxDB.setDatabase(INFLUXDB_DATABASE);

                // Requête pour récupérer toutes les données de la mesure "data"
                String query = "SELECT * FROM data";

                // Exécution de la requête et récupération des résultats
                QueryResult queryResult = influxDB.query(new Query(query));

                // ajouter les données dans la liste ecowattData
                for (QueryResult.Result result : queryResult.getResults()) {
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

                // Appel de la méthode de rappel avec les résultats de la requête
                listener.onDataFetched(queryResult.getResults());
            }
        }).start();

        // Retourner la liste ecowattData
        return ecowattData;
    }
}


