package com.example.ecoapp;

import android.os.AsyncTask;

import java.util.List;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

public class InfluxDBClient {
    private static final String INFLUXDB_URL = "http://192.168.64.6:8086";
    private static final String INFLUXDB_DATABASE = "SAE32";

    public void getAllEcowattData(OnDataFetchedListener listener) {
        new InfluxDBQueryTask(listener).execute();
    }

    private class InfluxDBQueryTask extends AsyncTask<Void, Void, List<QueryResult.Result>> {
        private OnDataFetchedListener listener;

        public InfluxDBQueryTask(OnDataFetchedListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<QueryResult.Result> doInBackground(Void... voids) {
            // Connexion à la base de données InfluxDB
            InfluxDB influxDB = InfluxDBFactory.connect(INFLUXDB_URL);
            influxDB.setDatabase(INFLUXDB_DATABASE);

            // Requête pour récupérer toutes les données de la mesure "data"
            String query = "SELECT * FROM data";

            // Exécution de la requête et récupération des résultats
            QueryResult queryResult = influxDB.query(new Query(query));
            influxDB.close();

            return queryResult.getResults();
        }

        @Override
        protected void onPostExecute(List<QueryResult.Result> results) {
            if (listener != null) {
                listener.onDataFetched(results);
            }
        }
    }

    public interface OnDataFetchedListener {
        void onDataFetched(List<QueryResult.Result> results);
    }
}
