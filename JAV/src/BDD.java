import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class App {

    private Connection connexion;

    public Connection getConnexion () {
        return connexion;
    }

    public void searchIntoDb () {
        String sql;
        ResultSet rs;

        sql= "select * from Infos";
        rs= null;

        try (
             PreparedStatement pstmt  = connexion.prepareStatement(sql)){
            rs  = pstmt.executeQuery();

            // Traitement du résultat
            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") +  "\t" + 
                    rs.getString("nom") + "\t" +
                    rs.getInt("annee")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
    }

    public App () {
        try {
            // L'url d'accès            ici juste en dessous faut mettre un .db, je n'en ai pas trouvé
            String url = "jdbc:sqlite:./demo.db";
            // Créer une bd ou l'ouvrir si existante
            connexion = DriverManager.getConnection(url);
            System.out.println("Connexion à SQLite établie.");    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        App p;

        p= new App();
        /*
        p.searchIntoDb();*/
    }
}
