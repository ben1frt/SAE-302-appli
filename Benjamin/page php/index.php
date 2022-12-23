<?php

# récupération d'un fichier json
$json = file_get_contents('http://localhost:8080/Projet%20Benjamin/Projet%20Benjamin/page%20php/JSON%20file/JSON%20file.json');

# décodage du fichier json
$json_data = json_decode($json, true);

# affichage des données



?>