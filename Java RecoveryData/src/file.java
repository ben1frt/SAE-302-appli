import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import org.json.JSONObject;
import org.json.*;



// classe premettant de définir un fichier en le téléchargant depuis une url
// et en le stockant dans un fichier .db

public class file {

    private Connection connexion;

    public Connection getConnexion () {
        return connexion;
    }

    // téléchargement d'un fichier depuis une url et stocker dans un fichier .db

    public file (String url, String fileName) {
        try {
            // L'url d'accès
            URL website = new URL(url);
            // Créer une bd ou l'ouvrir si existante
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // requete permettant de récupérer les données d'un fichier .db en chaines de caractères

    public String getValues (String nom) {
        String values = "";
        try {
            // L'url d'accès connexion
            String url = "jdbc:sqlite:./"+nom;
            // Créer une bd ou l'ouvrir si existante
            connexion = DriverManager.getConnection(url);
            System.out.println("Connexion à "+nom+" établie.");    
            // Créer une requête
            String sql = "select * from Infos where id= (select max(id) from Infos)";
            PreparedStatement statement = connexion.prepareStatement(sql);
            // Exécuter la requête
            ResultSet result = statement.executeQuery();
            // Récupérer les données
            values = result.getString("data");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return values;
    }

    // Création d'un objet JSON à partir d'une chaine de caractères

    public JSONObject createJSON (String values) {
        JSONObject obj = new JSONObject(values);
        return obj;
    }

    // Afficher les données d'un objet JSON

    public void showJSON (JSONObject obj) {
        System.out.println(obj);
    }

    // Récupérer les données utiles d'un objet JSON dans un HashMap

    public HashMap<String, String[]> getUsefulData (JSONObject obj) {
        // récupération des données
        JSONArray data = obj.getJSONArray("signals");
        // création d'un HashMap pour stocker les données utiles
        HashMap<String, String[]> usefulData = new HashMap<String, String[]>();
        // récupération des données utiles
        for (int i = 0; i < data.length(); i++) {
            JSONObject donnée = data.getJSONObject(i);
            // récupération de la date
            String jour = donnée.getString("jour");
            // ajout de la date dans le HashMap
            usefulData.put(jour, new String[24]);
            // récupération des données de la date
            JSONArray heures = donnée.getJSONArray("values");
            // récupération des données utiles de la date
            for (int j = 0; j < heures.length(); j++) {
                JSONObject heure = heures.getJSONObject(j);
                // récupération de l'heure
                Integer h = heure.getInt("pas");
                // récupération de la consommation
                Integer consommation = heure.getInt("hvalue");
                // ajout de la consommation dans le HashMap
                usefulData.get(jour)[h] = consommation.toString();
            }
        }
        // afficher les données utiles
        /*for (String key : usefulData.keySet()) {
            System.out.println(key);
            for (int i = 0; i < usefulData.get(key).length; i++) {
                System.out.println(usefulData.get(key)[i]);
            }
        }*/
        return usefulData;
    }

    // Création d'un fichier .csv à partir d'un HashMap avec les données utiles avec le séparateur ";"
    
    public void createCSV (HashMap<String, String[]> usefulData, String name) {
        // création d'un fichier .csv
        try {
            // création d'un fichier .csv
            File file = new File(name + ".csv");
            // création d'un objet FileWriter
            FileWriter fw = new FileWriter(file);
            // création d'un objet BufferedWriter
            BufferedWriter bw = new BufferedWriter(fw);
            // nom des colonnes du fichier .csv
            bw.write("days,pas1,pas2,pas3,pas4,pas5,pas6,pas7,pas8,pas9,pas10,pas11,pas12,pas13,pas14,pas15,pas16,pas17,pas18,pas19,pas20,pas21,pas22,pas23,pas24");
            // saut de ligne
            bw.newLine();
            // récupération des données utiles
            for (String key : usefulData.keySet()) {
                // récupération de la date
                bw.write(key+",");
                // récupération des données de la date
                for (int i = 0; i < (usefulData.get(key).length - 1); i++) {
                    // récupération de la consommation
                    bw.write(usefulData.get(key)[i]+",");
                }
                bw.write(usefulData.get(key)[usefulData.get(key).length - 1]);
                // saut de ligne
                bw.newLine();
            }
            // fermeture du fichier
            bw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
