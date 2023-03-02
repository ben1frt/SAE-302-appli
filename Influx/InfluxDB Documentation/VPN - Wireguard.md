# VPN - Wireguard

Pour nous simplifier la vie, on va utiliser pivpn un utilitaire permettant de paramétrer automatiquement Wireguard. 

On utilise cette solution pour des raison de temps de et de praticité. Cela fonctionne tout aussi bien que le package d’origine et c’est très rapide a mettre en place.

# Installation PiVPN

On récupère le paque sur GitHub :

```
git clone https://github.com/pivpn/pivpn.git
sudo bash pivpn/auto_install/install.sh
```

- Dans l’installeur, on choisit Wireguard
- Le port est laissé par défaut 51820
- DNS au choix
- Le client utilise un IP publique
- On accepte tout le reste

Et voila on a mis en place un serveur VPN !

# Comment s’en servir ?

liste de commande :

```
pivpn
::: Control all PiVPN specific functions!
:::
::: Usage: pivpn <command> [option]
:::
::: Commands:
:::    -a, add              Create a client conf profile
:::    -c, clients          List any connected clients to the server
:::    -d, debug            Start a debugging session if having trouble
:::    -l, list             List all clients
:::   -qr, qrcode           Show the qrcode of a client for use with the mobile app
:::    -r, remove           Remove a client
:::  -off, off              Disable a client
:::   -on, on               Enable a client
:::    -h, help             Show this help dialog
:::    -u, uninstall        Uninstall pivpn from your system!
:::   -up, update           Updates PiVPN Scripts
:::   -bk, backup           Backup VPN configs and user profiles
```

## Création d’un hôte

pour créer un hôte, on utilise la commande 

```
pivpn -a 
```

On suit la procédure et notre profil est prêt à être exporter

## Récupérer le profil

2 manières s’offrent à nous le QR code ou l’export du fichier depuis l’emplacement ou il est enregistré.

```
pivpn -qr 
-> choix du profil 
>QR code généré 
(utiliser de préférence un terminal en SSH pour des soucis de génération du QR))
```

Ou bien on copie le fichier stocké dans /home/ubuntu/configs/

(le chemin du fichier est écrit lors de la génération du profil.)

## Listing des clients

On peut lister les clients avec la commande 

```
pivpn -c 
```

Cette commande nous est utile pour vérifier le bon fonctionnement de la connexion

## Ouverture des ports

Afin de pouvoir nous connecter au VPN, nous avons besoin d’ouvrir un port sur la Box Internet 

Cela dépend du fournisseurs, il faudra ce référer a leur doc pour associer un port de celle-ci a notre serveur nouvellement configurés.

# Utilisation du client

Nous avons besoin d’installer l’application Wireguard sur notre machine cliente afin de monter le tunnel 

on doit donc après l’avoir installé 

1. Créer un tunnel vide
2. Copier-coller le contenu du fichier dans le tunnel ou suivre le QR code
3. On clique sur activer et on espère