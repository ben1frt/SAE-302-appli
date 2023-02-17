import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Dictionary;
import java.util.List;
import java.util.ArrayList;

public class _Aurelien {

    private Connection connexion;

    public Connection getConnexion () {
        return connexion;
    }

    //télécharger un fichier .db depuis une url
    public static void downloadFile(String url, String fileName) throws IOException {
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }

    public _Aurelien (String nom) {
        try {
            // L'url d'accès
            String url = "jdbc:sqlite:./"+nom;
            // Créer une bd ou l'ouvrir si existante
            connexion = DriverManager.getConnection(url);
            System.out.println("Connexion à "+nom+" établie.");    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // les données semble être un fichier JSON, mais nous récupérons sous un format String
    // nous allons donc devoir le traiter pour récupérer les données utiles
    // pour cela, nouse avons analysé le fichier et nous avons trouvé des répétitions
    // qui nous permettent de faire des irérations pour récupérer les données utiles
    // exemple du début des données que l'on récupéres:
    //      {"signals": [
    //          {
    //          "GenerationFichier": "2023-01-24T23:00:00+01:00", 
    //          "GenerationFichier": "2023-01-24T23:00:00+01:00", 
    //          "jour": "2023-01-25T00:00:00+01:00", 
    //          "dvalue": 1, 
    //          "message": "Pas d\u2019alerte.", 
    //          "values": [
    //              {"pas": 0, "hvalue": 1}, 
    //              {"pas": 1, "hvalue": 1}, 
    //              {"pas": 2, "hvalue": 1}, 
    // cela se répète pour chaque jour, et nous aperçevons qu'il y a des une hiérarchie
    // il y a des objets qui contiennent d'autres objets, mais aussi des tableaux pour certains
    // nous avons donc décidé de commencer à traiter les données par les objets les plus hauts
    // ici l'objets le plus haut est "signals" mais il ne nous intéresse pas
    // nous allons donc commencer par segmenter par l'objet "GenerationFichier" (split())
    // pour récupérer le jour impliqué aux valeurs (indexOf() & substring())
    // ainsi il ets important


    public void searchIntoDb () {
        // INITIALISATION DES VARIABLES POUR RÉCUÉPRER LES DONNÉES UTILES
        // Pour la requete
        String sql;
        ResultSet request;
        String data;
        // en cas de changement de format de données, il faudra changer les variables suivantes
        String segmentation1 = "GenerationFichier";
        // pour le traitement des données
        List<String> jourList = new ArrayList<String>();
        List<String> valueList = new ArrayList<String>();

        // REQUETE SQL
        sql = "select * from Infos where id= (select max(id) from Infos)";

        // vérifie si la requete est fonctionnelle avant de l'exécuter
        try (PreparedStatement pstmt  = connexion.prepareStatement(sql)){
            // Exécution de la requete
            request = pstmt.executeQuery();

            // Traitement du résultat
            data = request.getString("data");
            String[] jours = data.split("\""+segmentation1+"\":");
            for (int i = 1; i < jours.length; i++) {
                //System.out.println(jours[i]);
                int jourIndex = jours[i].indexOf("\"jour\": \"");
                //System.out.println(jour);
                String test = jours[i].substring(jourIndex+9, jourIndex+34);
                //System.out.println(test);
                jourList.add(test);

                String[] values1 = jours[i].split("\"values\":");
                String[] values2 = values1[1].split("},");
                for (int j = 0; j < values2.length; j++) {
                    int valueIndex = values2[j].indexOf("\"hvalue\":");
                    //System.out.println(valueIndex);
                    if (valueIndex == 13 || valueIndex == 12) {
                        String test2 = values2[j].substring(valueIndex+10, valueIndex+11);
                        //System.out.println(test2);
                        valueList.add(test2);
                    }
                }
            }
            System.out.println(valueList);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertIntoDb (String nom, int an) {
        String sql;


        sql= "insert into Infos ('nom', 'annee') Values (?, ?);";
        try (
        PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setInt(2, an);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initDb () {
        Statement s;
        String sql;
        
        try {
            s= connexion.createStatement();
            sql= "CREATE TABLE IF NOT EXISTS signals ( " + 
                "GenerationFichier DATE," +
                "jour DATE, " +
                "dvalue INTEGER, " +
                "message VARCHAR( 150 ), " +
                "annee INTEGER);";
            s.execute(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //main
    public static void main(String[] args) throws Exception {
        //télécharger le fichier .db
        downloadFile("http://isis.unice.fr/~mgautero/ext/sae302/bd/ecowatt.db", "internet.db");
        //afficher le contenu de la bd
        _Aurelien internet = new _Aurelien("internet.db");
        //SAE302 local = new SAE302("local.db")
        internet.searchIntoDb();

    }
}