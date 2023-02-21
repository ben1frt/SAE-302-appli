import org.json.JSONObject;

public class Principal {

    public static void main(String[] args) throws Exception {
        // création d'un objet de type file
        file f = new file("http://isis.unice.fr/~mgautero/ext/sae302/bd/ecowatt.db", "internet.db");
        // récupération des données du fichier .db
        String values = f.getValues("internet.db");
        // création d'un objet JSON à partir des données récupérées
        JSONObject obj = f.createJSON(values);
        // affichage des données de l'objet JSON
        f.showJSON(obj);
        // getUsefulData permet de récupérer les données utiles de l'objet JSON
        f.getUsefulData(obj);
    }
}
