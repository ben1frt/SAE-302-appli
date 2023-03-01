# Influx → Android Studio

Pour récupérer les données de la base de données InfluxDB sur une application Android construite avec Android Studio, on peut utiliser une bibliothèque cliente pour InfluxDB, telle que `influxdb-java`.

Voici les étapes à suivre pour intégrer `influxdb-java` à votre projet Android :

Ajoutez la dépendance à `influxdb-java` dans votre fichier `build.gradle (Module: app)` :

```java
dependencies {
implementation 'org.influxdb:influxdb-java:2.23'
}
```

Utilisez la bibliothèque `influxdb-java` pour interroger la base de données et récupérer les données souhaitées. Voici le code pour récupérer toutes les données de la mesure "Ecowatt" :

```java

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
```

Il faut utiliser la méthode `getAllEcowattData()` pour récupérer les données de la mesure "Ecowatt" depuis l’application Android. On peut par exemple les afficher dans une vue de liste , en utilisant un adaptateur personnalisé pour les afficher correctement.