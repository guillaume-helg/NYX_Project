package toulouse.miage.l3.nyx.core.utils;

import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Commande;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static toulouse.miage.l3.nyx.core.model.Usine.addToCommandes;
import static toulouse.miage.l3.nyx.core.model.Usine.getCommandes;

public class UtilsCommande {
    /**
     * Create new file, in which you can read every chaine in the arrayList listesCommande
     * The file is in the repertory export
     * It contains : the date of the commande, the content of the command and his quantity
     * and the total price of the command with is percentage of result.
     */
    public static boolean writeResultInAFile() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = dateTime.format(formatter);

        String nomFichier = Paths.get("NYX", "src", "main", "resources", "toulouse", "miage", "l3", "nyx", "save", "commande", "commande_" + formattedDate + ".txt").toString();

        String separator = "\n--------------------------------------------------------------------\n";
        try {
            PrintWriter fichier = new PrintWriter(new FileWriter(nomFichier));

            fichier.println("Date de la commande -> " + LocalDateTime.now());
            fichier.println(separator);
            fichier.println("L'incateur de valeur est égal à -> "); // mettre la valeur du résultat des commandes
            fichier.println(separator);
            fichier.println("La liste des commandes \n");

            for (Commande c : getCommandes()) {
                fichier.println("\tChaîne : " + c.getChaine().getCode() + " - " + c.getChaine().getNom());
                fichier.println("\tQuantité : " + c.getQuantity());
                fichier.println("\tListe d'éléments d'entrée : " + c.getChaine().getListeElementEntree());
                fichier.println("\tListe d'éléments de sortie : " + c.getChaine().getListeElementSortie());
                fichier.println("\n");
            }

            fichier.println(separator);
            fichier.close();
        } catch (IOException ex) {
            System.err.println("File access problem");
            return false;
        }
        return true;
    }

    public static void parseHashmapToCommand(Map<Chaine, Integer> listeCommande) {
        for (Map.Entry<Chaine, Integer> entry : listeCommande.entrySet()) {
            addToCommandes(new Commande(entry.getKey(), entry.getValue()));
        }
    }
}
