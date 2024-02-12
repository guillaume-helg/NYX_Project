package toulouse.miage.l3.nyx.core.utils;

import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.model.Usine;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UtilsChaine {

    /**
     * Read line of a file named chaines.csv, and transform these line into an object Chaine,
     * add it to an Arraylist and return it
     * @return : ArrayList with Chaine read from the file chaine.csv
     */
    public static ArrayList<Chaine> readChaine() {
        String nomFichier = "NYX/src/main/resources/toulouse/miage/l3/nyx/save/chaines.csv";
        String ligne;
        ArrayList<Chaine> chaines = new ArrayList<>();

        try {
            BufferedReader fichier = new BufferedReader(new FileReader(nomFichier));

            while ((ligne = fichier.readLine()) != null) {
                chaines.add(parseChaine(ligne));
            }

            fichier.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès fichier");
        }
        return chaines;
    }


    /**
     * Parse a line in parameter with the format : Code;Nom;Entrée (code,qte);Sortie (code,qte)
     * into a new Chaine
     *
     * @param line : Here is an example of line : C001;Propulsion;(E004,1),(E002,0.5),(E003,0.1);(E005,1)
     * @return : Return a new Chaine create with content of this line
     */
    public static Chaine parseChaine(String line) {
        String[] parts = line.split(";");

        String code = parts[0];
        String name = parts[1];

        HashMap<Element, Double> inputElementList = parseElementList(parts[2]);
        HashMap<Element, Double> outputElementList = parseElementList(parts[3]);

        return new Chaine(code, name, inputElementList, outputElementList);
    }

    /**
     * Parse an input in parameter with the format : (E004,1),(E002,0.5),(E003,0.1)
     * into a Hashmap<Element, Double> and return it
     *
     * Before putting Element into the Hashmap, the function check if the Element exist in
     * the list of Element, if not it print an error
     *
     * @param input : Here is an example of line : (E004,1),(E002,0.5),(E003,0.1)
     * @return Hashmap which contains a list of Element and quantity attributed
     */
    private static HashMap<Element, Double> parseElementList(String input) {
        HashMap<Element, Double> elementMap = new HashMap<>();
        String[] elements = input.split(",");

        for (int i = 0; i < elements.length; i += 2) {
            String code = elements[i].replaceAll("[(]", "");
            Double value = Double.parseDouble(elements[i + 1].replaceAll("[)]", ""));

            Element existingElement = findElementByCode(Usine.getElements(), code);

            if (existingElement != null) {
                elementMap.put(existingElement, value);
            } else {
                System.err.println("Your Element does not exist");
            }
        }
        return elementMap;
    }

    /**
     * Research, from a list of Element passed in parameter, an element which have the same code
     * also passed in parameter. The function return the Element with the code.
     * @param elements : list of element, in which you can retrieve every Element of the app
     * @param code : unique code used like a primary key for the object Element
     * @return : return the Element which contain this code/primary key
     */
    private static Element findElementByCode(List<Element> elements, String code) {
        for (Element element : elements) {
            if (element.getCode().equals(code)) {
                return element;
            }
        }
        return null;
    }

    /**
     * Write the object Element contains in a table chaines into a file chaine.csv
     * @param chaines : table with every chaine of use by the application
     */
    public static void writeChaine(Chaine[] chaines) {
        String fileName = "chaine.csv";
        try {
            PrintWriter file = new PrintWriter(new FileWriter(fileName));

            for (Chaine chaine : chaines) {
                file.println(chaine.getCode() + ";"
                                + chaine.getNom() + ";"
//                           + parseElementListIntoText(chaine.getFormattedListeEntree() + ";"
//                           + parseElementListIntoText(chaine.getFormattedListeSortie() + ";"
                );
            }

            file.close();
        } catch (IOException ex) {
            System.out.println("File access problem");
        }
    }
}