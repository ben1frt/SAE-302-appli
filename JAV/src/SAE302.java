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
import java.util.ArrayList;

public class SAE302 {

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

    public SAE302 (String nom) {
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

    public void searchIntoDb () {
        String sql;
        ResultSet rs;
        String data;
        ArrayList<String> data3;

        sql = "select * from Infos where id= (select max(id) from Infos)";
        rs = null;

        try (
            PreparedStatement pstmt  = connexion.prepareStatement(sql)){
            rs = pstmt.executeQuery();

            // Traitement du résultat  
            data = rs.getString("data");
            //System.out.println(data);
            String[] data2 = data.split("\\[");
            for (String a : data2){
                data3.add(a.split("\\{"));
            }
            for (String a : b){
                System.out.println(a);
            }

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
        SAE302 internet = new SAE302("internet.db");
        //SAE302 local = new SAE302("local.db")
        internet.searchIntoDb();

    }
}