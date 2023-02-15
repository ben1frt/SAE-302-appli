public class Main {
    public static void main(String[] args) throws Exception {
        //télécharger le fichier .db
        downloadFile("http://isis.unice.fr/~mgautero/ext/sae302/bd/ecowatt.db", "internet.db");
        //afficher le contenu de la bd
        ben internet = new ben("internet.db");
        //SAE302 local = new SAE302("local.db")
        internet.searchIntoDb();
    }
}
