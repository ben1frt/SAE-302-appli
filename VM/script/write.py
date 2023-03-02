import csv
from influxdb import InfluxDBClient

############################################
host = 'localhost'
port = 8086
table = 'data'
database = 'SAE32'
#user = 'UID'
#password = 'PASSWORD'

############################################

# Connexion à la base de données InfluxDB

#client = InfluxDBClient(host='localhost', port=8086, user, password)

client = InfluxDBClient(host, port)
client.switch_database(database)

try:
    client.drop_measurement(table)
finally:
    client.create_retention_policy(table, '1w', 1, default=True)


# Ouverture du fichier CSV et insertion des données dans la base de données
with open('/root/script/java/data.csv', 'r') as csv_file:
    csv_reader = csv.DictReader(csv_file)
    for row in csv_reader:
        timestamp_rfc3339 = row['days']
        del row['days']
        data_points = [
            {
                "measurement": table,
                "time": timestamp_rfc3339,
                "fields": row
            }
        ]
        client.write_points(data_points)
