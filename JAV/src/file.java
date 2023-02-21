import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.DriverManager;



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

    // Récupérer les données utiles d'un objet JSON : "jour", "hvalue"

    public void getUsefulData (JSONObject obj) {
        String[] data = obj.toString().split(",");
        for (int i = 0; i < data.length; i++) {
            if (data[i].contains("jour")) {
                System.out.println(data[i]);
            }
            if (data[i].contains("hvalue")) {
                System.out.println(data[i]);
            }
        }
    }
    // Transformer les données utiles d'un objet JSON dans un fichier csv avec le séparateur ","

}
