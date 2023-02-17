public class Principal {

    public static void main(String[] args) throws Exception {
        // création d'un objet de type file
        file f = new file("http://isis.unice.fr/~mgautero/ext/sae302/bd/ecowatt.db", "internet.db");
        // connexion à la base de données
        f.connect("internet.db");
        // afficher le contenu de la base de données
        f.showInformations("internet.db");
    }
}
