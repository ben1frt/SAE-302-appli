package com.example.ecoapp;

import java.time.Instant;
import java.util.List;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

public class InfluxDBClient {
    private static final String INFLUXDB_URL = "[http://localhost:8086](http://localhost:8086/)";
    private static final String INFLUXDB_DATABASE = "SAE32";

    public List<QueryResult.Result> getAllEcowattData() {
        // Connexion à la base de données InfluxDB
        InfluxDB influxDB = InfluxDBFactory.connect(INFLUXDB_URL);
        influxDB.setDatabase(INFLUXDB_DATABASE);

        // Requête pour récupérer toutes les données de la mesure "Ecowatt"
        String query = "SELECT * FROM Ecowatt";

        // Exécution de la requête et récupération des résultats
        QueryResult queryResult = influxDB.query(new Query(query));
        influxDB.close();

        return queryResult.getResults();
    }
}
