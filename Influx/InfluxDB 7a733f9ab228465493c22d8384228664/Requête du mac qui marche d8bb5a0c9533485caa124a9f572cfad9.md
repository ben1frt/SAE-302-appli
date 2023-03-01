# Requête du mac qui marche

Requete provenant du MAC qui fonctionne sur l’interface UI

import "csv"
import "experimental/http"

url = "[https://raw.githubusercontent.com/ben1frt/SAE-302-appli/main/Java RecoveryData/usefulData.csv](https://raw.githubusercontent.com/ben1frt/SAE-302-appli/main/Java%20RecoveryData/usefulData.csv)"
csvData = string(v: http.get(url: url).body)

csv.from(csv: csvData, mode: "raw")