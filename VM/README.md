# VM

Pour mettre en place notre VM, on va devoir au préalable avoir d’installer :

- Java
- Python3, PIP
- crontab
- InfluxDB et influxDB-client

Si ce n’est pas fait, il suffit de taper la commande suivante :

```
apt update && apt install -y influxdb influxdb-client pip default-jdk cron
```

ensuite on récupère le répertoire [script](https://github.com/ben1frt/SAE-302-appli/tree/main/VM/script) présent dans le répertoire VM ici présent, on place ces scripts dans le répertoire d’entrée de root pour éviter toute altération de celui-ci par un utilisateur sans privilège.

```
wget lien 

cp -r  /chemin/Repertoire /root/ 
```

Il ne nous reste plus qu’a ajouter les scripts dans crontab pour effectuer l’ajout de donnée dans la table de manière automatisée 

Dans /etc/crontab on ajoute ceci a la suite :

```
#Toutes les 5 minutes l'utilisateur root lancera le script 'Shortcut'
*/5 * * * *	root	/root/script/Shortcut 
```

on oublie pas de changer les droits d’utilisation du script :

```
chmod u+rwx /root/script/Shortcut #Droit d'exécution, de lecture et d'écriture pour root 
chmod g+rx /root/script/Shortcut #Droit d'exécution, de lecture et d'écriture pour le groupe
chmod o-rwx /root/script/Shortcut #Aucun droit aux autres
```

Notre serveur est maintenant autonome, il ne reste plus qu’à lier la base de donnée à notre application.