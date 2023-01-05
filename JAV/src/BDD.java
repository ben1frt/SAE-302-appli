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

public class SAE302_main {

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

    public SAE302_main () {
        try {
            // L'url d'accès
            String url = "jdbc:sqlite:./ecowattest.db";
            // Créer une bd ou l'ouvrir si existante
            connexion = DriverManager.getConnection(url);
            System.out.println("Connexion à SQLite établie.");    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchIntoDb () {
        String sql;
        ResultSet rs;

        sql = "select * from Infos";
        rs = null;

        try (
             PreparedStatement pstmt  = connexion.prepareStatement(sql)){
            rs = pstmt.executeQuery();

            // Traitement du résultat
            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") +  "\t" + 
                    rs.getString("data")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //main
    public static void main(String[] args) throws Exception {
        //télécharger le fichier .db
        downloadFile("http://isis.unice.fr/~mgautero/ext/sae302/bd/ecowatt.db", "ecowattest.db");
        //afficher le contenu de la bd
        SAE302_main app = new SAE302_main();
        app.searchIntoDb();

    }

    
}
