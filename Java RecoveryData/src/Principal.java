import org.json.JSONObject;

public class Principal {

    public static void main(String[] args) {

        // création d'une variable de pour le nom du fichier .db et csv
        String nameBdd = "eco";
        String nameCsv = "data";
        // création d'un objet de type file
        file f = new file("http://isis.unice.fr/~mgautero/ext/sae302/bd/ecowatt.db", nameBdd+".db");
        // récupération des données du fichier .db
        String values = f.getValues(nameBdd+".db");
        // création d'un objet JSON à partir des données récupérées
        JSONObject obj = f.createJSON(values);
        // affichage des données de l'objet JSON
        ////f.showJSON(obj);
        // getUsefulData permet de récupérer les données utiles de l'objet JSON
        f.getUsefulData(obj);
        // création d'un fichier .csv à partir des données utiles
        f.createCSV(f.getUsefulData(obj), nameCsv);
    }
}
