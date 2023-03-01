# Requête

# Ecriture dans la base de donnée

Dans notre cas, la base de donnée n’est pas protégé par des mots de passe

Pour écrire dans la base de donnée, on utilisera le script python suivant :

```python
import csv
from influxdb import InfluxDBClient

############################################
host = 'localhost'
port = 8086
#user = 'UID'
#password = 'PASSWORD'

############################################

# Connexion à la base de données InfluxDB

#client = InfluxDBClient(host='localhost', port=8086, user, password)

client = InfluxDBClient(host=, port)
client.switch_database('SAE32')

# Ouverture du fichier CSV et insertion des données dans la base de données
with open('usefulData', 'r') as csv_file:
    csv_reader = csv.DictReader(csv_file)
    for row in csv_reader:
        timestamp_rfc3339 = row['days']
        del row['days']
        data_points = [
            {
                "measurement": "nom_de_la_mesure",
                "time": timestamp_rfc3339,
                "fields": row
            }
        ]
        client.write_points(data_points)
```

Pour le moment le script fonctionne en python, on doit maintenant essayer de passer avec le langage Java pour écrire dans la BDD.

## Test Java

## Installation de Java sur Linux

```java
sudo add-apt-repository ppa:openjdk-r/ppa

sudo apt-get update

sudo apt-get install openjdk-11-jdk
```

## Tentative en Java

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

public class Main {

    public static void main(String[] args) {
        // Connexion à la base de données InfluxDB
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086");
        influxDB.setDatabase("SAE32");

        // Ouverture du fichier CSV et insertion des données dans la base de données
        try (BufferedReader br = new BufferedReader(new FileReader("usefulData.csv"))) {
            String line;
            Map<String, String> tags = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String timestamp_rfc3339 = values[0];
                for (int i = 1; i < values.length; i++) {
                    String field_name = "pas" + i;
                    double field_value = Double.parseDouble(values[i]);
                    Point point = Point.measurement("Ecowatt")
                            .time(ZonedDateTime.parse(timestamp_rfc3339, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant().toEpochMilli(), javax.measure.unit.SI.MILLI(javax.measure.unit.SI.SECOND))
                            .addField(field_name, field_value)
                            .build();
                    influxDB.write(point);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```

Ce script “ne” fonctionne “pas” encore, il faut encore 2-3 jours de test afin de permettre son utilisation.