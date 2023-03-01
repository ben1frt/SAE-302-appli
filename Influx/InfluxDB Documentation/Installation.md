# Installation

# Comment Installer Influx UI

On installe le paquet permettant d’initialiser la base

```
apt install influxdb 
```

Pour pouvoir communiquer avec la BDD depuis le terminal, on ajoute également :

```
apt install influxdb-client
```

D’autre méthode sont possible mais la commande `influx` ne fonctionne que de cette manière, si on suit la doc sur ce [lien](https://docs.influxdata.com/influxdb/cloud/tools/influx-cli/?t=Linux), la commande influx ne retourne que la page —help.