import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

// classe premettant de définir un fichier en le téléchargant depuis une url
// et en le stockant dans un fichier .db

public class file {

    private Connection connexion;

    public Connection getConnexion () {
        return connexion;
    }

    // télécharger un fichier .db depuis une url
    private void downloadFile(String url, String fileName) {
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

    // constructeur de la classe file
    public file (String url, String fileName) {
        try {
            // télécharger le fichier depuis l'url
            downloadFile(url, fileName);
            System.out.println("Connexion à "+fileName+" établie.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // connexion à la base de données
    public void connect (String nom) {
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

    // récupérer les informations du fichier .db créé avec une requete SQL
    public void getInformations (String nom) {
        String sql;
        // REQUETE SQL
        sql = "select * from Infos where id= (select max(id) from Infos)";
        // vérifie si la requete est fonctionnelle avant de l'exécuter
        try (PreparedStatement pstmt  = connexion.prepareStatement(sql)){
            // récupère les résultats de la requete
            ResultSet rs  = pstmt.getResultSet();
            // récupère les données de la requete
            while (rs.next()) {
                // récupère les données de la requete
                System.out.println(rs.getString("GenerationFichier"));
                System.out.println(rs.getString("jour"));
                System.out.println(rs.getString("dvalue"));
                System.out.println(rs.getString("message"));
                System.out.println(rs.getString("pas"));
                System.out.println(rs.getString("hvalue"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // afficher les informations du fichier .db créé avec une requete SQL
    public void showInformations (String nom) {
        String sql;
        // REQUETE SQL
        sql = "select * from Infos where id= (select max(id) from Infos)";
        // vérifie si la requete est fonctionnelle avant de l'exécuter
        try (PreparedStatement pstmt  = connexion.prepareStatement(sql)){
            // récupère les résultats de la requete
            ResultSet rs = pstmt.executeQuery();
            String data = rs.getString("data");
            // création d'un objet JSON à partir d'une chaîne de caractères
            JSONObject obj = new JSONObject(data);
            // récupère les données de la requete
            obj.showData(data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
